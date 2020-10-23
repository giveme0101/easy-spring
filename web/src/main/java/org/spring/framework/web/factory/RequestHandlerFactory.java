package org.spring.framework.web.factory;

import io.netty.handler.codec.http.HttpMethod;
import org.spring.framework.core.aware.ApplicationContextAware;
import org.spring.framework.core.context.ApplicationContext;
import org.spring.framework.web.handler.GetRequestHandler;
import org.spring.framework.web.handler.PostRequestHandler;
import org.spring.framework.web.handler.RequestHandler;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:28:00
 **/
public class RequestHandlerFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public RequestHandler get(HttpMethod httpMethod) {

        if (HttpMethod.GET.equals(httpMethod)){
            return applicationContext.getBean(GetRequestHandler.class);
        }

        if (HttpMethod.POST.equals(httpMethod)){
            return applicationContext.getBean(PostRequestHandler.class);
        }

        throw new RuntimeException("请求方式暂未实现");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }
}
