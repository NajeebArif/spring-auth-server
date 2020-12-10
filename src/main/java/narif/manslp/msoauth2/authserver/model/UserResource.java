package narif.manslp.msoauth2.authserver.model;

import lombok.Getter;
import lombok.Setter;
import narif.manslp.msoauth2.authserver.entities.user.Authority;
import narif.manslp.msoauth2.authserver.entities.user.User;
import narif.manslp.msoauth2.authserver.entities.enums.HashingAlgo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Getter
@Setter
public class UserResource {

    private String username;
    private String password;
    private List<String> authorities;

    public User mapToUser(){
        User user = new User();
        user.setAlgorithm(HashingAlgo.BCRYPT);
        user.setUsername(getUsername());
        final var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(getPassword()));
        getAuthorities().forEach(a->user.addAuthority(new Authority(a)));
        return user;
    }

}
