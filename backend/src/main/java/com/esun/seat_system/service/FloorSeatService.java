package com.esun.seat_system.service;
import com.esun.seat_system.dto.ApiResponse;
import com.esun.seat_system.dto.SeatView;
import com.esun.seat_system.entity.Employee;
import com.esun.seat_system.entity.SeatingChart;
import com.esun.seat_system.repository.EmployeeRepository;
import com.esun.seat_system.repository.SeatingChartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


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
    public List<SeatView> getAllSeats() {
        return seatRepo.getAllSeats();
    }

    //取得所有員工編號
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }


    @Transactional
    public ApiResponse handleAction(String empId, Integer seatSeq) {
        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        SeatingChart seat = seatRepo.findById(seatSeq)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        Employee owner = employeeRepo.findByFloorSeatSeq(seatSeq);
        boolean hasSeat = emp.getFloorSeatSeq() != null;
        // 空位→assign
        if (owner == null) {
            if (emp.getFloorSeatSeq() != null) {
                return new ApiResponse("FAIL", "你已經佔用一個座位");
            }
            //DB更新(stored procedure)
            employeeRepo.assignSeatProcedure(empId, seatSeq);
            return new ApiResponse("SUCCESS", "Assign success");
        }

        //別人的座位
        if(owner != null && !owner.getEmpId().equals(empId)){
            if(!hasSeat){
                return new ApiResponse("FAIL", "這個座位已被其他人佔用，請選擇其他座位");
            }
            else {
                return new ApiResponse("FAIL", "這不是你的座位，不能幫別人取消座位");
            }
        }

        //自己的座位 → release
        if (owner != null && owner.getEmpId().equals(empId)) {
            //DB更新(stored procedure)
            employeeRepo.releaseSeatProcedure(empId);
            return new ApiResponse("SUCCESS", "Release success");
        }

        return new ApiResponse("FAIL", "錯誤情況");

    }



        // 指派座位
//    @Transactional
//    public ApiResponse assignSeat(String empId, Integer seatSeq) {
//        //驗證員工存在
//        Employee emp = employeeRepo.findById(empId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        //驗證座位碼存在
//        SeatingChart seat = seatRepo.findById(seatSeq)
//                .orElseThrow(() -> new RuntimeException("Seat not found"));
//
//
//        //規則1：一個人只能一個座位
//        if (emp.getFloorSeatSeq() != null) {
//            return new ApiResponse("FAIL","你已經佔用一個座位");
//        }
//
//        //規則2：座位不能重複佔用
//        if (employeeRepo.existsByFloorSeatSeq(seatSeq)) {
//            return new ApiResponse("FAIL","這個座位已被其他人佔用");
//        }
//
//
//        //DB更新(stored procedure)
//        employeeRepo.assignSeatProcedure(empId, seatSeq);
//        return new ApiResponse("SUCCESS","佔用成功!");
//    }
//
//
//    //釋放座位
//    @Transactional
//    public ApiResponse releaseSeat(String empId, Integer seatSeq) {
//        Employee emp = employeeRepo.findById(empId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        if(emp.getFloorSeatSeq()==null){
//            return new ApiResponse("FAIL","沒有任何座位釋出");
//        }
//
//        if(!seatSeq.equals(emp.getFloorSeatSeq())){
//            return new ApiResponse("FAIL","這不是你的座位");
//        }
//
//        employeeRepo.releaseSeatProcedure(empId);
//        return new ApiResponse("SUCCESS","釋放成功!");
//    }
}
