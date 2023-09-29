package com.metaway.Auditoria.repositories;

import com.metaway.Auditoria.entities.audit.Change;
import com.metaway.Auditoria.entities.audit.ChangePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeRepository extends JpaRepository<Change, ChangePK> {

    Boolean existsByObjectIdAndProperyNameAndOldValueAndNewValue();
}
