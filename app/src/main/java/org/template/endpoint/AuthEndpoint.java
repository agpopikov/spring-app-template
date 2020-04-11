package org.template.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.template.model.auth.UserResponse;
import org.template.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthEndpoint {

    private final AuthService service;

    public AuthEndpoint(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public void login() {
        // stub
    }

    @PostMapping("refresh")
    public void refresh() {
        // stub
    }

    @GetMapping("/user")
    public UserResponse user() {
        return new UserResponse();
    }
}
