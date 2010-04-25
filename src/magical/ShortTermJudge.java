/*
 * 作成日: 2006/01/09
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package magical;

import java.util.Calendar;

import jp.kaburobo.information.PriceData;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;

/**
 * @author Tails
 *	短期での儲けを狙います
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class ShortTermJudge extends AverageJudge{
	public ShortTermJudge(Stock stock, InvestmentAgent agent,
			Calendar today) {
		super(stock,agent,today);
	}
	public int doJudge(){
		//価格のデータを再度つくる
		PriceData[] priceList = makePriceList(ANALYZE_DAYS, 0);
		double nowPrice = priceList[priceList.length-1].getPrices("").getPrice();
		double up = getAverage(-1,priceList);
		double down = getAverage(1,priceList);
		if(nowPrice > up){//売れ！
			System.out.println(this.Stock.getName()+"：上がった時の平均金額よりも高い・・・かも。");
			return -1;
		}else if(nowPrice < down){//買え！
			System.out.println(Stock.getName()+"：下がった時の平均金額よりも低い・・・かも。");
			return 1;
		}
		//何もするな！
		System.out.println(Stock.getName()+"：上がったとき，下がった時の平均金額の間・・・かも。");
		return 0;
	}
	/**
	 * 
	 * @author Tails
	 * 短期での「あがった」，「下がった」時の平均値を返します
	 * 
	 **/
	public double getAverage(int flag,PriceData[] priceList){	
		double average = 0;
		int count=0;
		for(int i=2;i<priceList.length;i++){
			double p1=priceList[i-2].getPrices("").getPrice();
			double p2=priceList[i-1].getPrices("").getPrice();
		double	p3=priceList[i].getPrices("").getPrice();
			if(0 < flag*(p1 - p2)
			&& 0 > flag*(p2 - p3)){
				//flag > 0　：　あがって，下がった
				//flag < 0　：　下がって，あがった
					count++;
					average += p2;
			}
		}
		return average/count;
	}
}
