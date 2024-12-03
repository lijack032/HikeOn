// LoginInteractorTest.java
package use_case_test;

import org.junit.Test;
import use_case.login.LoginInputData;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputData;
import use_case.register.RegisterInputData;
import use_case.register.RegisterInteractor;

import static org.junit.Assert.*;

public class LoginInteractorTest {

    @Test
    public void testLoginSuccess() {
        RegisterInteractor registerInteractor = new RegisterInteractor();
        registerInteractor.register(new RegisterInputData("user", "password"));

        LoginInteractor loginInteractor = new LoginInteractor();
        LoginInputData inputData = new LoginInputData("user", "password");
        LoginOutputData outputData = loginInteractor.login(inputData);
        assertTrue(outputData.isSuccess());
        assertEquals("Login successful. Welcome, user!", outputData.getMessage());
    }

    @Test
    public void testLoginFailure() {
        LoginInteractor loginInteractor = new LoginInteractor();
        LoginInputData inputData = new LoginInputData("user", "wrongpassword");
        LoginOutputData outputData = loginInteractor.login(inputData);
        assertFalse(outputData.isSuccess());
        assertEquals("Invalid username or password.", outputData.getMessage());
    }

    @Test
    public void testLoginFailureNoUser() {
        LoginInteractor loginInteractor = new LoginInteractor();
        LoginInputData inputData = new LoginInputData("", "password");
        LoginOutputData outputData = loginInteractor.login(inputData);
        assertFalse(outputData.isSuccess());
        assertEquals("Invalid username or password.", outputData.getMessage());
    }
}