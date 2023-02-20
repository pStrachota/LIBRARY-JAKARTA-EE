package pl.lodz.p.pas.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.lodz.p.pas.model.resource.RentableItem;
import pl.lodz.p.pas.model.user.Client;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rent extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;

    @NotNull
    private LocalDateTime beginTime;

    @Future
    private LocalDateTime endTime;

    @NotNull
    private double rentCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.MERGE)
    private Client client;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean isEnded;

    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    @Cascade(CascadeType.MERGE)
    @JoinColumn
    @BatchSize(size = 10)
    private List<RentableItem> rentableItems;

}
