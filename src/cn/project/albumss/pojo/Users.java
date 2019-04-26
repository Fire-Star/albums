/**
 * 
 */
package cn.project.albumss.pojo;

import java.io.Serializable;

/**
 * @author �³�
 *
 */
public class Users implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String openId; //�û�Ψһ��ʶ
	private String image;  //�û�ͷ��
	private String nickName;  //�û��ǳ�
	private String brief; //�û����
	private String user_type; //�û�����  0Ϊģ��  1Ϊ��Ӱʦ   ---ģ���ָ�Ϊ�ղ���
	private String background_image; //�û�����ͼ
	private String sex; //�Ա�  0 δ֪ 1���� 2Ů��
	private String fans; //��˿����
	private String favour_user; //��ע����
	
	/**
	 * �Ƿ����ˮӡ   0 ��   1��
	 */
	private String waterMark;
	
	/**
	 * ��Ӱʦ����λ��
	 */
	private String location;
	
	/**
	 * �Զ�������
	 */
	private String custom_name;
	
	/**
	 * �Ƿ�����΢����   0 ������    1����
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
