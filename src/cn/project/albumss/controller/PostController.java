/**
 * 
 */
package cn.project.albumss.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.project.albumss.pojo.Post;
import cn.project.albumss.service.PostService;

/**
 * @author �³�
 *
 */
@Controller
public class PostController {
	
	@Autowired
	private PostService service;
	
	/**
	 * ��ȡ���е�����-��ҳ --���ݵ���������
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_getall") 
	@ResponseBody
	public Map<String, Object> getAllPost(Post post) throws ParseException {
		
		return service.findGetAllPost(post);
	}
	
	/**
	 * ��ȡ���е�����-��ҳ --����ʱ������
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_getallByTime") 
	@ResponseBody
	public Map<String, Object> getAllPostByTime(Post post) throws ParseException {
		return service.findGetAllPostByTime(post);
	}
	
	/**
	 * ��ҳ��ȡ���е�������
	 * @param page
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_getAllPostIsLove")
	@ResponseBody
	public Map<String, Object> getAllPostIsLove(Post post) throws ParseException {
		return service.getAllPostIsLove(post);
	}
	
	/**
	 * ����openId ��ҳ��ѯ����
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_getPostByOpenId") 
	@ResponseBody
	public Map<String, Object> getPostByOpenId(Post post) throws ParseException {
		return service.findGetPostByOpenId(post);
	}
	
	/**
	 * �������ӹ���
	 * @param page
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_searchPost")
	@ResponseBody
	public Map<String, Object> searchPost(Post post) throws ParseException {
		return service.searchPost(post);
	}
	
	/**
	 * ��������
	 * @param post
	 * @return
	 */
	@RequestMapping(value="/post_add")
	@ResponseBody
	public Map<String, Object> addPost(Post post) {
		System.out.println(post.toString());
		return service.addPost(post);
	}
	/**
	 * ���ӵ�����
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/post_addlovenum")
	@ResponseBody
	public Map<String, Object> addLoveNum(String openId,String postId,String toOpenId) {
		return service.addLoveNum(openId, postId,toOpenId);
	}
	
	/**
	 * ���ٵ�����
	 * @param openId
	 * @param postId
	 * @return
	 */
	@RequestMapping(value="post_reducelovenum")
	@ResponseBody
	public Map<String, Object> deleteUserLovePost(String openId,String postId,String toOpenId) {
		Map<String,Object> map=new HashMap<>();
		service.deleteUserLovePost(openId, postId,toOpenId);
		return map;
	}
	
	/**
	 * ��ȡ�������ӵ�һ��ͼƬ
	 * @return
	 */
	@RequestMapping(value="/post_getAllFirstPic")
	@ResponseBody
	public Map<String, Object> getAllFirstPic() {
		return service.getAllFirstPic();
	}
	
	/**
	 * ��ȡ�����������ӵĵ�һ��ͼƬ
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/post_getAllFirstPicByOpenId")
	@ResponseBody
	public Map<String, Object> getFirstPicByOpenId(@RequestParam(value="openId")String openId) {
		return service.getFirstPicByOpenId(openId);
	}
	
	/**
	 * ɾ����������
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/post_deleteByOpenId")
	@ResponseBody
	public Map<String, Object> deleteByOpenId(@RequestParam(value="id")String id,HttpServletRequest request) {
		return service.deleteByOpenId(id,request);
	}
	
	/*
	 * �û��Ƿ�ϲ��ĳ��postid
	 */
	
	@RequestMapping(value="/post_userIsLikePostId")
	@ResponseBody
	public Integer getPostById(String postId,String openId) {
		return service.getPostById(postId,openId);
	}
	
	/**
	 * ����
	 * @param preUrl
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/test/urlParse")
	@ResponseBody
	public Map<String, Object> urlTest(String preUrl) throws IOException {
		Map<String, Object> map=new HashMap<String, Object>();
    	URL url=new URL(preUrl);
    	HttpURLConnection conn=(HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("GET");
    	conn.setInstanceFollowRedirects(false);
    	conn.connect();
    	String location=conn.getHeaderField("Location");
    	//System.out.println(location);
    	Document doc = Jsoup.connect(location).get();
    	//System.out.println(doc.getAllElements());
    	String data=doc.getAllElements().toString();
    	//System.out.println(data);
    	String goal=data.substring(data.indexOf("playAddr:")+11,data.indexOf("&line=0")+7);
    	map.put("goal", goal);
    	return map;
	}
}
