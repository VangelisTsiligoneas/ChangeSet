/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.domain.AbstractEntity;
import java.util.List;

/**
 *
 * @author Vangelis
 */
public interface AbstractService<T extends AbstractEntity> {

    T save(T entity);

    List<T> findAll();
}
