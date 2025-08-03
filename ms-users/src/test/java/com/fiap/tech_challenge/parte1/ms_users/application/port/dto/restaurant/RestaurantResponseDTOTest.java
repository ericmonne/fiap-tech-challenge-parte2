package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantResponseDTOTest {

    @Mock
    private AddressResponseDTO mockAddress;

    @Mock
    private OpeningHourResponseDTO mockOpeningHour;

    @Test
    void shouldCreateRestaurantResponseDTOWithAllFields() {
        UUID id = UUID.randomUUID();
        List<OpeningHourResponseDTO> openingHours = List.of(mockOpeningHour);

        RestaurantResponseDTO dto = new RestaurantResponseDTO(
                id,
                "Restaurante Teste",
                mockAddress,
                CuisineType.BRASILEIRA,
                openingHours
        );

        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals("Restaurante Teste", dto.name());
        assertEquals(mockAddress, dto.address());
        assertEquals(CuisineType.BRASILEIRA, dto.cuisineType());
        assertEquals(openingHours, dto.openingHours());
    }

    @Test
    void shouldAllowNullOpeningHours() {
        UUID id = UUID.randomUUID();

        RestaurantResponseDTO dto = new RestaurantResponseDTO(
                id,
                "Restaurante Teste",
                mockAddress,
                CuisineType.JAPONESA,
                null
        );

        assertNotNull(dto);
        assertNull(dto.openingHours());
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        List<OpeningHourResponseDTO> openingHours = List.of(mockOpeningHour);

        when(mockAddress.toString()).thenReturn("AddressResponseDTO[...]");
        when(mockOpeningHour.toString()).thenReturn("OpeningHourResponseDTO[...]");

        RestaurantResponseDTO dto = new RestaurantResponseDTO(
                id,
                "Restaurante Teste",
                mockAddress,
                CuisineType.ITALIANA,
                openingHours
        );

        String expected = "RestaurantResponseDTO[id=123e4567-e89b-12d3-a456-426614174000, " +
                "name=Restaurante Teste, address=AddressResponseDTO[...], " +
                "cuisineType=ITALIANA, openingHours=[OpeningHourResponseDTO[...]]]";

        assertEquals(expected, dto.toString());
    }

    @Test
    void shouldBeEqualWithSameValues() {
        UUID id = UUID.randomUUID();
        List<OpeningHourResponseDTO> openingHours = List.of(mockOpeningHour);

        RestaurantResponseDTO dto1 = new RestaurantResponseDTO(
                id,
                "Restaurante Teste",
                mockAddress,
                CuisineType.MEXICANA,
                openingHours
        );

        RestaurantResponseDTO dto2 = new RestaurantResponseDTO(
                id,
                "Restaurante Teste",
                mockAddress,
                CuisineType.MEXICANA,
                openingHours
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void shouldNotBeEqualWithDifferentValues() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        RestaurantResponseDTO dto1 = new RestaurantResponseDTO(
                id1,
                "Restaurante A",
                mockAddress,
                CuisineType.ITALIANA,
                null
        );

        RestaurantResponseDTO dto2 = new RestaurantResponseDTO(
                id2,
                "Restaurante B",
                mockAddress,
                CuisineType.ITALIANA,
                null
        );

        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }
}