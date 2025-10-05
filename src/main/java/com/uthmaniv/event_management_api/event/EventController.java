package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.participant.ParticipantDto;
import com.uthmaniv.event_management_api.util.ApiSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<ApiSuccess> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();

        return ResponseEntity.ok(new ApiSuccess("success", events));
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiSuccess> getByTitle(@RequestParam String title) {
        return ResponseEntity
                .ok(new ApiSuccess("success", eventService.findByTitle(title)));
    }

    @GetMapping("/search/description")
    public ResponseEntity<ApiSuccess> getByDescription(@RequestParam String description) {
        return ResponseEntity
                .ok(new ApiSuccess("Success", eventService.findByDescription(description)));
    }

    @GetMapping("/search/location")
    public ResponseEntity<ApiSuccess> getByLocation(@RequestParam String location) {
        return ResponseEntity
                .ok(new ApiSuccess("Success", eventService.findByLocation(location)));
    }

    @GetMapping("/participants")
    public ResponseEntity<ApiSuccess> getParticipants(@RequestParam String title) {
        return ResponseEntity
                .ok(new ApiSuccess("Success", eventService.getEventParticipants(title)));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addEvent(@RequestBody EventDto dto) {
        eventService.createEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/add-participant")
    public ResponseEntity<Void> addSingleParticipant(@RequestParam String title,
                                                     @RequestBody ParticipantDto dto) {
        eventService.addSingleParticipant(title,dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add-participant/upload")
    public ResponseEntity<String> uploadParticipants(@RequestParam String title,
                                                     @RequestPart MultipartFile file) throws IOException {
        eventService.addParticipantsFromFile(title, file);
        return ResponseEntity.ok("Participants uploaded successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEvent(@RequestParam String title,
                                            @RequestBody EventDto dto) {
        eventService.updateEvent(title, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/title")
    public ResponseEntity<Void> updateTitle(@RequestParam String oldTitle,
                                            @RequestParam String newTitle) {
        eventService.updateTitle(oldTitle, newTitle);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/description")
    public ResponseEntity<Void> updateDescription(@RequestParam String title,
                                                  @RequestParam String description) {
        eventService.updateEventDescription(title, description);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/location")
    public ResponseEntity<Void> updateLocation(@RequestParam String title,
                                               @RequestParam String location) {
        eventService.updateEventDescription(title, location);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/date-time")
    public ResponseEntity<Void> updateTimeStamp(@RequestParam String title,
                                                @RequestParam LocalDateTime dateTime) {
        eventService.updateEventDateTime(title,dateTime);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEvent(@RequestParam String title) {
        eventService.deleteEvent(title);
        return ResponseEntity.noContent().build();
    }
}
