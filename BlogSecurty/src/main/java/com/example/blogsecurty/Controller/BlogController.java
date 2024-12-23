package com.example.blogsecurty.Controller;

import com.example.blogsecurty.Api.ApiResponse;
import com.example.blogsecurty.DTO.BlogDTO;
import com.example.blogsecurty.Model.MyUser;
import com.example.blogsecurty.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;

    // Admin
    @GetMapping("/all-blogs")
    public ResponseEntity getAllBlogs() {
        return ResponseEntity.status(200).body(blogService.getAllBlogs());
    }

    // User
    @GetMapping("/my-blogs")
    public ResponseEntity getMyBlogs(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(blogService.getMyBlogs(myUser.getId()));
    }

    // User
    @GetMapping("/{id}")
    public ResponseEntity getBlogById(@PathVariable Integer id,
                                         @AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(blogService.getBlogById(id, myUser.getId()));
    }

    // User
    @PostMapping("/add-blog")
    public ResponseEntity addBlog(@RequestBody @Valid BlogDTO blogDTO,
                                     @AuthenticationPrincipal MyUser myUser) {
        blogService.addBlog(blogDTO, myUser.getId());
        return ResponseEntity.status(201).body(new ApiResponse("Blog added successfully"));
    }

    // User
    @PutMapping("/update/{id}")
    public ResponseEntity updateBlog(@PathVariable Integer id,@RequestBody @Valid BlogDTO blogDTO, @AuthenticationPrincipal MyUser myUser) {
        blogService.updateBlog(id, blogDTO, myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Blog updated successfully"));
    }

    // User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id,
                                        @AuthenticationPrincipal MyUser myUser) {
        blogService.deleteBlog(id, myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Blog deleted successfully"));
    }

    // Admin
    @GetMapping("/user/{userId}")
    public ResponseEntity getBlogsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(blogService.getMyBlogs(userId));
    }

    // Admin
    @GetMapping("/title/{title}")
    public ResponseEntity getBlogByTitle(@PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(title));
    }
}
