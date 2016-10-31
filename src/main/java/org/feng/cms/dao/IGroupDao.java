package org.feng.cms.dao;

import java.util.List;

import org.feng.basic.dao.IBaseDao;
import org.feng.basic.model.Pager;
import org.feng.cms.model.Group;

public interface IGroupDao extends IBaseDao<Group> {
	public List<Group> listGroup();
	public Pager<Group> findGroup();
	public void deleteGroupUsers(int gid);

}
