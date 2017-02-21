package net.paoding.rose.jade.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import net.paoding.rose.jade.statement.StatementMetaData;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 将SQL结果集的一行映射为某种集合
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractCollectionRowMapper implements RowMapper {

    private Class<?> elementType;

    public AbstractCollectionRowMapper(StatementMetaData modifier) {
        Class<?>[] genericTypes = modifier.getGenericReturnTypes();
        if (genericTypes.length < 1) {
            throw new IllegalArgumentException("Collection generic");
        }
        elementType = genericTypes[0];
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        int columnSize = rs.getMetaData().getColumnCount();
        Collection collection = createCollection(columnSize);
        // columnIndex从1开始
        for (int columnIndex = 1; columnIndex <= columnSize; columnIndex++) {
            collection.add(JdbcUtils.getResultSetValue(rs, columnIndex, elementType));
        }
        return collection;
    }

    /**
     * 由子类覆盖此方法，提供一个空的具体集合实现类
     * 
     * @param columnSize
     * @return
     */
    protected abstract Collection createCollection(int columnSize);

}
