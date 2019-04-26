package cn.project.albumss.mybatis;

import java.util.HashMap;
import java.util.Map;

import cn.project.albumss.pojo.Post;
import cn.project.albumss.vo.BaseVo;



/**
*
* <p>Title: 商软ezFrame - Page<T></p>
*
* <p>Description: 对MyBatis分页数据进行�?��封装</p>
*
* <p>Copyright: Copyright BSST(c) 2001-2016</p>
*
* <p>Company: 重庆市商软科�?��限责任公�?/p>
*
* @author william
* @version 1.0
*/
public class Page {
	/**
	 * 页码，默认是第一�?
	 */
    private int pageNo = 1;
    
    /**
     * 每页显示的记录数，默认是15
     */
    private int pageSize = 15;
    
    /**
     * 总记录数
     */
    private int totalRecord;
    
    private String openId;
    
    /**
     * 总页�?
     */
    private int totalPage;
    
    /**
     * 其他的参数我们把它分装成�?��Map对象
     */
    private Map<String, Object> params = null;
    
    public Page() {
    }
    
    /**
     * 构�?函数，传入一个bo对象，将bo对象存入到params中，同时将bo中分页信息写入类�?
     * @param bo Object对象,包含分页、查询参数的对象
     */
    public Page(BaseVo bo) {
    	pageNo = bo.getPage();
    	pageSize = bo.getRows();
		
		params = new HashMap<String, Object>();
		params.put("bo", bo);
    }
    
    
    
    
    public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getPageNo() {
       return pageNo;
    }
 
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }
 
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       
       //在设置�?页数的时候计算出对应的�?页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号�?
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
    
    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=")
              .append(pageSize).append(", totalPage=").append(totalPage).append(
                     ", totalRecord=").append(totalRecord).append("]");
       
       return builder.toString();
    }
}
