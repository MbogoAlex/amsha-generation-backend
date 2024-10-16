package com.generation.amsha.blog.dao;

import com.generation.amsha.blog.model.Blog;
import java.util.List;

public interface BlogDao {
    Blog createBlog(Blog blog);
    Blog updateBlog(Blog blog);
    Blog getBlogByBlogId(Integer blogId);
    List<Blog> getUserBlogs(Integer userId);

    List<Blog> getBlogByTitle(String title);
    List<Blog> getAllBlogs(Integer userId, String title, String startDate, String endDate);
}
