package service;

import model.hub.HubEvent;
import model.sensor.SensorEvent;

public interface EventService {
    void collectHubEvent(HubEvent event);

    void collectSensorEvent(SensorEvent event);
}
