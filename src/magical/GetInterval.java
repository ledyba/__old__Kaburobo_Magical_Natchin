/*
 * �쐬��: 2005/12/29
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package magical;

import java.util.Calendar;

import jp.kaburobo.information.IndexInformation;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;

/**
 * @author Sonic
 *	臒l�����߂邽�߂́C�v���O�����ł��D
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class GetInterval {
	private Stock[] stocks;
	private Calendar Today;
	private Intervals interval;
	private InvestmentAgent Agent;
	FluctuationAnalyzer FA;
	public Intervals getInterval(InvestmentAgent agent,Calendar today){
		stocks = agent.getStocks();
		interval = new Intervals(stocks.length);//臒l�Ȃǂ�����킷
		Today = today;
		Agent = agent;
		interval.FLUCTUATION_INTERVAL = getFlactuation();
		interval.TURNOVER_INTERVAL = getTurnover();//�o����
		return interval;
	}
	/**
	 * �W���΍������߂܂��D
	 * @return
	 */
	double getFlactuation(){
		double average = 0;//N�����ςƖ����̕W���΍�
		for (int i = 0; i < stocks.length; i++) {
			FluctuationAnalyzer FA = new FluctuationAnalyzer(stocks[i],Agent,Today);
			interval.fluctuations[i] = FA.getFluc();
			average += interval.fluctuations[i] / stocks.length;
		}
		return average;
	}
	/**
	 * �o�����̕��ϒl�����߂܂�
	 * @return
	 */
	double getTurnover(){
		double average=0;
		for (int i = 0; i < stocks.length; i++) {
			IndexInformation IF = Agent.getInformationManager()
			.getIndexInformationMonthly(stocks[i]);//�ꃖ���Ԃ̏������������܂��D
			interval.turnovers[i] = IF.getPrices().getTurnover();
			average += interval.turnovers[i] / stocks.length;
		}
		return average;
//		return Math.sqrt(average);
	}
	
}
