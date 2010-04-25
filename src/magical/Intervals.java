/*
 * 作成日: 2005/12/29
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package magical;

/**
 * @author Sonic
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class Intervals{
	public double FLUCTUATION_INTERVAL;//75日平均の標準偏差の平均値
	public double DAY_FLUCTUATION_INTERVAL;//75日平均と毎日の標準偏差の平均値
	public double TURNOVER_INTERVAL;//出来高の平均値
	public int NUMBER;//銘柄の数
	public double fluctuations[];//平均と毎日の出された標準偏差
	public double turnovers[];//出された出来高
	public Intervals(int number){
		NUMBER = number;
		/*配列確保*/
		fluctuations = new double[NUMBER];
		turnovers = new double[NUMBER];
	}
}