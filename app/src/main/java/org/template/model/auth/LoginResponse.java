package org.template.model.auth;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private String refresh;
}
