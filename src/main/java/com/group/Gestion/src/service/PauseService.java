package com.group.Gestion.src.service;

import com.group.Gestion.src.repository.PauseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PauseService {
    private PauseRepository pauseRepository;
    @Autowired
    public void setPauseRepository(PauseRepository pauseRepository) {
        this.pauseRepository = pauseRepository;
    }
}
