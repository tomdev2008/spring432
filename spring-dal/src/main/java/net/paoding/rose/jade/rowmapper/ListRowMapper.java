package net.paoding.rose.jade.rowmapper;

import java.util.ArrayList;
import java.util.Collection;

import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 将SQL结果集的一行映射为List
 * 
 */
@SuppressWarnings({"rawtypes"})
public class ListRowMapper extends AbstractCollectionRowMapper {

    public ListRowMapper(StatementMetaData modifier) {
        super(modifier);
    }

    @Override
    protected Collection createCollection(int columnSize) {
        return new ArrayList(columnSize);
    }
}
