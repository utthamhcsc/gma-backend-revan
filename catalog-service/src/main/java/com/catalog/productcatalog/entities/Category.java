package com.catalog.productcatalog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.catalog.productcatalog.util.Slugify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Category {
	@Id
	private String categoryId;
	private String categoryName;
	private String parentCategory;
	
	@PrePersist
	public void setCategory() {
		this.categoryId=Slugify.toSlug(this.categoryName);
	}

}
