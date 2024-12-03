// RegisterInteractorTest.java
package use_case_test;

import org.junit.Test;
import use_case.register.RegisterInputData;
import use_case.register.RegisterInteractor;
import use_case.register.RegisterOutputData;

import static org.junit.Assert.*;

public class RegisterInteractorTest {

    @Test
    public void testRegisterSuccess() {
        RegisterInteractor registerInteractor = new RegisterInteractor();
        RegisterInputData inputData = new RegisterInputData("newuser", "newpassword");
        RegisterOutputData outputData = registerInteractor.register(inputData);
        assertTrue(outputData.isSuccess());
        assertEquals("Registration successful.", outputData.getMessage());
    }

    @Test
    public void testRegisterFailure() {
        RegisterInteractor registerInteractor = new RegisterInteractor();
        RegisterInputData inputData = new RegisterInputData("existinguser", "password");
        registerInteractor.register(inputData);
        RegisterOutputData outputData = registerInteractor.register(inputData);
        assertFalse(outputData.isSuccess());
        assertEquals("Username already exists.", outputData.getMessage());
    }

    @Test
    public void testRegisterFailure2() {
        RegisterInteractor registerInteractor = new RegisterInteractor();
        RegisterInputData inputData = new RegisterInputData("user", "1");
        RegisterOutputData outputData = registerInteractor.register(inputData);
        assertFalse(outputData.isSuccess());
        assertEquals("Password must be at least 6 characters long.", outputData.getMessage());
    }


}