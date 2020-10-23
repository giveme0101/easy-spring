package org.spring.framework.web.factory;

import io.netty.handler.codec.http.HttpMethod;
import org.spring.framework.web.annotation.GetMapping;
import org.spring.framework.web.annotation.PostMapping;
import org.spring.framework.web.annotation.RestController;
import org.spring.framework.web.entity.MethodDetail;
import org.spring.framework.web.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Save routing-related mapping information
 *
 * @author shuang.kou
 * @createTime 2020年09月29日 13:27:00
 **/
public class RouteMethodMapper {

    // get request url -> target method.
    // eg: "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> UserController.get(java.lang.Integer)
    private static final Map<String, Method> GET_REQUEST_MAPPINGS = new HashMap<>();
    // post request url -> target method.
    private static final Map<String, Method> POST_REQUEST_MAPPINGS = new HashMap<>();
    // formatted get request url -> original url
    // eg : "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> /user/{id}
    private static final Map<String, String> GET_URL_MAPPINGS = new HashMap<>();
    // formatted post request url -> original url
    private static final Map<String, String> POST_URL_MAPPINGS = new HashMap<>();

    public static void loadRoutes(String configPackage) {

        Set<Class<?>> classes = ReflectionUtil.scanAnnotatedClass(configPackage, RestController.class);

        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (null != restController) {
                Method[] methods = aClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        if (getMapping != null) {
                            String url = getMapping.value();
                            String formattedUrl = formatUrl(url);
                            GET_REQUEST_MAPPINGS.put(formattedUrl, method);
                            GET_URL_MAPPINGS.put(formattedUrl, url);
                        }
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            String url = postMapping.value();
                            String formattedUrl = formatUrl(url);
                            POST_REQUEST_MAPPINGS.put(formattedUrl, method);
                            POST_URL_MAPPINGS.put(formattedUrl, url);
                        }
                    }
                }
            }
        }
    }

    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = new MethodDetail();
        if (httpMethod == HttpMethod.GET) {
            methodDetail.build(requestPath, GET_REQUEST_MAPPINGS, GET_URL_MAPPINGS);
            return methodDetail;
        }

        if (httpMethod == HttpMethod.POST) {
            methodDetail.build(requestPath, POST_REQUEST_MAPPINGS, POST_URL_MAPPINGS);
            return methodDetail;
        }
        return null;
    }

    /**
     * format the url
     * for example : "/user/{name}" -> "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$"
     */
    private static String formatUrl(String url) {
        // replace {xxx} placeholders with regular expressions matching Chinese, English letters and numbers, and underscores
        String originPattern = url.replaceAll("(\\{\\w+})", "[^/]+");
        String pattern = "^" + originPattern + "/?$";
        return pattern.replaceAll("/+", "/");
    }

}
