/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vangelis
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
}
