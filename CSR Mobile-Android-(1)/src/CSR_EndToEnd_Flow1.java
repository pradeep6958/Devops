
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

import com.perfectomobile.httpclient.utils.FileUtils;
/*import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;*/
import com.perfectomobile.selenium.util.EclipseConnector;
import com.sun.corba.se.impl.util.Utility;
import com.sun.org.apache.xpath.internal.XPath;

import io.appium.java_client.android.AndroidDriver;

public class CSR_EndToEnd_Flow1 implements Runnable {
	private String deviceID;
	private RemoteWebDriver driver;
	private String NTNET_ID = "pgouda@amdocs.com";
	private String pass = "Blossom@123";
	private String NTNET_ID2="pritamc@amdocs.com";
	private String passwo2 = "Amdocs&123";

	private int index;

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Test Flow for CSR start....");
		List<String> devicesIDs = new LinkedList();
		
		// samsung galaxy device
	devicesIDs.add("ADADB879");
		
	//iphone7
//devicesIDs.add("3B74D48CD1555F29B76D55F495D01B384E2E7F58");
		//iPhone 7--13-11-2017
//devicesIDs.add("955B0252C50AFBAFF240E6AC814CF4FB1E9F03CF");
    
  // devicesIDs.add("F39C49EB5D722D6DBD14CC4366FF6E44084EC96D");
	//883A694706D73CD320FEFEC47F3ECF69BD97C9CD
	//devicesIDs.add("883A694706D73CD320FEFEC47F3ECF69BD97C9CD");
		
		ExecutorService executor = Executors.newFixedThreadPool(devicesIDs.size());
		for (int i = 0; i < devicesIDs.size(); i++) {
			CSR_EndToEnd_Flow1 testFlow4 = new CSR_EndToEnd_Flow1(devicesIDs.get(i));
			executor.execute(testFlow4);
		}
	}
	
	public CSR_EndToEnd_Flow1(String deviceID) 
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
	                .withJob(new Job("my-custom-job-name", 123).withBranch("my-branch"))    
	                .withProject(new Project("Sample Reportium project", "1.0"))
	               .withContextTags("AndroidNativeAppTests")
	                .withCustomFields(new CustomField("team", "devOps"))
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
		   Map<String, Object> ERparams15 = new HashMap<>();
           ERparams15.put("name", "CSR");
           Object ERresult15 = driver.executeScript("mobile:application:clean", ERparams15);
           System.out.println("app clean from recent list");


			}
			
		
