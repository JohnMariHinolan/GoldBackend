package com.embedded.objects;

import java.util.Date;

public class Awarded extends Quotation {

	String prNum;
	String awardedDate;
	String conforme;
	String awardedBy;
	
	public Awarded(){
		
	}
	
	public Awarded(Quotation quotation, String prNum, String awardedBy){
		
		super(quotation.getClientName(),quotation.getStoreName(),quotation.getBranchName(),quotation.getQuotationName(),quotation.getStatus(),quotation.getDateGiven() );
		this.prNum = prNum;
		this.awardedBy =awardedBy;
	}
	
	public Awarded(Quotation quotation, String conforme){
		
		super(quotation.getClientName(),quotation.getStoreName(),quotation.getBranchName(),quotation.getQuotationName(),quotation.getStatus(),quotation.getDateGiven() );
		this.conforme = conforme;
	
	}
	
	public Awarded(String clientName,String storeName,String branchName,String quotationName,String status,Date dateGiven ){
		super(clientName,storeName,branchName,quotationName,status,dateGiven);
		
	}
	
	

	public String getPrNum() {
		return prNum;
	}

	public void setPrNum(String prNum) {
		this.prNum = prNum;
	}

	public String getAwardedDate() {
		return awardedDate;
	}

	public void setAwardedDate(String awardedDate) {
		this.awardedDate = awardedDate;
	}

	public String getConforme() {
		return conforme;
	}

	public void setConforme(String conforme) {
		this.conforme = conforme;
	}

	public String getAwardedBy() {
		return awardedBy;
	}

	public void setAwardedBy(String awardedBy) {
		this.awardedBy = awardedBy;
	}
	
}
