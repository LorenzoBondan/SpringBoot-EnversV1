package com.metaway.Auditoria.services;

import com.metaway.Auditoria.dtos.MusicDTO;
import com.metaway.Auditoria.entities.Music;
import com.metaway.Auditoria.entities.User;
import com.metaway.Auditoria.entities.audit.Change;
import com.metaway.Auditoria.entities.audit.ComparisonResult;
import com.metaway.Auditoria.repositories.AlbumRepository;
import com.metaway.Auditoria.repositories.AuditRepository;
import com.metaway.Auditoria.repositories.ChangeRepository;
import com.metaway.Auditoria.repositories.MusicRepository;
import com.metaway.Auditoria.services.exceptions.DatabaseException;
import com.metaway.Auditoria.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.*;

@Service
public class MusicService {

    @Autowired
    private MusicRepository repository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AuditRepository auditRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private ChangeRepository changeRepository;

    @Transactional(readOnly = true)
    public List<MusicDTO> findAll(){
        return repository.findAll().stream().map(MusicDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public MusicDTO findById(Long id){
        Music entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        return new MusicDTO(entity);
    }

    @Transactional
    public MusicDTO insert(MusicDTO dto){
        Music entity = new Music();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new MusicDTO(entity);
    }

    @Transactional
    public MusicDTO update(Long id, MusicDTO dto){
        Music entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new MusicDTO(entity);
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
    
    public void copyDtoToEntity(MusicDTO dto, Music entity){
        entity.setTitle(dto.getTitle());
        entity.setTimeDuration(dto.getTimeDuration());
        entity.setAlbum(albumRepository.getReferenceById(dto.getAlbumId()));
    }

    @Transactional
    public List<String> findRevisions(Long id){
        return repository.findRevisions(id).stream().map(Object::toString).toList();
    }

    @Transactional
    public List<Music> getAllMusicsAudits(@PathVariable Long id){
        return auditRepository.getRevisions(Music.class, "id", id);
    }

    @Transactional
    public List<ComparisonResult> findAllComparisons() {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<ComparisonResult> comparisons = new ArrayList<>();

        List<Long> musicIds = auditReader.createQuery()
                .forRevisionsOfEntity(Music.class, false, true)
                .addProjection(AuditEntity.id())
                .getResultList();

        Set<Long> musicIdsSet = new HashSet<>(musicIds);

        for (Long musicId : musicIdsSet) {
            List<Object[]> revisions = auditReader.createQuery()
                    .forRevisionsOfEntity(Music.class, false, true)
                    .add(AuditEntity.id().eq(musicId))
                    .getResultList();

            for (int i = 1; i < revisions.size(); i++) {
                Music currentMusic = (Music) revisions.get(i)[0];
                Music previousMusic = (Music) revisions.get(i - 1)[0];

                ComparisonResult comparison = compareMusic(currentMusic, previousMusic);
                comparisons.add(comparison);
            }
        }

        return comparisons;
    }

    @Transactional
    private ComparisonResult compareMusic(Music current, Music previous) {
        ComparisonResult result = new ComparisonResult();
        User user = authService.authenticated();

        if (!Objects.equals(current.getTitle(), previous.getTitle())) {
            Change change = new Change();
            change.setInstant(Instant.now().toString());
            change.setUsername(user.getEmail());
            change.setObjectId(current.getId().toString());
            change.setOldValue(previous.getTitle());
            change.setNewValue(current.getTitle());
            change.setPropertyName("title");
            changeRepository.save(change);

            result.addChange(current.getId(), "title", previous.getTitle(), current.getTitle(), Instant.now(), user.getEmail());
        }
        if (!Objects.equals(current.getTimeDuration(), previous.getTimeDuration())) {
            Change change = new Change(current.getId().toString(), "timeDuration", current.getTimeDuration().toString(), previous.getTimeDuration().toString(), Instant.now().toString(), user.getEmail());
            result.addChange(current.getId(), "timeDuration", current.getTimeDuration(), previous.getTimeDuration(), Instant.now(), user.getEmail());
            changeRepository.save(change);
        }

        return result;
    }
}
