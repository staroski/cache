package br.com.staroski.tools.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of an object cache.<BR>
 * Instances of this class depend on a {@link CacheLoader} to load objects on demand.<BR>
 * 
 * @author Ricardo Artur Staroski
 * 
 * @see CacheLoader
 */
@SuppressWarnings("unchecked")
public final class Cache {

    // map that stores the loaded objects
    private final Map<MultiKey, Object> map = new HashMap<MultiKey, Object>();

    /**
     * Clears the content of this {@link Cache cache}.
     */
    public void clear() {
        map.clear();
    }

    /**
     * Gets a value from the specified search key.<BR>
     * If the value was not yet loaded then the specified {@link CacheLoader} will be used to load it and store in {@link Cache cache}.
     * 
     * @param loader
     *            The {@link CacheLoader} responsible for loading the value.
     * @param key
     *            The search key used to get the value.
     * @return The value found for the specified search key.
     * @throws IllegalArgumentException
     *             If the {@link CacheLoader} is <code>null</code>
     */
    public <K, V> V get(final CacheLoader<K, V> loader, final K key) {
        if (loader == null) {
            throw new IllegalArgumentException("loader cannot be null");
        }
        final MultiKey pair = MultiKey.composedBy(loader, key);
        V value = (V) map.get(pair);
        if (value == null) {
            synchronized (this) { // the 2 nested if's really are equals, it's a "double-checked locking"
                value = (V) map.get(pair);
                if (value == null) {
                    value = loader.load(key); // let the loader load the value for the specified key
                    map.put(pair, value);
                }
            }
        }
        return value;
    }

    /**
     * Removes from cache the value associated with the specified {@link CacheLoader} and search key.
     * 
     * @param loader
     *            The {@link CacheLoader}.
     * @param key
     *            The search key.
     */
    public <K, V> void remove(final CacheLoader<K, V> loader, final K key) {
        final MultiKey pair = MultiKey.composedBy(loader, key);
        if (map.containsKey(pair)) {
            synchronized (this) {
                map.remove(pair);
            }
        }
    }
}