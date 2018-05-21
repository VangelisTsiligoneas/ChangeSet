/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.domain.Changeset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * An implementation of a {@link ChangesetService} service.
 */
@Service
public class ChangesetServiceImpl extends AbstractServiceImpl<Changeset> implements ChangesetService {

    private static final Logger LOG = LoggerFactory.getLogger(ChangesetServiceImpl.class);

    public ChangesetServiceImpl() {
        super(Changeset.class);
    }
}
