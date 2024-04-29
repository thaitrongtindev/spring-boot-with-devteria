package com.devteria.jpaapi.dto.request.response;

import lombok.Builder;

public class IntrospectResponse {
    private boolean valid;

    public IntrospectResponse(boolean b) {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
