package org.spring.framework.core.config;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name EmbeddedValueResolverAware
 * @Date 2020/10/23 10:09
 */
public interface EmbeddedValueResolverAware {

    void setEmbeddedValueResolver(StringValueResolver resolver);

}
