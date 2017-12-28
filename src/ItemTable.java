import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 商品表への操作をまとめたクラス
 *
 * @author BP16001
 *
 */
public class ItemTable extends Table {

	/**
	 * コンストラクタ
	 *
	 * @throws SQLException
	 */
	public ItemTable() throws SQLException {
		super("商品表", null);
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("商品ID");
		columns.add("商品名");
		columns.add("値段");
		columns.add("在庫数");
		columns_ = columns;

	}

	/**
	 * 商品の登録
	 *
	 * @param itemName
	 *            商品名
	 * @param price
	 *            値段
	 * @param stock
	 *            在庫数
	 * @return 登録が成功したかどうか
	 * @throws SQLException
	 */
	public boolean register(String itemName, int price, int stock) {

		itemName = addQuot(itemName);

		ArrayList<Object> record = new ArrayList<>();

		try {
			record.add(getUniqueItemID());
			record.add(itemName);
			record.add(price);
			record.add(stock);
			insertRecord(record);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		return true;

	}

	private int getUniqueItemID() throws SQLException {
		return getMax("商品ID") + 1;
	}

	/**
	 * 在庫を更新する
	 *
	 * @param itemID
	 * @param quantity
	 * @return
	 * @throws SQLException
	 */
	public boolean setStock(int itemID, int quantity) throws SQLException {
		executeQuery("update 商品表 set 在庫数=" + quantity + " where 商品ID=" + itemID);
		return false;
	}

	/**
	 * 商品IDから商品名を取得
	 * @param itemID
	 * @return
	 * @throws SQLException
	 */
	public String getItemName(int itemID) throws SQLException {
		//			ResultSet resultSet = executeQuery("select 商品名 from 商品表 where 商品ID=" + itemID);
		ResultSet resultSet = selectColumn(columns_.get(1), itemID);
		if (!resultSet.next()) {
			return null;
		}
		return resultSet.getString(1);

	}

	/**
	 * 商品IDから値段を取得
	 * @param itemID
	 * @return
	 * @throws SQLException
	 */
	public int getPrice(int itemID) throws SQLException {
		//			ResultSet resultSet = executeQuery("select 値段 from 商品表 where 商品ID=" + itemID);
		ResultSet resultSet = selectColumn(columns_.get(2), itemID);
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt(1);
	}

	/**
	 * 商品IDから在庫数を取得
	 * @param itemID
	 * @return
	 * @throws SQLException
	 */
	public int getStock(int itemID) throws SQLException {
		//		ResultSet resultSet = executeQuery("select 在庫数 from 商品表 where 商品ID=" + itemID);
		ResultSet resultSet = selectColumn(columns_.get(3), itemID);
		if (!resultSet.next()) {
			return -1;
		}
		return resultSet.getInt(1);
	}

}
