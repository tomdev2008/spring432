package net.paoding.rose.jade.statement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StatementRuntimeImpl implements StatementRuntime {

    private final StatementMetaData metaData;

    private final Map<String, Object> parameters;

    private String sql;

    private Object[] args;

    private Map<String, Object> properties;

    public StatementRuntimeImpl(StatementMetaData metaData, Map<String, Object> parameters) {
        this.metaData = metaData;
        this.parameters = parameters;
        this.sql = metaData.getSQL();
    }

    @Override
    public StatementMetaData getMetaData() {
        return metaData;
    }

    @Override
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public void setSQL(String sql) {
        this.sql = sql;
    }

    @Override
    public String getSQL() {
        return sql;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public Map<String, Object> getProperties() {
        if (properties == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(this.properties);
    }

    @Override
    public void setProperty(String name, Object value) {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
        this.properties.put(name, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProperty(String name) {
        return (T) (properties == null ? null : properties.get(name));
    }

}
