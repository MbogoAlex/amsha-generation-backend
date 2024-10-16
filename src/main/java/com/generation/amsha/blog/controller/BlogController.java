package com.generation.amsha.blog.controller;

import com.generation.amsha.blog.dto.BlogDto;
import com.generation.amsha.blog.dto.BlogPayload;
import com.generation.amsha.blog.dto.BlogUpdatePayload;
import com.generation.amsha.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogController {
    ResponseEntity<Response> createBlog(BlogPayload blog, MultipartFile file) throws IOException;

    ResponseEntity<Response> removeFile(Integer blogId, String bucketName);
    ResponseEntity<Response> updateBlog(BlogUpdatePayload blog, MultipartFile file) throws IOException;
    ResponseEntity<Response> archiveBlog(Integer blogId);
    ResponseEntity<Response> getBlogByBlogId(Integer blogId);
    ResponseEntity<Response> getUserBlogs(Integer userId);

    ResponseEntity<Response> getBlogByTitle(String title);
    ResponseEntity<Response> getAllBlogs(Integer userId, String title, String startDate, String endDate);
}
