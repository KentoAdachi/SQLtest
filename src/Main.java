import java.sql.SQLException;

/**
 * @author BP16001
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {

		ItemTable itemTable = new ItemTable();
		CustomerTable customerTable = new CustomerTable();
		OrderTable orderTable = new OrderTable();

		System.out.println("変更前");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();


		customerTable.register("user1", "aaa");
		itemTable.register("item1", 100,10);
		orderTable.register(1, 1, 1);



		System.out.println("変更後");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();


		itemTable.deleteAllRecord();
		customerTable.deleteAllRecord();
		orderTable.deleteAllRecord();

		System.out.println("消去後");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();
	}



}
