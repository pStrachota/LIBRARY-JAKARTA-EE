package pl.lodz.p.pas.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.lodz.p.pas.model.resource.RentableItem;
import pl.lodz.p.pas.model.user.Client;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = "rentId")
@AllArgsConstructor
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentId;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private double rentCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.MERGE)
    private Client client;

    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    @Cascade(CascadeType.MERGE)
    @JoinColumn
    @BatchSize(size = 10)
    private List<RentableItem> rentableItem;

}
