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
	public boolean register(String itemName, int price,int stock) {

		itemName = addQuot(itemName);

		ArrayList<Object> record = new ArrayList<>();

		try {
		record.add(getUniqueItemID());
		record.add(itemName);
		record.add(price);
		record.add(stock);
		insertRecord(record);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		return true;

	}

	private int getUniqueItemID() throws SQLException {
		return getMax("商品ID") + 1;
	}

}
