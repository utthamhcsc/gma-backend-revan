package com.catalog.productcatalog.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.catalog.productcatalog.util.Slugify;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table
@Data
public class SellerCategory implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("categorySlug")
	private String categoryId;
	
	private String categoryName;
	
	private String parentCategory;
	
	@PrePersist
	public void setCategory() {
		this.categoryId=Slugify.toSlug(this.categoryName);
	}

}
