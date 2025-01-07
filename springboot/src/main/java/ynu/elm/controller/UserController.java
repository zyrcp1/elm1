package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.User;
import ynu.elm.service.UserService;

@RestController
@RequestMapping(value = "/UserController")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/getUserByIdByPass")
    public User getUserByIdByPass(@RequestParam(value = "userId") String userId,@RequestParam(value = "password") String password){
        return userService.getUserByIdByPass(userId,password);
    }

    @GetMapping(value = "/getUserById")
    public int getUserById(@RequestParam(value = "userId") String userId){
        return userService.getUserById(userId);
    }

    @PostMapping(value = "/saveUser")
    public int saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }
}
