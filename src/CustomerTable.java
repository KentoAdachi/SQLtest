import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 顧客表への操作をまとめたクラス
 *
 * @author BP16001
 *
 */
public class CustomerTable extends Table {

	/**
	 * コンストラクタ
	 *
	 * @throws SQLException
	 */
	public CustomerTable() throws SQLException {
		super("顧客表", null);
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("顧客ID");
		columns.add("氏名");
		columns.add("パスワード");
		columns_ = columns;

	}

	/**
	 * 会員登録
	 *
	 * @param userName
	 *            ユーザー名
	 * @param password
	 *            パスワード
	 * @return 登録が成功したかどうか
	 * @throws SQLException
	 */
	public boolean register(String userName, String password) {

		userName = addQuot(userName);
		password = addQuot(password);

		ArrayList<Object> record = new ArrayList<>();
		try {
			record.add(getUniqueCustomerID());
			record.add(userName);
			record.add(password);
			insertRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private int getUniqueCustomerID() throws SQLException {
		return getMax("顧客ID") + 1;
	}

}
