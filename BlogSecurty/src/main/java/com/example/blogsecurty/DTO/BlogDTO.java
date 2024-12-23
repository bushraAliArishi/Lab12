package com.example.blogsecurty.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {

    @NotEmpty(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotEmpty(message = "Body is required")
    @Size(min = 20, message = "Body must contain at least 20 characters")
    private String body;
}
