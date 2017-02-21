package net.paoding.rose.jade.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用{@link SQLParam} 注解标注DAO方法的参数，指定参数的名称
 * 使得可以在SQL中通过":参数名"的方式使用它。
 * Jade通过PreparedStatment动态地把参数值提交给数据库执行。
 * <p>
 * 
 * <span style='margin-left:50px;'>
 * <code>@SQL("SELECT id, account, name FROM user WHERE id=:userId")<span>
 * <br>
 * <span style='margin-left:50px;'> public User getUser(@SQLParam("userId") String id);</code><span>
 * <p>
 */
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SQLParam {

    /**
     * 指出这个值是 SQL 语句中哪个参数的值
     * 
     * @return 对应 SQL 语句中哪个参数
     */
    String value();
}
