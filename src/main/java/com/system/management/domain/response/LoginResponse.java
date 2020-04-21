package com.system.management.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends GenericResponse {

    public LoginResponse(int returnCode, String token) {
        super(returnCode, token);
    }
}
