package io.github.seastar.tx.org;

import io.github.seastar.tx.org.model.OrgUser;
import io.github.seastar.tx.org.service.OrgUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrgUserServiceTests {


    @Autowired
    private OrgUserService orgUserService;


    @Test
    public void testFindByUid() {

        OrgUser user = orgUserService.findByUid(1);

        System.out.println(user);
    }


}
