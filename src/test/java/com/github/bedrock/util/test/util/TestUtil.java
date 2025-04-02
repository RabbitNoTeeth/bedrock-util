package com.github.bedrock.util.test.util;

import com.github.bedrock.util.JsonUtils;
import com.github.bedrock.util.test.entity.UserEntity;

public class TestUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(JsonUtils.encode(new UserEntity()));
    }

}
