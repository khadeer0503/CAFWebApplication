package com.CAF.WebApplication.Model;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="User_Management")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String codicefiscale;
	private String documents;
	private String description;
	private String gender;
	private String city;
	@Lob
	private String fileName;
	@Column(length = 15)
	private long phone;
	private String dob;
	private String status;
	@CreationTimestamp
	private LocalDateTime registerLocalDateTime;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(int id, String name, String email, String codicefiscale, String documents, String description, String gender, String city, String fileName, long phone, String dob, String status, LocalDateTime registerLocalDateTime, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.codicefiscale = codicefiscale;
		this.documents = documents;
		this.description = description;
		this.gender = gender;
		this.city = city;
		this.fileName = fileName;
		this.phone = phone;
		this.dob = dob;
		this.status = status;
		this.registerLocalDateTime = registerLocalDateTime;
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodicefiscale() {
		return codicefiscale;
	}

	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}

	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getRegisterLocalDateTime() {
		return registerLocalDateTime;
	}

	public void setRegisterLocalDateTime(LocalDateTime registerLocalDateTime) {
		this.registerLocalDateTime = registerLocalDateTime;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", codicefiscale='" + codicefiscale + '\'' +
				", documents='" + documents + '\'' +
				", description='" + description + '\'' +
				", gender='" + gender + '\'' +
				", city='" + city + '\'' +
				", fileName='" + fileName + '\'' +
				", phone=" + phone +
				", dob='" + dob + '\'' +
				", status='" + status + '\'' +
				", registerLocalDateTime=" + registerLocalDateTime +
				", updatedAt=" + updatedAt +
				'}';
	}
}
