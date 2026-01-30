package com.waste.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.waste.bean.Citizen;
import com.waste.util.DBUtil;


public class CitizenDAO {
	public Citizen findCitizen(String citizenID)
	{
        try{
        	Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps =connection.prepareStatement("SELECT * FROM CITIZEN_TBL WHERE CITIZEN_ID=?");
            ps.setString(1,citizenID);
            ResultSet rs = ps.executeQuery();
                if(rs.next())
                {
                   	return new Citizen();
                }
            }catch(SQLException e)
              {
        	       e.printStackTrace();
              }
        return null;
    }
	
	public List<Citizen>viewAllCitizens()
	{
		List<Citizen> list = new ArrayList<>();
		try 
		{
			Connection connection=DBUtil.getDBConnection();
			PreparedStatement ps=connection.prepareStatement("SELECT * FROM CITIZEN_TBL");
			ResultSet rs=ps.executeQuery();
			    if(rs.next())
			    {
			          Citizen c=new Citizen();
			          c.setCitizenID(rs.getString("CITIZENT_ID"));
			          c.setFullName(rs.getString("FULL NAME"));
			          c.setStatus(rs.getString("STATUS"));
			          list.add(c);
			    }
		}catch(SQLException e)
		    {
		     	e.printStackTrace();
		    }
	   return list;
	}
	
	public boolean insertCitizen(Citizen citizen)
	{
			Connection connection = DBUtil.getDBConnection();
			try {
				PreparedStatement ps = connection.prepareStatement("INSERT INTO CITIZEN_TBL VALUES(?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, citizen.getCitizenID());
				ps.setString(2, citizen.getFullName());
				ps.setString(3,citizen.getHouseOrBuildingName());
				ps.setString(4, citizen.getStreetName());
				ps.setString(5,citizen.getAreaOrLocality());
				ps.setString(6,citizen.getWardCode());
				ps.setString(7, citizen.getRouteCode());
				ps.setString(8,citizen.getMobile());
				ps.setString(9, citizen.getEmail());
				ps.setString(10,citizen.getStatus());
			    return ps.executeUpdate() == 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
	}
	public boolean updateCitizenStatus(String citizenID,String status)
	{
		Connection connection=DBUtil.getDBConnection();
		try {
			PreparedStatement ps=connection.prepareStatement("UPDATE CITIZEN_TBL SET Status=? WHERE CITIZEN_ID=?");
			ps.setString(1, status);
			ps.setString(2, citizenID);
			return ps.executeUpdate()== 1;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteCitizen(String citizenID)
	{
		Connection connection = DBUtil.getDBConnection();
		try {
			PreparedStatement ps=connection.prepareStatement("DELETE FROM CITIZEN_TBL WHERE CITIZEN_ID=? ");
			ps.setString(1,citizenID);
			return ps.executeUpdate()==1;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
