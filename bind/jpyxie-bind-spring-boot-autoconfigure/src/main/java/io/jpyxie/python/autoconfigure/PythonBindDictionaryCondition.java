package io.jpyxie.python.autoconfigure;

public class PythonBindDictionaryCondition extends PythonBindTypeCondition {
    protected PythonBindDictionaryCondition() {
        super(PythonBindProperties.Type.DICTIONARY);
    }
}
