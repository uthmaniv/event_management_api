package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.exception.ResourceAlreadyExistsException;
import com.uthmaniv.event_management_api.exception.ResourceNotFoundException;
import com.uthmaniv.event_management_api.exception.InvalidFileFormatException;
import com.uthmaniv.event_management_api.participant.Participant;
import com.uthmaniv.event_management_api.participant.ParticipantDto;
import com.uthmaniv.event_management_api.participant.ParticipantMapper;
import com.uthmaniv.event_management_api.participant.ParticipantRepository;
import com.uthmaniv.event_management_api.user.User;
import com.uthmaniv.event_management_api.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;


    public void createEvent(EventDto dto) {
        boolean exists = eventRepository.existsByTitleAndLocationAndDateTime(
                dto.title(),
                dto.location(),
                dto.dateTime()
        );

        if (exists) {
            throw new ResourceAlreadyExistsException("Event already exists");
        }
        Event event = eventMapper.toEntity(dto);
        event.setUser(userService.getCurrentUser());
        eventRepository.save(event);
    }

    public void updateEvent(long id, EventDto dto) {
        User user = userService.getCurrentUser();
        Event existingEvent = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Event Not Found"));
        existingEvent.setTitle(dto.title());
        existingEvent.setDescription(dto.description());
        existingEvent.setLocation(dto.location());
        existingEvent.setDateTime(dto.dateTime());

        eventRepository.save(existingEvent);
        eventMapper.toDto(existingEvent);
    }

    public void updateEventDescription(long id, String description) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Event Not Found"));
        event.setDescription(description);
        eventRepository.save(event);
    }

    public void updateEventLocation(long id, String location) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Event Not Found"));
        event.setLocation(location);
        eventRepository.save(event);
    }

    public void updateEventDateTime(long id, LocalDateTime dateTime) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Event Not Found"));
        event.setDateTime(dateTime);
        eventRepository.save(event);
    }

    public void updateTitle(long id, String newTitle) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->  new ResourceNotFoundException("Event not found"));
        event.setTitle(newTitle);

        eventRepository.save(event);
    }

    public void deleteEvent(long id) {
        User user = userService.getCurrentUser();
        Event existingEvent = eventRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.delete(existingEvent);
    }

    public List<EventDto> getAllEvents(){
        List<Event> events = eventRepository.findByUserId(userService.getCurrentUser().getId());
        return eventMapper.toDtoList(events);
    }

    public EventDto findByTitle(String title) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByTitleAndUserId(title, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return eventMapper.toDto(event);
    }

    public EventDto findByDescription(String description) {
        User user = userService.getCurrentUser();
        Event event = eventRepository.findByDescriptionAndUserId(description, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return eventMapper.toDto(event);
    }

    public List<EventDto> findByLocation(String location) {
        User user = userService.getCurrentUser();
        List<Event> events = eventRepository.findByLocationAndUserId(location, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return eventMapper.toDtoList(events);
    }

}
