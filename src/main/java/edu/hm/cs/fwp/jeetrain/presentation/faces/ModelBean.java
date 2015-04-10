/* ModelBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.faces;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Size;

/**
 * {@code ManagedBean} backing the /helloFaces/helloWorld view.
 * 
 * @author theism
 * @version %PR% %PRT% %PO%
 * @since release 1.0 11.04.2013 23:53:35
 */
@Named("model")
@RequestScoped
public class ModelBean implements Serializable {

	private static final long serialVersionUID = 4069463169212859579L;

	@Size(max = 8)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String apply() {
		return null;
	}
}
