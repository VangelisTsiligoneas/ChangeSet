/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset;

import com.ninjaCorporation.Changeset.constants.Resources;
import com.ninjaCorporation.Changeset.domain.Changeset;
import com.ninjaCorporation.Changeset.domain.Tenant;
import com.ninjaCorporation.Changeset.domain.User;
import com.ninjaCorporation.Changeset.services.ChangesetService;
import com.ninjaCorporation.Changeset.services.TenantService;
import com.ninjaCorporation.Changeset.services.UserService;
import com.ninjaCorporation.Changeset.utils.MyResourceUtils;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 *
 * This class configures the data at start up
 */
@Configuration
public class DataConfiguration {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChangesetService changesetService;

    private static final Logger LOG = LoggerFactory.getLogger(DataConfiguration.class);

    @PostConstruct
    @Transactional
    public void configureData() {
        LOG.info("Configuration of Data..");
        populateData();
        LOG.info("Configuration of Data completed successfully.");
    }

    private void populateData() {
        class Populator {

            public void populateFirstEntry() {
                Tenant tenant = new Tenant();
                tenant.setName("tenant1");
                tenant = tenantService.save(tenant);
                User user = new User();
                user.setUsername("coolUser");
                user.setTenant(tenant);
                user = userService.save(user);
                Changeset changeset = new Changeset();
                changeset.setData(MyResourceUtils.getContent(Resources.DEMO_DATA));
                changeset.setUser(user);
                changeset = changesetService.save(changeset);
            }

            public void populateSecondEntry() {
                Tenant tenant = new Tenant();
                tenant.setName("tenant2");
                tenant = tenantService.save(tenant);
                User user = new User();
                user.setUsername("notSoCoolUser");
                user.setTenant(tenant);
                user = userService.save(user);
                Changeset changeset = new Changeset();
                changeset.setData(MyResourceUtils.getContent(Resources.DEMO_DATA_2));
                changeset.setUser(user);
                changeset = changesetService.save(changeset);
            }
        }
        
        Populator populator = new Populator();
        //FIRST ENTRY
        populator.populateFirstEntry();

        //SECOND ENTRY
        populator.populateSecondEntry();
    }
}
