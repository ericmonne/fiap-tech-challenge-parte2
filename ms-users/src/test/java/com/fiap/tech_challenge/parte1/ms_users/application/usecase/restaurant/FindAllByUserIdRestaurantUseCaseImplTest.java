package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllByUserIdRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private AddressGateway addressGateway;

    @Mock
    private OpeningHourGateway openingHourGateway;

    @Mock
    private IRestaurantMapper restaurantMapper;

    @InjectMocks
    private FindAllByUserIdRestaurantUseCaseImpl useCase;

    @Test
    void shouldReturnEmptyListWhenNoRestaurantsFound() {
        UUID userId = UUID.randomUUID();
        int size = 10;
        int offset = 0;

        when(restaurantGateway.findAllRestaurantsByUserId(userId, size, offset)).thenReturn(List.of());

        List<RestaurantResponseDTO> result = useCase.execute(userId, size, offset);

        assertThat(result).isEmpty();
        verify(restaurantGateway).findAllRestaurantsByUserId(userId, size, offset);
        verifyNoMoreInteractions(addressGateway, openingHourGateway, restaurantMapper);
    }

    @Test
    void shouldReturnRestaurantsWithAddressAndOpeningHours() {
        UUID userId = UUID.randomUUID();
        int size = 10;
        int offset = 0;
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        Address address = new Address();
        List<OpeningHour> openingHours = List.of(new OpeningHour());
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);

        when(restaurantGateway.findAllRestaurantsByUserId(userId, size, offset)).thenReturn(List.of(restaurant));
        when(addressGateway.findByRestaurantId(restaurantId)).thenReturn(Optional.of(address));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(openingHours);
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(responseDTO);

        List<RestaurantResponseDTO> result = useCase.execute(userId, size, offset);

        assertThat(result).hasSize(1).contains(responseDTO);
        assertThat(restaurant.getAddress()).isEqualTo(address);
        assertThat(restaurant.getOpeningHours()).isEqualTo(openingHours);

        verify(restaurantGateway).findAllRestaurantsByUserId(userId, size, offset);
        verify(addressGateway).findByRestaurantId(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(restaurant);
    }

    @Test
    void shouldHandleRestaurantWithoutAddress() {
        UUID userId = UUID.randomUUID();
        int size = 10;
        int offset = 0;
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);

        when(restaurantGateway.findAllRestaurantsByUserId(userId, size, offset)).thenReturn(List.of(restaurant));
        when(addressGateway.findByRestaurantId(restaurantId)).thenReturn(Optional.empty());
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of());
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(responseDTO);

        List<RestaurantResponseDTO> result = useCase.execute(userId, size, offset);

        assertThat(result).hasSize(1).contains(responseDTO);
        assertThat(restaurant.getAddress()).isNull();
        assertThat(restaurant.getOpeningHours()).isEmpty();
    }

    @Test
    void shouldHandleMultipleRestaurants() {
        UUID userId = UUID.randomUUID();
        int size = 10;
        int offset = 0;

        UUID restaurantId1 = UUID.randomUUID();
        UUID restaurantId2 = UUID.randomUUID();

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(restaurantId1);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(restaurantId2);

        Address address1 = new Address();
        Address address2 = new Address();
        List<OpeningHour> openingHours1 = List.of(new OpeningHour());
        List<OpeningHour> openingHours2 = List.of(new OpeningHour(), new OpeningHour());

        RestaurantResponseDTO responseDTO1 = mock(RestaurantResponseDTO.class);
        RestaurantResponseDTO responseDTO2 = mock(RestaurantResponseDTO.class);

        when(restaurantGateway.findAllRestaurantsByUserId(userId, size, offset))
                .thenReturn(List.of(restaurant1, restaurant2));

        when(addressGateway.findByRestaurantId(restaurantId1)).thenReturn(Optional.of(address1));
        when(addressGateway.findByRestaurantId(restaurantId2)).thenReturn(Optional.of(address2));

        when(openingHourGateway.findByRestaurantId(restaurantId1)).thenReturn(openingHours1);
        when(openingHourGateway.findByRestaurantId(restaurantId2)).thenReturn(openingHours2);

        when(restaurantMapper.toResponseDTO(restaurant1)).thenReturn(responseDTO1);
        when(restaurantMapper.toResponseDTO(restaurant2)).thenReturn(responseDTO2);

        List<RestaurantResponseDTO> result = useCase.execute(userId, size, offset);

        assertThat(result).hasSize(2).containsExactly(responseDTO1, responseDTO2);
        assertThat(restaurant1.getAddress()).isEqualTo(address1);
        assertThat(restaurant2.getAddress()).isEqualTo(address2);
        assertThat(restaurant1.getOpeningHours()).isEqualTo(openingHours1);
        assertThat(restaurant2.getOpeningHours()).isEqualTo(openingHours2);
    }
}