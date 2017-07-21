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
	 * 获取水利分析单次分析的管网环号信息
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	public void getgfloop(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getgfloop(ctt, ja);
	}
	
	/**
	 * 获取tms条件查询结果
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void gettmsgis(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.gettmsgis(ctt, ja);
	}
	
	/**
	 * tms关联设备
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	public void addtms(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.addtms(ctt, jo);
	}
	
	/**
	 * tms接口删除关联设备
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
	 * 流量计---调压器配置
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
	 * 爆管分析
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//执行存储过程
	public void doTrace(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doTrace(ctt, jo);
	}
	//删除历史记录
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
	//获取爆管分析结果数据
	public void getAeResult(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getAeResult(ctt, jo);
	}
	
	
	/**
	 * 调压器找立管
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
	 * 水利分析
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	//删除一条行记录
	public void delGfDate(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.delGfDate(ctt, jo);
	}
	//调用水利分析的存储过程
	public void doGf(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doGf(ctt, jo);
	}
	//通过fid查询gf分析结果表
	public void getGfByFid(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfByFid(ctt, ja);
	}
	//根据pid获取分析结果表
	public void getGfByPid(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfByPid(ctt, ja);
	}
	//获取序列
	public void getFxSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getFxSeq(ctt, jo);
	}
	//查询数据
	public void getGf(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGf(ctt, ja);
	}
	public void selhgp(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.selhgp(ctt, ja);
	}
	//获取查询的历史记录
	public void getGfHis(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfHis(ctt, ja);
	}
	//插入数据
	public void setGf(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.setGf(ctt, jo);
	}
	//根据fid修改GF数据---USETYPE
	public void updateGfByFid(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.updateGfByFid(ctt, jo);
	}
	//获取水利分析后的信息
	public void getInformation(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getInformation(ctt, ja);
	}
	//获取供用气点的坐标
	public void getftpnt(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getftpnt(ctt, ja);
	}
	//更新温度
	public void updateGfByPid(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.updateGfByPid(ctt, jo);
	}
	//水利分析环形管网号、长度
	public void getGfIn(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGfIn(ctt, ja);
	}
	//水利分析结果环形管网高亮
	public void getLoopHighLight(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getLoopHighLight(ctt, ja);
	}
	//获取水利分析调试信息
	public void getGF2Att(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGF2Att(ctt, ja);
	}
	//水利分析修改调试信息
	public void editGA(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.editGA(ctt, jo);
	}
	//最低压力点
	public void getMinPress(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getMinPress(ctt, ja);
	}
	//插入选择的环线
	public void setLoopLine(CxSvrContext ctt, JSONObject jo, int major, int minor) throws Exception {
		hkct.setLoopLine(ctt, jo, major, minor);
	}
	//显示已选中的环线
	public void getLoopLines(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getLoopLines(ctt, ja);
	}
	//删除选中的环线
	public void delLoopLine(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.delLoopLine(ctt, jo);
	}
	//水利分析拷贝数据
	public void copyGF(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.copyGF(ctt, jo);
	}
	//修改温度、微调率、分析名称
	public void edittna(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.edittna(ctt, jo);
	}
	//获取气体类型
	public void getGasType(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGasType(ctt, ja);
	}
	
	/**
	 * 属性空间查询
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//获取多边形坐标
	public void getGGAS(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGGAS(ctt, ja);
	}
	//存入多边形空间数据
	public void setGGAS(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.setGGAS(ctt, jo);
	}
	//取出已存入多边形名称
	public void getGGASName(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getGGASName(ctt, ja);
	}
	//获取序列号PID
	public void getHpSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getHpSeq(ctt, jo);
	}
	//执行PKG_HKCT.HKCT_EXP(pid)获取点、线数据
	public void getExpPntLin(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getExpPntLin(ctt, jo);
	}
	//获取已有的多边形范围点、线数据
	public void getExpPntLinOld(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.getExpPntLinOld(ctt, jo);
	}
	//获取范围分析后点分页数据
	public void getExpPntByPage(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getExpPntByPage(ctt, ja);
	}
	//线分页数据
	public void getExpLinByPage(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getExpLinByPage(ctt, ja);
	}
	//调气量分析
	public void doAnaTQL(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.doAnaTQL(ctt, jo);
	} 

	
	
	/**
	 * 供用气分析
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
	 * 测试
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void getcomtday(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getcomtday(ctt, ja);
	}
	//测试导出excel
	public void reportExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception {
		hkct.reportExcel(ctt, jo, res);
	}
	//读取本地TXT文本
	public void readTxt(CxSvrContext ctt, JSONObject jo) throws Exception {
		hkct.readTxt(ctt, jo);
	}
	//获取爆管分析所有报表信息
	public void getAEAllResult(CxSvrContext ctt, JSONArray ja) throws Exception {
		hkct.getAEAllResult(ctt, ja);
	}
	
	//爆管分析导出excel报表
	public void reportAeExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception {
		hkct.reportAeExcel(ctt, jo, res);
	}
	
}
