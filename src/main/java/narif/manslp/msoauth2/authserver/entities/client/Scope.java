package narif.manslp.msoauth2.authserver.entities.client;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Scope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JoinColumn(name = "client")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public Scope(String name){
        this.name = name;
    }
}
