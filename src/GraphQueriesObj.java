import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.embedded.objects.ClientObj;
import com.embedded.objects.PersonObj;

public class GraphQueriesObj {

	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;

	public GraphQueriesObj(String DB_PATH) {

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		registerShutdownHook(graphDb);

	}

	public void clientInsertNode(String clienName) {
		if ("".equals(clienName)) {
			return;
		} else if (isClientExist(clienName)) {
			System.out.println("CLIENT ALREADY EXIST !");
			return;

		}
		StringBuilder qb = new StringBuilder();

		qb.append("MERGE (c:CLIENT_NODE{clientId:'" + clienName
				+ "'}) RETURN c.clientId AS clientId");

		Result result = executeQuery(qb.toString());
		while (result.hasNext()) {

			Map<String, Object> row = result.next();
			System.out.println(row.get("clientId"));
		}
	}


	
	public void insertClientStore(String clienName, String storeName) {

		if (!(isClientExist(clienName))) {
			System.out.println(clienName + " DOESNT EXIST");
			return;

		}
		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (c:CLIENT_NODE{clientId:'" + clienName + "'}) ");
		qb.append("MERGE (c)<-[r:STORE_OF]-(s:STORE_NODE) ");
		qb.append("SET s.storeName='" + storeName + "' ");
		qb.append("RETURN s.storeName AS storeName ");
		Result result = executeQuery(qb.toString());
		while (result.hasNext()) {

			Map<String, Object> row = result.next();
			System.out.println("STORE NAME: " + row.get("storeName"));
		}

	}

	public String insertBranchToStore(String location,String storeName) {
if ("".equals(location)) {
			
			return "Location is Blank";
		}
		if(!isStoreExist(storeName))
		{
		
			return "Store does not exist";
		}
		if(isBranchExistStore(storeName,location))
		{
		
			return "Branch for that store already exist ";
		}
		
		
		StringBuilder qb = new StringBuilder();
		qb.append("Match (c:STORE_NODE{storeName:'"+storeName+"'}) ");
		qb.append("MERGE (b:BRANCH_NODE{branchLoc:'"+location+"'}) ");
		qb.append("MERGE (c)<-[:BRANCH_OF]-(b) RETURN b ");
		
		Result result = executeQuery(qb.toString());
		if (result.hasNext()) {

			Map<String, Object> row = result.next();
			if(location.equals(row.get("b.branchLoc")))
			{
				return "Branch in " + location + " is added to " + storeName;
			}
		}
		
		return "failed";
		
	}

	public String insertBranchToClient(String location,String clientName) {
		if ("".equals(location)) {
			
			return "Location is Blank";
		}
		if(!isClientExist(clientName))
		{
		
			return "Client does not exist";
		}
		if(isBranchExist(clientName,location))
		{
		
			return "Branch for that client already exist ";
		}
		
		
		StringBuilder qb = new StringBuilder();
		qb.append("Match (c:CLIENT_NODE{clientId:'"+clientName+"'}) ");
		qb.append("MERGE (b:BRANCH_NODE{branchLoc:'"+location+"'}) ");
		qb.append("MERGE (c)<-[:BRANCH_OF]-(b) RETURN c ");
		
		Result result = executeQuery(qb.toString());
		System.out.println("BRANCH_LOCATION: " + result.next().get("c.clientId"));
		if (result.hasNext()) {

			Map<String, Object> row = result.next();
			if(location.equals(row.get("c.clientId")))
			{
				return "Branch in " + location + " is added to " + clientName;
			}
		}
		
		return "failed";
	}
	
