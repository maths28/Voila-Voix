package dataTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextTools {

	public static List<String> textSpliter(String aText) {
		// mots pour le moment inutiles pour l'analyse des donn�es
		String[] stopWords = { "  alors  ", "  au  ", " de ", " des ", " aucuns ", " aussi ", " autre ", " avant ",
				" avec ", " avoir ", " bon ", " car ", " ce ", " cela ", " ces ", " ceux ", " chaque ", " ci ",
				" comme ", " comment ", " dans ", " des ", " du ", " dedans ", " dehors ", " depuis ", " devrait ",
				" doit ", " donc ", " dos ", " d�but ", " elle ", " elles ", " en ", " encore ", " essai ", " est ",
				" et ", " eu ", " fait ", " faites ", " fois ", " font ", " hors ", " ici ", " il ", " ils ", " je ",
				" juste ", " la ", " le ", " les ", " leur ", " l� ", " ma ", " maintenant ", " mais ", " mes ",
				" mine ", " moins ", " mon ", " mot ", " m�me ", " ni ", " nomm�s ", " notre ", " nous ", " ou ",
				" o� ", " par ", " parce ", " pas ", " peut ", " peu ", " plupart ", " pour ", " pourquoi ", " quand ",
				" que ", " quel ", " quelle ", " quelles ", " quels ", " qui ", " sa ", " sans ", " ses ",
				" seulement ", " si ", " sien ", " son ", " sont ", " sous ", " soyez ", " sujet ", " sur ", " ta ",
				" tandis ", " tellement ", " tels ", " tes ", " ton ", " tous ", " tout ", " trop ", " tr�s ", " tu ",
				" voient ", " vont ", " ainsi ", " les ", " on ", " ont ", " votre ", " vous ", " vu ", " �a ",
				" �taient ", " �tat ", " �tions ", " �t� ", " �tre " };
		String[] ponctuation = { ",", ";", ".", "?", "!", "...", "bncat:fr:" };
		String[] ponctuationWTSpace = { "(", ")", "[", "]", "{", "}", "_" };
		aText = aText.toLowerCase();
		aText = " " + aText;

		for (int i = 0; i < ponctuationWTSpace.length; i++) {
			aText = aText.replace(ponctuationWTSpace[i], " ");
		}
		System.out.println(aText);

		// Permet d'eviter d'avoir des mots comme jaime.
		aText = aText.replace("'", " ");
		System.out.println(aText);

		for (int i = 0; i < ponctuation.length; i++) {
			aText = aText.replace(ponctuation[i], "");
		}

		// Permet d'eviter d'avoir des mots comme jaime.
		aText = aText.replace("'", " ");
		System.out.println(aText);

		for (int i = 0; i < stopWords.length; i++) {
			aText = aText.replace(stopWords[i], " ");
		}
		aText = aText.replace("  ", " ");

		List<String> wordsTab = new ArrayList<String>(Arrays.asList(aText.split(" ")));

		// On enleves les lettres seuls ou les cases vides.
		for (int i = 0; i < wordsTab.size(); i++) {
			if (wordsTab.get(i).length() <= 1) {
				wordsTab.remove(i);
			}
			if (wordsTab.get(i).length() <= 1) {
				wordsTab.remove(i);
			}

		}
		System.out.println(wordsTab);
		return wordsTab;
	}

}
