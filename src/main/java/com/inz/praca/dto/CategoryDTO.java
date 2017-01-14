package com.inz.praca.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	@NotNull
	@Size(min = 4, max = 20)
	private String name;

	@NotNull
	@Size(min = 5)
	private String description;

}
