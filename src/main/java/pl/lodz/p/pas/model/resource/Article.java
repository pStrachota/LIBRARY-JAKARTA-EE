package pl.lodz.p.pas.model.resource;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article extends RentableItem {

    @NotEmpty
    private String parentOrganisation;

    @Builder
    public Article(Long rentableItemId, long version, boolean isAvailable, String serialNumber,
                   String author,
                   String title,
                   String parentOrganisation) {
        super(rentableItemId, isAvailable, serialNumber, author, title);
        this.parentOrganisation = parentOrganisation;
    }

}
