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
 * @author 陈超
 *
 */
@Controller
public class PostController {
	
	@Autowired
	private PostService service;
	
	/**
	 * 获取所有的帖子-分页 --根据点赞数排序
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_getall") 
	@ResponseBody
	public Map<String, Object> getAllPost(Post post) throws ParseException {
		
		return service.findGetAllPost(post);
	}
	
	/**
	 * 获取所有的帖子-分页 --根据时间排序
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_getallByTime") 
	@ResponseBody
	public Map<String, Object> getAllPostByTime(Post post) throws ParseException {
		return service.findGetAllPostByTime(post);
	}
	
	/**
	 * 分页获取所有点赞帖子
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
	 * 根据openId 分页查询帖子
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/post_getPostByOpenId") 
	@ResponseBody
	public Map<String, Object> getPostByOpenId(Post post) throws ParseException {
		return service.findGetPostByOpenId(post);
	}
	
	/**
	 * 搜索帖子功能
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
	 * 新增帖子
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
	 * 增加点赞数
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/post_addlovenum")
	@ResponseBody
	public Map<String, Object> addLoveNum(String openId,String postId,String toOpenId) {
		return service.addLoveNum(openId, postId,toOpenId);
	}
	
	/**
	 * 减少点赞数
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
	 * 获取所有帖子第一张图片
	 * @return
	 */
	@RequestMapping(value="/post_getAllFirstPic")
	@ResponseBody
	public Map<String, Object> getAllFirstPic() {
		return service.getAllFirstPic();
	}
	
	/**
	 * 获取个人所有帖子的第一张图片
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/post_getAllFirstPicByOpenId")
	@ResponseBody
	public Map<String, Object> getFirstPicByOpenId(@RequestParam(value="openId")String openId) {
		return service.getFirstPicByOpenId(openId);
	}
	
	/**
	 * 删除个人帖子
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/post_deleteByOpenId")
	@ResponseBody
	public Map<String, Object> deleteByOpenId(@RequestParam(value="id")String id,HttpServletRequest request) {
		return service.deleteByOpenId(id,request);
	}
	
	/*
	 * 用户是否喜欢某个postid
	 */
	
	@RequestMapping(value="/post_userIsLikePostId")
	@ResponseBody
	public Integer getPostById(String postId,String openId) {
		return service.getPostById(postId,openId);
	}
	
	/**
	 * 测试
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
