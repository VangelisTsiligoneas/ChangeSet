/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

import com.ninjaCorporation.Changeset.components.DiffReportProducer;
import com.ninjaCorporation.Changeset.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * An implementation of a {@link ChangesetDiffService} service.
 */
@Service
public class ChangesetDiffServiceImpl implements ChangesetDiffService {    

    @Autowired
    private ChangesetService changesetService;
    
    @Autowired
    private DiffReportProducer diffReportProducer;    

    private static final Logger LOG = LoggerFactory.getLogger(ChangesetDiffServiceImpl.class);

    @Override
    public String produceDiffReport(long changesetId1, long changesetId2) {
        try {
            String data1 = changesetService.findByIdOrFail(changesetId1).getData();
            String data2 = changesetService.findByIdOrFail(changesetId2).getData();
            return diffReportProducer.produceDiffReport(data1, data2);
        } catch (BusinessException ex) {
            throw new BusinessException(String.format("Could not produce diff report for changeset ids: %s and %s", changesetId1, changesetId2), ex);
        }
    }
}
