package dataTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapTools {

	public static Map<String, Integer> fusion(List<LinkedHashMap<String, Integer>> listOfHash) {
		for (int i = 0; i < listOfHash.size() - 1; i++) {
			System.out.println("Fusion des HASHMAP" + listOfHash.get(i));
			listOfHash.get(i + 1).putAll(fusion(listOfHash.get(i), listOfHash.get(i + 1)));
		}

		return listOfHash.get(listOfHash.size() - 1);
	}

	public static HashMap<String, Integer> fusion(LinkedHashMap<String, Integer> mapAdd,
			LinkedHashMap<String, Integer> mapResult) {

		for (String key : mapAdd.keySet()) {
			if (mapResult.containsKey(key)) {
				mapResult.put(key, (mapAdd.get(key) + mapResult.get(key)) * 2);
			} else {
				mapResult.put(key, mapAdd.get(key));
			}
		}
		return mapResult;
	}

	public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
		List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});

		// LinkedHashMap will keep the keys in the order they are inserted
		// which is currently sorted on natural ordering
		Map<K, V> sortedMap = new LinkedHashMap<K, V>();

		for (Map.Entry<K, V> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static LinkedHashMap<String, Integer> reduction(Map<String, Integer> mapResult) {
		Map<String, Integer> mapTempo = MapTools.sortByValues(mapResult);
		ArrayList<Integer> valList = new ArrayList<>();

		ArrayList<String> wordList = new ArrayList<>(mapTempo.keySet());
		for (String word : wordList) {
			valList.add(mapTempo.get(word));
		}

		System.out.println(valList);
		LinkedHashMap<String, Integer> finalMap = new LinkedHashMap<>();
		int c = 5;
		int c2 = 5;
		if (wordList.size() < 5) {
			c = wordList.size();
		}

		for (int i = 0; i < c; i++) {
			finalMap.put(wordList.get(wordList.size() - i - 1), c2 - i);

			if (i < c - 1) {
				if (valList.get(valList.size() - i - 1) == valList.get(valList.size() - i - 2)) {
					c2++;
				}
			}

		}
		System.out.println("MINI MAP" + finalMap);

		return finalMap;

	}
}
