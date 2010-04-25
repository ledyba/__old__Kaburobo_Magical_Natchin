/*
 * �쐬��: 2006/01/09
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package magical;

import java.util.Calendar;

import jp.kaburobo.information.PriceData;
import jp.kaburobo.investment.InvestmentAgent;
import jp.kaburobo.investment.Stock;

/**
 * @author Tails
 *	�Z���łׂ̖���_���܂�
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class ShortTermJudge extends AverageJudge{
	public ShortTermJudge(Stock stock, InvestmentAgent agent,
			Calendar today) {
		super(stock,agent,today);
	}
	public int doJudge(){
		//���i�̃f�[�^���ēx����
		PriceData[] priceList = makePriceList(ANALYZE_DAYS, 0);
		double nowPrice = priceList[priceList.length-1].getPrices("").getPrice();
		double up = getAverage(-1,priceList);
		double down = getAverage(1,priceList);
		if(nowPrice > up){//����I
			System.out.println(this.Stock.getName()+"�F�オ�������̕��ϋ��z���������E�E�E�����B");
			return -1;
		}else if(nowPrice < down){//�����I
			System.out.println(Stock.getName()+"�F�����������̕��ϋ��z�����Ⴂ�E�E�E�����B");
			return 1;
		}
		//��������ȁI
		System.out.println(Stock.getName()+"�F�オ�����Ƃ��C�����������̕��ϋ��z�̊ԁE�E�E�����B");
		return 0;
	}
	/**
	 * 
	 * @author Tails
	 * �Z���ł́u���������v�C�u���������v���̕��ϒl��Ԃ��܂�
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
				//flag > 0�@�F�@�������āC��������
				//flag < 0�@�F�@�������āC��������
					count++;
					average += p2;
			}
		}
		return average/count;
	}
}
