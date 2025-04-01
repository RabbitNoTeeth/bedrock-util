package com.gitee.rabbitnoteeth.bedrock.util;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import com.gitee.rabbitnoteeth.bedrock.util.exception.LogException;
import com.gitee.rabbitnoteeth.bedrock.util.function.BrConsumer;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class LogUtils {

    private LogUtils() {
    }

    public static void loadConfiguration(URL url) throws LogException {
        configure(joranConfigurator -> joranConfigurator.doConfigure(url));
    }

    public static void loadConfiguration(String filename) throws LogException {
        configure(joranConfigurator -> joranConfigurator.doConfigure(filename));
    }

    public static void loadConfiguration(File file) throws LogException {
        configure(joranConfigurator -> joranConfigurator.doConfigure(file));
    }

    public static void loadConfiguration(InputStream inputStream) throws LogException {
        configure(joranConfigurator -> joranConfigurator.doConfigure(inputStream));
    }

    private static void configure(BrConsumer<JoranConfigurator> configuratorConsumer) throws LogException {
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);
            loggerContext.reset();
            configuratorConsumer.accept(joranConfigurator);
            StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
        } catch (Throwable e) {
            throw new LogException("failed to load configuration");
        }
    }

}
