package com.proje.service.impl;

import java.util.List;

import com.proje.model.User;
import com.proje.repository.UserRepository;
import com.proje.repository.impl.UserRepositoryImpl;
import com.proje.service.UserService;

public class UserServiceImpl implements UserService{

	private UserRepository userRepository = new UserRepositoryImpl();

	public User saveUser(User user) {
		return userRepository.saveUser(user);
	}

	public boolean saveUserProduct(int userId, int ProductId) {
		return userRepository.saveUserProduct(userId, ProductId);
	}

	public User updateUser(User user) {
		return userRepository.updateUser(user);
	}

	public boolean removeUser(int id) {
		return userRepository.removeUser(id);
	}

	public User findUserById(int id) {
		return userRepository.findUserById(id);
	}

	public User findUserProductById(int id) {
		return userRepository.findUserProductById(id);
	}

	public List<User> findUsers() {
		return userRepository.findUsers();
	}
	
	
}
