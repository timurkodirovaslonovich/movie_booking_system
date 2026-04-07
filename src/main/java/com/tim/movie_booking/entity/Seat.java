package com.tim.movie_booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Getter
@Setter
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer rowNumber;

    @Column(nullable = false)
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Ticket> tickets = new ArrayList<>();

    public Seat() {
    }

    public Seat(Integer rowNumber, Integer seatNumber, SeatType seatType, Hall hall) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.hall = hall;
    }

}