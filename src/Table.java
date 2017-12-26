import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 汎用的なテーブルへのSQL操作をまとめたクラス
 *
 * @author BP16001 足立賢人 試験場
 */
public class Table {

	//////////////
	//フィールド//
	//////////////
	SQLManager manager_;
	String name_;
	ArrayList<String> columns_;

	/**
	 * デフォルトコンストラクタ
	 *
	 * @param name
	 *            テーブル名
	 * @param columns
	 *            項目名
	 *
	 * @throws SQLException
	 */
	public Table(String name, ArrayList<String> columns) throws SQLException {
		// TODO 自動生成されたコンストラクター・スタブ
		manager_ = new SQLManager();
		name_ = name;
		columns_ = columns;
	}

	String getName() {
		return name_;
	}

	/**
	 * 任意のSQL文の実行
	 *
	 * @param sql
	 *            SQL文
	 * @return
	 * @throws SQLException
	 */
	private ResultSet executeQuery(String sql) throws SQLException {
		return manager_.executeQuery(sql);
	}

	/**
	 * テーブル内のすべてのレコードを削除する
	 *
	 * @return 実行結果
	 * @throws SQLException
	 */
	public ResultSet deleteAllRecord() throws SQLException {
		return executeQuery("delete from " + name_);
	}

	/**
	 * テーブルにレコードを追加する
	 *
	 * @param data
	 *            レコード内容
	 * @return 実行結果
	 * @throws SQLException
	 */
	public ResultSet insertRecord(ArrayList<String> data) throws SQLException {

		//		java7以前で書きたいなら
		StringBuilder builder = new StringBuilder();
		for (String datum : data) {
			if (builder.length() > 0) {
				builder.append(",");
			}
			builder.append(datum);
		}
		return executeQuery("insert into " + name_ + "values(" + builder.toString() + "");

		//		java8以降
		//		return executeQuery("insert into "+name_+"values("+String.join(",", data)+")");
	}

	/**
	 * 全てのレコードを表示する
	 *
	 * @return
	 * @throws SQLException
	 */
	public ResultSet showAllRecord() throws SQLException {
		ResultSet resultSet = executeQuery("select * from " + name_);
		while (resultSet.next()) {
			for (String column : columns_) {
				System.out.print(column + ": " + resultSet.getString(column) + ", ");
			}
			System.out.println("");
		}
		return resultSet;
	}

}
