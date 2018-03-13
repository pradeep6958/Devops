
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
/*import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;*/
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.selenium.util.EclipseConnector;

public class TripshareFlow1 implements Runnable {
	private String deviceID;
	private RemoteWebDriver driver;
	private String NTNET_ID = "pgouda@amdocs.com";
	private String pass = "Blossom@789";
/*	private String NTNET_ID2="pritamc@amdocs.com";
	private String passwo2 = "Amdocs&123";*/

	private int index;

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Test Flow for CSR start....");
		List<String> devicesIDs = new LinkedList();
		
		// samsung galaxy device
	//devicesIDs.add("9EB54791");
		
	//samsung galaxy s5-sm
devicesIDs.add("55F09C6C855C13BFED4C2189173EB7DF43F15EFC");
		//iPhone 7--13-11-2017
//devicesIDs.add("0715F7752C4B3038");
    
  //devicesIDs.add("632E9899");
	//883A694706D73CD320FEFEC47F3ECF69BD97C9CD
	//devicesIDs.add("883A694706D73CD320FEFEC47F3ECF69BD97C9CD");
		
		ExecutorService executor = Executors.newFixedThreadPool(devicesIDs.size());
		for (int i = 0; i < devicesIDs.size(); i++) {
			TripshareFlow1 testFlow4 = new TripshareFlow1(devicesIDs.get(i));
			executor.execute(testFlow4);
		}
	}
	
	public TripshareFlow1(String deviceID) 
	{
		this.deviceID = deviceID;
	}

	@Override
	public void run() {

		// Start the application

		// Object result1 = driver.executeScript("mobile:application:open",
		// params1);

		System.out.println("Run started");
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		String host = "partners.perfectomobile.com";
		
		
		capabilities.setCapability("user", "tester1@ad-wisory.com");
		capabilities.setCapability("password", "Test123");
		capabilities.setCapability("deviceName", deviceID);

		capabilities.setCapability("autoLaunch", true);
		capabilities.setCapability("outputVideo", false);

		// Use the automationName capability to define the required framework -
		// Appium (this is the default) or PerfectoMobile.
		// capabilities.setCapability("automationName", "PerfectoMobile");

		// When Working in Amdocs LAN
		System.getProperties().put("http.proxyHost", "genproxy");
		System.getProperties().put("http.proxyPort", "8080");
		 System.getProperties().put("https.proxyHost", "genproxy");
		 System.getProperties().put("https.proxyPort", "8080");

		try {
			setExecutionIdCapability(capabilities, host);
			driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"),capabilities);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			 // Reporting client
	        /*PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
	                
	                .withProject(new Project("Sample Reportium project", "1.0"))
	               .withContextTags("AndroidNativeAppTests")
	             
	                .withWebDriver(driver)
	                .build();
	        ReportiumClient reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
*/
			// Check iOS or Android:
			index = 1;
			Map<String, Object> deviceOS = new HashMap<>();
			deviceOS.put("property", "os");
			String os = (String) driver.executeScript("mobile:handset:info", deviceOS);
			if (os.equals("Android"))
				index = 2;
			else
				index = 1;

			System.out.println(deviceID + " Go to home screen");
			Map<String, Object> params2 = new HashMap<>();
			Object result2 = driver.executeScript("mobile:handset:ready", params2);
			
			if (index == 2) { // android	
		
				//clean app 
		   Map<String, Object> yogesh12 = new HashMap<>();
		   yogesh12.put("name", "Tripshare");
           Object yogesh12q = driver.executeScript("mobile:application:clean", yogesh12);
           System.out.println("app clean from recent list");


			}
			
		
else { 
				
//clean app
Map<String, Object> yogesh124 = new HashMap<>();
yogesh124.put("name", "Tripshare");
Object yogesh124a = driver.executeScript("mobile:application:clean", yogesh124);
System.out.println("app clean from recent list");



			}
			// ------------Start install/verify/--------------
			// Check if the app is installed

			System.out.println(deviceID + " Checking if the app is installed on device");
			Map<String, Object> sanjiv12 = new HashMap<>();
			sanjiv12.put("format", "identifier");
			String appList = (String) driver.executeScript("mobile:application:find", sanjiv12);
			String appID = "";
			String appPath = "";
			// http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=1549
			if (index == 2) { // android
				appID = " com.amdocs.tripshare";

				appPath = "PRIVATE:script\\Tripshare.apk";
				
			} else { // iOS
				appID = "com.amdocs.tripshare";

				appPath = "PRIVATE:script\\Tripshare.ipa";
			}
			if (appList.indexOf(appID) > -1) {
				System.out.println(deviceID + " The app is installed");
			} else {
				
				/*Map<String, Object> kalia2 = new HashMap<>();
				kalia2.put("file", "PRIVATE:script\\CSR_1.0.0.ipa");
				Object kalia22 = driver.executeScript("mobile:application:install", kalia2);*/
				System.out.println(deviceID + " The app is not installed");
				System.out.println(deviceID + " Installing the app");
				Map<String, Object> ilu = new HashMap<>();
				ilu.put("file", appPath);
				ilu.put("instrument", "instrument");
				String resultStr = (String) driver.executeScript("mobile:application:install", ilu);
				System.out.println(deviceID + " THE RESULT IS: " + resultStr);
				
				
                }

			
			// ------------Finish install/verify--------------

			System.out.println(deviceID + " Step1: Start application Tripshare");
			Map<String, Object> kalia1q = new HashMap<>();
			kalia1q.put("name", "Tripshare");
			driver.executeScript("mobile:application:open", kalia1q);

			sleep(8000);
			System.out.println(deviceID + " Verify: App has been started and shown");
			Map<String, Object> params2n = new HashMap<>();
			params2n.put("content", "Sign In");
			params2n.put("source", "native");
			String result3b = (String) driver.executeScript("mobile:checkpoint:text", params2n);
			if (result3b.equals("true")) {
				System.out.println(deviceID + " Result: TRUE");
			} else {
				System.out.println(deviceID + " Result: FALSE");
			}
			 sleep(1000);
			 Logger logger=Logger.getLogger("Tripshare_EndToEnd_Flow1");
			 PropertyConfigurator.configure("Log4j.properties");

			
			//String sArticle;
			if (index == 2) 
			{
				sleep(1000);
				
Map<String, Object> neha41 = new HashMap<>();
neha41.put("location", "1249,1450");
Object neha41q = driver.executeScript("mobile:touch:tap", neha41);
logger.info("pop up handled");


				//Android
				switchToContext(driver, "NATIVE_APP");
				driver.findElementByXPath("//*[@resource-id=\"i0116\"]").sendKeys("pgouda@amdocs.com");
				
				

				switchToContext(driver, "NATIVE_APP");
           driver.findElementByXPath("//*[@resource-id=\"idSIButton9\"]").click();



		         
				

					/*	
						switchToContext(driver, "NATIVE_APP");
           driver.findElementByXPath("//*[@resource-id=\'userNameInput\']").sendKeys(NTNET_ID);
*/

					
						System.out.println(deviceID + " Step4: Enter password and click on login");
						switchToContext(driver, "NATIVE_APP");
			   			 sleep(1000);

			   driver.findElementByXPath("//*[@resource-id=\'passwordInput\']").sendKeys(pass);


		           //sleep(10000);
						// Click on login
						switchToContext(driver, "NATIVE_APP");
                  driver.findElementByXPath("//*[@resource-id=\"submitButton\"]").click();
                  
                  try {
                	  Map<String, Object> neha45 = new HashMap<>();
                	  neha45.put("label", "No");
                	  Object result15m = driver.executeScript("mobile:button-text:click", neha45);
                	  logger.info("No button clicked-android");

				} catch (Exception e) {
					// TODO: handle exception
				}


				
			} else { 
				// iOS
				//allow flow popup implementation
				try {
					
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@label=\"Allow\"]").click();
                 sleep(1000);
                 logger.info("allow pop up handled sucessfully");
				} catch (Exception e) {
					// TODO: handle exception
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@label=\"Allow\"]").click();
	                 logger.info("allow pop up handled sucessfully");


				}
				
			
			sleep(400);
				
				System.out.println(deviceID + " Step2: Enter Email address, no password and click Login");
				


           
				
				try {
					sleep(300);
					//ios ms page yes controller
					Map<String, Object> pady17 = new HashMap<>();
					pady17.put("location", "891,902");
                Object pady17a = driver.executeScript("mobile:touch:tap", pady17);
               System.out.println("yes button clicked-ios");


				} catch (Exception e) {
					// TODO: handle exception
					//ios ms page no controller
					switchToContext(driver, "NATIVE_APP");
            driver.findElementByXPath("//*[@label=\"No\"]").click();
             System.out.println("No button clicked-ios");


					}


            }
		
               if (index==2) 
               {
            	   //android
              // what on ur mind section 
            	   try {
            		   switchToContext(driver, "NATIVE_APP");
  					 driver.findElementByXPath("//*[@resource-id=\"locations_value\"]").clear();

				} catch (Exception e) {
					// TODO: handle exception
					 switchToContext(driver, "NATIVE_APP");
					 driver.findElementByXPath("//*[@resource-id=\"locations_value\"]").clear();
					 

				}
            	   try {
            		switchToContext(driver, "NATIVE_APP");
    				driver.findElementByXPath("//*[@resource-id=\"locations_value\"]").sendKeys("Pune");

				} catch (Exception e) {
					// TODO: handle exceptionl
					System.out.println("test case failed to enter pune");
				}
            	   
            	   
 //play button android  
 try 
 {
	switchToContext(driver, "NATIVE_APP");
driver.findElement(By.xpath("//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.webkit.WebView[1]/android.webkit.WebView[1]/android.view.View[4]/android.view.View[3]/android.view.View[2]/android.view.View[1]")).click();
logger.info("play button clicked android");

	   
} catch (Exception e) {
	// TODO: handle exception
	logger.info("test case failed for play button");
}  
 sleep(4000);
 
  
 //verify all element
 
  Boolean eat=driver.findElement(By.xpath("//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.webkit.WebView[1]/android.webkit.WebView[1]/android.view.View[4]/android.view.View[6]/android.widget.Button[1]")).isDisplayed();
 if (eat) {
	logger.info("Eat n drink,things to do, airport, Hotels,shopping all verified-Android ");
}
 else 
 {
logger.info("test case failed for verifed all those elements:-eat n drink,things to do,airport,hotels");
	 
}

//amdocs logo android 
  
	  Boolean logo;
	  logo=driver.findElement(By.xpath("//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.webkit.WebView[1]/android.webkit.WebView[1]/android.view.View[6]")).isDisplayed();
	  if (logo) {
	 	logger.info("logo verified-android");
	 } else 
	 {
   logger.info("test case failed for logo-android");
	 }

 // search functionality 
	 /* 
	  try {
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@text=\"BG_3\"]").click();


		logger.info("Hotel name entered sucessfully-androids");
	  } catch (Exception e) {
		// TODO: handle exception
		
		logger.info("Hotels entered sucessfully-android");
	}*/
	  
	  //Restaurant
    /*try {
   
System.out.println("eat n drink clicked");
logger.info("eatn and drink clicked-android");

	
	} catch (Exception e) {
		// TODO: handle exception
		Map<String, Object> params16 = new HashMap<>();
params16.put("label", "Eat & Drink");
Object result16 = driver.executeScript("mobile:button-text:click", params16);


		
	}  	*/
	
	  
	
	
	/*//verify all feedback
	Map<String, Object> prasanna02 = new HashMap<>();
	prasanna02.put("location", "665,2495");
	prasanna02.put("operation", "down");
    Object prasanna02q = driver.executeScript("mobile:touch:tap", prasanna02);
    


Map<String, Object> prasanna03 = new HashMap<>();
List<String> coordinates12 = new ArrayList<>();
coordinates12.add("650,1795");
prasanna03.put("location", coordinates12);
prasanna03.put("auxiliary", "notap");
Object prasanna03q = driver.executeScript("mobile:touch:drag", prasanna03);

Map<String, Object> prasanna04 = new HashMap<>();
List<String> coordinates13 = new ArrayList<>();
coordinates13.add("680,635");
prasanna04.put("location", coordinates13);
prasanna04.put("auxiliary", "notap");
Object prasanna04q = driver.executeScript("mobile:touch:drag", prasanna04);

Map<String, Object> prasanna05 = new HashMap<>();
prasanna05.put("location", "680,635");
prasanna05.put("operation", "up");
Object prasanna05q = driver.executeScript("mobile:touch:tap", prasanna05);

Map<String, Object> prasanna06 = new HashMap<>();
List<String> coordinates15 = new ArrayList<>();
coordinates15.add("650,1200");
coordinates15.add("680,675");
prasanna06.put("location", coordinates15);
prasanna06.put("auxiliary", "notap");
Object prasanna06q = driver.executeScript("mobile:touch:drag", prasanna06);


*/



//verify user profile
	
	
/*boolean isDispalyed34=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/imgUserProfile\"]").isDisplayed();

if (isDispalyed34) {
	System.out.println("profile pic verified");
	logger.info("profile pic verified-android");
} else {
System.out.println("profile not verified");
}
	*/








///android sampat///////////////////////////////////
/////////////////////////////////
////////////////////////////////////////////////////////

//////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
}
  else 
{
	//ios code implementation
		//ok button
		sleep(4000);
		//ok laert pop up--disable
		/*Boolean alertPopupMsg=false;
		try {
			sleep(1000);
			switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"OK\"]").click();
logger.info("ok button clicked-ios");

	System.out.println("ok button sucessfully clicked");
	alertPopupMsg=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"OK\"]").click();
			logger.info("ok button clicked-ios");

		}
*/
		//register now button implementation
		Boolean ReButton=false;
		try {
			
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"REGISTER NOW\"]").click();
	System.out.println("Reg button button sucessfully clicked");
	logger.info("Reg button button sucessfully clicked-ios");

	ReButton=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			Map<String, Object> RegNw = new HashMap<>();
			RegNw.put("label", "Register Now");
			Object Regnow = driver.executeScript("mobile:button-text:click", RegNw);


			System.out.println("test case failed for reg button pls check");
			logger.info("Reg button button sucessfully clicked-ios");

		}
		
	/*	//phone number Edit
		Boolean EditBox=false;
		try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//UIAScrollView/UIATextField[2]").sendKeys("9172559176");

	System.out.println("Edit phone number 9178887412");
	logger.info("phone number edit sucess-ios");

	 EditBox=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("test case failed for Mobile number pls check");
			logger.info("phone number edit sucess-ios");

		}*/
	/*	sleep(1000);
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Done\"]").click();
	logger.info("Done button clicked-ios");
*/
/*
	//swap point set
	Boolean swap=false;
	try {
		
		Map<String, Object> babu = new HashMap<>();
		babu.put("location", "695,576");
		Object ressqult28 = driver.executeScript("mobile:touch:tap", babu);
	    System.out.println("swap success");
		logger.info("swap success-ios");

	    swap=true;
	    
	} catch (Exception e) 
	{
	
Map<String, Object> sanjiv13 = new HashMap<>();
sanjiv13.put("location", "1140,819");
Object sanjiv14 = driver.executeScript("mobile:touch:tap", sanjiv13);
System.out.println("swap sucess");
logger.info("swap success-ios");
//System.out.println("swap failed");
}
*/
	
		// TODO: handle exception
	

