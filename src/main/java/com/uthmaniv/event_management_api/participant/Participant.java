package com.uthmaniv.event_management_api.participant;

import com.uthmaniv.event_management_api.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Participant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @NonNull
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    @NonNull
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @NonNull
    private String email;

    @Column(name = "phone_no", unique = true)
    @NotNull(message = "Phone number is required")
    @NonNull
    private Long phoneNumber;

    @ManyToMany(mappedBy = "participants")
    private Set<Event> events = new HashSet<>();
}
