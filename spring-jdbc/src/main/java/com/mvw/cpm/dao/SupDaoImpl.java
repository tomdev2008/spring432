package com.mvw.cpm.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * 查询封装
 */
public class SupDaoImpl<T> implements ResultSetExtractor<T> {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public T getById(String sql, Object... args) throws Exception {
		return jdbcTemplate.query(sql, args, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T extractData(ResultSet rs) throws SQLException, DataAccessException {
		try {
			Type sType = getClass().getGenericSuperclass();
			Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
			Class<T> c = (Class<T>) (generics[0]);
			T t = (T) c.newInstance();
			initObject(t, rs, c);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//反射初始化对象
	protected void initObject(T t, ResultSet rs, Class<T> clazz) throws Exception {
		if (rs.next()) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				String cName = getCname(f);
				//获得对应的查询内容
				Object paramVal = null;
				Class<?> clazzField = f.getType();
				if (clazzField == String.class) {
					paramVal = rs.getString(cName);
				} else if (clazzField == short.class || clazzField == Short.class) {
					paramVal = rs.getShort(cName);
				} else if (clazzField == int.class || clazzField == Integer.class) {
					paramVal = rs.getInt(cName);
				} else if (clazzField == long.class || clazzField == Long.class) {
					paramVal = rs.getLong(cName);
				} else if (clazzField == float.class || clazzField == Float.class) {
					paramVal = rs.getFloat(cName);
				} else if (clazzField == double.class || clazzField == Double.class) {
					paramVal = rs.getDouble(cName);
				} else if (clazzField == boolean.class || clazzField == Boolean.class) {
					paramVal = rs.getBoolean(cName);
				} else if (clazzField == byte.class || clazzField == Byte.class) {
					paramVal = rs.getByte(cName);
				} else if (clazzField == char.class || clazzField == Character.class) {
					paramVal = rs.getCharacterStream(cName);
				} else if (clazzField == Date.class) {
					paramVal = rs.getTimestamp(cName);
				}
				//调用set方法设置值
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), t.getClass());
				pd.getWriteMethod().invoke(t, paramVal);
			}
		}
	}

	/**
	 * 反射获得列名，可以加注解或查询时指定别名
	 * 
	 * @param f
	 * @return
	 */
	private String getCname(Field f) {
		String cName = f.getName();
		
		/*
		Column cl = f.getAnnotation(Column.class);
		if (cl != null && !StringUtils.isBlank(cl.value())) {
			cName = cl.value();
		}*/
		return cName;
	}
}
