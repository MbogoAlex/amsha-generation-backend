package com.generation.amsha.aws.controller;

import com.generation.amsha.aws.enumeration.FileType;
import com.generation.amsha.aws.service.AwsService;
import com.generation.amsha.response.BuildResponse;
import com.generation.amsha.response.Response;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/s3bucketstorage")
public class AwsController {
    private BuildResponse buildResponse = new BuildResponse();
    @Autowired
    private AwsService service;

    // Endpoint to list files in a bucket
    @GetMapping("/{bucketName}")
    public ResponseEntity<?> listFiles(
            @PathVariable("bucketName") String bucketName
    ) {
        val body = service.listFiles(bucketName);
        return ResponseEntity.ok(body);
    }

    // Endpoint to upload a file to a bucket
    @PostMapping("/{bucketName}/upload")
    @SneakyThrows(IOException.class)
    public ResponseEntity<Response> uploadFiles(
            @PathVariable("bucketName") String bucketName,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception {
        try {
            if (files.length == 0) {
                return buildResponse.buildResponse("files", "Files is empty", "Empty", HttpStatus.OK);
            } else {
                return buildResponse.buildResponse("files", service.uploadFiles(bucketName, files), "files uploaded", HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    // Endpoint to download a file from a bucket
    @SneakyThrows
    @GetMapping("/{bucketName}/download/{fileName}")
    public ResponseEntity<?> downloadFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        val body = service.downloadFile(bucketName, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(FileType.fromFilename(fileName))
                .body(body.toByteArray());
    }

    // Endpoint to delete a file from a bucket
    @DeleteMapping("/{bucketName}/{fileName}")
    public ResponseEntity<?> deleteFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        service.deleteFile(bucketName, fileName);
        return ResponseEntity.ok().build();
    }
}