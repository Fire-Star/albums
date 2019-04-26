/**
 * 
 */
package cn.project.albumss.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 陈超
 *
 */
public class UploadPicUtils {
	/**
	 * 保存一张图片，返回数据库存储名
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static String uploadPic(MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException {
		String lastFileName="";
		if (!file.isEmpty()) {
			//获取时间毫秒
			long fileName= Calendar.getInstance().getTimeInMillis();
			//获取原文件名
			String orgFileName=file.getOriginalFilename();
			String fileStype=orgFileName.substring(orgFileName.lastIndexOf("."));
			lastFileName=fileName+fileStype;
			String filePath = request.getSession().getServletContext().getRealPath("/pics");
			File uploadFile = new File(filePath,lastFileName);
			File proFile = new File(filePath);
			if(!proFile.exists()){
				proFile.mkdirs();
			}
			file.transferTo(uploadFile);
			//String path="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/pics/fix/"+lastFileName;
		}
		return lastFileName;
	}
	/**
	 * 根据图片名字删除图片  删除图片文件
	 * @param fileName
	 */
	public static void deletePic(String fileName,HttpServletRequest request) {
		String filePath = request.getSession().getServletContext().getRealPath("/pics");
		String photoPath=filePath+"/"+fileName;
		File file=new File(photoPath);
		file.delete();
	}
}
