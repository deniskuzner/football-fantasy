package com.fon.footballfantasy.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "matches")
public class Match implements Serializable {

	private static final long serialVersionUID = 9089264137411582677L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	private String url;
	private String result;
	@Column(name = "date_time")
	private LocalDateTime dateTime;
	private String venue;
	@ManyToOne
	@JoinColumn(name = "host_id")
	private Club host;
	@ManyToOne
	@JoinColumn(name = "guest_id")
	private Club guest;
	@JsonIgnoreProperties(value = "matches")
	@ManyToOne
	@JoinColumn(name = "gameweek_id")
	private Gameweek gameweek;
	@Transient
	private List<MatchEvent> events;
	
}
