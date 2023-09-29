package com.metaway.Auditoria.controllers;

import com.metaway.Auditoria.dtos.MusicDTO;
import com.metaway.Auditoria.entities.Music;
import com.metaway.Auditoria.entities.audit.ComparisonResult;
import com.metaway.Auditoria.services.AuthService;
import com.metaway.Auditoria.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/musics")
public class MusicController {

    @Autowired
    private MusicService service;

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<MusicDTO>> findAll(){
        List<MusicDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MusicDTO> findById(@PathVariable Long id){
        MusicDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<MusicDTO> insert(@RequestBody MusicDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MusicDTO> update(@PathVariable Long id, @RequestBody MusicDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MusicDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/revisions/{id}")
    public ResponseEntity<List<String>> findRevisions(@PathVariable Long id){
        List<String> list = service.findRevisions(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/audit/{id}")
    public List<Music> getAllMusicsAudits(@PathVariable Long id) {
        return service.getAllMusicsAudits(id);
    }

    @GetMapping("/diffall")
    public ResponseEntity<List<ComparisonResult>> getAllComparisons() {
        List<ComparisonResult> comparisons = service.findAllComparisons();
        return ResponseEntity.ok(comparisons);
    }

}
