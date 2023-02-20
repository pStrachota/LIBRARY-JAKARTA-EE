package pl.lodz.p.pas.dto.mapper;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import pl.lodz.p.pas.dto.user.AdminDto;
import pl.lodz.p.pas.dto.user.ClientDto;
import pl.lodz.p.pas.dto.user.ManagerDto;
import pl.lodz.p.pas.dto.user.UserDto;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Client;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.model.user.User;

@Singleton
@Startup
public class UserDtoMapper {

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public User mapToUser(UserDto userDto) {
        String password = passwordHash(userDto.getPassword());

        if (userDto instanceof ClientDto) {
            ClientDto clientDto = (ClientDto) userDto;
            return Client.builder()
                    .name(clientDto.getName())
                    .surname(clientDto.getSurname())
                    .isActive(true)
                    .password(password)
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

    public String passwordHash(String password) {
        return passwordHash.generate(password.toCharArray());
    }

    @PostConstruct
    void init() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "64000");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
    }

    public boolean comparePasswords(String password, String hashedPassword) {
        return passwordHash.verify(password.toCharArray(), hashedPassword);
    }

}
