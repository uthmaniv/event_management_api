package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.participant.ParticipantDto;
import com.uthmaniv.event_management_api.util.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @Operation(
            description = "Get all events",
            summary = "Retrieves all the events added"
    )
    @GetMapping
    public ResponseEntity<ApiSuccess> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();

        return ResponseEntity.ok(new ApiSuccess("success", events));
    }

    @Operation(
            description = "Search event by title",
            summary = "Retrieves event for a given title"
    )
    @GetMapping("/search/title")
    public ResponseEntity<ApiSuccess> getByTitle(@RequestParam String title) {
        return ResponseEntity
                .ok(new ApiSuccess("success", eventService.findByTitle(title)));
    }

    @Operation(
            description = "Search event by description",
            summary = "Retrieves event for a given description"
    )
    @GetMapping("/search/description")
    public ResponseEntity<ApiSuccess> getByDescription(@RequestParam String description) {
        return ResponseEntity
                .ok(new ApiSuccess("Success", eventService.findByDescription(description)));
    }

    @Operation(
            description = "Search event by location",
            summary = "Retrieves event for a given location"
    )
    @GetMapping("/search/location")
    public ResponseEntity<ApiSuccess> getByLocation(@RequestParam String location) {
        return ResponseEntity
                .ok(new ApiSuccess("Success", eventService.findByLocation(location)));
    }

    @Operation(
            description = "Get all participants of an event",
            summary = "Retrieves all registered participant of a given event"
    )
    @GetMapping("/participants")
    public ResponseEntity<ApiSuccess> getParticipants(@RequestParam String title) {
        return ResponseEntity
                .ok(new ApiSuccess("Success", eventService.getEventParticipants(title)));
    }

    @Operation(
            description = "Create new Event"
    )
    @PostMapping("/add")
    public ResponseEntity<Void> addEvent(@Valid  @RequestBody EventDto dto) {
        eventService.createEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Register a participant to an event"
    )
    @PostMapping("/add-participant")
    public ResponseEntity<Void> addSingleParticipant(@RequestParam String title,
                                                     @Valid @RequestBody ParticipantDto dto) {
        eventService.addSingleParticipant(title,dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Register multiple participants",
            summary = "Uploads a csv containing list of participants to be registered to an event"
    )
    @PostMapping("/add-participant/upload")
    public ResponseEntity<String> uploadParticipants(@RequestParam String title,
                                                     @RequestParam MultipartFile file) throws IOException {
        eventService.addParticipantsFromFile(title, file);
        return ResponseEntity.ok("Participants uploaded successfully");
    }

    @Operation(
            description = "Update Event"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> updateEvent(@RequestParam String title,
                                            @Valid @RequestBody EventDto dto) {
        eventService.updateEvent(title, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Update event title"
    )
    @PatchMapping("/update/title")
    public ResponseEntity<Void> updateTitle(@RequestParam String oldTitle,
                                            @RequestParam String newTitle) {
        eventService.updateTitle(oldTitle, newTitle);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Update event description"
    )
    @PatchMapping("/update/description")
    public ResponseEntity<Void> updateDescription(@RequestParam String title,
                                                  @RequestParam String description) {
        eventService.updateEventDescription(title, description);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Update event location"
    )
    @PatchMapping("/update/location")
    public ResponseEntity<Void> updateLocation(@RequestParam String title,
                                               @RequestParam String location) {
        eventService.updateEventLocation(title, location);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Update event time and date"
    )
    @PatchMapping("/update/date-time")
    public ResponseEntity<Void> updateTimeStamp(@RequestParam String title,
                                                @RequestParam LocalDateTime dateTime) {
        eventService.updateEventDateTime(title,dateTime);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Delete event"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEvent(@RequestParam String title) {
        eventService.deleteEvent(title);
        return ResponseEntity.noContent().build();
    }
}
