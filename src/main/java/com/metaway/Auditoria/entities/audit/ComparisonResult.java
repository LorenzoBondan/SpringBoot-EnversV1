package com.metaway.Auditoria.entities.audit;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ComparisonResult {
    private List<Change> changes = new ArrayList<>();

    public void addChange(Long objectId, String propertyName, Object oldValue, Object newValue, Instant instant, String username) {
        Change change = new Change(objectId.toString(), propertyName, oldValue.toString(), newValue.toString(), instant.toString(), username);
        changes.add(change);
    }

    public List<Change> getChanges() {
        return changes;
    }
}
