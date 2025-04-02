package com.github.rabbitnoteeth.bedrock.util.test.util;

import com.github.rabbitnoteeth.bedrock.util.JsonUtils;
import com.github.rabbitnoteeth.bedrock.util.test.entity.UserEntity;

public class TestUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(JsonUtils.encode(new UserEntity()));
    }

}
