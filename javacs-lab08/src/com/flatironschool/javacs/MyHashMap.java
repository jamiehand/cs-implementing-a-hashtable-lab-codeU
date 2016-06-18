/**
 *
 */
package com.flatironschool.javacs;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

	// average number of entries per map before we rehash
	protected static final double FACTOR = 1.0;

	// keep track of size here to be able to set it to 0 before rehashing
	private int size = 0;

	@Override
	public void clear() {
		super.clear();
		size = 0;
	}

	@Override
	public V put(K key, V value) {
		// V oldValue = super.put(key, value);
		MyLinearMap<K, V> map = chooseMap(key);
		// The key may or may not be new to the map. If it's new, it will increase
		// the map's size; if it's not, it won't. Keeping track of size in the
		// way below allows us to account for this.
		size -= map.size();
		V oldValue = map.put(key, value);
		size += map.size();

		//System.out.println("Put " + key + " in " + map + " size now " + map.size());

		// check if the number of elements per map exceeds the threshold
		if (size() > maps.size() * FACTOR) {
			size = 0;
			rehash();
		}
		return oldValue;
	}

	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	protected void rehash() {
        // TODO: fill this in.
        // throw new UnsupportedOperationException();
				/* make an array to hold all n entries */
				ArrayList<Entry> entries = new ArrayList<Entry>(size());
				for (MyLinearMap map : maps) {
						entries.addAll(map.getEntries());
				}
				makeMaps(2*maps.size());
				for (Entry<K, V> entry : entries) {
					put(entry.getKey(), entry.getValue());
				}
	}

	@Override
	public V remove(Object key) {
		MyLinearMap<K, V> map = chooseMap(key);
		// The key may or may not be in map. If it's there, removing will decrease
		// the map's size; if it's not, it won't. Keeping track of size in the
		// way below allows us to account for this.
		size -= map.size();
		V oldValue = map.remove(key);
		size += map.size();
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}
