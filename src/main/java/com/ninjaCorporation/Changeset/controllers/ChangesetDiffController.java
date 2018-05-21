/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.controllers;

import com.ninjaCorporation.Changeset.services.ChangesetDiffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * This is a changeset Report controller
 */
@RestController
@RequestMapping("/changesetReport")
public class ChangesetDiffController extends AbstractController{

    @Autowired
    private ChangesetDiffService changesetDiffService;

    private static final Logger LOG = LoggerFactory.getLogger(ChangesetDiffController.class);

    @RequestMapping(value = "getDiffReport", method = GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getDiffReport(@RequestParam(name = "changesetId1") long changesetId1, @RequestParam(name = "changesetId2") long changesetId2) {
        return changesetDiffService.produceDiffReport(changesetId1, changesetId2);
    }

    @RequestMapping(value = "hello", method = GET)
    public String hello() {
        return "hello";
    }
}
