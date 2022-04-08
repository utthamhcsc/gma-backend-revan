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
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

	@Id
	private String currencyId;
	private String currencyName;
	private String currencySymbol;
	@PrePersist
	public void setCurrencyKey() {
	this.currencyId=Slugify.toSlug(this.currencyName);	
	}
	
}
