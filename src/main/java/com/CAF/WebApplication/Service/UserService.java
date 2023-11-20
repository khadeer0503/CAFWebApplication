package com.CAF.WebApplication.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.CAF.WebApplication.Model.User;

public interface UserService {
	
	List<User> findAllUsers();
	User get(int id);
	User addUser(User user);
	User updateOrSave(User user);
	void delete(int id);
	User view(User user);

	//pagination
	Page<User> paginating(int pageNo, int pageSize);

	//search method
	List<User> listSearch(String keyword);

}
