package com.catalog.productcatalog.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.catalog.productcatalog.util.Slugify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variant {
	@Id
	private String variantName;//size
	private String variantValues;//xl,l,s,m
	private String categories;//clothes,t-shirt
	
	@PrePersist
	public void saveVariantName() {
		this.variantName=Slugify.toSlug(this.variantName);
	}
}
