package com.example.blogsecurty.Repository;

import com.example.blogsecurty.Model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Blog findBlogById(Integer id);
    Blog findBlogByTitle(String title);
    List<Blog> findAllByUserId(Integer id);
}
