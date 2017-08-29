package cn.com.yto56.coresystem.common.stl.framework.db;


public class PageFooterColumn {

    /**
     * 查询列别名，与jqgrid中colModel中name保持一致
     */
    private String name;
    /**
     * 聚合函数表达式，如SUM(FEE),MAX(NO),count(xx),case xx then xx else xx等
     */
    private String aggExpression;

    /**
     * 用于HQL
     *
     * @param name
     * @param aggExpression
     */
    public PageFooterColumn(String name, String aggExpression) {
        this.name = name;
        this.aggExpression = aggExpression;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAggExpression() {
        return aggExpression;
    }

    public void setAggExpression(String aggExpression) {
        this.aggExpression = aggExpression;
    }

}
