package pl.lodz.p.pas.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String login;
}