else { 
				
//clean app
Map<String, Object> dead = new HashMap<>();
dead.put("name", "CSR");
Object deadSea = driver.executeScript("mobile:application:clean", dead);
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
				appID = "com.amdocs.CSR";

				appPath = "PRIVATE:script\\CSR_1.0.1.apk";
				
			} else { // iOS
				appID = "com.amdocs.CSR";

				appPath = "PRIVATE:script\\CSR_1.0.1.ipa";
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

			System.out.println(deviceID + " Step1: Start application CSR");
			Map<String, Object> kalia1 = new HashMap<>();
			kalia1.put("name", "CSR");
			driver.executeScript("mobile:application:open", kalia1);

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
			 Logger logger=Logger.getLogger("CSR_EndToEnd_Flow1");
			 PropertyConfigurator.configure("Log4j.properties");

			
			//String sArticle;
			if (index == 2) { // Android
				
				
				System.out.println(deviceID + " Step2: Enter Email address, no password and click Login");
				switchToContext(driver, "NATIVE_APP");
                driver.findElementByXPath("//*[@resource-id=\"userNameInput\"]").sendKeys(NTNET_ID2);
                
                System.out.println(deviceID + " Step4: Enter password and click on login");
                logger.info("enter password");
				
   			 sleep(1000);
   			Boolean textObjectFound = false;
			try {
				switchToContext(driver, "NATIVE_APP");
				
				
				driver.findElementByXPath("//*[@resource-id=\"passwordInput\"]").sendKeys(passwo2);				
				textObjectFound = true;
				System.out.println("password section clear");
				
			} catch (Exception e) {
				// TODO: handle exception
				textObjectFound=false;
			}
			
//after enter user name we required side tapping (needle point set)				
Map<String, Object> params9h = new HashMap<>();
params9h.put("location", "1030,1143");
Object result9h = driver.executeScript("mobile:touch:tap", params9h);

					sleep(100);
					//verify signin button whether its working or not
					Boolean textObjectFound1 = false;
					try {
						switchToContext(driver, "NATIVE_APP");
			            driver.findElementByXPath("//*[@resource-id=\"submissionArea\"]").click();

					textObjectFound1 = true;
					System.out.println("submit button succesfully clicked");
					
				        } catch (Exception e) {
					  // TODO: handle exception
					   textObjectFound1=false;
				       }
					sleep(1000);
					try {
						//AUTHENTICATION HANDLE MS PAGE 
						
						
				       switchToContext(driver, "NATIVE_APP");
                 driver.findElementByXPath("//*[@resource-id=\"idSIButton9\"]").click();

		
                  System.out.println("Yes button clicked");

					} catch (Exception e) {
						// TODO: handle exception
		                System.out.println("test case failed for reg button");
      

					}
				
			} else { // iOS
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
				
				/*//signin cancel flow implementation'
				Boolean canc=false;
				try {
					
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@label=\"Cancel\"]").click();
					logger.info("cancel click sucessfully");
					
				System.out.println("signin cancel flow");
				} catch (Exception e) {
					// TODO: handle exception
					driver.findElementByXPath("//*[@label='Cancel']").click();
					e.getMessage();
					
				}*/
				
				
/*try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//UIAApplication/UIAWindow[2]").click();
	System.out.println("chek net connection clicked");
} catch (Exception e) {
	// TODO: handle exception
	Map<String, Object> surya = new HashMap<>();
	surya.put("content", "Please  check your internet connection");
	surya.put("screen.top", "0%");
	surya.put("screen.height", "100%");
	surya.put("screen.left", "0%");
	surya.put("screen.width", "100%");
	Object result177 = driver.executeScript("mobile:text:select", surya);
System.out.println("internet connection cliked");
}*/
				sleep(400);
				
				System.out.println(deviceID + " Step2: Enter Email address, no password and click Login");
	   			 sleep(1000);

				switchToContext(driver, "NATIVE_APP");
				
				driver.findElementByXPath("//*[@value='someone@example.com']").sendKeys(NTNET_ID);
				
				System.out.println(deviceID + " Step4: Enter password and click on login");
				switchToContext(driver, "NATIVE_APP");
	   			 sleep(1000);

           driver.findElementByXPath("//*[@value='Password']").sendKeys(pass);
           //sleep(10000);
				// Click on login
				switchToContext(driver, "NATIVE_APP");
				driver.findElementByXPath("//*[@label='Sign in']").click();
				
				
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
            	//text input part phase   
				sleep(10000);
			
//Alert 1st time user have to enter mobile number(ok button)
/*Boolean textOk=false;
		try {
Map<String, Object> jnan1 = new HashMap<>();
jnan1.put("label", "OK");
Object jnan3 = driver.executeScript("mobile:button-text:click", jnan1);
System.out.println("ok button succesfully clicked");
textOk=true;

} 
		
				     catch (Exception e) {
					// TODO: handle exception
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@label=\"OK\"]").click();

                  textOk=false;

				}*/
				//sleep(100000);
				//register now button implementation for android
				
try {
					
Map<String, Object> harsha = new HashMap<>();
harsha.put("label", "REGISTER NOW");
harsha.put("screen.top", "0%");
harsha.put("screen.height", "100%");
harsha.put("screen.left", "0%");
harsha.put("screen.width", "100%");
Object harsha1 = driver.executeScript("mobile:button-text:click", harsha);

logger.info("register now button clicked sucessfully-android");

				} catch (Exception e) 
				{
					
					// TODO: handle exception
Map<String, Object> sanjiv205 = new HashMap<>();
sanjiv205.put("label", "REGISTER NOW");
sanjiv205.put("screen.top", "0%");
sanjiv205.put("screen.height", "100%");
sanjiv205.put("screen.left", "0%");
sanjiv205.put("screen.width", "100%");
Object sanjiv215 = driver.executeScript("mobile:button-text:click", sanjiv205);


 System.out.println("test case failed for reg button pls check");
                
				}
				sleep(100);
		//phone number edit android
				/*Boolean sanjiv2=false;
				try {
			switchToContext(driver, "NATIVE_APP");
         driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtMobileNo\"]").sendKeys("9172559176");

logger.info("mobile entered sucessfully");

			 sanjiv2=true;
				} catch (Exception e) 
				{
					// TODO: handle exception
					System.out.println("test case failed for Mobile number pls check");
				}	*/		
				
				sleep(1000);		
				
			/*	//swap point set for android 
				Boolean swap=false;
				try {
				Map<String, Object> sanjiv3 = new HashMap<>();
                sanjiv3.put("location", "820,786");
                Object sanjiv4 = driver.executeScript("mobile:touch:tap", sanjiv3);

               logger.info("swap success");
				swap=true;
				} catch (Exception e) 
				{
					Map<String, Object> babu2 = new HashMap<>();
					babu2.put("location", "695,576");
					Object ressqu2lt28 = driver.executeScript("mobile:touch:tap", babu2);
		               logger.info("swap success");
					// TODO: handle exception
				}
				*/
				//sleep(1000);		
				
				//dropdown click android
				
			/*	try {
					
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/tv\"]").click();
logger.info("dropdown click -androidandroid");
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
					logger.info("dropdown click failed- android");

				}
			*/	
				//from drop down it will select 3
			/*	try {
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@text=\"1\"]").click();
                logger.info("from drop down it will select 3-android");
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}
				
				try {
				switchToContext(driver, "NATIVE_APP");
                driver.findElementByXPath("//*[@text=\"Name of member 2\"]").sendKeys("pradeep");
                logger.info("enter name sucessfully");
	
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}*/
				
/*Map<String, Object> par0ms25 = new HashMap<>();
par0ms25.put("location", "983,1831");
Object redff=driver.executeScript("mobile:touch:tap", par0ms25);

				Map<String, Object> sanjiv8 = new HashMap<>();
				sanjiv8.put("keySequence", "BACK");
	Object sanjiv84= driver.executeScript("mobile:presskey", sanjiv8);
	System.out.println("back button pressing");
	
		sleep(100);
				


				
			Map<String, Object> sanjiv8 = new HashMap<>();
			sanjiv8.put("keySequence", "BACK");
Object sanjiv84= driver.executeScript("mobile:presskey", sanjiv8);
System.out.println("back button pressing");*/
	
//RegisteR INNERPAGE button for android
		Boolean sanjiv9=false;
		try {
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnRegister_R\"]").click();

logger.info("Reg button button2 sucessfully clicked-android");

	sanjiv9=true;
		} catch (Exception e) 
		
		{
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnRegister\"]").click();


			// TODO: handle exception
			System.out.println("test case failed for reg button-2 pls check");
		}			
				
	//sucess alert ok clicked android and pop up message verified
		try {
			
			sleep(100);
Map<String, Object> sanjiv33 = new HashMap<>();
sanjiv33.put("label", "OK");
Object sanjiv34 = driver.executeScript("mobile:button-text:click", sanjiv33);
logger.info("accept clicked succesfully-android");

		} catch (Exception e) 
		{
			// TODO: handle exception
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"android:id/button1\"]").click();


			System.out.println("test case failed for accept button-1");
		}		
	//tab interchange for android verification
		try {
			Map<String, Object> sanjiv36 = new HashMap<>();
			sanjiv36.put("label", "ALL EVENTS");
			sanjiv36.put("screen.top", "0%");
			sanjiv36.put("screen.height", "100%");
			sanjiv36.put("screen.left", "0%");
			sanjiv36.put("screen.width", "100%");
Object sanjiv37 = driver.executeScript("mobile:button-text:click", sanjiv36);
logger.info("all events tab clicked-android");

			
		} catch (Exception e) {
			// TODO: handle exception
		e.getMessage();
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtTab2\"]").click();
System.out.println("all event tab clicked");
        }
	//tab interchange android
		
	try {
		
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtTab1\"]").click();
logger.info("upcoming events tab clicked-android");

	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
		Map<String, Object> sanjiv38 = new HashMap<>();
		sanjiv38.put("label", "UPCOMING EVENTS");
