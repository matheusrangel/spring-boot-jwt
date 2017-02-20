package com.hackathon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.model.User;
import com.hackathon.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public User getUserByUsername(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}
	
	@RequestMapping(method=RequestMethod.GET, params={"name"} )
	public List<User> getUserByName(@RequestParam("name") String name) {
		return userRepository.findByName(name);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User user) {
		return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, params={"id"} )
	public ResponseEntity<?> deleteUser(@RequestParam("id") Long id) {
		userRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
