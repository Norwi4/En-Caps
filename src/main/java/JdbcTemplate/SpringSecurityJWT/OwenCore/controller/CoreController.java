package JdbcTemplate.SpringSecurityJWT.OwenCore.controller;

import JdbcTemplate.SpringSecurityJWT.OwenCore.auth.AuthInOwen;
import JdbcTemplate.SpringSecurityJWT.OwenCore.model.OwenRequestLogin;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CoreController {

    private final AuthInOwen auth;

    public CoreController(AuthInOwen auth) {
        this.auth = auth;
    }

    @PostMapping("/login")
    public ResponseEntity<String> createTutorial(@RequestBody @JsonInclude(JsonInclude.Include.NON_NULL) OwenRequestLogin user) {
        try {
            auth.authOwen(user);
            return new ResponseEntity<>("Вы авторизированы", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
