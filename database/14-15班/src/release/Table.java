package release;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 汎用的なテーブルへのSQL操作をまとめたクラス
 *
 * @author BP16001 足立賢人
 */
public class Table {


	static SQLManager manager_;
	String name_;
	ArrayList<String> columns_;

	/**
	 * コンストラクタ
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

	/**
	 * ArrayListをStringに結合する java8以降でのString.joinに相当する
	 *
	 * @param record
	 * @return
	 */
	private static String join(ArrayList<Object> record) {
		StringBuilder builder = new StringBuilder();
		for (Object datum : record) {
			if (builder.length() > 0) {
				builder.append(",");
			}
			builder.append(datum);
		}
		return builder.toString();

	}

	/**
	 * 任意のSQL文の実行
	 *
	 * @param sql
	 *            SQL文
	 * @return
	 * @throws SQLException
	 */
	protected static ResultSet executeQuery(String sql) throws SQLException {
		return SQLManager.executeQuery(sql);
	}

	/**
	 * テーブル内のすべてのレコードを削除する
	 *
	 * @return 実行結果
	 * @throws SQLException
	 */
	public ResultSet deleteAllRecord() throws SQLException {
		return executeQuery("truncate table " + name_);
	}

	/**
	 * テーブルのレコード数を返す
	 * @return
	 * @throws SQLException
	 */
	public int count() throws SQLException {
		ResultSet resultSet =  executeQuery("select count(*) from "+name_);
		resultSet.next();
		return resultSet.getInt(1);
	}

	/**
	 * テーブルに新たなレコードを追加する
	 *
	 * @param record
	 *            レコード内容
	 * @return 実行結果
	 * @throws SQLException
	 */
	public ResultSet insertRecord(ArrayList<Object> record) throws SQLException {
		return executeQuery("insert into " + name_ + " values(" + join(record) + ")");
		//		java8以降
		//		return executeQuery("insert into "+name_+"values("+String.join(",", data)+")");
	}

	/**
	 * テーブルに新たなレコードを追加する 挿入するラベル名を指定する
	 *
	 * @param columns
	 *            カラム名
	 * @param record
	 *            レコード内容
	 * @return
	 * @throws SQLException
	 */
	public ResultSet insertRecord(ArrayList<String> columns, ArrayList<Object> record) throws SQLException {
		return executeQuery("insert into " + name_ + " (" + join(new ArrayList<Object>(columns)) + ")" + " values("
				+ join(record) + ")");
	}

	/**
	 * 全てのレコードを取得する
	 *
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getAllRecord() throws SQLException {
		return executeQuery("select * from " + name_);
	}

	/**
	 * 全てのレコードを表示する getAllRecordの表示付き版
	 *
	 * @return
	 * @throws SQLException
	 */
	public ResultSet showAllRecord() throws SQLException {
		ResultSet resultSet = getAllRecord();
		while (resultSet.next()) {
			for (Iterator<String> iterator = columns_.iterator(); iterator.hasNext();) {
				String column = iterator.next();

				System.out.printf("%4s : %4s", column, resultSet.getString(column));
				if (iterator.hasNext()) {
					System.out.print(", ");
				}
			}
			System.out.println("");
		}
		return resultSet;
	}

	/**
	 * 指定列内の最大値を返す
	 *
	 * @param column
	 * @return
	 * @throws SQLException
	 */
	public int getMax(String column) throws SQLException {
		ResultSet resultSet = executeQuery("select max ( " + column + " ) from " + name_);
		resultSet.next();
		return resultSet.getInt(1);
	}

	/**
	 * 文字列をシングルクォーテーションで囲む
	 * @param string
	 * @return
	 */
	protected static String addQuot(String string) {
		return "'"+string+"'";

	}

	protected ResultSet selectColumn(String column,String key,int value) throws SQLException {
		return executeQuery("select "+column+" from "+name_+" where "+key+"="+value);
	}
	protected ResultSet selectColumn(String column,int keyvalue) throws SQLException {
		return selectColumn(column, columns_.get(0), keyvalue);

	}

}
