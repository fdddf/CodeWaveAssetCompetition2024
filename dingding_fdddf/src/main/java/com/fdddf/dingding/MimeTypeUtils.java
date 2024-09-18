package com.fdddf.dingding;

import java.util.HashMap;
import java.util.Map;

public class MimeTypeUtils {
    private static final Map<String, String> mimeTypeToExtensionMap = new HashMap<>();

    static {
        mimeTypeToExtensionMap.put("image/jpeg", ".jpg");
        mimeTypeToExtensionMap.put("image/png", ".png");
        mimeTypeToExtensionMap.put("image/gif", ".gif");
        mimeTypeToExtensionMap.put("image/bmp", ".bmp");
        mimeTypeToExtensionMap.put("audio/mpeg", ".mp3");
        mimeTypeToExtensionMap.put("audio/x-wav", ".wav");
        mimeTypeToExtensionMap.put("audio/amr", ".amr");
        mimeTypeToExtensionMap.put("audio/ogg", ".ogg");
        mimeTypeToExtensionMap.put("video/mp4", ".mp4");
        mimeTypeToExtensionMap.put("application/zip", ".zip");
        mimeTypeToExtensionMap.put("application/pdf", ".pdf");
        mimeTypeToExtensionMap.put("application/msword", ".doc");
        mimeTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx");
        mimeTypeToExtensionMap.put("application/vnd.ms-excel", ".xls");
        mimeTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");
        mimeTypeToExtensionMap.put("application/vnd.ms-powerpoint", ".ppt");
        mimeTypeToExtensionMap.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx");
        mimeTypeToExtensionMap.put("application/x-rar-compressed", ".rar");
        // Add more MIME types and their extensions as needed
    }

    public static String getExtension(String mimeType, String fileUrl) {
        // guess from file extension
        String extension = fileUrl.substring(fileUrl.lastIndexOf("."));

        return mimeTypeToExtensionMap.getOrDefault(mimeType, "."+extension);
    }
}