/* Model.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.servlet;

import java.io.Serializable;

/**
 * Simple view model object holding the view data.
 * 
 * @author theism
 * @version %PR% %PRT% %PO%
 * @since release 1.0 11.04.2013 23:28:27
 */
public class Model implements Serializable {

	private static final long serialVersionUID = -106291244415377437L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
