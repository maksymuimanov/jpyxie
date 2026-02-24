package io.jpyxie.python.autoconfigure;

public class PythonEnvironmentOnExistingRemoveCondition extends PythonEnvironmentOnExistingTypeCondition {
    public PythonEnvironmentOnExistingRemoveCondition() {
        super(PythonEnvironmentProperties.OnExisting.REMOVE);
    }
}
