package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.exception.EventAlreadyExistsException;
import com.uthmaniv.event_management_api.exception.EventNotFoundException;
import com.uthmaniv.event_management_api.exception.InvalidFileFormatException;
import com.uthmaniv.event_management_api.participant.Participant;
import com.uthmaniv.event_management_api.participant.ParticipantDto;
import com.uthmaniv.event_management_api.participant.ParticipantMapper;
import com.uthmaniv.event_management_api.participant.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ParticipantMapper participantMapper;
    private final ParticipantRepository participantRepository;

    public EventService(EventRepository eventRepository,
                        EventMapper eventMapper,
                        ParticipantMapper participantMapper,
                        ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.participantMapper = participantMapper;
        this.participantRepository = participantRepository;
    }

    public void createEvent(EventDto dto) {
        if (eventRepository.existsByTitle(dto.title())){
            throw new EventAlreadyExistsException("Event with title '" + dto.title() + "' already exists");
        }
        eventRepository.save(eventMapper.toEntity(dto));
    }

    public void updateEvent(long id, EventDto dto) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("Event Not Found"));
        existingEvent.setTitle(dto.title());
        existingEvent.setDescription(dto.description());
        existingEvent.setLocation(dto.location());
        existingEvent.setDateTime(dto.dateTime());

        eventRepository.save(existingEvent);
        eventMapper.toDto(existingEvent);
    }

    public void updateEventDescription(long id, String description) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("Event Not Found"));
        event.setDescription(description);
        eventRepository.save(event);
    }

    public void updateEventLocation(long id, String location) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("Event Not Found"));
        event.setLocation(location);
        eventRepository.save(event);
    }

    public void updateEventDateTime(long id, LocalDateTime dateTime) {
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new EventNotFoundException("Event Not Found"));
        event.setDateTime(dateTime);
        eventRepository.save(event);
    }

    public void updateTitle(long id, String newTitle) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() ->  new EventNotFoundException("Event not found"));
        event.setTitle(newTitle);

        eventRepository.save(event);
    }

    public void deleteEvent(long id) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        eventRepository.delete(existingEvent);
    }

    public List<EventDto> getAllEvents(){
        List<Event> events = eventRepository.findAll();
        return eventMapper.toDtoList(events);
    }

    public EventDto findByTitle(String title) {
        Event event = eventRepository.findByTitle(title).orElseThrow(() -> new EventNotFoundException("Event not found"));
        return eventMapper.toDto(event);
    }

    public EventDto findByDescription(String description) {
        Event event = eventRepository.findByDescription(description).orElseThrow(() -> new EventNotFoundException("Event not found"));
        return eventMapper.toDto(event);
    }

    public List<EventDto> findByLocation(String location) {
        List<Event> events = eventRepository.findByLocation(location).orElseThrow(() -> new EventNotFoundException("Event not found"));
        return eventMapper.toDtoList(events);
    }

    public void addSingleParticipant(long id, ParticipantDto participantDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

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
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
        List<Participant> participants = participantMapper.parseFromCsv(participantsFile);

        participantRepository.saveAll(participants);
        event.getParticipants().addAll(participants);
        eventRepository.save(event);
    }

    public List<ParticipantDto> getEventParticipants (long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        return participantMapper.toDtoList(event.getParticipants().stream().toList());
    }

}
