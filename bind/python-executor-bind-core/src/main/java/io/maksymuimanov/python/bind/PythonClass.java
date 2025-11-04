package io.maksymuimanov.python.bind;

public class PythonClass implements PythonSerializable {
    public static final String PYTHON_CLASS_KEYWORD = "class";
    private String name;
    private PythonInitFunction initFunction;

    public PythonClass(String name, PythonInitFunction initFunction) {
        this.name = name;
        this.initFunction = initFunction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PythonInitFunction getInitFunction() {
        return initFunction;
    }

    public void setInitFunction(PythonInitFunction initFunction) {
        this.initFunction = initFunction;
    }

    @Override
    public String toPythonString() {
        return String.join("", PYTHON_CLASS_KEYWORD, " ", this.getName(), ":\n", this.getInitFunction().toPythonString());
    }
}
