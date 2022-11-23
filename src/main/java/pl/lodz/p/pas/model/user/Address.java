package pl.lodz.p.pas.model.user;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String number;
}
