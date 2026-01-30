package com.waste.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.waste.bean.WasteServiceRow;
import com.waste.util.DBUtil;

public class WasteServiceDAO {
    public int generateServiceRowID()
    {
    	Connection connection=DBUtil.getDBConnection();
    	String sql="select WASTE_SEQ.NEXTVAL from dual";
    	try {
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        int serviceRow=rs.getInt(1);
            return serviceRow;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
    }
    public boolean insertServiceRow(WasteServiceRow row)
    {
    	Connection connection=DBUtil.getDBConnection();
    	try {
    		PreparedStatement ps=connection.prepareStatement("INSERT INTO WASTE_SERVICE_TBL VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    		ps.setInt(1, row.getServiceRowId());
    		ps.setString(2, row.getCitizenId());
    		ps.setString(3, row.getServiceType());
    		ps.setString(4, row.getWardCode());
    		ps.setString(5, row.getRouteCode());
    		ps.setDate(6,row.getScheduledDate());
    		ps.setString(7, row.getVehicleNo());
    		ps.setString(8,row.getCrewShift());
    		ps.setDate(9, row.getComplaintDate());
    		ps.setString(10, row.getComplaintType());
    		ps.setString(11, row.getComplaintDescription());
    		ps.setString(12,row.getPriorityLevel());
    		ps.setString(13, row.getComplaintStatus());
    		ps.setString(14,row.getClosureRemarks());
    		return ps.executeUpdate()==1;
    	}catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	return false;
    }
    public WasteServiceRow findServiceRow(int serviceRowID)
    {
    	Connection connection=DBUtil.getDBConnection();
    	try {
    		PreparedStatement ps=connection.prepareStatement("SELECT * FROM WASTE_SERVICE_TBL WHERE SERVICE_ROW_ID=?");
    		ps.setInt(1, serviceRowID);
    		ResultSet rs=ps.executeQuery();
    		if (rs.next()) {
                WasteServiceRow row = new WasteServiceRow();
                row.setServiceRowId(rs.getInt("SERVICE_ROW_ID"));
                row.setCitizenId(rs.getString("CITIZEN_ID"));
                row.setServiceType(rs.getString("SERVICE_TYPE"));
                row.setWardCode(rs.getString("WARD_CODE"));
                row.setRouteCode(rs.getString("ROUTE_CODE"));
                row.setScheduledDate(rs.getDate("SCHEDULED_DATE"));
                row.setVehicleNo(rs.getString("VEHICLE_NO"));
                row.setCrewShift(rs.getString("CREW_SHIFT"));
                row.setComplaintDate(rs.getDate("COMPLAINT_DATE"));
                row.setComplaintType(rs.getString("COMPLAINT_TYPE"));
                row.setComplaintDescription(rs.getString("COMPLAINT_DESCRIPTION"));
                row.setPriorityLevel(rs.getString("PRIORITY_LEVEL"));
                row.setComplaintStatus(rs.getString("COMPLAINT_STATUS"));
                row.setClosureRemarks(rs.getString("CLOSURE_REMARKS"));
            }
    	}catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
    public List<WasteServiceRow>findComplaintsByCitizen(String CitizenID)
    {
    	List<WasteServiceRow>list = new ArrayList<>();
    	Connection connection=DBUtil.getDBConnection();
    	try {
    		PreparedStatement ps=connection.prepareStatement("SELECT * FROM WASTE_SERVICE_TBL WHERE SERVICE_TYPE='COMPLAINT' AND CITIZEN_ID=?");
    		ps.setString(1, CitizenID);
    		ResultSet rs=ps.executeQuery();
    		if(rs.next())
    		{
    			WasteServiceRow r=new WasteServiceRow();
    			r.setServiceRowId(rs.getInt("SERVICE_ROW_ID"));
    			r.setComplaintStatus(rs.getString("COMPLAINT_STATUS"));
    			list.add(r);
    		}
    	}catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	return list;
    }
    public List<WasteServiceRow>findOpenComplaintsByCitizen(String CitizenID)
    {
    	List<WasteServiceRow>list=new ArrayList<>();
    	Connection connection=DBUtil.getDBConnection();
    	try {
    		PreparedStatement ps=connection.prepareStatement("SELECT * FROM WASTE_SERVICE_TBL WHERE CITIZEN_ID=? AND COMPLAINT_STATUS IN ('OPEN','IN_PROGRESS')");
    		ps.setString(1,CitizenID);
    		ResultSet rs=ps.executeQuery();
    		if(rs.next())
    		{
    			WasteServiceRow r=new WasteServiceRow();
    			r.setServiceRowId(rs.getInt("SERVICE_ROW_ID"));
    			list.add(r);
    		}
    	}catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	return list;
    }
    public boolean updateComplainStatusAndClosure(int serviceRowID,String newStatus,String closureRemarks)
    {
    	Connection connection=DBUtil.getDBConnection();
    	try {
    		PreparedStatement ps=connection.prepareStatement("UPDATE WASTE_SERVICE_TBL SET COMPLAINT_STATUS=?,CLOSURE_REMARKS=? WHERE SERVICE_ROW_ID=?");
    		ps.setString(1, newStatus);
    		ps.setString(2, closureRemarks);
    		ps.setInt(3, serviceRowID);
    		return ps.executeUpdate()== 1;
    	}catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	return false;
    }
}
