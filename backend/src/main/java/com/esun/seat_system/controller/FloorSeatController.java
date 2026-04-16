package com.esun.seat_system.controller;
import com.esun.seat_system.dto.ApiResponse;
import com.esun.seat_system.dto.SeatActionRequest;
import com.esun.seat_system.dto.SeatView;
import com.esun.seat_system.entity.Employee;
import com.esun.seat_system.service.FloorSeatService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/seats")
public class FloorSeatController {
    private final FloorSeatService floorSeatService;

    public FloorSeatController(FloorSeatService floorSeatService) {
        this.floorSeatService = floorSeatService;
    }

    //取得全部座位
    @GetMapping
    public List<SeatView> getSeats() {
        return floorSeatService.getAllSeats();
    }

    //取得全部員工資料
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return floorSeatService.getAllEmployees();
    }


    //依樓層查詢
//    @GetMapping("/floor/{floor}")
//    public List<SeatingChart> getByFloor(@PathVariable Integer floor) {
//        return floorSeatService.getSeatsByFloor(floor);
//    }

    //佔用/釋放座位
    @PostMapping("/action")
    public ApiResponse action(@RequestBody SeatActionRequest req) {
        return floorSeatService.handleAction(req.getEmpId(), req.getSeatSeq());
    }


    //指派座位
//    @PostMapping("/assign")
//    public ApiResponse assignSeat(
//            @RequestParam String empId,
//            @RequestParam Integer seatSeq
//    ) {
//        return floorSeatService.assignSeat(empId, seatSeq);
//    }
//
//    //釋放座位
//    @PostMapping("/release")
//    public ApiResponse releaseSeat(
//            @RequestParam String empId,
//            @RequestParam Integer seatSeq) {
//        return floorSeatService.releaseSeat(empId, seatSeq);
//    }
}
