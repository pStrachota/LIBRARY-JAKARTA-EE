package pl.lodz.p.pas.dto.mapper;

import pl.lodz.p.pas.dto.ClientDto;
import pl.lodz.p.pas.dto.UserDto;
import pl.lodz.p.pas.model.user.Client;
import pl.lodz.p.pas.model.user.User;

public class UserDtoMapper {

    public static User mapToUser(UserDto userDto) {

        if (userDto instanceof ClientDto) {
            ClientDto clientDto = (ClientDto) userDto;
            return Client.builder()
                    .name(clientDto.getName())
                    .surname(clientDto.getSurname())
                    .password(clientDto.getPassword())
                    .address(clientDto.getAddress())
                    .clientType(clientDto.getClientType())
                    .build();
        } else {
            return User.builder()
                    .name(userDto.getName())
                    .surname(userDto.getSurname())
                    .password(userDto.getPassword())
                    .build();
        }

    }

}
