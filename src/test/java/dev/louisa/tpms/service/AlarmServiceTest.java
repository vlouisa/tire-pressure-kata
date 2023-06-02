package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;
import dev.louisa.tpms.service.alarm.Alarm;
import dev.louisa.tpms.service.alarm.AlarmService;
import dev.louisa.tpms.service.pressure.PressureCheck;
import dev.louisa.tpms.service.pressure.PressureGauge;
import dev.louisa.tpms.service.pressure.PressureTooHighCheck;
import dev.louisa.tpms.service.pressure.PressureTooLowCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.louisa.tpms.service.alarm.AlarmStatus.OFF;
import static dev.louisa.tpms.service.alarm.AlarmStatus.ON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {
    @Mock
    private TireSensor tireSensor;
    private AlarmService alarmService;

    @BeforeEach
    void setUp() {
        PressureCheck pressureCheck = createChain(24, 29);
        alarmService = new AlarmService(new Alarm(), new PressureGauge(pressureCheck));
    }

    //TODO: redundant-1
    private static PressureCheck createChain(double lowPressureThreshold , double highPressureThreshold) {
        var tooHighCheck = new PressureTooHighCheck(highPressureThreshold);
        var tooLowCheck = new PressureTooLowCheck(lowPressureThreshold);
        tooLowCheck.setNextCheck(tooHighCheck);
        return tooLowCheck;
    }

    @Test
    void should_not_activate_alarm_when_service_is_initialized() {
        assertThat(alarmService.getAlarmStatus()).isEqualTo(OFF);
    }

    @ParameterizedTest
    @ValueSource(doubles = {24.0, 27.3, 29.0})
    void should_not_activate_alarm_when_pressure_is_optimal(double tirePressure) {
        when(tireSensor.measureTirePressure()).thenReturn(tirePressure);
        
        alarmService.check(tireSensor);
        assertThat(alarmService.getAlarmStatus()).isEqualTo(OFF);
    }

    @ParameterizedTest
    @ValueSource(doubles = {20.43, 23.99})
    void should_activate_alarm_when_pressure_is_too_low(double tirePressure) {
        when(tireSensor.measureTirePressure()).thenReturn(tirePressure);
        
        alarmService.check(tireSensor);
        assertThat(alarmService.getAlarmStatus()).isEqualTo(ON);
    }

    @ParameterizedTest
    @ValueSource(doubles = {29.01, 31.5})
    void should_activate_alarm_when_pressure_is_too_high(double tirePressure) {
        when(tireSensor.measureTirePressure()).thenReturn(tirePressure);
        
        alarmService.check(tireSensor);
        assertThat(alarmService.getAlarmStatus()).isEqualTo(ON);
    }

    @Test
    void should_stay_activated_once_it_is_activated() {
        when(tireSensor.measureTirePressure()).thenReturn(25.1, 31.0, 25.1, 24.2);

        alarmService.check(tireSensor);
        assertThat(alarmService.getAlarmStatus()).isEqualTo(OFF);
        alarmService.check(tireSensor);
        assertThat(alarmService.getAlarmStatus()).isEqualTo(ON);
        alarmService.check(tireSensor);
        assertThat(alarmService.getAlarmStatus()).isEqualTo(ON);
        alarmService.check(tireSensor);
        assertThat(alarmService.getAlarmStatus()).isEqualTo(ON);
    }
}