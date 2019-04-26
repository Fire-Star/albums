/**
 * 
 */
package cn.project.albumss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.project.albumss.mybatis.Page;
import cn.project.albumss.pojo.Post;

/**
 * @author 陈超
 *
 */
public interface PostDao {
	
	/**
	 * 获取所有的帖子-分页 --根据点赞数排序
	 * @return
	 */
	public List<Post> getAllPost(Page page);
	/**
	 * 获取所有的帖子-分页 --根据点赞数排序
	 * @return
	 */
	public List<Post> getAllPostByTime(Page page);
	
	/**
	 * 分页获取所有点赞帖子
	 * @param page
	 * @return
	 */
	List<Post> getAllPostIsLove(Page page);
	
	/**
	 * 搜索帖子功能
	 * @param page
	 * @return
	 */
	List<Post> searchPost(Page page);
	
	/**
	 * 根据openId 分页查询帖子
	 * @return
	 */
	public List<Post> getPostByOpenId(Page page);
	
	/**
	 * 新增帖子
	 * @param post
	 */
	public void addPost(Post post);
	/**
	 * 执行点赞数更新+1操作
	 * @param openId
	 */
	public void addLoveNum(String postId);
	/**
	 * 根据Id查询点赞数
	 * @param id
	 * @return
	 */
	public int queryLoveNum(int id);
	/**
	 * 获取所有帖子第一张图片
	 * @return
	 */
	public List<String> getAllFirstPic();
	
	/**
	 * 获取个人所有帖子的第一张图片
	 */
	public List<String> getFirstPicByOpenId(String openId);
	
	/**
	 * 删除个人帖子
	 * @param openId
	 * @param id
	 */
	public void deleteByOpenId(@Param("id")String id);
	
	/**
	 * 增加关系表记录
	 * @param openId
	 * @param postId
	 */
	void addUserLovePost(@Param("openId")String openId,@Param("postId")String postId);
	
	/**
	 * 减小帖子点赞数目
	 * @param postId
	 */
	void reduceLoveNum(String postId);
	
	/**
	 * 删除关系表记录
	 * @param openId
	 * @param postId
	 */
	void deleteUserLovePost(@Param("openId")String openId,@Param("postId")String postId);
	
	/**
	 * 判断是否存在用户点赞记录
	 * @param openId
	 * @param postId
	 * @return
	 */
	int isExitUserLovePost(@Param("openId")String openId,@Param("postId")String postId);
	
	/**
	 * 根据帖子ID查询帖子图片
	 * @param id
	 * @return
	 */
	String findImageById(String id);
	
	public Integer getPostById(@Param("postId")String postId,@Param("openid")String openid);
}
