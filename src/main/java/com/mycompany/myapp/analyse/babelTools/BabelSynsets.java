package com.mycompany.myapp.analyse.babelTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.myapp.analyse.metaClass.Definition;
import com.mycompany.myapp.analyse.metaClass.Word;
import org.springframework.stereotype.Component;

import it.uniroma1.lcl.babelnet.BabelNet;

@Component
public class BabelSynsets {

	public static List<Definition> synsetCreator(BabelNet bn, String aWord) throws IOException {
		Word senses = MyService.saveSynset(aWord, bn);

		return senses.getSynsetList();

	}

	public static List<List<Definition>> synsetCreator(BabelNet bn, List<String> aList) throws IOException {
		List<List<Definition>> listSynset = new ArrayList<>();
		for (String aWord : aList) {
			listSynset.add(synsetCreator(bn, aWord));
		}
		return listSynset;

	}

}
