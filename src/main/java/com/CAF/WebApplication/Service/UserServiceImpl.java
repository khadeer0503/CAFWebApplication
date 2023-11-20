package com.CAF.WebApplication.Service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.CAF.WebApplication.Dao.UserRepo;
import com.CAF.WebApplication.Model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	public UserServiceImpl(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}
	public UserRepo getUserRepo() {
		return userRepo;
	}
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	
				@Override
				public List<User> findAllUsers() {
					
					return this.userRepo.findAll();
				}
			
				@Override
				public User get(int id) {
					
					return this.userRepo.findById(id).get();
				}
			
				@Override
				public User addUser(User user) {
			
					return this.userRepo.save(user);
				}
			
				@Override
				public User updateOrSave(User user) {
					return this.userRepo.save(user);
						
				}
			
				@Override
				public void delete(int id) {
					 this.userRepo.deleteById(id);;
				 
				}

				@Override
				public User view(User user) {
					return this.userRepo.save(user);
				}

	//pagination
				@Override
				public Page<User> paginating(int pageNo, int pageSize) {
					Pageable  pageable=PageRequest.of(pageNo-1, pageSize);
					return this.userRepo.findAll(pageable);
				}

	//search functionality
				@Override
				public List<User> listSearch(String keyword) {
								 if (keyword!= null && !keyword.isEmpty()) {
									// return userRepo.searchUsers("%"+keyword+"%");  //METHOD-2
									return userRepo.searchUsers(keyword); // METHOD-3
								 } else {
									 return Collections.emptyList();
								 }
				}

}
