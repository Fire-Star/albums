/**
 * 
 */
package cn.project.albumss.service;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import cn.project.albumss.mapper.UserDao;
import cn.project.albumss.pojo.UserInfo;
import cn.project.albumss.pojo.Users;
import cn.project.albumss.utils.HttpClientUtil;
import cn.project.albumss.utils.JsonUtils;
import cn.project.albumss.utils.UploadPicUtils;
import cn.project.albumss.utils.emojiConvert;

/**
 * @author �³�
 *
 */
@Service
public class UserService {
	
	private static Logger log2 = Logger.getLogger(UserService.class);
	@Autowired
	private UserDao dao;
//	@Autowired
	private emojiConvert emojiConvert = new emojiConvert();
	
	/**
	 * �ж��û��Ƿ���ڣ����ҷ���openId
	 * @param code
	 * @return
	 */
	public Map<String, Object> findIsExist(String code) {
		Map<String, Object> map=new HashMap<>();
		//����code����ȡ�û�openId
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> param = new HashMap<>();
		param.put("appid", "wx5fbeb66cab448999");
		param.put("secret", "1976e7861405ab13970f5f3c2c926c3c");
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");
		String wxResult = HttpClientUtil.doGet(url, param);
		UserInfo userInfo= JsonUtils.jsonToPojo(wxResult, UserInfo.class);
		
		//����openId�ж��û��Ƿ����
		int resulrCode=dao.isExist(userInfo.getOpenid());
		map.put("resultCode", resulrCode);
		map.put("openId", userInfo.getOpenid());
		return map;
	}
	/**
	 * ����openId,�����û�
	 * @param openId
	 * @return
	 */
	public Users findUserByOpenId(String openId) {
		Users users = dao.findUserByOpenId(openId);
		String ss = users.getNickName();
		try {
			users.setNickName(emojiConvert.utfemojiRecovery(ss));
		} catch (UnsupportedEncodingException e) {
			users.setNickName(ss);
		}
		System.out.println("\n\n*********************findUserByOpenId*************\n\n");
		System.out.println(users.getNickName());
		return users;
	}
	/**
	 * �����û�
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Users addUser(Users users) throws IllegalStateException, IOException {
		//��װ�û���Ϣ
		users.setFans("0");
		users.setFavour_user("0");
		users.setUser_type("0");
		
		String ss = users.getNickName();
		try {
			users.setNickName(emojiConvert.emojiConvertToUtf(ss));
		} catch (UnsupportedEncodingException e) {
			users.setNickName(ss);
		}
		//�������ݿ�
		dao.addUser(users);
		return users;
	}
	/**
	 * �޸��û�
	 * @param file
	 * @param user
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Map<String, Object> updateModifyUser(Users user,HttpServletRequest request,boolean isModifyPic) throws IllegalStateException, IOException {
		Map<String, Object> map=new HashMap<>();
		
		//ͼƬ�Ƿ��޸�
		if(isModifyPic) {
			//����openId��ȡ��ǰ��ͼƬ
			String fileName=dao.findImgByOpenId(user.getOpenId());
			//ɾ��ͼƬ
			UploadPicUtils.deletePic(fileName, request);
			if (!StringUtils.isNullOrEmpty(user.getBackground_image())) {
				//ɾ������ͼƬ
				String fileNa=dao.queryBgImgById(user.getOpenId());
				UploadPicUtils.deletePic(fileNa, request);
			}
		}
		String ss = user.getNickName();
		try {
			user.setNickName(emojiConvert.emojiConvertToUtf(ss));
		} catch (UnsupportedEncodingException e) {
			user.setNickName(ss);
		}
		
		//�޸��û�
		dao.modifyUser(user);
		return map;
	}
	/**
	 * ����openId����ѯ�û�������
	 * @param openId
	 * @return
	 */
	public Map<String, Object> findQueryPostNum(String openId) {
		Map<String, Object> map=new HashMap<>();
		map.put("postNum",dao.queryPostNum(openId)) ;
		return map;
	}
	/**
	 * �ϴ�һ��ͼƬ���������ݿⱣ����
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Map<String, Object> uploadPic(MultipartFile file,HttpServletRequest request,String isAdd,String name) throws IllegalStateException, IOException {
		Map<String, Object> map=new HashMap<>();
		String url=UploadPicUtils.uploadPic(file, request);

		log2.info("value********************uploadPic******info***********************\n\n\n\n\n\n\n");
		log2.info(name);
		log2.info(new String(name.getBytes(),"utf-8"));


		
		//��������ַ
		String serverUrl=request.getSession().getServletContext().getRealPath("/pics");
		
		//0.5�����ţ�0.5ѹ��
		File proFile = new File(serverUrl+"/scale");
		if(!proFile.exists()){
			proFile.mkdirs();
		}
		Thumbnails.of(serverUrl+"/"+url)  	//ԭͼ·��
			.scale(0.5f)   			//���ű���
			.outputQuality(0.5f)   	//ѹ������
			.toFile(serverUrl+"/scale/"+url);
		
		//0.5ѹ��
		File proFile1 = new File(serverUrl+"/quality");
		if(!proFile1.exists()){
			proFile1.mkdirs();
		}
		Thumbnails.of(serverUrl+"/"+url)  	//ԭͼ·��
			.scale(1f)   			//���ű���
			.outputQuality(0.5f)   	//ѹ������
			.toFile(serverUrl+"/quality/"+url);
		
		map.put("qualityImg", "quality/"+url);
		map.put("sacleImg", "scale/"+url);
		map.put("img",url);
		if ("1".equals(isAdd)) {
			//ͼƬ��ˮӡ
			pressText(serverUrl+"/"+url, "Collected by "+name);
		}
		return map;
	}
	
	/**
	 * �޸��û�����
	 * @param users
	 */
	public void updateUserType(Users users) {
		String ss = users.getNickName();
		try {
			users.setNickName(emojiConvert.emojiConvertToUtf(ss));
		} catch (UnsupportedEncodingException e) {
			users.setNickName(ss);
		}
		dao.updateUserType(users);
	}
	
	
	@Test
	public void testttt() throws IOException {
		Thumbnails.of("C:/Users/��ͺ�/Desktop/ѧϰ����/pic/1.jpg").scale(1f).outputQuality(0.5f).toFile("C:/Users/��ͺ�/Desktop/ѧϰ����/pic/test1231.jpg");
	}
	
