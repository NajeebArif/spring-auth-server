package narif.manslp.msoauth2.authserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class ScrapBook {

    @Test
    @DisplayName("Small method to generate the hashed password to be fed to data.sql")
    public void testBc(){
        final var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final var encode = bCryptPasswordEncoder.encode("12345");
        //$2a$10$2cWWrDbVY4IFIB3YbovvtOaqVXWGKaGy8vZpZezyiow5MwgoNz5rS
        System.out.println(encode);
        final var match = bCryptPasswordEncoder
                .matches("12345", encode);
        assertThat(match).isTrue();
    }
}
