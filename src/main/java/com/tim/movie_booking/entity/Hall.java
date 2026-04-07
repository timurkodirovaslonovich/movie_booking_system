package com.tim.movie_booking.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String hallNumber;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ShowTime> showTimes = new ArrayList<>();

    public Hall() {
    }

    public Hall(String hallNumber, Integer capacity, Cinema cinema) {
        this.hallNumber = hallNumber;
        this.capacity = capacity;
        this.cinema = cinema;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(String hallNumber) {
        this.hallNumber = hallNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }
}