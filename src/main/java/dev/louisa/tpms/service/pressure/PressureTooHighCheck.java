package dev.louisa.tpms.service.pressure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PressureTooHighCheck extends PressureCheck {
    private final double pressureThreshold;

    public void doCheck(double pressure) {
        if (pressure > pressureThreshold) {
            throw new PressureTooHighException();
        }
    }
}
