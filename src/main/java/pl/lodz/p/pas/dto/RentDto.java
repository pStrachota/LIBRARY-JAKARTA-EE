package pl.lodz.p.pas.dto;


import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentDto {

    @NotNull
    private Long clientId;

    @NotNull
    private List<Long> rentableItemIds;

}
