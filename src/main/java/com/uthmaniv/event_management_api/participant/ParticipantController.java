package com.uthmaniv.event_management_api.participant;

import com.uthmaniv.event_management_api.util.ApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess getParticipants(@RequestParam long id) {
        return new ApiSuccess("Success", participantService.getEventParticipants(id));
    }

    @Operation(description = "Register a participant to an event")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess addSingleParticipant(@RequestParam long id,
                                     @Valid @RequestBody ParticipantDto dto) {
        return new ApiSuccess("Success", participantService.addSingleParticipant(id,dto));
    }

    @Operation(description = "Register multiple participants",
            summary = "Uploads a csv containing list of participants to be registered to an event")
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess uploadParticipants(@RequestParam long id,
                                     @RequestParam MultipartFile file) throws IOException {
        return new ApiSuccess("Success", participantService.addParticipantsFromFile(id, file));
    }

    @Operation(description = "Update participant Invitation status")
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicAuth")
    public ApiSuccess updateInvitationStatus(@RequestParam long id,
                                       @RequestParam String invitationStatus) {
       return new ApiSuccess(
               "Success",
               participantService.updateInvitationStatus(
                       id,Enum.valueOf(Participant.InvitationStatus.class,invitationStatus)
               )
       );
    }
}
