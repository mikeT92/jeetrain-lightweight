/*
 * jeetrain-lightweight:FacesConfigurationBean.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.presentation;

import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;

/**
 * Configuration bean that enables true support of JSF version 2.3 when run in a
 * Java EE 8 application server.
 * <p>
 * Please make sure that context parameter
 * {@code javax.faces.ENABLE_CDI_RESOLVER_CHAIN} is set to {@code true} as well.
 * </p>
 * 
 * @author theism
 * @version 1.0
 * @since 27.12.2017
 */
@FacesConfig(version = Version.JSF_2_3)
public class FacesConfigurationBean {

}
