package stage.wstp.others;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Tests for {@link WordNetTool}.
 *
 * @author Emmanuel ELISA
 */
public class WordNetToolTest {

	@Test
	public void TestgetSynonymsTags(){
		String word = "picture";
		String[] synonyms = {"picture", "image", "icon", "ikon", "painting"};
		List<String> synonymsOfPicture =  Arrays.asList(synonyms);
		assertTrue("failure - picture's synonyms are picture, image, icon, ikon, painting ", WordNetTool.getInstance().getSynonymsTags(word).containsAll(synonymsOfPicture));
	}
}
