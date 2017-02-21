package com.mvw.cpm.persistent;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * 表外键策略
 * 
 * @author gaotingping
 *
 * 2016年12月19日 下午1:36:48
 */
public class MyNamingStrategy extends ImprovedNamingStrategy {

    private static final long serialVersionUID = 5229268684374514538L;

	private static final String TABLE_NAME_PREFIX = "gtp_t_";

	private static final String FOREIGN_KYE_PREFIX = "fk_";

	/**
	 * 类名映射表名
	 */
	@Override
	public String classToTableName(String tableName) {
		return TABLE_NAME_PREFIX + addUnderscores(tableName);
	}

	/**
	 * 多对多映射
	 */
	@Override
	public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
		if (associatedEntityTable == null) {
			return tableName(TABLE_NAME_PREFIX + ownerEntityTable + '_' + propertyToColumnName(propertyName));
		}
		return tableName(TABLE_NAME_PREFIX + ownerEntityTable + "_" + associatedEntityTable);
	}

	/**
	 * 外键映射列名
	 */
	@Override
	public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
		return columnName(FOREIGN_KYE_PREFIX + propertyTableName+"_"+referencedColumnName);
	}
}
