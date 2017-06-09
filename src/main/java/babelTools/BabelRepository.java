package babelTools;

import org.springframework.data.jpa.repository.JpaRepository;

import metaClass.Word;

public interface BabelRepository extends JpaRepository<Word, Integer> {

	Word findOneByName(String word);

}
