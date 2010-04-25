/*
 * 作成日: 2005/09/29
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package magical;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import jp.kaburobo.information.PriceData;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.technical.MovingAverage;
import jp.kaburobo.technical.TechnicalAnalysisManager;

/**
 * @author Latias
 * 
 * 移動平均からの標準偏差を求めます．
 * 株価の変動が激しいかどうかのチェック用．
 *  
 */
public class FluctuationAnalyzer {
	public static final int AVERAGE_DAYS = 75;//標準偏差を求めるのに使う日数

	public static final int ANALYZE_DAYS = 30;//何日間分の株価の標準偏差を出すか

	Stock Stock;

	InvestmentAgent Agent;

	Calendar Today;

	TechnicalAnalysisManager Tam;
	PriceData[] priceList;
	double[] movingList;
	double[] averageList;
	public FluctuationAnalyzer(Stock stock, InvestmentAgent agent,
			Calendar today) {
		Stock = stock;
		Agent = agent;
		Today = today;
		Tam = agent.getTechnicalAnalysisManager();
		//価格のデータをつくる
		priceList = makePriceList(ANALYZE_DAYS, AVERAGE_DAYS);
		//そしてANALYZE_DAYS分の株価をつくる
		movingList = makeMovingList(priceList,ANALYZE_DAYS);
		//平均移動のリストも作って
		averageList = makeAverageList(priceList, AVERAGE_DAYS,
				ANALYZE_DAYS);
	}
	/**
	 * 平均と毎日の標準偏差を求める
	 * @return
	 */
	public double getFluc(){
		return getFluctuation(movingList,averageList);
	}
	/**
	 * 価格のリストを製作する
	 * 
	 * @param analyzeDays
	 * @param averageDays
	 * @return
	 */
	private PriceData[] makePriceList(int analyzeDays, int averageDays) {
		List tmpList = Agent.getInformationManager().getIndexInformation(Stock,
				Today, -analyzeDays - averageDays + 1);
		PriceData[] priceList = (PriceData[]) tmpList.toArray(new PriceData[0]);
		/*
		 * for(int i=0;i <priceList.length;i++){
		 * System.out.print(priceList[i].getPrices("").getPrice()+","); }
		 * System.out.println("");
		 */
		return priceList;
	}

	/**
	 * 移動平均を求めます
	 * 
	 * @param priceList
	 * @param averageDays
	 * @param analyzeDays
	 * @return
	 */
	private double[] makeAverageList(PriceData[] priceList, int averageDays,
			int analyzeDays) {
		double average[] = new double[analyzeDays];
		List averageList = Tam.getMovingAverage(priceList, "", averageDays);
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
	/**
	 * 株価を作ります
	 * @param priceList
	 * @param analyzeDays
	 * @return
	 */
	
	private double[] makeMovingList(PriceData[] priceList,int analyzeDays){
		double[] movingList = new double[analyzeDays];
		for(int i=0;i<analyzeDays;i++){
			movingList[i] = priceList[priceList.length-1].getPrices("").getPrice();
		}
		return movingList;
	}
	/**
	 * 平均と日にちの標準偏差を作成します
	 * 
	 * @param movingList
	 * @param averageList
	 * @return
	 */
	private double getFluctuation(double[] movingList, double[] averageList){
		if(movingList.length != averageList.length){
			System.out.println("Error!");
		}
		double sum=0;
		int count = 0;
		for(int i=0;i<movingList.length;i++){
			double gap = (movingList[i]-averageList[i]);
			sum += (gap*gap)/count;
			count++;
		}
		return Math.sqrt(sum);
	}
}