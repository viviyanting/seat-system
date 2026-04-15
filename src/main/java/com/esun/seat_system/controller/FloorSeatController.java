package com.esun.seat_system.controller;
import com.esun.seat_system.service.FloorSeatService;
import com.esun.seat_system.entity.SeatingChart;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/seats")
public class FloorSeatController {
    private final FloorSeatService floorSeatService;

    public FloorSeatController(FloorSeatService floorSeatService) {
        this.floorSeatService = floorSeatService;
    }

    //取得全部座位
    @GetMapping
    public List<SeatingChart> getAllSeats() {
        return floorSeatService.getAllSeats();
    }

    //依樓層查詢
    @GetMapping("/floor/{floor}")
    public List<SeatingChart> getByFloor(@PathVariable Integer floor) {
        return floorSeatService.getSeatsByFloor(floor);
    }

    //指派座位
    @PostMapping("/assign")
    public String assignSeat(
            @RequestParam Integer seatSeq,
            @RequestParam String empId
    ) {
        return floorSeatService.assignSeat(empId, seatSeq);
    }

    //釋放座位
    @PostMapping("/release")
    public String releaseSeat(@RequestParam String empId) {
        return floorSeatService.releaseSeat(empId);
    }
}
