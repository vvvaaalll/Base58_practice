package com.example.Base58_practice.contoller;

import com.example.Base58_practice.dto.UpdateUserDto;
import com.example.Base58_practice.dto.UserDto;
import com.example.Base58_practice.service.RabbitMqSender;
import com.example.Base58_practice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final RabbitMqSender rabbitMQSender;

    @Operation(description = "Returns page of users", summary = "Returns page of users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("admin")
    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/page")
    public Page<UserDto> getUserPage(final Pageable pageable) {
        return userService.getUserPage(pageable);
    }


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("admin")
    @GetMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getUsers() throws RuntimeException {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping()
    @Transactional
    @RolesAllowed({"admin", "user"})
    public UserDto addUser(@RequestBody UserDto userDto) throws RuntimeException {

        rabbitMQSender.send(userDto);
        return this.userService.addUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    @RolesAllowed("user")
    @PatchMapping("/{id}")
    public UserDto patchUser(@PathVariable("id") long id, @RequestBody UpdateUserDto updateUserDto) throws RuntimeException {
        return this.userService.patchUser(id, updateUserDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @RolesAllowed("user")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") long id) throws RuntimeException {
        return this.userService.getUserById(id);
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) throws RuntimeException {
        this.userService.deleteUser(id);
    }

}
