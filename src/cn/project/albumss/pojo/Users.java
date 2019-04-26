/**
 * 
 */
package cn.project.albumss.pojo;

import java.io.Serializable;

/**
 * @author 陈超
 *
 */
public class Users implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String openId; //用户唯一标识
	private String image;  //用户头像
	private String nickName;  //用户昵称
	private String brief; //用户简介
	private String user_type; //用户类型  0为模特  1为摄影师   ---模特现改为收藏者
	private String background_image; //用户背景图
	private String sex; //性别  0 未知 1男性 2女性
	private String fans; //粉丝数量
	private String favour_user; //关注数量
	
	/**
	 * 是否添加水印   0 不   1是
	 */
	private String waterMark;
	
	/**
	 * 摄影师常用位置
	 */
	private String location;
	
	/**
	 * 自定义名称
	 */
	private String custom_name;
	
	/**
	 * 是否隐藏微信名   0 不隐藏    1隐藏
	 */
	private String is_hide;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
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
	public String getBackground_image() {
		return background_image;
	}
	public void setBackground_image(String background_image) {
		this.background_image = background_image;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getFans() {
		return fans;
	}
	public void setFans(String fans) {
		this.fans = fans;
	}
	public String getFavour_user() {
		return favour_user;
	}
	public void setFavour_user(String favour_user) {
		this.favour_user = favour_user;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCustom_name() {
		return custom_name;
	}
	public void setCustom_name(String custom_name) {
		this.custom_name = custom_name;
	}
	public String getIs_hide() {
		return is_hide;
	}
	public void setIs_hide(String is_hide) {
		this.is_hide = is_hide;
	}
	public String getWaterMark() {
		return waterMark;
	}
	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}
}
