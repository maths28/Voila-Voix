package babelTools;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.jlt.util.Language;
import metaClass.Word;

@Component
@Transactional
public class MyService {
	@Autowired
	private static BabelRepository repo = new BabelRepository() {
		
		@Override
		public Word save(Word entity) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Word findOne(Integer id) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean exists(Integer id) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void deleteAll() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void delete(Iterable<? extends Word> entities) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void delete(Word entity) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void delete(Integer id) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public long count() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public Page<Word> findAll(Pageable pageable) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Word saveAndFlush(Word entity) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Word> save(Iterable<? extends Word> entities) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void flush() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public List<Word> findAll(Sort sort) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Word> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void deleteInBatch(Iterable<Word> entities) {
			// TODO Auto-generated method stub
			
		}
		
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
			
			System.out.println("Objet crée NET"  + word.getSynsetList());
			return word;
		} else {
			System.out.println("Object in BDD");
			return null;

		}

	}

}
