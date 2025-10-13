package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.participant.ParticipantDto;
import com.uthmaniv.event_management_api.participant.ParticipantService;
import com.uthmaniv.event_management_api.util.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/events")
public class EventController {

    private final EventService eventService;
    private final ParticipantService participantService;

    @Operation(description = "Get all events",
               summary = "Retrieves all the events added")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Events retrieved successfully",
                    content = @Content(schema = @Schema(implementation = EventDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();

        return new ApiSuccess("success", events);
    }

    @Operation(description = "Search event by title",
               summary = "Retrieves event for a given title")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event retrieved successfully",
                    content = @Content(schema = @Schema(implementation = EventDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/title")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getByTitle(@RequestParam String title) {
        return new ApiSuccess("success", eventService.findByTitle(title));
    }

    @Operation(description = "Search event by description",
               summary = "Retrieves event for a given description")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event retrieved successfully",
                    content = @Content(schema = @Schema(implementation = EventDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/description")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getByDescription(@RequestParam String description) {
        return new ApiSuccess("Success", eventService.findByDescription(description));
    }

    @Operation(description = "Search event by location",
               summary = "Retrieves event for a given location")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event retrieved successfully",
                    content = @Content(schema = @Schema(implementation = EventDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getByLocation(@RequestParam String location) {
        return new ApiSuccess("Success", eventService.findByLocation(location));
    }

    @Operation(description = "Get all participants of an event",
               summary = "Retrieves all registered participant of a given event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Participants retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ParticipantDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/participants")
    @ResponseStatus(HttpStatus.OK)
    public ApiSuccess getParticipants(@RequestParam long id) {
        return new ApiSuccess("Success", participantService.getEventParticipants(id));
    }

    @Operation(description = "Create new Event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Event createad successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEvent(@Valid  @RequestBody EventDto dto) {
        eventService.createEvent(dto);
    }

    @Operation(description = "Register a participant to an event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Participant added successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/participants/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSingleParticipant(@RequestParam long id,
                                     @Valid @RequestBody ParticipantDto dto) {
        participantService.addSingleParticipant(id,dto);
    }

    @Operation(description = "Register multiple participants",
               summary = "Uploads a csv containing list of participants to be registered to an event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Participants uploaded successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/participants/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadParticipants(@RequestParam long id,
                                     @RequestParam MultipartFile file) throws IOException {
        participantService.addParticipantsFromFile(id, file);
        return "Participants uploaded successfully";
    }

    @Operation(description = "Update Event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Event updated successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEvent(@RequestParam long id,
                            @Valid @RequestBody EventDto dto) {
        eventService.updateEvent(id, dto);
    }

    @Operation(description = "Update event title")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Event title updated successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/title")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTitle(@RequestParam long id,
                            @RequestParam String newTitle) {
        eventService.updateTitle(id, newTitle);
    }

    @Operation(description = "Update event description")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Event description updated successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/description")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDescription(@RequestParam long id,
                                  @RequestParam String description) {
        eventService.updateEventDescription(id, description);
    }

    @Operation(description = "Update event location")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Event location updated successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocation(@RequestParam long id,
                               @RequestParam String location) {
        eventService.updateEventLocation(id, location);
    }

    @Operation(description = "Update event time and date")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Event date-time updated successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/date-time")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTimeStamp(@RequestParam long id,
                                @RequestParam LocalDateTime dateTime) {
        eventService.updateEventDateTime(id,dateTime);
    }

    @Operation(description = "Delete event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Event deleted successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@RequestParam  long id) {
        eventService.deleteEvent(id);
    }
}
