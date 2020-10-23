package org.spring.framework.core.aware;

import org.spring.framework.core.config.StringValueResolver;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name EmbeddedValueResolverAware
 * @Date 2020/10/23 10:09
 */
public interface EmbeddedValueResolverAware {

    void setEmbeddedValueResolver(StringValueResolver resolver);

}
