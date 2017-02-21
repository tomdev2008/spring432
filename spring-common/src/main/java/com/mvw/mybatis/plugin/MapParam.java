package com.mvw.mybatis.plugin;

import java.util.HashMap;

public class MapParam extends HashMap<String, String> {

	private static final long serialVersionUID = 8610783515514005448L;

	public static final String KEY_FIELD = "mapKeyField";

	public static final String VALUE_FIELD = "mapValueField";

	public MapParam() {

	}

	public MapParam(String keyField, String valueField) {
		this.put(KEY_FIELD, keyField);
		this.put(VALUE_FIELD, valueField);
	}

	public String getKeyField() {
		return get(KEY_FIELD);
	}

	public String getValueField() {
		return get(VALUE_FIELD);
	}
}
