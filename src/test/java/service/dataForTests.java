package service;

import java.util.Arrays;
import pl.lodz.p.pas.dto.user.AdminDto;
import pl.lodz.p.pas.dto.resource.BookDto;
import pl.lodz.p.pas.dto.user.ClientDto;
import pl.lodz.p.pas.dto.RentDto;
import pl.lodz.p.pas.dto.user.UserDto;
import pl.lodz.p.pas.model.user.Address;
import pl.lodz.p.pas.model.user.ClientType;

public class dataForTests {

    public static final String url = "http://localhost:8080/pas/api/";

    public static ClientDto clientDto = ClientDto.builder()
            .name("Adam")
            .surname("Kowalski")
            .login("login1")
            .clientType(ClientType.STUDENT)
            .address(Address.builder()
                    .number("1")
                    .street("Street")
                    .city("City")
                    .build())
            .build();

    public static ClientDto incorrectClient = ClientDto.builder()
            .surname("Kowalski")
            .login("login1")
            .clientType(ClientType.STUDENT)
            .address(Address.builder()
                    .number("1")
                    .street("Street")
                    .city("City")
                    .build())
            .build();

    public static ClientDto updatedClientDto = ClientDto.builder()
            .name("changed")
            .surname("Kowalski")
            .login("adam")
            .clientType(ClientType.STUDENT)
            .address(Address.builder()
                    .number("1")
                    .street("Street")
                    .city("City")
                    .build())
            .build();

    public static UserDto adminDto = AdminDto.builder()
            .name("Jan")
            .surname("Kowalski")
            .login("admin")
            .privileges("database")
            .position("admin")
            .build();


    public static BookDto bookDto = BookDto.builder()
            .title("Balladyna")
            .author("Adam Mickiewicz")
            .publishingHouse("PWN")
            .serialNumber("123456789")
            .build();

    public static BookDto incorrectBook = BookDto.builder()
            .author("Adam Mickiewicz")
            .publishingHouse("PWN")
            .serialNumber("123456789")
            .build();

    public static BookDto updatedBookDto = BookDto.builder()
            .title("Balladyna")
            .author("Adam Mickiewicz")
            .publishingHouse("PWN_updated")
            .serialNumber("123456789")
            .build();

    public static RentDto rentDto = RentDto.builder()
            .clientId(3L)
            .rentableItemIds(Arrays.asList(3L))
            .build();

    public static RentDto incorrectUserRentDto = RentDto.builder()
            .clientId(4L)
            .rentableItemIds(Arrays.asList(3L))
            .build();
}
