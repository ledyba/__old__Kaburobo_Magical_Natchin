/*
 * 作成日: 2005/09/29
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package magical;

/**
 * gammma：ちょっと本気モードです
 * */

import java.util.Calendar;
import java.util.Map;

import jp.kaburobo.investment.Holding;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Portfolio;
import jp.kaburobo.investment.SimpleStockOrder;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.investment.StockOrder;
import jp.kaburobo.robot.Robot;
import jp.kaburobo.util.Time;

/**
 * @author Latias 本番用プログラム「魔法少女マジカル☆なっちん」 TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 *         ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class Main implements Robot {
//	public static final long HOW_MUCH = 2;//一度に注文する単位．
	public static final long HOW_MUCH = 75 * 10000;//一度に注文する金額．

	static InvestmentAgent Agent;

	static Calendar today;

	static Stock[] stocks;

	static EachStockAnalyzer ESA;

	public Main() {
		today = Time.getTime();//現在の時間を取得
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see jp.kaburobo.robot.Robot#run(jp.kaburobo.investment.InvestmentAgent)
	 */
	public void run(InvestmentAgent agent) {
		System.out.println("魔法少女マジカル☆なっちん ver1.40(2006/01/18)");
		System.out.println("がんばる・・・かも。");
		// TODO 自動生成されたメソッド・スタブ
		Agent = agent;
		Intervals it = (new GetInterval()).getInterval(agent, today);
		ESA = new EachStockAnalyzer(agent, today);
		stocks = agent.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			int ans = ESA.analyze(stocks[i], i, it);
			if (ans > 0) {//株を買うべきである
				System.out.println(stocks[i].getName() + "：買う・・・かも。");
				order(stocks[i], StockOrder.BUY);//買います

				Portfolio pt = agent.getPortfolio();
				Map mp = pt.getHoldings();
				Holding hd = (Holding) mp.get(stocks[i]);
				int amount = 0;
				if (hd != null) {
					amount = hd.getNumber();
//					System.out.println(amount);
				}
				
			} else if (ans == 0) {//なにもすべきでない
				//なにもしない
				System.out.println(stocks[i].getName() + "：何もしない・・・かも。");
			} else if (ans < 0) {//株を売るべきである
				Portfolio pt = agent.getPortfolio();
				Map mp = pt.getHoldings();
				Holding hd = (Holding) mp.get(stocks[i]);
				int amount = 0;
				if (hd != null) {
					amount = hd.getNumber();
				}
				if (amount > 0) {
					System.out.println(stocks[i].getName() + "：売る・・・かも。");
					order(stocks[i], StockOrder.SELL);//売ります
				} else {
					System.out
							.println(stocks[i].getName() + "：空売りはしたくない・・・かも。");
				}
			}

		}
	}

	public void order(Stock stock, int type) {
/*		IndexInformation ii = Agent.getInformationManager().getIndexInformation(stock);
		StockOrder order = new SimpleStockOrder(stock, type, StockOrder.NUMBER,
				ii.getTradingLotSize()*HOW_MUCH, 0);//最低の一単位買います．
				*/
		StockOrder order = new SimpleStockOrder(stock, type, StockOrder.MONEY,
				HOW_MUCH, 0);//最低の一単位買います．
		Agent.order(order);//注文
	}

}