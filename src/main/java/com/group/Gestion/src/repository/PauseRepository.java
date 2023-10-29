package com.group.Gestion.src.repository;

import com.group.Gestion.src.model.Pause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PauseRepository extends JpaRepository<Pause,Long> {

}
