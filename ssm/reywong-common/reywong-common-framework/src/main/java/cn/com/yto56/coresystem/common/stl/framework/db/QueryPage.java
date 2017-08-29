package cn.com.yto56.coresystem.common.stl.framework.db;

import org.springframework.jdbc.core.RowMapper;

import java.util.*;

@SuppressWarnings("deprecation")
public class QueryPage {
    private Map<String, Object> parameterMap = new HashMap<String, Object>();
    private int page;
    private int rows;

    // 排序参数容器
    Map<String, String> sortMap = null;

    // 每页记录数
    int pageSize;

    // 目标页
    int pageIndex;

    // 总记录数
    int totalRows;

    // 是否查询总数
    boolean calCount = true;

    // SQL
    String sqlString;

    // 查询条件列表
    List queryConditionList = new ArrayList();

    List<PageFooterColumn> pageFooter = new ArrayList<PageFooterColumn>();

    @SuppressWarnings("rawtypes")
    RowMapper rowMapper;

    public QueryPage(int pageSize, int pageIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    // 初始化操作
    public QueryPage(int pageSize, int pageIndex, String sortName, String sortOrder) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;

        if (sortName != null && !sortName.equals("")) {
            this.addSort(sortName, sortOrder);
        }
    }


    /**
     * 排序
     *
     * @param sortName
     * @param sortOrder
     */
    public void addSort(String sortName, String sortOrder) {

        if (sortMap == null) {
            sortMap = new LinkedHashMap<String, String>();
        }

        sortMap.put(sortName, sortOrder);
    }

    /**
     * 设置排序
     *
     * @param sortName
     * @param sortOrder
     */
    public void setSort(String sortName, String sortOrder) {

        if (sortMap == null) {
            sortMap = new LinkedHashMap<String, String>();
        }

        sortMap.put(sortName, sortOrder);
    }

    public Map<String, String> getSortMap() {
        return sortMap;
    }

    public void setSortMap(Map<String, String> sortMap) {
        this.sortMap = sortMap;
    }

    public void clearSortMap() {
        if (sortMap == null) {
            sortMap = new HashMap<String, String>();
        }

        sortMap.clear();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }


    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    public List getQueryConditionList() {
        return queryConditionList;
    }

    public void setQueryConditionList(List queryConditionList) {
        this.queryConditionList = queryConditionList;
    }

    public void addQueryCondition(Object queryCondition) {
        this.queryConditionList.add(queryCondition);
    }

    public void clearQueryCondition() {
        this.queryConditionList.clear();
    }


    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

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

    public List<PageFooterColumn> getPageFooter() {
        return pageFooter;
    }

    public void setPageFooter(List<PageFooterColumn> pageFooter) {
        this.pageFooter = pageFooter;
    }

    public void addPageFooterColumn(PageFooterColumn column) {
        pageFooter.add(column);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List getAllNotNullArg() {
        List args = new ArrayList();
        for (Object cv : this.queryConditionList) {
            if (cv != null)
                args.add(cv);
        }

        for (PageFooterColumn column : pageFooter) {
            args.add(column);
        }

        return args;
    }

    public boolean isCalCount() {
        return calCount;
    }

    public void setCalCount(boolean calCount) {
        this.calCount = calCount;
    }


    @SuppressWarnings("rawtypes")
    public RowMapper getRowMapper() {
        return rowMapper;
    }

    @SuppressWarnings("rawtypes")
    public void setRowMapper(RowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

}
