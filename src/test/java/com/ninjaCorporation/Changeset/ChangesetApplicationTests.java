package com.ninjaCorporation.Changeset;

import com.ninjaCorporation.Changeset.domain.User;
import com.ninjaCorporation.Changeset.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
@ContextConfiguration(classes =ChangesetApplication.class)
public class ChangesetApplicationTests {
    
    @Autowired
    private UserRepository userRepository;
    
    private static final Logger LOG = LoggerFactory.getLogger(ChangesetApplicationTests.class);

	@Test
	public void contextLoads() {
            User user = new User();
            user.setUsername("vangelis");
            user = userRepository.save(user);
            LOG.info(String.format("user id: %s, username: %s", user.getId(), user.getUsername()));
	}
}
