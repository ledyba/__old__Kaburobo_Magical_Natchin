/*
 * 作成日: 2005/09/14
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package psi.kaburobo.Falco;

import java.util.Calendar;

import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.SimpleStockOrder;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.investment.StockOrder;
import jp.kaburobo.robot.Robot;
import jp.kaburobo.util.Time;

/**
 * カブロボプログラム「少々ずれているが問題は無い」
 * @author Latias
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class Main implements Robot {
	static final int HOW_MUCH=500000;//50万円．いくらを一気に買うべきか
	Time time;
	Calendar today;
	Stock[] stock;
	eachStockAnalyzer Analyzer;
	public Main(){
		Time time = new Time();
		Calendar today = Time.getTime();//今日の日付を取得
	}
	/* (非 Javadoc)
	 * @see jp.kaburobo.robot.Robot#run(jp.kaburobo.investment.InvestmentAgent)
	 */
	public void run(InvestmentAgent agent) {
		// TODO 自動生成されたメソッド・スタブ
		stock = agent.getStocks();
		for(int i=0;i<stock.length;i++){
			Analyzer = new eachStockAnalyzer(stock[i],agent,today);
			int whatToDo = Analyzer.Analyze();
			if(whatToDo != eachStockAnalyzer.NOTHING){//何かしろってことならば
			agent.order(makeOrder(stock[i],whatToDo));
			}
		}
//		Portfolio pf = agent.getPortfolio();
//		pf.getHoldings();
	}
	public StockOrder makeOrder(Stock stock,int flag){
		StockOrder order = new SimpleStockOrder();
		order.setTradeType(flag);
		order.setLimitType(StockOrder.MARKET);
		order.setUnitType(StockOrder.MONEY);
		order.setQuantity(HOW_MUCH);
		order.setStock(stock);
		return order;
	}

}
