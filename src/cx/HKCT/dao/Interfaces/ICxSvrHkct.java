package cx.HKCT.dao.Interfaces;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cx.dao.CxSvrContext;

public interface ICxSvrHkct
{
	public void getmaxid(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	public void gettmsgis(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	/**
	 * tms������ѯ���
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void gettmsgis(CxSvrContext ctt, JSONArray ja) throws Exception;
	//tms�����豸
	public void addtms(CxSvrContext ctt, JSONObject jo) throws Exception;
	//tmsɾ�������豸
	public void deltms(CxSvrContext ctt, JSONObject jo) throws Exception;
	//tms---��ҳ
	public void getTmsByPage(CxSvrContext ctt, JSONArray ja) throws Exception;
	//tms�����豸�����ѯ�ѹ����豸
	public void getTmsByCode(CxSvrContext ctt, JSONArray ja) throws Exception;
	//tms��ʾ��ѯ����е��ѹ������
	public void getTmsByReady(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	
	/**
	 * ���ܷ���
	 */
	//ִ�б��ܷ����Ĵ洢����
	public void doTrace(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//ɾ����ʷ��¼
	public void delHistory(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//�û�������ʷ��¼ʱ�ȵ��ô˴洢����
	public void doPKG(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//��ȡ���ܷ�����ʷ��¼
	public void getHistory(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//ͨ��sql��ȡ��Ӧ����
	public void getResult(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡ�������к�
	public void getTip(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//���ιط�����Ҫ���رշ��Ų���HKCT_TRACE_INVALIDVALVE�ٽ��з���
	public void insertV(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//���汬�ܽ��
	public void saveTrace(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//�������-------��ѹ����
	public void getGovernor(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡ��������е�Ӱ����߱�
	public void getPipe(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//�������-----���ܱ�
	public void getRiser(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//�������------��Դ��
	public void getSource(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡ��Դǰû�з��ŵ���Դ
	public void getSource0(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//�������-----���ű�
	public void getValve(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡ���ܷ������з������
	public void getAeResult(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	/**
	 * ��ѹ��������
	 */
	//���ò���ͬѹ�����ܵĴ洢����-----HKCT_GOV_FIND_RISER(govid in number,findType in number,p in number)
	public void doSamePress(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//ͨ��ǰ̨��������sql��ȡ���ܽ��
	public void getIt(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//ͨ��pid��ȡ�������
	public void getReslutGFR(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//ͨ��pid�Ӳ�ѯ����л�ȡ����
	public void getReslutForRiser(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡ���к�-----pid
	public void getSeq(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	
	
	/**
	 * ������---��ѹ������
	 */
	//ɾ��
	public void del(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//���޻�·������л�ȡѡ�е���·��netid
	public void getScada_flow(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//��ȡ��·�������
	public void getScada_net(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡ�޻�·����ͼ�����
	public void getScada_pipe(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//ͨ��ѡ�е��ߺ͸���һ�˵ĵ��ȡ����һ�˵ĵ�
	public void getPnt(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//��ȡ�޻�·�����ܵ��ID
	public void getScada_point(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//���������ñ�
	public void getScada(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡδ��ع���ID
	public void getUnScada_pipe(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//��ȡ��Ч��������Ϣ
	public void getFlow(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//��ȡ��Ч��ѹ����Ϣ
	public void getGov(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//���ݲ�ͬ���ͻ�ȡ����
	public void getResultByType(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//����PKG_HKCT_SCADA.HKCT_SCADA_DO(pid)�洢����
	public void scadaDo(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//���ü���ѹ���洢����
	public void scadaDoGov(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//�޸ĵ�ѹ������
	public void UpdateScadaG(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//�޸������ƵĽ����㡢���ߡ�������
	public void updateScadaL(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	
	
	
	/**
	 * ˮ������
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//ɾ��һ�����ݼ�¼
	public void delGfDate(CxSvrContext ctt, JSONObject jo) throws Exception;
	//����ˮ�������Ĵ洢����
	public void doGf(CxSvrContext ctt, JSONObject jo) throws Exception;
	//ͨ��fid��ѯgf���������
	public void getGfByFid(CxSvrContext ctt, JSONArray ja) throws Exception;
	//����pid��ѯ����������
	public void getGfByPid(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ȡ����
	public void getFxSeq(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��ѯ����
	public void getGf(CxSvrContext ctt, JSONArray ja) throws Exception;
	public void selhgp(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ȡ��ѯ����ʷ��¼
	public void getGfHis(CxSvrContext ctt, JSONArray ja) throws Exception;
	//�������ݡ�
	public void setGf(CxSvrContext ctt, JSONObject jo) throws Exception;
	//����fid�޸�GF����---usetype0 ������Ҫ������ֻҪѹ��
	public void updateGfByFid(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��ȡˮ�����������Ϣ
	public void getInformation(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ѯ�������������
	public void getftpnt(CxSvrContext ctt, JSONArray ja) throws Exception;
	//�����¶�
	public void updateGfByPid(CxSvrContext ctt, JSONObject jo) throws Exception;
	//ˮ���������ι����š�����
	public void getGfIn(CxSvrContext ctt, JSONArray ja) throws Exception;
	//ˮ������������ι���������ʾ
	public void getLoopHighLight(CxSvrContext ctt, JSONArray ja) throws Exception;
	//GF_LOOP2
	public void getgfloop(CxSvrContext ctt, JSONArray ja) throws Exception;
	//������Ϣ
	public void getGF2Att(CxSvrContext ctt, JSONArray ja) throws Exception;
	//�޸ĵ�����Ϣ
	public void editGA(CxSvrContext ctt, JSONObject jo) throws Exception;
	//���ѹ����
	public void getMinPress(CxSvrContext ctt, JSONArray ja) throws Exception;
	//����ѡ��Ļ���
	public void setLoopLine(CxSvrContext ctt, JSONObject jo, int major, int minor) throws Exception;
	//��ʾ��ѡ�еĻ���
	public void getLoopLines(CxSvrContext ctt, JSONArray ja) throws Exception;
	//ɾ��ѡ�еĻ���
	public void delLoopLine(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��������
	public void copyGF(CxSvrContext ctt, JSONObject jo) throws Exception;
	//�޸��¶ȡ�΢���ʡ���������
	public void edittna(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��ȡ��������
	public void getGasType(CxSvrContext ctt, JSONArray ja) throws Exception;
	//����������
	public void doAnaTQL(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	/**
	 * ���Կռ����
	 */
	//�������οռ�����
	public void setGGAS(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��ȡ����οռ�����
	public void getGGAS(CxSvrContext ctt, JSONArray ja) throws Exception;
	//ȡ���Ѵ�����������
	public void getGGASName(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ȡ���к�pid
	public void getHpSeq(CxSvrContext ctt, JSONObject jo) throws Exception;
	//ִ��PKG_HKCT.HKCT_EXP(pid)��ȡ�㡢������
	public void getExpPntLin(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��ȡ��Χ�����ĵ��ҳ����
	public void getExpPntByPage(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ȡ��Χ�������߷�ҳ����
	public void getExpLinByPage(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ȡ���еĶ���η�Χ�㡢������
	public void getExpPntLinOld(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	
	

	/**
	 * ����������
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//���ù����������洢����
	public void ScadaSD(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��ȡ������ʱ��
	public void getSdDate(CxSvrContext ctt, JSONArray ja) throws Exception;
	//�����������
	public void getSd_point(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ȡ���и��������Ƶ�λ��
	public void getSd_AllPoint(CxSvrContext ctt, JSONArray ja) throws Exception;
	//ͨ��ʱ���ȡ��Ӧ�ķ������
	public void getSdByDate(CxSvrContext ctt, JSONObject jo) throws Exception;
	//ѡ��һ����¼�������ü�¼�еĹ���
	public void getSDFid(CxSvrContext ctt, JSONArray ja) throws Exception;
	//��ȡ�������������
	public void getSD(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	/**
	 * ����
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void getcomtday(CxSvrContext ctt, JSONArray ja) throws Exception;
	//���Ե���excel
	public void reportExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception;
	//��ȡ����txt�ı�
	public void readTxt(CxSvrContext ctt, JSONObject jo) throws Exception;
	//��ȡ���ܷ������б�����Ϣ
	public void getAEAllResult(CxSvrContext ctt, JSONArray ja) throws Exception;
	//���ܷ�������Excel����
	public void reportAeExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception;
	
	
}
