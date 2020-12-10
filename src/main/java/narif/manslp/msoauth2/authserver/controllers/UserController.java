package narif.manslp.msoauth2.authserver.controllers;

import narif.manslp.msoauth2.authserver.model.UserResource;
import narif.manslp.msoauth2.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public UserResource getForUserName(@PathVariable("username") String username){
        return userService.getUserResourceByUsername(username);
    }

    @PostMapping
    public UserResource addUser(@RequestBody UserResource userResource){
        return userService.saveUser(userResource);
    }

}
