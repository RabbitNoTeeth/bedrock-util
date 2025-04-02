package com.github.rabbitnoteeth.bedrock.util;

import com.github.rabbitnoteeth.bedrock.util.entity.Resource;
import com.github.rabbitnoteeth.bedrock.util.exception.FileException;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils {

    private FileUtils() {
    }

    public static void writeFile(String path, String fileName, byte[] fileBytes) throws FileException {
        try {
            File filePath = new File(path);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath.getPath() + "/" + fileName))) {
                bos.write(fileBytes);
            }
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

    public static byte[] readFile(String filePath) throws FileException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            int length = bis.available();
            byte[] res = new byte[length];
            bis.read(res);
            return res;
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

    public static InputStream loadFile(String filePath) throws FileException {
        return loadFile(filePath, false);
    }

    public static InputStream loadFile(String filePath, boolean withPrefix) throws FileException {
        try {
            if (!withPrefix) {
                return new FileInputStream(filePath);
            }
            if (filePath.startsWith("classpath:")) {
                filePath = filePath.replace("classpath:", "");
                return getResourceAsStream(filePath);
            } else if  (filePath.startsWith("file:")) {
                filePath = filePath.replace("file:", "");
                return new FileInputStream(filePath);
            } else {
                throw  new IllegalArgumentException("Invalid file path prefix, the optional prefix is ['classpath:', 'file:'] ");
            }
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

    public static InputStream getResourceAsStream(String path) throws FileException {
        try {
            ClassLoader classLoader = FileUtils.class.getClassLoader();
            URL url = classLoader.getResource(path);
            if (url == null) {
                return null;
            }
            return url.openStream();
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

    public static Resource getResource(String path) throws FileException {
        try {
            ClassLoader classLoader = FileUtils.class.getClassLoader();
            URL url = classLoader.getResource(path);
            if (url == null) {
                return null;
            }
            return new Resource(path);
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

    public static List<Resource> getResources(String path) throws FileException {
        return getResources("glob", path);
    }

    public static List<Resource> getResources(String syntax, String path) throws FileException {
        try {
            List<Resource> result = new ArrayList<>();
            PathMatcher matcher;
            ClassLoader classLoader = FileUtils.class.getClassLoader();
            URL rootResource = classLoader.getResource("");
            if (rootResource != null) {
                String rootPath = rootResource.getPath();
                if (!rootPath.endsWith("/")) {
                    rootPath += "/";
                }
                if (rootPath.startsWith("/") && SystemUtils.IS_OS_WINDOWS) {
                    rootPath = rootPath.substring(1);
                }
                matcher = FileSystems.getDefault().getPathMatcher(syntax + ":" + rootPath + path);
                Enumeration<URL> resources = classLoader.getResources("");
                while (resources.hasMoreElements()) {
                    URL url = resources.nextElement();
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                        scanFiles(rootPath, new File(filePath), matcher, result);
                    }
                }
            } else {
                matcher = FileSystems.getDefault().getPathMatcher(syntax + ":" + path);
                File root = new File(".");
                if (root.isDirectory()) {
                    File[] files = root.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            String fileName = file.getName();
                            if (fileName.endsWith(".jar")) {
                                scanFilesOfJar(file, matcher, result);
                            }
                        }
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

    private static void scanFiles(String rootPath, File file, PathMatcher matcher, List<Resource> resources) throws FileException {
        try {
            if (!file.exists() || !file.isDirectory()) {
                return;
            }
            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                return;
            }
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    scanFiles(rootPath, subFile, matcher, resources);
                } else {
                    if (matcher.matches(subFile.toPath())) {
                        String subFilePath = subFile.getPath();
                        subFilePath = subFilePath.replaceAll("\\\\", "/");
                        resources.add(new Resource(subFilePath.replace(rootPath, "")));
                    }
                }
            }
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

    private static void scanFilesOfJar(File file, PathMatcher matcher, List<Resource> resources) throws FileException {
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                String entryName = entry.getName();
                if (matcher.matches(new File(entryName).toPath())) {
                    resources.add(new Resource(entryName));
                }
            }
        } catch (Throwable e) {
            throw new FileException(e);
        }
    }

}
