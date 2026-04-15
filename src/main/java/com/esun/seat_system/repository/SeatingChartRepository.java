package com.esun.seat_system.repository;
import com.esun.seat_system.entity.SeatingChart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatingChartRepository extends JpaRepository<SeatingChart, Integer> {


    List<SeatingChart> findByFloorNo(Integer floorNo);

}
