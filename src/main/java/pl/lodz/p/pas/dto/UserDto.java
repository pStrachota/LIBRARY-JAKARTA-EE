package pl.lodz.p.pas.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
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
