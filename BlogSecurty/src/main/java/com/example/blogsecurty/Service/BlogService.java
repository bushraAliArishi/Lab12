package com.example.blogsecurty.Service;

import com.example.blogsecurty.Api.ApiException;
import com.example.blogsecurty.DTO.BlogDTO;
import com.example.blogsecurty.Model.Blog;
import com.example.blogsecurty.Model.MyUser;
import com.example.blogsecurty.Repository.BlogRepository;
import com.example.blogsecurty.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MyUserRepository myUserRepository;

    public List<BlogDTO> getAllBlogs() {
        List<Blog> blogList = blogRepository.findAll();
        if (blogList.isEmpty()) {
            throw new ApiException("No blogs found");
        }
        List<BlogDTO> dtoList = new ArrayList<>();
        for (Blog blog : blogList) {
            dtoList.add(convertToDTO(blog));
        }
        return dtoList;
    }

    public BlogDTO getBlogById(Integer blogId, Integer authId) {
        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        if (!blog.getUser().getId().equals(authId)) {
            throw new ApiException("Sorry, You do not have the authority to get this blog!");
        }
        return convertToDTO(blog);
    }

    public List<BlogDTO> getMyBlogs(Integer authId) {
        MyUser user = myUserRepository.findMyUserById(authId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        List<Blog> blogs = blogRepository.findAllByUserId(user.getId());
        if (blogs.isEmpty()) {
            throw new ApiException("You have no blogs yet!");
        }
        List<BlogDTO> dtoList = new ArrayList<>();
        for (Blog blog : blogs) {
            dtoList.add(convertToDTO(blog));
        }
        return dtoList;
    }

    public BlogDTO getBlogByTitle(String title) {
        Blog blog = blogRepository.findBlogByTitle(title);
        if (blog == null) {
            throw new ApiException("Blog Not Found by that title!");
        }
        return convertToDTO(blog);
    }

    public void addBlog(BlogDTO blogDTO, Integer authId) {
        MyUser user = myUserRepository.findMyUserById(authId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Blog newBlog = convertToEntity(blogDTO);
        newBlog.setUser(user);
        blogRepository.save(newBlog);
    }

    public void updateBlog(Integer blogId, BlogDTO updatedBlogDTO, Integer authId) {
        Blog oldBlog = blogRepository.findBlogById(blogId);
        if (oldBlog == null) {
            throw new ApiException("Blog not found");
        }
        if (!oldBlog.getUser().getId().equals(authId)) {
            throw new ApiException("Sorry, you do not have the authority to update this blog!");
        }
        oldBlog.setTitle(updatedBlogDTO.getTitle());
        oldBlog.setBody(updatedBlogDTO.getBody());
        blogRepository.save(oldBlog);
    }

    public void deleteBlog(Integer blogId, Integer authId) {
        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        if (!blog.getUser().getId().equals(authId)) {
            throw new ApiException("Sorry, you do not have the authority to delete this blog!");
        }
        blogRepository.delete(blog);
    }

    private Blog convertToEntity(BlogDTO blogDTO) {
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setBody(blogDTO.getBody());
        return blog;
    }

    private BlogDTO convertToDTO(Blog blog) {
        BlogDTO dto = new BlogDTO();
        dto.setTitle(blog.getTitle());
        dto.setBody(blog.getBody());
        return dto;
    }
}
