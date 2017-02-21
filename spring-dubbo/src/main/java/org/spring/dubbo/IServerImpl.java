package org.spring.dubbo;

/**
 * 服务实现
 * 
 * @author gaotingping
 * 
 */
public class IServerImpl implements IService{

	@Override
	public String sayHello(String str) {
		System.out.println("你成功了 str="+str);
		return "ok:"+str;
	}

}
