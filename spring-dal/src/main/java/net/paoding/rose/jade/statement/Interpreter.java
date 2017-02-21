package net.paoding.rose.jade.statement;

import org.springframework.core.annotation.Order;

/**
 * 可用 {@link Order}来调节优先级，根据 {@link Order} 语义，值越小越有效；
 * <p>
 * 
 * 如果没有标注 {@link Order} 使用默认值0。
 * <p>
 * 
 * 可以理解为这是jade的拦截器
 * <p>
 * 
 */
//按Spring语义规定，Order值越高该解释器越后执行
@Order(0)
public interface Interpreter {

    /**
     * 
     * @param runtime
     */
    void interpret(StatementRuntime runtime);

}
