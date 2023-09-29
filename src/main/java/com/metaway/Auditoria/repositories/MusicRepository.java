package com.metaway.Auditoria.repositories;

import com.metaway.Auditoria.entities.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface MusicRepository extends JpaRepository<Music, Long>, RevisionRepository<Music, Long, Long> {
}
