/**
 * 
 */
package cn.project.albumss.mapper;

import cn.project.albumss.pojo.Users;

/**
 * @author 陈超
 *
 */
public interface UserDao {

	
	/**
	 * 根据openId,返回用户
	 * @param code
	 * @return
	 */
	public Users findUserByOpenId(String openId);
	/**
	 * 根据openId，判断用户是否存在
	 * @param openId
	 * @return
	 */
	public Integer isExist(String openId);
	/**
	 * 新增用户
	 * @param user
	 */
	public void addUser(Users user);
	/**
	 * 根据openId,获取先前的图片
	 * @param openId
	 * @return
	 */
	public String findImgByOpenId(String openId);
	/**
	 * 修改用户
	 * @param user
	 */
	public void modifyUser(Users user);
	/**
	 * 根据openId，查询用户发帖数
	 * @param openId
	 * @return
	 */
	public int queryPostNum(String openId);
	
	/**
	 * 根据openId查询背景图片
	 * @param openId
	 * @return
	 */
	String queryBgImgById(String openId);
	
	/**
	 * 修改用户类型
	 * @param users
	 */
	void updateUserType(Users users);
}
