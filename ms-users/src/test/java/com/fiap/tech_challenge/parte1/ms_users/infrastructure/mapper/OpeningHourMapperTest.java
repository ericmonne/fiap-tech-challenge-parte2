package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour.JdbcOpeningHourEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OpeningHourMapperTest {

    private final OpeningHourMapper mapper = new OpeningHourMapper();

    @Test
    void testToOpeningHourResponseDTO() {
        UUID id = UUID.randomUUID();
        OpeningHour openingHour = new OpeningHour();
        openingHour.setId(id);
        openingHour.setOpeningTime(LocalTime.of(9, 0));
        openingHour.setClosingTime(LocalTime.of(18, 0));

        OpeningHourResponseDTO dto = mapper.toOpeningHourResponseDTO(openingHour);

        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.openingTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(dto.closingTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void testToOpeningHourResponseDTOList() {
        OpeningHour openingHour1 = new OpeningHour();
        openingHour1.setId(UUID.randomUUID());
        openingHour1.setOpeningTime(LocalTime.of(10, 0));
        openingHour1.setClosingTime(LocalTime.of(19, 0));

        OpeningHour openingHour2 = new OpeningHour();
        openingHour2.setId(UUID.randomUUID());
        openingHour2.setOpeningTime(LocalTime.of(8, 0));
        openingHour2.setClosingTime(LocalTime.of(17, 0));

        List<OpeningHourResponseDTO> dtoList = mapper.toOpeningHourResponseDTO(List.of(openingHour1, openingHour2));

        assertThat(dtoList).hasSize(2);
    }

    @Test
    void testToEntityFromDTO() {
        OpeningHourRequestDTO dto = new OpeningHourRequestDTO(
                null,
                LocalTime.of(11, 0),
                LocalTime.of(20, 0)
        );

        OpeningHour openingHour = mapper.toEntity(dto);

        assertThat(openingHour.getOpeningTime()).isEqualTo(LocalTime.of(11, 0));
        assertThat(openingHour.getClosingTime()).isEqualTo(LocalTime.of(20, 0));
    }

    @Test
    void testToEntityListFromDTOList() {
        OpeningHourRequestDTO dto1 = new OpeningHourRequestDTO(
                null,
                LocalTime.of(12, 0),
                LocalTime.of(21, 0)
        );
        OpeningHourRequestDTO dto2 = new OpeningHourRequestDTO(
                null,
                LocalTime.of(13, 0),
                LocalTime.of(22, 0)
        );

        List<OpeningHour> openingHours = mapper.toEntity(List.of(dto1, dto2));

        assertThat(openingHours).hasSize(2);
    }

    @Test
    void testToJdbcOpeningHourEntity() {
        UUID openingHourId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        OpeningHour openingHour = new OpeningHour();
        openingHour.setId(openingHourId);
        openingHour.setOpeningTime(LocalTime.of(14, 0));
        openingHour.setClosingTime(LocalTime.of(23, 0));

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        openingHour.setRestaurant(restaurant);

        JdbcOpeningHourEntity entity = mapper.toJdbcOpeningHourEntity(openingHour);

        assertThat(entity.getId()).isEqualTo(openingHourId);
        assertThat(entity.getOpeningTime()).isEqualTo(LocalTime.of(14, 0));
        assertThat(entity.getClosingTime()).isEqualTo(LocalTime.of(23, 0));
        assertThat(entity.getRestaurantId()).isEqualTo(restaurantId);
    }
}
