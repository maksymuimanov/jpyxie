from google.protobuf.internal import containers as _containers
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from collections.abc import Iterable as _Iterable, Mapping as _Mapping
from typing import ClassVar as _ClassVar, Optional as _Optional

DESCRIPTOR: _descriptor.FileDescriptor

class GrpcPythonRequest(_message.Message):
    __slots__ = ("script", "fieldNames")
    SCRIPT_FIELD_NUMBER: _ClassVar[int]
    FIELDNAMES_FIELD_NUMBER: _ClassVar[int]
    script: str
    fieldNames: _containers.RepeatedScalarFieldContainer[str]
    def __init__(self, script: _Optional[str] = ..., fieldNames: _Optional[_Iterable[str]] = ...) -> None: ...

class GrpcPythonResponse(_message.Message):
    __slots__ = ("fields",)
    class FieldsEntry(_message.Message):
        __slots__ = ("key", "value")
        KEY_FIELD_NUMBER: _ClassVar[int]
        VALUE_FIELD_NUMBER: _ClassVar[int]
        key: str
        value: str
        def __init__(self, key: _Optional[str] = ..., value: _Optional[str] = ...) -> None: ...
    FIELDS_FIELD_NUMBER: _ClassVar[int]
    fields: _containers.ScalarMap[str, str]
    def __init__(self, fields: _Optional[_Mapping[str, str]] = ...) -> None: ...

class GrpcPythonPipRequest(_message.Message):
    __slots__ = ("name", "libraryName", "options")
    NAME_FIELD_NUMBER: _ClassVar[int]
    LIBRARYNAME_FIELD_NUMBER: _ClassVar[int]
    OPTIONS_FIELD_NUMBER: _ClassVar[int]
    name: str
    libraryName: str
    options: _containers.RepeatedScalarFieldContainer[str]
    def __init__(self, name: _Optional[str] = ..., libraryName: _Optional[str] = ..., options: _Optional[_Iterable[str]] = ...) -> None: ...

class GrpcPythonPipResponse(_message.Message):
    __slots__ = ("successful",)
    SUCCESSFUL_FIELD_NUMBER: _ClassVar[int]
    successful: bool
    def __init__(self, successful: bool = ...) -> None: ...
