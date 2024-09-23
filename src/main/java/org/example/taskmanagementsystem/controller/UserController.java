package org.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taskmanagementsystem.dto.Result;
import org.example.taskmanagementsystem.dto.StatusCode;
import org.example.taskmanagementsystem.dto.user.UserRq;
import org.example.taskmanagementsystem.dto.user.UserRqToUserConverter;
import org.example.taskmanagementsystem.dto.user.UserRs;
import org.example.taskmanagementsystem.dto.user.UserToUserRsConverter;
import org.example.taskmanagementsystem.entity.User;
import org.example.taskmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/user")
public class UserController {

    private final UserService userService;
    private final UserToUserRsConverter userToUserRsConverter;
    private final UserRqToUserConverter userRqToUserConverter;



    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {

        return new Result(true, StatusCode.SUCCESS, "Found one",
                userToUserRsConverter.convert(userService.findById(id)));
    }


    @GetMapping
    public Result findAll() {

        List<User> users = userService.findAll();
        List<UserRs> userRsList = users.stream().
                map(userToUserRsConverter::convert)
                .toList();

        return new Result(true,StatusCode.SUCCESS, "Found all", userRsList);
    }


    @PostMapping
    public Result createUser(@RequestBody @Valid UserRq rq){

        User createdUser = userService.create(userRqToUserConverter.convert(rq));

        return new Result(true,StatusCode.SUCCESS, "User created",
                userToUserRsConverter.convert(createdUser));

    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody @Valid UserRq rq) {

        User user = userService.update(id, userRqToUserConverter.convert(rq));

        return new Result(true,StatusCode.SUCCESS,"Update success",
                userToUserRsConverter.convert(user));
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return new Result(true,StatusCode.SUCCESS, "Delete success");
    }
}
