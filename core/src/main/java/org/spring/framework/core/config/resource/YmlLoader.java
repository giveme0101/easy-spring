package org.spring.framework.core.config.resource;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author kevin xiajun94@FoxMail.com
 * @Description
 * @name YmlLoader
 * @Date 2020/10/22 14:39
 */
public class YmlLoader extends AbstractResourcesLoader {

    private static final String[] YML_EXTENSIONS = new String[]{".yml", ".yaml"};

    @Override
    public Map<String, String> loadProperty() throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(this.getProperty()));
        Map<String, Object> map = new Yaml().loadAs(isr, Map.class);
        return expandMap(null, map);
    }

    @Override
    public boolean isMatch(File file) {
        boolean match = false;
        for (final String suffix : YML_EXTENSIONS) {
            if (file.getName().endsWith(suffix)){
                match = true;
                break;
            }
        };

        return match;
    }

    private Map<String, String> expandMap(String path, Map<String, Object> map){

        Map<String, String> resultMap = new HashMap<>();

        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value instanceof Map){
                resultMap.putAll(expandMap(concatPath(path, key), (Map<String, Object>) value));
                continue;
            }

            resultMap.put(concatPath(path, key), String.valueOf(value));
        }

        return resultMap;
    }

    private String concatPath(String path, String key){
        return path == null ? key : path.concat(".").concat(key);
    }

}
