package org.spring.framework.core.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name ClassScanner
 * @Date 2020/10/26 10:09
 */
public class ClassScanner {

    private static ClassLoader CLASS_LOADER = ClassLoader.class.getClassLoader();
    static {
        if (null == CLASS_LOADER){
            CLASS_LOADER = ClassLoader.getSystemClassLoader();
        }
    }

    public static Set<Class> scan(String basePackage, Predicate selector){

        try {
            String abstractPath = basePackage.replace(".", "/");
            URL resource = CLASS_LOADER.getResource(abstractPath);
            if (null == resource){
                return new HashSet<>(0);
            }

            URI uri = resource.toURI();
            File file = new File(uri);
            if (!file.isDirectory()){
                throw new RuntimeException("not a package");
            }

            return scanDirectory(file, selector);
        } catch (URISyntaxException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private static Set<Class> scanDirectory(File folder, Predicate selector){

        Set<Class> classSet = new HashSet<>();

        for (final File file : folder.listFiles()) {

            if (file.isDirectory()){
                classSet.addAll(scanDirectory(file, selector));
                continue;
            }

            if (isClassFile(file)){
                try {
                    String classPath = getClassPath(file);
                    Class<?> aClass = CLASS_LOADER.loadClass(classPath);

                    if (selector.test(aClass)) {
                        classSet.add(aClass);
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        return classSet;
    }

    private static boolean isClassFile(File file){
        return file.getName().endsWith(".class");
    }

    private static String getClassPath(File file){
        String absolutePath = file.getAbsolutePath();
        String classPath = absolutePath.substring(absolutePath.indexOf("\\target\\classes\\"), absolutePath.indexOf(".class"));
        String classPackage = classPath.replace("\\target\\classes\\", "").replace("\\", ".");
        return classPackage;
    }

}
