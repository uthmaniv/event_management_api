package com.uthmaniv.event_management_api.participant;

import com.uthmaniv.event_management_api.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Participant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @Email
    private String email;

    @Column
    private long phoneNumber;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Participant(String firstName, String lastName, String email, long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    private Participant() {}

    public String getFirstName() {
        return firstName;
    }

    public Participant setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Participant setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Participant setEmail(String email) {
        this.email = email;
        return this;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public Participant setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return getPhoneNumber() == that.getPhoneNumber() && Objects.equals(id, that.id) && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getFirstName(), getLastName(), getEmail(), getPhoneNumber());
    }
}
