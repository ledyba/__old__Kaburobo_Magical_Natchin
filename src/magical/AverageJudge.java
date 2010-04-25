/*
 * �쐬��: 2005/12/29
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
 * @author Sonic
 * 75�����ς̕��ϒl�ƁC75�����ς̕��ϒl�Ƃ̕W���΍������߁C���������邩�����߂܂��D
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class AverageJudge {
	protected Stock Stock;
	protected InvestmentAgent Agent;
	protected Calendar Today;

	public static final int AVERAGE_DAYS = 75;//�W���΍������߂�̂Ɏg������
	public static final int ANALYZE_DAYS = 30;//�����ԕ��̊����̕W���΍����o����

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

		//���i�̃f�[�^���ēx����
		PriceData[] priceList = makePriceList(ANALYZE_DAYS, AVERAGE_DAYS);
		//���ψړ��̃��X�g���ēx�����
		double[] averageList = makeAverageList(priceList, AVERAGE_DAYS,
				ANALYZE_DAYS);
		
		/*�ړ����ς̕W���΍��ƕ��ϒl�����߂܂�*/
		double average = getAverage(averageList);
		double fluctuation = Math.sqrt(getFluctuation(averageList,average));
		double now = priceList[priceList.length-1].getPrices("").getPrice();
		
		if(now >= average+fluctuation){//����I
			System.out.println(Stock.getName()+"�F(���ρ{�W���΍�)���������E�E�E�����D");
			judge = -1;
		}else if(now <= average-fluctuation){//�����I
			System.out.println(Stock.getName()+"�F(���ρ|�W���΍�)�����Ⴂ�E�E�E�����D");
			judge = 1;
		}else{//��������ȁI
			System.out.println(Stock.getName()+"�F(���ρ}�W���΍�)����o�Ă��Ȃ��E�E�E�����B");
			judge = 0;
		}
		
		return judge;
	}
	/**
	 * �ړ����ς̕��ϒl�����߂܂�
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
	 * �ړ����ς̕W���΍������߂܂�
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
	 * ���i�̃��X�g�𐻍삷��
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
	 * �ړ����ς����߂܂�
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
