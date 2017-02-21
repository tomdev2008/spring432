package com.mvw.check;

/**
 * 服务状态检查规范
 * 
 * @author gaotingping
 *
 * 2016年8月25日 下午5:17:32
 */
public interface CheckService {

	public CheckResult checkDB();//数据库
	
	public CheckResult checkRedis();//redis
	
	public CheckResult checkMQ();//MQ
	
	public CheckResult checkTar();//tar
	
	public CheckResult checkBiz();//业务逻辑，可以检查下自己最关注的接口是否正常，建议查询类的
	
	public CheckResult checkSystem();//系统状态:如 cpu,IO,磁盘空间等
}
