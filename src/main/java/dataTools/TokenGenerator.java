package dataTools;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import it.uniroma1.lcl.babelnet.BabelSynset;
import metaClass.Definition;

public class TokenGenerator {
	/**
	 * Permet a partir d'une liste de definition d'obtenir une map contenant les
	 * mots importants
	 * 
	 * @param listOfSynset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List<LinkedHashMap<String, Integer>> tokeningList(List<List<Definition>> listOfSynset)
			throws UnsupportedEncodingException {
		List<LinkedHashMap<String, Integer>> wordsValuesList = new ArrayList<>();

		for (List<Definition> senses : listOfSynset) {
			wordsValuesList.add(tokening(senses));
		}
		return wordsValuesList;
	}

	// Permet de recuperer une hashMap depuis une liste une liste de BabelSynset
	public static LinkedHashMap<String, Integer> tokening(List<Definition> senses) throws UnsupportedEncodingException {
		LinkedHashMap<String, Integer> aMap = new LinkedHashMap<String, Integer>();

		for (Definition Synset : senses) { // On ajoute chaque elements de la
											// liste a la map du mot.
			// Ajouter le merge.
			aMap.putAll(tokening((LinkedHashMap) aMap, Synset.toString()));
			System.out.println("tokening : " + aMap);
		}
		System.out.println("Final tokening : " + aMap);
		return aMap;

	}

	/**
	 * Permet a partir d'un string de definition d'obtenir une map contenant les
	 * mots importants
	 * 
	 * @param aMap
	 * @param getCategories
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static LinkedHashMap<String, Integer> tokening(LinkedHashMap<String, Integer> aMap, String getCategories)
			throws UnsupportedEncodingException {
		final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
		final Charset UTF_8 = Charset.forName("UTF-8");

		byte ptext[] = getCategories.getBytes(ISO_8859_1);
		getCategories = new String(ptext, UTF_8);

		List<String> wordsTab = TextTools.textSpliter(getCategories);
		System.out.println("GetCategories : " + getCategories);

		for (String word : wordsTab) {
			System.out.println("Mot d'une tab : " + word);
		}

		for (int i = 0; i < wordsTab.size(); i++) {
			if (aMap.containsKey(wordsTab.get(i))) {
				aMap.put(wordsTab.get(i), aMap.get(wordsTab.get(i)) + 1);
				System.out.println("Cl� : " + wordsTab.get(i) + " valeur " + (aMap.get(wordsTab.get(i)) + 1));

			} else {
				aMap.put(wordsTab.get(i), 1);
			}
		}
		System.out.println("Sortie de mapping" + aMap);
		return MapTools.reduction(aMap);
	}

}
