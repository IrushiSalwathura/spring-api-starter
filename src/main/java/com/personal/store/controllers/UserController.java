package com.personal.store.controllers;

import com.personal.store.dtos.RegisterUserRequest;
import com.personal.store.dtos.UserDto;
import com.personal.store.entities.User;
import com.personal.store.mappers.UserMapper;
import com.personal.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    //method: Get
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name="sort") String sortBy
    ){
        if (!Set.of("name","email").contains(sortBy))
            sortBy = "name";

        return userRepository
                .findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user =  userRepository.findById(id).orElse(null);
        if (user == null){
            return ResponseEntity.notFound().build(); //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userMapper.toDto(user)); //return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ){
        var user = userMapper.toEntity(request);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }
}
