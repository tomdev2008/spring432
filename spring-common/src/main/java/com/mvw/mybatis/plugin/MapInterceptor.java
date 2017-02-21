package com.mvw.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
    <select id="find" resultType="map" parameterType="MapParam">  
       select code, name from t_city  
    </select>  
    
    MapParam param = new MapParam("code", "name");  
    Map<Object, Object> result = cityMapper.find(param);  
    
          总感觉mybatis留的余地太小了
 * @author gaotingping
 *
 * 2016年8月18日 上午11:53:03
 */
@Intercepts(@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }))
public class MapInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		if (target instanceof ResultSetHandler) {
			DefaultResultSetHandler handler = (DefaultResultSetHandler) target;
			ParameterHandler pHandler = Reflect.getFieldValue(handler, "parameterHandler");
			Object paramObj = pHandler.getParameterObject();
			if (paramObj instanceof MapParam) {
				MapParam param = (MapParam) paramObj;

				String keyField = param.getKeyField();
				String valueField = param.getValueField();	
				if (valueField == null) {
					return handleKeyResult(invocation.proceed(), keyField);
				} else {
					Statement statement = (Statement) invocation.getArgs()[0];
					return handleResultSet(statement.getResultSet(), keyField, valueField);
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
	
	private Object handleKeyResult(Object resultObj, String keyField) {
		List<?> list = (List<?>) resultObj;
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			Object key = null;
			if (obj instanceof Map<?, ?>) {
				Map<?, ?> tmpMap = (Map<?, ?>) obj;
				key = (Object) tmpMap.get(keyField);
			} else {
				key = Reflect.getFieldValue(obj, keyField);
			}
			map.put(key, obj);
		}
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(map);
		return resultList;
	}

	private Object handleResultSet(ResultSet resultSet, String keyField,String valueField) {
		if (resultSet != null) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			List<Object> resultList = new ArrayList<Object>();
			try {
				while (resultSet.next()) {
					Object key = resultSet.getObject(keyField);
					Object value = resultSet.getObject(valueField);
					map.put(key, value);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			resultList.add(map);
			return resultList;
		}
		return null;
	}
}

class Reflect {

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object obj, String fieldName) {
		Object result = null;
		Field field = getField(obj, fieldName);
		if (field != null) {
			field.setAccessible(true);
			try {
				result = field.get(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (T) result;
	}

	private static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return field;
	}
}