/*

	sleep(1000);
	try {
		switchToContext(driver, "NATIVE_APP");

	driver.findElementByXPath("//*[@label=\"img downarrow@2x\"]").click();
	System.out.println("drop down clicked");
	logger.info("click on drop down button sucess -ios");


	} catch (Exception e) {
		// TODO: handle exception
		
		System.out.println("dropdown failed");
		logger.info("dropdown failed -ios");
		
	}
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"3\"]").click();
    System.out.println("3 clicked");
	logger.info("3 clicked from dropdown -ios");
    switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@value=\"Name of member 1\"]").sendKeys("pritam");
	logger.info("enter name pritam sucessfully -ios");

	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Done\"]").click();
	logger.info("Done button clicked sucessfully -ios");

	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@value=\"Name of member 2\"]").sendKeys("pradeep");
	logger.info("enter name pradeep sucessfully -ios");

	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Done\"]").click();
	logger.info("Done button clicked sucessfully -ios");

	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@value=\"Name of member 3\"]").sendKeys("neha");
	logger.info("name entered neha sucessfully");
	try {
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"Done\"]").click();
		logger.info("Done button clicked sucessfully -ios");

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		logger.info("failed done button -ios");

	}*/
	
	sleep(100);
	
	///////////////////////////////check box implementation--disabled ////////////////////////////////////
	
	/*Boolean sel=driver.findElementByXPath("//*[@label=\"img checkbox@2x\"]").isEnabled();
	if (sel) 
	{
    System.out.println("element enabled");
	logger.info("checkbox marked sucessfully -ios");

	} 
	else 
	{
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"img checkbox@2x\"]").click();
        System.out.println("check box selected");
    	logger.info("checkbox marked sucessfully -ios");

	}*/
	
