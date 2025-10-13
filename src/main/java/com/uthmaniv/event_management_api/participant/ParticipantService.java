package com.uthmaniv.event_management_api.participant;

import com.uthmaniv.event_management_api.event.Event;
import com.uthmaniv.event_management_api.event.EventRepository;
import com.uthmaniv.event_management_api.exception.InvalidFileFormatException;
import com.uthmaniv.event_management_api.exception.ResourceNotFoundException;
import com.uthmaniv.event_management_api.user.User;
import com.uthmaniv.event_management_api.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;
    private final UserService userService;
    private final EventRepository eventRepository;

    public void addSingleParticipant(long id, ParticipantDto participantDto) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Participant participant = participantRepository.findByEmail(participantDto.email())
                .orElseGet(() -> {
                    Participant newParticipant = participantMapper.toEntity(participantDto);
                    return participantRepository.save(newParticipant);
                });

        event.getParticipants().add(participant);
        eventRepository.save(event);
    }


    public void addParticipantsFromFile(long id, MultipartFile participantsFile) throws IOException {
        if (!participantsFile.getContentType().equals("text/csv") &&
                !participantsFile.getOriginalFilename().endsWith(".csv")) {
            throw new InvalidFileFormatException("Invalid file format. Please upload a CSV file.");
        }
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        List<Participant> participants = participantMapper.parseFromCsv(event,participantsFile);

        participantRepository.saveAll(participants);
        event.getParticipants().addAll(participants);
        eventRepository.save(event);
    }

    public List<ParticipantDto> getEventParticipants (long id) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return participantMapper.toDtoList(event.getParticipants().stream().toList());
    }

    public void updateInvitationStatus(long participantId) {
        User currentUser = userService.getCurrentUser();
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        // Ensure this participant belongs to an event owned by the logged-in user
        if (!participant.getEvent().getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Participant Not found");
        }

        participant.setInvitationStatus(Participant.InvitationStatus.ACCEPTED);
        participantRepository.save(participant);
    }

}
