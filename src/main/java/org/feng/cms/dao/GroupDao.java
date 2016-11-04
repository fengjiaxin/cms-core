package org.feng.cms.dao;

import java.util.List;

import org.feng.basic.dao.BaseDao;
import org.feng.basic.model.Pager;
import org.feng.cms.model.Channel;
import org.feng.cms.model.ChannelTree;
import org.feng.cms.model.Group;
import org.feng.cms.model.GroupChannel;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {

	@Override
	public List<Group> listGroup() {
		return this.list("from Group");
	}

	@Override
	public Pager<Group> findGroup() {
		return this.find("from Group");
	}

	@Override
	public void deleteGroupUsers(int gid) {
		this.updateByHql("delete UserGroup ug where ug.group.id=?",gid);
	}

	@Override
	public void addGroupChannel(Group group, Channel channel) {
		GroupChannel gc = this.loadGroupChannel(group.getId(), channel.getId());
		if(gc!=null) return;
		gc = new GroupChannel();
		gc.setGroup(group);
		gc.setChannel(channel);
		this.getSession().save(gc);
	}

	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
		String hql = "from GroupChannel where group.id =? and channel.id =?";
		return (GroupChannel) this.queryObject(hql, new Object[]{gid,cid});
	}

	@Override
	public void clearGroupChannel(int gid) {
		String hql = "delete GroupChannle gc where gc.group.id = ?";
		this.updateByHql(hql, gid);
	}

	@Override
	public void deleteGroupChannel(int gid, int cid) {
		String hql = "delete GroupChannel gc where gc.group.id = ? and gc.channel.id =?";
		this.updateByHql(hql, gid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		String hql = "select gc.channel.id from GroupChannel gc where gc.group.id =?";
		
		return this.getSession().createQuery(hql).setParameter(0, gid).list();
	}

	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
		String sql = "select c.id as id,c.name as name,c.pid ad pid from"+
					"t_group_channel gc legt join t_channel c on (gc.c_id =c.id)"+
					"where gc.g_id =?";
		List<ChannelTree> cts = this.listBySql(sql, gid, ChannelTree.class, false);
		ChannelDao.initTreeNode(cts);
		return cts;
	}

	@Override
	public List<ChannelTree> generateUserChannelTree(int uid) {
		String sql = "select distinct c.id as id,c.name as name,c.pid as pid from"+
					"t_group_channel gc left join t_channel c on(gc.c_id = c.id) "+
					"left join t_user_group ug on(ug.g_id = gc.g_id)"+
					"where ug.u_id = ?";
		List<ChannelTree> cts = this.listBySql(sql, uid, ChannelTree.class, false);
		ChannelDao.initTreeNode(cts);
		return cts;
	}

}
