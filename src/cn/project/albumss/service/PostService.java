/**
 * 
 */
package cn.project.albumss.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.project.albumss.mapper.OperationDao;
import cn.project.albumss.mapper.PostDao;
import cn.project.albumss.mybatis.Page;
import cn.project.albumss.pojo.MessagePo;
import cn.project.albumss.pojo.Post;
import cn.project.albumss.utils.RelativeDateFormat;
import cn.project.albumss.utils.UploadPicUtils;
import cn.project.albumss.utils.emojiConvert;
import cn.project.albumss.vo.BaseVo;

/**
 * @author 陈超
 * 
 */
@Service
public class PostService {

	@Autowired
	private PostDao dao;
	@Autowired
	private OperationDao operationDao;
	private emojiConvert emoji = new emojiConvert();
	/**
	 * 获取所有的帖子-分页 --根据点赞数排序
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findGetAllPost(Post post) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		String parentOpenId = post.getOpen_id();
		Page page = new Page(post);

		// 获取所有帖子
		List<Post> list = dao.getAllPost(page);

		// 格式化记录
		JSONArray array = JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = format.parse(jsonObject.getString("time"));
			String d = RelativeDateFormat.format(date);
			array.getJSONObject(i).put("time", d);
			
			//emoji 转化
			String snickName = jsonObject.getString("user_nick");
			try {
				array.getJSONObject(i).put("user_nick", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			} catch (NullPointerException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}

			String temp = (String) jsonObject.get("image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList = temp.split(",");
			JSONArray tempArray = new JSONArray();
			JSONObject tempObject = new JSONObject();
			for (int j = 0; j < imgList.length; j++) {
				tempObject.put("img", imgList[j]);
				tempArray.add(tempObject);
			}
			array.getJSONObject(i).put("imgs", tempArray);

			String temp1 = (String) jsonObject.get("scale_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList1 = temp1.split(",");
			JSONArray tempArray1 = new JSONArray();
			JSONObject tempObject1 = new JSONObject();
			for (int j = 0; j < imgList1.length; j++) {
				tempObject1.put("img", imgList1[j]);
				tempArray1.add(tempObject1);
			}
			array.getJSONObject(i).put("scaleImgs", tempArray1);

			String temp2 = (String) jsonObject.get("quality_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList2 = temp2.split(",");
			JSONArray tempArray2 = new JSONArray();
			JSONObject tempObject2 = new JSONObject();
			for (int j = 0; j < imgList2.length; j++) {
				tempObject2.put("img", imgList2[j]);
				tempArray2.add(tempObject2);
			}
			array.getJSONObject(i).put("qualityImgs", tempArray2);

			// 封装点赞情况
			String postId = array.getJSONObject(i).getString("id");
			int isExit = dao.isExitUserLovePost(parentOpenId, postId);
			array.getJSONObject(i).put("isLove", isExit);
		}
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}

	/**
	 * 获取所有的帖子 分页 根据时间排序
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findGetAllPostByTime(Post post)
			throws ParseException {
		Map<String, Object> map = new HashMap<>();
		String parentOpenId = post.getOpen_id();
		Page page = new Page(post);
		page.setOpenId(post.getOpen_id());
		// 获取所有帖子
		List<Post> list = dao.getAllPostByTime(page);
		// 格式化记录
		JSONArray array = JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = format.parse(jsonObject.getString("time"));
			String d = RelativeDateFormat.format(date);
			array.getJSONObject(i).put("time", d);
			
			//emoji 转化
			String snickName = jsonObject.getString("user_nick");
			try {
				array.getJSONObject(i).put("user_nick", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}catch (NullPointerException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}

			String temp = (String) jsonObject.get("image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList = temp.split(",");
			JSONArray tempArray = new JSONArray();
			JSONObject tempObject = new JSONObject();
			for (int j = 0; j < imgList.length; j++) {
				tempObject.put("img", imgList[j]);
				tempArray.add(tempObject);
			}
			array.getJSONObject(i).put("imgs", tempArray);

			String temp1 = (String) jsonObject.get("scale_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList1 = temp1.split(",");
			JSONArray tempArray1 = new JSONArray();
			JSONObject tempObject1 = new JSONObject();
			for (int j = 0; j < imgList1.length; j++) {
				tempObject1.put("img", imgList1[j]);
				tempArray1.add(tempObject1);
			}
			array.getJSONObject(i).put("scaleImgs", tempArray1);

			String temp2 = (String) jsonObject.get("quality_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList2 = temp2.split(",");
			JSONArray tempArray2 = new JSONArray();
			JSONObject tempObject2 = new JSONObject();
			for (int j = 0; j < imgList2.length; j++) {
				tempObject2.put("img", imgList2[j]);
				tempArray2.add(tempObject2);
			}
			array.getJSONObject(i).put("qualityImgs", tempArray2);

			// 封装点赞情况
			String postId = array.getJSONObject(i).getString("id");
			int isExit = dao.isExitUserLovePost(parentOpenId, postId);
			array.getJSONObject(i).put("isLove", isExit);
		}
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}

	/**
	 * 分页获取所有点赞帖子
	 * 
	 * @param page
	 * @return
	 */
	public Map<String, Object> getAllPostIsLove(Post post)
			throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(post);
		List<Post> lists = dao.getAllPostIsLove(page);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n\n");
		System.out.println(lists.size());
		System.out.println(lists.toString());
		// 去除集合中已经删除的帖子
		Iterator<Post> it = lists.iterator();
		while (it.hasNext()) {
			Post postTemp = (Post) it.next();
			if (postTemp==null || postTemp.getOpen_id() == null || postTemp.getOpen_id() == "") {
				it.remove();
			}
		}
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^over\n\n");
		System.out.println(lists.size());
		// 格式化记录
		JSONArray array = JSONArray.fromObject(lists);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
			Date date = format.parse(jsonObject.getString("time"));
			String d = RelativeDateFormat.format(date);
			array.getJSONObject(i).put("time", d);
			
