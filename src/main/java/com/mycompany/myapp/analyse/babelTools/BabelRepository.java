package com.mycompany.myapp.analyse.babelTools;

import com.mycompany.myapp.analyse.metaClass.Word;


public interface BabelRepository  {

	Word findOneByName(String word);

}
