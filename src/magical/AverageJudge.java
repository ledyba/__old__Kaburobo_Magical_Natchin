/*
 * 作成日: 2005/12/29
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
 * @author Sonic
 * 75日平均の平均値と，75日平均の平均値との標準偏差を求め，買うか売るかを決めます．
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class AverageJudge {
	protected Stock Stock;
	protected InvestmentAgent Agent;
	protected Calendar Today;

	public static final int AVERAGE_DAYS = 75;//標準偏差を求めるのに使う日数
	public static final int ANALYZE_DAYS = 30;//何日間分の株価の標準偏差を出すか

	TechnicalAnalysisManager Tam;

	public AverageJudge(Stock stock, InvestmentAgent agent,
			Calendar today) {
		Stock = stock;
		Agent = agent;
		Today = today;
		Tam = agent.getTechnicalAnalysisManager();
	}
	public int doJudge(){
		int judge = 0;

		//価格のデータを再度つくる
		PriceData[] priceList = makePriceList(ANALYZE_DAYS, AVERAGE_DAYS);
		//平均移動のリストを再度作って
		double[] averageList = makeAverageList(priceList, AVERAGE_DAYS,
				ANALYZE_DAYS);
		
		/*移動平均の標準偏差と平均値を求めます*/
		double average = getAverage(averageList);
		double fluctuation = Math.sqrt(getFluctuation(averageList,average));
		double now = priceList[priceList.length-1].getPrices("").getPrice();
		
		if(now >= average+fluctuation){//売れ！
			System.out.println(Stock.getName()+"：(平均＋標準偏差)よりも高い・・・かも．");
			judge = -1;
		}else if(now <= average-fluctuation){//買え！
			System.out.println(Stock.getName()+"：(平均－標準偏差)よりも低い・・・かも．");
			judge = 1;
		}else{//何もするな！
			System.out.println(Stock.getName()+"：(平均±標準偏差)から出ていない・・・かも。");
			judge = 0;
		}
		
		return judge;
	}
	/**
	 * 移動平均の平均値を求めます
	 * @param averageList
	 * @return
	 */
	public double getAverage(double[] averageList){
		double average = 0;
		for(int i=0; i < averageList.length;i++){
			average += averageList[i];
		}
		average = average / averageList.length;
		return average;
	}
	/**
	 * 移動平均の標準偏差を求めます
	 * @param averageList
	 * @param average
	 * @return
	 */
	public double getFluctuation(double[] averageList,double average){
		double fluctuation = 0;
		for(int i=0; i < averageList.length;i++){
			double tmp = (averageList[i]-average);
			fluctuation += tmp * tmp;
		}
		fluctuation = fluctuation / average;
		return fluctuation;
	}
	
	/**
	 * 価格のリストを製作する
	 * 
	 * @param analyzeDays
	 * @param averageDays
	 * @return
	 */
	public PriceData[] makePriceList(int analyzeDays, int averageDays) {
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
	public double[] makeAverageList(PriceData[] priceList, int averageDays,
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
	
}
