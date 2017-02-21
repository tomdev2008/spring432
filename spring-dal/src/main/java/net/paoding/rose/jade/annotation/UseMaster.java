package net.paoding.rose.jade.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用：{#link UseMaster} 标注需要强制查询 master 数据库的 DAO 接口方法。
 * <p>
 * 
 * 特别强调，代码中并没有对强制使用master库的实现，这个可以自己增强
 * 详细可以参考{ @link MasterSlaveDataSourceFactory}
 * 实现(我已实现过了)
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseMaster {

    /**
     * 是否需要强制查询 master 数据库。
     * 
     * @return 强制查询 master 数据库
     */
    boolean value() default true;
}
