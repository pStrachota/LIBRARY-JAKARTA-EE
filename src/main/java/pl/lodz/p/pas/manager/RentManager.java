package pl.lodz.p.pas.manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.lodz.p.pas.dto.RentDto;
import pl.lodz.p.pas.exception.DeactivatedUserException;
import pl.lodz.p.pas.exception.EndedRentException;
import pl.lodz.p.pas.exception.ExceededLimitException;
import pl.lodz.p.pas.exception.ItemNotFoundException;
import pl.lodz.p.pas.exception.RentableItemNotAvailableException;
import pl.lodz.p.pas.exception.WrongClientTypeException;
import pl.lodz.p.pas.model.Rent;
import pl.lodz.p.pas.model.resource.RentableItem;
import pl.lodz.p.pas.model.user.Client;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.RentRepo;
import pl.lodz.p.pas.repository.RentableItemRepo;
import pl.lodz.p.pas.repository.UserRepo;

@Stateless
public class RentManager {

    @EJB
    RentRepo currentRentDbRepo;

    @EJB
    RentableItemRepo rentableItemDbRepo;

    @EJB
    UserRepo userDbRepo;

    public void addRent(RentDto rentDto) {

        User user = userDbRepo.findByID(rentDto.getClientId())
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
        Client client = null;

        if (!(user instanceof Client)) {
            throw new WrongClientTypeException("User is not a client");
        } else {
            client = (Client) user;
        }

        if (!client.isActive()) {
            throw new DeactivatedUserException("Client is deactivated");
        }

        List<RentableItem> rentableItems = new ArrayList<>();

        rentDto.getRentableItemIds().forEach(rentableItemId -> {
            RentableItem rentableItem = rentableItemDbRepo.findByID(rentableItemId)
                    .orElseThrow(() -> new ItemNotFoundException("RentableItem not found"));
            if (!rentableItem.isAvailable()) {
                throw new RentableItemNotAvailableException("RentableItem is rented");
            }
            rentableItem.setAvailable(false);
            rentableItems.add(rentableItem);
        });


        long clientId = client.getId();

        int clientRents = currentRentDbRepo.getItems().stream()
                .filter(r -> r.getClient().getId() == clientId)
                .mapToInt(r -> r.getRentableItems().size())
                .sum();

        Rent rent = new Rent();
        rent.setClient(client);
        rent.setEnded(false);
        rent.setRentableItems(rentableItems);
        rent.setBeginTime(LocalDateTime.now());
        rent.setEndTime(LocalDateTime.now().plusDays(client.getClientType().getMaxDays()));

        if (client.getClientType().getMaxItems() > clientRents) {
            currentRentDbRepo.add(rent);
        } else {
            throw new ExceededLimitException("Client has reached max items");
        }

    }


    public List<Rent> getRents() {
        return currentRentDbRepo.getItems();
    }

    public Rent getRent(long id) {
        return currentRentDbRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("Rent not found"));
    }

    public void removeRent(Long id) {

        Rent rent = currentRentDbRepo.findByID(id)
                .orElseThrow(() -> new ItemNotFoundException("Rent not found"));

        if (rent.isEnded()) {
            throw new EndedRentException("Rent is already ended");
        }

        Client client = rent.getClient();
        rent.setEndTime(LocalDateTime.now());
        rent.setEnded(true);

        rent.getRentableItems().forEach(rentableItem -> {
            rentableItem.setAvailable(true);
        });

        if (rent.getEndTime()
                .isAfter(rent.getBeginTime().plusDays(client.getClientType().getMaxDays()))) {

            int daysAfterEndTime = rent.getEndTime().getDayOfYear() -
                    rent.getBeginTime().plusDays(client.getClientType().getMaxDays())
                            .getDayOfYear();
            rent.setRentCost(client.getClientType().getPenalty() * daysAfterEndTime);
        }

        currentRentDbRepo.update(id, rent);
    }
}
