package com.uthmaniv.event_management_api.event;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {

    public Event toEntity(EventDto dto){
        Event event = new Event();
        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setLocation(dto.location());
        event.setDateTime(dto.dateTime());

        return event;
    }

    public EventDto toDto(Event event) {
        return new EventDto(
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getDateTime()
        );
    }

    public List<EventDto> toDtoList(List<Event> events){
        List<EventDto> eventDtoList = new ArrayList<>();

        for(Event event : events) {
            eventDtoList.add(toDto(event));
        }

        return eventDtoList;
    }
}
