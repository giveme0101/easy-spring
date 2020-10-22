package org.spring.framework.core.aware;

import java.util.Map;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ResourceAware
 * @Date 2020/10/20 15:20
 */
public interface ResourceAware extends Aware {

    void setResources(Map<String, String> resources);

}