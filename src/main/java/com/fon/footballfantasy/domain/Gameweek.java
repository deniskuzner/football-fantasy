package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "gameweeks")
public class Gameweek implements Serializable {

	private static final long serialVersionUID = -2505204007816968035L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	@Column(name = "order_number")
	private int orderNumber;
	@OneToMany(mappedBy = "gameweek", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Match> matches;
	@JsonIgnoreProperties(value = "gameweek", allowSetters = true)
	@OneToMany(mappedBy = "gameweek")
	private List<PlayerGameweekPerformance> playerGameweekPerformances;
	
}
