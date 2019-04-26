/**
 * 
 */
package cn.project.albumss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.project.albumss.mybatis.Page;
import cn.project.albumss.pojo.Post;

/**
 * @author �³�
 *
 */
public interface PostDao {
	
	/**
	 * ��ȡ���е�����-��ҳ --���ݵ���������
	 * @return
	 */
	public List<Post> getAllPost(Page page);
	/**
	 * ��ȡ���е�����-��ҳ --���ݵ���������
	 * @return
	 */
	public List<Post> getAllPostByTime(Page page);
	
	/**
	 * ��ҳ��ȡ���е�������
	 * @param page
	 * @return
	 */
	List<Post> getAllPostIsLove(Page page);
	
	/**
	 * �������ӹ���
	 * @param page
	 * @return
	 */
	List<Post> searchPost(Page page);
	
	/**
	 * ����openId ��ҳ��ѯ����
	 * @return
	 */
	public List<Post> getPostByOpenId(Page page);
	
	/**
	 * ��������
	 * @param post
	 */
	public void addPost(Post post);
	/**
	 * ִ�е���������+1����
	 * @param openId
	 */
	public void addLoveNum(String postId);
	/**
	 * ����Id��ѯ������
	 * @param id
	 * @return
	 */
	public int queryLoveNum(int id);
	/**
	 * ��ȡ�������ӵ�һ��ͼƬ
	 * @return
	 */
	public List<String> getAllFirstPic();
	
	/**
	 * ��ȡ�����������ӵĵ�һ��ͼƬ
	 */
	public List<String> getFirstPicByOpenId(String openId);
	
	/**
	 * ɾ����������
	 * @param openId
	 * @param id
	 */
	public void deleteByOpenId(@Param("id")String id);
	
	/**
	 * ���ӹ�ϵ���¼
	 * @param openId
	 * @param postId
	 */
	void addUserLovePost(@Param("openId")String openId,@Param("postId")String postId);
	
	/**
	 * ��С���ӵ�����Ŀ
	 * @param postId
	 */
	void reduceLoveNum(String postId);
	
	/**
	 * ɾ����ϵ���¼
	 * @param openId
	 * @param postId
	 */
	void deleteUserLovePost(@Param("openId")String openId,@Param("postId")String postId);
	
	/**
	 * �ж��Ƿ�����û����޼�¼
	 * @param openId
	 * @param postId
	 * @return
	 */
	int isExitUserLovePost(@Param("openId")String openId,@Param("postId")String postId);
	
	/**
	 * ��������ID��ѯ����ͼƬ
	 * @param id
	 * @return
	 */
	String findImageById(String id);
	
	public Integer getPostById(@Param("postId")String postId,@Param("openid")String openid);
}
