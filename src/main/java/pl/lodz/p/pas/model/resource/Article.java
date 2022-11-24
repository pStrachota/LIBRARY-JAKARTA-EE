package pl.lodz.p.pas.model.resource;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Article extends RentableItem {

    @NotEmpty
    private String parentOrganisation;

    @Builder
    public Article(long id, long version, boolean isAvailable, String serialNumber,
                   String author,
                   String title,
                   String parentOrganisation) {
        super(id, version, isAvailable, serialNumber, author, title);
        this.parentOrganisation = parentOrganisation;
    }

}
