package com.esun.seat_system.service;


import com.esun.seat_system.entity.Employee;
import com.esun.seat_system.entity.SeatingChart;
import com.esun.seat_system.repository.EmployeeRepository;
import com.esun.seat_system.repository.SeatingChartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Service
public class FloorSeatService {
    private final EmployeeRepository employeeRepo;
    private final SeatingChartRepository seatRepo;


    public FloorSeatService(EmployeeRepository employeeRepo,
                       SeatingChartRepository seatRepo) {
        this.employeeRepo = employeeRepo;
        this.seatRepo = seatRepo;
    }

    // 取得所有座位表
    public List<SeatingChart> getAllSeats() {
        return seatRepo.findAll();
    }

    // 取得樓層座位表
    public List<SeatingChart> getSeatsByFloor(Integer floorNo) {
        return seatRepo.findByFloorNo(floorNo);
    }

    // 指派座位
    @Transactional
    public String assignSeat(String empId, Integer seatSeq) {
        //驗證員工存在
        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        //驗證座位碼存在
        SeatingChart seat = seatRepo.findById(seatSeq)
                .orElseThrow(() -> new RuntimeException("Seat not found"));


        //規則1：一個人只能一個座位
        if (emp.getFloorSeatSeq() != null) {
            return "Employee already has seat";
        }

        //規則2：座位不能重複
        if (employeeRepo.existsByFloorSeatSeq(seatSeq)) {
            return "Seat already occupied";
        }


        //DB更新(stored procedure)
        employeeRepo.assignSeatProcedure(empId, seatSeq);
        return "Assign success";
    }


    //釋放座位
    @Transactional
    public String releaseSeat(String empId) {
        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if(emp.getFloorSeatSeq()==null){
            return "No seat to release";
        }

        employeeRepo.releaseSeatProcedure(empId);
        return "Release success";
    }
}
