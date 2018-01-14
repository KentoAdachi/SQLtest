package release;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 注文表への操作をまとめたクラス
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
			ArrayList<Object> record = new ArrayList<>();
			record.add(getUniqueOrderNumber());
			record.add(customerID);
			record.add(itemID);
			record.add(quantity);
			insertRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private int getUniqueOrderNumber() throws SQLException {
		return getMax("注文番号") + 1;
	}

	/**
	 * 注文蛮行から顧客IDを取得する
	 * @param orderNumber
	 * @return
	 * @throws SQLException
	 */
	public int getCustomerID(int orderNumber) throws SQLException {
		//			ResultSet resultSet = executeQuery("select 顧客ID from 注文表 where 注文番号=" + orderNumber);
		ResultSet resultSet = selectColumn(columns_.get(1), orderNumber);
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt(1);
	}

	/**
	 * 注文蛮行から商品IDを取得する
	 * @param orderNumber
	 * @return
	 * @throws SQLException
	 */
	public int getItemID(int orderNumber) throws SQLException {
		//		ResultSet resultSet = executeQuery("select 商品ID from 注文表 where 注文番号=" + orderNumber);
		ResultSet resultSet = selectColumn(columns_.get(2), orderNumber);
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt(1);
	}

	/**
	 * 注文蛮行から購入数量を取得する
	 * @param orderNumber
	 * @return
	 * @throws SQLException
	 */
	public int getQuantity(int orderNumber) throws SQLException {
		//		ResultSet resultSet = executeQuery("select 購入数量 from 注文表 where 注文番号=" + orderNumber);
		ResultSet resultSet = selectColumn(columns_.get(3), orderNumber);
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt(1);
	}

}
