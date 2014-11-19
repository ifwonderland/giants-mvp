package glint.mvp.cache;

/**
 *
 * All singleton cache needs to register with the central cache manager
 *
 * Created by ifwonderland on 11/17/14.
 */
public interface Cache<K, V> {

    /**
     * Get a singleton instance of this cache.
     * @return singleton instance of this cache.
     */
    V get(K key);


    /**
     * Add new cache into the registry
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * remove by key and returned its value if found otherwise return null
     * @param key
     */
    V remove(K key);


    /**
     *remove the first element in the cache
     */
    V remove() throws Exception;


    /**
     * Clears all the caches.
     */
    void clear();

}
