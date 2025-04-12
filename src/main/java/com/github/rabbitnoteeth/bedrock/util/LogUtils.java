package com.github.rabbitnoteeth.bedrock.util;

import com.github.rabbitnoteeth.bedrock.util.exception.LogException;
import org.apache.logging.log4j.core.config.Configurator;

public class LogUtils {

    private LogUtils() {
    }

    public static void loadConfiguration(String filePath) throws LogException {
        Configurator.initialize(null, filePath);
    }

}
