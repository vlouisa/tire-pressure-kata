package dev.louisa.tpms.sensor;

import java.util.Random;

// The reading of the pressure value from the sensor is simulated in this implementation.
// Because the focus of the exercise is on the other class.
public class TireSensor {
    private static final double OFFSET = 22.5;

    public double measureTirePressure() {
        return OFFSET + samplePressure();
    }

    private static double samplePressure() {
        // placeholder implementation that simulates a real sensor in a real tire
        final var numberGenerator = new Random();
        return 8 * numberGenerator.nextDouble();
    }
}