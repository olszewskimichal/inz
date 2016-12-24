package com.inz.praca.dto;

import javax.validation.constraints.Size;

import com.inz.praca.validators.ValidEmail;
import com.inz.praca.validators.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserDTO {

	@NotBlank
	@Size(min = 3, max = 30)
	private String name;

	@NotBlank
	@Size(min = 3, max = 30)
	private String lastName;

	@ValidEmail
	@Size(max = 50)
	private String email;

	@ValidPassword
	private String password;

	private String confirmPassword;
}
