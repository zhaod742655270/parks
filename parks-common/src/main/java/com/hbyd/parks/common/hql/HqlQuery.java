package com.hbyd.parks.common.hql;

/**
 * <pre>
 * HQL 的构成：
 *
 *| incipit  |     header     | predicate |    footer    |
 *|----------|----------------|-----------|--------------|
 *| from ... | inner join ... | where ... | order by ... |
 *</pre>
 *
 * @author ren_xt
 */
public class HqlQuery extends Predicate {
    /**
     * HQL 开头的部分，即：from clause, eg: from Employee e
     */
    String incipit;

    //following the incipit, usually contains joins
    String header = "";

    //closing the query: order by and other things
    String footer = "";

    //distinct predicate
    String distinct;

    boolean count = false;

    public enum SortOrder { ASC, DESC };

    public HqlQuery( Class entityClass )
    {
        super();
        this.incipit = "from "
                + entityClass.getName()
                + " obj ";//default nickname
    }

    /**
     * The incipit can start with "from  " or
     * with the less common "select  from  ..."
     */
    public HqlQuery( String incipit )
    {
        super();
        this.incipit = incipit;
    }

    /**
     * Inner Join
     * @param expression what to join with
     * @param alias Aliased name for this join
     * @return
     */
    public HqlQuery join( String expression, String alias )
    {
        header += " join " + expression + " as " + alias;
        return this;
    }

    public HqlQuery leftJoin( String expression, String alias )
    {
        header += " left join " + expression + " as " + alias;
        return this;
    }

    public HqlQuery rightJoin( String expression, String alias )
    {
        header += " right join " + expression + " as " + alias;
        return this;
    }

    public HqlQuery count()
    {
        this.count = true;
        return this;
    }

    public boolean hasCount(){ return count; }

    /**
     * Useful when we want enable/disable counting
     * @param wantsCount true/false
     * @return this same query object, for chaining calls.
     */
    public HqlQuery count(boolean wantsCount )
    {
        this.count = wantsCount;
        return this;
    }

    /**
     * 组建 from clause
     * @return
     */
    private String getIncipit()
    {
        String ret = (incipit == null ? "" : incipit + " " );
        if( distinct == null
                || ret.trim().toUpperCase().startsWith( "SELECT" ) )
        {
            //unable to apply distinct!.
            return ret;
        }
        return "select " + (count ? " count(" : "" )
                + (distinct == null ? "obj" : " distinct " + distinct )
                + (count ? ")" : "" ) + " " + incipit;
    }

    @Override
    public String getQueryString()
    {
        return getIncipit() + header + super.getQueryString() + footer;
    }

    @Override
    public String toString()
    {
        return getQueryString();
    }

    public HqlQuery addOrderBy( String orderBy )
    {
        footer += (footer.length() == 0 ? " ORDER BY " : "," ) + orderBy;
        return this;
    }

    public HqlQuery distinct()
    {
        return distinct(null);
    }

    /**
     * NOTE:distinct is applicable ONLY when the query is NOT already
     *           starting with SELECT statement..
     * @param alias aliased name on which to apply the distinct
     *   as in "select distinct(<alias>) from MyTable <alias>"
     * @return
     */
    public HqlQuery distinct( String alias )
    {
        this.distinct = alias;
        return this;
    }

    /**
     * utility method to handle order by clauses.
     * @param sortProperty the property to apply the sorting to.
     * @param sortOrder see enum at class start
     * @param entityAlias  alias to be used for table. defaults to 'obj'
     * @return
     */
    public HqlQuery order( String sortProperty, String sortOrder
            , String entityAlias )
    {
        //esamino ordinamento
        if( sortProperty == null )
            return this; //no ordering.

        String orderType = "ASC";

        //obj prefix, to avoid ambiguity in the query with joins
        sortProperty = ( entityAlias != null ? (entityAlias + ".") : "" )
                + sortProperty;

        orderType = "ASC".equalsIgnoreCase( sortOrder ) ?
                "ASC" : "DESC";
        addOrderBy( sortProperty + " " + orderType );
        return this;
    }

    /**
     * utility method to handle order by clauses.
     * @param sortProperty the property to apply the sorting to.
     * @param sortOrder see enum at class start
     * @return
     */
    public HqlQuery order( String sortProperty, String sortOrder )
    {
        return order( sortProperty, sortOrder, "obj" );
    }

    /**
     * Same as above, but using the Enum
     */
    public HqlQuery order( String sortProperty, SortOrder sortOrder )
    {
        return order( sortProperty, sortOrder.name() );
    }

    public boolean hasOrder() {
        return footer.contains( "ORDER BY" );
    }

}