Object sanjiv39 = driver.executeScript("mobile:button-text:click", sanjiv38);
System.out.println("upcoming event clicked sucessfully");

		
	}
	
	try {
		
		Map<String, Object> sanjiv40 = new HashMap<>();
		sanjiv40.put("label", "ALL EVENTS");
		sanjiv40.put("screen.top", "0%");
		sanjiv40.put("screen.height", "100%");
		sanjiv40.put("screen.left", "0%");
		sanjiv40.put("screen.width", "100%");
        Object sanjiv41 = driver.executeScript("mobile:button-text:click", sanjiv40);
        logger.info("all events tab clicked-android");
		
	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
		switchToContext(driver, "NATIVE_APP");
       driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtTab2\"]").click();
       System.out.println("all event tab clicked");
		
		
	}	
	sleep(4000);
	/*//calendar swipe for android
sleep(200);
	try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnNext\"]").click();
logger.info("calender next clcked to december-android");
	
	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnNext\"]").click();
		System.out.println("calender next clcked to december");
		
	}*/
	
	//event date clicked 7 date
	try {
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@text=\"10\"]").click();
logger.info("10 date clicked-android");

	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@text=\"20\"]").click();
		
	}
	
	//event verify under calendar
	
Boolean isDisplayed31=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtEventTitle\"]").isDisplayed();
if (isDisplayed31) 
{
	logger.info("event verified for date 10th-android");
}
else {
	
	System.out.println("event not verified under calendar");
	
}
	
	/*//pre arrow for android

	sleep(100);
	
	try {
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnPrev\"]").click();
logger.info("prev button clicked-android");

		
	} catch (Exception e) {
		e.getMessage();
		// TODO: handle exception
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnPrev\"]").click();
    }
	
	//30 date clicked for android
	try {
		switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/tblCalender\"]//*[@class=\"android.widget.TableLayout\"]/android.widget.TableRow[6]/android.widget.LinearLayout[5]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.TextView[1]").click();
	logger.info("test case pass for event 30");


	} catch (Exception e) {
		logger.info("test case failed for nov 30");
	}
    
    sleep(100);
    switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtTab1\"]").click();
	*/
	/*try {
		//minimize the app
		Map<String, Object> sanjiv43 = new HashMap<>();
		sanjiv43.put("target", "menu");
	Object sanjiv44 = driver.executeScript("mobile:handset:ready", sanjiv43);
logger.info("minimize done for android");
	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
	}
	sleep(1000);*/
	/*try {
		
		//OPEN CSR ONCE AGAIN  ON ANDROID

		Map<String, Object> sanjiv45 = new HashMap<>();
		sanjiv45.put("name", "Instagram");
		Object sanjiv46 = driver.executeScript("mobile:application:open", sanjiv45);
        logger.info("Insta open sucessfull-android-");
	} catch (Exception e) {
		// TODO: handle exception
	}*/
