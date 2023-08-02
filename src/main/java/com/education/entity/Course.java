package com.education.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "COURSE_NAME")
	private String courseName;
	
	@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Student> students = new HashSet<>();
}
