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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "clubs")
public class Club implements Serializable {
	
	private static final long serialVersionUID = 7705085158586004971L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "modified_on")
	private LocalDateTime modifiedOn;
	
	private String url;
	private String name;
	private String image;
	@JsonIgnoreProperties(value = "club", allowSetters = true)
	@OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Player> players;
	
	@Override
	public String toString() {
		return "Club [id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", url=" + url + ", name="
				+ name + ", image=" + image + "]";
	}

}
