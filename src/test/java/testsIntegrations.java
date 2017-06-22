
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

public class testsIntegrations {

	@Test
	public void testBouchonné() throws IOException {


		BabelNet bn = BabelNet.getInstance();

		String theTxt = "Les ordinateurs ont des GPU des CPU ainsi que des périphériques "; //Le texte a analyser, phrase possible, mais moyen tant que textTools n'a pas corriger la section avec ecrit //PARTIE A CORRIGER ITERATOR
		List<String> wordList = TextTools.textSpliter(theTxt); //On le decoupe
		System.out.println("TEXTE SPLITE" + wordList + "TEXTE SPLITE");
		
		List<List<Definition>> listOfSynset = BabelSynsets.synsetCreator(bn, wordList); //On recupere une list des synset des mots importants
		System.out.println("LISTE OF SYNSET " + listOfSynset +"LISTE OF SYNSET");
		
		//On recupere une list listant les valeurs de chaque elements definissant chaque mot du texte.
		System.out.println("GENERATION DE TOKEN");
		List<LinkedHashMap<String, Integer>> listOfHasMap = TokenGenerator.tokeningList(listOfSynset);
		System.out.println(listOfHasMap);
//		
		Map<String, Integer> linkedMap = MapTools.fusion(listOfHasMap);
		Assert.assertEquals("{processeur=5, ordinateur=4, graphique=4, informatique=4, p�riph�rique=4}",MapTools.reduction(linkedMap).toString());

		
	}

}
