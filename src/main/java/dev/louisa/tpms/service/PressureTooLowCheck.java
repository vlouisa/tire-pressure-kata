package dev.louisa.tpms.service;

class PressureTooLowCheck extends PressureCheck {
    private final double pressureThreshold;

    public PressureTooLowCheck(double pressureThreshold) {
        this.pressureThreshold = pressureThreshold;
    }

    public void doCheck(double pressure) {
        if (pressure < pressureThreshold) {
            throw new TpmsPressureTooLowException();
        }
    }
}
