package io.github.zhyshko.service;

public interface Service<T> {

    T get(Long id);

    T get(T model);

    T save(T entity);

    void delete(T model);

    void delete(Long id);

}
