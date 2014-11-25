package glint.mvp.dao;

import java.util.List;

/**
 * Define generic interface for DAO layer
 * <p/>
 * Created by ifwonderland on 11/22/14.
 */
public interface Dao<T> {

    /**
     * Get one by id
     *
     * @param id
     * @return
     */
    T get(int id);

    /**
     * List all entries
     *
     * @return
     */
    List<T> list();

}
