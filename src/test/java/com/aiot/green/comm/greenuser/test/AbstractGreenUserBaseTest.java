package com.aiot.green.comm.greenuser.test;

import java.util.HashMap;

import com.aiot.green.api.base.PageHandler;
import com.aiot.green.api.base.PageHandler.Pager;
import com.shrimp.framework.core.ISystemCache;
import com.shrimp.framework.core.SystemHandle;
import com.shrimp.framework.test.AbstractBaseTest;

public class AbstractGreenUserBaseTest extends AbstractBaseTest {
	protected ISystemCache cache = SystemHandle.GetInstance().getSystemCache();

	public AbstractGreenUserBaseTest() {
		cache.createSession(new HashMap<String, Object>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8783823227811879403L;

			{
				put("user_code", "333333");
				put("unit_code", "444444");
			}
		});
		PageHandler.setPager(new Pager(1, 10));
	}
}
