package com.ninjaCorporation.Changeset;

import static com.ninjaCorporation.Changeset.constants.Resources.DEMO_DATA;
import com.ninjaCorporation.Changeset.domain.Changeset;
import com.ninjaCorporation.Changeset.domain.User;
import com.ninjaCorporation.Changeset.services.ChangesetService;
import com.ninjaCorporation.Changeset.services.TenantService;
import com.ninjaCorporation.Changeset.services.UserService;
import com.ninjaCorporation.Changeset.utils.ResourceUtils;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
@ContextConfiguration(classes = ChangesetApplication.class)
public class ChangesetApplicationTests {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChangesetService changesetService;

    private static final Logger LOG = LoggerFactory.getLogger(ChangesetApplicationTests.class);

    @Test
    public void contextLoads() {
        List<Changeset> changesets = changesetService.findAll();
        changesets.forEach(it -> LOG.info(String.format("changeset id: %s, user: %s.", it.getId(), it.getUser().getUsername())));
    }

    @Test
    public void getFileResource() throws IOException {
//        String content = ResourceUtils.getContent(DEMO_DATA);
//        LOG.info("content: " + content);
    }
}
