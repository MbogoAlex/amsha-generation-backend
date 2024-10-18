package com.generation.amsha.blog.controller;

import com.generation.amsha.blog.dto.BlogPayload;
import com.generation.amsha.blog.dto.BlogUpdatePayload;
import com.generation.amsha.blog.service.BlogService;
import com.generation.amsha.response.BuildResponse;
import com.generation.amsha.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin(origins = {"http://localhost:4200", "https://amsha-gen-96609f863a46.herokuapp.com"})
@RestController
@RequestMapping("/api/")
public class BlogControllerImpl implements BlogController{
    BuildResponse buildResponse = new BuildResponse();
    private final BlogService blogService;
    @Autowired
    public BlogControllerImpl(
            BlogService blogService
    ) {
        this.blogService = blogService;
    }
    @PostMapping("blog")
    @Override
    public ResponseEntity<Response> createBlog(
            @RequestPart("data") BlogPayload blog,
            @RequestPart(value = "file") MultipartFile file
    ) throws IOException {
        return buildResponse.buildResponse("blog", blogService.createBlog(blog, file), "Blog created", HttpStatus.CREATED);
    }
    @PutMapping("blog/removefile/{blogId}/{bucketName}")
    @Override
    public ResponseEntity<Response> removeFile(
            @PathVariable("blogId") Integer blogId,
            @PathVariable("bucketName") String bucketName
    ) {
        return buildResponse.buildResponse("blog", blogService.removeFile(blogId, bucketName), "File removed", HttpStatus.OK);
    }
    @PutMapping("blog")
    @Override
    public ResponseEntity<Response> updateBlog(
            @RequestPart("data") BlogUpdatePayload blog,
            @RequestPart(value = "file") MultipartFile file
    ) throws IOException {
        return buildResponse.buildResponse("blog", blogService.updateBlog(blog, file), "Blog updated", HttpStatus.OK);
    }
    @PutMapping("blog/archive/{blogId}")
    @Override
    public ResponseEntity<Response> archiveBlog(@PathVariable("blogId") Integer blogId) {
        return buildResponse.buildResponse("blog", blogService.archiveBlog(blogId), "Blog archived", HttpStatus.OK);
    }
    @GetMapping("blog/bif/{blogId}")
    @Override
    public ResponseEntity<Response> getBlogByBlogId(@PathVariable("blogId") Integer blogId) {
        return buildResponse.buildResponse("blog", blogService.getBlogByBlogId(blogId), "Blog fetched", HttpStatus.OK);
    }
    @GetMapping("blog/uid/{userId}")
    @Override
    public ResponseEntity<Response> getUserBlogs(@PathVariable("userId") Integer userId) {
        return buildResponse.buildResponse("blog", blogService.getUserBlogs(userId), "Blogs fetched", HttpStatus.OK);
    }
    @GetMapping("blog/title")
    @Override
    public ResponseEntity<Response> getBlogByTitle(
            @RequestParam(name = "title", required = false) String title
    ) {
        return buildResponse.buildResponse("blog", blogService.getBlogByTitle(title), "Blogs fetched", HttpStatus.OK);
    }
    @GetMapping("blog/all")
    @Override
    public ResponseEntity<Response> getAllBlogs(
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
    ) {
        return buildResponse.buildResponse("blog", blogService.getAllBlogs(userId, title, startDate, endDate), "Blogs fetched", HttpStatus.OK);
    }
}
