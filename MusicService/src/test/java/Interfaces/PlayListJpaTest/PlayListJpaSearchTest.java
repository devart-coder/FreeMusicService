package Interfaces.PlayListJpaTest;

public interface PlayListJpaSearchTest {
	void findOnceByIdTest()throws Exception;
	void findOnceByUserIdAndNameTest()throws Exception;
	void findOnceByUserIdAndMainTest()throws Exception;
	void findOnceByUserNameAndMainTest()throws Exception;
	void findOnceByAuthAndMainTest()throws Exception;
	void findOnceTests()throws Exception;
	
	void findAllByUserIdTest()throws Exception;
	void findAllByUserNameTest()throws Exception;
	void findAllByUserTest()throws Exception;
	void findAllByAuthTest()throws Exception;
	
	void findAll()throws Exception;
//	void findThrowsTest()throws Exception;
//	void findByNullArgsWithThrowTest() throws Exception;
}
