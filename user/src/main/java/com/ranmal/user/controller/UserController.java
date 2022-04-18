package com.ranmal.user.controller;

import com.ranmal.user.dto.UserLoginRequestDTO;
import com.ranmal.user.dto.UserRequestDTO;
import com.ranmal.user.dto.UserResponseDTO;
import com.ranmal.user.exception.ApiExceptionResponse;
import com.ranmal.user.model.User;
import com.ranmal.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "api/v1/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create Users", description = "Create a User and return the created basic user details with ID", tags = "Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created"),
            @ApiResponse(responseCode = "409", description = "User Already Exists",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request for User Creation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User newUser = User.builder().
                firstName(userRequestDTO.getFirstName()).
                lastName(userRequestDTO.getLastName()).
                userName(userRequestDTO.getUserName()).
                email(userRequestDTO.getEmail()).
                address(userRequestDTO.getAddress()).
                hashCredential(userRequestDTO.getHashCredential()).
                build();
        return new ResponseEntity<>(this.userService.addNewUser(newUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Authenticate Users", description = "Authenticate the User and return basic user details with ID", tags = "Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Authenticated"),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User Not Authorized",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request for User Authentication",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiExceptionResponse.class))
                    }),
    })
    @PostMapping(path = "/login")
    public UserResponseDTO userLogin(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        return this.userService.authenticateUser(userLoginRequestDTO);
    }

}
