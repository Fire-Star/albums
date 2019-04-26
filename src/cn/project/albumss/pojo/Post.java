/**
 * 
 */
package cn.project.albumss.pojo;

import java.io.Serializable;

import cn.project.albumss.vo.BaseVo;


/**
 * @author �³�
 *
 */
public class Post extends BaseVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String open_id;
	private String image;
	private String user_image;
	private String user_nick;
	private int love_num;
	private String post_lau;
	private String time;
	private String parentOpenid;
	private String scale_image;
	private String quality_image;
	
	//0Ϊ��ѯȫ��    1Ϊ��ѯ��Ӱʦ  2Ϊ��ѯģ��  3Ϊ��ѯ��ע
	private String queryType;
	
	private String user_type;
	private String brief;
	
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", open_id=" + open_id + ", image=" + image
				+ ", love_num=" + love_num + ", post_lau=" + post_lau
				+ ", time=" + time + "]";
	}
	public int getLove_num() {
		return love_num;
	}
	public void setLove_num(int love_num) {
		this.love_num = love_num;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public String getUser_nick() {
		return user_nick;
	}
	public void setUser_nick(String user_nick) {
		this.user_nick = user_nick;
	}
	public String getPost_lau() {
		return post_lau;
	}
	public void setPost_lau(String post_lau) {
		this.post_lau = post_lau;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getParentOpenid() {
		return parentOpenid;
	}
	public void setParentOpenid(String parentOpenid) {
		this.parentOpenid = parentOpenid;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getScale_image() {
		return scale_image;
	}
	public void setScale_image(String scale_image) {
		this.scale_image = scale_image;
	}
	public String getQuality_image() {
		return quality_image;
	}
	public void setQuality_image(String quality_image) {
		this.quality_image = quality_image;
	}
}
