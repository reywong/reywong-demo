package cn.com.yto56.coresystem.common.stl.framework.db;

import cn.com.yto56.coresystem.common.stl.framework.base.StringUtils;
import cn.com.yto56.coresystem.common.stl.framework.db.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.*;

/**
 * JDBC数据操作实现,注入dataSource
 *
 * @author wangrui
 */
public class BaseJdbcUtil extends JdbcDaoSupport {

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String COMMA = " , ";
    public static final String SPACE = " ";
    public static final String COUNT = "COUNT__";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取数据库的列名及对应的数据类型</br>
     *
     * @param tableName 数据库名
     * @return Map<key,value> key为列名 value为对应的数据类型
     */
    public List getColumnNameList(JdbcTemplate jdbcTemplate, String tableName) {
        List list = null;
        String sql = "select * from " + tableName + " limit 1,1";
        RowCountCallbackHandler rcch = new RowCountCallbackHandler();
        try {
            jdbcTemplate.query(sql, rcch);
            String[] columnNames = rcch.getColumnNames();
            for (int i = 0; i < columnNames.length; i++) {
                list.add(columnNames[i]);
            }
        } catch (Exception e) {
            logger.error("sql执行异常", e);
        }
        return list;
    }

    /**
     * 判断表中是否存在给定字段
     *
     * @param jdbcTemplate
     * @param tableName
     * @param columnName
     * @return
     */
    public boolean isExistColumnName(JdbcTemplate jdbcTemplate, String tableName, String columnName) {
        boolean result = false;
        if (StringUtils.isNotBlank(columnName) && StringUtils.isNotBlank(tableName)) {
            List columnNames = getColumnNameList(jdbcTemplate, tableName);
            if (columnNames.contains(columnName)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 判断是否存在
     *
     * @param tableName
     * @param data
     * @return
     */
    public boolean checkForUpdate(String tableName, Map data) {
        boolean result = false;
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from " + tableName + " where 1=1 ");
        Set keySet = data.keySet();
        Iterator it = keySet.iterator();
        List list = new ArrayList();

        for (; it.hasNext(); ) {
            String key = (String) it.next();
            sql.append(" and " + key + "=?");
            list.add(data.get(key));
        }
        sql.append(" for update");
        int t = getJdbcTemplate().queryForInt(sql.toString(), list.toArray());
        if (t <= 0) {
            result = true;
        }
        return result;
    }

    public Map checkAndGetValue(String tableName, Map data) {
        Map result = new HashMap();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from " + tableName + " where 1=1 ");
        Set keySet = data.keySet();
        Iterator it = keySet.iterator();
        List list = new ArrayList();

        for (; it.hasNext(); ) {
            String key = (String) it.next();
            sql.append(" and " + key + "=?");
            list.add(data.get(key));
        }
        sql.append(" limit 0,1 for update");
        try {
            result = getJdbcTemplate().queryForMap(sql.toString(), list.toArray());
        } catch (Exception e) {
            logger.error("sql执行错误", e);
        }
        return result;
    }

    /**
     * 插入数据
     *
     * @param tableName 表名
     * @param datas     数据
     */
    public void insertData(String tableName, List<Map> datas) {
        if (datas != null && !datas.isEmpty()) {
            String[] sqls = new String[datas.size()];
            for (int i = 0; i < datas.size(); i++) {
                Map data = datas.get(i);
                if (data != null && !data.isEmpty()) {
                    String sql = "insert into " + tableName;
                    StringBuffer perSql = new StringBuffer();
                    perSql.append("(");
                    StringBuffer lastSql = new StringBuffer();
                    lastSql.append(" values (");
                    Set keySet = data.keySet();
                    Iterator it = keySet.iterator();
                    for (; it.hasNext(); ) {
                        String key = (String) it.next();
                        String valueType = data.get(key).getClass().getSimpleName().toLowerCase();
                        perSql.append(key + ",");
                        if (valueType.equals("string")) {
                            lastSql.append("'" + data.get(key) + "',");
                        } else {
                            lastSql.append(data.get(key) + ",");
                        }

                    }
                    sql = sql + perSql.toString().substring(0, perSql.toString().length() - 1) + ")" + lastSql.toString().substring(0, lastSql.toString().length() - 1) + ")";
                    logger.debug("执行sql:" + sql);
                    sqls[i] = sql;
                }
            }
            getJdbcTemplate().batchUpdate(sqls);
        }
    }

    /**
     * 插入数据
     *
     * @param jdbcTemplate
     * @param tableName    表名
     * @param datas        数据
     */
    public void insertData(JdbcTemplate jdbcTemplate, String tableName, List<Map> datas) {
        if (datas != null && !datas.isEmpty()) {
            String[] sqls = new String[datas.size()];
            for (int i = 0; i < datas.size(); i++) {
                Map data = datas.get(i);
                if (data != null && !data.isEmpty()) {
                    String sql = "insert into " + tableName;
                    StringBuffer perSql = new StringBuffer();
                    perSql.append("(");
                    StringBuffer lastSql = new StringBuffer();
                    lastSql.append(" values (");
                    Set keySet = data.keySet();
                    Iterator it = keySet.iterator();
                    for (; it.hasNext(); ) {
                        String key = (String) it.next();
                        String valueType = data.get(key).getClass().getSimpleName().toLowerCase();
                        perSql.append(key + ",");
                        if (valueType.equals("string")) {
                            lastSql.append("'" + data.get(key) + "',");
                        } else {
                            lastSql.append(data.get(key) + ",");
                        }

                    }
                    sql = sql + perSql.toString().substring(0, perSql.toString().length() - 1) + ")" + lastSql.toString().substring(0, lastSql.toString().length() - 1) + ")";
                    logger.debug("执行sql:" + sql);
                    sqls[i] = sql;
                }
            }
            jdbcTemplate.batchUpdate(sqls);
        }
    }


    /**
     * 插入数据
     *
     * @param tableName 表名
     * @param data      数据
     * @return 记录id
     */
    public int insertData(String tableName, Map data) {
        int result = 0;
        if (data != null && !data.isEmpty()) {
            String sql = "insert into " + tableName;
            StringBuffer perSql = new StringBuffer();
            perSql.append("(");
            StringBuffer lastSql = new StringBuffer();
            lastSql.append(" values (");
            Set keySet = data.keySet();
            Iterator it = keySet.iterator();
            List list = new ArrayList();
            for (; it.hasNext(); ) {
                String key = (String) it.next();
                perSql.append(key + ",");
                lastSql.append("?,");
                list.add(data.get(key));
            }
            sql = sql + perSql.toString().substring(0, perSql.toString().length() - 1) + ")" + lastSql.toString().substring(0, lastSql.toString().length() - 1) + ")";
            logger.debug("执行sql:" + sql);
            result = getJdbcTemplate().update(sql, list.toArray());
        }
        return result;
    }

    /**
     * 插入数据
     *
     * @param jdbcTemplate 数据库链接
     * @param tableName    表名
     * @param data         数据
     * @return 记录id
     */
    public int insertData(JdbcTemplate jdbcTemplate, String tableName, Map data) {
        int result = 0;
        if (data != null && !data.isEmpty()) {
            String sql = "insert into " + tableName;
            StringBuffer perSql = new StringBuffer();
            perSql.append("(");
            StringBuffer lastSql = new StringBuffer();
            lastSql.append(" values (");
            Set keySet = data.keySet();
            Iterator it = keySet.iterator();
            List list = new ArrayList();
            for (; it.hasNext(); ) {
                String key = (String) it.next();
                perSql.append(key + ",");
                lastSql.append("?,");
                list.add(data.get(key));
            }
            sql = sql + perSql.toString().substring(0, perSql.toString().length() - 1) + ")" + lastSql.toString().substring(0, lastSql.toString().length() - 1) + ")";
            logger.debug("执行sql:" + sql);
            result = jdbcTemplate.update(sql, list.toArray());
        }
        return result;
    }

    /**
     * 更新数据库
     *
     * @param tableName
     * @param set
     * @param where
     * @param noWhere
     */
    public int updateData(JdbcTemplate jdbcTemplate, String tableName, Map set, Map where, boolean noWhere) {
        int result = 0;
        if ((noWhere && StringUtils.isNotBlank(tableName) && set != null && !set.isEmpty()) ||
                (!noWhere && StringUtils.isNotBlank(tableName) && set != null && !set.isEmpty() && where != null && !where.isEmpty())
                ) {
            String sql = "update  " + tableName + " set ";
            StringBuffer setSql = new StringBuffer();
            StringBuffer whereSql = new StringBuffer(" where 1=1 ");
            Set setSet = set.keySet();
            Iterator<String> it = setSet.iterator();
            List list = new ArrayList();
            for (; it.hasNext(); ) {
                String setKey = it.next();
                setSql.append(setKey + "=?,");
                list.add(set.get(setKey));

            }

            Set whereSet = where.keySet();
            it = whereSet.iterator();
            for (; it.hasNext(); ) {
                String whereKey = it.next();
                whereSql.append(" and " + whereKey + "=?");
                list.add(where.get(whereKey));
            }
            sql = sql + setSql.toString().substring(0, setSql.toString().length() - 1) + whereSql;
            logger.debug("执行sql:" + sql);
            result = jdbcTemplate.update(sql, list.toArray());
        }
        return result;
    }

    /**
     * 更新数据库
     *
     * @param tableName
     * @param set
     * @param where
     * @param noWhere
     */
    public int updateData(String tableName, Map set, Map where, boolean noWhere) {
        int result = 0;
        if ((noWhere && StringUtils.isNotBlank(tableName) && set != null && !set.isEmpty()) ||
                (!noWhere && StringUtils.isNotBlank(tableName) && set != null && !set.isEmpty() && where != null && !where.isEmpty())
                ) {
            String sql = "update  " + tableName + " set ";
            StringBuffer setSql = new StringBuffer();
            StringBuffer whereSql = new StringBuffer(" where 1=1 ");
            Set setSet = set.keySet();
            Iterator<String> it = setSet.iterator();
            List list = new ArrayList();
            for (; it.hasNext(); ) {
                String setKey = it.next();
                setSql.append(setKey + "=?,");
                list.add(set.get(setKey));

            }

            Set whereSet = where.keySet();
            it = whereSet.iterator();
            for (; it.hasNext(); ) {
                String whereKey = it.next();
                whereSql.append(" and " + whereKey + "=?");
                list.add(where.get(whereKey));
            }
            sql = sql + setSql.toString().substring(0, setSql.toString().length() - 1) + whereSql;
            logger.debug("执行sql:" + sql);
            result = getJdbcTemplate().update(sql, list.toArray());
        }
        return result;
    }

    public List<Map<String, Object>> query(String sql, List<Object> args)throws DaoException {
        return (List<Map<String, Object>>) getJdbcTemplate().query(sql,args == null ? new Object[0] : args.toArray(), new ColumnMapRowMapper());
    }

    /**
     * MySql 分页查询
     *
     * @param sql
     * @param args
     * @param pageSize
     * @param pageIndex
     * @param rowMapper
     * @return
     * @throws cn.com.yto56.coresystem.common.stl.framework.db.exception.DaoException
     */
    public IPage<?> queryByPageMS(String sql, List<Object> args, int pageSize, int pageIndex, final Map<String, String> sortMap, RowMapper rowMapper) throws DaoException {
        return queryByPageMS(this.getJdbcTemplate(), sql, args, pageSize, pageIndex, sortMap, rowMapper);
    }

    public IPage<?> queryByPageMS(JdbcTemplate jdbcTemplate, String sql, List<Object> args, int pageSize, int pageIndex, final Map<String, String> sortMap, RowMapper rowMapper) throws DaoException {
        QueryPage queryPage = new QueryPage(pageSize, pageIndex);
        queryPage.setSqlString(sql);
        if (args != null) {
            for (Object arg : args) {
                queryPage.addQueryCondition(arg);
            }
        }
        queryPage.setRowMapper(rowMapper);
        queryPage.setSortMap(sortMap);
        return queryByPageMS(jdbcTemplate, queryPage);
    }

    /**
     * MySql 分页查询
     *
     * @param queryPage 查询
     * @return
     * @throws cn.com.yto56.coresystem.common.stl.framework.db.exception.DaoException
     */
    public IPage<?> queryByPageMS(JdbcTemplate jdbcTemplate, QueryPage queryPage) throws DaoException {
        String sql = queryPage.getSqlString();
        List args = queryPage.getAllNotNullArg();
        int pageSize = queryPage.getPageSize();
        int pageIndex = queryPage.getPageIndex();
        RowMapper rowMapper = queryPage.getRowMapper();
        boolean calCount = queryPage.isCalCount();
        Map<String, String> sortMap = queryPage.getSortMap();
        List<PageFooterColumn> footers = new ArrayList<PageFooterColumn>();
        List<Object> params = new ArrayList<Object>();
        if (args != null && args.size() > 0) {
            for (Object arg : args) {
                if ((arg instanceof PageFooterColumn))
                    footers.add((PageFooterColumn) arg);
                else
                    params.add(arg);
            }
        }

        // 汇总
        Map<String, Object> aggregations = null;
        Integer total = pageSize;
        if (calCount) {
            aggregations = aggregateMS(jdbcTemplate, sql, params, footers);
            total = Integer.valueOf(aggregations.get(COUNT).toString());
        }

        // 如果有排序条件，在sqlString后添加order by
        StringBuffer sbSql = new StringBuffer(sql);
        if (sortMap != null && !sortMap.isEmpty()) {
            sbSql.append(ORDER_BY);
            int i = 0;
            for (Iterator it = sortMap.keySet().iterator(); it.hasNext(); ) {
                Object o = it.next();
                String fieldName = o.toString();
                String orderType = sortMap.get(o.toString()).toString();
                if (i > 0) {
                    sbSql.append(COMMA);
                }
                if (ASC.equalsIgnoreCase(orderType)) {
                    sbSql.append(fieldName).append(SPACE).append(ASC);
                } else {
                    sbSql.append(fieldName).append(SPACE).append(DESC);
                }
                i++;
            }

        }

        // 翻页
        int newPageIndex = pageIndex - 1;
        if (calCount) {
            if (pageIndex < 0) {
                newPageIndex = 0;
            } else if (total > 0 && total <= pageSize * (pageIndex - 1)) {
                newPageIndex = total % pageSize == 0 ? total / pageSize - 1 : total / pageIndex;
            }
        }

        params.add(pageSize * newPageIndex); // FirstResult
        params.add(pageSize); // MaxResults
        Page page = new Page(jdbcTemplate.query(sbSql + " limit ?,? ", params.toArray(), rowMapper), total, pageSize, newPageIndex);
        page.setUserdata(aggregations);
        return page;
    }

    /**
     * MySql 分页查询
     *
     * @param sql
     * @param args
     * @param footers
     * @return
     * @throws cn.com.yto56.coresystem.common.stl.framework.db.exception.DaoException
     */
    public Map<String, Object> aggregateMS(JdbcTemplate jdbcTemplate, String sql, List<Object> args, List<PageFooterColumn> footers) {
        StringBuilder aggSql = new StringBuilder();
        aggSql.append("select count(*) as ");
        aggSql.append(COUNT).append(SPACE);
        if (footers != null) {
            for (PageFooterColumn column : footers) {
                aggSql.append(",").append(column.getAggExpression()).append(" as ").append(column.getName());
            }
        }
        aggSql.append(" from (").append(sql).append(") t ");
        if (logger.isDebugEnabled()) {
            logger.debug("aggSql:" + aggSql.toString());
        }
        Map<String, Object> map = jdbcTemplate.queryForMap(aggSql.toString(), args.toArray());
        if (footers != null) {
            for (PageFooterColumn column : footers) {
                String key = column.getName().toUpperCase();
                Object value = map.get(key);
                map.remove(key);
                map.put(column.getName(), value);
            }
        }
        return map;
    }

}
