import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 注文表
 *
 * @author BP16001
 *
 */
public class OrderTable extends Table {

	/**
	 * コンストラクタ
	 *
	 * @throws SQLException
	 */
	public OrderTable() throws SQLException {
		super("注文表", null);
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("注文番号");
		columns.add("顧客ID");
		columns.add("商品ID");
		columns.add("購入数量");
		columns_ = columns;

	}

	/**
	 * レコードを追加する
	 *
	 * @param customerID
	 * @param itemID
	 * @param quantity
	 *
	 * @return 追加に成功したかどうか
	 * @throws SQLException
	 */
	public boolean register(int customerID, int itemID, int quantity) {

		try {
		//在庫数の確認
		if (!checkStock(itemID, quantity)) {
			return false;
		}

		//料金の表示
		System.out.println("料金は "+getPrice(itemID) * quantity+" 円");

		ArrayList<Object> record = new ArrayList<>();
		record.add(getUniqueOrderNumber());
		record.add(customerID);
		record.add(itemID);
		record.add(quantity);
		insertRecord(record);
		//ここから

		//商品表の在庫の更新
		int stock = getStock(itemID);
		executeQuery("update 商品表 set 在庫数="+(stock-1)+" where 商品ID="+itemID);

		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private int getUniqueOrderNumber() throws SQLException {
		return getMax("注文番号") + 1;
	}

	private boolean checkStock(int itemID, int quantity) throws SQLException {
		//在庫が存在するか
		int stock = getStock(itemID);
		if (stock - quantity <0) {
			System.out.println("商品が存在しないか在庫が不足しています");
			return false;
		}

		return true;
	}

	private int getStock(int itemID) throws SQLException {
		ResultSet resultSet = executeQuery("select 在庫数 from 商品表 where 商品ID="+itemID);
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt(1);
	}

	private int getPrice(int itemID) throws SQLException {
		ResultSet resultSet = executeQuery("select 値段 from 商品表 where 商品ID=" + itemID);
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt(1);
	}

}
