package ru.practicum.service.snapshot.handler;

import org.springframework.stereotype.Component;
import ru.practicum.model.Condition;
import ru.practicum.model.ScenarioCondition;
import ru.yandex.practicum.kafka.telemetry.sensor.SwitchSensorAvro;
import ru.yandex.practicum.kafka.telemetry.snapshot.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.snapshot.SensorsSnapshotAvro;

@Component
public class SwitchConditionHandler implements ConditionHandler {
    @Override
    public String getType() {
        return "SWITCH";
    }

    @Override
    public boolean check(ScenarioCondition scenarioCondition, SensorsSnapshotAvro sensorsSnapshotAvro) {
        Condition condition = scenarioCondition.getCondition();
        String sensorId = scenarioCondition.getSensor().getId();
        SensorStateAvro state = sensorsSnapshotAvro.getSensorsState().get(sensorId);

        if (state == null) {
            return false;
        }

        SwitchSensorAvro data = (SwitchSensorAvro) state.getData();

        if (condition.getOperation().equals("EQUALS")) {
            return data.getState() == ((condition.getValue()) == 1);
        }
        return false;
    }
}
