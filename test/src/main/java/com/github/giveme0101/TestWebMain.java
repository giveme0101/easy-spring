package com.github.giveme0101;

import com.github.convert.BeanConverter;
import org.spring.framework.core.annotation.ComponentScan;
import org.spring.framework.core.annotation.Import;
import org.spring.framework.web.WebApplicationContext;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name demo
 * @Date 2020-10-23 11:25:28
 */
@ComponentScan(basePackage = {
        "com.github.giveme0101.config",
        "com.github.giveme0101.converter",
        "com.github.giveme0101.dao",
        "com.github.giveme0101.service",
        "com.github.giveme0101.controller"
})
@Import(BeanConverter.class)
public class TestWebMain {

    public static void main(String[] args) {

        WebApplicationContext context = new WebApplicationContext();
        context.run(TestWebMain.class);

    }

}
