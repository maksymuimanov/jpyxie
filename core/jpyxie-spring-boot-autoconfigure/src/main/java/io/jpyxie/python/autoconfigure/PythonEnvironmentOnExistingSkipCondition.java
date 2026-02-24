package io.jpyxie.python.autoconfigure;

public class PythonEnvironmentOnExistingSkipCondition extends PythonEnvironmentOnExistingTypeCondition {
    public PythonEnvironmentOnExistingSkipCondition() {
        super(PythonEnvironmentProperties.OnExisting.SKIP);
    }
}
