/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.domain.AbstractEntity;
import java.util.List;
import java.util.Optional;

/**
 *
 * This interface represents the abstraction of an entity service
 * @param <T> a type of entity
 */
public interface AbstractService<T extends AbstractEntity> {

    T save(T entity);
    
    Optional<T> findById(long id);
    
    T findByIdOrFail(long id);

    List<T> findAll();
}
