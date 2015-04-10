package edu.hm.cs.fwp.jeetrain.presentation.navigation;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * {@code ManagedBean} backing views page01 and page02.
 * 
 * @author theism
 */
//@Named("navigator")
//@RequestScoped
public class NavigatorBean implements Serializable {

	private long id;

	private Data data = new Data();

	public Data getData() {
		return data;
	}

	public long getId() {
		return id;
	}

	public void setId(long key) {
		this.id = key;
	}

	/**
	 * Event handler called before the associated view is rendered.
	 */
	public void onPreRenderView() {

		if (this.id != 0L) {
			this.data = (Data) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap()
					.remove(Long.toString(this.id));
			if (this.data == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Missing object in session!", null));
			}
		}
	}

	public String apply() {
		System.out.println(getClass().getSimpleName() + "#apply data=" + data);
		this.data.setId(System.currentTimeMillis()); // simuliert generierung
														// von Schlüsseln
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(Long.toString(this.data.getId()), this.data);
		return "page02?faces-redirect=true&id=" + this.data.getId();
	}
}
