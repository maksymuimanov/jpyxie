import logging
import os
import subprocess
import sys
from typing import List

from fastapi import FastAPI, HTTPException, Security
from fastapi.security import APIKeyHeader
from pydantic import BaseModel

TOKEN = os.getenv("PYTHON_SERVER_TOKEN")
os.environ.pop("PYTHON_SERVER_TOKEN", None)
SUBPROCESS_TIMEOUT = float(os.getenv("PYTHON_SUBPROCESS_TIMEOUT", "30"))
LOGGING_ENABLED = os.getenv("PYTHON_LOGGING_ENABLED", "true").lower() == "true"
if LOGGING_ENABLED:
    logging.basicConfig(
        level=logging.INFO,
        format="%(asctime)s [%(levelname)s] %(message)s",
    )

app = FastAPI()
token_header = APIKeyHeader(name="X-Token", auto_error=False)

class PythonRestRequest(BaseModel):
    script: str
    fieldNames: List[str] = []

class PythonRestPipRequest(BaseModel):
    name: str
    libraryName: str
    options: List[str] = []

@app.post("/execute")
async def execute_script(request: PythonRestRequest,
                         api_key_header: str = Security(token_header)):
    if not api_key_header or api_key_header != TOKEN:
        if LOGGING_ENABLED:
            logging.info(f"Client failed to connect to the server: {request}")
        raise HTTPException(401, detail="Client is not authenticated")
    try:
        java_execution_context = {}
        exec(request.script, java_execution_context, java_execution_context)
        if LOGGING_ENABLED:
            logging.info(f"Client executed the script: {request}")
        result = {}
        for field_name in request.fieldNames:
            if field_name not in java_execution_context:
                raise HTTPException(400, f"Field '{field_name}' not found")
            result[field_name] = java_execution_context[field_name]
        return result
    except Exception as e:
        if LOGGING_ENABLED:
            logging.info(f"Client failed to execute the script: {request}, {str(e)}")
        raise HTTPException(400, detail=str(e))
        
@app.post("/pip")
def execute_pip_command(request: PythonRestPipRequest,
                        api_key_header: str = Security(token_header)):
    if not api_key_header or api_key_header != TOKEN:
        if LOGGING_ENABLED:
            logging.info(f"Client failed to connect to the server: {request}")
        raise HTTPException(401, detail="Client is not authenticated")
    try:
        process = subprocess.run(
            [sys.executable, "-m", "pip", request.name, request.libraryName, *request.options],
            stdout=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL,
            timeout=SUBPROCESS_TIMEOUT
        )
        if LOGGING_ENABLED:
            logging.info(f"Client executed the pip command: {request.name}, {request.libraryName}")
        return_code = process.returncode
        return return_code == 0
    except Exception as e:
        if LOGGING_ENABLED:
            logging.info(f"Client failed to execute the pip command: {request.name}, {request.libraryName}, {str(e)}")
        raise HTTPException(400, detail=str(e))