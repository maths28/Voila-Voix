package com.mycompany.myapp.analyse.babelTools;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import com.mycompany.myapp.analyse.metaClass.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.jlt.util.Language;

@Component
@Transactional
public class MyService {
	@Autowired
	private static BabelRepository repo = new BabelRepository() {

		@Override
		public Word findOneByName(String word) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public static Word saveSynset(String aWord, BabelNet bn) throws IOException {

		Word found = repo.findOneByName(aWord);
		if (found == null) {
			Word word = new Word(aWord, bn.getSynsets(aWord, Language.FR));
			System.out.println("Objet crée NET");
			return word;
		} else {
			System.out.println("Object in BDD");
			return null;

		}

	}

}
