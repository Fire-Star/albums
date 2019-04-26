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
 * @author �³�
 *
 */
public interface OperationDao {

	/**
	 * ��ӹ�ע
	 * @param openId
	 * @param fansOpenId
	 */
	void addFollow(@Param("openId")String openId,@Param("fansOpenId")String fansOpenId);
	
	/**
	 * ���ӷ�˿��
	 * @param fansOpenId
	 */
	void addFansNum(String fansOpenId);
	
	void reduceFansNum(String fansOpenId);
	
	/**
	 * ���ӹ�ע��
	 * @param openId
	 */
	void addFollowNum(String openId);
	
	/**
	 * ���ٹ�ע��
	 * @param openId
	 */
	void reduceFollowNum(String openId);
	
	/**
	 * �ж��Ƿ���ڹ�ע��ϵ
	 * @param openId
	 * @param fansOpenId
	 * @return
	 */
	int isFollow(@Param("openId")String openId,@Param("fansOpenId")String fansOpenId);
	
	/**
	 * ɾ����ע
	 * @param openId
	 * @param fansOpenId
	 */
	void reduceFollow(@Param("openId")String openId,@Param("fansOpenId")String fansOpenId);
	
	/**
	 * ������Ϣ
	 * @param po
	 */
	void addMessageInfo(MessagePo po);
	
	/**
	 * ��ҳ��ѯ������Ϣ
	 * @param page
	 * @return
	 */
	List<MessagePo> queryMessageInfos(Page page);
	
	/**
	 * ɾ��֪ͨ
	 * @param po
	 */
	void deleteMessageInfo(MessagePo po);
	
	/**
	 * �޸�δ��״̬
	 * @param id
	 */
	void updateIsRead(String id);
	
	/**
	 * ��ѯ��˿
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	List<MessagePo> queryMessageByOpenIdFans(Page page);
	
	/**
	 * ��ѯ��ע
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	List<MessagePo> queryMessageByOpenIdFollow(Page page);
	
	/**
	 * �û�δ����Ϣ����
	 * @param to_openId
	 * @return
	 */
	int queryIsReading(String to_openId);
	
	/**
	 * ���ĳ�����Ϣ�����Ϊ�Ѷ�
	 * @param to_openId
	 */
	void updateIsReading(String to_openId);
	
	/**
	 * �Ƿ����
	 * @param openId
	 * @param id
	 * @return
	 */
	int queryIslove(@Param("openId")String openId,@Param("id")String id);
}
