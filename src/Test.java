// Test1.java
// Javaアプリケーションから、HSQLDB上のテーブルuserinfoに
// アクセスする

// DB接続関係のAPIをインポート
// 下記5行の代わりに、import java.sql.*;としてもよい
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public static Connection getConnection() {
		try {
			// JDBCドライバのロード（JDK1.5以降では不要）
			Class.forName("org.hsqldb.jdbcDriver");

			// DB接続オブジェクトの作成
			//（引数は、データベースのURLとシステム名、ユーザ名、パスワード)
			// HSQLDBの場合、ユーザ名：sa、パスワード：なしのデフォルト設定がある
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");

			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static ResultSet executeQuery(String sql) throws SQLException {
		Connection con = getConnection();
		// ステートメントオブジェクト（SQLコンテナ）の作成
		Statement smt = con.createStatement();
		// ステートメント（SQL文）の実行と結果の取得
		ResultSet rs = smt.executeQuery(sql);
		// ステートメントオブジェクト（SQLコンテナ）のクローズ
		smt.close();
		//DB接続オブジェクトのクローズ
		con.close();
		//ステートメント(SQL文）の実行結果のリターン
		return rs;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("変更前");
		showTable();

		insertRecord(4, "u4", "i4", 1);

		System.out.println("変更後");
		showTable();

		executeQuery("delete from 注文表 where 注文番号=4");

		System.out.println("終了前");
		showTable();

	}

	static void showTable() throws SQLException {
		ResultSet rs = executeQuery("select * from 注文表");
		// ステートメント(SQL文）の実行結果の解析と表示
		while (rs.next()) {
			System.out.println("注文番号=" + rs.getString(1) + ", 顧客ID=" + rs.getString(2) + ", 商品ID=" + rs.getString(3)
					+ ", 購入数量=" + rs.getString(4));
		}
		System.out.println("\n");
	}

	static void insertRecord(int orderNumber, String customID, String itemID, int quantity) throws SQLException {
		executeQuery(
				"insert into 注文表 values(" + orderNumber + ",'" + customID + "','" + itemID + "'," + quantity + ")");
	}

}
