package pl.lodz.p.pas.dto.resource;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
