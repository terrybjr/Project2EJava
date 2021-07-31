package application.security;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class LoginDS {
	String email;
	String password;
}
