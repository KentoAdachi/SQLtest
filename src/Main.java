import java.sql.SQLException;

/**
 * 機能テスト用 最終的にはユーザーインターフェース用のクラスを別に作る
 *
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

		OrderManager manager = new OrderManager(orderTable, itemTable);

		customerTable.register("user1", "aaa");
		itemTable.register("item1", 100, 10);
		customerTable.register("user2", "bbb");
		itemTable.register("item2", 200, 5);

		System.out.println("\n変更前");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();
		System.out.println("");

		manager.order(1, 1, 1);
		manager.order(2, 2, 2);

		System.out.println("\n変更後");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();
		System.out.println("");

		itemTable.deleteAllRecord();
		customerTable.deleteAllRecord();
		orderTable.deleteAllRecord();

		System.out.println("\n消去後");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();
		System.out.println("");
	}

}
