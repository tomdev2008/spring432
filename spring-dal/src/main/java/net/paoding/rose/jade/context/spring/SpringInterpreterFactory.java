package net.paoding.rose.jade.context.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import net.paoding.rose.jade.statement.DefaultInterpreterFactory;
import net.paoding.rose.jade.statement.Interpreter;
import net.paoding.rose.jade.statement.InterpreterComparator;
import net.paoding.rose.jade.statement.InterpreterFactory;
import net.paoding.rose.jade.statement.StatementMetaData;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * jade在spring下的解析器工厂实现,负责Interpreter实现类的解析工作
 */
public class SpringInterpreterFactory implements InterpreterFactory, ApplicationContextAware {

    private DefaultInterpreterFactory interpreterFactory;

    private ListableBeanFactory beanFactory;

    public SpringInterpreterFactory() {
    }

    public SpringInterpreterFactory(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanFactory = applicationContext;
    }

    @Override
    public Interpreter[] getInterpreters(StatementMetaData metaData) {
        if (interpreterFactory == null) {
            init();
        }
        return interpreterFactory.getInterpreters(metaData);
    }

    private void init() {
        synchronized (this) {
            if (interpreterFactory == null) {
                Map<String, Interpreter> map = beanFactory.getBeansOfType(Interpreter.class);
                ArrayList<Interpreter> interpreters = new ArrayList<Interpreter>(map.values());
                Collections.sort(interpreters, new InterpreterComparator());
                interpreterFactory = new DefaultInterpreterFactory(interpreters);
            }
        }
    }

}