	public String insertQuotation(String quotationName,Date date,String branchName,String clientOrstore){
		if("".equals(branchName)){
			return "BranchNme is blank ";
		}
		else if("".equals(quotationName)){
			return "Quotation is blank ";
		}
		else if("".equals(clientOrstore)){
			return "clientOrstore is blank ";
		}
		
		StringBuilder qb = new StringBuilder();
		if(isStoreExist(clientOrstore))
		{
			if(!isBranchExistStore(branchName,clientOrstore)){
				return "Branch Not in Store ";
			}
			qb.append("MATCH (store:STORE_NODE{storeName:'"+clientOrstore+"'})<-[:BRANCH_OF]");
			qb.append("-(br:BRANCH_NODE{branchLoc:'"+branchName+"'}) ");
			qb.append("MERGE (br)<-[:QUOTATION_OF]-(quote:QUOTATION_NODE)" );
			qb.append("SET quote.quotationName='"+quotationName+"' ");
			qb.append("SET quote.date='"+date+"' ");
			
			return "ADDING SUCCESS";
		}
		else if(isClientExist(clientOrstore))
		{
			if(!isBranchExist(branchName,clientOrstore)){
				return "Branch Not in Store ";
			}
			qb.append("MATCH (store:CLIENT_NODE{storeName:'"+clientOrstore+"'})<-[:BRANCH_OF]");
			qb.append("-(br:BRANCH_NODE{branchLoc:'"+branchName+"'}) ");
			qb.append("MERGE (br)<-[:QUOTATION_OF]-(quote:QUOTATION_NODE)" );
			qb.append("SET quote.quotationName='"+quotationName+"' ");
			qb.append("SET quote.date='"+date+"' ");
			
			return "ADDING SUCCESS";
		}
		else
		return "failed";
	}
	

	public void insertPerson(String userName, String passWord,
			String firstName, String middleName, String lastName) {

		if ("".equals(userName)) {
			System.out.println(userName + "is blank! ");
			return;

		} else if (isUserNameExist(userName)) {
			System.out.println("Username already exist!");
			return;
		} else if ("".equals(passWord)) {
			System.out.println("Password is blank!");
			return;
		}

		StringBuilder qb = new StringBuilder();

		qb.append("MERGE (p:PERSON_NODE{userName:'" + userName + "',passWord:'"
				+ passWord + "',firstName:'" + firstName + "',middleName:'"
				+ middleName + "',lastName:'" + lastName + "'}) ");
		qb.append("RETURN p");
		Result result = executeQuery(qb.toString());
		while (result.hasNext()) {

			Map<String, Object> row = result.next();
			System.out.println("USER INPUT SUCCESS");
		}

	}

	public boolean checkUser(String userName, String passWord) {
		if ("".equals(userName)) {
			System.out.println(userName + "is blank! ");
			return false;

		} else if (!isUserNameExist(userName)) {
			System.out.println("Username doesnt Exist!");
			return false;
		} else if ("".equals(passWord)) {
			System.out.println("Password is blank!");
			return false;
		}
		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (p:PERSON_NODE) ");
		qb.append("WHERE p.userName='" + userName + "' AND " + "p.passWord='"
				+ passWord + "' ");
		qb.append("RETURN p");
		Result result = executeQuery(qb.toString());

		if (result.hasNext())
			return true;
		else
			return false;
	}

	public PersonObj[] searchPerson(String userName) {

		return null;
	}

	/***** GETTERS ******/

	public ClientObj[] getAllClients(int limit) {
		ArrayList<ClientObj> cObj = new ArrayList<ClientObj>();
		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (p:CLIENT_NODE)  ");
		qb.append("RETURN p.clientId As clientId ORDER BY p.clientId limit "
				+ limit);
		System.out.println("/****CLIENT****/");
		Result result = executeQuery(qb.toString());

		while (result.hasNext()) {
			ClientObj co = new ClientObj();
			co.setClientName((String) result.next().get("clientId"));
			cObj.add(co);

		}
		ClientObj[] array = new ClientObj[cObj.size()];
		for (int i = 0; i < cObj.size(); i++) {
			array[i] = cObj.get(i);
		}
		return array;

	}

	public PersonObj[] getAllUsers(int limit) {
		StringBuilder qb = new StringBuilder();
		ArrayList<PersonObj> pObj = new ArrayList<PersonObj>();
		qb.append("MATCH (p:PERSON_NODE) ");
		qb.append("RETURN p.userName as userName,p.passWord as passWord,p.firstName As firstName,p.middleName As middleName, p.lastName As lastName  ORDER BY p.userName  limit "
				+ limit);

		Result result = executeQuery(qb.toString());

		while (result.hasNext()) {
			Map<String, Object> row = result.next();
			PersonObj po = new PersonObj();
			po.setFirstName((String) row.get("firstName"));
			po.setLastName((String) row.get("lastName"));
			po.setMiddleName((String) row.get("middleName"));
			po.setUserName((String) row.get("userName"));
			po.setPassWord((String) row.get("passWord"));
			pObj.add(po);
		}
		PersonObj[] array = new PersonObj[pObj.size()];
		for (int i = 0; i < pObj.size(); i++) {
			array[i] = pObj.get(i);
		}
		return array;
	}

