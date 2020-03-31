package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "players")
public class Player implements Serializable {
	
	private static final long serialVersionUID = -7725311564610239522L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	private String url;
	private String name;
	private String nationality;
	@Column(name = "birth_date")
	private String birthDate;
	private String age;
	private String position;
	private String height;
	private String weight;
	private String image;
	@ManyToOne
	@JoinColumn(name = "club_id")
	private Club club;
	
}
