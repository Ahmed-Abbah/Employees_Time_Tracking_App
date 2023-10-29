package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.WorkDay;
import com.group.Gestion.src.repository.WorkDayRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WorkDayService {

    private WorkDayRepository workDayRepository;
    @Autowired
    public void setWorkTimeRepository(WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;
    }

    public WorkDay save(WorkDay workDay){
         return this.workDayRepository.save(workDay);
    }

    public WorkDay update(WorkDay workDay){
        return this.update(workDay);
    }

    public WorkDay findWorkDayByDateAndEmployee(Employee employee,String currentDate){
        return this.workDayRepository.findWorkDayByDateAndEmployee(employee,currentDate);
    }
}
