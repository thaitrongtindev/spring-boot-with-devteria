package com.devteria.jpaapi.dto.request.response;


public class AuthenticationResponse {
    private boolean authenticated;

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
