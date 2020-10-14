package org.spring.framework.core.beandefinition;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanDefinition
 * @Date 2020/09/17 9:31
 */
@Getter
public class BeanDefinition {

    public static final String SINGLETON = "singleton";
    public static final String PROTOTYPE = "prototype";

    @Setter
    private Class<?> beanClass;

    @Setter
    private Boolean isLazyInit = false;

    @Setter
    private Boolean isPrimary = true;

    @Setter
    private String scope = SINGLETON;

    @Setter
    private Boolean isFactoryBean = false;

    private Boolean isSingleton = true;

    private Boolean isPrototype = false;

    public void setScope(String scope) {
        this.isSingleton = SINGLETON.equals(scope);
        this.isPrototype = PROTOTYPE.equals(scope);
        this.scope = scope;
    }

    public void setIsSingleton(boolean isSingleton){
        this.isSingleton = isSingleton;
        this.isPrototype = !isSingleton;
        this.scope = isSingleton ? SINGLETON : PROTOTYPE;
    }
}
