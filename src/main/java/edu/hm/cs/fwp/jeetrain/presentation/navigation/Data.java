package edu.hm.cs.fwp.jeetrain.presentation.navigation;

import java.io.Serializable;

/**
 * Simple {@code Domain Object}.
 * @author theism
 */
public class Data implements Serializable {

	private long id;
	
	private String text;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String toString() {
		return getClass().getSimpleName() + ":" + this.id;
	}
}
