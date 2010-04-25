/*
 * �쐬��: 2005/09/29
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package magical;

import java.util.Calendar;

import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;

/**
 * 
 * @author Sonic
 * 
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v: �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� -
 * �R�[�h�E�e���v���[�g
 */
public class EachStockAnalyzer {
	FluctuationAnalyzer FA;//�W���΍��A�i���C�U

	Calendar today;//�����́H

	InvestmentAgent agent;//�G�[�W�F���g
	
	Intervals Interval;//臒l���̑�
	
	public EachStockAnalyzer(InvestmentAgent agent, Calendar today) {
		this.agent = agent;
		this.today = today;
	}

	/**
	 * �A�i���C�U
	 * 
	 * @return ���𔃂��ׂ����C����ׂ����Ԃ��܂�
	 */
	public int analyze(Stock Stock,int number,Intervals interval) {
		FA = new FluctuationAnalyzer(Stock, agent, today);
		Interval = interval;//臒l��W���΍��̃f�[�^
		if (Interval.fluctuations[number] > Interval.FLUCTUATION_INTERVAL) {//�����̕ϓ����傫��
			System.out.println(Stock.getName()+"�F�����̕ϓ����傫���E�E�E�����B");
			return (new ShortTermJudge(Stock, agent, today)).doJudge();
		} else {//�����̕ϓ���������
			System.out.println(Stock.getName()+"�F�����̕ϓ����������E�E�E�����B");
			if(Interval.turnovers[number] >= Interval.TURNOVER_INTERVAL){//�o����������Ƃ�����傫��
				/*����́u���肵�Ă���v�Ƃ������ƁD*/
				//30�����ς��W���΍��𒴂��Ă��邩�ۂ��Ŕ��f����
				System.out.println(Stock.getName()+"�F�o�������傫���E�E�E�����B");
				AverageJudge aj = new AverageJudge(Stock, agent, today);
				return aj.doJudge();
			}else{//�ϓ����������C�o���������������D
				/*�P�Ɂu�l�C���Ȃ��v�D�Ηz�����H*/
				System.out.println(Stock.getName()+"�F�o�������������C�l�C�������E�E�E�����B");
				return 0;//�����ȁC����ȁD
			}

		}
	}
}