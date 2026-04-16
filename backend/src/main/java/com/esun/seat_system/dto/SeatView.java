package com.esun.seat_system.dto;


import lombok.Getter;

@Getter
public class SeatView {
    private Integer floorSeatSeq;
    private Integer floorNo;
    private String seatNo;
    private String empId;

    public SeatView(Integer floorSeatSeq, Integer floorNo, String seatNo, String empId) {
        this.floorSeatSeq = floorSeatSeq;
        this.floorNo = floorNo;
        this.seatNo = seatNo;
        this.empId = empId;
    }

}
