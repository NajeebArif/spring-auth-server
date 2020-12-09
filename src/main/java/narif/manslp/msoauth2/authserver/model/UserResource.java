package narif.manslp.msoauth2.authserver.model;

import narif.manslp.msoauth2.authserver.entities.Authority;
import narif.manslp.msoauth2.authserver.entities.User;
import narif.manslp.msoauth2.authserver.entities.enums.HashingAlgo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
