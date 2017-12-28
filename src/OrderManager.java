import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 注文処理を行うクラス
 * @author BP16001
 *
 */
public class OrderManager {

	OrderTable orderTable_;
	ItemTable itemTable_;

	/**
	 * コンストラクタ
	 * @param orderTable
	 * @param itemTable
	 */
	public OrderManager(OrderTable orderTable, ItemTable itemTable) {
		orderTable_ = orderTable;
		itemTable_ = itemTable;
	}

	/**
	 * 注文処理 注文表にレコードを追加し商品表の在庫を更新する
	 *
	 * @param customerID
	 * @param itemID
	 * @param quantity
	 *
	 * @return 追加に成功したかどうか
	 * @throws SQLException
	 */
	public boolean order(int customerID, int itemID, int quantity) throws SQLException {

		//在庫数の確認
		if (!checkStock(itemID, quantity)) {
			return false;
		}
		//price
		System.out.println("確認 : 料金は " + itemTable_.getPrice(itemID) * quantity + " 円");

		ArrayList<Object> record = new ArrayList<>();
		record.add(customerID);
		record.add(itemID);
		record.add(quantity);
		orderTable_.register(customerID, itemID, quantity);

		//商品表の在庫の更新
		int stock = itemTable_.getStock(itemID);
		itemTable_.setStock(itemID, stock - quantity);

		return true;
	}

	/**
	 * 在庫数を調べる
	 * @param itemID
	 * @param quantity
	 * @return
	 * @throws SQLException
	 */
	private boolean checkStock(int itemID, int quantity) throws SQLException {
		//在庫が存在するか
		int stock = itemTable_.getStock(itemID);
		if (stock - quantity < 0) {
			System.out.println("商品が存在しないか在庫が不足しています");
			return false;
		}

		return true;
	}
}
