package cx.HKCT.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cx.dao.CxSvrVariable;
import cx.HKCT.dao.CxSvrHkct;
import cx.dao.CxSvrContext;


public class HkctSvr extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain;charset=UTF-8");

		String				cmd = req.getParameter("cmd");
		int					major = Integer.parseInt(req.getParameter("_major"));
		int					minor = Integer.parseInt(req.getParameter("_minor"));

		//CxSvrContext		ctt = new CxSvrContext(CxSvrVariable.dataMan, req, major, minor);
		CxSvrContext		ctt = new CxSvrContext(CxSvrVariable.dataMan, req, major, minor);
								//ctt.dataMan.dsMan.get("DbScada").dbo
		CxSvrHkct		    obj = new CxSvrHkct();
		
		JSONObject			jo = new JSONObject();
		JSONArray			ja = new JSONArray();
		boolean				isArray = false;

		try
		{
			switch(cmd)
			{
			case "getmax":
				{
					isArray = false;
					obj.getmax(ctt, jo);
				}
				break;
			case "gettms":
				{
					isArray = false;
					obj.gettms(ctt, jo);
				}
				break;
			case "getgfloop":
				{
					isArray = true;
					obj.getgfloop(ctt, ja);
				}
				break;
			case "gettmsgis":
				{
					isArray = true;
					obj.gettmsgis(ctt, ja);
				}
				break;
			case "addtms":
				{
					isArray = false;
					obj.addtms(ctt, jo);
				}
				break;
			case "deltms":
				{
					isArray = false;
					obj.deltms(ctt, jo);
				}
				break;
			case "gettmsbypage":
				{
					isArray = true;
					obj.getTmsByPage(ctt, ja);
				}
				break;
			case "getTmsByCode":
				{
					isArray = true;
					obj.getTmsByCode(ctt, ja);
				}
				break;
			case "getTmsByReady":
				{
					isArray = true;
					obj.getTmsByReady(ctt, ja);
				}
				break;
			case "trace":
				{
					isArray = false;
					obj.doTrace(ctt, jo);
				}
				break;
			case "DelHistory":
				{
					isArray = false;
					obj.delHistory(ctt, jo);
				}
				break;
			case "doPkg":
				{
					isArray = false;
					obj.doPKG(ctt, jo);
				}
				break;
			case "getHis":
				{
					isArray = true;
					obj.getHistory(ctt, ja);
				}
				break;
			case "getResult":
				{
					isArray = true;
					obj.getResult(ctt, ja);
				}
				break;
			case "getTip":
				{
					isArray = false;
					obj.getTip(ctt, jo);
				}
				break;
			case "insertV":
				{
					isArray = false;
					obj.insertV(ctt, jo);
				}
				break;
			case "SaveTrace":
				{
					isArray = false;
					obj.saveTrace(ctt, jo);
				}
				break;
			case "tg":
				{
					isArray = true;
					obj.getGovernor(ctt, ja);
				}
				break;
			case "tp":
				{
					isArray = true;
					obj.getPipe(ctt, ja);
				}
				break;
			case "tr":
				{
					isArray = true;
					obj.getRiser(ctt, ja);
				}
				break;
			case "ts":
				{
					isArray = true;
					obj.getSource(ctt, ja);
				}
				break;
			case "ts0":
				{
					isArray = true;
					obj.getSource0(ctt, ja);
				}
				break;
			case "tv":
				{
					isArray = true;
					obj.getValve(ctt, ja);
				}
				break;
			case "getAe":
				{
					isArray = false;
					obj.getAeResult(ctt, jo);
				}
				break;
			case "findSamePressR":
				{
					isArray = false;
					obj.doSamePress(ctt, jo);
				}
				break;
			case "getIt":
				{
					isArray = true;
					obj.getIt(ctt, ja);
				}
				break;
			case "getReslutGFR":
				{
					isArray = true;
					obj.getReslutGFR(ctt, ja);
				}
				break;
			case "getRiser":
				{
					isArray = true;
					obj.getReslutForRiser(ctt, ja);
				}
				break;
			case "getSeq":
				{
					isArray = false;
					obj.getSeq(ctt, jo);
				}
				break;
			case "getGF2Att":
				{
					isArray = true;
					obj.getGF2Att(ctt, ja);
				}
				break;
			case "DelScada":
				{
					isArray = false;
					obj.del(ctt, jo);
				}
				break;
			case "GetScada_flow":
				{
					isArray = false;
					obj.getScada_flow(ctt, jo);
				}
				break;
			case "GetScada_net":
				{
					isArray = true;
					obj.getScada_net(ctt, ja);
				}
				break;
			case "GetScada_pipe":
				{
					isArray  = true;
					obj.getScada_pipe(ctt, ja);
				}
				break;
			case "GetScada_Pnt":
				{
					isArray = false;
					obj.getPnt(ctt, jo);
				}
				break;
			case "GetScada_point":
				{
					isArray = true;
					obj.getScada_point(ctt, ja);
				}
				break;
			case "GetScada":
				{
					isArray = true;
					obj.getScada(ctt, ja);
				}
				break;
			case "GetUnScada_pipe":
				{
					isArray = true;
					obj.getUnScada_pipe(ctt, ja);
				}
				break;
			case "Scada_Flow":
				{
					isArray = false;
					obj.getFlow(ctt, jo);
				}
				break;
			case "Scada_Gov":
				{
					isArray = false;
					obj.getGov(ctt, jo);
				}
				break;
			case "ScadaByPttype":
				{
					isArray = true;
					obj.getResultByType(ctt, ja);
				}
				break;
			case "ScadaDo":
				{
					isArray = false;
					obj.scadaDo(ctt, jo);
				}
				break;
			case "ScadaDoGov":
				{
					isArray = true;
					obj.scadaDoGov(ctt, ja);
				}
				break;
			case "UpdateScadaG":
				{
					isArray = false;
					obj.UpdateScadaG(ctt, jo);
				}
				break;
			case "UpdateScadaL":
				{
					isArray = false;
					obj.updateScadaL(ctt, jo);
				}
				break;
			case "editGA":
				{
					isArray = false;
					obj.editGA(ctt, jo);
				}
				break;
			case "getMinPress":
				{
					isArray = true;
					obj.getMinPress(ctt, ja);
				}
				break;
			case "setLoopLine":
				{
					isArray = false;
					obj.setLoopLine(ctt, jo, major, minor);
				}
				break;
			case "getLoopLines":
				{
					isArray = true;
					obj.getLoopLines(ctt, ja);
				}
				break;
			case "delLoopLine":
				{
					isArray = false;
					obj.delLoopLine(ctt, jo);
				}
				break;
			case "copyGF":
				{
					isArray = false;
					obj.copyGF(ctt, jo);
				}
				break;
			case "edittna":
				{
					isArray = false;
					obj.edittna(ctt, jo);
				}
				break;
			case "getGGAS":
				{
					isArray = true;
					obj.getGGAS(ctt, ja);
				}
				break;
			case "setGGAS":
				{
					isArray = false;
					obj.setGGAS(ctt, jo);
				}
				break;
			case "getGGASName":
				{
					isArray = true;
					obj.getGGASName(ctt, ja);
				}
				break;
			case "DoScadaSD":
				{
					isArray = false;
					obj.ScadaSD(ctt, jo);
				}
				break;
			case "GetDate_SD":
				{
					isArray = true;
					obj.getSdDate(ctt, ja);
				}
				break;
			case "GetSd_Point":
				{
					isArray = true;
					obj.getSd_point(ctt, ja);
				}
				break;
			case "GetSdAllPoint":
				{
					isArray = true;
					obj.getSd_AllPoint(ctt, ja);
				}
				break;
			case "GetSdByDate":
				{
					isArray = false;
					obj.getSdByDate(ctt, jo);
				}
				break;
			case "GetSdByNetId":
				{
					isArray = true;
					obj.getSDFid(ctt, ja);
				}
				break;
			case "SdAnls":
				{
					isArray = false;
					obj.getSD(ctt, jo);
				}
				break;
			case "delGf":
				{
					isArray = false;
					obj.delGfDate(ctt, jo);
				}
				break;
			case "doGf":
				{
					isArray = false;
					obj.doGf(ctt, jo);
				}
				break;
			case "getGfFid":
				{
					isArray = true;
					obj.getGfByFid(ctt, ja);
				}
				break;
			case "getGfPid":
				{
					isArray = true;
					obj.getGfByPid(ctt, ja);
				}
				break;
			case "getGfSeq":
				{
					isArray = false;
					obj.getFxSeq(ctt, jo);
				}
				break;
			case "getGf":
				{
					isArray = true;
					obj.getGf(ctt, ja);
				}
				break;
			case "hgp":
				{
					isArray = true;
					obj.selhgp(ctt, ja);
				}
				break;
			case "getGfHis":
				{
					isArray = true;
					obj.getGfHis(ctt, ja);
				}
				break;
			case "setGf":
				{
					isArray = false;
					obj.setGf(ctt, jo);
				}
				break;
			case "updateGfFid":
				{
					isArray = false;
					obj.updateGfByFid(ctt, jo);
				}
				break;
			case "getInformation":
				{
					isArray = true;
					obj.getInformation(ctt, ja);
				}
				break;
			case "getftpnt":
				{
					isArray = true;
					obj.getftpnt(ctt, ja);
				}
				break;
			case "updateByPid":
				{
					isArray = false;
					obj.updateGfByPid(ctt, jo);
				}
				break;
			case "GfIn":
				{
					isArray = true;
					obj.getGfIn(ctt, ja);
				}
				break;
			case "GfLoop":
				{
					isArray = true;
					obj.getLoopHighLight(ctt, ja);
				}
				break;
			case "getGas":
				{
					isArray = true;
					obj.getGasType(ctt, ja);
				}
				break;
			case "getcomtday":
				{
					isArray = true;
					obj.getcomtday(ctt, ja);
				}
				break;
			case "reportExcel":
				{
					isArray = false;
					obj.reportExcel(ctt, jo, res);
				}
				break;
			case "getHpSeq":
				{
					isArray = false;
					obj.getHpSeq(ctt, jo);
				}
				break;
			case "getExpPntLin":
				{
					isArray = false;
					obj.getExpPntLin(ctt, jo);
				}
				break;
			case "getExpPntByPage":
				{
					isArray = true;
					obj.getExpPntByPage(ctt, ja);
				}
				break;
			case "getExpLinByPage":
				{
					isArray = true;
					obj.getExpLinByPage(ctt, ja);
				}
				break;
			case "doAnaTQL":
				{
					isArray = false;
					obj.doAnaTQL(ctt, jo);
				}
				break;
			case "ExpPntLinOl":
				{
					isArray = false;
					obj.getExpPntLinOld(ctt, jo);
				}
				break;
			case "readTxt":
				{
					isArray = false;
					obj.readTxt(ctt, jo);
				}
				break;
			case "getAEAllResult":
				{
					isArray = true;
					obj.getAEAllResult(ctt, ja);
				}
				break;
			case "reportAeExcel":
				{
					isArray = false;
					obj.reportAeExcel(ctt, jo, res);
				}
				break;
			}
		}
		catch(Exception e)
		{
			isArray = false;
			jo.put("ret", 1);
			jo.put("msg",e.getMessage());
		}
		finally
		{
			ctt.closeConn();

			if(isArray)
			{
				res.getWriter().print(ja.toString());
				res.getWriter().close();
			}
			else
			{
				res.getWriter().print(jo.toString());
				res.getWriter().close();
			}
		}
	}
}

