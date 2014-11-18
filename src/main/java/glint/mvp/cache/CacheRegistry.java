package glint.mvp.cache;

/**
 *
 * All singleton cache needs to register with the central cache manager
 *
 * Created by ifwonderland on 11/17/14.
 */
public interface CacheRegistry<K, V> {

    /**
     * Get a singleton instance of this cache.
     * @return singleton instance of this cache.
     */
    V get(K key);

    /**
     * remove by key
     * @param key
     */
    void remove(K key);


    /**
     * Clears all the caches.
     */
    void clear();

}
