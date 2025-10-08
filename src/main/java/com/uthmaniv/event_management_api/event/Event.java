package com.uthmaniv.event_management_api.event;

import com.uthmaniv.event_management_api.participant.Participant;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    @NonNull
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    @NonNull
    private String description;

    @Column
    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    @NonNull
    private String location;

    @Column
    @NotNull(message = "Date and time are required")
    @FutureOrPresent(message = "Event date must be in the present or future")
    @NonNull
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> participants = new HashSet<>();
}
