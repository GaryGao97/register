package com.example.register.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: minchen
 * 创建时间: 2020/3/3
 * 功能描述:
 */
public class FileUtils {
    public static String getParentPath(String path) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        int i = path.lastIndexOf("/");
        String viewPath = path.substring(0, i);
        return StringUtils.isBlank(viewPath) ? "/" : viewPath;
    }

    public static String getPathFileName(String path) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        int i = path.lastIndexOf("/");
        if (i >= 0) {
            return path.substring(i + 1);
        }
        return path;
    }

    public static String getFileSuffix(String path) {
        String pathFileName = getPathFileName(path);
        int i = pathFileName.lastIndexOf(".");
        if (i >= 0) {
            return pathFileName.substring(i + 1).toLowerCase();
        }
        return "";
    }

    public static String joinPath(String rootPath, String children) {
        if (StringUtils.isBlank(rootPath)) {
            return children;
        }

        if (StringUtils.isBlank(children)) {
            return rootPath;
        }

        if (rootPath.endsWith("/")) {
            return children.startsWith("/") ? rootPath + children.substring(1) : rootPath + children;
        }


        return children.startsWith("/") ? rootPath + children : rootPath + "/" + children;

    }
}
