package narif.manslp.msoauth2.authserver.entities.user;

import narif.manslp.msoauth2.authserver.entities.enums.HashingAlgo;
import narif.manslp.msoauth2.authserver.model.UserResource;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private HashingAlgo algorithm;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Authority> authorities = new ArrayList<>();

    public UserResource mapToUserResource(){
        final var userResource = new UserResource();
        userResource.setUsername(getUsername());
        userResource.setAuthorities(authorities.stream().map(Authority::getName).collect(Collectors.toList()));
        return userResource;
    }

    public void addAuthority(Authority authority){
        authorities.add(authority);
        authority.setUser(this);
    }

    public void removeAuthority(Authority authority){
        authorities.remove(authority);
        authority.setUser(null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public HashingAlgo getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(HashingAlgo algorithm) {
        this.algorithm = algorithm;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
