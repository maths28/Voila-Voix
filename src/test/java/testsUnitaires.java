
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import babelTools.BabelSynsets;
import dataTools.MapTools;
import dataTools.TextTools;
import dataTools.TokenGenerator;
import it.uniroma1.lcl.babelnet.BabelNet;
import metaClass.Definition;

public class testsUnitaires {

	@Test
	public void test() {
		List<String> temoins = new ArrayList<String>();
		temoins.add("aime");
		temoins.add("ordinateurs");
		temoins.add("gpu");
		Assert.assertEquals(temoins, TextTools.textSpliter("J'aime... aucuns les ordinateurs et les, GPU."));
	}

	@Test
	public void testBouchonné() throws IOException {

		String theTxt = "Les ordinateurs ont des GPU des CPU ainsi que des périphériques "; 
		List<String> wordList = TextTools.textSpliter(theTxt); // On le decoupe
		System.out.println("TEXTE SPLITE" + wordList + "TEXTE SPLITE");

		List<List<Definition>> listOfSynset = BabelSynsetsMoc.getSynsets(); // Bouchon
		System.out.println("LISTE OF SYNSET " + listOfSynset + "LISTE OF SYNSET");

		// On recupere une list listant les valeurs de chaque elements
		// definissant chaque mot du texte.
		System.out.println("GENERATION DE TOKEN");
		List<LinkedHashMap<String, Integer>> listOfHasMap = TokenGenerator.tokeningList(listOfSynset);
		System.out.println("test" + listOfHasMap);

		Assert.assertEquals(
				"[{processeur=5}, {processeur=5, graphique=5, conf�rence=4, pr�sidents=4, universit�=4}, {p�riph�rique=5, informatique=5, p�riph�riques=4, recueil=4}]",
				listOfHasMap.toString());

	}

}
