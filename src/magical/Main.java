/*
 * �쐬��: 2005/09/29
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package magical;

/**
 * gammma�F������Ɩ{�C���[�h�ł�
 * */

import java.util.Calendar;
import java.util.Map;

import jp.kaburobo.investment.Holding;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Portfolio;
import jp.kaburobo.investment.SimpleStockOrder;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.investment.StockOrder;
import jp.kaburobo.robot.Robot;
import jp.kaburobo.util.Time;

/**
 * @author Latias �{�ԗp�v���O�����u���@�����}�W�J�����Ȃ�����v TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 *         �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class Main implements Robot {
//	public static final long HOW_MUCH = 2;//��x�ɒ�������P�ʁD
	public static final long HOW_MUCH = 75 * 10000;//��x�ɒ���������z�D

	static InvestmentAgent Agent;

	static Calendar today;

	static Stock[] stocks;

	static EachStockAnalyzer ESA;

	public Main() {
		today = Time.getTime();//���݂̎��Ԃ��擾
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see jp.kaburobo.robot.Robot#run(jp.kaburobo.investment.InvestmentAgent)
	 */
	public void run(InvestmentAgent agent) {
		System.out.println("���@�����}�W�J�����Ȃ����� ver1.40(2006/01/18)");
		System.out.println("����΂�E�E�E�����B");
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Agent = agent;
		Intervals it = (new GetInterval()).getInterval(agent, today);
		ESA = new EachStockAnalyzer(agent, today);
		stocks = agent.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			int ans = ESA.analyze(stocks[i], i, it);
			if (ans > 0) {//���𔃂��ׂ��ł���
				System.out.println(stocks[i].getName() + "�F�����E�E�E�����B");
				order(stocks[i], StockOrder.BUY);//�����܂�

				Portfolio pt = agent.getPortfolio();
				Map mp = pt.getHoldings();
				Holding hd = (Holding) mp.get(stocks[i]);
				int amount = 0;
				if (hd != null) {
					amount = hd.getNumber();
//					System.out.println(amount);
				}
				
			} else if (ans == 0) {//�Ȃɂ����ׂ��łȂ�
				//�Ȃɂ����Ȃ�
				System.out.println(stocks[i].getName() + "�F�������Ȃ��E�E�E�����B");
			} else if (ans < 0) {//���𔄂�ׂ��ł���
				Portfolio pt = agent.getPortfolio();
				Map mp = pt.getHoldings();
				Holding hd = (Holding) mp.get(stocks[i]);
				int amount = 0;
				if (hd != null) {
					amount = hd.getNumber();
				}
				if (amount > 0) {
					System.out.println(stocks[i].getName() + "�F����E�E�E�����B");
					order(stocks[i], StockOrder.SELL);//����܂�
				} else {
					System.out
							.println(stocks[i].getName() + "�F�󔄂�͂������Ȃ��E�E�E�����B");
				}
			}

		}
	}

	public void order(Stock stock, int type) {
/*		IndexInformation ii = Agent.getInformationManager().getIndexInformation(stock);
		StockOrder order = new SimpleStockOrder(stock, type, StockOrder.NUMBER,
				ii.getTradingLotSize()*HOW_MUCH, 0);//�Œ�̈�P�ʔ����܂��D
				*/
		StockOrder order = new SimpleStockOrder(stock, type, StockOrder.MONEY,
				HOW_MUCH, 0);//�Œ�̈�P�ʔ����܂��D
		Agent.order(order);//����
	}

}