package net.paoding.rose.jade.statement;

/**
 * 解析器工厂，为每个Dao配置相应的解析器
 */
public interface InterpreterFactory {

    Interpreter[] getInterpreters(StatementMetaData metaData);
}
