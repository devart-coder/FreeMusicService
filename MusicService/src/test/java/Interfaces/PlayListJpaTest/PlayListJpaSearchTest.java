package Interfaces.PlayListJpaTest;

public interface PlayListJpaSearchTest {
	//Once
	void findOnceByIdTest()throws Exception;
	void findOnceByUserIdAndNameTest()throws Exception;
	void findOnceByUserIdAndMainTest()throws Exception;
	void findOnceByUserNameAndMainTest()throws Exception;
	void findOnceByAuthAndMainTest()throws Exception;
	void findOnceTests()throws Exception;
	//All
	void findAllByUserIdTest()throws Exception;
	void findAllByUsernameTest()throws Exception;
	void findAllByUserTest()throws Exception;
	void findAllByAuthTest()throws Exception;
	void findAll()throws Exception;
	
	//Throws
//	void findThrowsTest()throws Exception;
	void findByNullArgsWithThrowTest() throws Exception;
//	void findByEmptyNamesWithThrowTest() throws Exception;
}
