package cn.project.albumss.vo;

/**
* <p>Title: 商软ezFrame - BaseVo</p>
*
* <p>Description: �?��Vo的祖先，在这个基类中，我们定义了�?��常用的属�?/p>
*
* <p>Copyright: Copyright BSST(c) 2001-2015</p>
*
* <p>Company: 重庆商软冠联信息�?��发展有限公司</p>
*
* @author william
* @version 1.0
*/
public class BaseVo {
	/**
	 * 分页的情况下，当前页编号，页码从1�?��
	 */
	private int page;
	
	/**
	 * 定义每页显示的数据行�?
	 */
	private int rows;
	
	/**
	 * 点击表格表头排序的字�?
	 */
	private String sort;
	
	/**
	 * 表格排序的方式：asc、desc
	 */
	private String order;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
