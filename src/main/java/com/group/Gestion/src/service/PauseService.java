package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Pause;
import com.group.Gestion.src.repository.PauseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PauseService {
    private PauseRepository pauseRepository;
    @Autowired
    public void setPauseRepository(PauseRepository pauseRepository) {
        this.pauseRepository = pauseRepository;
    }

    public Pause save(Pause pause){

        return this.pauseRepository.save(pause);
    }
}
