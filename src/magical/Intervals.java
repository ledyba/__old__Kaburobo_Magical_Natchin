/*
 * �쐬��: 2005/12/29
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
package magical;

/**
 * @author Sonic
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎��փW�����v:
 * �E�B���h�E - �ݒ� - Java - �R�[�h�E�X�^�C�� - �R�[�h�E�e���v���[�g
 */
public class Intervals{
	public double FLUCTUATION_INTERVAL;//75�����ς̕W���΍��̕��ϒl
	public double DAY_FLUCTUATION_INTERVAL;//75�����ςƖ����̕W���΍��̕��ϒl
	public double TURNOVER_INTERVAL;//�o�����̕��ϒl
	public int NUMBER;//�����̐�
	public double fluctuations[];//���ςƖ����̏o���ꂽ�W���΍�
	public double turnovers[];//�o���ꂽ�o����
	public Intervals(int number){
		NUMBER = number;
		/*�z��m��*/
		fluctuations = new double[NUMBER];
		turnovers = new double[NUMBER];
	}
}