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
 * @author �³�
 * 
 */
@Service
public class OperationService {

	@Autowired
	private OperationDao dao;
	private emojiConvert emoji = new emojiConvert();

	/**
	 * ��ӹ�ע
	 * 
	 * @param openId
	 * @param fansOpenId
	 */
	public void addFollow(String openId, String fansOpenId) {
		dao.addFollow(openId, fansOpenId);
		// ����ע�߷�˿����һ
		dao.addFansNum(fansOpenId);
		// ��ע�߹�ע����һ
		dao.addFollowNum(openId);
		// ������ע��Ϣ֪ͨ
		MessagePo po = new MessagePo();
		po.setFrom_openId(openId);
		po.setTo_openId(fansOpenId);
		po.setType("2");
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		po.setTime(sFormat.format(new Date()));
		dao.addMessageInfo(po);
	}

	/**
	 * �ж��Ƿ���ڹ�ע��ϵ
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
	 * ɾ����ע
	 * 
	 * @param openId
	 * @param fansOpenId
	 */
	public void deleteReduceFollow(String openId, String fansOpenId) {
		dao.reduceFollow(openId, fansOpenId);
		// ����ע�߷�˿����һ
		dao.reduceFansNum(fansOpenId);
		// ��ע�߹�ע����һ
		dao.reduceFollowNum(openId);
		// ɾ����ע֪ͨ
		MessagePo po = new MessagePo();
		po.setFrom_openId(openId);
		po.setTo_openId(fansOpenId);
		po.setType("2");
		dao.deleteMessageInfo(po);
	}

	/**
	 * ��ҳ��ѯ������Ϣ
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
		// ��װ������Ϣ
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
			
			//emoji ת��
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
	 * ������Ϣ
	 * 
	 * @param po
	 */
	public void addSendMessage(MessagePo po) {
		// ��װ��Ϣ
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		po.setTime(sFormat.format(new Date()));
		po.setType("3");
		dao.addMessageInfo(po);
	}

	/**
	 * �޸�δ��״̬
	 * 
	 * @param id
	 */
	public void updateIsRead(String id) {
		dao.updateIsRead(id);
	}

	/**
	 * ��ѯ��˿
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> findQueryMessageByOpenIdFans(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFans(page);
		// ��װ������Ϣ
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
			//emoji ת��
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
	 * ��ѯ��˿ ���Ǳ���
	 * @param po
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> findQueryMessageByOpenIdFansNoMy(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFans(page);
		// ��װ������Ϣ
		for (MessagePo messagePo : list) {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = sFormat.parse(messagePo.getTime());
			String d = RelativeDateFormat.format(date);
			messagePo.setTime(d);
		}
		JSONArray array=JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			String fromOpenId=array.getJSONObject(i).getString("from_openId");  
			int count1 = dao.isFollow(po.getFrom_openId(),fromOpenId);  //po.getFrom_openId()ΪAPP�û�
			int count2 =dao.isFollow(fromOpenId, po.getFrom_openId());
			if (count1==0&&count2==0) {
				array.getJSONObject(i).put("isFollowFans", 0);  //���˲������κι�ע��ϵ
			}else if(count1==0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 1);  //app�û�û�й�ע���ˣ��������˹�ע��APP�û�
			}else if(count1!=0&&count2==0){
				array.getJSONObject(i).put("isFollowFans", 2);  //APP�û���ע�����ˣ�����û�й�עAPP�û�
			}else if(count1!=0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 3);  //APP�û���ע�����ˣ�����Ҳ��עAPP�û�
			}
			//emoji ת��
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
	 * ��ѯ��ע
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findQueryMessageByOpenIdFollow(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFollow(page);
		// ��װ������Ϣ
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
			//emoji ת��
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
	 * ��ѯ��ע   ���Ǳ���
	 * @param po
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findQueryMessageByOpenIdFollowNoMy(MessagePo po) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(po);
		List<MessagePo> list = dao.queryMessageByOpenIdFollow(page);
		// ��װ������Ϣ
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
				array.getJSONObject(i).put("isFollowFans", 0);  //���˲������κι�ע��ϵ
			}else if(count1==0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 1);  //app�û�û�й�ע���ˣ��������˹�ע��APP�û�
			}else if(count1!=0&&count2==0){
				array.getJSONObject(i).put("isFollowFans", 2);  //APP�û���ע�����ˣ�����û�й�עAPP�û�
			}else if(count1!=0&&count2!=0){
				array.getJSONObject(i).put("isFollowFans", 3);  //APP�û���ע�����ˣ�����Ҳ��עAPP�û�
			}
			//emoji ת��
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
	 * �û�δ����Ϣ����
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
	 * ���ĳ�����Ϣ�����Ϊ�Ѷ�
	 * @param to_openId
	 */
	public void updateIsReading(String to_openId) {
		dao.updateIsReading(to_openId);
	}
	
	/**
	 * �Ƿ����
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
