package net.paoding.rose.jade.statement;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.SQLType;

import org.apache.commons.lang.ClassUtils;

/**
 * 
 * {@link JdbcStatement} 封装了jdbc的访问逻辑
 */
public class JdbcStatement implements Statement {

    private final StatementMetaData metaData;

    private final Interpreter[] interpreters;

    private final Querier querier;

    private final boolean batchUpdate;

    private final SQLType sqlType;

    public JdbcStatement(StatementMetaData statementMetaData, SQLType sqlType,
            Interpreter[] interpreters, Querier querier) {
        this.metaData = statementMetaData;
        this.interpreters = (interpreters == null) ? new Interpreter[0] : interpreters;
        this.querier = querier;
        this.sqlType = sqlType;
        if (sqlType == SQLType.WRITE) {
            Method method = statementMetaData.getMethod();
            Class<?>[] types = method.getParameterTypes();
            Class<?> returnType = method.getReturnType();
            if (returnType.isPrimitive()) {
                returnType = ClassUtils.primitiveToWrapper(returnType);
            }
            if (types.length > 0 && List.class.isAssignableFrom(types[0])) {
                this.batchUpdate = true;
                if (returnType != void.class && returnType != int[].class
                        && returnType != Integer[].class && returnType != Integer.class) {
                    throw new IllegalArgumentException("error return type:"
                            + method.getDeclaringClass().getName() + "#" + method.getName() + "-->"
                            + returnType);
                }
            } else {
                this.batchUpdate = false;
                if (returnType != void.class && returnType != Boolean.class
                        && !Number.class.isAssignableFrom(returnType)) {
                    throw new IllegalArgumentException("error return type:"
                            + method.getDeclaringClass().getName() + "#" + method.getName() + "-->"
                            + returnType);
                }
            }
        } else {
            this.batchUpdate = false;
        }
    }

    @Override
    public StatementMetaData getMetaData() {
        return metaData;
    }

    @Override
    public Object execute(Map<String, Object> parameters) {
        if (batchUpdate) {
            
            Iterable<?> iterable = (Iterable<?>) parameters.get(":1");
            Iterator<?> iterator = (Iterator<?>) iterable.iterator();
            List<StatementRuntime> runtimes = new LinkedList<StatementRuntime>();
            while (iterator.hasNext()) {
                Object arg = iterator.next();
                HashMap<String, Object> clone = new HashMap<String, Object>(parameters);

                clone.put(":1", arg);
                if (metaData.getSQLParamAt(0) != null) {
                    clone.put(metaData.getSQLParamAt(0).value(), arg);
                }
                StatementRuntime runtime = new StatementRuntimeImpl(metaData, clone);
                //预设了拦截接口，方便用户扩展
                for (Interpreter interpreter : interpreters) {
                    interpreter.interpret(runtime);
                }
                runtimes.add(runtime);
            }
            return querier.execute(sqlType, runtimes.toArray(new StatementRuntime[0]));
        } else {
            StatementRuntime runtime = new StatementRuntimeImpl(metaData, parameters);
            for (Interpreter interpreter : interpreters) {
                interpreter.interpret(runtime);
            }
            return querier.execute(sqlType, runtime);
        }
    }

}
