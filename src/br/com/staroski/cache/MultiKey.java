package br.com.staroski.cache;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a key composed of more than one object.
 * 
 * @author Ricardo Artur Staroski
 */
@SuppressWarnings("unchecked")
public final class MultiKey implements Comparable<MultiKey> {

    /**
     * Creates a new {@link MultiKey compound key}.
     * 
     * @param first
     *            The first object of the key.
     * @param second
     *            The second object of the key.
     * @param more
     *            More objects that compose the key (<B>Optional</B>).
     */
    public static MultiKey composedBy(Object first, Object second, Object... more) {
        return new MultiKey(first, second, more);
    }

    // this class is immutable so the hashCode can be cached
    private final int hashCode;

    // the enclosed values that compose this compound key
    private final List<Object> keys;

    /**
     * Non public constructor, instances are created by factory method {@link #composedBy(Object, Object, Object...)}
     */
    private MultiKey(Object first, Object second, Object... more) {
        List<Object> allKeys = new LinkedList<Object>();
        allKeys.add(first);
        allKeys.add(second);
        allKeys.addAll(Arrays.asList(more));
        this.keys = allKeys;
        this.hashCode = allKeys.hashCode();
    }

    @Override
    public int compareTo(MultiKey other) {
        return this.hashCode - (other == null ? 0 : other.hashCode);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof MultiKey) {
            final MultiKey that = (MultiKey) object;
            // if the hashes are different then the keys also are different
            if (this.hashCode != that.hashCode) {
                return false;
            }
            // different keys can have the same hash, so we compare them with equals
            return this.keys.equals(that.keys);
        }
        return false;
    }

    /**
     * Gets the object at the specified index of this {@link MultiKey compound key}.
     * 
     * @param index
     *            The index of the desired object.
     * @return The object at the specified index.
     */
    public <T> T get(int index) {
        return (T) keys.get(index);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    /**
     * @return The amount of objects that compose this {@link MultiKey compound key}.
     */
    public int size() {
        return keys.size();
    }

    @Override
    public String toString() {
        return String.format("%s%s", getClass().getSimpleName(), keys.toString());
    }
}
