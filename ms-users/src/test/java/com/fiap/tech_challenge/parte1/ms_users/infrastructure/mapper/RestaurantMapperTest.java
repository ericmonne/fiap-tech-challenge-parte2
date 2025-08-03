package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.address.IAddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.openinghour.IOpeningHourMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.*;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantMapperTest {

    @Mock
    private IAddressMapper addressMapper;

    @Mock
    private IOpeningHourMapper openingHourMapper;

    @InjectMocks
    private RestaurantMapper restaurantMapper;

    @Test
    void shouldMapToResponseDTO() {
        UUID addressId = UUID.randomUUID();

        Address address = createAddress(addressId);
        List<OpeningHour> openingHourList = createOpeningoHourList();
        Restaurant restaurant = createRestaurant(address, openingHourList);

        AddressResponseDTO addressDTO = getAddressResponseDTO(addressId, address);

        List<OpeningHourResponseDTO> openingHourDTOList = getOpeningHourResponseDTOS(openingHourList);

        when(addressMapper.toAddressResponseDTO(address)).thenReturn(addressDTO);
        when(openingHourMapper.toOpeningHourResponseDTO(openingHourList))
                .thenReturn(openingHourDTOList);

        RestaurantResponseDTO dto = restaurantMapper.toResponseDTO(restaurant);

        assertEquals("Test Restaurant", dto.name());
        assertEquals(CuisineType.ITALIANA, dto.cuisineType());
        assertEquals(addressId, dto.address().id());
        assertEquals("01000-000", dto.address().zipcode());
    }

    @Test
    void shouldMapListToResponseDTOList() {
        Address address = createAddress(UUID.randomUUID());
        List<OpeningHour> openingHourList = createOpeningoHourList();
        Restaurant restaurant = createRestaurant(address, openingHourList);
        List<RestaurantResponseDTO> result = restaurantMapper.toResponseDTO(List.of(restaurant));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo(restaurant.getName());
    }

    @Test
    void shouldMapFromRequestToEntity(){
        UUID addressId = UUID.randomUUID();
        Address address = createAddress(addressId);
        List<OpeningHour> openingHourList = createOpeningoHourList();
        Restaurant restaurant = createRestaurant(address, openingHourList);

        var addressRequestDTO = getAddressRequestDTO(address);
        var openingHourRequestDTOList = getOpeningHourRequestDTOS(openingHourList);

        RestaurantRequestDTO requestDTO = new RestaurantRequestDTO(
                restaurant.getName(),
                addressRequestDTO,
                openingHourRequestDTOList,
                restaurant.getCuisineType()
        );

        User user = new User();
        user.setId(UUID.randomUUID());

        when(addressMapper.toEntity(addressRequestDTO)).thenReturn(address);
        when(openingHourMapper.toEntity(openingHourRequestDTOList)).thenReturn(openingHourList);

        Restaurant entity = restaurantMapper.toEntity(requestDTO, user);

        assertEquals(requestDTO.name(), entity.getName());
        assertEquals(requestDTO.cuisineType(), entity.getCuisineType());
        assertEquals(address, entity.getAddress());
        assertEquals(openingHourList, entity.getOpeningHours());
        assertEquals(user, entity.getUser());
    }

    @Test
    void shouldMapRestaurantToJdbcEntity() {
        Address address = createAddress(UUID.randomUUID());
        List<OpeningHour> openingHourList = createOpeningoHourList();
        Restaurant restaurant = createRestaurant(address, openingHourList);

        User user = new User();
        user.setId(UUID.randomUUID());
        restaurant.setUser(user);

        JdbcRestaurantEntity entity = restaurantMapper.toJdbcRestaurantEntity(restaurant);

        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo(restaurant.getName());
        assertThat(entity.getId()).isEqualTo(restaurant.getId());
        assertThat(entity.getUserId()).isEqualTo(user.getId());
    }


    private static Restaurant createRestaurant(Address address, List<OpeningHour> openingHourList) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Test Restaurant");
        restaurant.setCuisineType(CuisineType.ITALIANA);
        restaurant.setAddress(address);
        restaurant.setOpeningHours(openingHourList);
        return restaurant;
    }

    private static Address createAddress(UUID addressId) {
        Address address = new Address();
        address.setId(addressId);
        address.setZipcode("01000-000");
        address.setStreet("Rua Teste");
        address.setNumber(123);
        address.setComplement("ap 12");
        address.setNeighborhood("Centro");
        address.setCity("São Paulo");
        address.setState("SP");
        return address;
    }

    private static List<OpeningHour> createOpeningoHourList(){
        List<OpeningHour> openingHourList = new ArrayList<>();

        OpeningHour openingHour1 = new OpeningHour();
        openingHour1.setId(UUID.randomUUID());
        openingHour1.setWeekDay(WeekDay.SABADO);
        openingHour1.setOpeningTime(LocalTime.parse("13:00:00"));
        openingHour1.setClosingTime(LocalTime.parse("22:00:00"));

        OpeningHour openingHour2 = new OpeningHour();
        openingHour2.setId(UUID.randomUUID());
        openingHour2.setWeekDay(WeekDay.DOMINGO);
        openingHour2.setOpeningTime(LocalTime.parse("13:00:00"));
        openingHour2.setClosingTime(LocalTime.parse("22:00:00"));

        openingHourList.add(openingHour1);
        openingHourList.add(openingHour2);

        return openingHourList;
    }

    private static List<OpeningHourResponseDTO> getOpeningHourResponseDTOS(List<OpeningHour> openingHourList) {
        List<OpeningHourResponseDTO> openingHourDTOList = List.of(
                new OpeningHourResponseDTO(
                        openingHourList.get(0).getId(),
                        openingHourList.get(0).getWeekDay(),
                        openingHourList.get(0).getOpeningTime(),
                        openingHourList.get(0).getClosingTime()),
                new OpeningHourResponseDTO(
                        openingHourList.get(1).getId(),
                        openingHourList.get(1).getWeekDay(),
                        openingHourList.get(1).getOpeningTime(),
                        openingHourList.get(1).getClosingTime())
        );
        return openingHourDTOList;
    }

    private static AddressResponseDTO getAddressResponseDTO(UUID addressId, Address address) {
        AddressResponseDTO addressDTO = new AddressResponseDTO(
                addressId,
                address.getZipcode(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState()
        );
        return addressDTO;
    }

    private static List<OpeningHourRequestDTO> getOpeningHourRequestDTOS(List<OpeningHour> openingHourList) {
        List<OpeningHourRequestDTO> openingHourDTOList = List.of(
                new OpeningHourRequestDTO(
                        openingHourList.get(0).getWeekDay(),
                        openingHourList.get(0).getOpeningTime(),
                        openingHourList.get(0).getClosingTime()),
                new OpeningHourRequestDTO(
                        openingHourList.get(1).getWeekDay(),
                        openingHourList.get(1).getOpeningTime(),
                        openingHourList.get(1).getClosingTime())
        );
        return openingHourDTOList;
    }

    private static AddressRequestDTO getAddressRequestDTO(Address address) {
        AddressRequestDTO addressDTO = new AddressRequestDTO(
                address.getZipcode(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState()
        );
        return addressDTO;
    }
}
