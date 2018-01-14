package release;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Random;

/**
 * 注文機能デモ用のクライアント
 * @author BP16001
 *
 */
public class StateMachine {

	private static final int ITEM_DEFAULT = 20;
	private static final int CUSTOMER_DEFAULT = 100;
	private static CustomerTable customerTable_;
	private static ItemTable itemTable_;
	private static OrderTable orderTable_;
	private static OrderManager orderManager_;
	private static RecomendManager recomendManager_;
	private static BufferedReader reader_;
	private static int customerID_;

	/**
	 * メインメソッド
	 * @param args
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, NumberFormatException, SQLException {
		init();
		rootState();
	}
	private static void init() throws SQLException, NumberFormatException, IOException {

		customerTable_ = new CustomerTable();
		itemTable_ = new ItemTable();
		orderTable_ = new OrderTable();
		orderManager_ = new OrderManager(orderTable_, itemTable_, customerTable_);
		recomendManager_ = new RecomendManager(customerTable_, itemTable_, orderTable_);
		reader_= new BufferedReader(new InputStreamReader(System.in));

	}
	private static String readLine() throws IOException {
		System.out.print("<<");
		return reader_.readLine();
	}
	private static void rootState() throws IOException, NumberFormatException, SQLException {
		System.out.println("\nこんにちは\n login\n register\n [status]\n [setup]");
		String option = readLine();
		switch (option) {
		case "login":
			loginState();
			break;
		case "register":
			registerState();
			break;
		case "status":
			statusState();
			rootState();
			break;
		case "setup":
			setupState();
			rootState();
			break;
		default:
			System.out.println("exit");
			break;
		}

	}
	private static void setupState() throws SQLException {
		itemTable_.deleteAllRecord();
		customerTable_.deleteAllRecord();
		orderTable_.deleteAllRecord();
		registerAll(CUSTOMER_DEFAULT, customerTable_);
		registerAll(ITEM_DEFAULT, itemTable_);
		orderAll(CUSTOMER_DEFAULT,20, orderManager_);
		System.out.println("SETUP_COMPLETED");
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

	private static void statusState() throws SQLException{
		System.out.println("\n顧客表");
		customerTable_.showAllRecord();
		System.out.println("\n商品表");
		itemTable_.showAllRecord();
		System.out.println("\n注文表");
		orderTable_.showAllRecord();

	}
	private static void registerState() throws IOException {
		System.out.println("氏名を入力してください");
		String userName = readLine();
		System.out.println("パスワードを入力してください");
		String password = readLine();
		int customerID = customerTable_.register(userName, password);
		if (customerID == 0) {
			System.out.println("登録に失敗しました");
			return;
		}
		System.out.println("あなたの顧客IDは "+customerID+" です");
	}

	private static void loginState() throws IOException, NumberFormatException, SQLException{

		System.out.println("顧客IDを入力してください");
		int customerID = Integer.parseInt(readLine());
		System.out.println("パスワードを入力してください");
		String password = readLine();

		if(!orderManager_.login(customerID, password)) {
			System.out.println("パスワードが違います");
			return;
		}
		customerID_ = customerID;
		System.out.println("ようこそ "+customerTable_.getUserName(customerID_));
		System.out.println("何をしますか\n recomend\n order");
		String option = readLine();
		switch (option) {
		case "recomend":
			recomendState();
			break;
		case "order":
			orderState();
			break;
		default:
			System.out.println("終了します");
			break;
		}

	}

	private static void recomendState() throws SQLException {

		recomendManager_.recomend(customerID_);
	}
	private static void orderState() throws IOException, SQLException {
		System.out.println("購入する商品を入力してください");
		int itemID = Integer.parseInt(readLine());
		System.out.println("購入数量を入力してください");
		int quantity = Integer.parseInt(readLine());
		if( orderManager_.order(customerID_, itemID, quantity)) {
			System.out.println("成功");
		}

	}
}