/*//minimize the app android

Map<String, Object> sanjiv47 = new HashMap<>();
sanjiv47.put("keySequence", "HOME");
Object sanjiv48 = driver.executeScript("mobile:presskey", sanjiv47);
logger.info("minimize done-android");


sleep(100);
*/
/*try {
	
	Map<String, Object> sanjiv49 = new HashMap<>();
	sanjiv49.put("name", "CSR");
	Object sanjiv50 = driver.executeScript("mobile:application:open", sanjiv49);
System.out.println("csr open for second");
logger.info("csr open for second-android");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}

sleep(1000);
*/

   
//cam open android
try {
	
	/*// reportiumClient.testStart("CSR", new TestContext.Builder()
	            .withTestExecutionTags("ValidateCamera")
	            .withCustomFields(new CustomField("developer", "John"))
	            .build());

	    //step1: Validate login page
	    reportiumClient.testStart("CSR TESTING", new TestContext("Test TAG"));
	 //  reportiumClient.testStep("jhv");
	    reportiumClient.stepStart("step1: Validate camera");*/
//	Map<String, Object> sanjiv510 = new HashMap<>();
	//sanjiv510.put("name", "Camera");
	//sanjiv510.put("timeout", "5");
   // Object sanjiv52 = driver.executeScript("mobile:application:open", sanjiv510);
	//logger.info("cam open-android");
	
//	reportiumClient.testStop(TestResultFactory.createSuccess());
		
} catch (Exception e) {
	// TODO: handle exception
	//reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(),e));
	e.printStackTrace();
	
}
//String ReportURL = reportiumClient.getReportUrl();

