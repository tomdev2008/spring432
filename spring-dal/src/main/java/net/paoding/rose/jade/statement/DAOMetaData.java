package net.paoding.rose.jade.statement;

import java.util.Collections;
import java.util.Map;


/**
 * {@link DAOMetaData} 封装缓存一个DAO接口类本身的一些信息，比如类对象、类常量等等
 * 
 */
public class DAOMetaData {

    /**
     * DAO接类
     */
    private final Class<?> daoClass;

    /**
     * 定义在DAO接口上的常量（包含父接口的）
     */
    private final Map<String, ?> constants;

    /**
     * 
     * @param daoClass
     */
    public DAOMetaData(Class<?> daoClass) {
        this.daoClass = daoClass;
        this.constants = Collections.unmodifiableMap(GenericUtils.getConstantFrom(daoClass, true, true));
    }

    public Class<?> getDAOClass() {
        return daoClass;
    }

    public Map<String, ?> getConstants() {
        return constants;
    }

    @SuppressWarnings("unchecked")
    public <T> T getConstant(String fieldName) {
        return (T) constants.get(fieldName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DAOMetaData) {
            DAOMetaData other = (DAOMetaData) obj;
            return daoClass.equals(other.daoClass);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return daoClass.hashCode() * 13;
    }

    @Override
    public String toString() {
        return daoClass.getName();
    }
}
