package com.uthmaniv.event_management_api.participant;

import com.uthmaniv.event_management_api.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus = InvitationStatus.PENDING;

    public enum InvitationStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }
}
