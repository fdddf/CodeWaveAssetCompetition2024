package com.fdddf.dingding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.lowcode.core.annotation.NaslLogic;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Uploader {

    private static final String serverUrl = "https://oapi.dingtalk.com/media/upload";

    private static final Logger LCAP_LOGGER = LoggerFactory.getLogger("LCAP_EXTENSION_LOGGER");

    public static String printLog(String str) {
        LCAP_LOGGER.info(str);
        return str;
    }

    /**
     * Upload a file to the server
     * https://open.dingtalk.com/document/orgapp/upload-media-files
     *
     * @param url         of the file to be uploaded
     * @param accessToken 接口的应用凭证
     */
    @NaslLogic
    public UploadResponse upload(String url, String accessToken) {
        UploadResponse resp = null;
        try {
            File file = Downloader.downloadFile(url, "/tmp");
            String fileType = detectFileType(file);

            OkHttpClient client = new OkHttpClient();

            // Create RequestBody for the file
            RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/octet-stream"));

            // Create MultipartBody
            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("media", file.getName(), fileBody)
                    .addFormDataPart("type", fileType)
                    .build();

            Request request = new Request.Builder()
                    .url(serverUrl + "?access_token=" + accessToken)
                    .post(requestBody)
                    .build();

            // Execute the request
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                // Parse the JSON response
                assert response.body() != null;
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();

                // Map JSON to UploadResponse class
                resp = mapper.readValue(responseBody, UploadResponse.class);

                // Print the parsed response
                printLog("Response body: " + resp);
                System.out.println("Parsed response: " + resp.toString());
                return resp;

            } catch (Exception e) {
                e.printStackTrace();
                printLog("Error occurred: " + e);
            } finally {
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
            printLog("Error occurred: " + e);
        }
        return null;
    }

    private static String detectFileType(File file) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        switch (fileExtension) {
            case "jpg":
            case "gif":
            case "png":
            case "bmp":
                return "image";
            case "amr":
            case "mp3":
            case "wav":
            case "ogg":
                return "voice";
            case "mp4":
                return "video";
            case "doc":
            case "docx":
            case "xls":
            case "xlsx":
            case "ppt":
            case "pptx":
            case "zip":
            case "pdf":
            case "rar":
                return "file";
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }
    }

    public static void main(String[] args) {
        String url = "https://cdn.tosound.com:3321/preview?file=freesound%2F0%2F8%2F110706.mp3&token=ZnJlZXNvdW5kJTJGMCUyRjglMkYxMTA3MDYubXAz&sound=audio.mp3";
        url = "https://dev-crmtest-qa.app.codewave.163.com:443/upload/app/3a0aa71b-654b-42f5-af18-dfd464d522b1/output_20240918021633342.ogg";
        String accessToken = "fc79733c04aa34ea876e82c707ee1858";
        Uploader uploader = new Uploader();
        UploadResponse response = uploader.upload(url, accessToken);
        System.out.println(response);
    }
}
