package com.esun.seat_system.repository;
import com.esun.seat_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByFloorSeatSeq(Integer floorSeatSeq);
    @Procedure("assign_seat")
    void assignSeatProcedure(String p_emp_id, Integer p_seat_seq);

    @Procedure("release_seat")
    void releaseSeatProcedure(String p_emp_id);

    Employee findByFloorSeatSeq(Integer seatSeq);

}
