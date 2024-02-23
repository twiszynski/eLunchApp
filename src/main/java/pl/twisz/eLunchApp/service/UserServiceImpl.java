package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.UserDTO;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.User;
import pl.twisz.eLunchApp.model.UserBuilder;
import pl.twisz.eLunchApp.repo.RestaurantRepo;
import pl.twisz.eLunchApp.repo.UserRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.twisz.eLunchApp.utils.ConverterUtils.convert;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, UserDTO userDTO) {
        if (!Objects.equal(userDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByUuid(userDTO.getUuid())
                .orElseGet(() -> newUser(uuid));

        user.setPersonalData(convert(userDTO.getPersonalDataDTO()));
        user.setLoginData(convert(userDTO.getLoginDataDTO()));
        user.setArchive(userDTO.getArchive());

        if (user.getId() == null) {
            userRepo.save(user);
        }
    }

    private User newUser(UUID uuid) {
        return new UserBuilder()
                .withUuid(uuid)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        User user = userRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userRepo.delete(user);
    }

    @Override
    public Optional<UserDTO> getByUuid(UUID uuid) {
        return userRepo.findByUuid(uuid).map(ConverterUtils::convert);    }

    @Override
    public void validateNewOperation(UUID uuid, UserDTO userDTO) {
        if (!Objects.equal(userDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        userRepo.findByUuid(userDTO.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
