// LogoutInteractorTest.java
package use_case_test;

import org.junit.Test;
import use_case.logout.LogoutInteractor;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class LogoutInteractorTest {

    @Test
    public void testLogout() {
        LogoutInteractor logoutInteractor = new LogoutInteractor();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        logoutInteractor.logout();
        assertEquals("User logged out successfully.", outputStream.toString().trim());

    }
}