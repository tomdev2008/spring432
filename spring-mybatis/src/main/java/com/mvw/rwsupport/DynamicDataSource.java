package com.mvw.rwsupport;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

import com.mvw.rwsupport.support.CheckDataSource;
import com.mvw.rwsupport.support.HeartbeatService;
import com.mvw.rwsupport.support.TaskExecutorService;

/**
 * 自定义数据源切换
 * 
 * 所有的扩展，应该做到透明，接口话，方便扩展：开放和扩展是必须的
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private List<DataSource> readDataSources = null;/* 读库 */

	private DataSource writeDataSource;/* 写库 */

	private TaskExecutorService taskExecutorService;

	public AtomicBoolean isError = new AtomicBoolean(false);

	private HeartbeatService heartbeatService;

	private CheckDataSource checkDataSource;

	protected Object determineCurrentLookupKey() {
		return DBContextHolder.getDbType();
	}

	protected DataSource determineTargetDataSource() {

		Assert.notNull(readDataSources, "ReadDataSources router not initialized");
		Assert.notNull(writeDataSource, "WriteDataSource router not initialized");

		Object lookupKey = determineCurrentLookupKey();
		if (DBrwConstants.READ.equals(lookupKey)) {
			return getRandomReadDataSources();
		}

		return writeDataSource;
	}

	/**
	 * 若是写，按照负载策略在预排数据源中拿一个，失败迁移和自动恢复
	 * 
	 * 1.失败迁移:放入失败集合从服务队列删除?何时放入,由谁放入？发现连接错误，给个标记，让认定者去检查，可以后再放入失败集合， 次过程必须异步
	 * 2.失败队列做心跳检查
	 */

	/**
	 * 标记写数据源可能有问题，让哨兵去检查 由@see DefaultSqlSession调用
	 */
	public void remarkReadDataSourcesError() {
		if (!isError.get()) {
			isError.set(true);
			taskExecutorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						remarkReadDataSources();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			System.out.println("正在检查读数据源，暂不处理同类请求!");
		}
	}

	private void remarkReadDataSources() {
		try {
			int errorIndex = -1;
			//找到有问题的
			for (int i = 0; i < readDataSources.size(); i++) {
				boolean s = checkDataSource.isActive(readDataSources.get(i));
				if (!s) {
					errorIndex = i;
					break;
				}
			}
			//删除有问题的
			if (errorIndex > -1) {
				synchronized (readDataSources) {
					DataSource tmpError = readDataSources.get(errorIndex);
					heartbeatService.addErrorDataSources(tmpError);
					readDataSources.remove(errorIndex);
				}
			}
			isError.set(false);
			heartbeatService.startCheck();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DataSource getRandomReadDataSources() {

		Assert.notEmpty(readDataSources, "ReadDataSources router is empty");

		Random r = new Random();
		int key = r.nextInt(readDataSources.size());
		return readDataSources.get(key);
	}

	public List<DataSource> getReadDataSources() {
		return readDataSources;
	}

	public void setReadDataSources(List<DataSource> readDataSources) {
		this.readDataSources = readDataSources;
	}

	public DataSource getWriteDataSource() {
		return writeDataSource;
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}
}
