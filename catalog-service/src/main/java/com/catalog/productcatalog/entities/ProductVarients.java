package com.catalog.productcatalog.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVarients implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String variant;
	private String sku;
	private Boolean isBestSale;
	private Boolean isFeatured;
	private String productImage;
	private Double price;
	private Double availableQty;
	private Boolean isUnlimited;
	
	private Double minQty=1D;
	private Double maxQty;
	@ManyToOne
	@JsonIgnoreProperties("productVariants")
	private Product product;

}
