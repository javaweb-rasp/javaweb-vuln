package org.javaweb.vuls.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Character.MAX_VALUE;

public class LoggerManager {

	private static final LoggerContext LOGGER_CONTEXT = (LoggerContext) LoggerFactory.getILoggerFactory();

	public static Logger createRASPLogger(String loggerName, File file, Level level, String fileSize) {
		// 初始化日志appender
		RollingFileAppender appender = new RollingFileAppender();
		appender.setContext(LOGGER_CONTEXT);
		appender.setFile(file.toString());

		// 限制单个日志大小
		SizeBasedTriggeringPolicy sizeBasedTriggeringPolicy = new SizeBasedTriggeringPolicy();
		sizeBasedTriggeringPolicy.setMaxFileSize(FileSize.valueOf(fileSize));
		sizeBasedTriggeringPolicy.start();

		// 设置日志格式
		PatternLayoutEncoder layout = new PatternLayoutEncoder();
		layout.setPattern("%msg%n");
		layout.setContext(LOGGER_CONTEXT);
		layout.start();

		// 设置日志自动打包
		FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
		rollingPolicy.setContext(LOGGER_CONTEXT);
		rollingPolicy.setParent(appender);
		rollingPolicy.setMaxIndex(MAX_VALUE);
		rollingPolicy.setFileNamePattern(file.getParent() + "/" + file.getName() + ".%i.txt");
		rollingPolicy.start();

		appender.setEncoder(layout);
		appender.setRollingPolicy(rollingPolicy);
		appender.setTriggeringPolicy(sizeBasedTriggeringPolicy);
		appender.start();

		// 初始化日志配置
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		logger.addAppender(appender);
		logger.setLevel(level);
		logger.setAdditive(false);

		return logger;
	}

	public static void main(String[] args) {
		File                file   = new File("/Users/yz/IdeaProjects/anbai-lingxe-java/anbai-lingxe-test/lingxe-test-servlet/src/main/test.log");
		final Logger        LOG    = createRASPLogger("test", file, Level.INFO, "1KB");
		final AtomicInteger atomic = new AtomicInteger();

		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (atomic.getAndAdd(1) < 100000) {
						LOG.info(
								Thread.currentThread().getName() + "\t" +
										new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date())
						);
					}
				}
			}).start();
		}
	}

}
