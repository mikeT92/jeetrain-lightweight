package edu.hm.cs.fwp.jeetrain.presentation.navigation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@code ManagedBean} backing views page01 and page02.
 * 
 * @author theism
 */
@Named("navigator")
@ConversationScoped
public class ConversationalNavigatorBean implements Serializable {

	@Inject
	private Conversation conversation;

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

	public ConversationalNavigatorBean() {
		System.out.println(getClass().getSimpleName() + "#ConversationalNavigatorBean: "
				+ this.conversation);
	}
	
	@PostConstruct
	public void onPostConstruct() {
		System.out.println(getClass().getSimpleName() + "#onPostConstruct: "
				+ this.conversation);
	}
	
	@PreDestroy
	public void onPreDestroy() {
		System.out.println(getClass().getSimpleName() + "#onPreDestroy: "
				+ this.conversation);
	}

	/**
	 * Event handler called before the associated view is rendered.
	 */
	public void onPreRenderView() {
		System.out.println(getClass().getSimpleName() + "#onPreRenderView: "
				+ this.conversation);
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
	}

	public String apply() {
		System.out.println(getClass().getSimpleName() + "#apply data=" + data);
		this.data.setId(System.currentTimeMillis()); // simuliert generierung
														// von Schlüsseln
		return "page02?faces-redirect=true";
	}

	public String close() {
		if (!this.conversation.isTransient()) {
			this.conversation.end();
		}
		return "/home/home?faces-redirect=true";
	}
}
