package com.education.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Entity
@Table(name = "student")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FULL_NAME")
	@NotNull(message = "full name is null")
	private String fullName;
	
	@Column(name = "EMAIL")
	@Email(regexp = "^(.+)@(.+)$",message = "Invalid email")
	private String email;
	
	@Column(name = "MOBILE")
	@NotNull(message = "mobile is required")
	private String mobile;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "USERNAME",unique = true)
	@NotNull(message = "username is null")
	private String userName;
	
	@Column(name = "ACCESS_TOKEN")
	private String accessToken;
	
	
	@Column(name = "PASSWORD")
	@NotNull(message = "password is required")
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "students_courses",
	  joinColumns = {
			  @JoinColumn(name = "student_id",referencedColumnName = "id",nullable = false , updatable = false)
	  },
	  inverseJoinColumns = {
			  @JoinColumn(name = "course_id",referencedColumnName = "id",nullable = false , updatable = false)
	  }
			)
	private Set<Course> courses = new HashSet<>();
}
