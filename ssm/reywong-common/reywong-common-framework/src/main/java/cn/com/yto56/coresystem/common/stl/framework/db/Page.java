package cn.com.yto56.coresystem.common.stl.framework.db;


import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


/**
 * 分页实现
 *
 * @param <T> 分页数据类型
 * @author
 */
public class Page<T> implements IPage<T> {
    private int total; // 总页数

    private int pageSize; // 分页步长

    private int page; // 当前页

    private int records; // 总记录数

    private Collection<T> rows; // 数据

    private String responseText;

    private Map<String, Object> userdata;

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public Page(Collection<T> rows, int records, int pageSize, int page) {
        //StringUtils.toTextForHtml(rows);
        this.rows = (rows == null ? new ArrayList<T>(0) : rows);
        this.records = records;
        this.page = page;
        this.pageSize = pageSize;
        this.total = calcTotalPage();


        // jqgrid 当前页从1开始
        if (this.getTotal() <= this.getPage()) {
            this.setPage(this.getTotal());
        } else {
            this.setPage(this.getPage() + 1);
        }
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public void setRows(Collection<T> rows) {
        StringUtils.toTextForHtml(rows);
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public int getPageSize() {
        return pageSize;
    }

    private int calcTotalPage() {
        int t = getRecords();
        int p = getPageSize();
        if (t == 0 || p == 0)
            return 0;
        int r = t % p;
        int pages = (t - r) / p;
        if (r > 0)
            pages += 1;
        return pages;
    }

    public int getPage() {
        return page;
    }

    public int getPageBegin() {
        return (pageSize * page) + 1;
    }

    public int getPageEnd() {
        return getPageBegin() + getRows().size();
    }

    public Collection<T> getRows() {
        return rows;
    }

    public boolean isFirstPage() {
        return page == 0;
    }

    public boolean isLastPage() {
        return page + 1 == total || total == 0;
    }

    public boolean add(T o) {
        return rows.add(o);
    }

    public boolean addAll(Collection<? extends T> c) {
        return rows.addAll(c);
    }

    public void clear() {
        rows.clear();
    }

    public boolean contains(Object o) {
        return rows.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return rows.containsAll(c);
    }

    public boolean equals(Object o) {
        return rows.equals(o);
    }

    public int hashCode() {
        return rows.hashCode();
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }

    public Iterator<T> iterator() {
        return rows.iterator();
    }

    public boolean remove(Object o) {
        return rows.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return rows.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return rows.retainAll(c);
    }

    public int size() {
        return rows.size();
    }

    public Object[] toArray() {
        return rows.toArray();
    }

    @SuppressWarnings("hiding")
    public <T> T[] toArray(T[] a) {
        return rows.toArray(a);
    }

    public int getRecords() {
        return records;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Map<String, Object> getUserdata() {
        return userdata;
    }

    public void setUserdata(Map<String, Object> userdata) {
        this.userdata = userdata;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("total:" + total).append(",");
        sb.append("pageSize:" + pageSize).append(",");
        sb.append("page:" + page).append(",");
        sb.append("records:" + records);

        if (null != rows && !rows.isEmpty()) {
            sb.append(",rows size:" + rows.size());
        }

        return sb.toString();
    }

}
