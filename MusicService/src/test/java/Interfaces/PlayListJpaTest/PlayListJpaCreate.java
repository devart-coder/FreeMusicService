package Interfaces.PlayListJpaTest;

public interface PlayListJpaCreate {
	public void saveSupplierTest() throws Exception ;
	public void saveSupplierWithNotSavedExceptionTest() throws Exception ;
	public void saveSupplierWithNullArgExceptionTest() throws Exception ;

	public void saveIterableTest() throws Exception ;
	public void saveIterableWithNotSavedExceptionTest() throws Exception ;
	public void saveIterableWithNullArgExceptionTest() throws Exception ;
	
	public void saveEntityTest() throws Exception ;
	public void saveEntityWithNotSavedExceptionTest() throws Exception ;
	public void saveEntityWithNullArgExceptionTest() throws Exception ;
}
