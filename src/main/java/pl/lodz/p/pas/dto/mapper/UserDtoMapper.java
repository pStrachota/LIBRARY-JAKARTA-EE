package pl.lodz.p.pas.dto.mapper;

import pl.lodz.p.pas.dto.AdminDto;
import pl.lodz.p.pas.dto.ClientDto;
import pl.lodz.p.pas.dto.ManagerDto;
import pl.lodz.p.pas.dto.UserDto;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Client;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.model.user.User;

public class UserDtoMapper {

    public static User mapToUser(UserDto userDto) {

        if (userDto instanceof ClientDto) {
            ClientDto clientDto = (ClientDto) userDto;
            return Client.builder()
                    .name(clientDto.getName())
                    .surname(clientDto.getSurname())
                    .isActive(true)
                    .login(clientDto.getLogin())
                    .address(clientDto.getAddress())
                    .clientType(clientDto.getClientType())
                    .build();
        } else if (userDto instanceof AdminDto) {
            AdminDto adminDto = (AdminDto) userDto;
            return Admin.builder()
                    .name(adminDto.getName())
                    .surname(adminDto.getSurname())
                    .isActive(true)
                    .login(adminDto.getLogin())
                    .privileges(adminDto.getPrivileges())
                    .position("ADMIN | " + adminDto.getPosition())
                    .build();
        } else {
            ManagerDto managerDto = (ManagerDto) userDto;
            return Manager.builder()
                    .name(managerDto.getName())
                    .surname(managerDto.getSurname())
                    .isActive(true)
                    .login(managerDto.getLogin())
                    .position("MANAGER | " + managerDto.getPosition())
                    .build();
        }

    }

}
