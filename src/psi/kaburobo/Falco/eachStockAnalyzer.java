/*
 * �쐬��: 2005/09/14
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
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
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v: �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� -
 * �R�[�h�E�e���v���[�g
 */
public class eachStockAnalyzer {

	static final int UP_AVERAGE = 1;

	static final int DOWN_AVERAGE = -1;

	static final int DAY_LIMIT = 15;//�������Ă���C�������Ă���Ɣ��f���郊�~�b�g

	static final int BUY = StockOrder.BUY;

	static final int NOTHING = 0xffff;//�����ł����Ȃ��ƑʖڂȂ̂�C���ꂪ�D

	static final int SELL = StockOrder.SELL;

	static final int ANALYZE_DAY_LONG = 150;//�����Ԃ܂ŉ�͂��邩

	static final int AVERAGE_DAYS = 25;//�����̕��ς��Ƃ邩
	
	static final double CHANGED_LIMIT = 0.5/100;//���ꖢ���̕ω��Ȃ�ω��ƔF�߂Ȃ�

	double upAverage;//�����肫�������̍ō��l�̕���

	double downAverage;//�����肫�������̍ō��l�̕���

	double nowPrice;

	Calendar Today;//��͊J�n���̓��t

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
	 * �{�J�u���{�ɂ����čł��ŏd�v�ȁC��̓N���X
	 * 
	 * @return
	 */
	public int Analyze() {
		PriceData[] priceList = makePriceList(ANALYZE_DAY_LONG);//���i�̃��X�g�������
		nowPrice = priceList[priceList.length-1].getPrices("").getPrice();//�Ƃ肠�������݂̉��i������
		double[] averageList = makeAverageList(priceList);//���ψړ��̃��X�g�������
		//double[] movingList =
		// makeMovingList(nowPrice,averageList);//���ψړ�����m�C�Y�������������X�g���쐬
		upAverage = getUpDownAverage(averageList, UP_AVERAGE); //��ԏ�ɗ��Ă���Ƃ��̍ō��l
		downAverage = getUpDownAverage(averageList, DOWN_AVERAGE);//��ԉ��ɗ��Ă���Ƃ��̍ň��l
		return decide(averageList);
	}
	/**
	 * �ŁC���ǂǂ�����̂������܂��D
	 * @param averageList
	 * @return �s���R�[�h
	 */
	public int decide(double[] averageList) {
		//�܂��C���ꂼ��㉺�𒴂��Ă��邱��
		if (averageList[ANALYZE_DAY_LONG - 1] >= upAverage) {
			//���C������C��
//			if (averageList[ANALYZE_DAY_LONG - 2]
//					- averageList[ANALYZE_DAY_LONG - 1] < 0) {
				System.out.println(Stock.getName() + " : ("
						+ averageList[ANALYZE_DAY_LONG - 2] + "->"
						+ averageList[ANALYZE_DAY_LONG - 1] + ")" + downAverage
						+ "->" + downAverage);
				return SELL;
//			}
		} else if (averageList[ANALYZE_DAY_LONG - 1] <= downAverage) {
			//���C�オ��C��
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
	 * ���i�̃��X�g�𐻍삷��
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
	 * ���ψړ��𐻍삷��
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
	//	 * �m�C�Y�������ꂽ����
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
	 * �������ς�������̕��ϊ���
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
				if (type * (movingList[i] - movingList[i - 1]) < 0) {//�������ς�����I
					changed = true;//�����ύX
					count = 0;
				}else{
					changed = false;
				}
			}
			if (count >= DAY_LIMIT) {//DAY_LIMIT����������
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
		} else {//�ЂƂ��ǂ��̂��������
			return Integer.MAX_VALUE * type;
		}
	}
}