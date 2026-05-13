package models.entities.dao;

import models.entities.AbstractEntity;

import java.util.HashMap;
import java.util.Map;

public class MemoireDao<T extends AbstractEntity> implements Dao<T>{

    public Map<Long,T> persist = new HashMap<>();

    Long sequence = 0L;

    private Long incrementAutoSequence(){
        return this.sequence++;
    }

    @Override
    public void create(T entity) {
        entity.setId(incrementAutoSequence());
        persist.put(entity.getId(),entity);
    }

    @Override
    public T read(Long id) {
        return persist.get(id);
    }

    @Override
    public void update(T entity) {
        persist.replace(entity.getId(),entity);
    }

    @Override
    public void delete(Long id) {
        persist.remove(id);
    }

    @Override
    public boolean exists(Long id) {
        return persist.containsKey(id);
    }

    @Override
    public long count() {
        return persist.size();
    }
}
