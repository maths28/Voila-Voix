import java.util.ArrayList;
import java.util.List;

import metaClass.Definition;

public class BabelSynsetsMoc {

	public static List<List<Definition>>  getSynsets() {
		List<Definition> tabCpu = new ArrayList<>();
		tabCpu.add(new Definition("processeur"));

		List<Definition> tabGpu = new ArrayList<>();
		tabGpu.add(new Definition("processeur_graphique"));
		tabGpu.add(new Definition("Conférence_des_présidents_d'université"));

		List<Definition> tabDevices = new ArrayList<>();
		tabDevices.add(new Definition("périphérique_informatique"));
		tabDevices.add(new Definition("Périphériques_(recueil)"));

		List<List<Definition>> listSynset = new ArrayList<>();
		listSynset.add(tabCpu);
		listSynset.add(tabGpu);
		listSynset.add(tabDevices);


		return listSynset;
	}

}
