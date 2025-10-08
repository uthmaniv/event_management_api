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
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();

        return new ApiSuccess("success", events);
    }

    @Operation(
            description = "Search event by title",
            summary = "Retrieves event for a given title"
    )
    @GetMapping("/title")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getByTitle(@RequestParam String title) {
        return new ApiSuccess("success", eventService.findByTitle(title));
    }

    @Operation(
            description = "Search event by description",
            summary = "Retrieves event for a given description"
    )
    @GetMapping("/description")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getByDescription(@RequestParam String description) {
        return new ApiSuccess("Success", eventService.findByDescription(description));
    }

    @Operation(
            description = "Search event by location",
            summary = "Retrieves event for a given location"
    )
    @GetMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getByLocation(@RequestParam String location) {
        return new ApiSuccess("Success", eventService.findByLocation(location));
    }

    @Operation(
            description = "Get all participants of an event",
            summary = "Retrieves all registered participant of a given event"
    )
    @GetMapping("/participants")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getParticipants(@RequestParam long id) {
        return new ApiSuccess("Success", eventService.getEventParticipants(id));
    }

    @Operation(
            description = "Create new Event"
    )
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEvent(@Valid  @RequestBody EventDto dto) {
        eventService.createEvent(dto);
    }

    @Operation(
            description = "Register a participant to an event"
    )
    @PostMapping("/participants/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSingleParticipant(@RequestParam long id,
                                     @Valid @RequestBody ParticipantDto dto) {
        eventService.addSingleParticipant(id,dto);
    }

    @Operation(
            description = "Register multiple participants",
            summary = "Uploads a csv containing list of participants to be registered to an event"
    )
    @PostMapping("/participants/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadParticipants(@RequestParam long id,
                                     @RequestParam MultipartFile file) throws IOException {
        eventService.addParticipantsFromFile(id, file);
        return "Participants uploaded successfully";
    }

    @Operation(
            description = "Update Event"
    )
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEvent(@RequestParam long id,
                            @Valid @RequestBody EventDto dto) {
        eventService.updateEvent(id, dto);
    }

    @Operation(
            description = "Update event title"
    )
    @PatchMapping("/title")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTitle(@RequestParam long id,
                            @RequestParam String newTitle) {
        eventService.updateTitle(id, newTitle);
    }

    @Operation(
            description = "Update event description"
    )
    @PatchMapping("/description")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDescription(@RequestParam long id,
                                  @RequestParam String description) {
        eventService.updateEventDescription(id, description);
    }

    @Operation(
            description = "Update event location"
    )
    @PatchMapping("/location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocation(@RequestParam long id,
                               @RequestParam String location) {
        eventService.updateEventLocation(id, location);
    }

    @Operation(
            description = "Update event time and date"
    )
    @PatchMapping("/date-time")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTimeStamp(@RequestParam long id,
                                @RequestParam LocalDateTime dateTime) {
        eventService.updateEventDateTime(id,dateTime);
    }

    @Operation(
            description = "Delete event"
    )
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@RequestParam long id) {
        eventService.deleteEvent(id);
    }
}
