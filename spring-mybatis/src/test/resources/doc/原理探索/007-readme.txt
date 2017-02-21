#让世界简单和可控
http://mybatis.github.io/mybatis-3/zh/index.html

用最合适的技术去实现，并不断追求最佳实践

#建议
Velocity/springmvc/druid/(mybatis/jade(主从+分表现成的功能)

#每个人都有自己不同的经验积累和偏向,而框架也都有自己的应用场景
#没有绝对的好坏和绝对的优势，只有是否合适

#类型处理器
typeHandlers【BaseTypeHandler-->TypeHandler】
	<typeHandlers>
	  <typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
	</typeHandlers>
	
	// ExampleTypeHandler.java
	@MappedJdbcTypes(JdbcType.VARCHAR)
	public class ExampleTypeHandler extends BaseTypeHandler<String> {
	   ...
	}

#枚举类型的处理
EnumTypeHandler 		--映射为名称
EnumOrdinalTypeHandler	--映射为序列整型
<typeHandlers>
  <typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="java.math.RoundingMode"/>
</typeHandlers>

也可以在映射文件中，指定局部的映射类型处理器
typeHandler="org.apache.ibatis.type.EnumTypeHandler"

#对象工厂
ObjectFactory--每次创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成
<objectFactory type="org.mybatis.example.ExampleObjectFactory">
  <property name="someProperty" value="100"/>
</objectFactory>

#插件-- implements Interceptor
    Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
    ParameterHandler (getParameterObject, setParameters)
    ResultSetHandler (handleResultSets, handleOutputParameters)
    StatementHandler (prepare, parameterize, batch, update, query)
   
	<plugins>
	  <plugin interceptor="org.mybatis.example.ExamplePlugin">
	    <property name="someProperty" value="100"/>
	  </plugin>
	</plugins>

#核心:sqlSessionFactoryBuilder 
sqlSessionFactoryBuilder.build(myConfig)

#配置环境（environments）
	1)夸库
	  MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中
	2)生产，测试，正式
	注意：每个 SqlSessionFactory 实例只能选择其一
	
	<environments default="development">
	  <environment id="development">
	    <transactionManager type="JDBC">
	      <property name="..." value="..."/>
	    </transactionManager>
	    <dataSource type="POOLED">
	      <property name="driver" value="${driver}"/>
	      <property name="url" value="${url}"/>
	      <property name="username" value="${username}"/>
	      <property name="password" value="${password}"/>
	    </dataSource>
	  </environment>
	</environments>

#源码分析
XMLConfigBuilder-->解析配置  -->Configuration全局配置

在config中有new的方法
public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
    ParameterHandler parameterHandler = mappedStatement.getLang().createParameterHandler(mappedStatement,
     parameterObject, boundSql);
    parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler);//xxxAll方法
    return parameterHandler;
  }

#绑定拦截器的:jdk的动态代理
return Plugin.wrap(target, this);

#自己开发或找第三方资源，根据表来生成javaBean以及CRUD配置文件和DAO接口
#那样的话，一切就轻松多了啊，哈哈哈！
#通过元数据分析即可




#整合方式总结
1.自动接口注入
2.SqlSessionTemplate sqlSession;
3.extends SqlSessionDaoSupport
4.private UserMapper userMapper;