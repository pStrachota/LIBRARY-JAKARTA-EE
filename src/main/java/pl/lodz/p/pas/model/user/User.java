package pl.lodz.p.pas.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean isActive = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Version
    private long version;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Column(unique = true)
    private String login;

    public User(boolean isActive, String name, String surname, String login) {
        this.isActive = isActive;
        this.name = name;
        this.surname = surname;
        this.login = login;
    }

}
