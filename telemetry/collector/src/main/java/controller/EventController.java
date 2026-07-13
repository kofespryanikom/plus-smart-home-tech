package controller;

import lombok.RequiredArgsConstructor;
import model.hub.HubEvent;
import model.sensor.SensorEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.EventService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @PostMapping("/hubs")
    void collectHubEvent(@RequestBody HubEvent event) {
        eventService.collectHubEvent(event);
    }

    @PostMapping("/sensors")
    void collectSensorEvent(@RequestBody SensorEvent event) {
        eventService.collectSensorEvent(event);
    }
}
