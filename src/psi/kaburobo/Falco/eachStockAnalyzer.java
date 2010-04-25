/*
 * 作成日: 2005/09/14
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package psi.kaburobo.Falco;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import jp.kaburobo.information.PriceData;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.investment.StockOrder;
import jp.kaburobo.technical.MovingAverage;
import jp.kaburobo.technical.TechnicalAnalysisManager;

/**
 * @author Latias
 * 
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ: ウィンドウ - 設定 - Java - コード・スタイル -
 * コード・テンプレート
 */
public class eachStockAnalyzer {

	static final int UP_AVERAGE = 1;

	static final int DOWN_AVERAGE = -1;

	static final int DAY_LIMIT = 15;//下がっている，あがっていると判断するリミット

	static final int BUY = StockOrder.BUY;

	static final int NOTHING = 0xffff;//こうでもしないと駄目なのよ，これが．

	static final int SELL = StockOrder.SELL;

	static final int ANALYZE_DAY_LONG = 150;//何日間まで解析するか

	static final int AVERAGE_DAYS = 25;//何日の平均をとるか
	
	static final double CHANGED_LIMIT = 0.5/100;//これ未満の変化なら変化と認めない

	double upAverage;//あがりきった時の最高値の平均

	double downAverage;//下がりきった時の最高値の平均

	double nowPrice;

	Calendar Today;//解析開始時の日付

	InvestmentAgent Agent;

	TechnicalAnalysisManager Tam;

	Stock Stock;

	public eachStockAnalyzer(Stock stock, InvestmentAgent agent, Calendar today) {
		Today = today;
		Agent = agent;
		Tam = agent.getTechnicalAnalysisManager();
		Stock = stock;
	}

	/**
	 * 本カブロボにおいて最も最重要な，解析クラス
	 * 
	 * @return
	 */
	public int Analyze() {
		PriceData[] priceList = makePriceList(ANALYZE_DAY_LONG);//価格のリストを作って
		nowPrice = priceList[priceList.length-1].getPrices("").getPrice();//とりあえず現在の価格をメモ
		double[] averageList = makeAverageList(priceList);//平均移動のリストも作って
		//double[] movingList =
		// makeMovingList(nowPrice,averageList);//平均移動からノイズを除去したリストを作成
		upAverage = getUpDownAverage(averageList, UP_AVERAGE); //一番上に来ているときの最高値
		downAverage = getUpDownAverage(averageList, DOWN_AVERAGE);//一番下に来ているときの最安値
		return decide(averageList);
	}
	/**
	 * で，結局どうするのかえします．
	 * @param averageList
	 * @return 行動コード
	 */
	public int decide(double[] averageList) {
		//まず，それぞれ上下を超えていること
		if (averageList[ANALYZE_DAY_LONG - 1] >= upAverage) {
			//かつ，下がり気味
//			if (averageList[ANALYZE_DAY_LONG - 2]
//					- averageList[ANALYZE_DAY_LONG - 1] < 0) {
				System.out.println(Stock.getName() + " : ("
						+ averageList[ANALYZE_DAY_LONG - 2] + "->"
						+ averageList[ANALYZE_DAY_LONG - 1] + ")" + downAverage
						+ "->" + downAverage);
				return SELL;
//			}
		} else if (averageList[ANALYZE_DAY_LONG - 1] <= downAverage) {
			//かつ，上がり気味
//			if (averageList[ANALYZE_DAY_LONG - 2]
//					- averageList[ANALYZE_DAY_LONG - 1] > 0) {
				System.out.println(Stock.getName() + " : ("
						+ averageList[ANALYZE_DAY_LONG - 2] + "->"
						+ averageList[ANALYZE_DAY_LONG - 1] + ")" + downAverage
						+ "->" + downAverage);
				return BUY;
//			}
		}
		return NOTHING;
	}

	/**
	 * 価格のリストを製作する
	 * 
	 * @param day
	 * @return
	 */
	public PriceData[] makePriceList(int day) {
		List tmpList = Agent.getInformationManager().getIndexInformation(Stock,
				Today, -day - AVERAGE_DAYS + 1);
		PriceData[] priceList = (PriceData[]) tmpList.toArray(new PriceData[0]);
		/*
		 * for(int i=0;i <priceList.length;i++){
		 * System.out.print(priceList[i].getPrices("").getPrice()+","); }
		 * System.out.println("");
		 */
		return priceList;
	}

	/**
	 * 平均移動を製作する
	 * 
	 * @param priceList
	 * @return
	 */
	public double[] makeAverageList(PriceData[] priceList) {
		double average[] = new double[ANALYZE_DAY_LONG];
		List averageList = Tam.getMovingAverage(priceList, "", AVERAGE_DAYS);
		//		System.out.println(averageList.size());
		Iterator it = averageList.iterator();
		int dayCount = 0;
		while (it.hasNext()) {
			MovingAverage tmpMA = (MovingAverage) it.next();
			average[dayCount] = tmpMA.getMovingAverage();
			//System.out.print(average[dayCount]+",");
			dayCount++;
		}
		//System.out.println("\n");
		return average;
	}

	//	/**
	//	 * ノイズ除去された株価
	//	 * @param startPrice
	//	 * @param averageList
	//	 * @return
	//	 */
	//	public double[] makeMovingList(double startPrice,double[] averageList){
	//		double[] movingList = new double[Day];
	//		movingList[0] = startPrice;
	//		for(int i=1;i<Day;i++){
	//			movingList[i]=movingList[i-1]+averageList[i-1];
	//			//System.out.print(movingList[i]+",");
	//		}
	//		//System.out.println("");
	//		return movingList;
	//	}
	/**
	 * 向きが変わった時の平均株価
	 * 
	 * @param movingList
	 * @param averageList
	 * @param type
	 * @return
	 */
	public double getUpDownAverage(double[] movingList, int type) {
		float sum = 0;
		int sumCount = 0;
		int count = 0;
		boolean changed = false;
		for (int i = 1; i < ANALYZE_DAY_LONG; i++) {
			if (Math.abs(movingList[i] / movingList[i - 1] -1) < CHANGED_LIMIT && changed) {
				count++;
			}else {
				if (type * (movingList[i] - movingList[i - 1]) < 0) {//向きが変わった！
					changed = true;//向き変更
					count = 0;
				}else{
					changed = false;
				}
			}
			if (count >= DAY_LIMIT) {//DAY_LIMIT分続いたら
				//System.out.println(i+":"+type+":"+movingList[i - DAY_LIMIT]);
				sum += movingList[i - DAY_LIMIT];
				sumCount++;
				changed = false;
				count = 0;
			}
		}
		//System.out.println(sum / sumCount + ",");
		if (sumCount > 0) {
			return (double) sum / sumCount;
		} else {//ひとつも良いのが無ければ
			return Integer.MAX_VALUE * type;
		}
	}
}