sleep(100);
/*//selfie mode on
Map<String, Object> sanjiv53 = new HashMap<>();
sanjiv53.put("location", "975,97");
Object sanjiv54 = driver.executeScript("mobile:touch:tap", sanjiv53);
System.out.println("selfi on");
logger.info("selfie on-android");
//System.out.println(devicedropdown click android);

sleep(100);
*/
//image captured
/*try {
	Map<String, Object> sanjiv55 = new HashMap<>();
	sanjiv55.put("location", "533,1771");
	Object sanjiv56 = driver.executeScript("mobile:touch:tap", sanjiv55);
	
	logger.info("image captureed");
} catch (Exception e) {
	// TODO: handle exception
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@resource-id=\"com.sec.android.app.camera:id/camera_preview\"]").click();
	System.out.println("photo clicked");

}*/


/*try {
	//close camera
	Map<String, Object> sanjiv57 = new HashMap<>();
	sanjiv57.put("name", "Camera");
	Object sanjiv58 = driver.executeScript("mobile:application:close", sanjiv57);
	logger.info("camera closed");

} catch (Exception e) {
	// TODO: handle exception
}
*/
/*try {
  //  reportiumClient.stepStart("step2: Validate csr");
	
	Map<String, Object> sanjiv59 = new HashMap<>();
	sanjiv59.put("name", "CSR");
	Object sanjiv60 = driver.executeScript("mobile:application:open", sanjiv59);
    System.out.println("csr open for second");
    logger.info("csr open for second-android");
//reportiumClient.testStop(TestResultFactory.createSuccess());

} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
	//reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(),e));
}*/


/*//verify amdocs logo
sleep(100);

boolean isDispalyed33 =

driver.findElementByXPath("//hierarchy/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.ImageView[1]").isDisplayed();
if (isDispalyed33) {
	System.out.println("amdocs logo is displayed");
} else {
	


System.out.println("logo not displayed");
}
*/



//verify user profile
	
	
boolean isDispalyed34=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/imgUserProfile\"]").isDisplayed();

if (isDispalyed34) {
	System.out.println("profile pic verified");
	logger.info("profile pic verified-android");
} else {
System.out.println("profile not verified");
}
	
/*//verify reg now button
boolean isDispalyed35=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnRgisterNow\"]").isDisplayed();

if (isDispalyed35) {
	logger.info("reg now button verified");
} else {
System.out.println("reg now button not verified");
}*/





/*//verify 700 points
boolean isDispalyed36=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtPoints\"]").isDisplayed();

if (isDispalyed36) {
	logger.info("700 points verified");
} else {
System.out.println("700 points not verified");
}*/

/*//verify csr logo

boolean isDispalyed37=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/imgPic\"]").isDisplayed();
if (isDispalyed37) {
	logger.info("csr logo verified-android");
} else {
System.out.println("csr logo not verified");
}
*/


//click on event for verify innerpage
/*try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@text=\"API Testing\"]").click();
	logger.info("event clicked");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}*/

/*boolean isDispalyed38=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtDate\"]").isDisplayed();

if (isDispalyed38) {
	logger.info("event page verifed inner page-android");
} else {
System.out.println("event not verified");
}
*/


