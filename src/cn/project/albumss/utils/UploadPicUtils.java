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
 * @author �³�
 *
 */
public class UploadPicUtils {
	/**
	 * ����һ��ͼƬ���������ݿ�洢��
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static String uploadPic(MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException {
		String lastFileName="";
		if (!file.isEmpty()) {
			//��ȡʱ�����
			long fileName= Calendar.getInstance().getTimeInMillis();
			//��ȡԭ�ļ���
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
	 * ����ͼƬ����ɾ��ͼƬ  ɾ��ͼƬ�ļ�
	 * @param fileName
	 */
	public static void deletePic(String fileName,HttpServletRequest request) {
		String filePath = request.getSession().getServletContext().getRealPath("/pics");
		String photoPath=filePath+"/"+fileName;
		File file=new File(photoPath);
		file.delete();
	}
}
