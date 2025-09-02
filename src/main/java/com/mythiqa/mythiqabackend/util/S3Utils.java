package com.mythiqa.mythiqabackend.util;

public class S3Utils {

    public static String extractS3ObjectKey(String url) {
        if (url == null || url.isEmpty()) {
            return "";
        }

        // Find the "amazonaws.com/" part and take everything after it
        String marker = ".amazonaws.com/";
        int index = url.indexOf(marker);
        if (index == -1) {
            return "";
        }
        return url.substring(index + marker.length());
    }

}
