package com.hbyd.parks.common.hql;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A predicate is the part of the HQL sentence following the " from ClassName "
 * sentence
 * Example usage:
 * <pre>
 * {@code
 * HqlQuery query = new HqlQuery( "from MyClass obj" );
 * query
 * .and( Predicate.eq( "property", value )
 * .and( Predicate.ilike("property", value+"%" )
 * .and( Predicate.eq("prop1", value )
 * .or( Predicate.eq( "prop2", value )));
 * }
 * </pre>
 * @author ren_xt
 */
public class Predicate {
    private static Logger log = Logger.getLogger( Predicate.class );
    private String predicate;
    private List<Object> paramValues;

    public Predicate()
    {
        this.predicate = "";
        paramValues = new ArrayList<Object>();
    }
    public Predicate( String predicate )
    {
        this.predicate = (predicate == null ? "" : predicate);
        paramValues = new ArrayList();
    }

    public Predicate( String predicate, Object... paramValues )
    {
        this.predicate = (predicate == null ? "" : predicate);
        this.paramValues = new ArrayList();
        this.paramValues.addAll( Arrays.asList( paramValues ) );
    }

    public Predicate and( Predicate pg )
    {
        if( predicate.length() == 0 )
            this.predicate = " WHERE " + enclose(pg.getQueryString());
        else
            this.predicate += " AND " + enclose(pg.getQueryString());
        this.paramValues.addAll( Arrays.asList( pg.getParametersValue() ) );
        return this;
    }

    /**自定义实现：将多个 Predicate 复合为一个 Predicate
     * @param ps Predicate 数组
     * @return 合成后的 Predicate
     */
    public static Predicate and(Predicate... ps){
        StringBuilder sb = new StringBuilder();
        List paramValues = new ArrayList();

        String logicStr = " and ";
        sb.append("(");
        for (Predicate p : ps) {
            sb.append(p.getQueryString());
            sb.append(logicStr);
            for (Object o : p.getParametersValue()) {
                paramValues.add(o);
            }
        }
        sb.setLength(sb.length() - logicStr.length());//去掉最后的逻辑符号
        sb.append(")");

        return new Predicate( sb.toString(),
                paramValues.toArray() );
    }

    /**自定义实现：将多个 Predicate 复合为一个 Predicate
     * @param ps Predicate 数组
     * @return 合成后的 Predicate
     */
    public static Predicate or(Predicate... ps){
        StringBuilder sb = new StringBuilder();
        List paramValues = new ArrayList();

        String logicStr = " or ";
        sb.append("(");
        for (Predicate p : ps) {
            sb.append(p.getQueryString());
            sb.append(logicStr);
            for (Object o : p.getParametersValue()) {
                paramValues.add(o);
            }
        }
        sb.setLength(sb.length() - logicStr.length());//去掉最后的逻辑符号
        sb.append(")");

        return new Predicate( sb.toString(),
                paramValues.toArray() );
    }

    public Predicate or( Predicate pg )
    {
        if( predicate.length() == 0 )
            this.predicate = " WHERE " + enclose(pg.getQueryString());
        else
            this.predicate += " OR " + enclose(pg.getQueryString());
        this.paramValues.addAll( Arrays.asList( pg.getParametersValue() ) );
        return this;
    }

    private String enclose( String src )
    {
        return "(" + src + ")";
    }

    public static Predicate enclose( Predicate pg )
    {
        return new Predicate( "(" + pg.getQueryString() + ")",
                pg.getParametersValue() );
    }

    /**
     * 等于
     * @param expression
     * @param value
     * @return
     */
    public static Predicate eq( String expression, Object value )
    {
        return new Predicate( expression + " = ?", value );
    }

    /**
     * 不等于
     * @param expression
     * @param value
     * @return
     */
    public static Predicate neq( String expression, Object value )
    {
        return new Predicate( expression + " != ?", value );
    }

    /**
     * 小于
     * @param expression
     * @param value
     * @return
     */
    public static Predicate lt(String expression, Object value){
        return new Predicate(expression + " < ?", value);
    }

    /**
     * 小于等于
     * @param expression
     * @param value
     * @return
     */
    public static Predicate le(String expression, Object value){
        return new Predicate(expression + " <= ?", value);
    }

    /**
     * 大于
     * @param expression
     * @param value
     * @return
     */
    public static Predicate gt(String expression, Object value){
        return new Predicate(expression + " > ?", value);
    }

    /**
     * 大于等于
     * @param expression
     * @param value
     * @return
     */
    public static Predicate ge(String expression, Object value){
        return new Predicate(expression + " >= ?", value);
    }

    /**
     * 模糊匹配
     * @param expression
     * @param value
     * @return
     */
    public static Predicate like( String expression, String value )
    {
        if( value == null )
            value = "";
        return new Predicate( expression + " like ?", value );
    }

    /**
     * like ignore case
     * @param expression 表达式
     * @param value 目标值
     * @return 谓词、断言
     */
    public static Predicate ilike( String expression, String value )
    {
        if( value == null )
            value = "";
        return new Predicate( "upper(" + expression + ") like ?",
                value.toUpperCase() );
    }

    public static Predicate in( String expression, Object[] values )
    {
        String cond = expression + " in (";
        for( Object o : values )
        {
            cond += (cond.endsWith("(") ? "?" : ",?" );
        }
        cond += ")";
        return new Predicate( cond, values );
    }

    @Override
    public String toString()
    {
        return getQueryString();
    }

    public String getQueryString()
    {
        return predicate;
    }

    public Object[] getParametersValue()
    {
        return paramValues.toArray();
    }

    /**
     * 获取 where clause
     * @return
     */
    public String getPredicate() {
        return predicate;
    }
}