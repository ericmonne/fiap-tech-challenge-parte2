package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.RestaurantRoutes;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller.RestaurantControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantsControllerTest {

    @Mock
    private RestaurantControllerInputPort restaurantControllerInputPort;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RestaurantsController restaurantsController;

    private final UUID testUserId = UUID.randomUUID();
    private final UUID testRestaurantId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        // Config mínima por erro de unecessary stubbing
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getRestaurantById_ShouldReturnOkWithRestaurant() {
        RestaurantResponseDTO expectedResponse = mock(RestaurantResponseDTO.class);
        when(restaurantControllerInputPort.getRestaurantById(testRestaurantId))
                .thenReturn(expectedResponse);

        ResponseEntity<RestaurantResponseDTO> response =
                restaurantsController.getRestaurantById(testRestaurantId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(restaurantControllerInputPort).getRestaurantById(testRestaurantId);
    }

    @Test
    void findAllRestaurantsByUser_ShouldReturnPaginatedList() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(restaurantControllerInputPort.getUserIdByLogin(any())).thenReturn(testUserId);

        int size = 10;
        int page = 1;
        List<RestaurantResponseDTO> expectedList = List.of(mock(RestaurantResponseDTO.class));
        when(restaurantControllerInputPort.findAllRestaurantsByUser(any(), anyInt(), anyInt()))
                .thenReturn(expectedList);

        ResponseEntity<List<RestaurantResponseDTO>> response =
                restaurantsController.findAllRestaurantsByUser(size, page);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedList);
        verify(restaurantControllerInputPort)
                .findAllRestaurantsByUser(testUserId, size, 0);
    }

    @Test
    void createRestaurant_ShouldReturnCreatedWithLocation() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(restaurantControllerInputPort.getUserIdByLogin(any())).thenReturn(testUserId);

        RestaurantRequestDTO request = mock(RestaurantRequestDTO.class);

        AddressResponseDTO mockAddress = new AddressResponseDTO(
                UUID.randomUUID(),
                "01230-456",
                "Rua Teste",
                123,
                "ap 41",
                "Flores",
                "São Paulo",
                "SP"
        );

        List<OpeningHourResponseDTO> mockOpeningHours = List.of(
                new OpeningHourResponseDTO(
                        UUID.randomUUID(),
                        WeekDay.SEGUNDA,
                        LocalTime.of(10, 0),
                        LocalTime.of(22, 0)
                )
        );

        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                testRestaurantId,
                "Test Restaurant",
                mockAddress,
                CuisineType.ARABE,
                mockOpeningHours
        );

        when(restaurantControllerInputPort.createRestaurant(any(), any()))
                .thenReturn(expectedResponse);

        ResponseEntity<RestaurantResponseDTO> response =
                restaurantsController.createRestaurant(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        RestaurantResponseDTO responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.id()).isEqualTo(testRestaurantId);
        assertThat(responseBody.name()).isEqualTo("Test Restaurant");
        assertThat(responseBody.cuisineType()).isEqualTo(CuisineType.ARABE);
        assertThat(responseBody.address()).isEqualTo(mockAddress);
        assertThat(responseBody.openingHours()).hasSize(1);

        assertThat(response.getHeaders().getLocation())
                .isEqualTo(URI.create(RestaurantRoutes.RESTAURANTS_BASE + "/" + testRestaurantId));

        verify(restaurantControllerInputPort)
                .createRestaurant(request, testUserId);
    }

    @Test
    void updateRestaurant_ShouldReturnOkWithUpdatedRestaurant() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(restaurantControllerInputPort.getUserIdByLogin(any())).thenReturn(testUserId);

        RestaurantRequestDTO request = mock(RestaurantRequestDTO.class);
        RestaurantResponseDTO expectedResponse = mock(RestaurantResponseDTO.class);
        when(restaurantControllerInputPort.updateRestaurant(any(), any(), any()))
                .thenReturn(expectedResponse);

        ResponseEntity<RestaurantResponseDTO> response =
                restaurantsController.updateRestaurant(testRestaurantId, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(restaurantControllerInputPort)
                .updateRestaurant(testRestaurantId, request, testUserId);
    }

    @Test
    void deleteRestaurant_ShouldReturnNoContent() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(restaurantControllerInputPort.getUserIdByLogin(any())).thenReturn(testUserId);

        doNothing().when(restaurantControllerInputPort)
                .deleteRestaurant(any(), any());

        ResponseEntity<Void> response =
                restaurantsController.deleteRestaurant(testRestaurantId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(restaurantControllerInputPort)
                .deleteRestaurant(testRestaurantId, testUserId);
    }
}