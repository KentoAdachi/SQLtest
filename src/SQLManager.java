import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLを実行するためのクラス
 *
 * @author BP16001
 */
public class SQLManager {

	//////////////
	//フィールド//
	//////////////
	private Statement currentStatement_;

	/**
	 * デフォルトコンストラクタ
	 *
	 * @throws SQLException
	 */
	public SQLManager() throws SQLException {
		// TODO 自動生成されたコンストラクター・スタブ
		currentStatement_ = getConnection().createStatement();
	}

	/**
	 * コネクションの取得例外処理付き
	 *
	 * @return コネクション
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException {
		// DB接続オブジェクトの作成
		//（引数は、データベースのURLとシステム名、ユーザ名、パスワード)
		// HSQLDBの場合、ユーザ名：sa、パスワード：なしのデフォルト設定がある
		return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
	}

	/**
	 * SQL文を実行する
	 *
	 * @param sql
	 * @return 実行結果
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		return currentStatement_.executeQuery(sql);
	}

	/**
	 * コネクションを閉じる
	 *
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		currentStatement_.close();
	}

}
