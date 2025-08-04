package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.OpeningHourMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JdbcOpeningHourDataSourceTest {

    @Mock
    private JdbcOpeningHourRepository jdbcOpeningHourRepository;

    @Mock
    private OpeningHourMapper openingHourMapper;

    @InjectMocks
    private JdbcOpeningHourDataSource dataSource;

    private UUID openingHourId;
    private UUID restaurantId;
    private OpeningHour openingHour;
    private JdbcOpeningHourEntity jdbcEntity;

    @BeforeEach
    void setUp() {
        openingHourId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        openingHour = new OpeningHour();
        openingHour.setId(openingHourId);
        openingHour.setRestaurant(restaurant);
        openingHour.setWeekDay(WeekDay.SEGUNDA);
        openingHour.setOpeningTime(LocalTime.of(8, 0));
        openingHour.setClosingTime(LocalTime.of(18, 0));

        jdbcEntity = new JdbcOpeningHourEntity();
        jdbcEntity.setId(openingHourId);
        jdbcEntity.setRestaurantId(restaurantId);
        jdbcEntity.setWeekDay(WeekDay.SEGUNDA);
        jdbcEntity.setOpeningTime(LocalTime.of(8, 0));
        jdbcEntity.setClosingTime(LocalTime.of(18, 0));
    }

    @Test
    void createOpeningHour_ShouldConvertAndSaveEntity() {
        when(openingHourMapper.toJdbcOpeningHourEntity(openingHour)).thenReturn(jdbcEntity);
        when(jdbcOpeningHourRepository.save(jdbcEntity)).thenReturn(openingHourId);

        UUID resultId = dataSource.createOpeningHour(openingHour);

        assertEquals(openingHourId, resultId);
        verify(openingHourMapper).toJdbcOpeningHourEntity(openingHour);
        verify(jdbcOpeningHourRepository).save(jdbcEntity);
    }

    @Test
    void findById_ShouldReturnOpeningHourWhenExists() {
        when(jdbcOpeningHourRepository.findById(openingHourId)).thenReturn(Optional.of(openingHour));

        Optional<OpeningHour> result = dataSource.findById(openingHourId);

        assertTrue(result.isPresent());
        assertEquals(openingHour, result.get());
        verify(jdbcOpeningHourRepository).findById(openingHourId);
    }

    @Test
    void findById_ShouldReturnEmptyWhenNotExists() {
        when(jdbcOpeningHourRepository.findById(openingHourId)).thenReturn(Optional.empty());

        Optional<OpeningHour> result = dataSource.findById(openingHourId);

        assertTrue(result.isEmpty());
        verify(jdbcOpeningHourRepository).findById(openingHourId);
    }

    @Test
    void update_ShouldConvertAndUpdateEntity() {
        when(openingHourMapper.toJdbcOpeningHourEntity(openingHour)).thenReturn(jdbcEntity);
        doNothing().when(jdbcOpeningHourRepository).update(jdbcEntity);

        dataSource.update(openingHour);

        verify(openingHourMapper).toJdbcOpeningHourEntity(openingHour);
        verify(jdbcOpeningHourRepository).update(jdbcEntity);
    }

    @Test
    void findByRestaurantId_ShouldDelegateToRepository() {
        List<OpeningHour> expected = List.of(openingHour);

        when(jdbcOpeningHourRepository.findByRestaurantId(restaurantId)).thenReturn(expected);

        List<OpeningHour> result = dataSource.findByRestaurantId(restaurantId);

        assertEquals(expected, result);
        verify(jdbcOpeningHourRepository).findByRestaurantId(restaurantId);
    }

    @Test
    void deleteByRestaurantId_ShouldDelegateToRepository() {
        doNothing().when(jdbcOpeningHourRepository).deleteByRestaurantId(restaurantId);

        dataSource.deleteByRestaurantId(restaurantId);

        verify(jdbcOpeningHourRepository).deleteByRestaurantId(restaurantId);
    }

    @Test
    void findByRestaurantId_ShouldReturnEmptyListWhenNoResults() {
        when(jdbcOpeningHourRepository.findByRestaurantId(restaurantId)).thenReturn(List.of());

        List<OpeningHour> result = dataSource.findByRestaurantId(restaurantId);

        assertTrue(result.isEmpty());
        verify(jdbcOpeningHourRepository).findByRestaurantId(restaurantId);
    }
}