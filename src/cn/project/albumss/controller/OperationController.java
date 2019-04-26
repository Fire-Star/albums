/**
 * 
 */
package cn.project.albumss.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.project.albumss.pojo.MessagePo;
import cn.project.albumss.service.OperationService;

/**
 * @author 陈超
 * 主要处理业务逻辑
 */
@Controller
public class OperationController {

	@Autowired
	private OperationService service;
	
	/**
	 * 添加关注
	 * @param openId
	 * @param fansOpenId
	 */
	@RequestMapping(value="operation_addFollow")
	@ResponseBody
	public void addFollow(String openId,String fansOpenId) {
		service.addFollow(openId, fansOpenId);
	}
	
	/**
	 * 判断是否存在关注关系
	 * @param openId
	 * @param fansOpenId
	 * @return
	 */
	@RequestMapping(value="operation_isFollow")
	@ResponseBody
	public Map<String, Object> isFollow(String openId,String fansOpenId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map=service.getIsFollow(openId, fansOpenId);
		return map;
	}
	
	/**
	 * 删除关注
	 * @param openId
	 * @param fansOpenId
	 */
	@RequestMapping(value="operation_reduceFollow")
	@ResponseBody
	public void reduceFollow(String openId,String fansOpenId) {
		service.deleteReduceFollow(openId, fansOpenId);
	}
	
	/**
	 * 分页查询所有消息
	 * @param page
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="operation_queryMessageInfos")
	@ResponseBody
	public Map<String, Object> queryMessageInfos(MessagePo po) throws ParseException {
		Map<String, Object> map =new HashMap<>();
		map=service.findMessageInfos(po);
		return map;
	}
	
	/**
	 * 发送消息
	 * @param po
	 */
	@RequestMapping(value="operation_sendMessage")
	@ResponseBody
	public void sendMessage(MessagePo po) {
		service.addSendMessage(po);
	}
	
	/**
	 * 修改未读状态
	 * @param id
	 */
	@RequestMapping(value="operation_updateIsRead")
	@ResponseBody
	public void updateIsRead(String id) {
		service.updateIsRead(id);
	}
	
	/**
	 * 查询粉丝
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="operation_queryMessageByOpenId_fans")
	@ResponseBody
	public Map<String, Object> queryMessageByOpenIdFans(MessagePo po) throws ParseException {
		return service.findQueryMessageByOpenIdFans(po);
	}
	
	/**
	 * 查询粉丝 不是本事
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="operation_queryMessageByOpenId_fans_nomy")
	@ResponseBody
	public Map<String, Object> queryMessageByOpenIdFansNoMy(MessagePo po) throws ParseException {
		return service.findQueryMessageByOpenIdFansNoMy(po);
	}
	
	/**
	 * 查询关注
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="operation_queryMessageByOpenId_follow")
	@ResponseBody
	public Map<String, Object> queryMessageByOpenIdFollow(MessagePo po) throws ParseException{
		return service.findQueryMessageByOpenIdFollow(po);
	}
	
	/**
	 * 查询关注  不是本身
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="operation_queryMessageByOpenId_follow_nomy")
	@ResponseBody
	public Map<String, Object> queryMessageByOpenIdFollowNoMy(MessagePo po) throws ParseException{
		return service.findQueryMessageByOpenIdFollowNoMy(po);
	}
	
	/**
	 * 用户未读消息数量
	 * @param to_openId
	 * @return
	 */
	@RequestMapping(value="operation_queryIsReading")
	@ResponseBody
	public Map<String, Object> queryIsReading(String to_openId) {
		return service.findQueryIsReading(to_openId);
	}
	
	/**
	 * 更改除了消息意外的为已读
	 * @param to_openId
	 */
	@RequestMapping(value="operation_updateIsReading")
	@ResponseBody
	public void updateIsReading(String to_openId) {
		service.updateIsReading(to_openId);
	}
	
	/**
	 * 是否点赞
	 * @param openId
	 * @param id
	 * @return
	 */
	@RequestMapping(value="operation_queryIslove")
	@ResponseBody
	public Map<String, Object> queryIslove(@RequestParam(value="openId")String openId,@RequestParam(value="id")String id) {
		return service.queryIslove(openId, id);
	}
}
