package application.cdi.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;

import lombok.Data;

@RequestScoped
@Data
public class Global implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userId;

}
