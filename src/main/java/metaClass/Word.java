package metaClass;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import it.uniroma1.lcl.babelnet.BabelSynset;

@Entity(name = "objSynset")
public class Word {
	@Column(name = "word")
	private String word;

	@Column(name = "definition")
	private List<Definition> sysnet;

	public Word(String aWord, List<BabelSynset> aSynset) {
		this.sysnet = new ArrayList<>();
		for (BabelSynset a : aSynset) {
			Definition def = new Definition(a.toString());
			this.sysnet.add(def);
			System.out.println("LELELELLELEL  " + a.toString());

		}
		this.word = aWord;
		System.out.println("Name is : " + this.word);
	}

	public List<Definition> getSynsetList() {
		return this.sysnet;

	}
}
