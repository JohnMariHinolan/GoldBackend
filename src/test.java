
public class test {
	public static void main(String[] args){
		GraphQueriesObj gObj = new GraphQueriesObj("test/sample.graphdb");
		gObj.deleteLabel("PERSON");
		
	/****INSERT TEST****/////	
		//gObj.clientInsertNode("ROBINSON");
	//	gObj.clientInsertNode("YABU");
		//gObj.insertClientStore("ROBINSON", "SUPERMARKET");// should succeed
		//gObj.insertClientStore("ROBINSON_", "HANDYMAN");// should fail
		//gObj.insertPerson("userName", "p", "firstName", "middleName", "lastName");
		//gObj.insertPerson("slark", "rift", "firstName", "middleName", "lastName");
		System.out.println(gObj.insertBranchToClient("MALOLOS,Cubao","YABU" ));
		System.out.println(gObj.insertBranchToClient("Bartolina,Cubao","YABUBU" ));
		System.out.println(gObj.insertBranchToStore("Bartolina,Cubao","SUPERMARKET" ));
		System.out.println(gObj.insertBranchToStore("Bartolina,Cubao","HANDYMEN" ));
	/**** Checkers test***///
	//	System.out.println("SHOULD BE FALSE: " +gObj.checkUser("slark", "passWord"));
	//	System.out.println("SHOULD BE TRUE: " +gObj.checkUser("slark", "rift"));
	//	System.out.println("SHOULD BE FALSE: " +gObj.isClientExist("ROBISON"));
	//	System.out.println("SHOULD BE TRUE: " +gObj.isClientExist("ROBINSON"));
	//	System.out.println("SHOULD BE FALSE: " +gObj.isClientExist(""));
	//	System.out.println("SHOULD BE FALSE: " +gObj.clientHasStore(""));
	//	System.out.println("SHOULD BE TRUE: " +gObj.clientHasStore("ROBINSON"));
	//	System.out.println("SHOULD BE FALSE: " +gObj.isStoreExist("fdsf"));
	//	System.out.println("SHOULD BE TRUE: " +gObj.isStoreExist("HANDYMAN"));
		System.out.println("SHOULD BE FALSE: " +gObj.isBranchExistStore("Bartolina,Cubao","HANDYMEN"));
		System.out.println("SHOULD BE TRUE: " +gObj.isBranchExistStore("Bartolina,Cubao","SUPERMARKET" ));
		System.out.println("SHOULD BE FALSE: " +gObj.isBranchExist("Bartolina,Cubao","YABUBU"));
		System.out.println("SHOULD BE TRUE: " +gObj.isBranchExist("Bartolina,Cubao","YABU"));
	/**** GETTER TEST ***/
		//System.out.println(gObj.getAllClients(1000).toString());
		//System.out.println(gObj.getAllUsers(1000).toString());
		gObj.printStoreAndBranches();
		gObj.printClientAndBranches();
		
	}
}
