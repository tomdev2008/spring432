package net.paoding.rose.jade.statement;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.SQLType;
import net.paoding.rose.jade.dataaccess.DataAccess;
import net.paoding.rose.jade.dataaccess.DataAccessFactory;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

/**
 * 实现 SELECT 查询。
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class SelectQuerier implements Querier {

    private final RowMapper rowMapper;

    private final Class<?> returnType;

    private final DataAccessFactory dataAccessProvider;

    public SelectQuerier(DataAccessFactory dataAccessProvider, StatementMetaData metaData,
            RowMapper rowMapper) {
        this.dataAccessProvider = dataAccessProvider;
        this.returnType = metaData.getMethod().getReturnType();
        this.rowMapper = rowMapper;
    }

    @Override
    public Object execute(SQLType sqlType, StatementRuntime... runtimes) {
    	// 注意只取了第一个  并且强转
        return execute(sqlType, (StatementRuntime) runtimes[0]);
    }

    public Object execute(SQLType sqlType, StatementRuntime runtime) {
    	
    	//获取数据元时带了方法的元信息，这点很重要
        DataAccess dataAccess = dataAccessProvider.getDataAccess(
        	runtime.getMetaData(), 
        	runtime.getProperties());
        
        // 执行查询
        List<?> listResult = dataAccess.select(runtime.getSQL(), runtime.getArgs(), rowMapper);
        final int sizeResult = listResult.size();

        // 将 Result 转成方法的返回类型
        if (returnType.isAssignableFrom(List.class)) {

            // 返回  List 集合
            return listResult;

        } else if (returnType.isArray() && byte[].class != returnType) {
            Object array = Array.newInstance(returnType.getComponentType(), sizeResult);
            if (returnType.getComponentType().isPrimitive()) {
                int len = listResult.size();
                for (int i = 0; i < len; i++) {
                    Array.set(array, i, listResult.get(i));
                }
            } else {
                listResult.toArray((Object[]) array);
            }
            return array;

        } else if (Map.class.isAssignableFrom(returnType)) {
            // 将返回的  KeyValuePair 转换成  Map 对象
            // 因为entry.key可能为null，所以使用HashMap
            Map<Object, Object> map;
            if (returnType.isAssignableFrom(HashMap.class)) {

                map = new HashMap<Object, Object>(listResult.size() * 2);

            } else if (returnType.isAssignableFrom(Hashtable.class)) {

                map = new Hashtable<Object, Object>(listResult.size() * 2);

            } else {

                throw new Error(returnType.toString());
            }
            for (Object obj : listResult) {
                if (obj == null) {
                    continue;
                }

                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) obj;

                if (map.getClass() == Hashtable.class && entry.getKey() == null) {
                    continue;
                }

                map.put(entry.getKey(), entry.getValue());
            }

            return map;

        } else if (returnType.isAssignableFrom(HashSet.class)) {

            // 返回  Set 集合
            return new HashSet<Object>(listResult);

        } else {

            if (sizeResult == 1) {
                // 返回单个  Bean、Boolean等类型对象
                return listResult.get(0);

            } else if (sizeResult == 0) {

                // 基础类型的抛异常，其他的返回null
                if (returnType.isPrimitive()) {
                    String msg = "Incorrect result size: expected 1, actual " + sizeResult + ": " + runtime.getMetaData();
                    throw new EmptyResultDataAccessException(msg, 1);
                } else {
                    return null;
                }

            } else {
                // IncorrectResultSizeDataAccessException
                String msg = "Incorrect result size: expected 0 or 1, actual " + sizeResult + ": " + runtime.getMetaData();
                throw new IncorrectResultSizeDataAccessException(msg, 1, sizeResult);
            }
        }
    }
}
