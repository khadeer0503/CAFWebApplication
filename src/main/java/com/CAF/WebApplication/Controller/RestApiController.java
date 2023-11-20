package com.CAF.WebApplication.Controller;

import com.CAF.WebApplication.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.CAF.WebApplication.Dao.UserRepo;
import com.CAF.WebApplication.Service.UserService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

	private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;

	public UserRepo getUserRepo() {
		return userRepo;
	}
	public void setUserRepo(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public static Logger getLogger() {
		return logger;
	}




	//@RequestMapping(path = {"/search"})
/* 	@GetMapping("/search")
	    public ResponseEntity<List<User>> Searching(@Param("keyword") String keyword) {
	        List<User> listSearch = userService.listSearch(keyword);

	        System.out.println(listSearch);
	        System.out.println(keyword);
			return ResponseEntity.status(HttpStatus.OK).body(listSearch);
	    } */

/* 
         @PostMapping("create")
    public User createProduct(@RequestBody User user){
        return this.userRepo.save(user);
    } */

	//Multipart file
/* */
	@PostMapping("/multiple-files")
	public ResponseEntity<?> multipleFilesUploading(@ModelAttribute("user") User user,
			@RequestParam("multiplefiles") MultipartFile[] file,
			@RequestParam("gender") String gender,
			@RequestParam("status") String status){

		String uploads = System.getProperty("user.dir") + "/src/main/resources/static/files";

		//retrieving the files one by one
		Arrays.stream(file).forEach(multipartFile -> {
			logger.info("number of files uploaded :{}",file.length);
			logger.info("file name :{}",multipartFile.getOriginalFilename().toString());
			logger.info("file name :{}",multipartFile.getContentType());
			logger.info("file name :{}",multipartFile.getSize());
			logger.info("file name :{}",multipartFile.getResource());

			 //Storing the file
			//getting only file name
			String filename = multipartFile.getOriginalFilename(); //abc.png

			//getting with complete path with file name
			Path fullPath = Paths.get(uploads + File.separator + multipartFile.getOriginalFilename());  //src/files/abc.png

			try {
				Files.write(fullPath,multipartFile.getBytes());

				//copy or replace if file is already exists...
				Files.copy(multipartFile.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);

				//setting the file in to Database,so that will not get NUll value in the file field.
				user.setFileName(filename);
				user.setStatus(status);

				//saving the user
				this.userService.addUser(user);
				this.userService.updateOrSave(user);

				logger.info("files","{} this no. of files are added.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});


		return ResponseEntity.ok("Files successfully added .");
	}


	//update handler
	@PostMapping("/updateApi/{id}")
	public ResponseEntity<?>  updateOrSave(@PathVariable("id") int id, @ModelAttribute("user") User user,
								@RequestParam("files") MultipartFile[]  file)
	{
		//getting the User from its id and saving in UserID
		User userId =  this.userService.get(user.getId());
		//User userId = this.userService.get(id);

		if(userId !=null ) {
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

			// Handle uploaded files
			Arrays.stream(file).forEach(multipartFile ->{
				logger.info("number of files uploaded :{}",file.length);
				logger.info("file name :{}",multipartFile.getOriginalFilename().toString());
				logger.info("file name :{}",multipartFile.getContentType());
				logger.info("file name :{}",multipartFile.getSize());
				logger.info("file name :{}",multipartFile.getResource());

				String fileName = multipartFile.getOriginalFilename();

				try {
					//uploading the file in this path location
					// we can even create a variable and describe the location and use the variable name like  did in creating  or adding the file in Controller /RestController

					File storingFile = new ClassPathResource("static/files").getFile(); //abc.png
					Path fullPath = Paths.get(storingFile + File.separator + multipartFile.getOriginalFilename());  //src/files/abc.png
					Files.write(fullPath,multipartFile.getBytes());

					//copy or replace if file is already exists...
					Files.copy(multipartFile.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}



				//	user.setFileName(multipartFile.getOriginalFilename());
				user.setFileName(fileName);
				userId.setFileName(user.getFileName());

			});

			System.out.println(user.getFileName()); // Here getting null in fileName [Now its resolved]

			// Save the updated user to the database
			User UserData = this.userService.updateOrSave(userId);
			System.out.println("User Updated : " + UserData);


			// Add the updatedUser to the model
			logger.info("user","userdata");
			logger.info("message","Updated as requested !!");

			//m.addAttribute("user",UserData);
			//ra.addFlashAttribute("messge","Updated as requested !!");
			//m.addAttribute("title","User Management System");
			//return "redirect:/manage";

			return ResponseEntity.ok("Files successfully added .");

		}else {
			//return "redirect:/manage";//Exception page
			//logger.info("Error :","the file is not present here !!");
			logger.error("Error :","the file is not present here !!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}

		//return ResponseEntity.ok("Files successfully added returning  .");
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
	}






}
