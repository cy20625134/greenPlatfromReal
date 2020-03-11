package com.aiot.green.comm.greenuser.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.aiot.green.api.exception.GreenException;
import com.aiot.green.comm.GreenUser.service.IGreenUser;
import com.shrimp.green.api.comm.entity.GreenUser;

public class GreenUserServiceImplTest extends AbstractGreenUserBaseTest {
	@Resource(name = "comm.GreenUserService")
	private IGreenUser userService;

	@Test
	@Rollback(false)
	public void TestAddGreenUser() throws GreenException {
		GreenUser gu = userService.addGreenUser("崔勇2", "18640261376", null, "password", null, null, null, null);
		log.debug("");
	}

	@Test
	@Rollback(false)
	public void TestUpdateGreenUser() throws GreenException {
		GreenUser gu = userService.updateGreenUser("FC79F9554B7D444A8FC468AA0F6D9BAB", "1");
		log.debug("");
	}

	@Test
	@Rollback(false)
	public void TestUpdateGreenUserFull() throws GreenException {
		GreenUser tgu = userService.queryGreenUserByUuid("FC79F9554B7D444A8FC468AA0F6D9BAB");
		tgu.setName("催牛逼");
		tgu.setEmail("cuiyong@sia.cn");
		GreenUser gu = userService.updateGreenUser(tgu);
		log.debug("");

	}
}
