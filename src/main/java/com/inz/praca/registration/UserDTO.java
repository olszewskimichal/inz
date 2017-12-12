package com.inz.praca.registration;

import com.inz.praca.validators.ValidEmail;
import com.inz.praca.validators.ValidPassword;
import javax.validation.constraints.Size;
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
  private String email;

  @ValidPassword
  private String password;

  private String confirmPassword;
}
