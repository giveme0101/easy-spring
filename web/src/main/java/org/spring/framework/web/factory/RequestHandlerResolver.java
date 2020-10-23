package org.spring.framework.web.factory;

import io.netty.handler.codec.http.HttpMethod;
import org.spring.framework.core.aware.BeanFactoryAware;
import org.spring.framework.core.beans.BeanFactory;
import org.spring.framework.web.exception.RequestMethodNotSupport;
import org.spring.framework.web.handler.GetRequestHandler;
import org.spring.framework.web.handler.PostRequestHandler;
import org.spring.framework.web.handler.RequestHandler;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name RequestHandlerResolver
 * @Date 2020/10/23 16:18
 */
public class RequestHandlerResolver implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public RequestHandler getHandler(HttpMethod httpMethod) {

        if (HttpMethod.GET.equals(httpMethod)){
            return beanFactory.getBean(GetRequestHandler.class);
        }

        if (HttpMethod.POST.equals(httpMethod)){
            return beanFactory.getBean(PostRequestHandler.class);
        }

        throw new RequestMethodNotSupport("Http method [" + httpMethod.name() + "] not support");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
