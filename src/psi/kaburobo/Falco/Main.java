/*
 * �쐬��: 2005/09/14
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package psi.kaburobo.Falco;

import java.util.Calendar;

import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.SimpleStockOrder;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.investment.StockOrder;
import jp.kaburobo.robot.Robot;
import jp.kaburobo.util.Time;

/**
 * �J�u���{�v���O�����u���X����Ă��邪���͖����v
 * @author Latias
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class Main implements Robot {
	static final int HOW_MUCH=500000;//50���~�D���������C�ɔ����ׂ���
	Time time;
	Calendar today;
	Stock[] stock;
	eachStockAnalyzer Analyzer;
	public Main(){
		Time time = new Time();
		Calendar today = Time.getTime();//�����̓��t���擾
	}
	/* (�� Javadoc)
	 * @see jp.kaburobo.robot.Robot#run(jp.kaburobo.investment.InvestmentAgent)
	 */
	public void run(InvestmentAgent agent) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		stock = agent.getStocks();
		for(int i=0;i<stock.length;i++){
			Analyzer = new eachStockAnalyzer(stock[i],agent,today);
			int whatToDo = Analyzer.Analyze();
			if(whatToDo != eachStockAnalyzer.NOTHING){//����������Ă��ƂȂ��
			agent.order(makeOrder(stock[i],whatToDo));
			}
		}
//		Portfolio pf = agent.getPortfolio();
//		pf.getHoldings();
	}
	public StockOrder makeOrder(Stock stock,int flag){
		StockOrder order = new SimpleStockOrder();
		order.setTradeType(flag);
		order.setLimitType(StockOrder.MARKET);
		order.setUnitType(StockOrder.MONEY);
		order.setQuantity(HOW_MUCH);
		order.setStock(stock);
		return order;
	}

}
