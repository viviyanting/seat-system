package com.esun.seat_system.dto;

import lombok.Getter;

@Getter
public class ApiResponse {
    private String status;
    private String message;


    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }


}
