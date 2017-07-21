package cx.HKCT.dao.oracle;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import cx.HKCT.dao.Interfaces.ICxSvrHkct;
import cx.dao.CxSvrContext;

public class CxSvrHkctOra implements ICxSvrHkct {
	
	public CxSvrHkctOra() {
		
	}
	
	public void getmaxid(CxSvrContext ctt, JSONObject jo) throws Exception {
		String				sql = "select max(id) from "+ctt.ds.dbo+".pnt where minor = ?";
		PreparedStatement	ps = null;
		ResultSet			rs = null;
		int					userid = Integer.parseInt(ctt.req.getParameter("userid"));
		int					minor = Integer.parseInt(ctt.req.getParameter("_minor"));
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, minor);
			rs = ps.executeQuery();
					
			if(rs.next()) {
				jo.put("max", rs.getInt(1));
			}		
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void gettmsgis(CxSvrContext ctt, JSONObject jo) throws Exception {
		String				sql = "select * from "+ctt.ds.dbo+".pnt where id = ?";
		PreparedStatement	ps = null;
		ResultSet			rs = null;
		int              	fid = Integer.parseInt(ctt.req.getParameter("fid"));
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, fid);
			rs = ps.executeQuery();
						
			if(rs.next()) {
				jo.put("ID", rs.getInt(1));
				jo.put("minor", rs.getInt(2));
			}
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	/**
	 * 获取水利分析单次分析的管网环号信息
	 */
	@Override
	public void getgfloop(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "Select loopid 环号, sum(leng) 周长 from HKCT_GF_LOOP2 "
				+ "where ftype = ? and pid = ? group by loopid order by loopid asc";
		PreparedStatement ps = null;
		ResultSet rs     	 = null;
		int ftype 			 = Integer.parseInt(ctt.req.getParameter("ftype"));
		int pid 			 = Integer.parseInt(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, ftype);
			ps.setInt(2, pid);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				JSONObject	jo1 = new JSONObject();
				jo1.put("周长", rs.getInt(2));
				jo1.put("环号", rs.getInt(1));
				ja.add(jo1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	
	/**
	 * tms条件查询结果
	 */
	@Override
	public void gettmsgis(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select * from HKCT_TMS_GIS where ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String str = ctt.req.getParameter("sql");
		sql = sql + str;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("TMS_EQUIP_CODE", rs.getString(1));
				jo.put("TMS_EQUIP_NAME", rs.getString(2));
				jo.put("TMS_PK_CATEGORY", rs.getString(3));
				jo.put("TMS_status", rs.getString(4));
				jo.put("TMS_PK_LOCATION", rs.getString(5));
				jo.put("TMS_pk_jobmngfil", rs.getString(6));
				jo.put("freeitem1", rs.getString(7));
				jo.put("freeitem2", rs.getString(8));
				jo.put("freeitem3", rs.getString(9));
				jo.put("freeitem4", rs.getString(10));
				jo.put("freeitem5", rs.getString(11));
				jo.put("freeitem6", rs.getString(12));
				jo.put("freeitem7", rs.getString(13));
				jo.put("freeitem8", rs.getString(14));
				jo.put("freeitem9", rs.getString(15));
				jo.put("freeitem10", rs.getString(16));
				jo.put("TMS_TYPENAME", rs.getString(17));
				if(rs.getDate(18) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(rs.getDate(18));
					jo.put("updatedate", date);
				}
				jo.put("updateuser", rs.getString(19));
				ja.add(jo);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
		
		
	}

	/**
	 * tms关联设备---插入关联设备维码，主、次类型
	 */
	@Override
	public void addtms(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "insert into hkct_tms_relation (gisfid, gismajor, gisminor, tms_equip_code, editdate, edituser) ";
		PreparedStatement ps = null;
		String gisfid = ctt.req.getParameter("gisfid");
		String gismajor = ctt.req.getParameter("gismajor");
		String gisminor = ctt.req.getParameter("gisminor");
		String tms_equip_code = ctt.req.getParameter("tms_equip_code");
		String editdate = ctt.req.getParameter("editdate");
		String edituser = ctt.req.getParameter("edituser");
		
		String str = "";
		String [] fid = gisfid.split(",");
		String [] majors = gismajor.split(",");
		String [] minors = gisminor.split(",");
		
		Long[] intfid = new Long[fid.length];
		for(int i = 0; i < fid.length; i++){
			intfid[i] = Long.parseLong(fid[i]);
		}
		int[] intmajor = new int[majors.length];
		for(int i = 0; i < fid.length; i++){
			intmajor[i] = Integer.parseInt(majors[i]);
		}
		int[] intminor = new int[minors.length];
		for(int i = 0; i < fid.length; i++){
			intminor[i] = Integer.parseInt(minors[i]);
		}
		
		//select '1' from dual union select '2' from dual 
		
		for(int j = 0; j < intfid.length; j++) {
			str += "select " + intfid[j] + "," + intmajor[j] + "," + intminor[j] + "," + "'" + tms_equip_code + "'" + "," +
		"to_date(" + "'" + editdate + "'" + "," + "'" + "yyyy-MM-dd" + "'" + ")" + "," + "'" + edituser + "'" + " from dual" + " union ";
		}
		
		String sql1 = str.substring(0, str.length() - 7);
		
		sql = sql + sql1;
		try {
			ps = ctt.conn.prepareStatement(sql);
			int count = ps.executeUpdate();
			
			jo.put("ret", count + 1);
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	/**
	 * tms删除关联设备
	 */
	@Override
	public void deltms(CxSvrContext ctt, JSONObject jo) throws Exception {
		
		String sql = "delete from HKCT_TMS_RELATION where tms_equip_code = ? and gisfid in ";
		PreparedStatement ps = null;
		String equip_code = ctt.req.getParameter("code");
		String gisfid = ctt.req.getParameter("gisfid");
		String [] result = gisfid.split(",");
		String id = "";
		int[] intTemp = new int[result.length];
		for(int i = 0; i < result.length; i++){
			intTemp[i] = Integer.parseInt(result[i]);
		}
		for(int j = 0; j < intTemp.length; j++) {
			 id += intTemp[j] + ",";
		}
		String ids = id.substring(0, id.length() - 1);
		sql = sql + "(" + ids +")";
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, equip_code);
			
			int count = ps.executeUpdate();
			jo.put("ret", count + 1);
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}
	
	/**
	 * tms-----分页
	 */
	
	@Override
	public void getTmsByPage(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql            = "select TMS_TYPENAME 设备类型  ,TMS_EQUIP_NAME 设备名称,TMS_PK_LOCATION 位置,"
				+ "TMS_EQUIP_CODE 设备编码,TMS_pk_jobmngfil 项目编码,TMS_status 设备状态, TECHVALUE1 属性1,"
				+ "TECHVALUE2 属性2,TECHVALUE3 属性3,TECHVALUE4 属性4,TECHVALUE5 属性5,freeitem1 技术参数1,"
				+ "freeitem2 技术参数2,freeitem3 技术参数3,freeitem4 技术参数4,freeitem5 技术参数5,tms_typename 类型名称, updatedate 修改时间, updateuser 修改人 "
				+ "from (select t.*, rownum rn from (select * from HKCT_TMS_GIS where ";
		String sql1           = "select count(*) from HKCT_TMS_GIS where ";
		PreparedStatement ps  = null;
		PreparedStatement ps1 = null;
		ResultSet rs          = null;
		ResultSet rs1  		  = null;
		String str            = ctt.req.getParameter("sql");
		sql                   = sql + str + ") t where rownum <= ?) where rn > ?";
		sql1				  = sql1 + str;
		int pagenum           = Integer.parseInt(ctt.req.getParameter("pagenum"));
		int pagerows          = Integer.parseInt(ctt.req.getParameter("pagerows"));
		JSONObject jo1		  = new JSONObject();
		JSONObject jo2        = new JSONObject();
		JSONArray ja1		  = new JSONArray();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps1 = ctt.conn.prepareStatement(sql1);
			ps.setInt(1, pagenum * pagerows);
			ps.setInt(2, (pagenum - 1) * pagerows);
			rs = ps.executeQuery();
			rs1 = ps1.executeQuery();
			rs1.next();
			int count = rs1.getInt(1);
			//jo1.put("total", count);
			
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("设备类型", rs.getString(1));
				jo.put("设备名称", rs.getString(2));
				jo.put("位置", rs.getString(3));
				jo.put("设备编码", rs.getString(4));
				jo.put("项目编码", rs.getString(5));
				jo.put("设备状态", rs.getString(6));
				jo.put("属性1", rs.getString(7));
				jo.put("属性2", rs.getString(8));
				jo.put("属性3", rs.getString(9));
				jo.put("属性4", rs.getString(10));
				jo.put("属性5", rs.getString(11));
				jo.put("技术参数1", rs.getString(12));
				jo.put("技术参数2", rs.getString(13));
				jo.put("技术参数3", rs.getString(14));
				jo.put("技术参数4", rs.getString(15));
				jo.put("技术参数5", rs.getString(16));
				jo.put("类型名称", rs.getString(17));
				if(rs.getDate(18) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(rs.getDate(18));
					jo.put("修改时间", date);
				} else {
					jo.put("修改时间", "null");
				}
				jo.put("修改人", rs.getString(19));
				
				ja1.add(jo);
			}
			
			jo1.put("total", count);
			jo2.put("rows", ja1);
			ja.add(jo1);
			ja.add(jo2);
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
		
	}
	
	//tms根据设备编码查询已关联设备
	@Override
	public void getTmsByCode(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select * from HKCT_TMS_RELATION where tms_equip_code = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String tms_equip_code = ctt.req.getParameter("tms_equip_code");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, tms_equip_code);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("id", rs.getLong(1));
				jo.put("gisfid", rs.getLong(2));
				jo.put("gismajor", rs.getInt(3));
				jo.put("gisminor", rs.getInt(4));
				jo.put("tms_equip_code", rs.getString(5));
				if(rs.getDate(6) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(rs.getDate(6));
					jo.put("editdate", date);
				}
				jo.put("edituser", rs.getString(7));
				ja.add(jo);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}
	
	//tms显示查询结果中的已关联结果
	@Override
	public void getTmsByReady(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql 			  = "select * from (select t.*, rownum rn from ("
				+ "select * from hkct_tms_gis where tms_equip_code in ("
				+ "select distinct tms_equip_code from hkct_tms_relation where tms_equip_code in ("
				+ "select tms_equip_code from hkct_tms_gis where ";
		String sql1           = "select count(*) from (select * from hkct_tms_gis where tms_equip_code in ("
				+ "select distinct tms_equip_code from hkct_tms_relation where tms_equip_code in ("
				+ "select tms_equip_code from hkct_tms_gis where ";
		PreparedStatement ps  = null;
		PreparedStatement ps1 = null;
		ResultSet rs          = null;
		ResultSet rs1  		  = null;
		String str            = ctt.req.getParameter("sql");
		sql                   = sql + str + "))) t where rownum <= ?) where rn > ?";
		sql1				  = sql1 + str + ")))";
		int pagenum           = Integer.parseInt(ctt.req.getParameter("pagenum"));
		int pagerows          = Integer.parseInt(ctt.req.getParameter("pagerows"));
		JSONObject jo1		  = new JSONObject();
		JSONObject jo2        = new JSONObject();
		JSONArray ja1		  = new JSONArray();
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps1 = ctt.conn.prepareStatement(sql1);
			ps.setInt(1, pagenum * pagerows);
			ps.setInt(2, (pagenum - 1) * pagerows);
			rs = ps.executeQuery();
			rs1 = ps1.executeQuery();
			rs1.next();
			int count = rs1.getInt(1);
			
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("TMS_EQUIP_CODE", rs.getString(1));
				jo.put("TMS_EQUIP_NAME", rs.getString(2));
				jo.put("TMS_PK_CATEGORY", rs.getString(3));
				jo.put("TMS_status", rs.getString(4));
				jo.put("TMS_PK_LOCATION", rs.getString(5));
				jo.put("TMS_pk_jobmngfil", rs.getString(6));
				jo.put("freeitem1", rs.getString(7));
				jo.put("freeitem2", rs.getString(8));
				jo.put("freeitem3", rs.getString(9));
				jo.put("freeitem4", rs.getString(10));
				jo.put("freeitem5", rs.getString(11));
				jo.put("freeitem6", rs.getString(12));
				jo.put("freeitem7", rs.getString(13));
				jo.put("freeitem8", rs.getString(14));
				jo.put("freeitem9", rs.getString(15));
				jo.put("freeitem10", rs.getString(16));
				jo.put("TMS_TYPENAME", rs.getString(17));
				if(rs.getDate(18) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(rs.getDate(18));
					jo.put("updatedate", date);
				}
				jo.put("updateuser", rs.getString(19));
				ja1.add(jo);
			}
			
			jo1.put("total", count);
			jo2.put("rows", ja1);
			ja.add(jo1);
			ja.add(jo2);
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}
	

