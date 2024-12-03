package interface_adapter.autentication;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

public class AuthenticationPresenter implements LoginOutputBoundary, LogoutOutputBoundary {

    @Override
    public void presentLoginResult(LoginOutputData outputData) {
        System.out.println(outputData.getMessage());
    }

    @Override
    public void presentLogoutResult(LogoutOutputData outputData) {
        System.out.println(outputData.getMessage());
    }
}
