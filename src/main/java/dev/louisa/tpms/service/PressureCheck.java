package dev.louisa.tpms.service;

abstract class PressureCheck {
    private PressureCheck nextCheck;

    public final void setNextCheck(PressureCheck pressureCheck) {
        this.nextCheck = pressureCheck;
    }

    public void execute(double pressure) {
        doCheck(pressure);
        if (nextCheck != null) {
            nextCheck.execute(pressure);
        }
    }

    public abstract void doCheck(double pressure);
}
