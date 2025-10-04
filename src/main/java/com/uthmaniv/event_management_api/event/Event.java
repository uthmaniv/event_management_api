package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.participant.Participant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String description;

    @Column
    @NotBlank
    private String location;

    @Column
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> participants;

    public Event(String title, String description, String location, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
    }

    public Event() {}

    public String getTitle() {
        return title;
    }

    public Event setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Event setLocation(String location) {
        this.location = location;
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Event setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public Event setParticipants(Set<Participant> participants) {
        this.participants.addAll(participants);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(getTitle(), event.getTitle()) && Objects.equals(getDescription(), event.getDescription()) && Objects.equals(getLocation(), event.getLocation()) && Objects.equals(getDateTime(), event.getDateTime()) && Objects.equals(getParticipants(), event.getParticipants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getTitle(), getDescription(), getLocation(), getDateTime(), getParticipants());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", dateTime=" + dateTime +
                ", participants=" + participants +
                '}';
    }
}
