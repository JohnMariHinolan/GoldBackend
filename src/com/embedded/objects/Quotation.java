package com.embedded.objects;

import java.util.Date;

public class Quotation {

	String clientName;
	String storeName;
	String branchName;
	String quotationName;
	String status;
	Date dateGiven;
	
	public Quotation(){}
	
	public Quotation(String clientName,String storeName,String branchName,String quotationName,String status,Date dateGiven ){
		
		this(clientName,branchName,quotationName,status,dateGiven);
		this.storeName = storeName;
		
	}
	public Quotation(String clientName,String branchName,String quotationName,String status,Date dateGiven ){
		this.clientName = clientName;
		this.branchName = branchName;
		this.quotationName = quotationName;
		this.status = status;
		this.dateGiven = dateGiven;
		
	}
	
	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public String getQuotationName() {
		return quotationName;
	}


	public void setQuotationName(String quotationName) {
		this.quotationName = quotationName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getDateGiven() {
		return dateGiven;
	}


	public void setDateGiven(Date dateGiven) {
		this.dateGiven = dateGiven;
	}


	
	
	
}