/*try {
	switchToContext(driver, "NATIVE_APP");
	System.out.println("check box selected");
		
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
	

}*/
////////////////////////////////////////////	
	
		//Register-2 button
		Boolean FullReg=false;
		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//UIAButton[@label=\"REGISTER\"]").click();

	System.out.println("Reg button button2 sucessfully clicked");
	logger.info("Reg button button2 sucessfully clicked -ios");

	FullReg=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("test case failed for reg button-2 pls check");
			logger.info("test case failed for reg button-2 pls check--ios");
		}
		
	try {
		sleep(100);
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"ACCEPT\"]").click();
		System.out.println("accept button clicked succesfully");
		logger.info("accept button clicked succesfully--ios");

	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("test case failed for accept button-1");
		logger.info("test case failed for accept button-1--ios");

	}

	try {
		sleep(100);
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"OK\"]").click();
		System.out.println("ok cliked succes and reg part end");
		logger.info("ok button clicked sucessfully--ios");

	} catch (Exception e) {
		// TODO: handle exception
		Map<String, Object> jnan27 = new HashMap<>();
		jnan27.put("label", "OK");
        Object jnan3 = driver.executeScript("mobile:button-text:click", jnan27);


		System.out.println("test case failed for ok button");
		logger.info("test case failed for ok button-ios");
	}
	
	//tab interchange

		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"ALL EVENTS\"]").click();
			System.out.println("all event clicked sucessfully");
			logger.info("all event clicked sucessfully-ios");
			
			} catch (Exception e) {
			// TODO: handle exception
		e.getMessage();
logger.info("all event test case failed");
		}
		
		try {
			//tab interchange -allevent 
			switchToContext(driver, "NATIVE_APP");

	driver.findElementByXPath("//*[@label=\"UPCOMING EVENTS\"]").click();
	System.out.println("Upcoming event Tab interchange sucessfull");
	logger.info("Upcoming event Tab interchange sucessfull-ios");
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Upcoming event Tab interchange failed-ios");
			e.printStackTrace();

		}
		
		
