package models.entities.dao;

import models.entities.AbstractEntity;

public interface Dao<T extends AbstractEntity> {

    void create(T entity);

    T read(Long id);

    void update(T entity);

    void delete(Long id);

    boolean exists(Long id);

    long count();
}