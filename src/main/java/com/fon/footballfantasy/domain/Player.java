package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class Player implements Serializable {
	
	private static final long serialVersionUID = -7725311564610239522L;
	
	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	
	private String url;
	private String name;
	private String nationality;
	private String birthDate;
	private String age;
	private String position;
	private String height;
	private String weight;
	private String image;
	private Club club;
	
}
