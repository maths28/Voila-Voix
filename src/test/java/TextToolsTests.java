
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import dataTools.TextTools;

public class TextToolsTests {

	@Test
	public void test() {
		List<String> temoins = new ArrayList<String>();
		temoins.add("aime");
		temoins.add("ordinateurs");
		temoins.add("GPU");
		Assert.assertEquals(temoins, TextTools.textSpliter("J'aime... aucuns les ordinateurs et les, GPU."));

	}
}
