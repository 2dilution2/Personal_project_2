package com.spring.signalMate.users.controller;

import com.spring.signalMate.users.dto.UsersDto;
import com.spring.signalMate.users.entity.Users;
import com.spring.signalMate.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/register")
    public Users register(@RequestBody UsersDto usersDto) {
        return usersService.register(usersDto);
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable Long userId, @RequestBody UsersDto userDto) {
        usersService.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        usersService.delete(userId);
    }

}
