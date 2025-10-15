package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.participant.ParticipantService;
import com.uthmaniv.event_management_api.util.ApiSuccess;
import com.uthmaniv.event_management_api.util.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/events")
public class EventController {

    private final EventService eventService;
    private final ParticipantService participantService;

    @Operation(description = "Get all events",
               summary = "Retrieves all the events added")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<EventDto> eventPage = eventService.getAllEvents(pageable);

        PagedResponse<EventDto> pagedResponse = PagedResponse.fromPage(eventPage);
        return new ApiSuccess("success", pagedResponse);
    }

    @Operation(description = "Search event by title",
               summary = "Retrieves event for a given title")
    @GetMapping("/title")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess getByTitle(@RequestParam String title) {
        return new ApiSuccess("success", eventService.findByTitle(title));
    }

    @Operation(description = "Search event by description",
               summary = "Retrieves event for a given description")
    @GetMapping("/description")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess getByDescription(@RequestParam String description) {
        return new ApiSuccess("Success", eventService.findByDescription(description));
    }

    @Operation(description = "Search event by location",
               summary = "Retrieves event for a given location")
    @GetMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess getByLocation(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam String location) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<EventDto> eventPage = eventService.findByLocation(location,pageable);
        PagedResponse<EventDto> pagedResponse = PagedResponse.fromPage(eventPage);

        return new ApiSuccess("Success", pagedResponse);
    }

    @Operation(description = "Create new Event")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess addEvent(@Valid  @RequestBody EventDto dto) {
        return new ApiSuccess("success", eventService.createEvent(dto));
    }

    @Operation(description = "Update Event")
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess updateEvent(@RequestParam long id,
                            @Valid @RequestBody EventDto dto) {
        return new ApiSuccess("Success", eventService.updateEvent(id, dto));
    }

    @Operation(description = "Update event title")
    @PatchMapping("/title")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess updateTitle(@RequestParam long id,
                            @RequestParam String newTitle) {
        return new ApiSuccess("Success", eventService.updateTitle(id, newTitle));
    }

    @Operation(description = "Update event description")
    @PatchMapping("/description")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess updateDescription(@RequestParam long id,
                                  @RequestParam String description) {
        return new ApiSuccess("success", eventService.updateEventDescription(id, description));
    }

    @Operation(description = "Update event location")
    @PatchMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess updateLocation(@RequestParam long id,
                               @RequestParam String location) {
        return new ApiSuccess("success", eventService.updateEventLocation(id, location));
    }

    @Operation(description = "Update event time and date")
    @PatchMapping("/date-time")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess updateTimeStamp(@RequestParam long id,
                                @RequestParam LocalDateTime dateTime) {
        return new ApiSuccess("success", eventService.updateEventDateTime(id,dateTime));
    }

    @Operation(description = "Delete event")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "basicAuth")
    public void deleteEvent(@RequestParam  long id) {
        eventService.deleteEvent(id);
    }
}
