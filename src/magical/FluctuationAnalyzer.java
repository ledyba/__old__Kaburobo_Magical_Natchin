/*
 * �쐬��: 2005/09/29
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
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
 * �ړ����ς���̕W���΍������߂܂��D
 * �����̕ϓ������������ǂ����̃`�F�b�N�p�D
 *  
 */
public class FluctuationAnalyzer {
	public static final int AVERAGE_DAYS = 75;//�W���΍������߂�̂Ɏg������

	public static final int ANALYZE_DAYS = 30;//�����ԕ��̊����̕W���΍����o����

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
		//���i�̃f�[�^������
		priceList = makePriceList(ANALYZE_DAYS, AVERAGE_DAYS);
		//������ANALYZE_DAYS���̊���������
		movingList = makeMovingList(priceList,ANALYZE_DAYS);
		//���ψړ��̃��X�g�������
		averageList = makeAverageList(priceList, AVERAGE_DAYS,
				ANALYZE_DAYS);
	}
	/**
	 * ���ςƖ����̕W���΍������߂�
	 * @return
	 */
	public double getFluc(){
		return getFluctuation(movingList,averageList);
	}
	/**
	 * ���i�̃��X�g�𐻍삷��
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
	 * �ړ����ς����߂܂�
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
	 * ���������܂�
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
	 * ���ςƓ��ɂ��̕W���΍����쐬���܂�
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