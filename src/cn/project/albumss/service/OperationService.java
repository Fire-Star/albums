/**
 * 
 */
package cn.project.albumss.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.project.albumss.mapper.OperationDao;
import cn.project.albumss.mybatis.Page;
import cn.project.albumss.pojo.MessagePo;
import cn.project.albumss.utils.RelativeDateFormat;
import cn.project.albumss.utils.emojiConvert;

/**
 * @author 陈超
 * 
 */
@Service
public class OperationService {

	@Autowired
	private OperationDao dao;
	private emojiConvert emoji = new emojiConvert();

	/**
	 * 添加关注
	 * 
	 * @param openId
	 * @param fansOpenId
	 */
	public void addFollow(String openId, String fansOpenId) {
		dao.addFollow(openId, fansOpenId);
		// 被关注者粉丝数加一
		dao.addFansNum(fansOpenId);
		// 关注者关注数加一
		dao.addFollowNum(openId);
		// 新增关注消息通知
		MessagePo po = new MessagePo();
		po.setFrom_openId(openId);
		po.setTo_openId(fansOpenId);
		po.setType("2");
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		po.setTime(sFormat.format(new Date()));
		dao.addMessageInfo(po);
	}

	/**
	 * 判断是否存在关注关系
	 * 
	 * @param openId
	 * @param fansOpenId
	 * @return
	 */
	public Map<String, Object> getIsFollow(String openId, String fansOpenId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = dao.isFollow(openId, fansOpenId);
		map.put("isFollow", count);
		return map;
	}

	/**
	 * 删除关注
	 * 
	 * @param openId
	 * @param fansOpenId
	 */
	public void deleteReduceFollow(String openId, String fansOpenId) {
		dao.reduceFollow(openId, fansOpenId);
		// 被关注者粉丝数减一
		dao.reduceFansNum(fansOpenId);
		// 关注者关注数减一
		dao.reduceFollowNum(openId);
		// 删除关注通知
		MessagePo po = new MessagePo();
		po.setFrom_openId(openId);
		po.setTo_openId(fansOpenId);
		po.setType("2");
		dao.deleteMessageInfo(po);
	}

	/**
	 * 分页查询所有消息
	 * 
	 * @param page
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findMessageInfos(MessagePo po)
			throws ParseException {
		
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageInfos(page);
		// 封装返回信息
		for (MessagePo messagePo : list) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = sFormat.parse(messagePo.getTime());
			String d = RelativeDateFormat.format(date);
			messagePo.setTime(d);
		}
		
		JSONArray array=JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			String fromOpenId=array.getJSONObject(i).getString("from_openId");
			int count = dao.isFollow(po.getTo_openId(),fromOpenId);
			if (count==0) {
				array.getJSONObject(i).put("isFollowFans", 0);
			}else {
				array.getJSONObject(i).put("isFollowFans", 1);
			}
			
			//emoji 转化
			String snickName = array.getJSONObject(i).getString("nickName");
			try {
				array.getJSONObject(i).put("nickName", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("nickName", snickName);
			}
		}
		
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}

	/**
	 * 发送消息
	 * 
	 * @param po
	 */
	public void addSendMessage(MessagePo po) {
		// 封装信息
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		po.setTime(sFormat.format(new Date()));
		po.setType("3");
		dao.addMessageInfo(po);
	}

	/**
	 * 修改未读状态
	 * 
	 * @param id
	 */
	public void updateIsRead(String id) {
		dao.updateIsRead(id);
	}

