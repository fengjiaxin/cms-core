package org.feng.cms.dao;

import java.util.List;

import org.feng.basic.dao.IBaseDao;
import org.feng.cms.model.Channel;
import org.feng.cms.model.ChannelTree;

public interface IChannelDao extends IBaseDao<Channel> {
	/**
	 * 根据父id获取所有的子栏目
	 * @param pid
	 * @return
	 * @return: List<Channel>
	 */
	public List<Channel> listByParent(Integer pid);
	/**
	 * 获取子栏目的最大的排序号
	 * @param pid
	 * @return
	 * @return: int
	 */
	public int getMaxOrderByParent(Integer pid);
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return
	 * @return: List<ChannelTree>
	 */
	public List<ChannelTree> generateTree();
	/**
	 * 根据父类对象获取子栏目，并且生成树列表
	 * @param pid
	 * @return
	 * @return: List<ChannelTree>
	 */
	public List<ChannelTree> generateTreeByParent(Integer pid);
	
	public void updateSort(Integer[] ids);

	

}
