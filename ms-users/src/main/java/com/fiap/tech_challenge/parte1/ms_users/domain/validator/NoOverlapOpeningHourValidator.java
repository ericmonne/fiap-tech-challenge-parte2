package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.OverlappingOpeningHourException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoOverlapOpeningHourValidator implements OpeningHourValidator {

    private final OpeningHourGateway openingHourGateway;

    public NoOverlapOpeningHourValidator(OpeningHourGateway openingHourGateway) {
        this.openingHourGateway = openingHourGateway;
    }

    @Override
    public void validate(OpeningHour openingHour) {
        List<OpeningHour> existingHours = openingHourGateway.findByRestaurantId(openingHour.getRestaurant().getId());

        for (OpeningHour existing : existingHours) {
            boolean sameDay = existing.getWeekDay().equals(openingHour.getWeekDay());
            boolean overlap = openingHour.getOpeningTime().isBefore(existing.getClosingTime())
                    && openingHour.getClosingTime().isAfter(existing.getOpeningTime());

            if (sameDay && overlap && !existing.getId().equals(openingHour.getId())) {
                throw new OverlappingOpeningHourException("Horário sobreposto detectado para o dia " + openingHour.getWeekDay());
            }
        }
    }
}
