package dev.louisa.tpms.service;

class PressureTooHighCheck extends PressureCheck {
    private final double pressureThreshold;

    public PressureTooHighCheck(double pressureThreshold) {
        this.pressureThreshold = pressureThreshold;
    }

    public void doCheck(double pressure) {
        if (pressure > pressureThreshold) {
            throw new TpmsPressureTooHighException();
        }
    }
}