	public void printStoreAndBranches(){
		StringBuilder qb = new StringBuilder();
		qb.append("OPTIONAL MATCH (client)<-[:STORE_OF]-(store)<-[:BRANCH_OF]-(branch) ");
		qb.append("RETURN client.clientId As clientId, store.storeName AS storeName, branch.branchLoc As branchLoc ORDER BY clientId ");
		Result result = executeQuery(qb.toString());
		
		while(result.hasNext())
		{
			Map<String,Object> str = result.next();
			System.out.println("CLIENT: " + str.get("clientId") +" STORE: " + str.get("storeName") +" BRANCH: " + str.get("branchLoc"));
			
		}
	}
	public void printClientAndBranches(){
		StringBuilder qb = new StringBuilder();
		qb.append("OPTIONAL MATCH (client)<-[:BRANCH_OF]-(branch) ");
		qb.append("RETURN client.clientId As clientId, branch.branchLoc As branchLoc ORDER BY clientId ");
		Result result = executeQuery(qb.toString());
		
		while(result.hasNext())
		{
			Map<String,Object> str = result.next();
			System.out.println("CLIENT: " + str.get("clientId")  +" BRANCH: " + str.get("branchLoc"));
			
		}
	}
	
	/***** CHECKERS *****/

	public boolean isBranchExist(String branchName,String clientName){
		if("".equals(clientName))
		{return false;}
		if("".equals(branchName))
		{return false;}
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (c:CLIENT_NODE{clientId:'"+clientName+"'})<-[:BRANCH_OF]-");
		qb.append("(b:BRANCH_NODE{branchLoc:'"+branchName+"'}) RETURN b ");
		Result result = executeQuery(qb.toString());
		if (result.hasNext())
			return true;
		else
			return false;
		
	}
	public boolean isBranchExistStore(String branchName,String clientName){
		if("".equals(clientName))
		{return false;}
		if("".equals(branchName))
		{return false;}
		StringBuilder qb = new StringBuilder();
		qb.append("MATCH (c:STORE_NODE{storeName:'"+clientName+"'})<-[:BRANCH_OF]-");
		qb.append("(b:BRANCH_NODE{branchLoc:'"+branchName+"'}) RETURN b.branchLoc ");
		Result result = executeQuery(qb.toString());
		if (result.hasNext())
			return true;
		else
			return false;
		
	}
	
	public boolean isStoreExist(String storeName) {
		if ("".equals(storeName)) {
			return false;
		}
		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (c:STORE_NODE{storeName:'" + storeName
				+ "'}) RETURN c");

		Result result = executeQuery(qb.toString());

		if (result.hasNext()) {
			return true;
		} else
			return false;
	}

	public boolean isUserNameExist(String userName) {
		if ("".equals(userName)) {
			return false;
		}
		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (c:PERSON_NODE{userName:'" + userName
				+ "'}) RETURN c.userName AS clientId,c.passWord As pa");

		Result result = executeQuery(qb.toString());

		if (result.hasNext()) {
			return true;
		} else
			return false;

	}

	public boolean isClientExist(String clienName) {
		if ("".equals(clienName)) {
			return false;
		}
		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (c:CLIENT_NODE{clientId:'" + clienName
				+ "'}) RETURN c.clientId AS clientId");

		Result result = executeQuery(qb.toString());

		if (result.hasNext())
			return true;
		else
			return false;

	}

	public boolean clientHasStore(String clienName) {
		if ("".equals(clienName)) {
			return false;
		}

		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (c:CLIENT_NODE{clientId:'" + clienName
				+ "'})<-[r:STORE_OF]-(e:STORE_NODE) ");
		qb.append("RETURN c.clientId AS clientId");
		Result result = executeQuery(qb.toString());
		if (result.hasNext())
			return true;
		else
			return false;

	}

	/**** EXTRA ******/

	public void deleteLabel(String label) {

		StringBuilder qb = new StringBuilder();

		qb.append("MATCH (x:" + label + ") ");
		qb.append("DELETE x");
		Result result = executeQuery(qb.toString());

	}

	/**** EXECUTE CODE ****/

	public Result executeQuery(String query) {
		try (Transaction tx = graphDb.beginTx()) {
			// Database operations go here
			Result result = graphDb.execute(query);

			tx.success();

			return result;

		}

	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

}
