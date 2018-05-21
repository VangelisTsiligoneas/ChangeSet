/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.domain.AbstractEntity;
import com.ninjaCorporation.Changeset.exceptions.DataException;
import com.ninjaCorporation.Changeset.repositories.AbstractRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * This class implements methods that are common for all entity services.
 * @param <T> a type of entity
 */
public class AbstractServiceImpl<T extends AbstractEntity> implements AbstractService<T>{
    
    protected Class<T> entityClass;
    
    @Autowired
    private AbstractRepository<T> abstractRepository;

    public AbstractServiceImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        return abstractRepository.save(entity);
    }

    @Override
    public Optional<T> findById(long id) {
        return abstractRepository.findById(id);
    }

    @Override
    public T findByIdOrFail(long id) {
        Optional<T> result = findById(id);
        if(!result.isPresent()){
            throw new DataException(String.format("Could not find instance of %s for id: %s", entityClass.getSimpleName(), id));
        }
        return result.get();
    }

    @Override
    public List<T> findAll() {
        return abstractRepository.findAll();
    }
}
