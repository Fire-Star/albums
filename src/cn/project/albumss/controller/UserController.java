/**
 * 
 */
package cn.project.albumss.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.project.albumss.pojo.Users;
import cn.project.albumss.service.UserService;

/**
 * @author 閿熸枻鎷风陈超
 *
 */
@Controller
public class UserController {
	private static Logger log1 = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService service;
	
	
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹code閿熸枻鎷烽敓鍙鎷烽敓鐭紮鎷烽敓瑙掑嚖鎷蜂负閿熸枻鎷烽敓鐭紮鎷�
	 * @return
	 */
	@RequestMapping(value="/user_isExist")
	@ResponseBody
	public Map<String, Object> isExist(@RequestParam(value="code")String code) {
		return service.findIsExist(code);
	}
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹openId,閿熸枻鎷疯閿熺煫浼欐嫹
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/user_findbyopenid")
	@ResponseBody
	public Users findByOpenId(@RequestParam(value="openId")String openId) {
//		log1.debug("**********************openId*********************\n\n\n\n\n\\n\n\n");
//		log1.debug(openId);
//		log1.info("**********************openId*********************\n\n\n\n\n\\n\n\n");
//		log1.info(openId);
		return service.findUserByOpenId(openId);
	}
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熺煫浼欐嫹
	 * @param nickName
	 * @param openId
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="/user_adduser",method=RequestMethod.GET)
	@ResponseBody
	public Users addUser(Users users) throws IllegalStateException, IOException {
		return service.addUser(users);
	}
	/**
	 * 閿熺潾闈╂嫹閿熺煫浼欐嫹
	 * @param user
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="/user_modifyuser")
	@ResponseBody
	public Map<String, Object> modifyUser(Users user,@RequestParam(value="isModifyPic")Boolean isModifyPic,HttpServletRequest request) throws IllegalStateException, IOException {
		return service.updateModifyUser(user, request,isModifyPic);
	}
	/**
	 * 閿熸枻鎷烽敓鏂ゆ嫹openId閿熸枻鎷烽敓鏂ゆ嫹璇㈤敓鐭紮鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/user_querypostnum")
	@ResponseBody
	public Map<String, Object> queryPostNum(@RequestParam(value="openId")String openId) {
		return service.findQueryPostNum(openId);
	}
	
	/**
	 * 閿熻緝杈炬嫹涓�閿熸枻鎷峰浘鐗囬敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鎹峰尅鎷峰ú顫嫹閿燂拷
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="/user_uploadPic",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadPic(@RequestParam(value="img")MultipartFile file,@RequestParam(value="isAdd")String isAdd
			,@RequestParam(value="name")String name,
			HttpServletRequest request) throws IllegalStateException, IOException {
		
		return service.uploadPic(file, request,isAdd,name);
	}
	
	/**
	 * 閿熺潾闈╂嫹閿熺煫浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
	 * @param users
	 * @return
	 */
	@RequestMapping(value="/user_updateUserType")
	@ResponseBody
	public Map<String, Object> updateUserType(Users users) {
		Map<String, Object> map=new HashMap<>();
		service.updateUserType(users);
		return map;
	}
	
	
	/**
	 * 閿熸枻鎷烽敓鐨嗚鎷烽敓鑴氭帴鍖℃嫹
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/test_getmessage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSMSmessage(String phone) {
		Map<String, Object> map =new HashMap<>();
		CloseableHttpClient client=HttpClients.createDefault();
		CloseableHttpResponse response =null;
		try {
			URI uri=new URI("http://v.juhe.cn/sms/send?mobile="+phone+"&tpl_id=98579&tpl_value=%23code%23%3D5201315&key=d3a5a1b9185dc2b3b5763763d31213df");
			//URI uri=new URI("http://v.douyin.com/dH1osf");
			HttpGet get=new HttpGet(uri);
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String res=EntityUtils.toString(entity,"UTF-8");
			map.put("res", res);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (response!=null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	
	
}
