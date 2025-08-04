package com.fiap.tech_challenge.parte1.ms_users.application.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.user.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.EmailAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.PersistenceException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List; import java.util.UUID; 

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerInputPortImplTest {

    @Mock
    private RegisterUserUseCase registerUserUseCase;
    @Mock
    private UpdateUserUseCase updateUserUseCase;
    @Mock
    private FindListUserUseCase findListUserUseCase;
    @Mock
    private FindByIdUserUseCase findByIdUserUseCase;
    @Mock
    private DeactivateUserUseCase deactivateUserUseCase;
    @Mock
    private ReactivateUserUseCase reactivateUserUseCase;
    @Mock
    private ChangePasswordUserUseCase changePasswordUserUseCase;
    @Mock
    private AuthenticateUserUseCase authenticateUserUseCase;
    @Mock
    private IUserMapper iUserMapper;

    @InjectMocks
    private UsersControllerInputPortImpl usersController;

    @Test
    void getById_shouldReturnUserResponseDTO() {
        UUID id = UUID.randomUUID();
        User user = new User();
        List<AddressResponseDTO> addressResponseDTOList = List.of( new AddressResponseDTO(
                UUID.randomUUID(), "12345", "Street", 100, "Comp", "Neighborhood", "City", "State"
        ));
        UsersResponseDTO response = new UsersResponseDTO(id, "name", "email", "login", addressResponseDTOList);

        when(findByIdUserUseCase.execute(id)).thenReturn(user);
        when(iUserMapper.toResponseDTO(user)).thenReturn(response);

        UsersResponseDTO result = usersController.getById(id);

        assertThat(result).isEqualTo(response);
        verify(findByIdUserUseCase).execute(id);
        verify(iUserMapper).toResponseDTO(user);
    }

    @Test
    void create_shouldRegisterUserAndReturnCreateUserDTO() {
        List<AddressRequestDTO> addressRequestDTOList = List.of( new AddressRequestDTO("12345", "Street", 100, "Comp", "Neighborhood", "City", "State"));
        UsersRequestDTO dto = new UsersRequestDTO("name", "email", "login", "password", "OWNER", addressRequestDTOList);
        User user = new User();
        List<AddressResponseDTO> addressResponseDTOList = List.of( new AddressResponseDTO(
                UUID.randomUUID(), "12345", "Street", 100, "Comp", "Neighborhood", "City", "State"
        ));
        UsersResponseDTO response = new UsersResponseDTO(UUID.randomUUID(), "name", "email", "login", addressResponseDTOList);
        CreateUserDTO expected = new CreateUserDTO(response, "token");

        when(iUserMapper.toEntity(dto)).thenReturn(user);
        when(registerUserUseCase.execute(user)).thenReturn(expected);

        CreateUserDTO result = usersController.create(dto);

        assertThat(result).isEqualTo(expected);
        verify(iUserMapper).toEntity(dto);
        verify(registerUserUseCase).execute(user);
    }

    @Test
    void executeLogin_shouldReturnToken() {
        AuthenticationDataDTO authData = new AuthenticationDataDTO("email", "senha");
        TokenJWTInfoDTO token = new TokenJWTInfoDTO("jwt-token");

        when(authenticateUserUseCase.execute(authData)).thenReturn(token);

        TokenJWTInfoDTO result = usersController.executeLogin(authData);

        assertNotNull(result);
        assertThat(token).isEqualTo(result);
        verify(authenticateUserUseCase).execute(authData);
    }

    @Test
    void toggleActivation_shouldActivateUser() {
        UUID id = UUID.randomUUID();

        String result = usersController.toggleActivation(id, true);

        assertNotNull(result);
        assertNotEquals("User deactivated!", result);
        assertEquals("User activated!", result);
        verify(reactivateUserUseCase).execute(id);
    }

    @Test
    void toggleActivation_shouldDeactivateUser() {
        UUID id = UUID.randomUUID();

        String result = usersController.toggleActivation(id, false);

        assertNotNull(result);
        assertNotEquals("User activated!", result);
        assertEquals("User deactivated!", result);
        verify(deactivateUserUseCase).execute(id);
    }

    @Test
    void changePassword_shouldCallUseCaseAndReturnMessage() {
        UUID id = UUID.randomUUID();
        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO("old", "new");

        String result = usersController.changePassword(id, dto);

        assertEquals("Password updated successfully!", result);
        verify(changePasswordUserUseCase).execute(eq(id), any(ChangePasswordCommand.class));
    }

    @Test
    void updateUser_shouldReturnUpdatedUserDTO() {
        UUID id = UUID.randomUUID();
        UpdateUserDTO dto = new UpdateUserDTO("name", "email", "login", List.of(new AddressRequestDTO("12345", "Street", 100, "Comp", "Neighborhood", "City", "State")));
        User user = new User();
        User updatedUser = new User();
        UsersResponseDTO expected = new UsersResponseDTO(id, "name", "email", "login", List.of(new AddressResponseDTO(UUID.randomUUID(), "12345", "Street", 100, "Comp", "Neighborhood", "City", "State")));

        when(iUserMapper.toEntity(dto, id)).thenReturn(user);
        when(updateUserUseCase.execute(user)).thenReturn(updatedUser);
        when(iUserMapper.toResponseDTO(updatedUser)).thenReturn(expected);

        UsersResponseDTO result = usersController.updateUser(id, dto);

        assertNotNull(result);
        assertThat(expected).isEqualTo(result);
        verify(iUserMapper).toEntity(dto, id);
        verify(updateUserUseCase).execute(user);
        verify(iUserMapper).toResponseDTO(updatedUser);
    }

    @Test
    void findAllUsers_shouldReturnUserList() {
        List<User> users = List.of(new User(), new User());
        List<UsersResponseDTO> dtos = List.of(
                new UsersResponseDTO(UUID.randomUUID(), "name", "email", "login", List.of(new AddressResponseDTO(UUID.randomUUID(), "12345", "Street", 100, "Comp", "Neighborhood", "City", "State"))),
                new UsersResponseDTO(UUID.randomUUID(), "name2", "email2", "login2", List.of(new AddressResponseDTO(UUID.randomUUID(), "123456", "Street2", 101, "Comp2", "Neighborhood2", "City2", "State2")))
        );

        when(findListUserUseCase.execute(10, 0)).thenReturn(users);
        when(iUserMapper.toResponseDTO(users)).thenReturn(dtos);

        List<UsersResponseDTO> result = usersController.findAllUsers(10, 0);

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .isEqualTo(dtos);
        assertThat(dtos)
                .isNotEmpty()
                .hasSize(2);
        assertThat(result)
                .contains(dtos.get(0))
                .contains(dtos.get(1))
                .isEqualTo(dtos);
    }

    @Test
    void testCreateUserThrowsUserAlreadyExistsException() {
        User domain = new User("name", "email", "login", "password", new UserType("OWNER", "restaurant owner", true));
        UsersRequestDTO usersRequestDTO = new UsersRequestDTO("name", "email", "login", "password", "OWNER", List.of(new AddressRequestDTO("12345", "Street", 100, "Comp", "Neighborhood", "City", "State")));

        when(iUserMapper.toEntity(usersRequestDTO)).thenReturn(domain);
        doThrow(new EmailAlreadyExistsException("email already exists")).when(registerUserUseCase).execute(domain);

        assertThrows(EmailAlreadyExistsException.class, () -> usersController.create(usersRequestDTO));

        verify(registerUserUseCase).execute(domain);
    }

    @Test
    void testGetUserByIdThrowsUserNotFoundException() {
        UUID userId = UUID.randomUUID();


        when(findByIdUserUseCase.execute(userId)).thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> usersController.getById(userId));

        verify(findByIdUserUseCase).execute(userId);
    }

    @Test
    void testCreateUserThrowsUserPersistenceException() {

        User domain = new User("name", "email", "login", "password", new UserType("OWNER", "restaurant owner", true));
        UsersRequestDTO usersRequestDTO = new UsersRequestDTO("name", "email", "login", "password", "OWNER", List.of(new AddressRequestDTO("12345", "Street", 100, "Comp", "Neighborhood", "City", "State")));

        when(iUserMapper.toEntity(usersRequestDTO)).thenReturn(domain);
        doThrow(new PersistenceException("Erro ao salvar no banco")).when(registerUserUseCase).execute(domain);

        assertThrows(PersistenceException.class, () -> usersController.create(usersRequestDTO));

        verify(registerUserUseCase).execute(domain);
    }


    @Test
    void testCreateUserWithInvalidDataThrowsIllegalArgumentException() {
        UsersRequestDTO dto = new UsersRequestDTO("name", "email", "login", "password", "OWNER", List.of(new AddressRequestDTO("12345", "Street", 100, "Comp", "Neighborhood", "City", "State")));

        when(iUserMapper.toEntity(dto)).thenThrow(new IllegalArgumentException("Email inválido"));

        assertThrows(IllegalArgumentException.class, () -> usersController.create(dto));

        verify(iUserMapper).toEntity(dto);
    }


} 

 