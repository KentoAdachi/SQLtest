package release;
import java.sql.ResultSet;
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
	public int register(String userName, String password) {

		userName = addQuot(userName);
		password = addQuot(password);

		ArrayList<Object> record = new ArrayList<>();
		int customerID;
		try {
			customerID = getUniqueCustomerID();
			record.add(customerID);
			record.add(userName);
			record.add(password);
			insertRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return customerID;
	}

	private int getUniqueCustomerID() throws SQLException {
		return getMax(columns_.get(0)) + 1;
	}

	/**
	 * 顧客IDから氏名を取得
	 * @param customerID
	 * @return
	 * @throws SQLException
	 */
	public String getUserName(int customerID) throws SQLException {
		//		ResultSet resultSet = executeQuery("select 氏名 from 顧客 where 顧客ID=" + userID);
		ResultSet resultSet = selectColumn(columns_.get(1), customerID);
		if (!resultSet.next()) {
			return null;
		}
		return resultSet.getString(1);

	}

	/**
	 * 顧客IDからパスワードを取得
	 * @param customerID
	 * @return
	 * @throws SQLException
	 */
	public String getPassword(int customerID) throws SQLException {
		//		ResultSet resultSet = executeQuery("select パスワード from 顧客 where 顧客ID=" + userID);
		ResultSet resultSet = selectColumn(columns_.get(2), customerID);
		if (!resultSet.next()) {
			return null;
		}

		return resultSet.getString(1);

	}

}