//tab interchange all events

		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"ALL EVENTS\"]").click();
			System.out.println("all event clicked sucessfully");
			logger.info("all event clicked sucessfully-ios");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			
		}
/*		
	sleep(90000);
	sleep(1000);
*/
		/*try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"img nextarrow@2x\"]").click();
		//	driver.findElementByXPath("//*[@label=\"img nextarrow@2x\"]").click();

			System.out.println("next caledar clicked");
			
			logger.info("next button calendar clicked sucessfully-ios");

			
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			
		}*/
		
		/*try {

			switchToContext(driver, "NATIVE_APP");
	        driver.findElementByXPath("//*[@value=\"page 1 of 1\"]/UIACollectionCell[5]/UIAButton[1]").click();
           logger.info("date-1 event clicked -ios");

			
		} 
		catch (Exception e) {
			// TODO: handle exception
			switchToContext(driver, "NATIVE_APP");
	        driver.findElementByXPath("//*[@value=\"page 1 of 1\"]/UIACollectionCell[5]/UIAButton[1]").click();
          e.getMessage();


		}*/
		
		//event verify under calendar

	/*	Boolean isDisplayed11=driver.findElementByXPath("//*[@label=\"Thursday - 30th December\"]").isDisplayed();


		if(isDisplayed11){
			System.out.println("Event  verified under calendar");
			logger.info("Event  verified under calendar-ios");
		}
		else {
			System.out.println("event verified under calendar");
		}
		sleep(100);
		*/
		try {
			switchToContext(driver, "NATIVE_APP");
	           driver.findElementByXPath("//*[@label=\"10\"]").click();
           System.out.println("date 10 clicked");
           logger.info("date 10-clicked sucessfully-ios");

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}

		/*try {
			
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"img prearrow@2x\"]").click();
			//driver.findElementByXPath("//*[@label=\"img nextarrow@2x\"]").click();
           logger.info("prev button clicked sucess-ios");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			
			try {
				switchToContext(driver, "NATIVE_APP");
				driver.findElementByXPath("//*[@label=\"img prearrow@2x\"]").click();
		           logger.info("prev button clicked sucess-ios");

			} catch (Exception e2) {
				// TODO: handle exception
				e.getMessage();
				
				}
			
		}*/
		

   /* //minimize the app
		 Map<String, Object> bckgee = new HashMap<>();
		 bckgee.put("timeout", "10");
		 Object bckg12e = driver.executeScript("mobile:application:back", bckgee);
		System.out.println("minimize done");
		logger.info("minimize done-ios");*/
