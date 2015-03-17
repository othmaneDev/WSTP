package stage.wstp.others;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for {@link VerificationSyntaxe}.
 *
 * @author Emmanuel ELISA
 */
public class VerificationSyntaxeTest {
	
	@Test
    public void TestverificationTagWithPoidsAndMeta() {
		
		String successed = "[Test]NomWeb_Service:100 NomWeb_Service:20 [Test3]NomWebService:10";
		assertTrue("failure - shouldn't be false numero 1", VerificationSyntaxe.verificationTagWithPoidsAndMeta(successed));

		String successed1 = "NomWeb_Service:100 NomWeb_Service:20 NomWebService:10";
		assertTrue("failure - shouldn't be false numero 2", VerificationSyntaxe.verificationTagWithPoidsAndMeta(successed1));
		
		String successed2 = "NomWeb_Service:100";
		assertTrue("failure - shouldn't be false numero 3", VerificationSyntaxe.verificationTagWithPoidsAndMeta(successed2));

		
		String failed = "[Test]NomWeb_Service:100 NomWeb_Service: [Test3]NomWebService:10";
		assertFalse("failure - should be false numero 4", VerificationSyntaxe.verificationTagWithPoidsAndMeta(failed));
   
		String failed1 = "";
		assertFalse("failure - should be false numero 5", VerificationSyntaxe.verificationTagWithPoidsAndMeta(failed1));
		
		String failed2 = "[Test]NomWeb_Service:100 NomWeb_Service2:10 [Test3]NomWebService:101";
		assertFalse("failure - should be false numero 6", VerificationSyntaxe.verificationTagWithPoidsAndMeta(failed2));

	}
	
	@Test
    public void TestverificationTagWithPoids() {
		
		String successed = "NomWeb_Service:100 NomWeb_Service:20 NomWebService:10";
		assertTrue("failure - shouldn't be false numero 1", VerificationSyntaxe.verificationTagWithPoids(successed));

		String successed1 = "NomWeb_Service:100";
		assertTrue("failure - shouldn't be false numero 3", VerificationSyntaxe.verificationTagWithPoids(successed1));

		
		String failed = "[Test]NomWeb_Service:100 NomWeb_Service:20 [Test3]NomWebService:10";
		assertFalse("failure - should be false numero 4", VerificationSyntaxe.verificationTagWithPoids(failed));
   
		String failed1 = "";
		assertFalse("failure - should be false numero 5", VerificationSyntaxe.verificationTagWithPoids(failed1));
		
		String failed2 = "NomWeb_Service:100 NomWeb_Service2:10 NomWebService:101";
		assertFalse("failure - should be false numero 6", VerificationSyntaxe.verificationTagWithPoids(failed2));
	}
}
