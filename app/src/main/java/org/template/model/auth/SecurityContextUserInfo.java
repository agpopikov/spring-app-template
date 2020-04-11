package org.template.model.auth;

import lombok.Data;

@Data
public class SecurityContextUserInfo {

    private final String login;

    public SecurityContextUserInfo(String login) {
        this.login = login;
    }
}
