package com.metaway.Auditoria.controllers;

import com.metaway.Auditoria.dtos.AlbumDTO;
import com.metaway.Auditoria.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumService service;

    @GetMapping
    public ResponseEntity<Page<AlbumDTO>> findAllPaged(Pageable pageable){
        Page<AlbumDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlbumDTO> findById(@PathVariable Long id){
        AlbumDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> insert(@RequestBody AlbumDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlbumDTO> update(@PathVariable Long id, @RequestBody AlbumDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AlbumDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/revisions/{id}")
    public ResponseEntity<List<String>> findRevisions(@PathVariable Long id){
        List<String> list = service.findRevisions(id);
        return ResponseEntity.ok().body(list);
    }
}
