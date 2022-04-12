package com.ranmal.user.service;

import com.ranmal.user.dto.UserLoginRequestDTO;
import com.ranmal.user.dto.UserResponseDTO;
import com.ranmal.user.exception.InvalidCredentialsException;
import com.ranmal.user.exception.NotFoundException;
import com.ranmal.user.model.User;
import com.ranmal.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO addNewUser(User user) {
        User newUser = this.userRepository.saveAndFlush(user);
        return UserResponseDTO.builder().
                userId(newUser.getUserId()).
                userName(newUser.getUserName()).
                firstName(newUser.getFirstName()).
                lastName(newUser.getLastName()).
                build();
    }

    public UserResponseDTO authenticateUser(UserLoginRequestDTO userLoginRequestDTO) {
        User existingUser = this.userRepository.findUserByEmail(userLoginRequestDTO.getEmail());
        if (existingUser == null) {
            throw new NotFoundException("No User Found for Email : " + userLoginRequestDTO.getEmail());
        } else {
            if (userLoginRequestDTO.getHashCredential().equals(existingUser.getHashCredential())) {
                return UserResponseDTO.builder().
                        userId(existingUser.getUserId()).
                        userName(existingUser.getUserName()).
                        firstName(existingUser.getFirstName()).
                        lastName(existingUser.getLastName()).
                        build();
            } else {
                throw new InvalidCredentialsException("Credentials are Not Valid");
            }
        }
    }
}
