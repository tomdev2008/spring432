package net.paoding.rose.jade.statement;

import java.util.Map;

public interface StatementRuntime {

    StatementMetaData getMetaData();

    String getSQL();

    // 一个Statement完全可以被解出多个runtime，他们的sql不同，同时一个对应的args可能有几个（即批量更新）
    Object[] getArgs();

    Map<String, Object> getParameters();

    void setSQL(String sql);

    void setArgs(Object[] args);

    Map<String, Object> getProperties();

    void setProperty(String name, Object value);

    <T> T getProperty(String name);
}
