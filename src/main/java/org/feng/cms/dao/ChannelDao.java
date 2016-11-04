package org.feng.cms.dao;

import java.util.List;

import org.feng.basic.dao.BaseDao;
import org.feng.cms.model.Channel;
import org.feng.cms.model.ChannelTree;
import org.springframework.stereotype.Repository;

@Repository("channelDao")
public class ChannelDao extends BaseDao<Channel> implements IChannelDao {
	public static void initTreeNode(List<ChannelTree> cts) {
		cts.add(0, new ChannelTree(Channel.ROOT_ID,Channel.ROOT_NAME, -1));
		for(ChannelTree ct:cts){
			if(ct.getPid()==null)
				ct.setPid(0);
		}
	}

	@Override
	public List<Channel> listByParent(Integer pid) {
		String hql = "select c from Channel c left join fetch c.parent cp where cp.id ="
				+pid+"order by c.orders";
		if(pid ==null||pid==0)
			hql = "select c from Channel c where c.parent is null order by orders";
		return this.list(hql);
	}

	@Override
	public int getMaxOrderByParent(Integer pid) {
		String hql ="select max(c.orders) from Channel c where c.parent.id ="+pid;
		if(pid ==null || pid ==0)
			hql ="select max(c.orders) from Channel c where c.parent is null";
		Object obj = this.queryObject(hql);
		if(obj==null) return 0;
		return (Integer)obj;
	}

	@Override
	public List<ChannelTree> generateTree() {
		String sql = "select id,name,pid from t_channel order by orders";
		List<ChannelTree> cts = this.listBySql(sql, ChannelTree.class, false);
		initTreeNode(cts);
		return cts;
	}



	@Override
	public List<ChannelTree> generateTreeByParent(Integer pid) {
		String sql ;
		if(pid==null ||pid==0){
			sql = "select id,name,pid from t_channel where pid is null order by orders";
			return this.listBySql(sql, ChannelTree.class, false);
		}else{
			sql = "select id,name,pid from t_channel where pid = "+pid+"order by orders";
			return this.listBySql(sql, ChannelTree.class, false);
		}
		
	}

	@Override
	public void updateSort(Integer[] ids) {
		int index = 1;
		String hql = "update Channel c set c.orders =? where c.id =?";
		for(Integer id:ids){
			this.updateByHql(hql, new Object[]{index++,id});
		}
	}

}
