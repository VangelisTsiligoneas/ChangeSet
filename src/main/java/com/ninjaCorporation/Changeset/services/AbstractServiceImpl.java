/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.domain.AbstractEntity;
import com.ninjaCorporation.Changeset.repositories.AbstractRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Vangelis
 */
public class AbstractServiceImpl<T extends AbstractEntity> implements AbstractService<T>{
    
    @Autowired
    private AbstractRepository<T> abstractRepository;

    @Override
    public T save(T entity) {
        return abstractRepository.save(entity);
    }

    @Override
    public List<T> findAll() {
        return abstractRepository.findAll();
    }
}