/*		
	Map<String, Object> params25I = new HashMap<>();
	params25I.put("name", "Flipkart");
	Object result2I5 = driver.executeScript("mobile:application:open", params25I);
	System.out.println("social network opened");
*/
	Map<String, Object> params18 = new HashMap<>();
	params18.put("keySequence", "HOME");
	Object result18 = driver.executeScript("mobile:presskey", params18);
	System.out.println("pressing home button for back sucess");
	logger.info("pressing home button for back sucess-ios");

	Map<String, Object> FACEB = new HashMap<>();
	FACEB.put("name", "CSR");
	Object FACEB44 = driver.executeScript("mobile:application:open", FACEB);
	System.out.println("go back to csr once again sucessfully");
	logger.info("go back to csr once again sucessfully-ios");


	//minimize the app
	 Map<String, Object> bckg = new HashMap<>();
	 bckg.put("timeout", "10");
	 Object bckg12 = driver.executeScript("mobile:application:back", bckg);
	System.out.println("minimize done");
	logger.info("minimize done-ios");

	 //start app again for sec
	 Map<String, Object> pemp = new HashMap<>();
	 pemp.put("name", "CSR");
	 Object yuuu = driver.executeScript("mobile:application:open", pemp);
	 System.out.println("open app fofr sec");
	 logger.info("open app fofr sec-ios");
	 
	 

	/*Map<String, Object> garbage = new HashMap<>();
	garbage.put("name", "CSR");
	Object garba11 = driver.executeScript("mobile:application:close", garbage);
	System.out.println("close csr for a second");*/
		//minimize the app
	/* Map<String, Object> bckgww = new HashMap<>();
	 bckgww.put("timeout", "10");
	 Object bckg12ww = driver.executeScript("mobile:application:back", bckgww);
	System.out.println("minimize done");
	logger.info("minimize done-ios");
	
	Map<String, Object> cameraa = new HashMap<>();
	cameraa.put("name", "camera");
	Object cam78 = driver.executeScript("mobile:application:open", cameraa);
	System.out.println("cam open");
	logger.info("cam open -ios");

	Map<String, Object> yrtt = new HashMap<>();
	yrtt.put("location", "675,1236");
	Object result12 = driver.executeScript("mobile:touch:tap", yrtt);
	System.out.println("selfie mode on");
    logger.info("selfie mode on-ios");
	sleep(4000);
	Map<String, Object> params13o = new HashMap<>();
	params13o.put("location", "363,1215");
	Object result813 = driver.executeScript("mobile:touch:tap", params13o);
	System.out.println("image captured");
	logger.info("image captured-ios");

	Map<String, Object> ptarams14 = new HashMap<>();
	ptarams14.put("name", "CSR");
	Object result414 = driver.executeScript("mobile:application:open", ptarams14);
	System.out.println("csr open");
	logger.info("csr opened-ios");
*/
	
	sleep(100);
	try {
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"UPCOMING EVENTS\"]").click();
		logger.info("upcoming events sucess-ok-ios");
	} catch (Exception e) {
		// TODO: handle exception
		

e.getMessage();
	}
