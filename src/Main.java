import java.sql.SQLException;
import java.util.Random;

/**
 * 管理用
 * というか最初にデータベースを構築する用
 *
 * @author BP16001
 *
 */
public class Main {

	static final int CUSTOMER = 100;
	static final int ITEM = 20;

	/**
	 * メインメソッド
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {

		//変数の初期化
		ItemTable itemTable = new ItemTable();
		CustomerTable customerTable = new CustomerTable();
		OrderTable orderTable = new OrderTable();
		OrderManager orderManager = new OrderManager(orderTable, itemTable, customerTable);

		RecomendManager recomendManager = new RecomendManager(customerTable, itemTable, orderTable);

		//商品と顧客の登録
		registerAll(CUSTOMER, customerTable);
		registerAll(ITEM, itemTable);


		System.out.println("\n変更前");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();
		System.out.println("");

		//注文


		orderAll(CUSTOMER,ITEM, orderManager);


		System.out.println("\n変更後");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();
		System.out.println("");

//		recomendManager.recomend(1);

//		//テーブルの消去
//		itemTable.deleteAllRecord();
//		customerTable.deleteAllRecord();
//		orderTable.deleteAllRecord();

		System.out.println("\n消去後");
		itemTable.showAllRecord();
		customerTable.showAllRecord();
		orderTable.showAllRecord();
		System.out.println("");
	}

	private static void registerAll(int n,CustomerTable customerTable) {
		for(int i = 1; i <= n; i ++) {
			String userName = "user"+i;
			String password = "aaa";
			customerTable.register(userName, password);
		}
	}

	private static void registerAll(int n,ItemTable itemTable) {
		for(int i = 1; i <= n;i++) {
			String itemName = "item" + i;
			int price = 100;
			int stock = 100;
			itemTable.register(itemName, price, stock);
		}
	}

	private static void orderAll(int customer,int item,OrderManager orderManager) throws SQLException {

		Random random = new Random();
		for(int i = 1; i <= customer;i++) {
			int customerID = i;
			int n = random.nextInt(9);
			for(int j = 1;j <= 1+n;j++) {
				int itemID = random.nextInt(item)+1;
				int quantity = 1;
				orderManager.order(customerID, itemID, quantity);
			}

		}

	}

}
