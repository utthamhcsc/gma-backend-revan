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
public class Brand {
	
	@Id
	private String id;
	private String brandName;
	private String brandWebsite;
	private String brandIcon;
	private String brandImage;
	
	@PrePersist
	public void setBrandId() {
		this.id=Slugify.toSlug(this.brandName);
	}
	

}
