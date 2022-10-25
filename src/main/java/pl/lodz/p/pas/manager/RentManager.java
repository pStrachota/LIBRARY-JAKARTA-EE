package pl.lodz.p.pas.manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import pl.lodz.p.pas.dto.RentDto;
import pl.lodz.p.pas.model.Rent;
import pl.lodz.p.pas.model.resource.RentableItem;
import pl.lodz.p.pas.model.user.Client;
import pl.lodz.p.pas.repository.RentRepo;
import pl.lodz.p.pas.repository.RentableItemRepo;
import pl.lodz.p.pas.repository.UserRepo;

public class RentManager {

    @Inject
    RentRepo currentRentRepo;

    @Inject
    RentRepo archiveRentRepo;

    @Inject
    RentableItemRepo rentableItemRepo;

    @Inject
    UserRepo userRepo;

    public void addRent(RentDto rentDto) {

        Client client = (Client) userRepo.findByID(rentDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.isActive()) {
            throw new RuntimeException("Client is deactivated");
        }

        List<RentableItem> rentableItems = new ArrayList<>();

        rentDto.getRentableItemIds().forEach(rentableItemId -> {
            RentableItem rentableItem = rentableItemRepo.findByID(rentableItemId)
                    .orElseThrow(() -> new RuntimeException("RentableItem not found"));
            rentableItems.add(rentableItem);
        });

        long clientId = client.getId();

        int clientRents = currentRentRepo.getItems().stream()
                .filter(r -> r.getClient().getId() == clientId)
                .mapToInt(r -> r.getRentableItem().size())
                .sum();

        Rent rent = new Rent();
        rent.setClient(client);
        rent.setRentableItem(rentableItems);
        rent.setBeginTime(LocalDateTime.now());

        if (client.getClientType().getMaxItems() > clientRents) {
            currentRentRepo.add(rent);
        } else {
            throw new RuntimeException("Client has reached max items");
        }

    }

    public List<Rent> getRents() {
        return currentRentRepo.getItems();
    }

    public Rent getRent(long id) {
        return currentRentRepo.findByID(id)
                .orElseThrow(() -> new RuntimeException("Rent not found"));
    }

    public void removeRent(Long id) {

        Rent rent = currentRentRepo.findByID(id)
                .orElseThrow(() -> new RuntimeException("Rent not found"));

        Client client = rent.getClient();
        rent.setEndTime(LocalDateTime.now());

        if (rent.getEndTime()
                .isAfter(rent.getBeginTime().plusDays(client.getClientType().getMaxDays()))) {

            int daysAfterEndTime = rent.getEndTime().getDayOfYear() -
                    rent.getBeginTime().plusDays(client.getClientType().getMaxDays())
                            .getDayOfYear();
            rent.setRentCost(client.getClientType().getPenalty() * daysAfterEndTime);
        }

        currentRentRepo.remove(rent);
        archiveRentRepo.add(rent);
    }

    public void updateRent(Long id, RentDto rentDto) {

        Rent rent = currentRentRepo.findByID(id)
                .orElseThrow(() -> new RuntimeException("Rent not found"));

        Client client = (Client) userRepo.findByID(rentDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.isActive()) {
            throw new RuntimeException("Client is not active");
        }

        List<Long> rentableItemIds = rentDto.getRentableItemIds();
        List<RentableItem> rentableItems = new ArrayList<>();

        rentableItemIds.forEach(rentableItemId -> {
            RentableItem rentableItem = rentableItemRepo.findByID(rentableItemId)
                    .orElseThrow(() -> new RuntimeException("RentableItem not found"));
            rentableItems.add(rentableItem);
        });

        Rent updatedRent = new Rent();
        updatedRent.setClient(client);
        updatedRent.setRentableItem(rentableItems);
        updatedRent.setBeginTime(LocalDateTime.now());

        int index = currentRentRepo.getItems().indexOf(rent);
        currentRentRepo.getItems().set(index, updatedRent);
    }
}
