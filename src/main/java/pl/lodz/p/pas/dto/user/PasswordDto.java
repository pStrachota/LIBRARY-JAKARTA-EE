package pl.lodz.p.pas.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {

    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
