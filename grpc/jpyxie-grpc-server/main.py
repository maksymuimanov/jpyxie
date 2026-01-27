import json
import logging
import os
import subprocess
import sys
from concurrent import futures

import grpc

import python_pb2
import python_pb2_grpc

TOKEN = os.getenv("PYTHON_SERVER_TOKEN")
os.environ.pop("PYTHON_SERVER_TOKEN", None)
HOST = os.getenv("PYTHON_SERVER_HOST")
PORT = os.getenv("PYTHON_SERVER_PORT")
MAX_WORKERS = int(os.getenv("PYTHON_SERVER_THREAD_POOL_MAX_WORKERS"))
APPEARANCE = os.getenv("PYTHON_RESULT_APPEARANCE")
SUBPROCESS_TIMEOUT = float(os.getenv("PYTHON_SUBPROCESS_TIMEOUT", "30"))
LOGGING_ENABLED = os.getenv("PYTHON_LOGGING_ENABLED", "true").lower() == "true"
if LOGGING_ENABLED:
    logging.basicConfig(
        level=logging.INFO,
        format="%(asctime)s [%(levelname)s] %(message)s",
    )

class PythonService(python_pb2_grpc.PythonGrpcServiceServicer):
    def SendCode(self, request, context):
        if LOGGING_ENABLED:
            logging.info(f"Client wants to execute script: {request}")
        script = request.script
        meta = dict(context.invocation_metadata())
        if meta.get("x-token") != TOKEN:
            if LOGGING_ENABLED:
                logging.info(f"Client failed to connect to the server: {request}")
            context.set_code(grpc.StatusCode.PERMISSION_DENIED)
            context.set_details("Invalid credentials")
            return python_pb2.GrpcPythonResponse()
        try:
            java_execution_context = {}
            exec(script, java_execution_context, java_execution_context)
            if LOGGING_ENABLED:
                logging.info(f"Client executed the script: {request}")

            result = {}
            if request.fieldNames:
                for field_name in request.fieldNames:
                    if field_name not in java_execution_context:
                        context.set_details(f"Field '{field_name}' not found")
                        context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
                        return python_pb2.GrpcPythonResponse()
                    result[field_name] = json.dumps(java_execution_context[field_name])
            elif APPEARANCE and APPEARANCE in java_execution_context:
                result[APPEARANCE] = json.dumps(java_execution_context[APPEARANCE])

            return python_pb2.GrpcPythonResponse(fields=result)
        except Exception as e:
            if LOGGING_ENABLED:
                logging.info(f"Client failed to execute the script: {request}, {str(e)}")
            context.set_details(str(e))
            context.set_code(grpc.StatusCode.INTERNAL)
            return python_pb2.GrpcPythonResponse()

    def SendPip(self, request, context):
        if LOGGING_ENABLED:
            logging.info(f"Client wants to execute pip command: {request}")
        meta = dict(context.invocation_metadata())
        if meta.get("x-token") != TOKEN:
            if LOGGING_ENABLED:
                logging.info(f"Client failed to connect to the server: {request}")
            context.set_code(grpc.StatusCode.PERMISSION_DENIED)
            context.set_details("Invalid credentials")
            return python_pb2.GrpcPythonPipResponse(successful=False)
        try:
            process = subprocess.run(
                [sys.executable, "-m", "pip", request.name, request.libraryName, *request.options],
                stdout=subprocess.DEVNULL,
                stderr=subprocess.DEVNULL,
                timeout=SUBPROCESS_TIMEOUT,
            )
            if LOGGING_ENABLED:
                logging.info(f"Client executed the pip command: {request.name}, {request.libraryName}")
            return python_pb2.GrpcPythonPipResponse(successful=process.returncode == 0)
        except Exception as e:
            if LOGGING_ENABLED:
                logging.info(
                    f"Client failed to execute the pip command: {request.name}, {request.libraryName}, {str(e)}"
                )
            context.set_details(str(e))
            context.set_code(grpc.StatusCode.INTERNAL)
            return python_pb2.GrpcPythonPipResponse(successful=False)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=MAX_WORKERS))
    python_pb2_grpc.add_PythonGrpcServiceServicer_to_server(PythonService(), server)
    server.add_insecure_port(HOST + ':' + str(PORT))
    server.start()
    logging.info(f"gRPC server running at {HOST}:{PORT}")
    server.wait_for_termination()

if __name__ == '__main__':
    serve()