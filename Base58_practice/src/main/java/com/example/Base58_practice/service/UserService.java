package com.example.Base58_practice.service;

import com.example.Base58_practice.aspect.logging.annotation.VerboseLogging;
import com.example.Base58_practice.config.MessagingConfig;
import com.example.Base58_practice.dto.UpdateUserDto;
import com.example.Base58_practice.dto.UserDto;
import com.example.Base58_practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.Base58_practice.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @VerboseLogging
    public UserDto getUserById(final long id) throws RuntimeException {
        return mapEntityToUserDto(this.repository.findById(id).orElseThrow(() -> new RuntimeException("user not found")));
    }
    @VerboseLogging
    public Page<UserDto> getUserPage(final Pageable pageable) {
        return repository.findAll(pageable).map(this::mapEntityToUserDto);
    }

    @VerboseLogging
    public List<UserDto> getAllUsers() throws RuntimeException {

        List<UserDto> usersDto = new ArrayList<UserDto>();

        Pageable paging = PageRequest.of(0, 3, Sort.by("firstName"));


        Page<User> pagedResult = repository.findAll(paging);

        if (pagedResult.hasContent()) {

            pagedResult.getContent().forEach(user -> {
                usersDto.add(mapEntityToUserDto(user));

            });

            return usersDto;
        } else {

            return usersDto;
        }

    }





    @VerboseLogging
    public UserDto patchUser(final long id, final UpdateUserDto updateUserDto) {
        return mapEntityToUserDto(this.repository.save(mapUpdateUserDtoToEntity(this.repository.findById(id).orElseThrow(() -> new RuntimeException("user not found")), updateUserDto)));
    }
    @VerboseLogging
    public void deleteUser(final long id) throws RuntimeException {
        repository.delete(this.repository.findById(id).orElseThrow(() -> new RuntimeException("No user with such ID")));
    }

    @VerboseLogging
    public UserDto addUser(final UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());




        return mapEntityToUserDto(this.repository.save(user));


    }


    private User mapUpdateUserDtoToEntity(final User userEntity, final UpdateUserDto updateUserDto) throws RuntimeException {
        updateUserDto.getOptionalFirstName().ifPresent(userEntity::setFirstName);
        updateUserDto.getOptionalLastName().ifPresent(userEntity::setLastName);
        updateUserDto.getOptionalEmail().ifPresent(userEntity::setEmail);
        updateUserDto.getOptionalPhone().ifPresent(userEntity::setPhone);
        return userEntity;
    }


    private UserDto mapEntityToUserDto(final User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());

        return userDto;

    }

}
