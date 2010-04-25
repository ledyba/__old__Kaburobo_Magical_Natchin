/*
 * 作成日: 2005/09/14
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 * テスト専用
 */
package psi.kaburobo.Test;

import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.SimpleStockOrder;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.investment.StockOrder;
import jp.kaburobo.robot.Robot;

/**
 * @author Latias
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class Test implements Robot {

	/**
	 * @see jp.kaburobo.robot.Robot#run(jp.kaburobo.investment.InvestmentAgent)
	 */
	public void run(InvestmentAgent agent) {
		// TODO 自動生成されたメソッド・スタブ
		Stock[] myStock = agent.getStocks();
		StockOrder myOrder = new SimpleStockOrder();
		myOrder.setTradeType(StockOrder.BUY);
		myOrder.setLimitType(StockOrder.MARKET);
		myOrder.setUnitType(StockOrder.MONEY);
		myOrder.setQuantity(10000000);
		myOrder.setStock(myStock[15]);
		agent.order(myOrder);
	}

}
