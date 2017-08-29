package cn.com.yto56.coresystem.common.stl.framework.db;

import java.util.Collection;

/**
 * 分页接口
 *
 * @author : luoyue@cn.ibm.com
 *
 * @param <T> 分页数据类型
 */
public interface IPage<T>{
	/**
	 * 获取总页数
	 *
	 * @return 总页数
	 */
	int getTotal();

	/**
	 * 获取页面大小
	 *
	 * @return 页面大小
	 */
	int getPageSize();

	/**
	 * 获取当前页数
	 *
	 * @return 当前页数
	 */
	int getPage();
	
	
	/**
	 * 查询出的记录数
	 * 
	 * @return
	 */
	int getRecords();

	/**
	 * 是否为第一页
	 *
	 * @return 是否为第一页
	 */
	boolean isFirstPage();

	/**
	 * 是否为最后一页
	 *
	 * @return 是否为最后一页
	 */
	boolean isLastPage();

	/**
	 * 获取当前页起始记录行
	 *
	 * @return 起始记录行
	 */
	int getPageBegin();

	/**
	 * 获取当前页结束记录行
	 *
	 * @return 结束记录行
	 */
	int getPageEnd();

	/**
	 * 获取页面数据
	 *
	 * @return 页面数据
	 */
	Collection<T> getRows();
}
