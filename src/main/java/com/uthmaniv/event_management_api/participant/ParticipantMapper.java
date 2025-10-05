package com.uthmaniv.event_management_api.participant;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParticipantMapper {

    public Participant toEntity (ParticipantDto participantDto) {
        Participant participant = new Participant();
        participant.setFirstName(participantDto.firstName());
        participant.setLastName(participantDto.lastName());
        participant.setEmail(participantDto.email());
        participant.setPhoneNumber(participantDto.phoneNumber());

        return participant;
    }

    public ParticipantDto toDto (Participant participant) {
        return new ParticipantDto(
                participant.getFirstName(),
                participant.getLastName(),
                participant.getEmail(),
                participant.getPhoneNumber()
        );
    }

    public List<ParticipantDto> toDtoList (List<Participant> participants) {
        List<ParticipantDto> participantDtoList = new ArrayList<>();

        for (Participant participant: participants) {
            participantDtoList.add(toDto(participant));
        }

        return participantDtoList;
    }

    public List<Participant> parseFromCsv(MultipartFile participantFile) throws IOException {
        List<Participant> participants = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(participantFile.getInputStream()))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] csvArray = line.split(",");

                if (csvArray.length == 4) {
                    String firstName = csvArray[0].trim();
                    String lastName = csvArray[1].trim();
                    String email = csvArray[2].trim();
                    Long phone = Long.parseLong(csvArray[3].trim());

                    Participant participant = new Participant(firstName,lastName, email, phone);
                    participants.add(participant);
                }
            }

        } catch (IOException e) {
            throw new IOException("Error parsing CSV file: " + e.getMessage(), e);
        }

        return participants;
    }

}
