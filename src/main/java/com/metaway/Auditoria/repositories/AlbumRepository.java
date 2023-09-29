package com.metaway.Auditoria.repositories;

import com.metaway.Auditoria.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface AlbumRepository extends JpaRepository<Album, Long>, RevisionRepository<Album, Long, Long> {
}
