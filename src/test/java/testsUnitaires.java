
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


				String theTxt = "Les ordinateurs ont des GPU des CPU ainsi que des périphériques "; //Le texte a analyser, phrase possible, mais moyen tant que textTools n'a pas corriger la section avec ecrit //PARTIE A CORRIGER ITERATOR
				List<String> wordList = TextTools.textSpliter(theTxt); //On le decoupe
				System.out.println("TEXTE SPLITE" + wordList + "TEXTE SPLITE");
				
				List<List<Definition>> listOfSynset = BabelSynsetsMoc.getSynsets(); //Bouchon
				System.out.println("LISTE OF SYNSET " + listOfSynset +"LISTE OF SYNSET");
				
				//On recupere une list listant les valeurs de chaque elements definissant chaque mot du texte.
				System.out.println("GENERATION DE TOKEN");
				List<LinkedHashMap<String, Integer>> listOfHasMap = TokenGenerator.tokeningList(listOfSynset);
				System.out.println(listOfHasMap);
//				
				Map<String, Integer> linkedMap = MapTools.fusion(listOfHasMap);
				System.out.println(MapTools.sortByValues(MapTools.fusion(listOfHasMap)));
				System.out.println("FINAL : " + MapTools.reduction(linkedMap));
				Assert.assertEquals("{processeur=5, graphique=4, universit�=3, pr�sidents=3, conf�rence=3}",MapTools.reduction(linkedMap).toString());
		
	}

}
