package org.spring.dubbo.filter;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * dubbo扩展点:RPC过滤器
 * 
 * step1:implements Filter
 * 
 * step2:增加配置:/META-INF/dubbo/com.alibaba.dubbo.rpc.Filter
 *
 * step3:
 * <dubbo:provider filter="myRpcFilter"/>
 * <dubbo:service interface="dubbotest.IService" ref="iService" filter="myRpcFilter"/>
 * 
 * 具体细节参考:
 * http://dubbo.io/Developer+Guide-zh.htm
 * 
 * @author gaotingping
 *
 * 2016年8月29日 下午5:25:11
 */
public class MyRpcFilter implements Filter{

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		
		/*获得方法:获得注解，进行风控验证*/
		/*
		 * invocation=RpcInvocation [methodName=sayHello, parameterTypes=[class java.lang.String], 
		 * arguments=[hello], attachments={dubbo=2.8.4, input=151,
		 *  path=dubbotest.IService, interface=dubbotest.IService, version=0.0.0}]
		 */
		
		System.out.println("invocation="+invocation);
		Result result = invoker.invoke(invocation);
		System.out.println("result="+result);
		
		return result;
	}

}