	/**
	 * 爆管分析
	 */
	//执行存储过程
	@Override
	public void doTrace(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "{? = call PKG_HKCT_TRACE.HKCT_GET_PATH(?, ?, ?, ?)}";
		CallableStatement cs = null;
		int pipeid = Integer.parseInt(ctt.req.getParameter("pipeid"));
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		double x = Double.parseDouble(ctt.req.getParameter("x"));
		double y = Double.parseDouble(ctt.req.getParameter("y"));
		
		try {
			cs = ctt.conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, pipeid);
			cs.setLong(3, tid);
			cs.setDouble(4, x);
			cs.setDouble(5, y);
			cs.executeUpdate();
			int count = cs.getInt(1);
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(cs != null)
					cs.close();
			} catch(Exception e) {
				
			}
		}
	}

	//删除历史记录
	@Override
	public void delHistory(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "delete from HKCT_TRACE_HISTORY where tid = ?";
		PreparedStatement ps = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			int count = ps.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
		
	}

	@Override
	public void doPKG(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "{call HKCT_TRACE_SAVE.PKG_HKCT_TRACE(?)}";
		CallableStatement cs = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			cs = ctt.conn.prepareCall(sql);
			cs.setLong(1, tid);
			int count = cs.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(cs != null)
					cs.close();
			} catch(Exception e) {
				
			}
		}
		
	}

	@Override
	public void getHistory(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select pipeid, tid, pntx, pnty, isclose, editDate from HKCT_TRACE_HISTORY "
				+ "order by editDate DESC";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo1 = new JSONObject();
				jo1.put("pipeid", rs.getInt(1));
				jo1.put("tid", rs.getLong(2));
				jo1.put("pntx", rs.getDouble(3));
				jo1.put("pnty", rs.getDouble(4));
				jo1.put("isclose", rs.getInt(5));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(rs.getTimestamp(6));
				jo1.put("editDate", date);
				ja.add(jo1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getResult(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select sqlstr from HKCT_REPORT_CONFIG where id = ?"; 
		String sql1 = "";
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Long id = Long.parseLong(ctt.req.getParameter("id"));//2
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				sql1 = rs.getString(1);
			}
			sql = sql1 + " where tid = " + String.valueOf(tid);
//			sql = "select fid, displayid, trace_type, valve_size, press_o, valvetype, "
//					+ "near_address, trunc(x,3) x, trunc(y,3) y, name, bz from "
//					+ "(select distinct a.fid, a.tid, a.displayid, a.trace_type, a.valve_size, "
//					+ "a.press_o, a.valvetype, a.near_address, a.x, a.y, b.name name, "
//					+ "b.remark bz from HKCT_TRACE_REPORT_VALVE a, gasvalve b where "
//					+ "a.fid=b.id order by trace_type) where tid = 1705";
			//sql = "select distinct fid, tid, displayid, trace_type, valve_size, press_o, valvetype, near_address, x, y from HKCT_TRACE_REPORT_VALVE where tid = 204";
			ps1 = ctt.conn.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			ResultSetMetaData rsmd = rs1.getMetaData();
			while(rs1.next()) {
				JSONObject jo = new JSONObject();
				//Map<String, String> jo = new LinkedHashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if(rs1.getString(rsmd.getColumnName(i)) != null) {
						jo.put(rsmd.getColumnName(i), rs1.getString(rsmd.getColumnName(i)));
					} else {
						jo.put(rsmd.getColumnName(i), "");
					}
				}
				if(jo.has(rsmd.getColumnName(1))) {
					ja.add(jo);
				} else {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						jo.put(rsmd.getColumnName(i), "");
					}
					ja.add(jo);
				}
