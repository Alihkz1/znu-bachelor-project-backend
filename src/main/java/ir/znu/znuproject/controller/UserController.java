package ir.znu.znuproject.controller;

import ir.znu.znuproject.command.ChangePasswordCommand;
import ir.znu.znuproject.command.LoginCommand;
import ir.znu.znuproject.command.SignUpCommand;
import ir.znu.znuproject.dto.UserListDto;
import ir.znu.znuproject.service.UserService;
import ir.znu.znuproject.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<UserListDto>> list() {
        return userService.getAllUsers();
    }

    @PostMapping(path = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> signup(@RequestBody(required = true) SignUpCommand command) {
        return userService.signup(command);
    }

    @PostMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> login(@RequestBody(required = true) LoginCommand command) {
        return userService.login(command);
    }

    @PutMapping(path = "changepassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> changePassword(@RequestBody(required = true) ChangePasswordCommand command) {
        return userService.changePassword(command);
    }
}
