package pl.lodz.p.pas.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class ArticleDto extends RentableItemDto {

    @NotBlank
    private String parentOrganisation;

    @Builder
    public ArticleDto(String serialNumber, String author, String title,
                      String parentOrganisation) {
        super(serialNumber, author, title);
        this.parentOrganisation = parentOrganisation;
    }
}
