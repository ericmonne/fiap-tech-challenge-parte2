package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.OverlappingOpeningHourException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoOverlapOpeningHourValidatorTest {

    @Mock
    private OpeningHourGateway openingHourGateway;

    @InjectMocks
    private NoOverlapOpeningHourValidator validator;

    private final UUID restaurantId = UUID.randomUUID();
    private final Restaurant restaurant = new Restaurant();

    @BeforeEach
    void setUp() {
        restaurant.setId(restaurantId);
    }

    @Test
    void shouldPassValidationWhenNoExistingHours() {
        OpeningHour newHour = createOpeningHour(WeekDay.SEGUNDA, "10:00", "22:00");
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of());

        assertThatCode(() -> validator.validate(newHour))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldPassValidationWhenDifferentDays() {
        OpeningHour newHour = createOpeningHour(UUID.randomUUID(), WeekDay.SEGUNDA, "10:00", "22:00");
        OpeningHour existingHour = createOpeningHour(UUID.randomUUID(), WeekDay.TERCA, "10:00", "22:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatCode(() -> validator.validate(newHour))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldPassValidationWhenNonOverlappingTimes() {
        OpeningHour newHour = createOpeningHour(WeekDay.SEGUNDA, "10:00", "12:00");
        OpeningHour existingHour = createOpeningHour(WeekDay.SEGUNDA, "14:00", "22:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatCode(() -> validator.validate(newHour))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowWhenOverlappingTimesSameDay() {
        OpeningHour newHour = createOpeningHour(WeekDay.SEGUNDA, "10:00", "22:00");
        OpeningHour existingHour = createOpeningHour(WeekDay.SEGUNDA, "12:00", "15:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatThrownBy(() -> validator.validate(newHour))
                .isInstanceOf(OverlappingOpeningHourException.class)
                .hasMessageContaining("Horário sobreposto detectado para o dia SEGUNDA");
    }

    @Test
    void shouldPassWhenUpdatingSameOpeningHour() {
        UUID existingId = UUID.randomUUID();
        OpeningHour newHour = createOpeningHour(existingId, WeekDay.SEGUNDA, "10:00", "22:00");
        OpeningHour existingHour = createOpeningHour(existingId, WeekDay.SEGUNDA, "10:00", "22:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatCode(() -> validator.validate(newHour))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowWhenNewTimeContainsExisting() {
        OpeningHour newHour = createOpeningHour(WeekDay.SEGUNDA, "08:00", "23:00");
        OpeningHour existingHour = createOpeningHour(WeekDay.SEGUNDA, "12:00", "15:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatThrownBy(() -> validator.validate(newHour))
                .isInstanceOf(OverlappingOpeningHourException.class);
    }

    @Test
    void shouldThrowWhenExistingContainsNewTime() {
        OpeningHour newHour = createOpeningHour(WeekDay.SEGUNDA, "12:00", "15:00");
        OpeningHour existingHour = createOpeningHour(WeekDay.SEGUNDA, "08:00", "23:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatThrownBy(() -> validator.validate(newHour))
                .isInstanceOf(OverlappingOpeningHourException.class);
    }

    @Test
    void shouldThrowWhenNewTimeStartsDuringExisting() {
        OpeningHour newHour = createOpeningHour(WeekDay.SEGUNDA, "12:00", "18:00");
        OpeningHour existingHour = createOpeningHour(WeekDay.SEGUNDA, "10:00", "15:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatThrownBy(() -> validator.validate(newHour))
                .isInstanceOf(OverlappingOpeningHourException.class);
    }

    @Test
    void shouldThrowWhenNewTimeEndsDuringExisting() {
        OpeningHour newHour = createOpeningHour(WeekDay.SEGUNDA, "08:00", "12:00");
        OpeningHour existingHour = createOpeningHour(WeekDay.SEGUNDA, "10:00", "15:00");

        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(existingHour));

        assertThatThrownBy(() -> validator.validate(newHour))
                .isInstanceOf(OverlappingOpeningHourException.class);
    }

    private OpeningHour createOpeningHour(WeekDay day, String opening, String closing) {
        return createOpeningHour(UUID.randomUUID(), day, opening, closing);
    }

    private OpeningHour createOpeningHour(UUID id, WeekDay day, String opening, String closing) {
        OpeningHour oh = new OpeningHour();
        oh.setId(id);
        oh.setWeekDay(day);
        oh.setOpeningTime(LocalTime.parse(opening));
        oh.setClosingTime(LocalTime.parse(closing));
        oh.setRestaurant(restaurant);
        return oh;
    }
}