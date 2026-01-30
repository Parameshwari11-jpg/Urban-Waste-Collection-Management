package com.waste.bean;

import java.sql.Date; 
public class WasteServiceRow {
    private int serviceRowId;
    private String citizenId;
    private String serviceType;
    private String wardCode;
    private String routeCode;
    private Date scheduledDate;
    private String vehicleNo;
    private String crewShift;
    private Date complaintDate;
    private String complaintType;
    private String complaintDescription;
    private String priorityLevel;
    private String complaintStatus;
    private String closureRemarks;
	public int getServiceRowId() {
		return serviceRowId;
	}
	public void setServiceRowId(int serviceRowId) {
		this.serviceRowId = serviceRowId;
	}
	public String getCitizenId() {
		return citizenId;
	}
	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getWardCode() {
		return wardCode;
	}
	public void setWardCode(String wardCode) {
		this.wardCode = wardCode;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getCrewShift() {
		return crewShift;
	}
	public void setCrewShift(String crewShift) {
		this.crewShift = crewShift;
	}
	public Date getComplaintDate() {
		return complaintDate;
	}
	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}
	public String getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public String getComplaintDescription() {
		return complaintDescription;
	}
	public void setComplaintDescription(String complaintDescription) {
		this.complaintDescription = complaintDescription;
	}
	public String getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(String priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public String getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getClosureRemarks() {
		return closureRemarks;
	}
	public void setClosureRemarks(String closureRemarks) {
		this.closureRemarks = closureRemarks;
	}
}
