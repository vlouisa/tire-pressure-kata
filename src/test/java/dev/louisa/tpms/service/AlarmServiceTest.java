package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.louisa.tpms.service.AlarmStatus.OFF;
import static dev.louisa.tpms.service.AlarmStatus.ON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {
    @Mock
    private TireSensor tireSensor;
    private AlarmService alarmService;

    @BeforeEach
    void setUp() {
        alarmService = new AlarmService();
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