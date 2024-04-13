package common;

public interface OrderTable<T> {
	@SuppressWarnings("unchecked")
	void showOrderInTable(T... param);
	void clearTable();

}
