package net.paoding.rose.jade.statement;

import net.paoding.rose.jade.annotation.SQLType;

public interface Querier {

    Object execute(SQLType sqlType, StatementRuntime... runtimes);

}
