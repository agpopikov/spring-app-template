package org.template.model.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String login;
    private String password;
}