			//emoji 转化
			String snickName = jsonObject.getString("user_nick");
			try {
				array.getJSONObject(i).put("user_nick", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}catch (NullPointerException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}

			String temp = (String) jsonObject.get("image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList = temp.split(",");
			JSONArray tempArray = new JSONArray();
			JSONObject tempObject = new JSONObject();
			for (int j = 0; j < imgList.length; j++) {
				tempObject.put("img", imgList[j]);
				tempArray.add(tempObject);
			}
			array.getJSONObject(i).put("imgs", tempArray);

			String temp1 = (String) jsonObject.get("scale_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList1 = temp1.split(",");
			JSONArray tempArray1 = new JSONArray();
			JSONObject tempObject1 = new JSONObject();
			for (int j = 0; j < imgList1.length; j++) {
				tempObject1.put("img", imgList1[j]);
				tempArray1.add(tempObject1);
			}
			array.getJSONObject(i).put("scaleImgs", tempArray1);

			String temp2 = (String) jsonObject.get("quality_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList2 = temp2.split(",");
			JSONArray tempArray2 = new JSONArray();
			JSONObject tempObject2 = new JSONObject();
			for (int j = 0; j < imgList2.length; j++) {
				tempObject2.put("img", imgList2[j]);
				tempArray2.add(tempObject2);
			}
			array.getJSONObject(i).put("qualityImgs", tempArray2);
		}
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;

	}

	/**
	 * 根据openId 分页查询帖子
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> findGetPostByOpenId(Post post)
			throws ParseException {
		Map<String, Object> map = new HashMap<>();
		String parentOpenId = post.getParentOpenid();
		Page page = new Page(post);
		// 获取所有帖子
		List<Post> list = dao.getPostByOpenId(page);
		// 格式化记录
		JSONArray array = JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
//			SimpleDateFormat formatF = new SimpleDateFormat("MM月dd日");
			SimpleDateFormat format1 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format1.parse(jsonObject.getString("time"));
			String d = format1.format(date);
			array.getJSONObject(i).put("time", simplify(d));
			
			//emoji 转化
			String snickName = jsonObject.getString("user_nick");
			try {
				array.getJSONObject(i).put("user_nick", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}catch (NullPointerException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}

			String temp = (String) jsonObject.get("image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList = temp.split(",");
			JSONArray tempArray = new JSONArray();
			JSONObject tempObject = new JSONObject();
			for (int j = 0; j < imgList.length; j++) {
				tempObject.put("img", imgList[j]);
				tempArray.add(tempObject);
			}
			array.getJSONObject(i).put("imgs", tempArray);

			String temp1 = (String) jsonObject.get("scale_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList1 = temp1.split(",");
			JSONArray tempArray1 = new JSONArray();
			JSONObject tempObject1 = new JSONObject();
			for (int j = 0; j < imgList1.length; j++) {
				tempObject1.put("img", imgList1[j]);
				tempArray1.add(tempObject1);
			}
			array.getJSONObject(i).put("scaleImgs", tempArray1);

			String temp2 = (String) jsonObject.get("quality_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList2 = temp2.split(",");
			JSONArray tempArray2 = new JSONArray();
			JSONObject tempObject2 = new JSONObject();
			for (int j = 0; j < imgList2.length; j++) {
				tempObject2.put("img", imgList2[j]);
				tempArray2.add(tempObject2);
			}
			array.getJSONObject(i).put("qualityImgs", tempArray2);

			// 封装点赞情况
			String postId = array.getJSONObject(i).getString("id");
			int isExit = dao.isExitUserLovePost(parentOpenId, postId);
			array.getJSONObject(i).put("isLove", isExit);
		}
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		System.out.println(array.toString());
		return map;
	}

	/**
	 * 搜索帖子功能
	 * 
	 * @param page
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> searchPost(Post post)
			throws ParseException {

		Map<String, Object> map = new HashMap<>();
		Page page = new Page(post);
		List<Post> list = dao.searchPost(page);

		// 格式化记录
		JSONArray array = JSONArray.fromObject(list);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
//			SimpleDateFormat formatF = new SimpleDateFormat("MM月dd日");
			SimpleDateFormat format1 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format1.parse(jsonObject.getString("time"));
			String d = format1.format(date);
			array.getJSONObject(i).put("time", simplify(d));
			
			//emoji 转化
			String snickName = jsonObject.getString("user_nick");
			try {
				array.getJSONObject(i).put("user_nick", emoji.utfemojiRecovery(snickName));
			} catch (UnsupportedEncodingException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}catch (NullPointerException e) {
				array.getJSONObject(i).put("user_nick", snickName);
			}

			String temp = (String) jsonObject.get("image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList = temp.split(",");
			JSONArray tempArray = new JSONArray();
			JSONObject tempObject = new JSONObject();
			for (int j = 0; j < imgList.length; j++) {
				tempObject.put("img", imgList[j]);
				tempArray.add(tempObject);
			}
			array.getJSONObject(i).put("imgs", tempArray);

			String temp1 = (String) jsonObject.get("scale_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList1 = temp1.split(",");
			JSONArray tempArray1 = new JSONArray();
			JSONObject tempObject1 = new JSONObject();
			for (int j = 0; j < imgList1.length; j++) {
				tempObject1.put("img", imgList1[j]);
				tempArray1.add(tempObject1);
			}
			array.getJSONObject(i).put("scaleImgs", tempArray1);

			String temp2 = (String) jsonObject.get("quality_image"); // 1532419678830.png,1532419679105.jpg,
			String[] imgList2 = temp2.split(",");
			JSONArray tempArray2 = new JSONArray();
			JSONObject tempObject2 = new JSONObject();
			for (int j = 0; j < imgList2.length; j++) {
				tempObject2.put("img", imgList2[j]);
				tempArray2.add(tempObject2);
			}
			array.getJSONObject(i).put("qualityImgs", tempArray2);
		}
		map.put("list", array);
		map.put("total", page.getTotalRecord());
		return map;
	}

	/**
	 * 新增帖子
	 * 
	 * @param post
	 * @return
	 */
	public Map<String, Object> addPost(Post post) {
		Map<String, Object> map = new HashMap<>();
		if (post.getPost_lau() == null) {
			post.setPost_lau("");
		}
		post.setLove_num(0);
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		post.setTime(simpleDateFormat.format(date));
		
		String snickName = post.getUser_nick();
		try {
			post.setUser_nick(emoji.emojiConvertToUtf(snickName));
		} catch (UnsupportedEncodingException e) {
			post.setUser_nick(snickName);
		}catch (NullPointerException e) {
			post.setUser_nick(snickName);
		}
		
		dao.addPost(post);
		map.put("resultCode", 1);
		return map;
	}

	/**
	 * 增加点赞数
	 * 
	 * @param openId
	 * @return
	 */
	public Map<String, Object> addLoveNum(String openId, String postId,
			String toOpenId) {
		Map<String, Object> map = new HashMap<>();
		// 执行更新+1操作
		dao.addLoveNum(postId);
		// 新增关系表记录
		dao.addUserLovePost(openId, postId);
		// 新增通知记录
		// 如果是自己赞了自己，不添加通知记录
		if (!openId.equals(toOpenId)) {
			MessagePo po = new MessagePo();
			SimpleDateFormat sFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			po.setTime(sFormat.format(new Date()));
			po.setFrom_openId(openId);
			po.setTo_openId(toOpenId);
			po.setType("1");
			operationDao.addMessageInfo(po);
		}
		return map;
	}

	/**
	 * 减少点赞数
	 * 
	 * @param openId
	 * @param postId
	 */
	public void deleteUserLovePost(String openId, String postId, String toOpenId) {
		// 执行减一操作
		dao.reduceLoveNum(postId);
		// 删除关系表记录
		dao.deleteUserLovePost(openId, postId);
		// 删除通知记录
		if (!openId.equals(toOpenId)) {
			MessagePo po = new MessagePo();
			po.setFrom_openId(openId);
			po.setTo_openId(toOpenId);
			po.setType("1");
			operationDao.deleteMessageInfo(po);
		}
	}

	/**
	 * 获取所有帖子第一张图片
	 * 
	 * @return
	 */
	public Map<String, Object> getAllFirstPic() {
		Map<String, Object> map = new HashMap<>();
		List<String> list = dao.getAllFirstPic();
		List<String> listArr = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String[] s = list.get(i).split(",");
			listArr.add(s[0]);
		}
		map.put("list", listArr);
		return map;
	}

	/**
	 * 获取个人所有帖子的第一张图片
	 * 
	 * @param openId
	 * @return
	 */
	public Map<String, Object> getFirstPicByOpenId(String openId) {
		Map<String, Object> map = new HashMap<>();
		List<String> list = dao.getFirstPicByOpenId(openId);
		List<String> listArr = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String[] s = list.get(i).split(",");
			listArr.add(s[0]);
		}
		map.put("list", listArr);
		return map;
	}

