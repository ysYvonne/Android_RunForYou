package xzh.com.materialdesign.model;

import java.util.List;

/**
 * 说明 类的描述
 */
public interface PageList<T> {

	public int getPageSize();
	public int getCount();
	public List<T> getList();
}
