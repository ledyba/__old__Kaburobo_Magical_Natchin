/*
 * �쐬��: 2005/09/14
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 * �e�X�g��p
 */
package psi.kaburobo.Test;

import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.SimpleStockOrder;
import jp.kaburobo.investment.Stock;
import jp.kaburobo.investment.StockOrder;
import jp.kaburobo.robot.Robot;

/**
 * @author Latias
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class Test implements Robot {

	/**
	 * @see jp.kaburobo.robot.Robot#run(jp.kaburobo.investment.InvestmentAgent)
	 */
	public void run(InvestmentAgent agent) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Stock[] myStock = agent.getStocks();
		StockOrder myOrder = new SimpleStockOrder();
		myOrder.setTradeType(StockOrder.BUY);
		myOrder.setLimitType(StockOrder.MARKET);
		myOrder.setUnitType(StockOrder.MONEY);
		myOrder.setQuantity(10000000);
		myOrder.setStock(myStock[15]);
		agent.order(myOrder);
	}

}