//  verify amdocs logo
		sleep(100);
	boolean isDispalyed = driver.findElementByXPath("//*[@name=\"iPhone/img_amdocslogo@2x.png\"]").isDisplayed();

if (isDispalyed) 
{
	System.out.println("logo is displayed");
	logger.info("logo is displayed-ios");
}
else {
	System.out.println("logo not displayed");
}
sleep(1000);
//verify user profile
boolean isdispalyed2= driver.findElementByXPath("//UIAApplication/UIAWindow[2]/UIAImage[3]").isDisplayed();

if (isdispalyed2) {
	System.out.println("profile pic dispalyed");
	logger.info("profile pic dispalyed-ios");
} else {
System.out.println("profile pic not displayed..test case failed");
}
 
sleep(1000);
  //verify reg now button
boolean isDisplayed3=driver.findElementByXPath("//*[@label=\"UNREGISTER\"]").isDisplayed();
if (isDisplayed3) {
	System.out.println("UNreg now button verified");
	logger.info("UNreg now button verified");
} else {
System.out.println("UNreg now button not verified ..test case failed");
}
/*//verify 200 points
boolean isDisplayed4=driver.findElementByXPath("//*[@label=\"850 Points\"]").isDisplayed();
switchToContext(driver, "NATIVE_APP");

if (isDisplayed4) {
	System.out.println("850 pints verified");
	logger.info("850 popints verified-ios");
} else {
System.out.println("850 points not verified//test case failed");
}*/
sleep(100);
//verify csr logo
boolean isDisplayed5=driver.findElementByXPath("//*[@name=\"iPhone/img_blossom@2x.png\"]").isDisplayed();


