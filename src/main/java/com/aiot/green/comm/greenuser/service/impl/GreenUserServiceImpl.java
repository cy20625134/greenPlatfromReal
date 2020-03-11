package com.aiot.green.comm.greenuser.service.impl;

import java.util.List;

import com.aiot.green.api.base.PageHandler;
import com.aiot.green.api.exception.GreenException;
import com.aiot.green.comm.GreenUser.service.IGreenUser;
import com.shrimp.framework.core.ICriterion;
import com.shrimp.framework.core.ICriterion.ConditionType;
import com.shrimp.framework.core.support.AbstractServiceSupport;
import com.shrimp.framework.core.support.SystemCriterionSupport;
import com.shrimp.framework.entity.OperateType;
import com.shrimp.framework.entity.QueryResult;
import com.shrimp.framework.utils.IdMakerUtil;
import com.shrimp.framework.utils.StringUtil;
import com.shrimp.green.api.comm.entity.GreenUser;

public class GreenUserServiceImpl extends AbstractServiceSupport<GreenUser> implements IGreenUser {

	@Override
	public GreenUser addGreenUser(String name, String phone, String address, String password, String sex, String email, String identityId, Integer marriage) throws GreenException {
		if (name == null || phone == null || password == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "addGreenUser", "name||phone||password", "为null");
		GreenUser gu = new GreenUser();
		List<GreenUser> userList = queryGreenUserMultiPara(null, phone, null, null, null, null, null).getResultlist();
		if (userList != null && userList.size() > 0)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "addGreenUser", "注册手机号", "已注册");
		gu.setUuid(IdMakerUtil.makeByUUID());
		gu.setName(name);
		gu.setPhone(phone);
		gu.setPassword(password);
		gu.setAddress(address);
		gu.setSex(sex);
		gu.setEmail(email);
		gu.setIdentityId(identityId);
		gu.setMarriage(marriage);
		gu.setOperate(OperateType.OPERATE_INSERT);
		baseDao.save(gu);
		return gu;
	}

	@Override
	public GreenUser queryGreenUserByUuid(String userId) throws GreenException {
		if (userId == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "queryGreenUserByUuid", "userId", "为null");
		GreenUser initUser = baseDao.findById(userId);
		if (initUser == null) {
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "queryGreenUserByUuid", "initUser", "为null");
		}
		return initUser;
	}

	@Override
	public GreenUser updateGreenUser(GreenUser user) throws GreenException {
		if (user == null || user.getUuid() == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "updateGreenUser-comm", "user||user.getUuid", "为null");
		GreenUser initUser = baseDao.findById(user.getUuid());
		if (initUser == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "updateGreenUser-comm", "initUser", "为null");
		if (user.getName() != null)
			initUser.setName(user.getName());
		if (user.getAddress() != null)
			initUser.setAddress(user.getAddress());
		if (user.getEmail() != null)
			initUser.setEmail(user.getEmail());
		if (user.getIdentityId() != null)
			initUser.setIdentityId(user.getIdentityId());
		if (user.getMarriage() != null)
			initUser.setMarriage(user.getMarriage());
		user.setOperate(OperateType.OPERATE_UPDATE);
		baseDao.save(user);
		return user;
	}

	@Override
	public GreenUser updateGreenUser(String userId, String phone) throws GreenException {
		if (userId == null || phone == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "updateGreenUser-phone", "userId||phone", "为null");
		GreenUser initUser = baseDao.findById(userId);
		if (initUser == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "updateGreenUser-phone", "initUser", "为null");

		if (phone.equals(initUser.getPhone())) {
			log.debug("更改的手机号跟原来的相同，不做操作！");
			return initUser;
		} else {
			List<GreenUser> userList = queryGreenUserMultiPara(null, phone, null, null, null, null, null).getResultlist();
			if (userList != null && userList.size() > 0)
				throw new GreenException("comm.00.00", "GreenUserServiceImpl", "updateGreenUser-phone", "要更改的手机号", "已注册");
			initUser.setPhone(phone);
			initUser.setOperate(OperateType.OPERATE_UPDATE);
			baseDao.save(initUser);
			return initUser;
		}

	}

	@Override
	public void delGreenUser(String userId) throws GreenException {
		// TODO Auto-generated method stub

	}

	@Override
	public QueryResult<GreenUser> queryGreenUserMultiPara(String name, String phone, String email, String sex, String identifyId, Integer marriage, String address)
			throws GreenException {
		ICriterion<GreenUser> crit = SystemCriterionSupport.GetInstance(GreenUser.class);
		if (!StringUtil.isNull(name)) {
			crit.AddCondition("name", ConditionType.EQUAL, name);
		}
		if (!StringUtil.isNull(phone)) {
			crit.AddCondition("phone", ConditionType.EQUAL, phone);
		}
		if (!StringUtil.isNull(email)) {
			crit.AddCondition("email", ConditionType.EQUAL, email);
		}
		if (!StringUtil.isNull(sex)) {
			crit.AddCondition("sex", ConditionType.EQUAL, sex);
		}
		if (!StringUtil.isNull(identifyId)) {
			crit.AddCondition("identityId", ConditionType.EQUAL, identifyId);
		}
		if (marriage != null) {
			crit.AddCondition("marriage", ConditionType.EQUAL, marriage);
		}
		if (!StringUtil.isNull(address)) {
			crit.AddCondition("address", ConditionType.LIKE, address);
		}
		return baseDao.queryPageResult(crit, PageHandler.getPage(), PageHandler.getPageSize());
	}

	@Override
	public void resetPassword(String userId, String password) throws GreenException {
		if (userId == null || password == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "resetPassword", "userId||password", "为null");
		GreenUser initUser = baseDao.findById(userId);
		if (initUser == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "resetPassword", "initUser", "为null");
		initUser.setPassword(password);
		initUser.setOperate(OperateType.OPERATE_UPDATE);
		baseDao.save(initUser);
	}

	@Override
	public int checkGreenUser(String phone, String password) throws GreenException {
		if (phone == null || password == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "checkGreenUser", "phone||password", "为null");
		List<GreenUser> userList = queryGreenUserMultiPara(null, phone, null, null, null, null, null).getResultlist();
		if (userList == null || userList.size() == 0)
			return 2;
		if (userList.get(0).getPassword().equals(password)) {
			return 1;
		} else {
			return 3;
		}
	}

	@Override
	public GreenUser queryGreenUserByPhone(String phone) throws GreenException {
		if (phone == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "queryGreenUserByPhone", "phone", "为null");
		List<GreenUser> userList = queryGreenUserMultiPara(null, phone, null, null, null, null, null).getResultlist();
		if (userList == null || userList.size() == 0)
			return null;
		if (userList.size() > 1)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "queryGreenUserByPhone", "userList.size()", "超过1个，有重复phone！");
		return userList.get(0);
	}

	@Override
	public boolean isPhoneUsable(String phone) throws GreenException {
		if (phone == null)
			throw new GreenException("comm.00.00", "GreenUserServiceImpl", "isPhoneUsable", "phone", "为null");
		List<GreenUser> userList = queryGreenUserMultiPara(null, phone, null, null, null, null, null).getResultlist();
		if (userList == null || userList.size() == 0)
			return true;
		return false;
	}

}
