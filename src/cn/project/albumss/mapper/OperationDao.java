/**
 * 
 */
package cn.project.albumss.mapper;

import java.text.ParseException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;

import cn.project.albumss.mybatis.Page;
import cn.project.albumss.pojo.MessagePo;

/**
 * @author 陈超
 *
 */
public interface OperationDao {

	/**
	 * 添加关注
	 * @param openId
	 * @param fansOpenId
	 */
	void addFollow(@Param("openId")String openId,@Param("fansOpenId")String fansOpenId);
	
	/**
	 * 增加粉丝数
	 * @param fansOpenId
	 */
	void addFansNum(String fansOpenId);
	
	void reduceFansNum(String fansOpenId);
	
	/**
	 * 增加关注数
	 * @param openId
	 */
	void addFollowNum(String openId);
	
	/**
	 * 减少关注数
	 * @param openId
	 */
	void reduceFollowNum(String openId);
	
	/**
	 * 判断是否存在关注关系
	 * @param openId
	 * @param fansOpenId
	 * @return
	 */
	int isFollow(@Param("openId")String openId,@Param("fansOpenId")String fansOpenId);
	
	/**
	 * 删除关注
	 * @param openId
	 * @param fansOpenId
	 */
	void reduceFollow(@Param("openId")String openId,@Param("fansOpenId")String fansOpenId);
	
	/**
	 * 新增消息
	 * @param po
	 */
	void addMessageInfo(MessagePo po);
	
	/**
	 * 分页查询所有消息
	 * @param page
	 * @return
	 */
	List<MessagePo> queryMessageInfos(Page page);
	
	/**
	 * 删除通知
	 * @param po
	 */
	void deleteMessageInfo(MessagePo po);
	
	/**
	 * 修改未读状态
	 * @param id
	 */
	void updateIsRead(String id);
	
	/**
	 * 查询粉丝
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	List<MessagePo> queryMessageByOpenIdFans(Page page);
	
	/**
	 * 查询关注
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	List<MessagePo> queryMessageByOpenIdFollow(Page page);
	
	/**
	 * 用户未读消息数量
	 * @param to_openId
	 * @return
	 */
	int queryIsReading(String to_openId);
	
	/**
	 * 更改除了消息意外的为已读
	 * @param to_openId
	 */
	void updateIsReading(String to_openId);
	
	/**
	 * 是否点赞
	 * @param openId
	 * @param id
	 * @return
	 */
	int queryIslove(@Param("openId")String openId,@Param("id")String id);
}
