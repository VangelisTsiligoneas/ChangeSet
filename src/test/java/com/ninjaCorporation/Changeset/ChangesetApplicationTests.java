package com.ninjaCorporation.Changeset;

import com.ninjaCorporation.Changeset.components.DiffReportProducer;
import com.ninjaCorporation.Changeset.domain.Changeset;
import com.ninjaCorporation.Changeset.services.ChangesetDiffService;
import com.ninjaCorporation.Changeset.services.ChangesetService;
import com.ninjaCorporation.Changeset.utils.MyResourceUtils;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class contains methods that are test of the application.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ChangesetApplication.class)
public class ChangesetApplicationTests {

    private static final String TEST_FILE_1 = "data/DemoData_test.json";

    private static final String TEST_FILE_2 = "data/DemoData_V2_test.json";

    @Autowired
    private ChangesetService changesetService;

    @Autowired
    private ChangesetDiffService changesetDiffService;

    @Autowired
    private DiffReportProducer diffReportProducer;

    private static final Logger LOG = LoggerFactory.getLogger(ChangesetApplicationTests.class);

    @Test
    public void contextLoads() {
        List<Changeset> changesets = changesetService.findAll();
        changesets.forEach(it -> LOG.info(String.format("changeset id: %s, user: %s.", it.getId(), it.getUser().getUsername())));
    }

    @Test
    public void diffReport() {
        try {
            String result = diffReportProducer.produceDiffReport(MyResourceUtils.getContent(TEST_FILE_1), MyResourceUtils.getContent(TEST_FILE_2));
            LOG.info(String.format("Result: %s", result));
        } catch (Exception ex) {
            LOG.error("Could not produce diff report", ex);
        }

//        String content = ResourceUtils.getContent(DEMO_DATA);
//        LOG.info("content: " + content);
    }

//    @Test
    public void diffService() {
        changesetDiffService.produceDiffReport(1, 2);
    }
}