/*//event date verification
boolean isDispalyed39=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtDate\"]").isDisplayed();

if (isDispalyed39) {
	logger.info("event date verified");
} else {
System.out.println("event date not verified");
}
*/	
	
	
/*//verify the event details	

boolean isDispalyed40=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtEventType\"]").isDisplayed();
if (isDispalyed40) {
	logger.info("event details location verified-android");
} else {
	System.out.println("event details location verified");

}*/
//android for unregister
/*Map<String, Object> medu = new HashMap<>();
List<String> coordinates4 = new ArrayList<>();
coordinates4.add("594,1104");
coordinates4.add("594,1034");
medu.put("location", coordinates4);
medu.put("auxiliary", "notap");
Object medu1 = driver.executeScript("mobile:touch:drag", medu);

Map<String, Object> medu2 = new HashMap<>();
medu2.put("location", "594,1240");
medu2.put("operation", "down");
Object medu3 = driver.executeScript("mobile:touch:tap", medu2);

Map<String, Object> medu4 = new HashMap<>();
List<String> coordinates3 = new ArrayList<>();
coordinates3.add("594,1166");
medu4.put("location", coordinates3);
medu4.put("auxiliary", "notap");
Object medu5 = driver.executeScript("mobile:touch:drag", medu4);

Map<String, Object> medu6 = new HashMap<>();
List<String> coordinates5 = new ArrayList<>();
coordinates5.add("594,997");
medu6.put("location", coordinates5);
medu6.put("auxiliary", "notap");
Object medu7 = driver.executeScript("mobile:touch:drag", medu6);

Map<String, Object> medu8 = new HashMap<>();
medu8.put("location", "594,997");
medu8.put("operation", "up");
Object medu9 = driver.executeScript("mobile:touch:tap", medu8);
logger.info("scroll up for android-pass");
*/
try {
	
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtTab1\"]").click();
logger.info("upcoming events tab clicked-android");

} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
	Map<String, Object> pady10 = new HashMap<>();
	pady10.put("label", "UPCOMING EVENTS");
Object pady10a = driver.executeScript("mobile:button-text:click", pady10);
System.out.println("upcoming event clicked sucessfully");

	
}
try {
	
	Map<String, Object> params4 = new HashMap<>();
	params4.put("label", "UnRegister");
	Object result4 = driver.executeScript("mobile:button-text:click", params4);
logger.info("unregister sucessfull-android");

} catch (Exception e) {
	// TODO: handle exception
}

//yes android
try {
	
	Map<String, Object> params5 = new HashMap<>();
	params5.put("label", "Yes");
	Object result5 = driver.executeScript("mobile:button-text:click", params5);
	logger.info("yes button clicked-android");

} catch (Exception e) {
	// TODO: handle exception
}


try {
	Map<String, Object> params6 = new HashMap<>();
	params6.put("label", "OK");
	Object result6 = driver.executeScript("mobile:button-text:click", params6);
	logger.info("ok button clicked-android");

} catch (Exception e) {
	// TODO: handle exception
}
/*try {
	//minimize the app android

	Map<String, Object> jnan9 = new HashMap<>();
	jnan9.put("keySequence", "HOME");
	Object jnan10 = driver.executeScript("mobile:presskey", jnan9);
	logger.info("minimize done");
	
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}*/

