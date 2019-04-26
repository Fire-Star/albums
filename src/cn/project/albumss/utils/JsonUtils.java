package cn.project.albumss.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @Title: JsonUtils.java
 * @Package com.lee.utils
 * @Description: 鑷畾涔夊搷搴旂粨鏋� 杞崲绫�
 * Copyright: Copyright (c) 2016
 * Company:Nathan.Lee.Salvatore
 * 
 * @author 陈超
 * @date 2016骞�鏈�9鏃�涓嬪崍11:05:03
 * @version V1.0
 */
public class JsonUtils {

    // 瀹氫箟jackson瀵硅薄
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 任意对象转化为Json字符串
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * Json字符串转化为pojo
     * 
     * @param jsonData json鏁版嵁
     * @param clazz 瀵硅薄涓殑object绫诲瀷
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Json字符串数组转化为List
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
}
