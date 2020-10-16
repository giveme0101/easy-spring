package org.spring.framework.core.beandefinition;

import lombok.extern.slf4j.Slf4j;
import org.spring.framework.core.Component;
import org.spring.framework.core.ComponentScan;
import org.spring.framework.core.aop.InterceptorFactory;
import org.spring.framework.core.bean.BeanDefinitionParser;
import org.spring.framework.core.bean.ImportClassImporter;
import org.spring.framework.core.util.EscapeUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
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

    private ClassLoader classLoader;

    private Map<String, Class> beanClassMap;

    public BeanDefinitionReader() {
        this.classLoader = BeanDefinitionReader.class.getClassLoader();
        this.beanClassMap = new ConcurrentHashMap<>();
    }

    public void register(Class<?>... configClass){
        for (final Class<?> aClass : configClass) {
            scan(aClass);
            doRegisterBean();
        }
    }

    private void scan(Class<?> configClass){

        String configPackage = configClass.getPackage().getName();
        doScan(configPackage);

        if (configClass.isAnnotationPresent(ComponentScan.class)) {

            ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
            String[] basePackages = componentScan.basePackage();

            InterceptorFactory.loadInterceptors(basePackages);

            for (final String basePackage : basePackages) {
                doScan(basePackage);
            }
        }
    }

    private void doScan(String basePackage){

        // 扫描Interceptor
        ImportClassImporter.importClass(basePackage);

        // 扫描其他bean
        try {
            String abstractPath = basePackage.replace(".", "/");
            URI uri = classLoader.getResource(abstractPath).toURI();

            File file = new File(uri);
            if (file.isDirectory()){
                scanDirectory(file);
            }
        } catch (URISyntaxException ex){
            ex.printStackTrace();
        }
    }

    private void doRegisterBean(){
        for (final Map.Entry<String, Class> entry : beanClassMap.entrySet()) {

            String beanName = entry.getKey();
            Class beanClass = entry.getValue();

            BeanDefinition bd = BeanDefinitionParser.parse(beanClass);
            BeanDefinitionHolder.put(beanName, bd);
        }
    }

    private void scanDirectory(File folder){
        for (final File file : folder.listFiles()) {

            if (file.isDirectory()){
                scanDirectory(file);
                continue;
            }

            if (isClassFile(file)){
                try {
                    String classPath = getClassPath(file);
                    Class<?> aClass = classLoader.loadClass(classPath);
                    registerBeanClass(aClass);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    private boolean isClassFile(File file){
        return file.getName().endsWith(".class");
    }

    private String getClassPath(File file){
        String absolutePath = file.getAbsolutePath();
        String classPath = absolutePath.substring(absolutePath.indexOf("\\target\\classes\\"), absolutePath.indexOf(".class"));
        String classPackage = classPath.replace("\\target\\classes\\", "").replace("\\", ".");
        return classPackage;
    }

    private boolean registerBeanClass(Class beanClass){

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
    }

}
