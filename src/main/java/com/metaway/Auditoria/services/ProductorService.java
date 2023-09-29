package com.metaway.Auditoria.services;

import com.metaway.Auditoria.dtos.ProductorDTO;
import com.metaway.Auditoria.entities.Productor;
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
import java.util.stream.Collectors;

@Service
public class ProductorService {

    @Autowired
    private ProductorRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductorDTO> findAllPaged(Pageable pageable){
        Page<Productor> list = repository.findAll(pageable);
        return list.map(ProductorDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductorDTO findById(Long id){
        Productor entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        return new ProductorDTO(entity);
    }

    @Transactional
    public ProductorDTO insert(ProductorDTO dto){
        Productor entity = new Productor();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductorDTO(entity);
    }

    @Transactional
    public ProductorDTO update(Long id, ProductorDTO dto){
        Productor entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductorDTO(entity);
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

    @Transactional(readOnly = true)
    public List<String> findRevisions(Long id){
        return repository.findRevisions(id).stream().map(Object::toString).collect(Collectors.toList());
    }
}
