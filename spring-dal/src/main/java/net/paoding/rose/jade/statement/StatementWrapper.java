package net.paoding.rose.jade.statement;

public interface StatementWrapper extends Statement {

    /**
     * 
     * @param statement
     */
    void setStatement(Statement statement);

    /**
     * 
     * @return
     */
    Statement getStatement();
}
