package Interfaces.PlayListJpaTest;

public interface PlayListJpaCreate {
	//Supplier
	public void saveSupplierTest() throws Exception ;
	public void saveSupplierWithNotSavedExceptionTest() throws Exception ;
	public void saveSupplierWithNullArgExceptionTest() throws Exception ;
	public void saveSupplierWithDuplicationTest() throws Exception ;
	//Iterable
	public void saveIterableTest() throws Exception ;
	public void saveIterableWithNotSavedExceptionTest() throws Exception ;
	public void saveIterableWithNullArgExceptionTest() throws Exception ;
	public void saveIterableWithDuplicationTest() throws Exception ;
	//Entity
	public void saveEntityTest() throws Exception ;
	public void saveEntityWithNotSavedExceptionTest() throws Exception ;
	public void saveEntityWithNullArgExceptionTest() throws Exception ;
	public void saveEntityWithDuplicationTest() throws Exception ;
}
