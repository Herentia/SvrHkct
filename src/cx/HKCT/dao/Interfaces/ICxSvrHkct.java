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
	 * tms条件查询结果
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void gettmsgis(CxSvrContext ctt, JSONArray ja) throws Exception;
	//tms关联设备
	public void addtms(CxSvrContext ctt, JSONObject jo) throws Exception;
	//tms删除关联设备
	public void deltms(CxSvrContext ctt, JSONObject jo) throws Exception;
	//tms---分页
	public void getTmsByPage(CxSvrContext ctt, JSONArray ja) throws Exception;
	//tms根据设备编码查询已关联设备
	public void getTmsByCode(CxSvrContext ctt, JSONArray ja) throws Exception;
	//tms显示查询结果中的已关联结果
	public void getTmsByReady(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	
	/**
	 * 爆管分析
	 */
	//执行爆管分析的存储过程
	public void doTrace(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//删除历史记录
	public void delHistory(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//用户保存历史记录时先调用此存储过程
	public void doPKG(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//获取爆管分析历史记录
	public void getHistory(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//通过sql获取相应数据
	public void getResult(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取分析序列号
	public void getTip(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//二次关阀，需要将关闭阀门插入HKCT_TRACE_INVALIDVALVE再进行分析
	public void insertV(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//保存爆管结果
	public void saveTrace(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//分析结果-------调压器表
	public void getGovernor(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取分析结果中的影响管线表
	public void getPipe(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//分析结果-----立管表
	public void getRiser(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//分析结果------气源表
	public void getSource(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取气源前没有阀门的气源
	public void getSource0(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//分析结果-----阀门表
	public void getValve(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取爆管分析所有分析结果
	public void getAeResult(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	/**
	 * 调压器找立管
	 */
	//调用查找同压力立管的存储过程-----HKCT_GOV_FIND_RISER(govid in number,findType in number,p in number)
	public void doSamePress(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//通过前台传过来的sql获取立管结果
	public void getIt(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//通过pid获取结果数据
	public void getReslutGFR(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//通过pid从查询结果中获取立管
	public void getReslutForRiser(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取序列号-----pid
	public void getSeq(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	
	
	/**
	 * 流量计---调压器配置
	 */
	//删除
	public void del(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//从无环路结果表中获取选中的网路的netid
	public void getScada_flow(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//获取环路表的数据
	public void getScada_net(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取无环路高亮图层的线
	public void getScada_pipe(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//通过选中的线和该线一端的点获取另外一端的点
	public void getPnt(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//获取无环路高亮管点的ID
	public void getScada_point(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//流量计配置表
	public void getScada(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取未监控管线ID
	public void getUnScada_pipe(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//获取有效流量计信息
	public void getFlow(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//获取有效调压器信息
	public void getGov(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//根据不同类型获取数据
	public void getResultByType(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//调用PKG_HKCT_SCADA.HKCT_SCADA_DO(pid)存储过程
	public void scadaDo(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//调用检查调压器存储过程
	public void scadaDoGov(CxSvrContext ctt, JSONArray ja) throws Exception;
	
	//修改调压器数据
	public void UpdateScadaG(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	//修改流量计的进气点、管线、出气点
	public void updateScadaL(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	
	
	
	/**
	 * 水利分析
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//删除一行数据记录
	public void delGfDate(CxSvrContext ctt, JSONObject jo) throws Exception;
	//调用水利分析的存储过程
	public void doGf(CxSvrContext ctt, JSONObject jo) throws Exception;
	//通过fid查询gf分析结果表
	public void getGfByFid(CxSvrContext ctt, JSONArray ja) throws Exception;
	//根据pid查询分析参数表
	public void getGfByPid(CxSvrContext ctt, JSONArray ja) throws Exception;
	//获取序列
	public void getFxSeq(CxSvrContext ctt, JSONObject jo) throws Exception;
	//查询数据
	public void getGf(CxSvrContext ctt, JSONArray ja) throws Exception;
	public void selhgp(CxSvrContext ctt, JSONArray ja) throws Exception;
	//获取查询的历史记录
	public void getGfHis(CxSvrContext ctt, JSONArray ja) throws Exception;
	//插入数据、
	public void setGf(CxSvrContext ctt, JSONObject jo) throws Exception;
	//根据fid修改GF数据---usetype0 供气不要流量，只要压力
	public void updateGfByFid(CxSvrContext ctt, JSONObject jo) throws Exception;
	//获取水利分析后的信息
	public void getInformation(CxSvrContext ctt, JSONArray ja) throws Exception;
	//查询供用气点的坐标
	public void getftpnt(CxSvrContext ctt, JSONArray ja) throws Exception;
	//更新温度
	public void updateGfByPid(CxSvrContext ctt, JSONObject jo) throws Exception;
	//水利分析环形管网号、长度
	public void getGfIn(CxSvrContext ctt, JSONArray ja) throws Exception;
	//水利分析结果环形管网高亮显示
	public void getLoopHighLight(CxSvrContext ctt, JSONArray ja) throws Exception;
	//GF_LOOP2
	public void getgfloop(CxSvrContext ctt, JSONArray ja) throws Exception;
	//调试信息
	public void getGF2Att(CxSvrContext ctt, JSONArray ja) throws Exception;
	//修改调试信息
	public void editGA(CxSvrContext ctt, JSONObject jo) throws Exception;
	//最低压力点
	public void getMinPress(CxSvrContext ctt, JSONArray ja) throws Exception;
	//插入选择的环线
	public void setLoopLine(CxSvrContext ctt, JSONObject jo, int major, int minor) throws Exception;
	//显示已选中的环线
	public void getLoopLines(CxSvrContext ctt, JSONArray ja) throws Exception;
	//删除选中的环线
	public void delLoopLine(CxSvrContext ctt, JSONObject jo) throws Exception;
	//复制数据
	public void copyGF(CxSvrContext ctt, JSONObject jo) throws Exception;
	//修改温度、微调率、分析名称
	public void edittna(CxSvrContext ctt, JSONObject jo) throws Exception;
	//获取气体类型
	public void getGasType(CxSvrContext ctt, JSONArray ja) throws Exception;
	//调气量分析
	public void doAnaTQL(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	/**
	 * 属性空间分析
	 */
	//存入多边形空间数据
	public void setGGAS(CxSvrContext ctt, JSONObject jo) throws Exception;
	//获取多边形空间数据
	public void getGGAS(CxSvrContext ctt, JSONArray ja) throws Exception;
	//取出已存入多边形名称
	public void getGGASName(CxSvrContext ctt, JSONArray ja) throws Exception;
	//获取序列号pid
	public void getHpSeq(CxSvrContext ctt, JSONObject jo) throws Exception;
	//执行PKG_HKCT.HKCT_EXP(pid)获取点、线数据
	public void getExpPntLin(CxSvrContext ctt, JSONObject jo) throws Exception;
	//获取范围分析的点分页数据
	public void getExpPntByPage(CxSvrContext ctt, JSONArray ja) throws Exception;
	//获取范围分析的线分页数据
	public void getExpLinByPage(CxSvrContext ctt, JSONArray ja) throws Exception;
	//获取已有的多边形范围点、线数据
	public void getExpPntLinOld(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	
	

	/**
	 * 供用气分析
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	//调用供用气分析存储过程
	public void ScadaSD(CxSvrContext ctt, JSONObject jo) throws Exception;
	//获取下拉框时间
	public void getSdDate(CxSvrContext ctt, JSONArray ja) throws Exception;
	//供用气点高亮
	public void getSd_point(CxSvrContext ctt, JSONArray ja) throws Exception;
	//获取所有高亮流量计的位置
	public void getSd_AllPoint(CxSvrContext ctt, JSONArray ja) throws Exception;
	//通过时间获取相应的分析结果
	public void getSdByDate(CxSvrContext ctt, JSONObject jo) throws Exception;
	//选中一条记录，高亮该记录中的管线
	public void getSDFid(CxSvrContext ctt, JSONArray ja) throws Exception;
	//获取供用气分析结果
	public void getSD(CxSvrContext ctt, JSONObject jo) throws Exception;
	
	/**
	 * 测试
	 * @param ctt
	 * @param ja
	 * @throws Exception
	 */
	public void getcomtday(CxSvrContext ctt, JSONArray ja) throws Exception;
	//测试导出excel
	public void reportExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception;
	//读取本地txt文本
	public void readTxt(CxSvrContext ctt, JSONObject jo) throws Exception;
	//获取爆管分析所有报表信息
	public void getAEAllResult(CxSvrContext ctt, JSONArray ja) throws Exception;
	//爆管分析导出Excel报表
	public void reportAeExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception;
	
	
}
