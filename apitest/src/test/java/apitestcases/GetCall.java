package apitestcases;


	import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import RestClient.RestClient;
import TestBase.TestBase;
import  Util.TestUtil;


	public class GetCall extends TestBase{
		TestBase testBase;
		String serviceUrl;
		String apiUrl;
		String url;
		RestClient restClient;
		CloseableHttpResponse closebaleHttpResponse;
		
	@BeforeMethod
		public void setUp() throws ClientProtocolException, IOException{
			testBase = new TestBase();
			serviceUrl = prop.getProperty("URL");
			apiUrl = prop.getProperty("serviceURL");
			//https://reqres.in/api/users
			
			url = serviceUrl + apiUrl;
			
		}
		
		
		
		@Test(priority=1)
		public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException{
			restClient = new RestClient();
			closebaleHttpResponse = ((RestClient) restClient).get(url);
			
		
			int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
			System.out.println("Status Code--->"+ statusCode);
			
			AssertJUnit.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);

		
			String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
			
			JSONObject responseJson = new JSONObject(responseString);
			System.out.println("Response JSON from API---> "+ responseJson);
			
			String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
			System.out.println("value of per page is-->"+ perPageValue);
			AssertJUnit.assertEquals(Integer.parseInt(perPageValue), 3);
			
			String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
			System.out.println("value of total is-->"+ totalValue);		
			AssertJUnit.assertEquals(Integer.parseInt(totalValue), 12);

		
			String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
			String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
			String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
			String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

			System.out.println(lastName);
			System.out.println(id);
			System.out.println(avatar);
			System.out.println(firstName);
			
			
			Header[] headersArray =  closebaleHttpResponse.getAllHeaders();
			HashMap<String, String> allHeaders = new HashMap<String, String>();	
			for(Header header : headersArray){
				allHeaders.put(header.getName(), header.getValue());
			}	
			System.out.println("Headers Array-->"+allHeaders);
			
			
			
		}
		

}
