package cx.HKCT.dao;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cx.HKCT.dao.Interfaces.ICxSvrHkct;
import cx.dao.CxSvrContext;
import cx.pub.Factory.CxSvrFactory;

public class CxSvrHkct 
{
	private	ICxSvrHkct hkct;
	
	public CxSvrHkct()
	{
		try
		{
			hkct = (ICxSvrHkct)CxSvrFactory.getInstance("ICxSvrHkct");
		}
		catch(Exception e)
		{
		}
	}
	
	public void getmax(CxSvrContext ctt, JSONObject jo) throws Exception
	{
		hkct.getmaxid(ctt, jo);
	}
	
	public void gettms(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.gettmsgis(ctt, jo);
	}
	
	/**
	 * ��ȡˮ���������η����Ĺ���������Ϣ
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	public void getgfloop(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getgfloop(ctt, ja);
	}
	
	/**
	 * ��ȡtms������ѯ���
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void gettmsgis(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.gettmsgis(ctt, ja);
	}
	
	/**
	 * tms�����豸
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	public void addtms(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.addtms(ctt, jo);
	}
	
	/**
	 * tms�ӿ�ɾ�������豸
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	public void deltms(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.deltms(ctt, jo);
	}
	public void getTmsByPage(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getTmsByPage(ctt, ja);
	}
	public void getTmsByCode(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getTmsByCode(ctt, ja);
	}
	public void getTmsByReady(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getTmsByReady(ctt, ja);
	}
	
	/**
	 * ������---��ѹ������
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	public void del(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.del(ctt, jo);
	}
	public void getScada_flow(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getScada_flow(ctt, jo);
	}
	public void getScada_net(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getScada_net(ctt, ja);
	}
	public void getScada_pipe(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getScada_pipe(ctt, ja);
	}
	public void getPnt(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getPnt(ctt, jo);
	}
	public void getScada_point(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getScada_point(ctt, ja);
	}
	public void getScada(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getScada(ctt, ja);
	}
	public void getUnScada_pipe(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getUnScada_pipe(ctt, ja);
	}
	public void getFlow(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getFlow(ctt, jo);
	}
	public void getGov(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getGov(ctt, jo);
	}
	public void getResultByType(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getResultByType(ctt, ja);
	}
	public void scadaDo(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.scadaDo(ctt, jo);
	}
	public void scadaDoGov(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.scadaDoGov(ctt, ja);
	}
	public void UpdateScadaG(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.UpdateScadaG(ctt, jo);
	}
	public void updateScadaL(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.updateScadaL(ctt, jo);
	}
	
	
	
	/**
	 * ���ܷ���
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//ִ�д洢����
	public void doTrace(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doTrace(ctt, jo);
	}
	//ɾ����ʷ��¼
	public void delHistory(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.delHistory(ctt, jo);
	}
	public void doPKG(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doPKG(ctt, jo);
	}
	public void getHistory(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getHistory(ctt, ja);
	}
	public void getResult(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getResult(ctt, ja);
	}
	public void getTip(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getTip(ctt, jo);
	}
	public void insertV(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.insertV(ctt, jo);
	}
	public void saveTrace(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.saveTrace(ctt, jo);
	}
	public void getGovernor(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGovernor(ctt, ja);
	}
	public void getPipe(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getPipe(ctt, ja);
	}
	public void getRiser(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getRiser(ctt, ja);
	}
	public void getSource(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getSource(ctt, ja);
	}
	public void getSource0(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getSource0(ctt, ja);
	}
	public void getValve(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getValve(ctt, ja);
	}
	//��ȡ���ܷ����������
	public void getAeResult(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getAeResult(ctt, jo);
	}
	
	
	/**
	 * ��ѹ��������
	 */
	public void doSamePress(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doSamePress(ctt, jo);
	}
	public void getIt(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getIt(ctt, ja);
	}
	public void getReslutGFR(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getReslutGFR(ctt, ja);
	}
	public void getReslutForRiser(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getReslutForRiser(ctt, ja);
	}
	public void getSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getSeq(ctt, jo);
	}
	
	
	/**
	 * ˮ������
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	//ɾ��һ���м�¼
	public void delGfDate(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.delGfDate(ctt, jo);
	}
	//����ˮ�������Ĵ洢����
	public void doGf(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doGf(ctt, jo);
	}
	//ͨ��fid��ѯgf���������
	public void getGfByFid(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfByFid(ctt, ja);
	}
	//����pid��ȡ���������
	public void getGfByPid(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfByPid(ctt, ja);
	}
	//��ȡ����
	public void getFxSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getFxSeq(ctt, jo);
	}
	//��ѯ����
	public void getGf(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGf(ctt, ja);
	}
	public void selhgp(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.selhgp(ctt, ja);
	}
	//��ȡ��ѯ����ʷ��¼
	public void getGfHis(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfHis(ctt, ja);
	}
	//��������
	public void setGf(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.setGf(ctt, jo);
	}
	//����fid�޸�GF����---USETYPE
	public void updateGfByFid(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.updateGfByFid(ctt, jo);
	}
	//��ȡˮ�����������Ϣ
	public void getInformation(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getInformation(ctt, ja);
	}
	//��ȡ�������������
	public void getftpnt(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getftpnt(ctt, ja);
	}
	//�����¶�
	public void updateGfByPid(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.updateGfByPid(ctt, jo);
	}
	//ˮ���������ι����š�����
	public void getGfIn(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfIn(ctt, ja);
	}
	//ˮ������������ι�������
	public void getLoopHighLight(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getLoopHighLight(ctt, ja);
	}
	//��ȡˮ������������Ϣ
	public void getGF2Att(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGF2Att(ctt, ja);
	}
	//ˮ�������޸ĵ�����Ϣ
	public void editGA(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.editGA(ctt, jo);
	}
	//���ѹ����
	public void getMinPress(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getMinPress(ctt, ja);
	}
	//����ѡ��Ļ���
	public void setLoopLine(CxSvrContext ctt, JSONObject jo, int major, int minor) throws Exception {
		hkct.setLoopLine(ctt, jo, major, minor);
	}
	//��ʾ��ѡ�еĻ���
	public void getLoopLines(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getLoopLines(ctt, ja);
	}
	//ɾ��ѡ�еĻ���
	public void delLoopLine(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.delLoopLine(ctt, jo);
	}
	//ˮ��������������
	public void copyGF(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.copyGF(ctt, jo);
	}
	//�޸��¶ȡ�΢���ʡ���������
	public void edittna(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.edittna(ctt, jo);
	}
	//��ȡ��������
	public void getGasType(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGasType(ctt, ja);
	}
	
	/**
	 * ���Կռ��ѯ
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//��ȡ���������
	public void getGGAS(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGGAS(ctt, ja);
	}
	//�������οռ�����
	public void setGGAS(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.setGGAS(ctt, jo);
	}
	//ȡ���Ѵ�����������
	public void getGGASName(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGGASName(ctt, ja);
	}
	//��ȡ���к�PID
	public void getHpSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getHpSeq(ctt, jo);
	}
	//ִ��PKG_HKCT.HKCT_EXP(pid)��ȡ�㡢������
	public void getExpPntLin(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getExpPntLin(ctt, jo);
	}
	//��ȡ���еĶ���η�Χ�㡢������
	public void getExpPntLinOld(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getExpPntLinOld(ctt, jo);
	}
	//��ȡ��Χ��������ҳ����
	public void getExpPntByPage(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getExpPntByPage(ctt, ja);
	}
	//�߷�ҳ����
	public void getExpLinByPage(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getExpLinByPage(ctt, ja);
	}
	//����������
	public void doAnaTQL(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doAnaTQL(ctt, jo);
	} 

	
	
	/**
	 * ����������
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	public void ScadaSD(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.ScadaSD(ctt, jo);
	}
	public void getSdDate(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getSdDate(ctt, ja);
	}
	public void getSd_point(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getSd_point(ctt, ja);
	}
	public void getSd_AllPoint(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getSd_AllPoint(ctt, ja);
	}
	public void getSdByDate(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getSdByDate(ctt, jo);
	}
	public void getSDFid(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getSDFid(ctt, ja);
	}
	public void getSD(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getSD(ctt, jo);
	}
	
	
	/**
	 * ����
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void getcomtday(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getcomtday(ctt, ja);
	}
	//���Ե���excel
	public void reportExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception {
		hkct.reportExcel(ctt, jo, res);
	}
	//��ȡ����TXT�ı�
	public void readTxt(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.readTxt(ctt, jo);
	}
	//��ȡ���ܷ������б�����Ϣ
	public void getAEAllResult(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getAEAllResult(ctt, ja);
	}
	
	//���ܷ�������excel����
	public void reportAeExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception {
		hkct.reportAeExcel(ctt, jo, res);
	}
	
}
