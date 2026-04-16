package com.esun.seat_system.repository;


import com.esun.seat_system.dto.SeatView;
import com.esun.seat_system.entity.SeatingChart;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface SeatingChartRepository extends JpaRepository<SeatingChart, Integer> {
    @Query("""
        SELECT new com.esun.seat_system.dto.SeatView(
            s.floorSeatSeq,
            s.floorNo,
            s.seatNo,
            e.empId
        )
        FROM SeatingChart s
        LEFT JOIN Employee e
        ON s.floorSeatSeq = e.floorSeatSeq
    """)
    List<SeatView> getAllSeats();
}








