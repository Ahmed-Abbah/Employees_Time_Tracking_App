package com.group.Gestion.src.service;

import com.group.Gestion.src.repository.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkTimeService {

    private WorkTimeRepository workTimeRepository;
    @Autowired
    public void setWorkTimeRepository(WorkTimeRepository workTimeRepository) {
        this.workTimeRepository = workTimeRepository;
    }
}
