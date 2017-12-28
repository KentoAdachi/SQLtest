import java.sql.SQLException;

/**
 * ログイン処理を行うクラス
 * @author BP16001
 *
 */
public class loginManager {

	CustomerTable customerTable_;

	/**
	 * コンストラクタ
	 * @param customerTable
	 */
	public loginManager(CustomerTable customerTable) {
		customerTable_ = customerTable;
	}

	/**
	 * ログイン処理 passwordとcustomerIDに紐づけられたpasswordが一致した場合true
	 *
	 * @param customerID
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean login(int customerID, String password) throws SQLException {
		if (customerTable_.getPassword(customerID).equals(password)) {
			System.out.println("logined!");
			return true;
		}
		System.out.println("password not correct");
		return false;
	}

}
