package narif.manslp.msoauth2.authserver.controllers;

import narif.manslp.msoauth2.authserver.entities.User;
import narif.manslp.msoauth2.authserver.model.UserResource;
import narif.manslp.msoauth2.authserver.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @GetMapping("{username}")
    public UserResource getForUserName(@PathVariable("username") String username){
        final var one = userJpaRepository.findUserByUsername(username);
        return one.map(User::mapToUserResource).orElseThrow(()->new IllegalArgumentException("User details not found."));
    }

    @PostMapping
    public UserResource addUser(@RequestBody UserResource userResource){
        final var user = userJpaRepository.save(userResource.mapToUser());
        return user.mapToUserResource();
    }

}
