package ru.rsreu.TrafficCodeServer.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCringeService<T> {

    protected final JpaRepository<T, Long> repository;

    @Autowired
    public AbstractCringeService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T saveAndFlush(T entity) {
        return repository.saveAndFlush(entity);
    }

    public List<T> saveAll(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public List<T> findAllById(Iterable<Long> longs) {
        return repository.findAllById(longs);
    }

    public Optional<T> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
