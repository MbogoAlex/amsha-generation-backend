package com.generation.amsha.blog.dao;

import com.generation.amsha.blog.model.Blog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogDaoImpl implements BlogDao{
    private final EntityManager entityManager;
    @Autowired
    public BlogDaoImpl(
            EntityManager entityManager
    ) {
        this.entityManager = entityManager;
    }
    @Override
    public Blog createBlog(Blog blog) {
        entityManager.persist(blog);
        return blog;
    }

    @Override
    public Blog updateBlog(Blog blog) {
        entityManager.merge(blog);
        return blog;
    }

    @Override
    public Blog getBlogByBlogId(Integer blogId) {
        TypedQuery<Blog> query = entityManager.createQuery("from Blog where id = :id", Blog.class);
        query.setParameter("id", blogId);
        return query.getSingleResult();
    }

    @Override
    public List<Blog> getUserBlogs(Integer userId) {
        TypedQuery<Blog> query = entityManager.createQuery("from Blog where userAccount.id =:userId", Blog.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Blog> getBlogByTitle(String title) {
        // If title is null or blank, query for blogs with null or empty title
        if (title == null || title.isBlank()) {
            return entityManager.createQuery("from Blog where title is null", Blog.class)
                    .getResultList();
        }

        // Otherwise, perform a case-insensitive search for blogs containing the title
        TypedQuery<Blog> query = entityManager.createQuery(
                "from Blog where LOWER(title) like LOWER(concat('%', :title, '%'))", Blog.class);

        query.setParameter("title", title.toLowerCase());
        return query.getResultList();
    }


    @Override
    public List<Blog> getAllBlogs(Integer userId, String title, String startDate, String endDate) {
        // Create CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Blog> query = cb.createQuery(Blog.class);
        Root<Blog> blog = query.from(Blog.class);

        // Initialize predicate list
        List<Predicate> predicates = new ArrayList<>();

        // Filter by userId if provided
        if (userId != null) {
            predicates.add(cb.equal(blog.get("userAccount").get("id"), userId));
        }

        // Filter by title if provided
        if (title != null && !title.isEmpty()) {
            // Use 'like' for partial matching (case-insensitive)
            predicates.add(cb.like(cb.lower(blog.get("title")), "%" + title.toLowerCase() + "%"));
        }

        // Filter by startDate if provided
        if (startDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDateTime startDateTime = start.atStartOfDay(); // Start at 00:00:00
            predicates.add(cb.greaterThanOrEqualTo(blog.get("createdAt"), startDateTime));
        }

        // Filter by endDate if provided
        if (endDate != null) {
            LocalDate end = LocalDate.parse(endDate);
            LocalDateTime endDateTime = end.atTime(23, 59, 59); // End at 23:59:59
            predicates.add(cb.lessThanOrEqualTo(blog.get("createdAt"), endDateTime));
        }

        // Combine predicates and apply them to the query
        query.select(blog).where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute query and return result list
        return entityManager.createQuery(query).getResultList();
    }

}
