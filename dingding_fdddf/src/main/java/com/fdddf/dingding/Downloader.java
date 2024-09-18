package com.fdddf.dingding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class Downloader {
    public static File downloadFile(String fileUrl, String destinationDirectory) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get MIME type from response header
        String contentType = connection.getContentType();
        String fileExtension = MimeTypeUtils.getExtension(contentType, fileUrl);

        // Generate a random file name
        String randomFileName = UUID.randomUUID() + fileExtension;

        // Construct the full destination path
        String destinationPath = destinationDirectory + File.separator + randomFileName;

        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return new File(destinationPath);
    }

}

