package com.metaway.Auditoria.services;

import com.metaway.Auditoria.dtos.AlbumDTO;
import com.metaway.Auditoria.entities.Album;
import com.metaway.Auditoria.repositories.AlbumRepository;
import com.metaway.Auditoria.repositories.ProductorRepository;
import com.metaway.Auditoria.services.exceptions.DatabaseException;
import com.metaway.Auditoria.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    @Autowired
    private ProductorRepository productorRepository;

    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAllPaged(Pageable pageable){
        Page<Album> list = repository.findAll(pageable);
        return list.map(AlbumDTO::new);
    }

    @Transactional(readOnly = true)
    public AlbumDTO findById(Long id){
        Album entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        return new AlbumDTO(entity);
    }

    @Transactional
    public AlbumDTO insert(AlbumDTO dto){
        Album entity = new Album();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new AlbumDTO(entity);
    }

    @Transactional
    public AlbumDTO update(Long id, AlbumDTO dto){
        Album entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new AlbumDTO(entity);
    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    public void copyDtoToEntity(AlbumDTO dto, Album entity){
        entity.setTitle(dto.getTitle());
        entity.setDateCreate(dto.getDateCreate());
        entity.setAuthor(dto.getAuthor());
        entity.setProductor(productorRepository.getReferenceById(dto.getProductorId()));
    }

    @Transactional
    public List<String> findRevisions(Long id){
        return repository.findRevisions(id).stream().map(Object::toString).toList();
    }
}
