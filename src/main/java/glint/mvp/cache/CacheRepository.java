package glint.mvp.cache;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Central repository for caches. Final class and cannot be subclassed.
 * <p/>
 * Created by ifwonderland on 11/17/14.
 */
public final class CacheRepository {

    private static Logger log = Logger.getLogger(CacheRepository.class);

    private static Map<String, CacheRegistry> caches = new HashMap<>();

    private CacheRepository() {
        //private constructor to prevent instantiation
    }


    /**
     * Synchronized impl to get instance of singleton CacheRegistry, if not found, return null.
     * @param className
     * @return null if not found otherwise returns singleton cache registry
     */
    public static synchronized CacheRegistry getInstance(String className) {
        if (StringUtils.isEmpty(className)){
            log.warn("Class name passed in is null or empty. No cache found, return null.");
            return null;
        }


        CacheRegistry cache = caches.get(className);
        if(cache!=null){
            log.debug("Cache found for class name: "+className);
            return cache;
        }

        //otherwise instantialize these caches, lazy instantiation
        Class c = null;
        try {
            c = Class.forName(className);
            Object obj = c.newInstance();

            if(!(obj instanceof CacheRegistry))
                throw new ClassNotFoundException("Class is not of type: CacheRegistry. "+className);

            cache = (CacheRegistry)obj;

            caches.put(className, cache);
            return cache;
        } catch (Exception e) {
            log.error("Creating new instance for class name: "+className+" failed. ", e);
            return null;
        }

    }


}
