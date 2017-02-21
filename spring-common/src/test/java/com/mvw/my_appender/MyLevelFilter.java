package com.mvw.my_appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;

public class MyLevelFilter extends LevelFilter{

	@Override
	public FilterReply decide(ILoggingEvent event) {
		
		/*什么时间，那个类出现了error,错误信息是什么[那个应用？？根据类的包名来截取]*/
		System.out.println(event);
		if (event.getLevel().equals(Level.ERROR)) {
           //错误日志让我特殊处理下吧
        }
		System.out.println(event.getArgumentArray());
		System.out.println(event.getThrowableProxy().getMessage());
		
//		event.getFormattedMessage();
//		event.getLoggerName();
//		event.getTimeStamp();
		
		return super.decide(event);
	}
}
