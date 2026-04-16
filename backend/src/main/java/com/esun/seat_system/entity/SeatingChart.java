package com.esun.seat_system.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seating_chart")
public class SeatingChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLOOR_SEAT_SEQ")
    private Integer floorSeatSeq;

    @Column(name = "FLOOR_NO")
    private Integer floorNo;

    @Column(name = "SEAT_NO")
    private String seatNo;

}
