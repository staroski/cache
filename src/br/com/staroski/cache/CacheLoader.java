package br.com.staroski.cache;

/**
 * Interface for loading objects from a {@link Cache cache}.
 * 
 * @author Ricardo Artur Staroski
 *
 * @param <K>
 *            Data type of the {@link Cache cache}'s search key.
 * @param <V>
 *            Data type of the object loaded from the search key.
 * @see Cache
 */
public interface CacheLoader<K, V> {

    /**
     * Uses the specified search key to load a value.
     * 
     * @param key
     *            The search key.
     * @return The value loaded from the search key.
     */
    public V load(K key);
}