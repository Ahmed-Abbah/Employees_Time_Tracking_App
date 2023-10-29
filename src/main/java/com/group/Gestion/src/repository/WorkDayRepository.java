package com.group.Gestion.src.repository;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkDayRepository extends JpaRepository<WorkDay,Long> {

    @Query("SELECT e FROM WorkDay e WHERE e.employee = :employee AND e.date = :date")
    WorkDay findWorkDayByDateAndEmployee(@Param("employee") Employee employee, @Param("date") String date);
}
