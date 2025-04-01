package com.gitee.rabbitnoteeth.bedrock.util;

import com.gitee.rabbitnoteeth.bedrock.util.exception.YamlException;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;

public class YamlUtils {

    private YamlUtils() {
    }

    public static <T> T loadResource(String path, Class<? super T> type) throws YamlException {
        try (InputStream inputStream = YamlUtils.class.getClassLoader().getResourceAsStream(path)) {
            return new Yaml().loadAs(inputStream, type);
        } catch (Throwable e) {
            throw new YamlException(e);
        }
    }

    public static <T> T loadFile(String path, Class<? super T> type) throws YamlException {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            return new Yaml().loadAs(inputStream, type);
        } catch (Throwable e) {
            throw new YamlException(e);
        }
    }

}
