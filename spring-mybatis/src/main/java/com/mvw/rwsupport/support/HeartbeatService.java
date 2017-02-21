package com.mvw.rwsupport.support;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

/**
 * 心跳维护
 * 
 * @author gaotingping
 *
 *         2016年11月23日 下午5:27:56
 */
public class HeartbeatService {

	private Timer timer;

	private List<DataSource> errorDataSources = null;/* 读库 */

	private static AtomicBoolean isActive = new AtomicBoolean(false);

	public void start(TimerTask task, long period) {
		if (timer == null || !isActive.get()) {
			isActive.set(true);
			timer = new Timer();
			timer.schedule(task, 0, period);
		} else {
			System.out.println("任务正在运行，忽略开启!");
		}
	}

	public void close() {
		if (timer != null) {
			isActive.set(false);
			timer.cancel();
			timer = null;
		}
	}

	public static void main(String[] args) {

		HeartbeatService hs = new HeartbeatService();
		hs.start(new TimerTask() {
			int i = 0;

			@Override
			public void run() {
				System.out.println("1秒执行一次_" + (i++));
				if (i % 2 == 0) {
					try {
						throw new RuntimeException("宝格措");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 1000);
		hs.start(new TimerTask() {
			int i = 0;

			@Override
			public void run() {
				System.out.println("1秒执行一次_" + (i++));
				if (i % 2 == 0) {
					try {
						throw new RuntimeException("宝格措");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 1000);

		int i = 0;
		while (i < 5) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
		hs.close();
		hs.start(new TimerTask() {
			int i = 0;

			@Override
			public void run() {
				System.out.println("1秒执行一次_" + (i++));
				if (i % 2 == 0) {
					try {
						throw new RuntimeException("宝格措");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 1000);
	}

	public void addErrorDataSources(DataSource tmpError) {
		errorDataSources.add(tmpError);
	}

	public void startCheck() {
		if (!errorDataSources.isEmpty()) {
			start(new TimerTask() {
				@Override
				public void run() {

				}
			}, 3000);
		}
	}
}
