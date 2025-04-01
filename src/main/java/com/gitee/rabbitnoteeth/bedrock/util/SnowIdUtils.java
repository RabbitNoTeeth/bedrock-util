package com.gitee.rabbitnoteeth.bedrock.util;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;

public class SnowIdUtils {

    private SnowIdUtils() {}

    static {
        YitIdHelper.setIdGenerator(new IdGeneratorOptions());
    }

    public static long nextId() {
        return YitIdHelper.nextId();
    }

}
