package com.generation.amsha.blog.service;

import com.generation.amsha.aws.service.AwsService;
import com.generation.amsha.blog.dao.BlogDao;
import com.generation.amsha.blog.dto.BlogDto;
import com.generation.amsha.blog.dto.BlogPayload;
import com.generation.amsha.blog.dto.BlogUpdatePayload;
import com.generation.amsha.blog.mapper.BlogMapper;
import com.generation.amsha.blog.model.Blog;
import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.model.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    BlogMapper blogMapper = new BlogMapper();
    private final BlogDao blogDao;
    private final UserAccountDao userAccountDao;

    private final AwsService awsService;
    @Autowired
    public BlogServiceImpl(
            BlogDao blogDao,
            UserAccountDao userAccountDao,
            AwsService awsService
    ) {
        this.blogDao = blogDao;
        this.userAccountDao = userAccountDao;
        this.awsService = awsService;
    }
    @Transactional
    @Override
    public BlogDto createBlog(BlogPayload blog, MultipartFile file) throws IOException {
        UserAccount userAccount = userAccountDao.getUserById(blog.getUserId());
        String fileName = null;
        String filePath =  null;
        if(!file.isEmpty()) {
            fileName = awsService.uploadFile(blog.getBucketName(), file);
            filePath = awsService.getFileUrl(blog.getBucketName(), fileName);
        }
        Blog blog1 = Blog.builder()
                .title(blog.getTitle())
                .createdAt(LocalDateTime.now().plusHours(3))
                .userAccount(userAccount)
                .imageName(fileName)
                .imagePath(filePath)
                .paragraph(blog.getParagraph())
                .archived(false)
                .comments(new ArrayList<>())
                .tags(blog.getTags())
                .build();

        return blogMapper.blogToBlogDto(blogDao.createBlog(blog1));
    }

    @Transactional
    @Override
    public String removeFile(Integer blogId, String bucketName) {
        Blog blog = blogDao.getBlogByBlogId(blogId);

        awsService.deleteFile(bucketName, blog.getImageName());
        blog.setImageName(null);
        blog.setImagePath(null);
        blogDao.updateBlog(blog);

        return "Blog image removed";
    }
    @Transactional
    @Override
    public BlogDto updateBlog(BlogUpdatePayload blog, MultipartFile file) throws IOException {
        Blog blog1 = blogDao.getBlogByBlogId(blog.getBlogId());
        String fileName = blog1.getImageName();
        String filePath =  blog1.getImagePath();
        if(!blog.getTitle().isEmpty()) {
            blog1.setTitle(blog.getTitle());
        }
        if(!blog.getParagraph().isEmpty()) {
            blog1.setParagraph(blog.getParagraph());
        }

        if(!file.isEmpty()) {
            fileName = awsService.uploadFile(blog.getBucketName(), file);
            filePath = awsService.getFileUrl(blog.getBucketName(), fileName);
        }

        blog1.setImageName(fileName);
        blog1.setImagePath(filePath);

        blog1.setTags(blog.getTags());
        blog1.setUpdatedAt(LocalDateTime.now().plusHours(3));

        return blogMapper.blogToBlogDto(blogDao.updateBlog(blog1));
    }
    @Transactional
    @Override
    public String archiveBlog(Integer blogId) {
        Blog blog = blogDao.getBlogByBlogId(blogId);
        blog.setArchived(true);
        blogDao.updateBlog(blog);
        return "Blog archived";
    }
    @Transactional
    @Override
    public BlogDto getBlogByBlogId(Integer blogId) {
        return blogMapper.blogToBlogDto(blogDao.getBlogByBlogId(blogId));
    }
    @Transactional
    @Override
    public List<BlogDto> getUserBlogs(Integer userId) {
        List<BlogDto> blogDtos = new ArrayList<>();
        List<Blog> blogs = blogDao.getUserBlogs(userId);

        for(Blog blog : blogs) {
            blogDtos.add(blogMapper.blogToBlogDto(blog));
        }
        return blogDtos;
    }
    @Transactional
    @Override
    public List<BlogDto> getBlogByTitle(String title) {
        List<BlogDto> blogDtos = new ArrayList<>();
        List<Blog> blogs = blogDao.getBlogByTitle(title);

        for(Blog blog : blogs) {
            blogDtos.add(blogMapper.blogToBlogDto(blog));
        }
        return blogDtos;
    }
    @Transactional
    @Override
    public List<BlogDto> getAllBlogs(Integer userId, String title, String startDate, String endDate) {
        List<BlogDto> blogDtos = new ArrayList<>();
        List<Blog> blogs = blogDao.getAllBlogs(userId, title, startDate, endDate);

        for(Blog blog : blogs) {
            blogDtos.add(blogMapper.blogToBlogDto(blog));
        }
        return blogDtos;
    }
}
