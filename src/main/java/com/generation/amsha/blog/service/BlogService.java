package com.generation.amsha.blog.service;

import com.generation.amsha.blog.dto.BlogDto;
import com.generation.amsha.blog.dto.BlogPayload;
import com.generation.amsha.blog.dto.BlogUpdatePayload;
import com.generation.amsha.blog.model.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    BlogDto createBlog(BlogPayload blog, MultipartFile file) throws IOException;

    String removeFile(Integer blogId, String bucketName);
    BlogDto updateBlog(BlogUpdatePayload blog, MultipartFile file) throws IOException;
    String archiveBlog(Integer blogId);
    BlogDto getBlogByBlogId(Integer blogId);
    List<BlogDto> getUserBlogs(Integer userId);

    List<BlogDto> getBlogByTitle(String title);
    List<BlogDto> getAllBlogs(Integer userId, String title, String startDate, String endDate);
}
