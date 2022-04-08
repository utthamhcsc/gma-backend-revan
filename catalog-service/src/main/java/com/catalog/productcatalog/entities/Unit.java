package com.catalog.productcatalog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer unitId;
	private String unitName;
	private String unitShortName;

}
