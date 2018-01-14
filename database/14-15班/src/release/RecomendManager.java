package release;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * おすすめ表示
 *
 * @author BP16001
 *
 */
public class RecomendManager {

	OrderTable orderTable_;
	CustomerTable customerTable_;
	ItemTable itemTable_;

	/**
	 * @param customerTable
	 * @param itemTable
	 * @param orderTable
	 */
	public RecomendManager(CustomerTable customerTable, ItemTable itemTable, OrderTable orderTable) {
		customerTable_ = customerTable;
		itemTable_ = itemTable;
		orderTable_ = orderTable;
	}

	/**
	 * おすすめ商品を取得する
	 *
	 * @param customerID
	 *            顧客ID
	 * @return おすすめする商品の商品ID
	 * @throws SQLException
	 */
	public int recomend(int customerID) throws SQLException {
		customerID--;
		int n = customerTable_.count();
		int m = itemTable_.count();

		boolean mat[][] = new boolean[n][m];

		//初期化
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				mat[i][j] = false;
			}
		}
		//注文した事のある組み合わせをtrueに
		ResultSet resultSet = Table.executeQuery("select distinct 顧客ID,商品ID from 注文表");
		while (resultSet.next()) {
			int i = resultSet.getInt("顧客ID") - 1;
			int j = resultSet.getInt("商品ID") - 1;
			mat[i][j] = true;
		}
		//対象との距離を調べる
		int distance[] = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (mat[i][j] ^ mat[customerID][j]) {
					distance[i]++;
				}
			}
		}

		//購入傾向の近いユーザーを抽出
		ArrayList<Integer> group = new ArrayList<>();
		for (int i = 0; i < distance.length; i++) {
			int rank = 1;
			for (int j = 0; j < distance.length; j++) {
				if (distance[j] < distance[i]) {
					rank++;
				}
			}
			//上位10%をリストに追加
			if (rank <= n / 10) {
				group.add(i);
			}
			//			System.out.println("rank:"+rank);
		}

		//商品のランクをつける
		int popularity[] = new int[m];
		for (int i : group) {
			for (int j = 0; j < m; j++) {
				if (mat[customerID][j]) {
					continue;
				}
				if (mat[i][j]) {
					popularity[j]++;
				}
			}
		}

		int max = 0;
		int index = -1;
		for (int i = 0; i < popularity.length; i++) {
			if (popularity[i] > max) {
				max = popularity[i];
				index = i;
			}
		}

		index++;
		System.out.println("おすすめ商品は" + index);
		resultSet = SQLManager.executeQuery("select * from 商品表 where 商品ID = " + index);
		if (resultSet.next()) {
			System.out.println("商品名 : " + resultSet.getString("商品名") + ", 値段 : " + resultSet.getInt("値段") + ", 在庫数 : "
					+ resultSet.getInt("在庫数"));
		}

		//		for (int i = 0;i<popularity.length;i++) {
		//			System.out.println("popularity["+i+"] : "+popularity[i]);
		//		}
		//
		//		System.out.println("");
		//		for (Integer integer : group) {
		//			System.out.println("group:"+integer);
		//		}
		//		System.out.println("");
		//
		//		for (int i : distance) {
		//			System.out.println(i);
		//		}
		//
		//		for (boolean[] bs : mat) {
		//			System.out.println("");
		//			for (boolean b : bs) {
		//				System.out.printf("%6b", b);
		//			}
		//		}
		return index;
	}
}
