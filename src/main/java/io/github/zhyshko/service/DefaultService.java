package io.github.zhyshko.service;

import io.github.zhyshko.model.Base;
import io.github.zhyshko.model.order.Order;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public class DefaultService<T extends Base> implements io.github.zhyshko.service.Service<T> {

    private final JpaRepository<T, Long> dao;

    private DefaultService() {
        this(null);
    }

    protected DefaultService(JpaRepository<T, Long> dao) {
        this.dao = dao;
    }

    public T get(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by id: "+id));
    }

    public T get(T model) {
        return dao.findOne(Example.of(model))
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by example: "+model));
    }

    public T save(T entity) {
        return dao.save(entity);
    }

    public void delete(T model) {
        dao.delete(model);
    }

    public void delete(Long id) {
        dao.deleteById(id);
    }

    protected JpaRepository<T, Long> getDao() {
        return this.dao;
    }

}
