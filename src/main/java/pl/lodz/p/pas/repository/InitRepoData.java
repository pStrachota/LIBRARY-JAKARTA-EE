package pl.lodz.p.pas.repository;

import io.jsonwebtoken.lang.Collections;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import pl.lodz.p.pas.dto.RentDto;
import pl.lodz.p.pas.dto.resource.ArticleDto;
import pl.lodz.p.pas.dto.resource.BookDto;
import pl.lodz.p.pas.dto.user.AdminDto;
import pl.lodz.p.pas.dto.user.ClientDto;
import pl.lodz.p.pas.dto.user.ManagerDto;
import pl.lodz.p.pas.manager.RentManager;
import pl.lodz.p.pas.manager.RentableItemManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Address;
import pl.lodz.p.pas.model.user.ClientType;

@Startup
@Singleton
public class InitRepoData {

    @Inject
    RentableItemManager rentableItemManager;
    @Inject
    UserManager userManager;
    @Inject
    RentManager rentManager;

    @PostConstruct
    public void init() {
        rentableItemManager.addRentableItem(BookDto.builder().title("Ballads and romances")
                .author("Adam Mickiewicz").publishingHouse("PWN").serialNumber("1111111111111")
                .build());
        rentableItemManager.addRentableItem(BookDto.builder().title("Lord of the rings")
                .author("J.R.R. Tolkien").publishingHouse("Ontario").serialNumber("2222222222222")
                .build());
        rentableItemManager.addRentableItem(BookDto.builder().title("Harry Potter and the Order of the Phoenix")
                .author("J.K Rowling").publishingHouse("Media").serialNumber("3333333333333")
                .build());
        rentableItemManager.addRentableItem(BookDto.builder().title("Little women")
                .author("L.M. Alcott").publishingHouse("PWN").serialNumber("4444444444444")
                .build());
        rentableItemManager.addRentableItem(ArticleDto.builder().title("The Feynman lectures of Physics")
                .author("Richard Feynman").serialNumber("5555555555555")
                .parentOrganisation("Unknown").build());
        rentableItemManager.addRentableItem(ArticleDto.builder().title("Theory of general relativity")
                .author("Albert Einstein").serialNumber("6666666666666")
                .parentOrganisation("MIT Publishing").build());

        userManager.addUser(AdminDto.builder().name("John").surname("Doe").login("admin123")
                .password("admin123").privileges("ALL")
                .position("IT SPEC").build());
        userManager.addUser(
                ManagerDto.builder().name("Adam").surname("Driver").login("manager123")
                        .password("manager123")
                        .position("BOSS").build());
        userManager.addUser(
                ClientDto.builder().name("Jane").surname("White").login("client123")
                        .password("client123")
                        .address(new Address("Piotrkowska", "Łódź", "123"))
                        .clientType(ClientType.STUDENT).build());
        userManager.addUser(
                ClientDto.builder().name("Robert").surname("Brown").login("client321")
                        .password("client321")
                        .address(new Address("Złota", "Warszawa", "123"))
                        .clientType(ClientType.UNIVERSITY_EMPLOYEE).build());

        rentManager.addRent(RentDto.builder().clientId(3L)
                .rentableItemIds(Collections.arrayToList(new long[] {1L})).build());
        rentManager.addRent(RentDto.builder().clientId(4L)
                .rentableItemIds(Collections.arrayToList(new long[] {2L, 3L, 4L})).build());
    }
}
