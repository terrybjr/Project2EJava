package application.state;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import lombok.Data;

@SessionScoped
@Data
public class ApplicationState implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

}
