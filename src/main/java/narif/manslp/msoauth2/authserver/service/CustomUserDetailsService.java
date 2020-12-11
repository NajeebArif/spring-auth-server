package narif.manslp.msoauth2.authserver.service;

import lombok.extern.slf4j.Slf4j;
import narif.manslp.msoauth2.authserver.entities.user.Authority;
import narif.manslp.msoauth2.authserver.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository repository;

    public CustomUserDetailsService(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("==============");
        try {
            final var user = repository.findUserByUsername(s).get();
            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.joining()))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("BAD");
        }
    }
}
