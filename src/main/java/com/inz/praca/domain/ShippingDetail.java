package com.inz.praca.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

@Entity
@Data
@NoArgsConstructor(force = true)
public class ShippingDetail implements Serializable {

	private String street;
	private String city;
	private String houseNum;
	private String postCode;
	private String phoneNumber;
	@Id
	@GeneratedValue
	private Long id;

	public ShippingDetail(String street, String city, String houseNum, String postCode) {
		Assert.hasLength(city, "Miasto nie moze byc puste");
		Assert.hasLength(street, "Ulica nie moze byc pusta");
		Assert.hasLength(postCode, "Kod pocztowy nie moze byc pusty");
		this.street = street;
		this.houseNum = houseNum;
		this.city = city;
		this.postCode = postCode;
	}

}