	/**
	 * 删除个人帖子
	 * 
	 * @param openId
	 * @param id
	 * @return
	 */
	public Map<String, Object> deleteByOpenId(String id,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(id)) {
			// 删除帖子图片
			String imgList = dao.findImageById(id);
			for (String fileName : imgList.split(",")) {
				UploadPicUtils.deletePic(fileName, request);
			}
			// 删除帖子信息
			dao.deleteByOpenId(id);
		}
		map.put("resultCode", 1);
		return map;
	}
	
	/*
	 * 
	 */
	public Integer getPostById(String postId,String openId) {
		return dao.getPostById(postId,openId);

	}
	
	private String simplify(String date){
	      int index1 =date.indexOf("-");
	      int index2 =date.lastIndexOf("-");
	      int month;
	      int day;
	      int year=Integer.parseInt(date.substring(0,index1));
	      if(date.substring(index1+1,index1+2).equals("0")){
	          month=Integer.parseInt(date.substring(index1+2,index1+3));
	      }else {
	          month=Integer.parseInt(date.substring(index1+1,index1+3));
	      }
	      if(date.substring(index2+1,index2+2).equals("0")){
	          day=Integer.parseInt(date.substring(index2+2,index2+3));
	      }else{
	          day=Integer.parseInt(date.substring(index2+1,index2+3));
	      }
	      String date1=month+"月"+day+"日";
	      return  date1;
	  }
	
//	@Test
//	public void name() {
//		String ss = "2018-01-02 20:00:00";
////		System.out.println(simplify(ss));
//		System.out.println(ss);
//	}
}
