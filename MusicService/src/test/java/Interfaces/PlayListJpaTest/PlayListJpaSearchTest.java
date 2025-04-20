package Interfaces.PlayListJpaTest;

public interface PlayListJpaSearchTest {
	//Once
	void findOnceByIdTest()throws Exception;
	void findOnceByUserIdAndNameTest()throws Exception;
	void findOnceByUserIdAndMainTest()throws Exception;
	void findOnceByUserNameAndMainTest()throws Exception;
	void findOnceByAuthAndMainTest()throws Exception;
	//All
	void findAllByUserIdTest()throws Exception;
	void findAllByUsernameTest()throws Exception;
	void findAllByUserTest()throws Exception;
	void findAllByAuthTest()throws Exception;
	void findAllTests()throws Exception;
	
	//Throws
		//Once
	void findOnceByIdWithThrowsTest()throws Exception;
	void findOnceByUserIdAndNameWithThrowsTest()throws Exception;
	void findOnceByUserIdAndMainWithThrowsTest()throws Exception;
	void findOnceByUserNameAndMainWithThrowsTest()throws Exception;
	void findOnceByAuthAndMainWithThrowsTest()throws Exception;
		//All
	void findAllByUserIdWithThrowsTest()throws Exception;
	void findAllByUsernameWithThrowsTest()throws Exception;
	void findAllByUserWithThrowsTest()throws Exception;
	void findAllByAuthWithThrowsTest()throws Exception;
	void findAllWithThrowsTests()throws Exception;
}
