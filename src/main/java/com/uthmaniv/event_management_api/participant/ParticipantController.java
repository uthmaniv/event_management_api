package com.uthmaniv.event_management_api.participant;

import com.uthmaniv.event_management_api.util.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess getParticipants(@RequestParam long id) {
        return new ApiSuccess("Success", participantService.getEventParticipants(id));
    }

    @Operation(description = "Register a participant to an event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Participant added successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "basicAuth")
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
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "basicAuth")
    public String uploadParticipants(@RequestParam long id,
                                     @RequestParam MultipartFile file) throws IOException {
        participantService.addParticipantsFromFile(id, file);
        return "Participants uploaded successfully";
    }

    @Operation(description = "Update participant Invitation status")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Invitation status Updated Successfully"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public void updateInvitationStatus(@RequestParam long id,
                                       @RequestParam String invitationStatus) {
       participantService.updateInvitationStatus(id,Enum.valueOf(Participant.InvitationStatus.class,invitationStatus));
    }
}