//				if(jo.isEmpty()) {
//					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//						jo.put(rsmd.getColumnName(i), "");
//					}
//					ja.add(jo);
//				} else {
//					ja.add(jo);
//				}
			}
			if(ja.size() <= 0) {
				JSONObject jo = new JSONObject();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					jo.put(rsmd.getColumnName(i), "");
				}
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs1 != null)
					rs.close();
				if(ps1 != null)
					ps.close();
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getTip(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select SEQ_HKCT_PID.nextval from dual";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			Long tip = rs.getLong("nextval");
			jo.put("tip", tip);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void insertV(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "insert into HKCT_TRACE_INVALIDVALVE(fid, major, minor, tid) " +
					"values (?, ?, ?, ?)";
		int count = 0;
		PreparedStatement ps = null;
		String fid = ctt.req.getParameter("fid");
		int major = Integer.parseInt(ctt.req.getParameter("major"));
		int minor = Integer.parseInt(ctt.req.getParameter("minor"));
		int tid = Integer.parseInt(ctt.req.getParameter("tid"));
		try {
			ps = ctt.conn.prepareStatement(sql);
			
			String[] id = fid.split(",");
			int[] ids = new int[id.length];
			for (int i = 0; i < id.length; i++) {
			    ids[i] = Integer.parseInt(id[i]);
			}
			for(int j = 0; j < ids.length; j++) {
				ps.setInt(1, ids[j]);
				ps.setInt(2, major);
				ps.setInt(3, minor);
				ps.setInt(4, tid);
				count = ps.executeUpdate();
			}
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void saveTrace(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "insert into HKCT_TRACE_HISTORY(pipeid, tid, pntx, pnty, isclose) " +
					"values (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		int pipeid = Integer.parseInt(ctt.req.getParameter("pipeid"));
		int tid = Integer.parseInt(ctt.req.getParameter("tid"));
		double pntx = Double.parseDouble(ctt.req.getParameter("pntx"));
		double pnty = Double.parseDouble(ctt.req.getParameter("pnty"));
		int isclose = Integer.parseInt(ctt.req.getParameter("isclose"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, pipeid);
			ps.setInt(2, tid);
			ps.setDouble(3, pntx);
			ps.setDouble(4, pnty);
			ps.setInt(5, isclose);
			int count = ps.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getGovernor(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, press_o, next_process, tid from HKCT_TRACE_GOVERNOR where tid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("press_o", rs.getString(2));
				jo.put("next_process", rs.getInt(3));
				jo.put("tid", rs.getLong(4));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getPipe(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, press_o, tid, anatype from HKCT_TRACE_ISOLATE_PIPE where tid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("press_o", rs.getString(2));
				jo.put("tid", rs.getLong(3));
				jo.put("anatype", rs.getInt(4));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getRiser(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, press_o, tid from HKCT_TRACE_RISER where tid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("press_o", rs.getString(2));
				jo.put("tid", rs.getLong(3));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getSource(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, major, minor, sourcefid, tid from HKCT_TRACE_SOURCE where tid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("major", rs.getInt(2));
				jo.put("minor", rs.getInt(3));
				jo.put("sourcefid", rs.getInt(4));
				jo.put("tid", rs.getLong(5));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getSource0(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, major, minor, sourcefid, tid from HKCT_TRACE_SOURCE where tid = ? and SOURCEFID is null";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("major", rs.getInt(2));
				jo.put("minor", rs.getInt(3));
				jo.put("sourcefid", rs.getInt(4));
				jo.put("tid", rs.getLong(5));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getValve(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select distinct fid, nod, press_o, next_process, tid, displayid from " +
					"HKCT_TRACE_VALVE where tid = ? order by next_process desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("nod", rs.getInt(2));
				jo.put("press_o", rs.getString(3));
				jo.put("next_process", rs.getInt(4));
				jo.put("tid", rs.getLong(5));
				jo.put("displayid", rs.getInt(6));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	//获取爆管分析结果
	@Override
	public void getAeResult(CxSvrContext ctt, JSONObject jo) throws Exception {
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		String sql = "select isclose from HKCT_TRACE_HISTORY where tid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		//select fid, press_o, next_process, tid from HKCT_TRACE_GOVERNOR where tid = ?
		//调压器
		String sqlGov = "select a.fid, a.press_o, a.next_process, a.tid, b.geom "
				+ "from HKCT_TRACE_GOVERNOR a, pnt b where a.fid = b.id and a.tid = ?";
		PreparedStatement psGov = null;
		ResultSet rsGov = null;
		//管线
		String sqlPipe = "select a.fid, a.press_o, a.tid, a.anatype, b.geom "
				+ "from HKCT_TRACE_ISOLATE_PIPE a, lin b where a.fid = b.id and a.tid = ?";
		PreparedStatement psPipe = null;
		ResultSet rsPipe = null;
		//立管
		String sqlRiser = "select a.fid, a.press_o, a.tid, b.geom "
				+ "from HKCT_TRACE_RISER a, pnt b where a.fid = b.id and a.tid = ?";
		PreparedStatement psRiser = null;
		ResultSet rsRiser = null;
		//气源
		String sqlSource = "select a.fid, a.major, a.minor, a.sourcefid, a.tid, b.geom "
				+ "from HKCT_TRACE_SOURCE a, pnt b where a.fid = b.id and tid = ?";
		PreparedStatement psSource = null;
		ResultSet rsSource = null;
		//阀门
		String sqlValve = "select a.*, b.geom from "
				+ "(select distinct fid, nod, press_o, next_process, tid, displayid from HKCT_TRACE_VALVE where tid = ?) a, "
				+ "pnt b where a.fid = b.id order by next_process desc";
		PreparedStatement psValve = null;
		ResultSet rsValve = null;
		
		JSONArray jaGov = new JSONArray();
		//jaPipe1为主管线，jaPipe为次管线
		JSONArray jaPipe1 = new JSONArray();
		JSONArray jaPipe0 = new JSONArray();
		JSONArray jaRiser = new JSONArray();
		JSONArray jaSource = new JSONArray();
		//1、为闭关阀，，0为隔离，，2为失效
		JSONArray jaValve1 = new JSONArray();
		JSONArray jaValve0 = new JSONArray();
		JSONArray jaValve2 = new JSONArray();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, tid);
			rs = ps.executeQuery();
			rs.next();
			int isClose = rs.getInt(1);
			
			if(isClose == 1) {
				psGov = ctt.conn.prepareStatement(sqlGov);
				psGov.setLong(1, tid);
				rsGov = psGov.executeQuery();
				while(rsGov.next()) {
					JSONObject joGov = new JSONObject();
					joGov.put("fid", rsGov.getInt(1));
					joGov.put("press_o", rsGov.getString(2));
					joGov.put("next_process", rsGov.getInt(3));
					joGov.put("tid", rsGov.getLong(4));
					//获取调压器坐标
					Double[] bd = getPntCoor(rsGov, 5);
				    joGov.put("coor", bd);
					jaGov.add(joGov);
				}
				
				psPipe = ctt.conn.prepareStatement(sqlPipe);
				psPipe.setLong(1, tid);
				rsPipe = psPipe.executeQuery();
				while(rsPipe.next()) {
					JSONObject joPipe = new JSONObject();
					if(rsPipe.getInt(4) == 1) {
						joPipe.put("fid", rsPipe.getInt(1));
						joPipe.put("press_o", rsPipe.getString(2));
						joPipe.put("tid", rsPipe.getLong(3));
						joPipe.put("anatype", rsPipe.getInt(4));
						//获取管线坐标
						Object[] values = new Object[0];
					    Object[] values1 = new Object[0];
					    Struct oracleStruct = (Struct)rsPipe.getObject(5);
					    values = oracleStruct.getAttributes();
					    java.sql.Array oracleStruct1 = (Array) values[4];
					    Object obj = oracleStruct1.getArray();
					    BigDecimal[] bd_Array = (BigDecimal[]) obj;
					    Double[][] bd = {{bd_Array[0].doubleValue(), bd_Array[1].doubleValue()}, {bd_Array[2].doubleValue(), bd_Array[3].doubleValue()}};
					    joPipe.put("coor", bd);
						jaPipe1.add(joPipe);
					} else {
						joPipe.put("fid", rsPipe.getInt(1));
						joPipe.put("press_o", rsPipe.getString(2));
						joPipe.put("tid", rsPipe.getLong(3));
						joPipe.put("anatype", rsPipe.getInt(4));
						Object[] values = new Object[0];
					    Object[] values1 = new Object[0];
					    Struct oracleStruct = (Struct)rsPipe.getObject(5);
					    values = oracleStruct.getAttributes();
					    java.sql.Array oracleStruct1 = (Array) values[4];
					    Object obj = oracleStruct1.getArray();
					    BigDecimal[] bd_Array = (BigDecimal[]) obj;
					    Double[][] bd = {{bd_Array[0].doubleValue(), bd_Array[1].doubleValue()}, {bd_Array[2].doubleValue(), bd_Array[3].doubleValue()}};
					    joPipe.put("coor", bd);
						jaPipe0.add(joPipe);
					}
				}
				
				psRiser = ctt.conn.prepareStatement(sqlRiser);
				psRiser.setLong(1, tid);
				rsRiser = psRiser.executeQuery();
				while(rsRiser.next()) {
					JSONObject joRiser = new JSONObject();
					joRiser.put("fid", rsRiser.getInt(1));
					joRiser.put("press_o", rsRiser.getString(2));
					joRiser.put("tid", rsRiser.getLong(3));
					//获取立管的坐标
					Double[] bd = getPntCoor(rsRiser, 4);
				    joRiser.put("coor", bd);
					jaRiser.add(joRiser);
				}
				
				psSource = ctt.conn.prepareStatement(sqlSource);
				psSource.setLong(1, tid);
				rsSource = psSource.executeQuery();
				while(rsSource.next()) {
					JSONObject joSource = new JSONObject();
					joSource.put("fid", rsSource.getInt(1));
					joSource.put("major", rsSource.getInt(2));
					joSource.put("minor", rsSource.getInt(3));
					joSource.put("sourcefid", rsSource.getInt(4));
					joSource.put("tid", rsSource.getLong(5));
					//获取气源坐标
					Double[] bd = getPntCoor(rsSource, 6);
				    joSource.put("coor", bd);
					jaSource.add(joSource);
				}
				
				psValve = ctt.conn.prepareStatement(sqlValve);
				psValve.setLong(1, tid);
				rsValve = psValve.executeQuery();
				while(rsValve.next()) {
					JSONObject joValve = new JSONObject();
					if(rsValve.getInt(4) == 1) {
						joValve.put("fid", rsValve.getInt(1));
						joValve.put("nod", rsValve.getInt(2));
						joValve.put("press_o", rsValve.getString(3));
						joValve.put("next_process", rsValve.getInt(4));
						joValve.put("tid", rsValve.getLong(5));
						joValve.put("displayid", rsValve.getInt(6));
						//获取阀门坐标
						Double[] bd = getPntCoor(rsValve, 7);
					    joValve.put("coor", bd);
						jaValve1.add(joValve);
					} else if(rsValve.getInt(4) == 0) {
						joValve.put("fid", rsValve.getInt(1));
						joValve.put("nod", rsValve.getInt(2));
						joValve.put("press_o", rsValve.getString(3));
						joValve.put("next_process", rsValve.getInt(4));
						joValve.put("tid", rsValve.getLong(5));
						joValve.put("displayid", rsValve.getInt(6));
						//获取阀门坐标
						Double[] bd = getPntCoor(rsValve, 7);
					    joValve.put("coor", bd);
						jaValve0.add(joValve);
					} else {
						joValve.put("fid", rsValve.getInt(1));
						joValve.put("nod", rsValve.getInt(2));
						joValve.put("press_o", rsValve.getString(3));
						joValve.put("next_process", rsValve.getInt(4));
						joValve.put("tid", rsValve.getLong(5));
						joValve.put("displayid", rsValve.getInt(6));
						//获取阀门坐标
						Double[] bd = getPntCoor(rsValve, 7);
					    joValve.put("coor", bd);
						jaValve2.add(joValve);
					}
				}
				
				jo.put("isClose", isClose);
				jo.put("Gov", jaGov);
				jo.put("Pipe1", jaPipe1);
				jo.put("Pipe0", jaPipe0);
				jo.put("Riser", jaRiser);
				jo.put("Source", jaSource);
				jo.put("Valve1", jaValve1);
				jo.put("Valve0", jaValve0);
				jo.put("Valve2", jaValve2);
			} else {
				psSource = ctt.conn.prepareStatement(sqlSource);
				psSource.setLong(1, tid);
				rsSource = psSource.executeQuery();
				while(rsSource.next()) {
					JSONObject joSource = new JSONObject();
					joSource.put("fid", rsSource.getInt(1));
					joSource.put("major", rsSource.getInt(2));
					joSource.put("minor", rsSource.getInt(3));
					joSource.put("sourcefid", rsSource.getInt(4));
					joSource.put("tid", rsSource.getLong(5));
					Double[] bd = getPntCoor(rsSource, 6);
					joSource.put("coor", bd);
					jaSource.add(joSource);
				}
				jo.put("isClose", isClose);
				jo.put("Source", jaSource);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rsValve != null) {
					rsValve.close();
				}
				if(psValve != null) {
					psValve.close();
				}
				if(rsSource != null) {
					rsSource.close();
				}
				if(psSource != null) {
					psSource.close();
				}
				if(rsRiser != null) {
					rsRiser.close();
				}
				if(psRiser != null) {
					psRiser.close();
				}
				if(rsPipe != null) {
					rsPipe.close();
				}
				if(psPipe != null) {
					psPipe.close();
				}
				if(rsGov != null) {
					rsGov.close();
				}
				if(psGov != null) {
					psGov.close();
				}
			} catch(Exception e) {
				
			}
		}
	}
	
	/**
	 * 爆管分析获取个点坐标
	 * @param rs
	 * @param colnum
	 * @return
	 */
	private static Double[] getPntCoor(ResultSet rs, int colnum) {
		Double[] bd = new Double[2];
		Object[] values = new Object[0];
	    Object[] values1 = new Object[0];
	    try {
			Struct oracleStruct = (Struct)rs.getObject(colnum);
			values = oracleStruct.getAttributes();
			Struct oracleStruct1 = (Struct)values[2];
			values1 = oracleStruct1.getAttributes();
			BigDecimal i = (BigDecimal)values1[0];
			BigDecimal j = (BigDecimal)values1[1];
			bd[0] = i.doubleValue();
			bd[1] = j.doubleValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return bd;
	}
	
	/**
	 * 调压器找立管
	 */
	@Override
	public void doSamePress(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "{call pkg_hkct.hkct_gov_find_riser(?, ?, ?)}";
		CallableStatement cs = null;
		int pid = Integer.parseInt(ctt.req.getParameter("pid"));
		int findType = Integer.parseInt(ctt.req.getParameter("fType"));
		int govId = Integer.parseInt(ctt.req.getParameter("fid"));
		
		try {
			cs = ctt.conn.prepareCall(sql);
			cs.setInt(1, govId);
			cs.setInt(2, findType);
			cs.setInt(3, pid);
			int count = cs.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(cs != null) {
					cs.close();
				}
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getIt(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = ctt.req.getParameter("sql");
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				for(int i = 1; i <= rsmd.getColumnCount(); i ++) {
					jo.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
				}
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getReslutGFR(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select pid, fid, fType, lv from HKCT_GOV_RISER where pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("pid", rs.getLong(1));
				jo.put("fid", rs.getInt(2));
				jo.put("fType", rs.getInt(3));
				jo.put("lv", rs.getInt(4));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getReslutForRiser(CxSvrContext ctt, JSONArray ja)
			throws Exception {
		String sql = "select fid,Press_od from HKCT_GOV_RISER_RISER where pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fitno", rs.getString(1));
				jo.put("press_od", rs.getString(2));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select SEQ_HKCT_PID.nextval from dual";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			int seq = rs.getInt("nextval");
			jo.put("result", seq);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch(Exception e) {
				
			}
		}
	}
	
	
	/**
	 * 流量计----调压器配置
	 */
	@Override
	public void del(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "update hkct_scada_pt set status_c = 0 where " +
					"zhaddress = ? and pttype = ?";
		PreparedStatement ps = null;
		String zhaddress = ctt.req.getParameter("zhaddress");
		String pttype = ctt.req.getParameter("pttype");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, zhaddress);
			ps.setString(2, pttype);
			int count = ps.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				
			}
		}
	}

	@Override
	public void getScada_flow(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select netid from HKCT_SCADA_Flowmeter where pid = ? and fid = ? and from_to = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		int fid = Integer.parseInt(ctt.req.getParameter("fid"));
		int from_to = Integer.parseInt(ctt.req.getParameter("from_to"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			ps.setInt(2, fid);
			ps.setInt(3, from_to);
			rs = ps.executeQuery();
			rs.next();
			int netid = rs.getInt(1);
			jo.put("netid", netid);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void getScada_net(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid from HKCT_SCADA_Net where pid=? and netid=? and pipeid = ? and ftype = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		int netid = Integer.parseInt(ctt.req.getParameter("netid"));
		int pipeid = Integer.parseInt(ctt.req.getParameter("pipeid"));
		int ftype = Integer.parseInt(ctt.req.getParameter("ftype"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			ps.setInt(2, netid);
			ps.setInt(3, pipeid);
			ps.setInt(4, ftype);
			rs = ps.executeQuery();
			while(rs.next()) {
				ja.add(rs.getInt(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void getScada_pipe(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid from HKCT_SCADA_Net where netid = ? and ftype = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int netid = Integer.parseInt(ctt.req.getParameter("netid"));
		int ftype = Integer.parseInt(ctt.req.getParameter("ftype"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, netid);
			ps.setInt(2, ftype);
			rs = ps.executeQuery();
			while(rs.next()) {
				ja.add(rs.getInt(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getPnt(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select pntid from lintop where linid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pipefid = Integer.parseInt(ctt.req.getParameter("pipefid"));
		int to_pt = Integer.parseInt(ctt.req.getParameter("to_pt"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, pipefid);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) != to_pt) {
					jo.put("pntid", rs.getInt(1));
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getScada_point(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select a.fid, a.from_to, b.x, b.y, b.from_x, b.from_y, b.to_x, b.to_y from " +
					"HKCT_SCADA_Flowmeter a,HKCT_SCADA_PT b where a.netid = ? and a.fid = b.fid";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int netid = Integer.parseInt(ctt.req.getParameter("netid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, netid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("from_to", rs.getInt(2));
				jo.put("x", rs.getDouble(3));
				jo.put("y", rs.getDouble(4));
				jo.put("from_x", rs.getDouble(5));
				jo.put("from_y", rs.getDouble(6));
				jo.put("to_x", rs.getDouble(7));
				jo.put("to_y", rs.getDouble(8));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getScada(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select id, pname, zhnumber, paddress, zhaddress, pttype, gps_lon, gps_lat, x, y, "
				+ "history_file, fid, pipefid, from_pt, to_pt, flag, from_x, from_y, to_x, to_y, edit_by, "
				+ "status_c, edit_date, pid, inputnet, outnet, lowvalue, highvalue from HKCT_SCADA_PT t order by flag desc, inputnet, outnet desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("id", rs.getLong(1));
				jo.put("pname", rs.getString(2));
				jo.put("zhnumber", rs.getString(3));
				jo.put("paddress", rs.getString(4));
				jo.put("zhaddress", rs.getString(5));
				jo.put("pttype", rs.getString(6));
				jo.put("gps_lon", rs.getString(7));
				jo.put("gps_lat", rs.getString(8));
				jo.put("x", rs.getString(9));
				jo.put("y", rs.getString(10));
				jo.put("history_file", rs.getString(11));
				jo.put("fid", rs.getInt(12));
				jo.put("pipefid", rs.getString(13));
				jo.put("from_pt", rs.getInt(14));
				jo.put("to_pt", rs.getInt(15));
				if(rs.getInt(16) == 0) {
					jo.put("flag", "有环路");
				} else {
					jo.put("flag", "无环路");
				}
				jo.put("from_x", rs.getString(17));
				jo.put("from_y", rs.getString(18));
				jo.put("to_x", rs.getString(19));
				jo.put("to_y", rs.getString(20));
				jo.put("edit_by", rs.getString(21));
				jo.put("status_c", rs.getString(22));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(rs.getDate(23) != null) {
					String date = sdf.format(rs.getDate(23));
					jo.put("edit_date", date);
				} else {
					jo.put("edit_date", "");
				}
				jo.put("pid", rs.getLong(24));
				jo.put("inputnet", rs.getInt(25));
				jo.put("outnet", rs.getInt(26));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getUnScada_pipe(CxSvrContext ctt, JSONArray ja)
			throws Exception {
		String sql = "select id from lin minus (select fid from HKCT_SCADA_Net where pid = " +
					"(select pid from hkct_scada_pt where pttype like '%流量计%' and paddress like '%累积流量%' and rownum=1) and ftype=0 " +
					"union select fid from HKCT_SCADA_PT where pttype like '%流量计%' and paddress like '%累积流量%')";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				ja.add(rs.getInt(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getFlow(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select id, pname, zhnumber, paddress, zhaddress, pttype, gps_lon, gps_lat, x, y, history_file, fid, pipefid, from_pt, to_pt, flag, from_x, from_y, to_x, to_y, edit_by, status_c, edit_date, pid, inputnet, outnet " +
				"from hkct_scada_pt where pttype like '%流量计%' and paddress like '%累积流量%' and status_c = ? order by flag desc, inputnet, outnet desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int status_c = Integer.parseInt(ctt.req.getParameter("status_c"));
		JSONArray ja = new JSONArray();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, status_c);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo1 = new JSONObject();
				jo1.put("id", rs.getLong(1));
				jo1.put("pname", rs.getString(2));
				jo1.put("zhnumber", rs.getString(3));
				jo1.put("paddress", rs.getString(4));
				jo1.put("zhaddress", rs.getString(5));
				jo1.put("pttype", rs.getString(6));
				jo1.put("gps_lon", rs.getString(7));
				jo1.put("gps_lat", rs.getString(8));
				jo1.put("x", rs.getString(9));
				jo1.put("y", rs.getString(10));
				jo1.put("history_file", rs.getString(11));
				if(rs.getString(12) != null) {
					jo1.put("fid", rs.getString(12));
				} else {
					jo1.put("fid", "");
				}
				jo1.put("pipefid", rs.getString(13));
				if(rs.getString(14) != null) {
					jo1.put("from_pt", rs.getInt(14));
				} else {
					jo1.put("from_pt", "");
				}
				if(rs.getString(15) != null) {
					jo1.put("to_pt", rs.getInt(15));
				} else {
					jo1.put("to_pt", "");
				}
				if(rs.getString(16) != null) {
					if(rs.getInt(16) == 0) {
						jo1.put("flag", "有环路");
					} else {
						jo1.put("flag", "无环路");
					}
				} else {
					jo1.put("flag", "");
				}
				jo1.put("from_x", rs.getString(17));
				jo1.put("from_y", rs.getString(18));
				jo1.put("to_x", rs.getString(19));
				jo1.put("to_y", rs.getString(20));
				jo1.put("edit_by", rs.getString(21));
				jo1.put("status_c", rs.getInt(22));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(rs.getDate(23) != null) {
					String date = sdf.format(rs.getDate(23));
					jo1.put("edit_date", date);
				} else {
					jo1.put("edit_date", "");
				}
				jo1.put("pid", rs.getLong(24));
				if(rs.getString(25) != null) {
					jo1.put("inputnet", rs.getInt(25));
				} else {
					jo1.put("inputnet", "");
				}
				if(rs.getString(26) != null) {
					jo1.put("outnet", rs.getInt(26));
				} else {
					jo1.put("outnet", "");
				}
				ja.add(jo1);
			}
			jo.put("ret", ja);
		} catch (Exception e) {
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
		}
	}

	@Override
	public void getGov(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select distinct zhaddress, zhnumber, pttype, " +
					"gps_lon, gps_lat, x, y, history_file, fid, pipefid, from_pt, to_pt, " +
					"flag, from_x, from_y, to_x, to_y, edit_by, status_c, edit_date, pid, " +
					"inputnet, outnet from hkct_scada_pt where pttype like '%调压器%' and status_c = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray ja = new JSONArray();
		int status_c = Integer.parseInt(ctt.req.getParameter("status_c"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, status_c);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo1 = new JSONObject();
				jo1.put("zhaddress", rs.getString(1));
				jo1.put("zhnumber", rs.getString(2));
				jo1.put("pttype", rs.getString(3));
				jo1.put("gps_lon", rs.getString(4));
				jo1.put("gps_lat", rs.getString(5));
				jo1.put("x", rs.getString(6));
				jo1.put("y", rs.getString(7));
				jo1.put("history_file", rs.getString(8));
				if(rs.getString(9) != null) {
					jo1.put("fid", rs.getLong(9));
				} else {
					jo1.put("fid", "");
				}
				jo1.put("pipefid", rs.getString(10));
				if(rs.getString(11) != null) {
					jo1.put("from_pt", rs.getInt(11));
				} else {
					jo1.put("from_pt", "");
				}
				if(rs.getString(12) != null) {
					jo1.put("to_pt", rs.getInt(12));
				} else {
					jo1.put("to_pt", "");
				}
				if(rs.getString(13) != null) {
					if(rs.getInt(13) == 0) {
						jo1.put("flag", "有环路");
					} else {
						jo1.put("flag", "无环路");
					}
				} else {
					jo1.put("flag", "");
				}
				jo1.put("from_x", rs.getString(14));
				jo1.put("from_y", rs.getString(15));
				jo1.put("to_x", rs.getString(16));
				jo1.put("to_y", rs.getString(17));
				jo1.put("edit_by", rs.getString(18));
				jo1.put("status_c", rs.getInt(19));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(rs.getDate(20) != null) {
					String date = sdf.format(rs.getDate(20));
					jo1.put("edit_date", date);
				} else {
					jo1.put("edit_date", "");
				}
				jo1.put("pid", rs.getLong(21));
				if(rs.getString(22) != null) {
					jo1.put("inputnet", rs.getInt(22));
				} else {
					jo1.put("inputnet", "");
				}
				if(rs.getString(23) != null) {
					jo1.put("outnet", rs.getInt(23));
				} else {
					jo1.put("outnet", "");
				}
				ja.add(jo1);
			}
			jo.put("ret", ja);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getResultByType(CxSvrContext ctt, JSONArray ja)
			throws Exception {
		String sql = "select * from HKCT_SCADA_PT where pttype = ? and status_c = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String pttype = ctt.req.getParameter("pttype");
		int status_c = Integer.parseInt(ctt.req.getParameter("status_c"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, pttype);
			ps.setInt(2, status_c);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("id", rs.getLong(1));
				jo.put("pname", rs.getString(2));
				jo.put("zhnumber", rs.getString(3));
				jo.put("paddress", rs.getString(4));
				jo.put("zhaddress", rs.getString(5));
				jo.put("pttype", rs.getString(6));
				jo.put("gps_lon", rs.getString(7));
				jo.put("gps_lat", rs.getString(8));
				jo.put("x", rs.getString(9));
				jo.put("y", rs.getString(10));
				jo.put("history_file", rs.getString(11));
				if(rs.getString(12) != null) {
					jo.put("fid", rs.getString(12));
				} else {
					jo.put("fid", "");
				}
				jo.put("pipefid", rs.getString(13));
				if(rs.getString(14) != null) {
					jo.put("from_pt", rs.getInt(14));
				} else {
					jo.put("from_pt", "");
				}
				if(rs.getString(15) != null) {
					jo.put("to_pt", rs.getInt(15));
				} else {
					jo.put("to_pt", "");
				}
				if(rs.getString(16) != null) {
					if(rs.getInt(16) == 0) {
						jo.put("flag", "有环路");
					} else {
						jo.put("flag", "无环路");
					}
				} else {
					jo.put("flag", "");
				}
				jo.put("from_x", rs.getString(17));
				jo.put("from_y", rs.getString(18));
				jo.put("to_x", rs.getString(19));
				jo.put("to_y", rs.getString(20));
				jo.put("edit_by", rs.getString(21));
				jo.put("status_c", rs.getInt(22));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(rs.getDate(23) != null) {
					String date = sdf.format(rs.getDate(23));
					jo.put("edit_date", date);
				} else {
					jo.put("edit_date", "");
				}
				jo.put("pid", rs.getLong(24));
				if(rs.getString(25) != null) {
					jo.put("inputnet", rs.getInt(25));
				} else {
					jo.put("inputnet", "");
				}
				if(rs.getString(26) != null) {
					jo.put("outnet", rs.getInt(26));
				} else {
					jo.put("outnet", "");
				}
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void scadaDo(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select SEQ_HKCT_PID.nextval from dual";
		String sql1 = "call PKG_HKCT_SCADA.HKCT_SCADA_DO(?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			int pid = rs.getInt("nextval");
			
			cs = ctt.conn.prepareCall(sql1);
			cs.setInt(1, pid);
			int count  = cs.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(cs != null) {
					cs.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void scadaDoGov(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "{call PKG_HKCT_SCADA.HKCT_SCADA_CHECKGASGOVERNOR}";
		String sql1 = "select distinct zhaddress, zhnumber, pttype, " +
					"gps_lon, gps_lat, x, y, history_file, fid, pipefid, from_pt, to_pt, " +
					"flag, from_x, from_y, to_x, to_y, edit_by, status_c, edit_date, pid, " +
					"inputnet, outnet from hkct_scada_pt where pttype like '%调压器%' and status_c = 1";
		CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			cs = ctt.conn.prepareCall(sql);
			int count = cs.executeUpdate();
			if(count == 1) {
				ps = ctt.conn.prepareStatement(sql1);
				rs = ps.executeQuery();
				while(rs.next()) {
					JSONObject jo = new JSONObject();
					jo.put("zhaddress", rs.getString(1));
					jo.put("zhnumber", rs.getString(2));
					jo.put("pttype", rs.getString(3));
					jo.put("gps_lon", rs.getString(4));
					jo.put("gps_lat", rs.getString(5));
					jo.put("x", rs.getString(6));
					jo.put("y", rs.getString(7));
					jo.put("history_file", rs.getString(8));
					if(rs.getString(9) != null) {
						jo.put("fid", rs.getLong(9));
					} else {
						jo.put("fid", "");
					}
					jo.put("pipefid", rs.getString(10));
					if(rs.getString(11) != null) {
						jo.put("from_pt", rs.getInt(11));
					} else {
						jo.put("from_pt", "");
					}
					if(rs.getString(12) != null) {
						jo.put("to_pt", rs.getInt(12));
					} else {
						jo.put("to_pt", "");
					}
					if(rs.getString(13) != null) {
						if(rs.getInt(13) == 0) {
							jo.put("flag", "有环路");
						} else {
							jo.put("flag", "无环路");
						}
					} else {
						jo.put("flag", "");
					}
					jo.put("from_x", rs.getString(14));
					jo.put("from_y", rs.getString(15));
					jo.put("to_x", rs.getString(16));
					jo.put("to_y", rs.getString(17));
					jo.put("edit_by", rs.getString(18));
					jo.put("status_c", rs.getInt(19));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if(rs.getDate(20) != null) {
						String date = sdf.format(rs.getDate(20));
						jo.put("edit_date", date);
					} else {
						jo.put("edit_date", "");
					}
					jo.put("pid", rs.getLong(21));
					if(rs.getString(22) != null) {
						jo.put("inputnet", rs.getInt(22));
					} else {
						jo.put("inputnet", "");
					}
					if(rs.getString(23) != null) {
						jo.put("outnet", rs.getInt(23));
					} else {
						jo.put("outnet", "");
					}
					ja.add(jo);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void UpdateScadaG(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "update HKCT_SCADA_PT set x = ?, y = ?, fid = ?, edit_by = ?, "
				+ "edit_date = to_date(?,'yyyy-MM-dd HH24:mi:ss') where zhaddress  = ? and pttype = ?";
		PreparedStatement ps = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String now = sdf.format(new Date());
		String x = ctt.req.getParameter("x");
		String y = ctt.req.getParameter("y");
		int fid = Integer.parseInt(ctt.req.getParameter("fid"));
		String edit_by = ctt.req.getParameter("edit_by");
		String zhaddress = ctt.req.getParameter("zhaddress");
		String pttype = ctt.req.getParameter("pttype");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, x);
			ps.setString(2, y);
			ps.setInt(3, fid);
			ps.setString(4, edit_by);
			ps.setString(5, now);
			ps.setString(6, zhaddress);
			ps.setString(7, pttype);
			int count = ps.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			if(ps != null) {
				ps.close();
			}
		}
	}

	@Override
	public void updateScadaL(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "update HKCT_SCADA_PT set x = ?, y = ?, fid = ?, pipefid = ?, " +
					"from_pt = ?, to_pt = ?, from_x = ?, from_y = ?, to_x = ?, to_y = ?, edit_by = ?, "
					+ "edit_date = to_date(?,'yyyy-MM-dd HH24:mi:ss') where id = ?";
		PreparedStatement ps = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		String x = ctt.req.getParameter("x");
		String y = ctt.req.getParameter("y");
		int fid = Integer.parseInt(ctt.req.getParameter("fid"));
		String pipefid = ctt.req.getParameter("pipefid");
		int from_pt = Integer.parseInt(ctt.req.getParameter("from_pt"));
		int to_pt = Integer.parseInt(ctt.req.getParameter("to_pt"));
		String from_x = ctt.req.getParameter("from_x");
		String from_y = ctt.req.getParameter("from_y");
		String to_x = ctt.req.getParameter("to_x");
		String to_y = ctt.req.getParameter("to_y");
		String edit_by = ctt.req.getParameter("edit_by");
		int id = Integer.parseInt(ctt.req.getParameter("id"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, x);
			ps.setString(2, y);
			ps.setInt(3, fid);
			ps.setString(4, pipefid);
			ps.setInt(5, from_pt);
			ps.setInt(6, to_pt);
			ps.setString(7, from_x);
			ps.setString(8, from_y);
			ps.setString(9, to_x);
			ps.setString(10, to_y);
			ps.setString(11, edit_by);
			ps.setString(12, now);
			ps.setInt(13, id);
			int count = ps.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			if(ps != null) {
				ps.close();
			}
		}
	}
	

	/**
	 * 水利分析调试信息
	 */
	@Override
	public void getGF2Att(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, ftype, lv, pid, old_gj, old_matl, old_temperature, new_gj, new_matl, new_temperature, edituser, editdate "
				+ "from hkct_gf2_att where pid = ? order by fid";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pid = Integer.parseInt(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo1 = new JSONObject();
				jo1.put("fid", rs.getLong(1));
				jo1.put("ftype", rs.getInt(2));
				jo1.put("lv", rs.getInt(3));
				jo1.put("pid", rs.getLong(4));
				jo1.put("old_gj", rs.getInt(5));
				jo1.put("old_matl", rs.getString(6));
				jo1.put("old_temperature", rs.getInt(7));
				if(rs.getString(8) != null) {
					jo1.put("new_gj", rs.getString(8));
				} else {
					jo1.put("new_gj", "");
				}
				if(rs.getString(9) != null) {
					jo1.put("new_matl", rs.getString(9));
				} else {
					jo1.put("new_matl", "");
				}
				if(rs.getString(10) != null) {
					jo1.put("new_temperature", rs.getString(10));
				} else {
					jo1.put("new_temperature", "");
				}
				if(rs.getString(11) != null) {
					jo1.put("edituser", rs.getString(11));
				} else {
					jo1.put("edituser", "");
				}
				if(rs.getDate(12) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(rs.getDate(12));
					jo1.put("editdate", date);
				} else {
					jo1.put("editdate", "");
				}
				
				ja.add(jo1);
				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
		
	}

	@Override
	public void editGA(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "update HKCT_GF2_ATT set new_gj = ?, new_matl = ?, new_temperature = ?, "
				+ "edituser = ?, editdate = to_date(?, 'yyyy/MM/dd') where fid = ? and pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int gj = Integer.parseInt(ctt.req.getParameter("new_gj"));
		String matl = ctt.req.getParameter("new_matl");
		int temperature = Integer.parseInt(ctt.req.getParameter("new_temperature"));
		String edituser = ctt.req.getParameter("edituser");
		String date = ctt.req.getParameter("date");
		Long fid = Long.parseLong(ctt.req.getParameter("fid"));
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, gj);
			ps.setString(2, matl);
			ps.setInt(3, temperature);
			ps.setString(4, edituser);
			ps.setString(5, date);
			ps.setLong(6, fid);
			ps.setLong(7, pid);
			int count = ps.executeUpdate();
			
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
		
	}

	@Override
	public void getMinPress(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, pid, press, center_x, center_y from HKCT_GF_PATH2 where pid = ? and press in "
				+ "(select min(press) from HKCT_GF_PATH2  where pid = ?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			ps.setLong(2, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getLong(1));
				jo.put("pid", rs.getLong(2));
				jo.put("press", rs.getDouble(3));
				jo.put("center_x", rs.getDouble(4));
				jo.put("center_y", rs.getDouble(5));
				ja.add(jo);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	/**
	 * 插入选中的环线
	 */
	@Override
	public void setLoopLine(CxSvrContext ctt, JSONObject jo, int major, int minor) throws Exception {
		String sql = "insert into HKCT_GF_LOOP2_SELECT (fid, pid, ana_result, edituser, Editdate, major, minor) values (?, ?, ?, ?, to_date(?, 'yyyy-MM-dd'), ?, ?)";
		PreparedStatement ps = null;

		Long fid = Long.parseLong(ctt.req.getParameter("fid"));
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		String ana_result = ctt.req.getParameter("ana_result");
		String edituser = ctt.req.getParameter("edituser");
		String editdate = ctt.req.getParameter("editdate");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, fid);
			ps.setLong(2, pid);
			ps.setString(3, ana_result);
			ps.setString(4, edituser);
			ps.setString(5, editdate);
			ps.setInt(6, major);
			ps.setInt(7, minor);
			int count = ps.executeUpdate();
			
			if(count == 1) {
				jo.put("ret", count);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void getLoopLines(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, pid, ana_result, loopid, edituser, editdate, major, minor from hkct_gf_loop2_select where pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getLong(1));
				jo.put("pid", rs.getLong(2));
				jo.put("ana_result", rs.getString(3));
				if(rs.getInt(4) != 0) {
					jo.put("loopid", rs.getInt(4));
				}
				jo.put("edituser", rs.getString(5));
				if(rs.getDate(6) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(rs.getDate(6));
					jo.put("editdate", date);
				}
				jo.put("major", rs.getInt(7));
				jo.put("minor", rs.getInt(8));
				
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	@Override
	public void delLoopLine(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "delete from hkct_gf_loop2_select where fid = ? and pid = ?";
		PreparedStatement ps = null;
		Long fid = Long.parseLong(ctt.req.getParameter("fid"));
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareCall(sql);
			ps.setLong(1, fid);
			ps.setLong(2, pid);
			int count = ps.executeUpdate();
			if(count == 1) {
				jo.put("ret", count);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	//水利分析复制
	@Override
	public void copyGF(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "insert into hkct_gf_start2 "
				+ "select fid,press,gasflow,?,usetype,bz,anadate,lv,node1_fid,node2_fid,temperature,?,gfadjust,gas_type "
				+ "from HKCT_GF_START2 t where pid = ?";
		PreparedStatement ps = null;
		Long c_pid = Long.parseLong(ctt.req.getParameter("c_pid"));
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		String gfname = ctt.req.getParameter("gfname");
		
		try {
			ps = ctt.conn.prepareCall(sql);
			ps.setLong(1, c_pid);
			ps.setString(2, gfname);
			ps.setLong(3, pid);
			int count = ps.executeUpdate();
			jo.put("ret", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}
	
	/**
	 * 水利分析修改温度、微调率、分析名称
	 * @param ctt
	 * @param jo
	 * @throws Exception
	 */
	@Override
	public void edittna(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "update hkct_gf_start2 set temperature = ?, gfname = ?, gfadjust = ?, gas_type = ? where pid = ?";
		PreparedStatement ps = null;
		int tm = Integer.parseInt(ctt.req.getParameter("temperature"));
		String gfname = ctt.req.getParameter("gfname");
		double gfadjust = Double.parseDouble(ctt.req.getParameter("gfadjust"));
		String gastype = ctt.req.getParameter("gastype");
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareCall(sql);
			ps.setInt(1, tm);
			ps.setString(2, gfname);
			ps.setDouble(3, gfadjust);
			ps.setString(4, gastype);
			ps.setLong(5, pid);
			int count = ps.executeUpdate();
			jo.put("ret", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	//取多边形每个点的坐标
	@Override
	public void getGGAS(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "SELECT t.id, t.X, t.Y FROM HKCT_POLYGON c, TABLE(SDO_UTIL.GETVERTICES(c.geometry)) t where c.ployid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long ployid = Long.parseLong(ctt.req.getParameter("ployid"));
		
		try {
			ps = ctt.conn.prepareCall(sql);
			ps.setLong(1, ployid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				JSONArray jaPnt = new JSONArray();
				jo.put("id", rs.getLong(1));
				jaPnt.add(rs.getDouble(2));
				jaPnt.add(rs.getDouble(3));
				jo.put("pnt", jaPnt);
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	//存属性空间查询的多边形空间数据
	@Override
	public void setGGAS(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "insert into HKCT_POLYGON (PLOYID, PNAME, UP_DATE, GEOMETRY) "
				+ "values (?, ?, to_date(sysdate), "
				+ "SDO_GEOMETRY(2003, 54004, NULL, SDO_ELEM_INFO_ARRAY(1, 1003, 1), SDO_ORDINATE_ARRAY(";
		PreparedStatement ps = null;
		Long ployid = Long.parseLong(ctt.req.getParameter("ployid"));
		String pname = ctt.req.getParameter("pname");
		String coors = ctt.req.getParameter("coors");
		sql = sql + coors + ")))";
		
		try {
			ps = ctt.conn.prepareCall(sql);
			ps.setLong(1, ployid);
			ps.setString(2, pname);
			int count = ps.executeUpdate();
			if(count == 1) {
				jo.put("ret", "成功");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	//取出已存入多边形名称
	@Override
	public void getGGASName(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select ployid, pname from HKCT_POLYGON order by ployid";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("ployid", rs.getLong(1));
				jo.put("pname", rs.getString(2));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}

	
	/**
	 * 供用气分析
	 */
	//调用供用气分析存储过程
	@Override
	public void ScadaSD(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "call PKG_HKCT_SCADA.HKCT_SCADA_SUPPLY_DEMAND(?)";
		CallableStatement cs = null;
		String dateStr = ctt.req.getParameter("dateStr");
		
		try {
			cs = ctt.conn.prepareCall(sql);
			cs.setString(1, dateStr);
			int count = cs.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			if(cs != null) {
				cs.close();
			}
		}
	}

	//供用气分析获取下拉框时间
	@Override
	public void getSdDate(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select distinct statisticDate from HKCT_SUPPLY_DEMAND order by StatisticDate desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("colname", rs.getString(1));
				jo.put("namec", rs.getString(1));
				jo.put("namee", "statisticDate");
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getSd_point(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select a.fid, a.from_to, b.x, b.y, b.from_x, b.from_y, b.to_x, b.to_y from " +
					"Hkct_Scada_Flowmeter_History a,Hkct_Scada_Pt_History b where a.netid = ? "
					+ "and a.datestr = ? and a.fid = b.fid";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int netid = Integer.parseInt(ctt.req.getParameter("netid"));
		String dateStr = ctt.req.getParameter("dateStr");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, netid);
			ps.setString(2, dateStr);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("from_to", rs.getInt(2));
				jo.put("x", rs.getDouble(3));
				jo.put("y", rs.getDouble(4));
				jo.put("from_x", rs.getDouble(5));
				jo.put("from_y", rs.getDouble(6));
				jo.put("to_x", rs.getDouble(7));
				jo.put("to_y", rs.getDouble(8));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getSd_AllPoint(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select a.fid, a.from_to, b.x, b.y, b.from_x, b.from_y, b.to_x, b.to_y, b.inputnet " +
					"from Hkct_Scada_Flowmeter_History a,Hkct_Scada_Pt_History b where "
					+ "a.from_to = 1 and a.datestr = ? and a.fid = b.fid";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String dateStr = ctt.req.getParameter("dateStr");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, dateStr);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("from_to", rs.getInt(2));
				jo.put("x", rs.getDouble(3));
				jo.put("y", rs.getDouble(4));
				jo.put("from_x", rs.getDouble(5));
				jo.put("from_y", rs.getDouble(6));
				jo.put("to_x", rs.getDouble(7));
				jo.put("to_y", rs.getDouble(8));
				jo.put("inputnet", rs.getInt(9));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//通过时间获取相应的分析结果
	@Override
	public void getSdByDate(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select netid, supplyconsume, toconsume, fromconsume, " +
					"demandconsume, unknownconsume, domesticconsume, domesticnum, " +
					"candiconsume, candinum, netlength, netvolume, supplygovernor, " +
					"demandgovernor, risernum, statisticdate, pid, x, y, editdate " +
					"from HKCT_SUPPLY_DEMAND where statisticdate = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String s = ctt.req.getParameter("SdDate");
		JSONArray ja = new JSONArray();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, s);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo1 = new JSONObject();
				if(rs.getString(1) != null) {
					jo1.put("netid", rs.getInt(1));
				} else {
					jo1.put("netid", "");
				}
				if(rs.getString(2) != null) {
					jo1.put("supplyConsume", rs.getDouble(2));
				} else {
					jo1.put("supplyConsume", "");
				}
				if(rs.getString(3) != null) {
					jo1.put("toconsume", rs.getDouble(3));
				} else {
					jo1.put("toconsume", "");
				}
				if(rs.getString(4) != null) {
					jo1.put("fromconsume", rs.getDouble(4));
				} else {
					jo1.put("fromconsume", "");
				}
				if(rs.getString(5) != null) {
					jo1.put("demandConsume", rs.getDouble(5));
				} else {
					jo1.put("demandConsume", "");
				}
				if(rs.getString(6) != null) {
					jo1.put("unknownConsume", rs.getDouble(6));
				} else {
					jo1.put("unknownConsume", "");
				}
				if(rs.getString(7) != null) {
					jo1.put("domesticConsume", rs.getDouble(7));
				} else {
					jo1.put("domesticConsume", "");
				}
				if(rs.getString(8) != null) {
					jo1.put("domesticNum", rs.getInt(8));
				} else {
					jo1.put("domesticNum", "");
				}
				if(rs.getString(9) != null) {
					jo1.put("candiConsume", rs.getDouble(9));
				} else {
					jo1.put("candiConsume", "");
				}
				if(rs.getString(10) != null) {
					jo1.put("candiNum", rs.getInt(10));
				} else {
					jo1.put("candiNum", "");
				}
				if(rs.getString(11) != null) {
					jo1.put("netLength", rs.getLong(11));
				} else {
					jo1.put("netLength", "");
				}
				if(rs.getString(12) != null) {
					jo1.put("netVolume", rs.getDouble(12));
				} else {
					jo1.put("netVolume", "");
				}
				if(rs.getString(13) != null) {
					jo1.put("supplyGovernor", rs.getInt(13));
				} else {
					jo1.put("supplyGovernor", "");
				}
				jo1.put("demandGovernor", rs.getInt(14));
				jo1.put("riserNum", rs.getInt(15));
				jo1.put("statisticDate", rs.getString(16));
				if(rs.getString(17) != null) {
					jo1.put("pid", rs.getLong(17));
				} else {
					jo1.put("pid", "");
				}
				if(rs.getString(18) != null) {
					jo1.put("x", rs.getDouble(18));
				} else {
					jo1.put("x", "");
				}
				if(rs.getString(19) != null) {
					jo1.put("y", rs.getDouble(19));
				} else {
					jo1.put("y", "");
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(rs.getDate(20));
				jo1.put("editdate", date);
				ja.add(jo1);
			}
			jo.put("ret", ja);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getSDFid(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid from Hkct_Scada_Net_History  where netid = ? and ftype = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int netid = Integer.parseInt(ctt.req.getParameter("netid"));
		int ftype = Integer.parseInt(ctt.req.getParameter("ftype"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, netid);
			ps.setInt(2, ftype);
			rs = ps.executeQuery();
			while(rs.next()) {
				ja.add(rs.getInt(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getSD(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select netid, supplyconsume, toconsume, fromconsume, " +
					"demandconsume, unknownconsume, domesticconsume, domesticnum, " +
					"candiconsume, candinum, netlength, netvolume, supplygovernor, " +
					"demandgovernor, risernum, statisticdate, pid, x, y, editdate " +
					"from HKCT_SUPPLY_DEMAND order by StatisticDate desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray ja = new JSONArray();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo1 = new JSONObject();
				if(rs.getString(1) != null) {
					jo1.put("netid", rs.getInt(1));
				}
				if(rs.getString(2) != null) {
					jo1.put("supplyConsume", rs.getDouble(2));
				}
				if(rs.getString(3) != null) {
					jo1.put("toconsume", rs.getDouble(3));
				}
				if(rs.getString(4) != null) {
					jo1.put("fromconsume", rs.getDouble(4));
				}
				if(rs.getString(5) != null) {
					jo1.put("demandConsume", rs.getDouble(5));
				}
				if(rs.getString(6) != null) {
					jo1.put("unknownConsume", rs.getDouble(6));
				}
				if(rs.getString(7) != null) {
					jo1.put("domesticConsume", rs.getDouble(7));
				}
				if(rs.getString(8) != null) {
					jo1.put("domesticNum", rs.getInt(8));
				}
				if(rs.getString(9) != null) {
					jo1.put("candiConsume", rs.getDouble(9));
				}
				if(rs.getString(10) != null) {
					jo1.put("candiNum", rs.getInt(10));
				}
				if(rs.getString(11) != null) {
					jo1.put("netLength", rs.getLong(11));
				}
				if(rs.getString(12) != null) {
					jo1.put("netVolume", rs.getDouble(12));
				}
				if(rs.getString(13) != null) {
					jo1.put("supplyGovernor", rs.getInt(13));
				}
				jo1.put("demandGovernor", rs.getInt(14));
				jo1.put("riserNum", rs.getInt(15));
				jo1.put("statisticDate", rs.getString(16));
				if(rs.getString(17) != null) {
					jo1.put("pid", rs.getLong(17));
				}
				if(rs.getString(18) != null) {
					jo1.put("x", rs.getDouble(18));
				}
				if(rs.getString(19) != null) {
					jo1.put("y", rs.getDouble(19));
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(rs.getDate(20));
				jo1.put("editdate", date);
				ja.add(jo1);
			}
			jo.put("ret", ja);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delGfDate(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "delete from HKCT_GF_START2 where fid = ? and pid = ?";
		PreparedStatement ps = null;
		int fid = Integer.parseInt(ctt.req.getParameter("fid"));
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, fid);
			ps.setLong(2, pid);
			int count = ps.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doGf(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "{? = call PKG_HKCT_GF2.HKCT_GF2_DO(?)}";
		CallableStatement cs = null;
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			cs = ctt.conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setLong(2, pid);
			cs.executeUpdate();
			int count = cs.getInt(1);
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getGfByFid(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, press, gasflow, pid, usetype, bz, anadate from "
				+ "HKCT_GF_START2 where fid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int fid = Integer.parseInt(ctt.req.getParameter("fid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, fid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("press", rs.getDouble(2));
				jo.put("gasflow", rs.getDouble(3));
				jo.put("pid", rs.getLong(4));
				if(rs.getInt(5) == 0) {
					jo.put("usetype", "供气");
				} else {
					jo.put("usetype", "用气");
				}
				jo.put("bz", rs.getString(6));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(rs.getDate(7));
				jo.put("anadate", date);
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
		}
	}

	@Override
	public void getGfByPid(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, press, trunc(gasflow), pid, usetype, bz, anadate, lv, node1_fid, node2_fid,"
				+ "temperature, gfname, gfadjust, gas_type from HKCT_GF_START2 where pid = ? order by fid ASC";
		PreparedStatement ps = null;
		ResultSet rs = null;
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("press", rs.getDouble(2));
				jo.put("gasflow", rs.getDouble(3));
				jo.put("pid", rs.getLong(4));
				if(rs.getInt(5) == 0) {
					jo.put("usetype", "供气");
				} else {
					jo.put("usetype", "用气");
				}
				jo.put("bz", rs.getString(6));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(rs.getDate(7));
				jo.put("anadate", date);
				jo.put("lv", rs.getInt(8));
				jo.put("node1_fid", rs.getLong(9));
				jo.put("node2_fid", rs.getLong(10));
				jo.put("temperature", rs.getInt(11));
				jo.put("gfname", rs.getString(12));
				jo.put("gfadjust", rs.getDouble(13));
				jo.put("gas_type", rs.getString(14));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getFxSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select SEQ_HKCT_PID.nextval from dual";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			int seq = rs.getInt("nextval");
			jo.put("seq", seq);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getGf(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, press, gasflow, pid, usetype, bz, anadate, temperature "
				+ "from HKCT_GF_START2 order by pid DESC";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("press", rs.getDouble(2));
				jo.put("gasflow", rs.getDouble(3));
				jo.put("pid", rs.getLong(4));
				if(rs.getInt(5) == 0) {
					jo.put("usetype", "供气");
				} else {
					jo.put("usetype", "用气");
				}
				jo.put("bz", rs.getString(6));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(rs.getDate(7));
				jo.put("anadate", date);
				jo.put("temperature", rs.getInt(8));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void selhgp(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select fid, ftype, lv, pid, startfid, press_od, gj, matl, leng, fpnt, tpnt, "
				+ "press, gasflow, center_x, center_y, to_x, to_y, direction, display_flag from HKCT_GF_PATH2 "
				+ "where pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("ftype", rs.getInt(2));
				jo.put("lv", rs.getInt(3));
				jo.put("pid", rs.getLong(4));
				jo.put("startfid", rs.getInt(5));
				jo.put("press_od", rs.getString(6));
				jo.put("gj", rs.getInt(7));
				jo.put("matl", rs.getString(8));
				jo.put("leng", rs.getLong(9));
				jo.put("fpnt", rs.getInt(10));
				jo.put("tpnt", rs.getInt(11));
				jo.put("press", Double.toString(rs.getDouble(12)));
				jo.put("gasflow", Double.toString(rs.getDouble(13)));
				jo.put("center_x", rs.getDouble(14));
				jo.put("center_y", rs.getDouble(15));
				jo.put("to_x", rs.getDouble(16));
				jo.put("to_y", rs.getDouble(17));
				jo.put("direction", rs.getInt(18));
				jo.put("display_flag", rs.getInt(19));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getGfHis(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select gfname, pid, max(anadate), gas_type from hkct_gf_start2 group by gfname, pid, gas_type order by pid DESC";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("gfname", rs.getString(1));
				jo.put("pid", rs.getLong(2));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(rs.getDate(3));
				jo.put("anaDate", date);
				jo.put("gastype", rs.getString(4));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setGf(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "insert into HKCT_GF_START2(fid, press, pid, usetype, bz, anadate, "
				+ "temperature, gfname, gfadjust, gas_type) values (?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?)";
		String sql1 = "insert into HKCT_GF_START2(fid, gasflow, pid, usetype, bz, anadate, "
				+ "temperature, gfname, gfadjust, gas_type) values (?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?)";
		String sql2 = "delete from HKCT_GF_START2 where fid = ? and pid = ?";
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		int fid = Integer.parseInt(ctt.req.getParameter("fid"));
		String press = ctt.req.getParameter("press");
		String gasflow = ctt.req.getParameter("gasflow");
		long pid = Long.parseLong(ctt.req.getParameter("pids"));
		String usetype = ctt.req.getParameter("usetype");
		String bz = ctt.req.getParameter("bz");
		int tp = Integer.parseInt(ctt.req.getParameter("tp"));
		String gn = ctt.req.getParameter("gn");
		Double gaj = Double.parseDouble(ctt.req.getParameter("gaj"));
		String gastype = ctt.req.getParameter("gastype");
		
		try {
			ps2 = ctt.conn.prepareStatement(sql2);
			ps2.setInt(1, fid);
			ps2.setLong(2, pid);
			int count = ps2.executeUpdate();
			if(usetype.equals("0")) {
				ps = ctt.conn.prepareStatement(sql);
				ps.setInt(1, fid);
				ps.setDouble(2, Double.parseDouble(press));
				ps.setLong(3, pid);
				ps.setInt(4, Integer.parseInt(usetype));
				ps.setString(5, bz);
				ps.setInt(6, tp);
				ps.setString(7, gn);
				ps.setDouble(8, gaj);
				ps.setString(9, gastype);
				int num = ps.executeUpdate();
				jo.put("result", num);
			} else if(usetype.equals("1")) {
				ps = ctt.conn.prepareStatement(sql1);
				ps.setInt(1, fid);
				ps.setDouble(2, Double.parseDouble(gasflow));
				ps.setLong(3, pid);
				ps.setInt(4, Integer.parseInt(usetype));
				ps.setString(5, bz);
				ps.setInt(6, tp);
				ps.setString(7, gn);
				ps.setDouble(8, gaj);
				ps.setString(9, gastype);
				int num = ps.executeUpdate();
				jo.put("result", num);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(ps != null){
				ps.close();
			}
			if(ps2 != null) {
				ps2.close();
			}
		}
	}

	@Override
	public void updateGfByFid(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "update HKCT_GF_START2 set press = ?, usetype = ? where fid = ?";
		String sql1 = "update HKCT_GF_START2 set gasflow = ?, usetype = ? where fid = ?";
		PreparedStatement ps = null;
		int fid = Integer.parseInt(ctt.req.getParameter("fid"));
		double press = Double.parseDouble(ctt.req.getParameter("press"));
		double gasflow = Double.parseDouble(ctt.req.getParameter("gasflow"));
		int usetype = Integer.parseInt(ctt.req.getParameter("usetype"));
		
		try {
			if(usetype == 0) {
				ps = ctt.conn.prepareStatement(sql);
				ps.setDouble(1, press);
				ps.setInt(2, usetype);
				ps.setInt(3, fid);
				int count = ps.executeUpdate();
				jo.put("result", count);
			} else if(usetype == 1) {
				ps = ctt.conn.prepareStatement(sql1);
				ps.setDouble(1, gasflow);
				ps.setInt(2, usetype);
				ps.setInt(3, fid);
				int count = ps.executeUpdate();
				jo.put("result", count);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(ps != null) {
				ps.close();
			}
		}
	}

	@Override
	public void getInformation(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select pid, fid, major, minor, featuretype, infomation, editdate " +
					"from HKCT_GF_RESULTINFO2 where pid = ? order by editdate desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("pid", rs.getLong(1));
				jo.put("fid", rs.getInt(2));
				jo.put("major", rs.getInt(3));
				jo.put("minor", rs.getInt(4));
				jo.put("featuretype", rs.getString(5));
				jo.put("infomation", rs.getString(6));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String d = sdf.format(rs.getTimestamp(7));
				jo.put("editdate", d);
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}if(ps != null) {
				ps.close();
			}
		}
	}

	@Override
	public void getftpnt(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select a.press, trunc(a.gasflow), b.id, b.geom from HKCT_GF_START2 a, pnt b "
				+ "where a.fid=b.id and pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				if(rs.getString(1) != null) {
					jo.put("press", rs.getString(1));
				} else {
					jo.put("press", "");
				}
				if(rs.getString(2) != null) {
					jo.put("gasflow", rs.getString(2));
				} else {
					jo.put("gasflow", "");
				}
				jo.put("id", rs.getInt(3));
				Object[] values = new Object[0];
			    Object[] values1 = new Object[0];
			    Struct oracleStruct = (Struct)rs.getObject(4);
			    values = oracleStruct.getAttributes();
			    Struct oracleStruct1 = (Struct)values[2];
			    values1 = oracleStruct1.getAttributes();
			    BigDecimal i = (BigDecimal)values1[0];
			    BigDecimal j = (BigDecimal)values1[1];
			    jo.put("x", i);
			    jo.put("y", j);
			    ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateGfByPid(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "update HKCT_GF_START2 set temperature = ?, gfadjust = ?, gfname = ? "
				+ "where pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int temperature = Integer.parseInt(ctt.req.getParameter("temperature"));
		int pid = Integer.parseInt(ctt.req.getParameter("pid"));
		Double gaj = Double.parseDouble(ctt.req.getParameter("gaj"));
		String gfname = ctt.req.getParameter("gfname");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, temperature);
			ps.setDouble(2, gaj);
			ps.setString(3, gfname);
			ps.setInt(4, pid);
			int count = ps.executeUpdate();
			jo.put("result", count);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getGfIn(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "Select loopid, sum(leng) from HKCT_GF_LOOP2 "
					+ "where ftype = ? and pid = ? group by loopid order by loopid asc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int ftype = Integer.parseInt(ctt.req.getParameter("ftype"));
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, ftype);
			ps.setLong(2, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("loopid", rs.getInt(1));
				jo.put("sum", rs.getLong(2));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getLoopHighLight(CxSvrContext ctt, JSONArray ja)
			throws Exception {
		String sql = "select fid, ftype, pid, leng, fpnt, tpnt, loopid, directi from "
				+ "hkct_gf_loop2 where loopid = ? and pid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int loopid = Integer.parseInt(ctt.req.getParameter("loopid"));
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setInt(1, loopid);
			ps.setLong(2, pid);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("fid", rs.getInt(1));
				jo.put("ftype", rs.getInt(2));
				jo.put("pid", rs.getLong(3));
				jo.put("leng", rs.getLong(4));
				jo.put("fpnt", rs.getLong(5));
				jo.put("tpnt", rs.getLong(6));
				jo.put("loopid", rs.getInt(7));
				jo.put("directi", rs.getInt(8));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void getGasType(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select * from HKCT_GF_GASTYPE2";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("colname", rs.getString(1));
				jo.put("namec", rs.getString(1));
				jo.put("namee", "gastype");
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
		}
	}

	@Override
	public void getcomtday(CxSvrContext ctt, JSONArray ja) throws Exception {
		String sql = "select distinct pname from scada.hkcg_scada_pt_hour"+" where pname in ("+"select pname from hkct_scada_pt where zhaddress =?"+" and monitorType=?"+")";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String zhaddress = ctt.req.getParameter("zhaddress");
		String monitorType = ctt.req.getParameter("monitorType");
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setString(1, zhaddress);
			ps.setString(2, monitorType);
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("pname", rs.getString(1));
				ja.add(jo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
		}
	}

	@Override
	public void reportExcel(CxSvrContext ctt, JSONObject jo, HttpServletResponse res) throws Exception {
		String sqlPnt = "select pid, wydh, gxxz, jdxz, x, y, dmgc, ms, yljb, gj, prj, cz, type,"
				+ "bz, bz1, bz2, fid from HKCT_EXP_PNT where pid = ?";
		String sqlLin = "select pid, qdbh, zdbh, qdgc, zdgc, qdms, zdms, gxxz, gj, cz, yljb,"
				+ "msfs, msrq, qsdw, szdl, prj, bz, bz1, bz2, fid from HKCT_EXP_LIN where pid = ?";
		PreparedStatement psPnt = null;
		PreparedStatement psLin = null;
		ResultSet rsPnt = null;
		ResultSet rsLin = null;
//		JSONArray jaLin = new JSONArray();
		JSONArray jaPnt = new JSONArray();
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		String name = ctt.req.getParameter("name");
		try {
//			File file = new File("C:\\a.xls");
//			file.createNewFile();
//			OutputStream os = new FileOutputStream(file);//创建工作簿
//			URI uri = new URI("http://localhost:8080/gis");  
//		   	Desktop desktop = null;  
//		   	if (Desktop.isDesktopSupported()) {  
//			   desktop = Desktop.getDesktop();  
//		   	}
//		   	if (desktop != null)  
//		    desktop.browse(uri);
			//获取点数据，并将点数据存在一个jsonArray里面
			psPnt = ctt.conn.prepareStatement(sqlPnt);
			psPnt.setLong(1, pid);
			rsPnt = psPnt.executeQuery();
			String expPnt = "";
			while(rsPnt.next()) {
				expPnt = "";
				expPnt += "{pid:" + "'" + rsPnt.getString(1) + "'" + ","
			            + "wydh:" + "'" + rsPnt.getString(2) + "'" + ","
			            + "gxxz:" + "'" + rsPnt.getString(3) + "'" + ","
			            + "jdxz:" + "'" + rsPnt.getString(4) + "'" + ","
			            + "x:" + "'" + rsPnt.getString(5) + "'" + ","
			            + "y:" + "'" + rsPnt.getString(6) + "'" + ","
			            + "dmgc:" + "'" + rsPnt.getString(7) + "'" + ","
			            + "ms:" + "'" + rsPnt.getString(8) + "'" + ","
			            + "yljb:" + "'" + rsPnt.getString(9) + "'" + ","
			            + "gj:" + "'" + rsPnt.getString(10) + "'" + ","
			            + "prj:" + "'" + rsPnt.getString(11) + "'" + ","
			            + "cz:" + "'" + rsPnt.getString(12) + "'" + ","
			            + "type:" + "'" + rsPnt.getString(13) + "'" + ","
			            + "bz:" + "'" + rsPnt.getString(14) + "'" + ","
			            + "bz1:" + "'" + rsPnt.getString(15) + "'" + ","
			            + "bz2:" + "'" + rsPnt.getString(16) + "'" + ","
			            + "fid:" + "'" + rsPnt.getString(17) + "'" + ","
			            + "}" + ",";
				expPnt = expPnt.replaceAll("null", "");
				expPnt = expPnt.substring(0, expPnt.length() - 1);
				//JSONObject joLin = new JSONObject(expPnt);
				JSONArray joLin = JSONArray.fromObject(expPnt);
				jaPnt.add(joLin);
			}
//			expPnt = expPnt.replaceAll("null", "");
//			expPnt = "[" + expPnt.substring(0, expPnt.length() - 1) + "]";
//			JSONArray jaPnt = new JSONArray(expPnt);
			
			
			//获取线数据，并将线数据储存在一个jsonArray里面
			psLin = ctt.conn.prepareStatement(sqlLin);
			psLin.setLong(1, pid);
			rsLin = psLin.executeQuery();
			String expLin = "";
			while(rsLin.next()) {
				//expLin = "";
				expLin += "{pid:" + "'" + rsLin.getString(1) + "'" + ","
			            + "qdbh:" + "'" + rsLin.getString(2) + "'" + ","
			            + "zdbh:" + "'" + rsLin.getString(3) + "'" + ","
			            + "qdgc:" + "'" + rsLin.getString(4) + "'" + ","
			            + "zdgc:" + "'" + rsLin.getString(5) + "'" + ","
			            + "qdms:" + "'" + rsLin.getString(6) + "'" + ","
			            + "zdms:" + "'" + rsLin.getString(7) + "'" + ","
			            + "gxxz:" + "'" + rsLin.getString(8) + "'" + ","
			            + "gj:" + "'" + rsLin.getString(9) + "'" + ","
			            + "cz:" + "'" + rsLin.getString(10) + "'" + ","
			            + "yljb:" + "'" + rsLin.getString(11) + "'" + ","
			            + "msfs:" + "'" + rsLin.getString(12) + "'" + ","
			            + "msrq:" + "'" + rsLin.getString(13) + "'" + ","
			            + "qsdw:" + "'" + rsLin.getString(14) + "'" + ","
			            + "szdl:" + "'" + rsLin.getString(15) + "'" + ","
			            + "prj:" + "'" + rsLin.getString(16) + "'" + ","
			            + "bz:" + "'" + rsLin.getString(17) + "'" + ","
			            + "bz1:" + "'" + rsLin.getString(18) + "'" + ","
			            + "bz2:" + "'" + rsLin.getString(19) + "'" + ","
			            + "fid:" + "'" + rsLin.getString(20) + "'" + ","
			            + "}" + ",";
//				expLin = expLin.replaceAll("null", "");
//				expLin = expLin.substring(0, expLin.length() - 1);
//				JSONObject joLin = new JSONObject(expLin);
//				jaLin.put(joLin);
			}
			expLin = expLin.replaceAll("null", "");
			expLin = "[" + expLin.substring(0, expLin.length() - 1) + "]";
			//JSONArray jaLin = new JSONArray(expLin);
			JSONArray jaLin = JSONArray.fromObject(expLin);
			
//			String name = "aaa";
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Disposition", "attachment; filename=" + name + ".xls"); 
			OutputStream os = res.getOutputStream();
			
			WritableWorkbook ww = Workbook.createWorkbook(os);
			WritableSheet sheet1 = ww.createSheet("点", 0);//创建新的一页
			WritableSheet sheet2 = ww.createSheet("线", 1);
			
//			JSONObject jsonObj = new JSONObject();
//			jsonObj.put("姓名", "张三");
//			jsonObj.put("性别", "女");
//			jsonObj.put("年龄", "18");
//			JSONArray ja = new JSONArray();
//			ja.add(jsonObj);
			Label label;//单元格对象---点
			Label labelLin;//单元格对象----线
			int colnum = 0;//列数计数
			
			if(jaPnt.size() != 0) {
				//将第一行信息加到页中。如：姓名、年龄、性别
				JSONObject first = jaPnt.getJSONObject(0);
				Iterator<String> iterator = first.keys();//得到第一项的key集合
				while(iterator.hasNext()) {
					String key = iterator.next();
					label = new Label(colnum++, 0, key);
					sheet1.addCell(label);
				}
				
				//遍历jsonArray
				for(int i = 0; i < jaPnt.size(); i++) {
					JSONObject item = jaPnt.getJSONObject(i);//得到数组的每项
					iterator = item.keys();//得到key集合
					colnum = 0;//从第0列开始放
					while(iterator.hasNext()) {
						String key = iterator.next();
//						if(key.equals("年龄")) {
//							String value = item.getString(key);
//							jxl.write.Number numb = new jxl.write.Number(colnum++, (i + 1), Integer.parseInt(value)); 
//							sheet1.addCell(numb);
//						} else {
							String value = item.getString(key);
							label = new Label(colnum++, (i + 1), value);         
							sheet1.addCell(label);
//						}
					}
				}
			}
			
			
			colnum = 0;
			//将第一行信息加到页中。如：姓名、年龄、性别
			JSONObject lin = jaLin.getJSONObject(0);
			Iterator<String> iteratorLin = lin.keys();//得到第一项的key集合
			while(iteratorLin.hasNext()) {
				String key = iteratorLin.next();
				labelLin = new Label(colnum++, 0, key);
				sheet2.addCell(labelLin);
			}
			
			//遍历jsonArray
			for(int i = 0; i < jaLin.size(); i++) {
				JSONObject item = jaLin.getJSONObject(i);//得到数组的每项
				iteratorLin = item.keys();//得到key集合
				colnum = 0;//从第0列开始放
				while(iteratorLin.hasNext()) {
					String key = iteratorLin.next();
//					if(key.equals("年龄")) {
//						String value = item.getString(key);
//						jxl.write.Number numb = new jxl.write.Number(colnum++, (i + 1), Integer.parseInt(value)); 
//						sheet1.addCell(numb);
//					} else {
						String value = item.getString(key);
						labelLin = new Label(colnum++, (i + 1), value);
						sheet2.addCell(labelLin);
//					}
				}
			}
			
			ww.write(); // 加入到文件中
			ww.close(); // 关闭文件，释放资源
			//os.close();
		} catch (Exception e) {
			jo.put("result", "failed"); // 将调用该函数的结果返回
            jo.put("reason", e.getMessage()); // 将调用该函数失败的原因返回
		} finally {
			try {
				if(rsLin != null) {
					rsLin.close();
				}
				if(psLin != null) {
					psLin.close();
				}
				if(rsPnt != null) {
					rsPnt.close();
				}
				if(psPnt != null) {
					psPnt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		jo.put("result", 1);
	}

	/**
	 * 多边形空间数据分析---获取序列号pid
	 */
	@Override
	public void getHpSeq(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "select SEQ_HKCT_POLYGON.Nextval from dual";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			long seq = rs.getLong("nextval");
			jo.put("seq", seq);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行PKG_HKCT.HKCT_EXP(pid)获取点、线数据4280
	 */
	@Override
	public void getExpPntLin(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "{? = call PKG_HKCT.HKCT_EXP(?)}";
		String sqlPnt = "select pid, wydh, gxxz, jdxz, x, y, dmgc, ms, yljb, gj, prj, cz, type,"
				+ "bz, bz1, bz2, fid from HKCT_EXP_PNT where pid = ?";
		String sqlLin = "select pid, qdbh, zdbh, qdgc, zdgc, qdms, zdms, gxxz, gj, cz, yljb,"
				+ "msfs, msrq, qsdw, szdl, prj, bz, bz1, bz2, fid from HKCT_EXP_LIN where pid = ?";
		CallableStatement cs = null;
		PreparedStatement psPnt = null;
		PreparedStatement psLin = null;
		ResultSet rsPnt = null;
		ResultSet rsLin = null;
		JSONArray jaPnt = new JSONArray();
		JSONArray jaLin = new JSONArray();
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			cs = ctt.conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setLong(2, pid);
			cs.executeUpdate();
			int count = cs.getInt(1);
			System.out.println(count);
			if(count == 0) {
				//获取点数据，并将点数据存在一个jsonArray里面
				psPnt = ctt.conn.prepareStatement(sqlPnt);
				psPnt.setLong(1, pid);
				rsPnt = psPnt.executeQuery();
				while(rsPnt.next()) {
					JSONObject joPnt = new JSONObject();
					joPnt.put("pid", rsPnt.getLong(1));
					joPnt.put("wydh", rsPnt.getString(2));
					joPnt.put("gxxz", rsPnt.getString(3));
					joPnt.put("jdxz", rsPnt.getString(4));
					joPnt.put("x", rsPnt.getDouble(5));
					joPnt.put("y", rsPnt.getDouble(6));
					joPnt.put("dmgc", rsPnt.getString(7));
					joPnt.put("ms", rsPnt.getString(8));
					joPnt.put("yljb", rsPnt.getString(9));
					joPnt.put("gj", rsPnt.getString(10));
					joPnt.put("prj", rsPnt.getInt(11));
					joPnt.put("cz", rsPnt.getString(12));
					joPnt.put("type", rsPnt.getString(13));
					joPnt.put("bz", rsPnt.getString(14));
					joPnt.put("bz1", rsPnt.getString(15));
					joPnt.put("bz2", rsPnt.getString(16));
					joPnt.put("fid", rsPnt.getInt(17));
					jaPnt.add(joPnt);
				}
				//获取线数据，并将线数据储存在一个jsonArray里面
				psLin = ctt.conn.prepareStatement(sqlLin);
				psLin.setLong(1, pid);
				rsLin = psLin.executeQuery();
				while(rsLin.next()) {
					JSONObject joLin = new JSONObject();
					joLin.put("pid", rsLin.getLong(1));
					joLin.put("qdbh", rsLin.getString(2));
					joLin.put("zdbh", rsLin.getString(3));
					joLin.put("qdgc", rsLin.getString(4));
					joLin.put("zdgc", rsLin.getString(5));
					joLin.put("qdms", rsLin.getString(6));
					joLin.put("zdms", rsLin.getString(7));
					joLin.put("gxxz", rsLin.getString(8));
					joLin.put("gj", rsLin.getString(9));
					joLin.put("cz", rsLin.getString(10));
					joLin.put("yljb", rsLin.getString(11));
					joLin.put("msfs", rsLin.getString(12));
					joLin.put("msrq", rsLin.getString(13));
					joLin.put("qsdw", rsLin.getString(14));
					joLin.put("szdl", rsLin.getString(15));
					joLin.put("prj", rsLin.getInt(16));
					joLin.put("bz", rsLin.getString(17));
					joLin.put("bz1", rsLin.getString(18));
					joLin.put("bz2", rsLin.getString(19));
					joLin.put("fid", rsLin.getInt(20));
					jaLin.add(joLin);
				}
			}
			jo.put("ret", count);
			jo.put("pnt", jaPnt);
			jo.put("lin", jaLin);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rsLin != null) {
					rsLin.close();
				}
				if(psLin != null) {
					psLin.close();
				}
				if(rsPnt != null) {
					rsPnt.close();
				}
				if(psPnt != null) {
					psPnt.close();
				}
				if(cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取空间数据查询的点数据
	 */
	@Override
	public void getExpPntByPage(CxSvrContext ctt, JSONArray ja)
			throws Exception {
		String sql            = "select pid, wydh, gxxz, jdxz, x, y, dmgc, ms, yljb, gj, prj, cz, type,"
							  + "bz, bz1, bz2, fid from (select t.*, rownum rn from (select * from HKCT_EXP_PNT "
							  + "where pid = ?) t where rownum <= ?) where rn > ?";
		String countSql       = "select count(*) from HKCT_EXP_PNT where pid = ?";
		PreparedStatement ps  = null;
		PreparedStatement countPs = null;
		ResultSet rs          = null;
		ResultSet countRs     = null;
		Long pid 			  = Long.parseLong(ctt.req.getParameter("pid"));
		int pagenum           = Integer.parseInt(ctt.req.getParameter("pagenum"));
		int pagerows          = Integer.parseInt(ctt.req.getParameter("pagerows"));
		JSONObject jo1		  = new JSONObject();
		JSONObject jo2        = new JSONObject();
		JSONArray jaPnt		  = new JSONArray();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			ps.setInt(2, pagenum * pagerows);
			ps.setInt(3, (pagenum - 1) * pagerows);
			rs = ps.executeQuery();
			countPs = ctt.conn.prepareStatement(countSql);
			countPs.setLong(1, pid);
			countRs = countPs.executeQuery();
			countRs.next();
			int count = countRs.getInt(1);
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("pid", rs.getLong(1));
				jo.put("wydh", rs.getString(2));
				jo.put("gxxz", rs.getString(3));
				jo.put("jdxz", rs.getString(4));
				jo.put("x", rs.getDouble(5));
				jo.put("y", rs.getDouble(6));
				jo.put("dmgc", rs.getString(7));
				jo.put("ms", rs.getString(8));
				jo.put("yljb", rs.getString(9));
				jo.put("gj", rs.getString(10));
				jo.put("prj", rs.getInt(11));
				jo.put("cz", rs.getString(12));
				jo.put("type", rs.getString(13));
				jo.put("bz", rs.getString(14));
				jo.put("bz1", rs.getString(15));
				jo.put("bz2", rs.getString(16));
				jo.put("fid", rs.getInt(17));
				jaPnt.add(jo);
			}
			jo1.put("total", count);
			jo2.put("rows", jaPnt);
			ja.add(jo1);
			ja.add(jo2);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(countRs != null) {
					countRs.close();
				}
				if(countPs != null) {
					countPs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取空间数据查询的线数据
	 */
	@Override
	public void getExpLinByPage(CxSvrContext ctt, JSONArray ja)
			throws Exception {
		String sql = "select pid, qdbh, zdbh, qdgc, zdgc, qdms, zdms, gxxz, gj, cz, yljb, msfs, msrq,"
				   + "qsdw, szdl, prj, bz, bz1, bz2, fid from (select t.*, rownum rn from (select * "
				   + "from HKCT_EXP_LIN where pid = ?) t where rownum <= ?) where rn > ?";
		String countSql       = "select count(*) from HKCT_EXP_LIN where pid = ?";
		PreparedStatement ps  = null;
		PreparedStatement countPs = null;
		ResultSet rs          = null;
		ResultSet countRs     = null;
		Long pid 			  = Long.parseLong(ctt.req.getParameter("pid"));
		int pagenum           = Integer.parseInt(ctt.req.getParameter("pagenum"));
		int pagerows          = Integer.parseInt(ctt.req.getParameter("pagerows"));
		JSONObject jo1		  = new JSONObject();
		JSONObject jo2        = new JSONObject();
		JSONArray jaPnt		  = new JSONArray();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			ps.setLong(1, pid);
			ps.setInt(2, pagenum * pagerows);
			ps.setInt(3, (pagenum - 1) * pagerows);
			rs = ps.executeQuery();
			countPs = ctt.conn.prepareStatement(countSql);
			countPs.setLong(1, pid);
			countRs = countPs.executeQuery();
			countRs.next();
			int count = countRs.getInt(1);
			while(rs.next()) {
				JSONObject jo = new JSONObject();
				jo.put("pid", rs.getLong(1));
				jo.put("qdbh", rs.getString(2));
				jo.put("zdbh", rs.getString(3));
				jo.put("qdgc", rs.getString(4));
				jo.put("zdgc", rs.getString(5));
				jo.put("qdms", rs.getString(6));
				jo.put("zdms", rs.getString(7));
				jo.put("gxxz", rs.getString(8));
				jo.put("gj", rs.getString(9));
				jo.put("cz", rs.getString(10));
				jo.put("yljb", rs.getString(11));
				jo.put("msfs", rs.getString(12));
				jo.put("msrq", rs.getString(13));
				jo.put("qsdw", rs.getString(14));
				jo.put("szdl", rs.getString(15));
				jo.put("prj", rs.getString(16));
				jo.put("bz", rs.getString(17));
				jo.put("bz1", rs.getString(18));
				jo.put("bz2", rs.getString(19));
				jo.put("fid", rs.getString(20));
				jaPnt.add(jo);
			}
			jo1.put("total", count);
			jo2.put("rows", jaPnt);
			ja.add(jo1);
			ja.add(jo2);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(countRs != null) {
					countRs.close();
				}
				if(countPs != null) {
					countPs.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取已分析的多边形范围点、线数据
	 */
	@Override
	public void getExpPntLinOld(CxSvrContext ctt, JSONObject jo)
			throws Exception {
		String sqlPnt = "select pid, wydh, gxxz, jdxz, x, y, dmgc, ms, yljb, gj, prj, cz, type,"
				+ "bz, bz1, bz2, fid from HKCT_EXP_PNT where pid = ?";
		String sqlLin = "select pid, qdbh, zdbh, qdgc, zdgc, qdms, zdms, gxxz, gj, cz, yljb,"
				+ "msfs, msrq, qsdw, szdl, prj, bz, bz1, bz2, fid from HKCT_EXP_LIN where pid = ?";
		PreparedStatement psPnt = null;
		PreparedStatement psLin = null;
		ResultSet rsPnt = null;
		ResultSet rsLin = null;
		JSONArray jaPnt = new JSONArray();
		JSONArray jaLin = new JSONArray();
		long pid = Long.parseLong(ctt.req.getParameter("pid"));
		
		try {
			//获取点数据，并将点数据存在一个jsonArray里面
			psPnt = ctt.conn.prepareStatement(sqlPnt);
			psPnt.setLong(1, pid);
			rsPnt = psPnt.executeQuery();
			while(rsPnt.next()) {
				JSONObject joPnt = new JSONObject();
				joPnt.put("pid", rsPnt.getLong(1));
				joPnt.put("wydh", rsPnt.getString(2));
				joPnt.put("gxxz", rsPnt.getString(3));
				joPnt.put("jdxz", rsPnt.getString(4));
				joPnt.put("x", rsPnt.getDouble(5));
				joPnt.put("y", rsPnt.getDouble(6));
				joPnt.put("dmgc", rsPnt.getString(7));
				joPnt.put("ms", rsPnt.getString(8));
				joPnt.put("yljb", rsPnt.getString(9));
				joPnt.put("gj", rsPnt.getString(10));
				joPnt.put("prj", rsPnt.getInt(11));
				joPnt.put("cz", rsPnt.getString(12));
				joPnt.put("type", rsPnt.getString(13));
				joPnt.put("bz", rsPnt.getString(14));
				joPnt.put("bz1", rsPnt.getString(15));
				joPnt.put("bz2", rsPnt.getString(16));
				joPnt.put("fid", rsPnt.getInt(17));
				jaPnt.add(joPnt);
			}
			//获取线数据，并将线数据储存在一个jsonArray里面
			psLin = ctt.conn.prepareStatement(sqlLin);
			psLin.setLong(1, pid);
			rsLin = psLin.executeQuery();
			while(rsLin.next()) {
				JSONObject joLin = new JSONObject();
				joLin.put("pid", rsLin.getLong(1));
				joLin.put("qdbh", rsLin.getString(2));
				joLin.put("zdbh", rsLin.getString(3));
				joLin.put("qdgc", rsLin.getString(4));
				joLin.put("zdgc", rsLin.getString(5));
				joLin.put("qdms", rsLin.getString(6));
				joLin.put("zdms", rsLin.getString(7));
				joLin.put("gxxz", rsLin.getString(8));
				joLin.put("gj", rsLin.getString(9));
				joLin.put("cz", rsLin.getString(10));
				joLin.put("yljb", rsLin.getString(11));
				joLin.put("msfs", rsLin.getString(12));
				joLin.put("msrq", rsLin.getString(13));
				joLin.put("qsdw", rsLin.getString(14));
				joLin.put("szdl", rsLin.getString(15));
				joLin.put("prj", rsLin.getInt(16));
				joLin.put("bz", rsLin.getString(17));
				joLin.put("bz1", rsLin.getString(18));
				joLin.put("bz2", rsLin.getString(19));
				joLin.put("fid", rsLin.getInt(20));
				jaLin.add(joLin);
			}
			jo.put("pnt", jaPnt);
			jo.put("lin", jaLin);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rsLin != null) {
					rsLin.close();
				}
				if(psLin != null) {
					psLin.close();
				}
				if(rsPnt != null) {
					rsPnt.close();
				}
				if(psPnt != null) {
					psPnt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 调气量分析---最大压力分析
	 */
	@Override
	public void doAnaTQL(CxSvrContext ctt, JSONObject jo) throws Exception {
		String sql = "{? = call HKCT_GF2_GETSCALE(?, ?)}";
		String UpdateSql = "Update HKCT_GF_START2 set gasflow = ? * gasflow where pid = ?";
		String copySql = "insert into hkct_gf_start2 "
				+ "select fid,press,gasflow,?,usetype,bz,anadate,lv,node1_fid,node2_fid,temperature,?,gfadjust,gas_type "
				+ "from HKCT_GF_START2 t where pid = ?";
		CallableStatement cs = null;
		PreparedStatement ps = null;
		PreparedStatement copyPs = null;
		Long pid = Long.parseLong(ctt.req.getParameter("pid"));
		double maxGasflow = Double.parseDouble(ctt.req.getParameter("maxGasflow"));
		Long nextPid = Long.parseLong(ctt.req.getParameter("nextPid"));
		String gfName = ctt.req.getParameter("gfName");
		
		try {
			cs = ctt.conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.DOUBLE);
			cs.setLong(2, pid);
			cs.setDouble(3, maxGasflow);
			cs.executeUpdate();
			double count = cs.getDouble(1);
			
			if(count > 0) {
				copyPs = ctt.conn.prepareStatement(copySql);
				copyPs.setLong(1, nextPid);
				copyPs.setString(2, gfName);
				copyPs.setLong(3, pid);
				int copyNum = copyPs.executeUpdate();
				if(copyNum > 0) {
					ps = ctt.conn.prepareStatement(UpdateSql);
					ps.setDouble(1, count);
					ps.setLong(2, nextPid);
					int num = ps.executeUpdate();
					jo.put("result", num);
				}
			} else {
				jo.put("result", count);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取本地TXT文本
	 */
	@Override
	public void readTxt(CxSvrContext ctt, JSONObject jo) throws Exception {
		try {
			ArrayList<String> list = new ArrayList<String>();
			/*读取TXT 此处使用绝对路径*/
			String pathName = "D:\\apache-tomcat-7.0.55\\webapps\\gis\\plugin\\gw11\\HKCTAnalyst\\AE\\test.txt";
			File fileName = new File(pathName);
			InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(fileName), "utf-8"); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line = "";  
            line = br.readLine();
            while (line != null) {
            	list.add(line);
                line = br.readLine(); // 一次读入一行数据
            }
            list.trimToSize();//将动态数组转为固定数组，释放空余内存
            jo.put("txt", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获取爆管分析所有报表信息
	 */
	@Override
	public void getAEAllResult(CxSvrContext ctt, JSONArray ja)
			throws Exception {
		ArrayList<String []> listConfig = new ArrayList<String []>();//用来存储config表里的数据
		
		String sqlConfig = "select * from HKCT_REPORT_CONFIG order by id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		
		try {
			ps = ctt.conn.prepareStatement(sqlConfig);
			rs = ps.executeQuery();
			while(rs.next()) {
				String [] s = new String[2];
				s[0] = rs.getString(2);
				s[1] = rs.getString(3);
				listConfig.add(s);
			}
			
			//循环遍历sql语句配置表里面的数据，并通过sql查询来获取爆管的报表信息
			for(int i = 0; i < listConfig.size(); i++) {
				JSONObject jo = new JSONObject();
				JSONArray ja1 = new JSONArray();//报表单个sql查询结果
				ArrayList<String> listName = new ArrayList<String>();
				
				ArrayList<String> list = new ArrayList<String>();//存储config表每条数据中的name和sql
				String name = "", sql = "";
				String [] str = listConfig.get(i);
				for(String a : str) {
					list.add(a);
				}
				name = list.get(0);
				sql = list.get(1);
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;
				
				sql = sql + " where tid = " + String.valueOf(tid);
				ps1 = ctt.conn.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				ResultSetMetaData rsmd = rs1.getMetaData();
				while(rs1.next()) {
					ArrayList<String> listVaule = new ArrayList<String>();
					JSONObject jo1 = new JSONObject();
					for (int j = 1; j <= rsmd.getColumnCount(); j++) {
						if(listName.size() != rsmd.getColumnCount()) {
							listName.add(rsmd.getColumnName(j));
						}
						if(rs1.getString(rsmd.getColumnName(j)) != null) {
							listVaule.add(rs1.getString(rsmd.getColumnName(j)));
						} else {
							listVaule.add("");
						}
					}
					if(ja1.size() < 1) {
						if(listName != null) {
							ja1.add(listName);
							ja1.add(listVaule);
						} else {
							for (int j = 1; j <= rsmd.getColumnCount(); j++) {
								listName.add(rsmd.getColumnName(j));
							}
							ja1.add(listName);
						}
					} else {
						if(listName != null) {
							ja1.add(listVaule);
						} else {
							for (int j = 1; j <= rsmd.getColumnCount(); j++) {
								listName.add(rsmd.getColumnName(j));
							}
							ja1.add(listName);
						}
					}
					
				}
				if(ja1.size() <= 0) {
					for (int k = 1; k <= rsmd.getColumnCount(); k++) {
						listName.add(rsmd.getColumnName(k));
					}
					ja1.add(listName);
				}
				jo.put("name", name);
				jo.put("repData", ja1);
				ja.add(jo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * 爆管分析导出Excel报表
	 */
	//@Override
	public void reportAeExcel111(CxSvrContext ctt, JSONObject jo,
			HttpServletResponse res) throws Exception {
		//查询爆管分析报表的配置sql语句表
		String sql = "select describe, sqlstr from hkct_report_config";
		String sql1 = "";
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		String name = ctt.req.getParameter("name");
		Map<String, String> map = new LinkedHashMap<String, String>();
		//存储所有报表数据
		//Map<String, Map<String, String>> rfMap = new LinkedHashMap<String, Map<String,String>>();
		JSONObject rfMap = new JSONObject();
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				//将报表标题和对应的sql语句以键值对的形式存入map集合
				map.put(rs.getString(1), rs.getString(2));
			}
			//遍历map集合，循环取出报表对应的标题和数据
			Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
			while(it.hasNext()) 
			{
				Map.Entry<String, String> entry = it.next();
				String title = entry.getKey();//每个表的title
				String cfgSql = entry.getValue();//每个表的sql语句
				JSONArray ja = new JSONArray();//存过每个查询表的数据
				
				sql = cfgSql + " where tid = " + String.valueOf(tid);//查询报表数据
				ps1 = ctt.conn.prepareStatement(sql);//获取爆管分析各个报表的表结果
				rs1 = ps1.executeQuery();
				ResultSetMetaData rsmd = rs1.getMetaData();
				
				while(rs1.next()) 
				{
					//Map<String, String> cfgData = new LinkedHashMap<String, String>();
					JSONObject cfgData = new JSONObject();
					for(int i = 1; i <= rsmd.getColumnCount(); i++) 
					{
						if(rs1.getString(rsmd.getColumnName(i)) != null) 
						{
							cfgData.put(rsmd.getColumnName(i), rs1.getString(rsmd.getColumnName(i)));
						} else {
							cfgData.put(rsmd.getColumnName(i), "");
						}
					}
					ja.add(cfgData);
				}
//				if(ja.length() ==0)
//				{
//					Map<String, String> cfgData = new LinkedHashMap<String, String>();
//					for(int j = 0; j < rsmd.getColumnCount(); j++)
//					{
//						cfgData.put(rsmd.getColumnName(j), "");
//						ja.add(cfgData);
//					}
//				}
				rfMap.put(title, ja);
			}
			
			//导出excel
			
			//创建输出流
			OutputStream os = res.getOutputStream();
			//打开文件
			WritableWorkbook ww = Workbook.createWorkbook(os);
			//创建新的一页
			WritableSheet sheet1 = ww.createSheet("报表", 0);
			Label titleLabel;//单元格标题对象
			Label headLabel;//单元格表头对象
			Label contLabel;//单元格内容
			int colnum = 0;//列数计数
			int row = 0;//行数
			
			Iterator<String> iterator = rfMap.keys();//得到第一项的key集合---每个表的标题
			while(iterator.hasNext()) 
			{
				colnum = 0;
				//标题
				String key = iterator.next();
				titleLabel = new Label(colnum, row, key);//参数，，列、行、值
				sheet1.addCell(titleLabel);//每个表的标题
				row = row + 1;
				JSONArray jaData = rfMap.getJSONArray(key);
				if(jaData.size() != 0)
				{
					//表头
					JSONObject first = jaData.getJSONObject(0);
					Iterator<String> header = first.keys();//每个表的表头
					while(header.hasNext()) 
					{
						String k = header.next();
						headLabel = new Label(colnum++, row, k);
						sheet1.addCell(headLabel);
					}
					row = row + 1;
					//内容
					for(int i = 0; i < jaData.size(); i++)
					{
						JSONObject item = jaData.getJSONObject(i);//得到数组的每项
						Iterator<String> data = item.keys();//得到key集合
						colnum = 0;//从第0列开始放
						while(data.hasNext()) 
						{
							String k = data.next();
								String value = item.getString(k);
								contLabel = new Label(colnum++, row, value);         
								sheet1.addCell(contLabel);
						}
						row = row + 1;
					}
				}
			}
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Disposition", "attachment; filename=" + name + ".xls"); 
			ww.write(); // 加入到文件中
			ww.close(); // 关闭文件，释放资源
            os.close(); // 关闭输出流
			
			jo.put("result", "成功");
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs1 != null)
					rs.close();
				if(ps1 != null)
					ps.close();
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
//		jo.put("result", "成功");
	}

	
	public void reportAeExcel(CxSvrContext ctt, JSONObject jo,
			HttpServletResponse res) throws Exception {
		//查询爆管分析报表的配置sql语句表
		String sql = "select describe, sqlstr from hkct_report_config order by id";
		String sql1 = "";
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Long tid = Long.parseLong(ctt.req.getParameter("tid"));
		String name = ctt.req.getParameter("name");
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		//导出excel
		//创建输出流
		OutputStream os = res.getOutputStream();
		//打开文件
		WritableWorkbook ww = Workbook.createWorkbook(os);
		//创建新的一页
		WritableSheet sheet1 = ww.createSheet("报表", 0);
		Label titleLabel;//单元格标题对象
		Label headLabel;//单元格表头对象
		Label contLabel;//单元格内容
		int colnum = 0;//列数计数
		int row = 0;//行数
		
		try {
			ps = ctt.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				//将报表标题和对应的sql语句以键值对的形式存入map集合
				map.put(rs.getString(1), rs.getString(2));
			}
			
			//遍历map集合，循环取出报表每张表对应的标题和数据
			Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
			while(it.hasNext()) 
			{
				Map.Entry<String, String> entry = it.next();
				String title = entry.getKey();//每个表的title
				String cfgSql = entry.getValue();//每个表的sql语句
				JSONArray ja = new JSONArray();//存过每个查询表的数据
				
				sql = cfgSql + " where tid = " + String.valueOf(tid);//查询报表数据
				ps1 = ctt.conn.prepareStatement(sql);//获取爆管分析各个报表的表结果
				rs1 = ps1.executeQuery();
				ResultSetMetaData rsmd = rs1.getMetaData();
				
				colnum = 0;
				
				//标题
				//定义单元格样式
				WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 15,  
	                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
	                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色 
				
				WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义  
	            wcf_title.setBackground(jxl.format.Colour.YELLOW); // 设置单元格的背景颜色  
	            wcf_title.setAlignment(jxl.format.Alignment.LEFT); // 设置对齐方式  
	            wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); //设置边框 
	            
				titleLabel = new Label(colnum, row, title, wcf_title);//参数，，列、行、值
				sheet1.addCell(titleLabel);//每个表的标题
				sheet1.mergeCells(0, row, rsmd.getColumnCount() - 1, row);//列号，行号，从【1-2】向下合并的列数，【1-2】向下合并的行数
				sheet1.setRowView(row, 800);      //设置行的高度
				row = row + 1;
				
				//插入每张表的表头
				for(int i = 1; i <=rsmd.getColumnCount(); i++)
				{
					
					WritableFont wf_header = new WritableFont(WritableFont.ARIAL, 12,  
		                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
		                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
					WritableCellFormat wcf_header = new WritableCellFormat(wf_header); // 单元格定义  
					wcf_header.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色  
					wcf_header.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式  
					wcf_header.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); //设置边框 
					
					String k = rsmd.getColumnName(i);
					sheet1.setColumnView(colnum, 22);//设置列的宽度
					sheet1.setRowView(row, 600);      //设置行的高度
					headLabel = new Label(colnum++, row, k, wcf_header);
					sheet1.addCell(headLabel);
				}
				row = row + 1;
				
				//插入每张表的数据
				while(rs1.next()) 
				{
					colnum = 0;
					for(int i = 1; i <= rsmd.getColumnCount(); i++) 
					{
						if(rs1.getString(rsmd.getColumnName(i)) != null) 
						{
							WritableFont wf_cont = new WritableFont(WritableFont.ARIAL, 12,  
				                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
				                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
							WritableCellFormat wcf_cont = new WritableCellFormat(wf_cont); // 单元格定义  
							wcf_cont.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色  
							wcf_cont.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式  
							wcf_cont.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); //设置边框
				            
							sheet1.setRowView(row, 600);      //设置行的高度
							contLabel = new Label(colnum++, row, rs1.getString(rsmd.getColumnName(i)), wcf_cont);        
							sheet1.addCell(contLabel);
						} else {
							WritableFont wf_cont = new WritableFont(WritableFont.ARIAL, 12,  
				                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
				                    jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
							WritableCellFormat wcf_cont = new WritableCellFormat(wf_cont); // 单元格定义  
							wcf_cont.setBackground(jxl.format.Colour.WHITE); // 设置单元格的背景颜色  
							wcf_cont.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式  
							wcf_cont.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); //设置边框
							
							sheet1.setRowView(row, 600);      //设置行的高度
							contLabel = new Label(colnum++, row, "", wcf_cont);         
							sheet1.addCell(contLabel);
						}
					}
					row = row + 1; 
				}
				row = row + 1;
			}
			
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Disposition", "attachment; filename=" + name + ".xls"); 
			ww.write(); // 加入到文件中
			ww.close(); // 关闭文件，释放资源
            os.close(); // 关闭输出流
			
			jo.put("result", "成功");
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(rs1 != null)
					rs.close();
				if(ps1 != null)
					ps.close();
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
			} catch(Exception e) {
				
			}
		}
	}
	
	
	
	
	
	
	
}
