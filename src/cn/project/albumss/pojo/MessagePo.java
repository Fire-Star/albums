/**
 * 
 */
package cn.project.albumss.pojo;

import java.io.Serializable;

import cn.project.albumss.vo.BaseVo;

/**
 * @author ³Â³¬
 *
 */
public class MessagePo extends BaseVo implements Serializable{

	private int id;
	private String from_openId;
	private String to_openId;
	private String time;
	private String type;
	private String content;
	private String image;
	private String nickName;
	private String is_read;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFrom_openId() {
		return from_openId;
	}
	public void setFrom_openId(String from_openId) {
		this.from_openId = from_openId;
	}
	public String getTo_openId() {
		return to_openId;
	}
	public void setTo_openId(String to_openId) {
		this.to_openId = to_openId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
}
