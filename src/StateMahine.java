import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * 注文機能デモ用のクライアント
 * @author BP16001
 *
 */
public class StateMahine {

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
		System.out.println("こんにちは\n login\n register\n status");
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
			break;
		default:
			System.out.println("exit");
			break;
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
