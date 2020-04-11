package org.template.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.template.model.auth.SecurityContextUserInfo;
import org.template.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Collections.singletonList;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthService service = getApplicationContext().getBean(AuthService.class);
        http.authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").hasAuthority("ADMIN");
        http.csrf().disable();
        http.addFilterBefore(new JwtFilter(service::validateAndGetUserInfo), UsernamePasswordAuthenticationFilter.class);
    }

    private static class JwtFilter implements Filter {

        private final Function<String, Optional<SecurityContextUserInfo>> userInfo;

        private JwtFilter(Function<String, Optional<SecurityContextUserInfo>> userInfo) {
            this.userInfo = userInfo;
        }

        @Override
        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
            try {
                if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
                    String header = ((HttpServletRequest) req).getHeader("Authorization");
                    Optional<SecurityContextUserInfo> result = userInfo.apply(header.replaceFirst("Bearer", "").trim());
                    result.ifPresent(r -> SecurityContextHolder.getContext().setAuthentication(getAuth(r, "ADMIN")));
                }
            } catch (Exception e) {
                log.error("Error occurred while applying JWT filter", e);
            } finally {
                chain.doFilter(req, resp);
            }
        }

        private static Authentication getAuth(SecurityContextUserInfo principal, String authority) {
            List<SimpleGrantedAuthority> authorities = singletonList(new SimpleGrantedAuthority(authority));
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        }
    }
}
