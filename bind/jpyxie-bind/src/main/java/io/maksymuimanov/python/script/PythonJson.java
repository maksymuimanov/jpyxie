package io.maksymuimanov.python.script;

public class PythonJson extends PythonValueContainer<String> {
    public PythonJson(String value) {
        super(value);
    }

    @Override
    public String toPythonString() {
        return String.join("", "json.loads('", this.getValue(), "')");
    }
}
