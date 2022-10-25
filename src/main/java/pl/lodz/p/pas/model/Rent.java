package pl.lodz.p.pas.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.resource.RentableItem;
import pl.lodz.p.pas.model.user.Client;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = "rentId")
@AllArgsConstructor
public class Rent {

    private Long rentId;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private double rentCost;

    private Client client;

    private List<RentableItem> rentableItem;

}
