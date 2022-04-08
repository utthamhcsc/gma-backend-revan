package com.catalog.productcatalog.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.catalog.productcatalog.util.Slugify;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String  storeId;
	private String  storeName;
	private String  gstin;
	private String  storeAddress;
	private String  city;
	private String  country;
	private String  state;
	private String  location;
	@OneToMany(mappedBy = "store")
	@JsonIgnore
	private List<Product> products;
	private String sellerCategories;
	
	@PrePersist
	public void setIDforStore() {
		this.storeId=Slugify.toSlug(this.storeName);
	}
	

}
