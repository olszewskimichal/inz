package com.inz.praca.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.util.Assert;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
public class ShippingDetail {

	private final String street;
	private final String city;
	private final String houseNum;
	private final String postCode;
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
