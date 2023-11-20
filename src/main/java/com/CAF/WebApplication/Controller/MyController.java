package com.CAF.WebApplication.Controller;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.CAF.WebApplication.Model.User;
import com.CAF.WebApplication.Service.UserService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;



@Controller
//@RequestMapping("/users")
public class MyController {
	
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
	
	@Autowired
	private UserService userService;
	
	public MyController(UserService userService) {
		super();
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	
	// Normal home page
		@GetMapping("/")
		public String Home()
		{
			return "home";
		}
	
	//   listing all the Users in a new Page.
		@GetMapping("/manage")
		public String ManageTheUsersList(Model m)
		{

			List<User> findAllUsers = this.userService.findAllUsers();
			m.addAttribute("ListUser", findAllUsers);
			 m.addAttribute("title","User Management System");
//			 return paginationMethod(1,m);
		return "manageForm";
		}
		
		
		//  Show Add new User page
		@GetMapping("/addnewuser")
		public String addnewuser(Model m)
		{
			m.addAttribute("user", new User());
			m.addAttribute("title","User Management System");
			return "addForm";
		} 
		
		
//  Add new User handler
		@PostMapping("/saveaddeduser")
		public String addnewuser(@ModelAttribute("user") User user,@RequestParam("files") MultipartFile[] file,
				Model m, RedirectAttributes ra,HttpSession session,@RequestParam("gender") String gender,
				@RequestParam("status") String status) throws IOException
		{
			String uploads = System.getProperty("user.dir") + "/src/main/resources/static/files";
			System.out.println(System.getProperty("user.dir"));



		 //retrieving the files one by one
		 Arrays.stream(file).forEach(multipartFile -> {
			 logger.info("number of files uploaded :{}",file.length);
			 logger.info("file name :{}",multipartFile.getOriginalFilename().toString());
			 logger.info("type of a file :{}",multipartFile.getContentType());
			 logger.info("file size :{}",multipartFile.getSize());
			 logger.info("file Resource:{}",multipartFile.getResource());

			 //Storing the file
			 //getting only file name
			 String filename = multipartFile.getOriginalFilename(); //abc.png

			 //getting with complete path with file name
			 Path fullPath = Paths.get(uploads + File.separator + multipartFile.getOriginalFilename());  //src/files/abc.png

					 try {
						 Files.write(fullPath,multipartFile.getBytes());
						 //copy or replace if file is already exists...
						 Files.copy(multipartFile.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
					 } catch (IOException e) {
						 e.printStackTrace();
					 }
			 m.addAttribute(filename, fullPath);
			 m.addAttribute("message", "uploaded files :" + fullPath.toString());

			 //setting the file in to Database,so that will not get NUll value in the file field.
			 user.setFileName(filename);
			 user.setStatus(status);
		 });
			 //saving the user
			 this.userService.addUser(user);
			 this.userService.updateOrSave(user);

			 ra.addFlashAttribute("message", "User is successfully added !!");
			 System.out.println(user);
			 m.addAttribute("title", "User Management System");


		 return "redirect:/manage";
	 }

			 /*  for single file uploading
			  String originalFilename = file.getOriginalFilename();//ex:- abc.png
			  System.out.println(originalFilename);
			  Path Fullpath = Paths.get(uploads + File.separator + originalFilename);// /src/main/resource...abc.png

			  Files.write(Fullpath, file.getBytes());

			  //copy or replace if file is already exists...
			  Files.copy(file.getInputStream(), Fullpath, StandardCopyOption.REPLACE_EXISTING);
			  m.addAttribute(originalFilename, Fullpath);

			  m.addAttribute("message", "uploaded files :" + Fullpath.toString());

			  //setting the file in to Database,so that will not get NUll value in the file field.
			  user.setFileName(originalFilename);
			  user.setStatus(status);


			  //saving the user
			  this.userService.addUser(user);

			  ra.addFlashAttribute("message", "User is successfully added !!");
			  System.out.println(user);
			  m.addAttribute("title", "User Management System");

 */

		//updateOrSave User
		//First redirecting to the update page...same as add and addProcess user
		@GetMapping("/edit/{id}")
			//	@RequestMapping(value="/update/{id}",method = {RequestMethod.PUT,RequestMethod.GET }) 
				public String  update(@PathVariable("id") int id, Model m) 
				{
					m.addAttribute("user", userService.get(id));
					//this.userService.updateOrSave(user);
					m.addAttribute("title","User Management System");
					return "editForm";
				}

		//Handling the update request like addUser and addHandleUser
				//once the User filled the form of update then it will process the form like addProcess request
		@PostMapping("/update/{id}")
		//@RequestMapping(value="/update/{id}",method = {RequestMethod.PUT,RequestMethod.GET }) 
		public String  updateHandler(@PathVariable("id") int id,@ModelAttribute("user") User user,
				@RequestParam("files") MultipartFile[]  file,RedirectAttributes ra, Model m) throws  IOException
		{
			//getting the User from its id and saving in UserID
			User userId = this.userService.get(id);

				if(userId !=null) {
					 //inserting into the fields of the User to update
					 userId.setId(user.getId());
					 userId.setName(user.getName());
					 userId.setCity(user.getCity());
					 userId.setEmail(user.getEmail());
					 userId.setCodicefiscale(user.getCodicefiscale());
					 userId.setPhone(user.getPhone());
					 userId.setDob(user.getDob());
					 userId.setDescription(user.getDescription());
					 userId.setDocuments(user.getDocuments());
					 userId.setGender(user.getGender());
					 userId.setStatus(user.getStatus());

				 //setting file here
					Arrays.stream(file).forEach(multipartFile -> {
						 logger.info("number of files uploaded :{}",file.length);
						 logger.info("file name :{}",multipartFile.getOriginalFilename().toString());
						 logger.info("file ContentType :{}",multipartFile.getContentType());
						 logger.info("file Size :{}",multipartFile.getSize());
						 logger.info("file Resource :{}",multipartFile.getResource());

						 String fileName = multipartFile.getOriginalFilename();


							try {
								//uploading the file in this path location
								// we can even create a variable and describe the location and use the variable name like  did in creating  or adding the file in Controller /RestController

							//	File storingFile = new ClassPathResource("/src/main/resources/static/files").getFile(); //abc.png
								String uploads = System.getProperty("user.dir") + "/src/main/resources/static/files";
								System.out.println(System.getProperty("user.dir"));

								Path path = Paths.get(uploads + File.separator + multipartFile.getOriginalFilename());  //src/files/abc.png
								Files.write(path,multipartFile.getBytes());

								//copy or replace if file is already exists...
								Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

						} catch (IOException e) {
							e.printStackTrace();
						}

						//user.setFileName(multipartFile.getOriginalFilename());
						user.setFileName(fileName);
						userId.setFileName(user.getFileName());


					 });

					 //user.setFileName(file.getOriginalFilename());
					 System.out.println(user.getFileName()); // Here getting null in fileName [Now its resolved]
					 userId.setFileName(user.getFileName());


					 // Save the updated user to the database
					 User UserData = this.userService.updateOrSave(userId);

					 System.out.println("User Updated : " + UserData);
						// Add the updatedUser to the model
					 m.addAttribute("user",UserData);
					 ra.addFlashAttribute("message","Updated as requested !!");
					 m.addAttribute("title","User Management System");
					 return "redirect:/manage";

		 
				 }else {
					return "redirect:/manage";//Exception page
					}
		}
	
		
	
		//delete by id
		//@DeleteMapping("/delete/{id}")
		@RequestMapping(value="/delete/{id}",method = {RequestMethod.DELETE,RequestMethod.GET })
		public String  delete(@PathVariable("id") int id,RedirectAttributes ra)
		{
			 this.userService.delete(id);
			System.out.println("User id deleted : " );
			ra.addFlashAttribute("message", "Okay,I Deleted the User !!");
			return "redirect:/manage";
		}
	
		
		//  view User page
		@GetMapping("/view/{id}")
		public String view(@PathVariable("id") int id,@ModelAttribute User user,Model m)
		{
			
			User view = this.userService.get(id);
			m.addAttribute("user", view);
			m.addAttribute("title","User Management System");
			return "view";
		} 
		

//		//@RequestMapping(path = {"/search")
	@GetMapping("/search")
		public String Searching(@RequestParam("keyword") String keyword,Model m,RedirectAttributes ra) {

			List<User> listSearch = userService.listSearch(keyword);
				// Check if the list of search results is empty
				if (listSearch.isEmpty()) {
					// If the list is empty, add an attribute for the message to be shown in the view
					m.addAttribute("message", "No results found for the keyword: " + keyword);
				} else {
					m.addAttribute("listSearch", listSearch);
				}

		    	m.addAttribute("keyword", keyword);
				 System.out.println(listSearch);
		    	 System.out.println(keyword);
				return "search";

		}
	
		//Pagination
	@GetMapping("/page/{pageNo}")
	public String paginationMethod(@PathVariable(value = "pageNo") int pageNo,Model m){
			int pageSize =10;
		Page<User> page = this.userService.paginating(pageNo,pageSize);
		List<User> listUsers = page.getContent();
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages",page.getTotalPages());
		m.addAttribute("totalItems",page.getTotalElements());
		m.addAttribute("listUsers",listUsers);
		return "manageForm";
	}


	//demo file testing
	@GetMapping("/testing")
	public String Testing( Model m) {

		List<User> findAllUsers = this.userService.findAllUsers();
		m.addAttribute("ListUser", findAllUsers);
		 m.addAttribute("title","User Management System");

		return "demoFile";
	}
}
