/*
 * 作成日: 2005/09/29
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package magical;

import java.util.Calendar;

import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;

/**
 * 
 * @author Sonic
 * 
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ: ウィンドウ - 設定 - Java - コード・スタイル -
 * コード・テンプレート
 */
public class EachStockAnalyzer {
	FluctuationAnalyzer FA;//標準偏差アナライザ

	Calendar today;//今日は？

	InvestmentAgent agent;//エージェント
	
	Intervals Interval;//閾値その他
	
	public EachStockAnalyzer(InvestmentAgent agent, Calendar today) {
		this.agent = agent;
		this.today = today;
	}

	/**
	 * アナライザ
	 * 
	 * @return 株を買うべきか，売るべきか返します
	 */
	public int analyze(Stock Stock,int number,Intervals interval) {
		FA = new FluctuationAnalyzer(Stock, agent, today);
		Interval = interval;//閾値や標準偏差のデータ
		if (Interval.fluctuations[number] > Interval.FLUCTUATION_INTERVAL) {//株価の変動が大きい
			System.out.println(Stock.getName()+"：株価の変動が大きい・・・かも。");
			return (new ShortTermJudge(Stock, agent, today)).doJudge();
		} else {//株価の変動が小さい
			System.out.println(Stock.getName()+"：株価の変動が小さい・・・かも。");
			if(Interval.turnovers[number] >= Interval.TURNOVER_INTERVAL){//出来高があるところより大きい
				/*それは「安定している」ということ．*/
				//30日平均が標準偏差を超えているか否かで判断する
				System.out.println(Stock.getName()+"：出来高が大きい・・・かも。");
				AverageJudge aj = new AverageJudge(Stock, agent, today);
				return aj.doJudge();
			}else{//変動が小さく，出来高もちいさい．
				/*単に「人気がない」．斜陽かも？*/
				System.out.println(Stock.getName()+"：出来高が小さく，人気が無い・・・かも。");
				return 0;//買うな，売るな．
			}

		}
	}
}