	/** 
     * �������ˮӡ 
     * @param targetImg Ŀ��ͼƬ·�����磺C://myPictrue//1.jpg 
     * @param pressText ˮӡ���֣� �磺�й�֤ȯ�� 
     * @param fontName �������ƣ�    �磺���� 
     * @param fontStyle ������ʽ���磺�����б��(Font.BOLD|Font.ITALIC) 
     * @param fontSize �����С����λΪ���� 
     * @param color ������ɫ 
     * @param x ˮӡ���־���Ŀ��ͼƬ����ƫ���������x<0, �������м� 
     * @param y ˮӡ���־���Ŀ��ͼƬ�ϲ��ƫ���������y<0, �������м� 
     * @param alpha ͸����(0.0 -- 1.0, 0.0Ϊ��ȫ͸����1.0Ϊ��ȫ��͸��) 
     */
	public void pressText(String targetImg,String pressText){
		try {
			//MyImage2.pressText("C:\\Users\\chengjiangbo\\Desktop\\images\\IMG_300_100.jpg", "����֮ӡ", "����", Font.BOLD, 20, Color.WHITE,10, 10, 1f);
			String fontName="����";
			int fontStyle=Font.BOLD;
			int fontSize=10;
			Color color=Color.white;
			float alpha=(float) 0.5;
			File file = new File(targetImg);    
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			int fv = width>height?width:height;
			fontSize = (int) (fv*0.025);
			System.out.println("fontsize"+fontSize);
			
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image,0,0, width, height, null);
			Font font = new Font(fontName, fontStyle, fontSize);
			g.setFont(font);
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			FontMetrics fm = g.getFontMetrics();
			int width_wi = fm.stringWidth(pressText);
			int height_wi = fontSize;
			
			int x = (int)(fontSize/2);
			int y = (int)(fontSize/2);
			
			
			int widthDiff = width-width_wi;
			int heightDiff = height-height_wi;
			
//			FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
//	        int width = 0;
//	        for (int i = 0; i < content.length(); i++) {
//	            width += metrics.charWidth(content.charAt(i));
//	        }

			
//			if(x<0){
//				x = widthDiff/2;
//			}else if(x>widthDiff){
//				x=widthDiff;
//			}
//			
//			if(y<0){
//				y = heightDiff/2;
//			}else if(y>heightDiff){
//				y = heightDiff;
//			}
//			log2.info("***********************value********************uploadPic******pressText***********************\n");
//			log2.info("pressText");
//			log2.info(pressText);
//			System.out.println(width);
//			System.out.println(height);
//			System.out.println(widthDiff);
//			System.out.println(heightDiff);

			g.drawString(pressText, widthDiff-x, height-y);//ˮӡ�ļ�
			g.dispose();
			ImageIO.write(bufferedImage, "JPEG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����������س���
	 * @param text
	 * @return
	 */
	private int getTextLength(String text){
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			int wordLength = String.valueOf(text.charAt(i)).getBytes().length;
			if(wordLength > 1){
				length+=(wordLength-1);
			}
		}
		
		return length%2==0 ? length/2:length/2+1;
	}

	
	
}
