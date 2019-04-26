/**
 * 
 */
package cn.project.albumss.mapper;

import cn.project.albumss.pojo.Users;

/**
 * @author �³�
 *
 */
public interface UserDao {

	
	/**
	 * ����openId,�����û�
	 * @param code
	 * @return
	 */
	public Users findUserByOpenId(String openId);
	/**
	 * ����openId���ж��û��Ƿ����
	 * @param openId
	 * @return
	 */
	public Integer isExist(String openId);
	/**
	 * �����û�
	 * @param user
	 */
	public void addUser(Users user);
	/**
	 * ����openId,��ȡ��ǰ��ͼƬ
	 * @param openId
	 * @return
	 */
	public String findImgByOpenId(String openId);
	/**
	 * �޸��û�
	 * @param user
	 */
	public void modifyUser(Users user);
	/**
	 * ����openId����ѯ�û�������
	 * @param openId
	 * @return
	 */
	public int queryPostNum(String openId);
	
	/**
	 * ����openId��ѯ����ͼƬ
	 * @param openId
	 * @return
	 */
	String queryBgImgById(String openId);
	
	/**
	 * �޸��û�����
	 * @param users
	 */
	void updateUserType(Users users);
}
