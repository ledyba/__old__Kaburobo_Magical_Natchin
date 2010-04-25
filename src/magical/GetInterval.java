/*
 * 作成日: 2005/12/29
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package magical;

import java.util.Calendar;

import jp.kaburobo.information.IndexInformation;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;

/**
 * @author Sonic
 *	閾値を求めるための，プログラムです．
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class GetInterval {
	private Stock[] stocks;
	private Calendar Today;
	private Intervals interval;
	private InvestmentAgent Agent;
	FluctuationAnalyzer FA;
	public Intervals getInterval(InvestmentAgent agent,Calendar today){
		stocks = agent.getStocks();
		interval = new Intervals(stocks.length);//閾値などをあらわす
		Today = today;
		Agent = agent;
		interval.FLUCTUATION_INTERVAL = getFlactuation();
		interval.TURNOVER_INTERVAL = getTurnover();//出来高
		return interval;
	}
	/**
	 * 標準偏差を求めます．
	 * @return
	 */
	double getFlactuation(){
		double average = 0;//N日平均と毎日の標準偏差
		for (int i = 0; i < stocks.length; i++) {
			FluctuationAnalyzer FA = new FluctuationAnalyzer(stocks[i],Agent,Today);
			interval.fluctuations[i] = FA.getFluc();
			average += interval.fluctuations[i] / stocks.length;
		}
		return average;
	}
	/**
	 * 出来高の平均値を求めます
	 * @return
	 */
	double getTurnover(){
		double average=0;
		for (int i = 0; i < stocks.length; i++) {
			IndexInformation IF = Agent.getInformationManager()
			.getIndexInformationMonthly(stocks[i]);//一ヶ月間の情報をいただきます．
			interval.turnovers[i] = IF.getPrices().getTurnover();
			average += interval.turnovers[i] / stocks.length;
		}
		return average;
//		return Math.sqrt(average);
	}
	
}
