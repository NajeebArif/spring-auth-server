package narif.manslp.msoauth2.authserver.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "redirect_url")
public class RedirectUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;

    @JoinColumn(name = "client")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public RedirectUrl(String url) {
        this.url = url;
    }
}
