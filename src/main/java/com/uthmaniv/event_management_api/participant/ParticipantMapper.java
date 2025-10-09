package com.uthmaniv.event_management_api.participant;

import com.uthmaniv.event_management_api.event.Event;
import com.uthmaniv.event_management_api.event.EventRepository;
import com.uthmaniv.event_management_api.exception.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipantMapper {
    private final EventRepository eventRepository;

    public Participant toEntity (ParticipantDto participantDto) {
        Event event = eventRepository.findById(participantDto.eventId())
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        Participant participant = new Participant();
        participant.setFirstName(participantDto.firstName());
        participant.setLastName(participantDto.lastName());
        participant.setEmail(participantDto.email());
        participant.setPhoneNumber(participantDto.phoneNumber());
        participant.setEvent(event);

        return participant;
    }

    public ParticipantDto toDto (Participant participant) {
        return new ParticipantDto(
                participant.getFirstName(),
                participant.getLastName(),
                participant.getEmail(),
                participant.getPhoneNumber(),
                participant.getEvent().getId()
        );
    }

    public List<ParticipantDto> toDtoList (List<Participant> participants) {
        List<ParticipantDto> participantDtoList = new ArrayList<>();

        for (Participant participant: participants) {
            participantDtoList.add(toDto(participant));
        }

        return participantDtoList;
    }

    public List<Participant> parseFromCsv(Event event, MultipartFile participantFile) throws IOException {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(participantFile.getInputStream()))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] csvArray = line.split(",");

                if (csvArray.length >= 5) {
                    String firstName = csvArray[1].trim();
                    String lastName = csvArray[2].trim();
                    String email = csvArray[3].trim();
                    Long phone = Long.valueOf(csvArray[4].trim());

                    Participant participant = new Participant(firstName,lastName, email, phone, event);
                    participants.add(participant);
                }
            }

        } catch (IOException e) {
            throw new IOException("Error parsing CSV file: " + e.getMessage(), e);
        }

        return participants;
    }

}