if (isDisplayed5) {
	System.out.println("csr logo verified");
	logger.info("logo verifeid-ios");
} else {
System.out.println("csr logo not verified//test case failed");
}

sleep(100);
/*try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//UIAScrollView/UIATableView[2]/UIATableCell[1]/UIAStaticText[1]\"]").click();

	System.out.println("event cliked for verify");
	logger.info("vent cliked for verify");
} catch (Exception e) {
	// TODO: handle exception
	//click on event for verify innerpage
	Map<String, Object> mansa = new HashMap<>();
	mansa.put("label", "API Testing");
	Object mansa2 = driver.executeScript("mobile:button-text:click", mansa);
	logger.info("vent cliked for verify");


}*/

/*sleep(1000);
boolean satya99=driver.findElementByXPath("//*[@label=\"30 November 2017 | 08:30 AM to 09:30 AM\"]").isDisplayed();

if (satya99) {
	System.out.println("innerpage verified");
	logger.info("innerpage verifed-ios");
} else 
{
System.out.println("inner page not verified//test case failed");
}
//event date verification
boolean isDisplayed9=driver.findElementByXPath("//*[@label=\"30 November 2017 | 08:30 AM to 09:30 AM\"]").isDisplayed();

if (isDisplayed9) {
	System.out.println("Date verified");
	logger.info("date verifed-ios");
} else 
{
System.out.println("Date not verified//test case failed");
}
//verify the event details

Boolean isDisplayed10=driver.findElementByXPath("//*[@label=\"Meeting | Pune | One Time\"]").isDisplayed();

if(isDisplayed10){
	System.out.println("Event details verified");
	
	logger.info("event details verifed-ios");
}
else {
	System.out.println("event details test case failed");
}*/


//Register process complete
//process start for unregister

