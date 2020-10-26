package org.spring.framework.web.annotation;

import org.spring.framework.core.annotation.Import;
import org.spring.framework.web.factory.RequestHandlerResolver;
import org.spring.framework.web.factory.UrlMethodRouter;
import org.spring.framework.web.handler.GetRequestHandler;
import org.spring.framework.web.handler.PostRequestHandler;
import org.spring.framework.web.server.HttpServerHandler;
import org.spring.framework.web.server.NettyServer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import(value = {
        UrlMethodRouter.class,
        RequestHandlerResolver.class,
        GetRequestHandler.class,
        PostRequestHandler.class,
        HttpServerHandler.class,
        NettyServer.class
})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableWebMVC {

}
