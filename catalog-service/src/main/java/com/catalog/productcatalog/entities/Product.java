package com.catalog.productcatalog.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String productSlug;
	private String productName;
	private String productCategories;//electronics,mobile phone etc
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="store")
	private Store store;
	@Lob
	private String details;
	//private List<ProductVarientOptions> productVarients;
	private String invoiceDetails;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id",referencedColumnName = "id")
	private List<ProductAttributes> productAttributes;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private List<ProductVarients> productVariants;
	
	
	
	
	
	
	private String brand;
//	@ElementCollection(fetch = FetchType.LAZY)
//	//  @CollectionTable(name = "raw_events_custom", joinColumns = @JoinColumn(name =     "raw_event_id"))
//	  @MapKeyColumn(name = "key", length = 50)
//	  @Column(name = "value", length = 100)
//	  @BatchSize(size = 20)
//	  private Map<String, String> extraFields = new HashMap<String, String>();
	
	@PrePersist
	public void saveSlug() {
		this.productSlug=Slugify.toSlug(this.productName);
//		if(this.productVariants==null || this.productVariants.size()<=0) {
//			ProductVarients pv=ProductVarients.builder()
//					.isBestSale(false).isFeatured(false)
//					.isUnlimited(false)
//					.availableQty(availableQty)
//			
//		}
	}
}

