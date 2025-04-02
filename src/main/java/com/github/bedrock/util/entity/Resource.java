package com.github.bedrock.util.entity;

import java.io.InputStream;

public class Resource {

    private final String path;

    public Resource(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream(this.path);
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return this.path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Resource) {
            return path != null && path.equals(((Resource) obj).getPath());
        }
        return false;
    }
}
