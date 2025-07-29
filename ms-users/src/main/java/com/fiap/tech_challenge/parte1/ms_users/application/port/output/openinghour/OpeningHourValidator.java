package com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;

public interface OpeningHourValidator {
    void validate(OpeningHour openingHour);
}
