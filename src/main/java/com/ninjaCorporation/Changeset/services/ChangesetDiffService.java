/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.services;

/**
 *
 * This service contains methods related to diff reports of changeset data.
 */
public interface ChangesetDiffService {

    public String produceDiffReport(long changesetId1, long changesetId2);
}
