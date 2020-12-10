package narif.manslp.msoauth2.authserver.service;

import narif.manslp.msoauth2.authserver.entities.user.User;
import narif.manslp.msoauth2.authserver.model.UserResource;
import narif.manslp.msoauth2.authserver.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    public UserResource getUserResourceByUsername(String username) {
        final var one = userJpaRepository.findUserByUsername(username);
        return one.map(User::mapToUserResource).orElseThrow(()->new IllegalArgumentException("User details not found."));
    }

    public UserResource saveUser(UserResource userResource) {
        final var user = userJpaRepository.save(userResource.mapToUser());
        return user.mapToUserResource();
    }

    public List<UserResource> getUsers(){
        return userJpaRepository.findAll().stream().map(User::mapToUserResource).collect(Collectors.toList());
    }
}
