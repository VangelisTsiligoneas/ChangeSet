/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.domain.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vangelis
 */
@Service
public class TenantServiceImpl extends AbstractServiceImpl<Tenant> implements TenantService{
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceImpl.class);
}