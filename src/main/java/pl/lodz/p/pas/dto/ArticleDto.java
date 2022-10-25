package pl.lodz.p.pas.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ArticleDto extends RentableItemDto {

    private String parentOrganisation;

}