/*//verify mail in aw inbox
Map<String, Object> jnan6 = new HashMap<>();
jnan6.put("name", "inbox");
Object jnan7 = driver.executeScript("mobile:application:open", jnan6);
System.out.println("aw inbox opened");
sleep(200);
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.airwatch.email:id/password\"]").sendKeys("6969");
System.out.println("enter pssword");
//login
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.airwatch.email:id/login\"]").click();
System.out.println("login sucess for aw inbox");

Map<String, Object> ert = new HashMap<>();
ert.put("location", "761,1840");
Object ert5 = driver.executeScript("mobile:touch:tap", ert);
System.out.println("blossom india refreshed");

Map<String, Object> jnan17 = new HashMap<>();
jnan17.put("name", "inbox");
Object jnan18 = driver.executeScript("mobile:application:close", jnan17);
System.out.println("inbox closed sucessfully");
*/
try {
	//minimize the app android

	Map<String, Object> jnan21 = new HashMap<>();
	jnan21.put("keySequence", "HOME");
	Object jnan22 = driver.executeScript("mobile:presskey", jnan21);
	logger.info("minimize done");
	
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}
/*try {
	//go to settings for flight mode on
	Map<String, Object> jnan23 = new HashMap<>();
	jnan23.put("name", "settings");
	Object jnan24 = driver.executeScript("mobile:application:open", jnan23);
	logger.info("open setting-android");

} catch (Exception e) {
	// TODO: handle exception
}
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@text=\"Airplane mode\"]").click();
logger.info("airplane mode on-android");

} catch (Exception e) {
	// TODO: handle exception
}*/
/*try {
	Map<String, Object> satya5 = new HashMap<>();
	satya5.put("location", "989,152");
	Object satya6 = driver.executeScript("mobile:touch:tap", satya5);
System.out.println("mode on");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}
*/
try {
	
	Map<String, Object> satya7 = new HashMap<>();
	satya7.put("name", "CSR");
	Object satya8 = driver.executeScript("mobile:application:open", satya7);
logger.info("csr open for second-android");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}

//UNregister now button implementation for android
try {

	Map<String, Object> pady14 = new HashMap<>();
	pady14.put("label", "UNREGISTER");
    Object pady14a = driver.executeScript("mobile:button-text:click", pady14);



logger.info("unReg button button sucessfully clicked-android");
//sanjiv=true;
} catch (Exception e) 
{
	// TODO: handle exception
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnRgisterNow\"]").click();



System.out.println("test case passed for unregister button ");

}
sleep(200);
//internal unregister
try {
	Map<String, Object> pady15 = new HashMap<>();
	pady15.put("label", "UnRegister");
    Object pady15a = driver.executeScript("mobile:button-text:click", pady15);

    System.out.println("unReg button button2 sucessfully clicked");
} catch (Exception e) 

{
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnRegister_R\"]").click();

System.out.println("test case passed for unreg button-2 ");
}		

try {
	
Map<String, Object> params6 = new HashMap<>();
params6.put("label", "Yes");
Object result6 = driver.executeScript("mobile:button-text:click", params6);
System.out.println("Yes clicked--android");
	
	
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
	
}


//ok button settings
try {
	Map<String, Object> pady16 = new HashMap<>();
	pady16.put("label", "OK");
   Object pady16a = driver.executeScript("mobile:button-text:click", pady16);
   System.out.println("ok pop up clicked");

	} catch (Exception e) {
	// TODO: handle exception
	
}

//back button code
/*try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnBack_R\"]").click();
	System.out.println("back button clicked and verified");
	
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}*/
/*try {
	//minimize the app android

	Map<String, Object> deba5 = new HashMap<>();
	deba5.put("keySequence", "HOME");
	Object deba6 = driver.executeScript("mobile:presskey", deba5);
	System.out.println("minimize done");
	
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}*/

/*try {
	//go to settings for flight mode on
	Map<String, Object> deba2 = new HashMap<>();
	deba2.put("name", "settings");
	Object deba3 = driver.executeScript("mobile:application:open", deba2);
	System.out.println("open settings ");

} catch (Exception e) {
	// TODO: handle exception
}*/
/*try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@text=\"Airplane mode\"]").click();
System.out.println("airplane mode on");

} catch (Exception e) {
	// TODO: handle exception
}*/
/*try {
	Map<String, Object> asish1 = new HashMap<>();
	asish1.put("location", "939,150");
	Object asish2 = driver.executeScript("mobile:touch:tap", asish1);
	
	
System.out.println("flight mode off");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}*/
/*try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@resource-id=\"android:id/up\"]").click();
	
	System.out.println("back sucess");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}*/
sleep(100);


//sleep(1000);

/*try {
	Map<String, Object> asish9 = new HashMap<>();
	asish9.put("name", "CSR");
	Object asish10 = driver.executeScript("mobile:application:uninstall", asish9);

System.out.println("uninstall done");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}*/


///android sampat///////////////////////////////////
/////////////////////////////////
////////////////////////////////////////////////////////

//////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
} else 
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