	/**
	 * 查询粉丝
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> findQueryMessageByOpenIdFans(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFans(page);
		// 封装返回信息
		for (MessagePo messagePo : list) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = sFormat.parse(messagePo.getTime());
			String d = RelativeDateFormat.format(date);
			messagePo.setTime(d);
		}
		JSONArray array=JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			String fromOpenId=array.getJSONObject(i).getString("from_openId");
			int count = dao.isFollow(po.getFrom_openId(),fromOpenId);
			if (count==0) {
				array.getJSONObject(i).put("isFollowFans", 0);
			}else {
				array.getJSONObject(i).put("isFollowFans", 1);
			}
			//emoji 转化
			String snickName = array.getJSONObject(i).getString("nickName");
			try {
				array.getJSONObject(i).put("nickName", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("nickName", snickName);
			}
		}
		
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}
	
	/**
	 * 查询粉丝 不是本身
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> findQueryMessageByOpenIdFansNoMy(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFans(page);
		// 封装返回信息
		for (MessagePo messagePo : list) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = sFormat.parse(messagePo.getTime());
			String d = RelativeDateFormat.format(date);
			messagePo.setTime(d);
		}
		JSONArray array=JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			String fromOpenId=array.getJSONObject(i).getString("from_openId");  
			int count1 = dao.isFollow(po.getFrom_openId(),fromOpenId);  //po.getFrom_openId()为APP用户
			int count2 =dao.isFollow(fromOpenId, po.getFrom_openId());
			if (count1==0&&count2==0) {
				array.getJSONObject(i).put("isFollowFans", 0);  //两人不存在任何关注关系
			}else if(count1==0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 1);  //app用户没有关注他人，但是他人关注了APP用户
			}else if(count1!=0&&count2==0){
				array.getJSONObject(i).put("isFollowFans", 2);  //APP用户关注了他人，他人没有关注APP用户
			}else if(count1!=0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 3);  //APP用户关注了他人，他人也关注APP用户
			}
			//emoji 转化
			String snickName = array.getJSONObject(i).getString("nickName");
			try {
				array.getJSONObject(i).put("nickName", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("nickName", snickName);
			}
		}
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}
	
	/**
	 * 查询关注
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findQueryMessageByOpenIdFollow(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFollow(page);
		// 封装返回信息
		for (MessagePo messagePo : list) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = sFormat.parse(messagePo.getTime());
			String d = RelativeDateFormat.format(date);
			messagePo.setTime(d);
		}
		JSONArray array=JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			String toOpenId=array.getJSONObject(i).getString("to_openId");
			int count = dao.isFollow(toOpenId, po.getFrom_openId());
			if (count==0) {
				array.getJSONObject(i).put("isFollowFans", 0);
			}else {
				array.getJSONObject(i).put("isFollowFans", 1);
			}
			//emoji 转化
			String snickName = array.getJSONObject(i).getString("nickName");
			try {
				array.getJSONObject(i).put("nickName", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("nickName", snickName);
			}
		}
		
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}
	
	/**
	 * 查询关注   不是本身
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findQueryMessageByOpenIdFollowNoMy(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFollow(page);
		// 封装返回信息
		for (MessagePo messagePo : list) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = sFormat.parse(messagePo.getTime());
			String d = RelativeDateFormat.format(date);
			messagePo.setTime(d);
		}
		JSONArray array=JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			String toOpenId=array.getJSONObject(i).getString("to_openId");
			int count1=dao.isFollow(po.getTo_openId(), toOpenId);
			int count2=dao.isFollow(toOpenId, po.getTo_openId());
			if (count1==0&&count2==0) {
				array.getJSONObject(i).put("isFollowFans", 0);  //两人不存在任何关注关系
			}else if(count1==0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 1);  //app用户没有关注他人，但是他人关注了APP用户
			}else if(count1!=0&&count2==0){
				array.getJSONObject(i).put("isFollowFans", 2);  //APP用户关注了他人，他人没有关注APP用户
			}else if(count1!=0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 3);  //APP用户关注了他人，他人也关注APP用户
			}
			//emoji 转化
			String snickName = array.getJSONObject(i).getString("nickName");
			try {
				array.getJSONObject(i).put("nickName", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("nickName", snickName);
			}
		}
		
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}
	
	/**
	 * 用户未读消息数量
	 * @param to_openId
	 * @return
	 */
	public Map<String, Object> findQueryIsReading(String to_openId) {
		Map<String, Object> map=new HashMap<>();
		int count = dao.queryIsReading(to_openId);
		map.put("count",count);
		return map;
	}
	
	/**
	 * 更改除了消息意外的为已读
	 * @param to_openId
	 */
	public void updateIsReading(String to_openId) {
		dao.updateIsReading(to_openId);
	}
	
	/**
	 * 是否点赞
	 * @param openId
	 * @param id
	 * @return
	 */
	public Map<String, Object> queryIslove(String openId,String id) {
		Map<String, Object> map=new HashMap<>();
		int num = dao.queryIslove(openId, id);
		map.put("num", num);
		return map;
	}
}
