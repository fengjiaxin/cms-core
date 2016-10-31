package org.feng.cms.dao;

import java.util.List;

import org.feng.basic.dao.IBaseDao;
import org.feng.cms.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
	public List<Role> listRole();
	public void deleteRoleUsers(int rid);
}
