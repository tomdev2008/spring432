package net.paoding.rose.jade.rowmapper;

import java.util.Collection;
import java.util.HashSet;

import net.paoding.rose.jade.statement.StatementMetaData;

/**
 * 将SQL结果集的一行映射为Set
 * 
 */
@SuppressWarnings({"rawtypes"})
public class SetRowMapper extends AbstractCollectionRowMapper {

    public SetRowMapper(StatementMetaData modifier) {
        super(modifier);
    }

    @Override
    protected Collection createCollection(int columnSize) {
        return new HashSet(columnSize * 2);
    }
}
