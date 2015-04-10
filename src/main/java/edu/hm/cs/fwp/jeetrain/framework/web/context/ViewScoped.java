package edu.hm.cs.fwp.jeetrain.framework.web.context;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.NormalScope;

@NormalScope(passivating = true)
@Inherited
@Target({ TYPE, METHOD, FIELD })
@Retention(RUNTIME)
@Documented
public @interface ViewScoped {

}
