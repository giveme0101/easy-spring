package org.spring.framework.core.bd;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.aop.InterceptorFactory;
import org.spring.framework.core.annotation.Component;
import org.spring.framework.core.annotation.ComponentScan;
import org.spring.framework.core.beans.ImportClassLoader;
import org.spring.framework.core.util.ClassScanner;
import org.spring.framework.core.util.EscapeUtil;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name BeanDefinitionReader
 * @Date 2020/09/17 9:23
 */
@Slf4j
public class BeanDefinitionReader {

    private Map<String, Class> beanClassMap;

    public BeanDefinitionReader() {
        this.beanClassMap = new ConcurrentHashMap<>();
    }

    public void register(Class<?>... configClass){
        for (final Class<?> aClass : configClass) {
            scan(aClass);
            doRegisterBean();
        }
    }

    private void scan(Class<?> configClass) {

        // 扫描@Import
        ImportClassLoader.loadClass(configClass);

        // 扫描@ComponentScan
        if (configClass.isAnnotationPresent(ComponentScan.class)) {

            ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
            String[] basePackages = componentScan.basePackage();

            // 加载包下的interceptor拦截器
            InterceptorFactory.loadInterceptors(basePackages);

            for (final String basePackage : basePackages) {
                doScan(basePackage);
            }
        }
    }

    private void doScan(String basePackage){

        // 加载包下的@Import
        ImportClassLoader.loadPackage(basePackage);

        // @Component、@Service等
        ClassScanner.scan(basePackage, (clazz) -> {

            Class beanClass = (Class) clazz;

            Annotation[] annotations = beanClass.getAnnotations();
            for (final Annotation annotation : annotations) {

                Class<? extends Annotation> aClass = annotation.annotationType();

                if (aClass == Component.class){
                    Component component = (Component) beanClass.getAnnotation(Component.class);
//                String beanName = component.value();
//                if (null == beanName || beanName.isEmpty()){
                    String className = beanClass.getSimpleName();
                    String beanName = EscapeUtil.firstCharLowerCase(className);
//                }
                    beanClassMap.put(beanName, beanClass);
                }

                if (aClass.isAnnotationPresent(Component.class)){
                    String className = beanClass.getSimpleName();
                    String beanName = EscapeUtil.firstCharLowerCase(className);
                    beanClassMap.put(beanName, beanClass);
                    return true;
                }
            }

            return false;
        });
    }

    private void doRegisterBean(){
        for (final Map.Entry<String, Class> entry : beanClassMap.entrySet()) {

            String beanName = entry.getKey();
            Class beanClass = entry.getValue();

            RootBeanDefinition bd = BeanDefinitionParser.parse(beanClass);
            BeanDefinitionRegistry.put(beanName, bd);
        }
    }

}
