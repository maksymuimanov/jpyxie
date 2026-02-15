package io.jpyxie.python.autoconfigure;

public class PythonBindJsonCondition extends PythonBindTypeCondition {
    protected PythonBindJsonCondition() {
        super(PythonBindProperties.Type.JSON);
    }
}
