package com.tweetapp.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.api.model.User;
import com.tweetapp.api.model.UserResponse;
import com.tweetapp.api.repository.UserRepository;
import com.tweetapp.api.exception.InvalidUsernameException;
import com.tweetapp.api.exception.UsernameAlreadyExists;
import com.tweetapp.api.messaging.ProducerService;
@Service
public class UserServiceImpl implements UserService 
{
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProducerService producerService; 
	
	@Override
	public User createUser(User user) throws UsernameAlreadyExists 
	{
		// TODO Auto-generated method stub
		if(userRepository.findByUsername(user.getFirstName()) != null)
			throw new UsernameAlreadyExists("USername already exists");
		logger.info("Registration successfully...");
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) 
	{
		return userRepository.save(user);
	}

	@Override
	public int deleteUser(User user) 
	{
		userRepository.delete(user);
		logger.info("Deleted user successfully..");
		return 1;
	}

	@Override
	public List<User> getAllUsers() 
	{
		// TODO Auto-generated method stub
		logger.info("Retriving all the users");
		return userRepository.findAll();
	}

	@Override
	public List<User> getUserByUsername(String username) throws InvalidUsernameException
	{
		// TODO Auto-generated method stub
		if(userRepository.findByUsernameContaining(username)== null)
			throw new InvalidUsernameException("Please enter a valid username");
		logger.info("Retriving the user by the username.."+username);
		return userRepository.findByUsernameContaining(username);
	}

	@Override
	public Optional<User> getUserById(String id) 
	{
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public UserResponse loginUser(String username, String password) throws Exception 
	{
		// TODO Auto-generated method stub
		UserResponse response = new UserResponse();
		User user = userRepository.findByUsername(username);
		if (user.getPassword().equals(password)) {
			response.setUser(user);
			response.setLoginStatus("success");
			logger.info("Login Successful..");
		}
		else {
		response.setLoginStatus("failed");
		logger.info("Login failed");
		}
		return response;
	}

	@Override
	public Map<String, String> forgotPassword(String username) 
	{
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		User user = userRepository.findByUsername(username);
		user.setPassword(UUID.randomUUID().toString());
		userRepository.save(user);
		map.put("newPassword", user.getPassword());
		map.put("resetStatus","success");
		logger.info("Completed..");
		return map;
	}
	@Override
	public Map<String, String> resetPassword(String username,String password) 
	{
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		User user = userRepository.findByUsername(username);
		user.setPassword(password.toString());
		userRepository.save(user);
		map.put("newPassword", user.getPassword());
		map.put("resetStatus","success");
		logger.info("Reset password successful..");
		return map;
	}
	
}
