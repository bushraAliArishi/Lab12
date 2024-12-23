package com.example.blogsecurty.Service;

import com.example.blogsecurty.Api.ApiException;
import com.example.blogsecurty.DTO.MyUserDTO;
import com.example.blogsecurty.Model.MyUser;
import com.example.blogsecurty.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    public List<MyUserDTO> getAllUsers() {
        List<MyUser> users = myUserRepository.findAll();
        if (users.isEmpty()) {
            throw new ApiException("No users found");
        }
        List<MyUserDTO> dtoList = new ArrayList<>();
        for (MyUser user : users) {
            dtoList.add(convertToDTO(user));
        }
        return dtoList;
    }

    public MyUserDTO getUser(Integer id) {
        MyUser myUser = myUserRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("User Not Found!");
        }
        return convertToDTO(myUser);
    }

    public void registerNewUser(MyUserDTO myUserDTO) {
        MyUser newUser = convertToEntity(myUserDTO);
        newUser.setRole(myUserDTO.getRole() == null ? "USER" : myUserDTO.getRole());
        newUser.setPassword(new BCryptPasswordEncoder().encode(myUserDTO.getPassword()));
        myUserRepository.save(newUser);
    }

    public void deleteUser(Integer id) {
        MyUser myUser = myUserRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("User Not Found");
        }
        myUserRepository.delete(myUser);
    }

    public void updateUser(MyUserDTO myUserDTO, Integer id) {
        MyUser oldUser = myUserRepository.findMyUserById(id);
        if (oldUser == null) {
            throw new ApiException("User Not Found");
        }
        oldUser.setUsername(myUserDTO.getUsername());
        oldUser.setRole(myUserDTO.getRole() == null ? oldUser.getRole() : myUserDTO.getRole());
        oldUser.setPassword(new BCryptPasswordEncoder().encode(myUserDTO.getPassword()));
        myUserRepository.save(oldUser);
    }

    private MyUser convertToEntity(MyUserDTO dto) {
        MyUser user = new MyUser();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }

    private MyUserDTO convertToDTO(MyUser user) {
        MyUserDTO dto = new MyUserDTO();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }
}
