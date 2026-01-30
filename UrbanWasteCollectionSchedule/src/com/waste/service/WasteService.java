package com.waste.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.waste.bean.Citizen;
import com.waste.bean.WasteServiceRow;
import com.waste.dao.CitizenDAO;
import com.waste.dao.WasteServiceDAO;
import com.waste.util.ActiveComplaintsExistException;
import com.waste.util.ComplaintStatusException;
import com.waste.util.DBUtil;
import com.waste.util.ValidationException;

public class WasteService {
	private CitizenDAO citizenDAO = new CitizenDAO();
	private WasteServiceDAO wasteServiceDAO = new WasteServiceDAO();
    public Citizen viewCitizenDetails(String citizenID)
    {
    	if(citizenID == null || citizenID.trim().isEmpty())
    	{
    		return null;
    	}
    	return citizenDAO.findCitizen(citizenID);
    }
    public List<Citizen>viewAllCitizens()
    {
    	return citizenDAO.viewAllCitizens();
    }
    public boolean registerNewCitizen(Citizen citizen)
    {
    	try {
    	if(citizen == null || 
    			citizen.getCitizenID()==null || citizen.getCitizenID().trim().isEmpty() ||
    			citizen.getFullName()==null || citizen.getFullName().trim().isEmpty() || 
    			citizen.getHouseOrBuildingName()==null || citizen.getHouseOrBuildingName().trim().isEmpty()||
    			citizen.getStreetName()==null || citizen.getStreetName().trim().isEmpty()|| 
    			citizen.getWardCode()==null || citizen.getWardCode().trim().isEmpty()||
    			citizen.getRouteCode()==null || citizen.getRouteCode().trim().isEmpty()||
    			citizen.getMobile()==null || citizen.getMobile().trim().isEmpty())
    	{
    		throw new ValidationException();
    	}
    	String mobile=citizen.getMobile();
    	if(!mobile.matches("\\d{10,12}"))
    	{
    		throw new ValidationException();
    	}
    	Citizen existing = citizenDAO.findCitizen(citizen.getCitizenID());
    	if(existing != null) {
    	    System.out.println("Citizen already exists");
    	    return false;
    	}
    	if(citizen.getStatus() == null || citizen.getStatus().trim().isEmpty())
    	{
    		citizen.setStatus("ACTIVE");
    	}
    	}catch(ValidationException e)
    	{
    		e.printStackTrace();
    	}
    	return citizenDAO.insertCitizen(citizen);
    }
    public boolean logScheduledVisit(String wardCode,String routeCode,Date scheduledDate,String vehicleNo,String crewShift)
    {
    	try {
    		if(wardCode == null|| wardCode.trim().isEmpty()||
    		   routeCode == null || routeCode.trim().isEmpty()||
    		   vehicleNo == null || vehicleNo.trim().isEmpty()||
    		   crewShift == null || crewShift.trim().isEmpty()||
    		   scheduledDate == null )
    			    throw new ValidationException();
    	}catch(ValidationException e)
    		{
    			e.printStackTrace();
    			return false;
    		}
    		Connection connection = null;
    		try
    		{
    			connection = DBUtil.getDBConnection();
    			connection.setAutoCommit(false);
    			WasteServiceRow row=new WasteServiceRow();
    			row.setServiceType("SCHEDULED_VISIT");
    			row.setCitizenId(null);
    			row.setWardCode(wardCode);
    			row.setRouteCode(routeCode);
    			row.setScheduledDate(scheduledDate);
    			row.setVehicleNo(vehicleNo);
    			row.setCrewShift(crewShift);
                row.setComplaintDate(null);
    	        row.setComplaintDescription(null);
    	        row.setPriorityLevel(null);
    	        row.setComplaintStatus(null);
    	        row.setComplaintType(null);
    	        row.setClosureRemarks(null);
    	        
    			WasteServiceDAO dao=new WasteServiceDAO();
    			int serviceRowID=dao.generateServiceRowID();
    			row.setServiceRowId(serviceRowID);
    			
    			boolean inserted = dao.insertServiceRow(row);
    			
    			if(inserted)
    			{
    				connection.commit();
    				return true;
    			}
    			else
    			{
    				connection.rollback();
    				return false;
    			}
    		}catch(Exception e)
    		{
    			try {
    				       if(connection != null)
    				       {
    					         connection.rollback();
    					         connection.setAutoCommit(true);
    				       }
    				       
    			    } catch(SQLException se)
    			      {
    				       se.printStackTrace();
    			      }
    		}
			return false;
    }
    public boolean registerComplaint(String citizenID,String complaintType,String complaintDescription,String priorityLevel,Date complaintDate,Date relatedScheduledDate)
    {
    	Connection connection = null;
    	try {
    		       if(citizenID == null || citizenID.trim().isEmpty()||
    		          complaintType == null || complaintType.trim().isEmpty()||
    		          complaintDescription == null || complaintDescription.trim().isEmpty()|| 
    		          priorityLevel == null || priorityLevel.trim().isEmpty()||
    		          complaintDate ==  null)
    		         {
    			           throw new ValidationException();
    	             }
    		         Citizen citizen = citizenDAO.findCitizen(citizenID); 
    		         if(citizen == null || !"ACTIVE".equalsIgnoreCase(citizen.getStatus()))
    		         {
    			          return false;
    		         }
    		         if(relatedScheduledDate != null && complaintDate.before(relatedScheduledDate))
    		         {
    			          throw new ValidationException();
    	           	 }
    		WasteServiceRow row=new WasteServiceRow();
    		row.setServiceType("COMPLAINT");
    		row.setCitizenId(citizenID);
    		row.setWardCode(citizen.getWardCode());
    		row.setRouteCode(citizen.getRouteCode());
    		row.setComplaintDate(complaintDate);
    		row.setComplaintType(complaintType);
    		row.setComplaintDescription(complaintDescription);
    		row.setPriorityLevel(priorityLevel);
    	    row.setComplaintStatus("OPEN");
    	    row.setScheduledDate(null);
    	    row.setClosureRemarks(null);
    	    row.setVehicleNo(null);
    	    row.setCrewShift(null);
    	    connection=DBUtil.getDBConnection();
    	    connection.setAutoCommit(false);
    	    WasteServiceDAO dao=new WasteServiceDAO();
    	    int serviceRowId = dao.generateServiceRowID();
    	    row.setServiceRowId(serviceRowId);
    	    boolean inserted = dao.insertServiceRow(row);
    	    if(inserted)
    	    {
    	    	connection.commit();
    	    	return true;
    	    }
    	    else
    	    {
    	    	connection.rollback();
    	    	return false;
    	    }
    	}catch(ValidationException ve)
    	{
    		ve.printStackTrace();
    		return false;
    	}catch(Exception e)
    	{
    		try {
    			if(connection != null)
    			{
    				connection.rollback();
    			}
    		}catch(SQLException se)
    		{
    			se.printStackTrace();
    		}
    		e.printStackTrace();
    		return false;
    	}finally {
    		try {
    			if(connection != null)
    			{
    				connection.setAutoCommit(true);
    				connection.close();
    			}
    		}catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
    	}
    }
    public boolean updateComplaintStatus(int serviceRowID,String newStatus,String closureRemarks)
    {
    	Connection connection =null;
    	try{
    		if(serviceRowID <= 0 || newStatus == null || !(newStatus.equalsIgnoreCase("OPEN")|| newStatus.equalsIgnoreCase("IN_PROGRESS") || newStatus.equalsIgnoreCase("RESOLVED") || newStatus.equalsIgnoreCase("CLOSED")))
    		{
    			throw new ValidationException();
    		}
    		WasteServiceRow existingRow =wasteServiceDAO.findServiceRow(serviceRowID);
            if (existingRow == null ||!"COMPLAINT".equalsIgnoreCase(existingRow.getServiceType())) 
            {
                throw new ComplaintStatusException();
            }
            if ("CLOSED".equalsIgnoreCase(existingRow.getComplaintStatus())) 
            {
                throw new ComplaintStatusException();
            }
            connection = DBUtil.getDBConnection();
            connection.setAutoCommit(false);
            boolean updated =wasteServiceDAO.updateComplainStatusAndClosure(serviceRowID, newStatus, closureRemarks);
            if (updated) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (ValidationException | ComplaintStatusException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<WasteServiceRow>listComplaintsByCitizen(String citizenID)
    {
    		WasteServiceDAO dao = new WasteServiceDAO();
			return dao.findComplaintsByCitizen(citizenID);
    }
    public boolean deactivateOrRemoveCitizen(String citizenID)
    {
    	 try {
    	        if (citizenID == null || citizenID.trim().isEmpty()) {
    	            throw new ValidationException();
    	        }
    	        List<WasteServiceRow> openComplaints =wasteServiceDAO.findOpenComplaintsByCitizen(citizenID);
    	        if (openComplaints != null && !openComplaints.isEmpty()) {
    	            throw new ActiveComplaintsExistException();
    	        }
    	        return citizenDAO.updateCitizenStatus(citizenID, "INACTIVE");
    	    } catch (ValidationException | ActiveComplaintsExistException e) {
    	        e.printStackTrace();
    	        return false;
    	    }catch (Exception e) {
    	        e.printStackTrace();
    	        return false;
    	    }
      }
}


