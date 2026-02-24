package io.jpyxie.python.autoconfigure;

public class PythonEnvironmentOnExistingFailCondition extends PythonEnvironmentOnExistingTypeCondition {
    public PythonEnvironmentOnExistingFailCondition() {
        super(PythonEnvironmentProperties.OnExisting.FAIL);
    }
}
