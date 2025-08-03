package com.fiap.tech_challenge.parte1.ms_users.application.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTypeControllerInputPortImplTest {

    @Mock
    private CreateUserTypeUseCase createUserTypeUseCase;

    @Mock
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @Mock
    private FindByIdUserTypeUseCase findByIdUserTypeUseCase;

    @Mock
    private FindListUserTypeUseCase findListUserTypeUseCase;

    @Mock
    private DeactivateUserTypeUseCase deactivateUserTypeUseCase;

    @Mock
    private ReactivateUserTypeUserCase reactivateUserTypeUserCase;

    @Mock
    private IUserTypeMapper userTypeMapper;

    @InjectMocks
    private UserTypeControllerInputPortImpl controller;

    private UserTypeRequestDTO requestDTO;
    private UserTypeResponseDTO responseDTO;
    private UserType userType;
    private Long userId = 1L;

    @BeforeEach
    void setUp() {
        requestDTO = new UserTypeRequestDTO("Admin", "Administrator");
        responseDTO = new UserTypeResponseDTO(1L, "Admin", "Administrator", true);
        userType = new UserType("Admin", "Administrator", true);
    }

    @Test
    void create_ShouldDelegateToUseCaseAndReturnResponse() {
        when(createUserTypeUseCase.execute(requestDTO)).thenReturn(responseDTO);

        UserTypeResponseDTO result = controller.create(requestDTO);

        assertEquals(responseDTO, result);
        verify(createUserTypeUseCase).execute(requestDTO);
    }

    @Test
    void update_ShouldMapAndDelegateToUseCase() {
        when(userTypeMapper.toUserType(requestDTO, userId)).thenReturn(userType);
        when(updateUserTypeUseCase.execute(userType)).thenReturn(responseDTO);

        UserTypeResponseDTO result = controller.update(requestDTO, userId);

        assertEquals(responseDTO, result);
        verify(userTypeMapper).toUserType(requestDTO, userId);
        verify(updateUserTypeUseCase).execute(userType);
    }

    @Test
    void toggleActivationUserType_ShouldDeactivateWhenFalse() {
        String expectedMessage = "User deactivated!";

        String result = controller.toggleActivationUserType(userId, false);

        assertEquals(expectedMessage, result);
        verify(deactivateUserTypeUseCase).execute(userId);
        verifyNoInteractions(reactivateUserTypeUserCase);
    }

    @Test
    void toggleActivationUserType_ShouldReactivateWhenTrue() {
        String expectedMessage = "User activated!";

        String result = controller.toggleActivationUserType(userId, true);

        assertEquals(expectedMessage, result);
        verify(reactivateUserTypeUserCase).execute(userId);
        verifyNoInteractions(deactivateUserTypeUseCase);
    }

    @Test
    void findById_ShouldDelegateToUseCaseAndMapResponse() {
        when(findByIdUserTypeUseCase.execute(userId)).thenReturn(userType);
        when(userTypeMapper.toUserTypeResponseDto(userType)).thenReturn(responseDTO);

        UserTypeResponseDTO result = controller.findById(userId);

        assertEquals(responseDTO, result);
        verify(findByIdUserTypeUseCase).execute(userId);
        verify(userTypeMapper).toUserTypeResponseDto(userType);
    }

    @Test
    void findAllUserType_ShouldDelegateToUseCaseAndMapResponse() {
        int size = 10;
        int page = 0;
        List<UserType> userTypes = List.of(userType);
        List<UserTypeResponseDTO> expectedResponse = List.of(responseDTO);

        when(findListUserTypeUseCase.execute(size, page)).thenReturn(userTypes);
        when(userTypeMapper.toListUserTypeResponseDto(userTypes)).thenReturn(expectedResponse);

        List<UserTypeResponseDTO> result = controller.findAllUserType(size, page);

        assertEquals(expectedResponse, result);
        verify(findListUserTypeUseCase).execute(size, page);
        verify(userTypeMapper).toListUserTypeResponseDto(userTypes);
    }
}