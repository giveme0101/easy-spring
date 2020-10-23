package org.spring.framework.web;

import org.spring.framework.core.bd.BeanDefinitionParser;
import org.spring.framework.core.bd.BeanDefinitionRegistry;
import org.spring.framework.core.bd.RootBeanDefinition;
import org.spring.framework.core.context.AnnotationConfigApplicationContext;
import org.spring.framework.web.factory.RequestHandlerResolver;
import org.spring.framework.web.factory.UrlMethodRouter;
import org.spring.framework.web.handler.GetRequestHandler;
import org.spring.framework.web.handler.PostRequestHandler;
import org.spring.framework.web.server.HttpServerHandler;
import org.spring.framework.web.server.NettyServer;

import java.util.LinkedHashSet;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name WebApplicationContext
 * @Date 2020/10/23 11:08
 */
public class WebApplicationContext extends AnnotationConfigApplicationContext {

    public WebApplicationContext() {
        super();
        BeanDefinitionRegistry.putAll(new LinkedHashSet<RootBeanDefinition>(){{

            add(BeanDefinitionParser.parse(UrlMethodRouter.class));
            add(BeanDefinitionParser.parse(RequestHandlerResolver.class));
            add(BeanDefinitionParser.parse(GetRequestHandler.class));
            add(BeanDefinitionParser.parse(PostRequestHandler.class));
            add(BeanDefinitionParser.parse(HttpServerHandler.class));
            add(BeanDefinitionParser.parse(NettyServer.class));

        }});
    }

}