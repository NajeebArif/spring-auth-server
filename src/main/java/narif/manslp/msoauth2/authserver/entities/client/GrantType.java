package narif.manslp.msoauth2.authserver.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "grant_types")
public class GrantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JoinColumn(name = "client")
    @ManyToOne
//    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public GrantType(String name){
        this.name = name;
    }
}