try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Unregister\"]").click();
	System.out.println("unregister button clicked succefully");
	logger.info("unreg sucessfully-ios");

} catch (Exception e) {
	// TODO: handle exception
}
//
try {
	Map<String, Object> pady8 = new HashMap<>();
	pady8.put("label", "UnRegister");
Object pady8a = driver.executeScript("mobile:button-text:click", pady8);
logger.info("unreg button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("unreg button clicked failed");
}
try {
	Map<String, Object> pady9 = new HashMap<>();
	pady9.put("label", "Yes");
Object pady9a = driver.executeScript("mobile:button-text:click", pady9);
System.out.println("yes button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("yes failed");
}
sleep(100);
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"OK\"]").click();
System.out.println("alert pop up-ok for unregister");
logger.info("okn clicked-ios");
sleep(100);
/*switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"OK\"]").click();
System.out.println("ok clicked for unreg sucess alert popup");
logger.info("ok button clicked-ios");
//sleep(4000);
*/
//go to settings for flight mode
/*try {
	Map<String, Object> satya8 = new HashMap<>();
	satya8.put("name", "settings");
	Object satya9 = driver.executeScript("mobile:application:open", satya8);
	System.out.println("setting opended");
	logger.info("setting opened-ios");
	sleep(100);
	File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	
	 // now copy the  screenshot to desired location using copyFile //method
	FileUtils.copyFile(src, new File("C:\\Docs_Connect\\CSR Mobile-Android-(1)\\Screenshots\\settings.png"));
	
		
} catch (Exception e) {
	// TODO: handle exception
	
}*/


/*//flight mode on
try {
	
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//UIASwitch").click();
System.out.println("ui switch clicked/flight mode on");
logger.info("flight mode on-ios");

} catch (Exception e) {
	// TODO: handle exception
Map<String, Object> satya10 = new HashMap<>();
satya10.put("location", "697,467");
Object satya11 = driver.executeScript("mobile:touch:tap", satya10);
System.out.println("flight mode on ");

	e.getMessage();
	
}*/
sleep(1000);
/*//csr open once again
Map<String, Object> satya11 = new HashMap<>();
satya11.put("name", "CSR");
Object satya12 = driver.executeScript("mobile:application:open", satya11);
System.out.println("csr open once again");
logger.info("csr open once again-ios");
*/
//Register-2 button flight mode
		/*try {
			sleep(1000);
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//UIAButton[@label=\"REGISTER\"]").click();
           logger.info("Reg button button2 sucessfully clicked-ios");
           File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
       	
      	 // now copy the  screenshot to desired location using copyFile //method
      	FileUtils.copyFile(src, new File("C:\\Docs_Connect\\CSR Mobile-Android-(1)\\Screenshots\\fligh.png"));
      	
           
		} catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("test case failed for reg button-2 pls check");
		}*/
		
	/*try {
		sleep(100);
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"ACCEPT\"]").click();
		System.out.println("accept clicked succesfully and verification sucessfull");
		logger.info("accept sucess-ios");
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("test case failed for accept button-1");
	}
*/


try {
	
	//uninstall the app
	Map<String, Object> satya40 = new HashMap<>();
Object satya41 = driver.executeScript("mobile:application:reset", satya40);
System.out.println("app uninstall sucessfully");
logger.info("uninstall sucessfull-ios");
	
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}


//reporting part
//FileInputStream 
















            String reportPdfUrl = (String)(driver.getCapabilities().getCapability("reportPdfUrl"));
			// In case you want to down the report or the report
			// attachments, do it here.
			String PathAndFileName;
			// try {
			if (index == 2) { // android
				PathAndFileName = "C:\\test\\AndroidReportTestFlow33a";
			} else { // iOS
				PathAndFileName = "C:\\test\\iOSReportTestFlowo33a";
			}

			// This line of code is generating the report and saving it on the
			// drive
			RemoteWebDriverUtils1.downloadReport(driver, "pdf", PathAndFileName); // Here
		//	RemoteWebDriverUtils.downloadAttachment(driver, "video", "C:\test\repovideo", "flv");
			RemoteWebDriverUtils1.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

               } }
               catch (IOException e) {

			e.printStackTrace();
		}
	}
	/*
	 * RemoteWebDriverUtils.downloadAttachment(driver,
	 * "video","C:\\test\\report\\video", "flv");
	 * RemoteWebDriverUtils.downloadAttachment(driver,
	 * "image","C:\\test\\report\\images", "jpg");
	 */
	// ---------------------------email code------

	// -------------------------------------------------------------------

	// -------------------------------------------------------------------
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	private static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	private static String getCurrentContextHandle(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		String context = (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
		return context;
	}

	private static List<String> getContextHandles(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		List<String> contexts = (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
		return contexts;
	}

	private static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException {
		EclipseConnector connector = new EclipseConnector();
		String eclipseHost = connector.getHost();
		if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
			String executionId = connector.getExecutionId();
			capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);

		}
		

	}
}
