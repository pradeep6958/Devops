
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import sun.util.logging.resources.logging;

public class Fresh_Head_And_Ios implements Runnable {
	private String deviceID;
	private RemoteWebDriver driver;
	private String NTNET_ID = "pgouda@amdocs.com";
	private String pass = "Blossom@123";
	//private String NTNET_ID2="pritamc@amdocs.com";
	//private String passwo2 = "Amdocs*123";

	private int index;

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Test Flow for headstart start....");
		List<String> devicesIDs = new LinkedList();
		
		// samsung galaxy device
	devicesIDs.add("CE061716BDCC68550D7E");
		
	//iphone7
//devicesIDs.add("632E9899");
		//iPhone 7--13-11-2017
 //devicesIDs.add("955B0252C50AFBAFF240E6AC814CF4FB1E9F03CF");
    
    //devicesIDs.add("883A694706D73CD320FEFEC47F3ECF69BD97C9CD");
	//ipad -ios
 //devicesIDs.add("8B9C0353C6F2D5A6CDE34E5A4FFB33011BB6A5E5");
		
		
		ExecutorService executor = Executors.newFixedThreadPool(devicesIDs.size());
		for (int i = 0; i < devicesIDs.size(); i++) {
			Fresh_Head_And_Ios testFlow4 = new Fresh_Head_And_Ios(devicesIDs.get(i));
			executor.execute(testFlow4);
		}
	}
	
	public Fresh_Head_And_Ios(String deviceID) 
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
		//String host = "partners.perfectomobile.com";
		String host = "digitallab.corp.amdocs.com";

		
		
		//capabilities.setCapability("user", "tester1@ad-wisory.com");
		//capabilities.setCapability("password", "Test123");
		capabilities.setCapability("user", "yana.krasner@amdocs.com");
		capabilities.setCapability("password", "yana1");
		
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
				//clean app from recent list or home
				Map<String, Object> neha3 = new HashMap<>();
				neha3.put("name", "headstart");
                Object neha3r = driver.executeScript("mobile:application:clean", neha3);
                System.out.println("Headstart clean from recent list");

			}
			////input[@id='']
		
else { 
				
//clean app from recent list or home
Map<String, Object> neha1 = new HashMap<>();
neha1.put("name", "Headstart");
Object neha2 = driver.executeScript("mobile:application:close", neha1);
System.out.println("app clean from recent list-ipad");



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
				appID = "com.amdocs.headstart";

				appPath = "PRIVATE:script\\Headstart_1.1.8.apk";
				
			} else { // iOS
				appID = "com.amdocs.headstart";

				appPath = "PRIVATE:script\\HeadStart_1.1.8.ipa";
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

			System.out.println(deviceID + " Step1: Start application headstart start..");
			Map<String, Object> neha3 = new HashMap<>();
            neha3.put("name", "Headstart");
            Object neha4 = driver.executeScript("mobile:application:open", neha3);


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
			Logger logger=Logger.getLogger("headstart_EndToEnd_Flow1");
		    PropertyConfigurator.configure("Log4j.properties");

			
			//String sArticle;
			if (index == 2) { // Android
				
				System.out.println(deviceID + " Step2: Enter Email address, no password and click Login");
				switchToContext(driver, "NATIVE_APP");
                driver.findElementByXPath("//*[@resource-id=\"userNameInput\"]").sendKeys(NTNET_ID);
                System.out.println(deviceID + " Step4: Enter password and click on login");
               // logger.info("enter password");
				
   			 sleep(1000);
   			Boolean textObjectFound = false;
			try {
				switchToContext(driver, "NATIVE_APP");
				
				
				driver.findElementByXPath("//*[@resource-id=\"passwordInput\"]").sendKeys(pass);				
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
					
					sleep(3000);
					try {
						Map<String, Object> neha60 = new HashMap<>();
						neha60.put("label", "Yes");
                      Object neha60a = driver.executeScript("mobile:button-text:click", neha60);
                      logger.info("yes button clicked -android");

					} catch (Exception e) {
						// TODO: handle exception
						switchToContext(driver, "NATIVE_APP");
                     driver.findElementByXPath("//*[@resource-id=\"idSIButton9\"]").click();


						System.out.println("test case passed for yes button-android");
					}
				
			} else { // iOS
				
				
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


            }
		
               if (index==2) 
               {
	           //android
	           // News in Brief-android
            	   
            	   
            	
            	   try {
            		   Map<String, Object> sahoo1 = new HashMap<>();
            		   sahoo1.put("label", "News in Brief");
            		   Object sahoo2 = driver.executeScript("mobile:button-text:click", sahoo1);
            		   logger.info("News in Brief clicked-Android");

				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("News in Brief failed-android");
				}
          //Earning Reports
            	   
            	   
            	 try {
					switchToContext(driver, "NATIVE_APP");
               driver.findElementByXPath("//*[@text=\"Earnings Reports\"]").click();
                logger.info("Earning reports:clicked-android");

            		   
            		   
				} catch (Exception e) {
					// TODO: handle exception
	                logger.info("Earning reports:failed-android");

				}
             //Back button earning reports android
            	 try {
            		 switchToContext(driver, "NATIVE_APP");
                    driver.findElementByXPath("//*[@content-desc=\"Navigate up\"]").click();
                   logger.info("News in brief back clicked-android");

					
				} catch (Exception e) {
					// TODO: handle exception
					logger.info("Back button failed for news in brief-android");
				}

            	 
        //Eli gelman one on one video
         try {
	    switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@text=\"Eli Gelman One on One Video\"]").click();
logger.info("eligel man video-android");

 
        	 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("eligel man video failed-android");
	
			
		} 
         
        //popup handle
         try {
			Map<String, Object> sahoo3 = new HashMap<>();
			sahoo3.put("label", "YES");
            Object sahoo4 = driver.executeScript("mobile:button-text:click", sahoo3);


		} catch (Exception e) {
			// TODO: handle exception
			
		}
    //management Blogs
         try {
			Map<String, Object> sahoo5 = new HashMap<>();
			sahoo5.put("location", "399,428");
           Object sahoo6 = driver.executeScript("mobile:touch:tap", sahoo5);
			logger.info("mangement video play-android");


		} catch (Exception e) {
			// TODO: handle exception
			
		}
         
         try {
        	 Map<String, Object> naha41 = new HashMap<>();
             naha41.put("keySequence", "BACK");
             Object naha41a = driver.executeScript("mobile:presskey", naha41);
              logger.info("Back button clicked-android");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("test case failed for back button android--eligelmen video");
		}
         //managemnet blog
         try {
			Map<String, Object> neha42 = new HashMap<>();
			neha42.put("label", "Management Blogs");
            Object neha42a = driver.executeScript("mobile:button-text:click", neha42);
            logger.info("Management blog opened-android");

		} catch (Exception e) {
			// TODO: handle exception
		}
         //positive thinking
         try {
			Map<String, Object> neha43 = new HashMap<>();
			neha43.put("label", "Positive thinking");
          Object neha43a = driver.executeScript("mobile:button-text:click", neha43);
          logger.info("positive thinking back clicked-android");


		} catch (Exception e) {
			// TODO: handle exception
		}
         //Back button earning reports android
    	 try {
    		 switchToContext(driver, "NATIVE_APP");
            driver.findElementByXPath("//*[@content-desc=\"Navigate up\"]").click();
           logger.info("positive thinking back clicked-android");

			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Back button failed for positive thinking-android");
		}
    	//lifelong learning
    	 try {
 			Map<String, Object> neha44 = new HashMap<>();
 			neha44.put("label", "Lifelong learning");
           Object neha44a = driver.executeScript("mobile:button-text:click", neha44);
           logger.info("lifelong learning back clicked-android");


 		} catch (Exception e) {
 			// TODO: handle exception
 		}
    	//back button
    	 try {
    		 switchToContext(driver, "NATIVE_APP");
            driver.findElementByXPath("//*[@content-desc=\"Navigate up\"]").click();
           logger.info("positive thinking back clicked-android");

			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Back button failed for positive thinking-android");
		}
    	 
    	 //Recognizing our quiet heroes
    	 try {
    		 Map<String, Object> neha45 = new HashMap<>();
    		 neha45.put("label", "Recognizing our quiet heroes");
            Object neha45a = driver.executeScript("mobile:button-text:click", neha45);
            logger.info("Recognizing our quiet heroes clicked-android");

    		 
    		 
		} catch (Exception e) {
			// TODO: handle exception
			
			
		}
    	 
    	//back button
    	 try {
    		 switchToContext(driver, "NATIVE_APP");
            driver.findElementByXPath("//*[@content-desc=\"Navigate up\"]").click();
           logger.info("recognizing back clicked-android");

			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Back button failed for recognizing-android");
		}
    	 //a day in the life
    	 try {
    		 Map<String, Object> neha46 = new HashMap<>();
    		 neha46.put("label", "A day in the life");
             Object neha46a = driver.executeScript("mobile:button-text:click", neha46);
             logger.info("Recognizing our quiet heroes clicked-android");

     		 
    		 
		} catch (Exception e) {
			// TODO: handle exception
			
			
		}
    	 
    	 //back button
    	 try {
    		 switchToContext(driver, "NATIVE_APP");
            driver.findElementByXPath("//*[@content-desc=\"Navigate up\"]").click();
           logger.info("recognizing back clicked-android");

			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Back button failed for recognizing-android");
		}
    	 //back button
    	 try {
    		 switchToContext(driver, "NATIVE_APP");
            driver.findElementByXPath("//*[@content-desc=\"Navigate up\"]").click();
           logger.info("recognizing back clicked-android");

			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Back button failed for recognizing-android");
		}
    	//scroll code 
    	 Map<String, Object> params6 = new HashMap<>();
params6.put("location", "478,1558");
params6.put("operation", "down");
Object result6 = driver.executeScript("mobile:touch:tap", params6);

Map<String, Object> params7 = new HashMap<>();
List<String> coordinates7 = new ArrayList<>();
coordinates7.add("478,1424");
coordinates7.add("478,1237");
params7.put("location", coordinates7);
params7.put("auxiliary", "notap");
Object result7 = driver.executeScript("mobile:touch:drag", params7);

Map<String, Object> params9 = new HashMap<>();
List<String> coordinates9 = new ArrayList<>();
coordinates9.add("478,1085");
params9.put("location", coordinates9);
params9.put("auxiliary", "notap");
Object result9 = driver.executeScript("mobile:touch:drag", params9);

Map<String, Object> params10 = new HashMap<>();
params10.put("location", "478,1085");
params10.put("operation", "up");
Object result10 = driver.executeScript("mobile:touch:tap", params10);

Map<String, Object> params8 = new HashMap<>();
List<String> coordinates8 = new ArrayList<>();
coordinates8.add("478,1098");
params8.put("location", coordinates8);
params8.put("auxiliary", "notap");
Object result8 = driver.executeScript("mobile:touch:drag", params8);

sleep(3000);

 try {
	Map<String, Object> neha49 = new HashMap<>();
	neha49.put("label", "Recent Town Hall Meeting");
   Object neha49a = driver.executeScript("mobile:button-text:click", neha49);
   logger.info("recent town hall clicking");

} catch (Exception e) {
	// TODO: handle exception
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@text=\"Recent Town Hall Meeting\"]").click();
logger.info("recent town hall clicking");
}   
 try {
	Map<String, Object> neha50 = new HashMap<>();
	neha50.put("label", "YES");
Object neha50a = driver.executeScript("mobile:button-text:click", neha50);
logger.info("recent town hall video clicking");


} catch (Exception e) {
	// TODO: handle exception
	
	
}
 //video internal --recent town hall
   try {
	Map<String, Object> neha51 = new HashMap<>();
	neha51.put("location", "388,429");
Object neha51a = driver.executeScript("mobile:touch:tap", neha51);
logger.info("Recent town hall video playesd ");

	   
	   
} catch (Exception e) {
	// TODO: handle exception
	
	
	
} 	 
   // sleep(70000);	
  try {
	  Map<String, Object> neha53 = new HashMap<>();
	   neha53.put("keySequence", "BACK");
	Object neha53a = driver.executeScript("mobile:presskey", neha53);
	logger.info("Back button clicked-android");
	 
	    
} catch (Exception e) {
	// TODO: handle exception
}
   
    try {
    	//a day in the life
   	
   		 Map<String, Object> neha46f = new HashMap<>();
   		 neha46f.put("label", "CEO Blogs");
            Object neha46af = driver.executeScript("mobile:button-text:click", neha46f);
            logger.info("ceo blogs clicked-android");

    		 
   		 
		
	} catch (Exception e) {
		// TODO: handle exception
	}
    
    
    //back button
	 try {
		 switchToContext(driver, "NATIVE_APP");
       driver.findElementByXPath("//*[@content-desc=\"Navigate up\"]").click();
      logger.info("CEO BLOG CLICKED back clicked-android");

		
	} catch (Exception e) {
		// TODO: handle exception
		logger.info("Back button failed for ceo blogs-android");
	}  
    
    //categories clicking
	 
	 try {
		
		 switchToContext(driver, "NATIVE_APP");
         driver.findElementByXPath("//*[@resource-id=\"com.amdocs.headstart:id/iconCategories\"]").click();


	} catch (Exception e) {
		// TODO: handle exception
		
		
		
	}
    //Our comapany

	   try {
		   Map<String, Object> sahoo1q = new HashMap<>();
		   sahoo1q.put("label", "Our Company");
		   Object sahoo2q = driver.executeScript("mobile:button-text:click", sahoo1q);
		   logger.info("our company clicked-Android");

	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("our company failed-android");
	} 
	   //categories clicking
		 
		 try {
			
			 switchToContext(driver, "NATIVE_APP");
	         driver.findElementByXPath("//*[@resource-id=\"com.amdocs.headstart:id/iconCategories\"]").click();


		} catch (Exception e) {
			// TODO: handle exception
			
			
			
		}
    
	   try {
		   Map<String, Object> sahoo1q = new HashMap<>();
		   sahoo1q.put("label", "Our Industry");
		   Object sahoo2q = driver.executeScript("mobile:button-text:click", sahoo1q);
		   logger.info("our company clicked-Android");

	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("our company failed-android");
	} 
    
	   //categories clicking
		 
		 try {
			
			 switchToContext(driver, "NATIVE_APP");
	         driver.findElementByXPath("//*[@resource-id=\"com.amdocs.headstart:id/iconCategories\"]").click();


		} catch (Exception e) {
			// TODO: handle exception
			
			
			
		}
    
		/* try {
			  // Specify the path of file
			  File src=new File("filepath/excelsheetname.xlsx");
			 
			   // load file
			   FileInputStream fis=new FileInputStream(src);
			 
			   // Load workbook
			   XSSFWorkbook wb=new XSSFWorkbook(fis);
			   
			   // Load sheet- Here we are loading first sheetonly
			      XSSFSheet sh1= wb.getSheetAt(0);
			 
			  // getRow() specify which row we want to read.
			 
			  // and getCell() specify which column to read.
			  // getStringCellValue() specify that we are reading String data.
			 
			 
			 System.out.println(sh1.getRow(0).getCell(0).getStringCellValue());
			 
    
    
    */
    
    
    
///android sampat///////////////////////////////////
/////////////////////////////////
////////////////////////////////////////////////////////

//////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
} else 
{
	//ios code implementation
		//eli gel man video
	sleep(1000);
	try {
		
		Map<String, Object> neha5 = new HashMap<>();
		neha5.put("label", "Eli Gelman's Town Hall");
		Object neha6 = driver.executeScript("mobile:button-text:click", neha5);
		System.out.println("eli gelman town hall video paly sucessfully");
		logger.info("eli gelman video playing..-ipad");


	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("eli gel man video failed-ipad");
	}	
	sleep(70000);
//Touch in center part video
try {
	Map<String, Object> params3 = new HashMap<>();
params3.put("location", "1226,785");
Object result3 = driver.executeScript("mobile:touch:tap", params3);


} catch (Exception e) {
	// TODO: handle exception
}

//Eli gel man -Done button
try {
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"Done\"]").click();
logger.info("done button sucessfully clicked-ipad");
	
} catch (Exception e)

{
	// TODO: handle exception
	Map<String, Object> neha7 = new HashMap<>();
	neha7.put("location", "87,87");
	Object neha8 = driver.executeScript("mobile:touch:tap", neha7);
    System.out.println("exception handle--done button sucessfully clicked-ipad");
}

//Media Room
try {
	Map<String, Object> neha9  = new HashMap<>();
	neha9.put("label", "Media Room");
    Object neha10 = driver.executeScript("mobile:button-text:click", neha9);
    System.out.println("media room sucessfully clicked-ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("media room failed-ipad");
}
//back button for meida room
try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"icon back\"]").click();
logger.info("back button for media room clicked");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("media room failed-ipad");
}
//press kit
try {
	Map<String, Object> neha10 = new HashMap<>();
	neha10.put("label", "Press Kit");
Object neha11 = driver.executeScript("mobile:button-text:click", neha10);
logger.info("press kit open -ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Press kit failed-ipad");
}

//back button for press kit
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("back button for press kit clicked");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("press kit back button failed-ipad");
}
//positive thinking
try {
	Map<String, Object> neha13 = new HashMap<>();
	neha13.put("label", "Positive Thinking");
Object neha14 = driver.executeScript("mobile:button-text:click", neha13);
logger.info("positive thinking clicked-ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("positive thinking failed-ipad");
}
//back button for positive thinking
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("back button for positive thinking clicked");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("positive thinking back button failed-ipad");
}

//a day in the life
try {
	Map<String, Object> neha15 = new HashMap<>();
	neha15.put("label", "A day in the life");
   Object neha16 = driver.executeScript("mobile:button-text:click", neha15);
   logger.info("a day in the life clicked-ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("a day in the life failed-ipad");
}

//back button for positive thinking
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("back button for a day in the life clicked");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("a day in the life back button failed-ipad");
}

//life long learning
try {
	Map<String, Object> neha17 = new HashMap<>();
	neha17.put("label", "Lifelong ");
    Object neha18 = driver.executeScript("mobile:button-text:click", neha17);
    logger.info("lifelong clicked-ipad2");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("lifelong learning failed-ipad");
}
//back button for life long
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("life long back button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("life long back button failed-ipad");
}
//Recognizing our quiet
try {
	Map<String, Object> neha20 = new HashMap<>();
neha20.put("label", "Recognizing ");
Object neha21 = driver.executeScript("mobile:button-text:click", neha20);
logger.info("recognixing our quiet clicked-ipad");

	
} catch (Exception e) {
	// TODO: handle exception
System.out.println("recognizing our quiet-failed");	
	
}
//back button for recognizing our quiet heroes
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("recognizing our quiet heroesback button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("recognizing our quiet heroes back button failed-ipad");
}

//Categories
try {
	Map<String, Object> neha22 = new HashMap<>();
	neha22.put("label", "Categories");
	Object neha23 = driver.executeScript("mobile:button-text:click", neha22);
	logger.info("categories clicked-ipad");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("categories failed-ipad");
}

////////////
///////
// categores start
try {
	Map<String, Object> neha24 = new HashMap<>();
	neha24.put("label", "News in Brief");
Object neha25 = driver.executeScript("mobile:button-text:click", neha24);
logger.info("News in brief clicked sucessfully-ipad");


} catch (Exception e) {
	// TODO: handle exception
	logger.info("news in brief failed-ipad");
	
}
//earning reports

try {
	Map<String, Object> neha26 = new HashMap<>();
	neha26.put("label", "Earning Reports");
Object neha27 = driver.executeScript("mobile:button-text:click", neha26);
logger.info("Earning reports clicked-ipad");

	
} catch (Exception e) {
	// TODO: handle exception
	logger.info("earning reports failed-ipad");
}
//back button for earning reports
try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"icon back\"]").click();
logger.info("back button clicked-ipad");
} catch (Exception e) {
	// TODO: handle exception
logger.info("back button failed-ipad");

}
//eli gel man video
try {
	Map<String, Object> neha28 = new HashMap<>();
	neha28.put("label", "Eli Gelman one on one video");
	Object neha27 = driver.executeScript("mobile:button-text:click", neha28);
	logger.info("Eli gel man video play");
	
} catch (Exception e) {
	// TODO: handle exception
	
logger.info("eli gel man video failed-ipad");
}
//center click
try {
	Map<String, Object> neha29 = new HashMap<>();
neha29.put("location", "1476,892");
Object neha30 = driver.executeScript("mobile:touch:tap", neha29);


	
} catch (Exception e) {
	// TODO: handle exception
	
}
//Done button
try {
	Map<String, Object> neha31 = new HashMap<>();
	neha31.put("label", "Done");
	Object neha32 = driver.executeScript("mobile:button-text:click", neha31);
    logger.info("done button clicked-ipad");
} catch (Exception e) {
	// TODO: handle exception
	Map<String, Object> neha32 = new HashMap<>();
neha32.put("label", "Done");
Object neha33 = driver.executeScript("mobile:button-text:click", neha32);
logger.info("eli gel man video done button clicked-ipad ");

}
//management blog
try {
	Map<String, Object> neha34 = new HashMap<>();
	neha34.put("label", "Management Blog");
Object neha35 = driver.executeScript("mobile:button-text:click", neha34);
logger.info("management blog clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	logger.info("management blog failed-ipad");
	
}
try {
	Map<String, Object> neha36 = new HashMap<>();
neha36.put("location", "1726,1475");
neha36.put("operation", "down");
Object neha37 = driver.executeScript("mobile:touch:tap", neha36);

Map<String, Object> neha38 = new HashMap<>();
List<String> coordinates11 = new ArrayList<>();
coordinates11.add("1726,987");
coordinates11.add("1726,517");
neha38.put("location", coordinates11);
neha38.put("auxiliary", "notap");
Object neha39 = driver.executeScript("mobile:touch:drag", neha38);

Map<String, Object> neha40 = new HashMap<>();
neha40.put("location", "1726,517");
neha40.put("operation", "up");
Object neha41 = driver.executeScript("mobile:touch:tap", neha40);
logger.info("scroll pass-ipad");

} catch (Exception e) {
	// TODO: handle exception
	logger.info("scroll failed-ipad");
}

/*//recent town hall meeting
try {
	Map<String, Object> neha42 = new HashMap<>();
	neha42.put("label", "Recent Town Hall Meeting");
Object neha43 = driver.executeScript("mobile:button-text:click", neha42);
logger.info("recent town hall clicked-ipad");

} catch (Exception e) {
	// TODO: handle exception
	logger.info("recent town hall failed-ipad");
	
}
try {
	//center click
	Map<String, Object> neha44 = new HashMap<>();
	neha44.put("location", "1480,1104");
	Object neha45 = driver.executeScript("mobile:touch:tap", neha44);
	System.out.println("done button clicked ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("done button failed-ipad");
}

//Done button
try {
	Map<String, Object> neha46 = new HashMap<>();
	neha46.put("label", "Done");
	Object neha47 = driver.executeScript("mobile:button-text:click", neha46);
  logger.info("done button clicked-ipad");
} catch (Exception e) {
	// TODO: handle exception
	Map<String, Object> neha49 = new HashMap<>();
	neha49.put("label", "Done");
Object neha50 = driver.executeScript("mobile:button-text:click", neha49);
logger.info("Eli gel man video done button clicked-ipad ");

}*/
sleep(3000);
//------------------//
/*try {
	Map<String, Object> neha133 = new HashMap<>();
	neha133.put("label", "Categories");
    Object neha134 = driver.executeScript("mobile:button-text:click", neha133);
    logger.info("News in brief clicked sucessfully-ipad");


} catch (Exception e) {
	// TODO: handle exception
	logger.info("news in brief failed-ipad");
	
}*/
/*try {
	Map<String, Object> neha36 = new HashMap<>();
neha36.put("location", "1726,1475");
neha36.put("operation", "down");
Object neha37 = driver.executeScript("mobile:touch:tap", neha36);

Map<String, Object> neha38 = new HashMap<>();
List<String> coordinates11 = new ArrayList<>();
coordinates11.add("1726,987");
coordinates11.add("1726,517");
neha38.put("location", coordinates11);
neha38.put("auxiliary", "notap");
Object neha39 = driver.executeScript("mobile:touch:drag", neha38);

Map<String, Object> neha40 = new HashMap<>();
neha40.put("location", "1726,517");
neha40.put("operation", "up");
Object neha41 = driver.executeScript("mobile:touch:tap", neha40);
logger.info("scroll pass-ipad");

} catch (Exception e) {
	// TODO: handle exception
	logger.info("scroll failed-ipad");
}*/
sleep(2000);
//ceo blog
try {
	Map<String, Object> neha51 = new HashMap<>();
	neha51.put("label", "CEO Blogs");
Object neha52 = driver.executeScript("mobile:button-text:click", neha51);
logger.info("CEO blogs clicked-ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("ceo blog failed-ipad");
}

//positive thinking 
try {
	Map<String, Object> neha56 = new HashMap<>();
	neha56.put("label", "Positive Thinking");
	Object neha55 = driver.executeScript("mobile:button-text:click", neha56);
logger.info("postive thinking");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("positive thinking failed-ipad");
}
//back button for recognizing our quiet heroes
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("media room button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Media room  back button failed-ipad");
}

//Recognizing our quiet heroes
try {
	Map<String, Object> neha61 = new HashMap<>();
	neha61.put("label", "Recognizing our quiet heroes");
Object neha62 = driver.executeScript("mobile:button-text:click", neha61);
logger.info("recognizing clicked-ipad");

	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("recognizing failed-ipad");
	
}
//back button for reconizing quiet heroes
try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"icon back\"]").click();
logger.info("back button clicked-ipad");
} catch (Exception e) {
	// TODO: handle exception
logger.info("back button failed-ipad");

}

//Categories
try {
	Map<String, Object> neha63 = new HashMap<>();
	neha63.put("label", "Categories");
	Object neha64 = driver.executeScript("mobile:button-text:click", neha63);
	logger.info("categories clicked-ipad");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("categories failed-ipad");
}


/*//media room
try {
	Map<String, Object> neha53 = new HashMap<>();
	neha53.put("label", "Media Room");
Object neha54 = driver.executeScript("mobile:button-text:click", neha53);
logger.info("media room clicked-ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Media room");
	
}

//back button for recognizing our quiet heroes
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("media room button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Media room  back button failed-ipad");
}*/

//Our company
try {
	Map<String, Object> neha68 = new HashMap<>();
	neha68.put("label", "Our Company");
	Object neha69 = driver.executeScript("mobile:button-text:click", neha68);
	logger.info("Our Company clicked -ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("our company failed-ipad");
}
//About amdocs
try {
	Map<String, Object> neha70 = new HashMap<>();
	neha70.put("label", "About amdocs");
    Object neha71 = driver.executeScript("mobile:button-text:click", neha70);
    logger.info("about amdocs clicked sucessfully-ipad");

	
} catch (Exception e) {
	// TODO: handle exception
	logger.info("about amdocs failed-ipad");
	
}
try {
	Map<String, Object> neha72 = new HashMap<>();
	neha72.put("location", "510,1393");
	neha72.put("operation", "down");
	Object neha73 = driver.executeScript("mobile:touch:tap", neha72);

	Map<String, Object> neha74 = new HashMap<>();
	List<String> coordinates19 = new ArrayList<>();
	coordinates19.add("514,1102");
	neha74.put("location", coordinates19);
	neha74.put("auxiliary", "notap");
	Object neha75 = driver.executeScript("mobile:touch:drag", neha74);

	Map<String, Object> neha76 = new HashMap<>();
	List<String> coordinates20 = new ArrayList<>();
	coordinates20.add("514,902");
	neha76.put("location", coordinates20);
	neha76.put("auxiliary", "notap");
	Object neha77 = driver.executeScript("mobile:touch:drag", neha76);

	Map<String, Object> neha78 = new HashMap<>();
	neha78.put("location", "514,902");
	neha78.put("operation", "up");
	Object neha79 = driver.executeScript("mobile:touch:tap", neha78);
	logger.info("scroll sucess-ipad");
} catch (Exception e) {
	// TODO: handle exception
	logger.info("scroll failed-ipad");
}

try {
	Map<String, Object> neha80 = new HashMap<>();
	neha80.put("label", "EXPERIENCE CENTERS");
    Object neha81 = driver.executeScript("mobile:button-text:click", neha80);
    
   logger.info("Exp center open-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	
	
}
try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//UIAWebView/UIAElement[1]").click();


} catch (Exception e) {
	// TODO: handle exception
}
try {
	Map<String, Object> neha82 = new HashMap<>();
	neha82.put("location", "1378,1114");
    Object neha83 = driver.executeScript("mobile:touch:tap", neha82);


} catch (Exception e) {
	// TODO: handle exception
}

sleep(50000);
//back button for recognizing our quiet heroes
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("media room button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Media room  back button failed-ipad");
}
//corporate fact sheet
try {
	Map<String, Object> neha83 = new HashMap<>();
	neha83.put("label", "Corporate Fact Sheet");
	Object neha84 = driver.executeScript("mobile:button-text:click", neha83);
logger.info("corporate face clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	
	System.out.println("corporate face-ipad");
}

//back button for CORPORATE FACT
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("media room button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Media room  back button failed-ipad");
}
//Our org structure
try {
	Map<String, Object> neha85 = new HashMap<>();
	neha85.put("label", "Our Organizational Structure");
Object neha86 = driver.executeScript("mobile:button-text:click", neha85);
logger.info("our org structure-ipad");

} catch (Exception e) {
	// TODO: handle exception
	
}
try {
Map<String, Object> neha87 = new HashMap<>();
neha87.put("label", "YES");
Object neha88 = driver.executeScript("mobile:button-text:click", neha87);
logger.info("yes button clciked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
}
try {
Map<String, Object> poonam01 = new HashMap<>();
poonam01.put("name", "HeadStart");
Object poonam02 = driver.executeScript("mobile:application:open", poonam01);
logger.info("headstar open once again-ipad");
	
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("headstart open process failed-ipad");
}

//Categories
try {
	Map<String, Object> poonam03 = new HashMap<>();
	poonam03.put("label", "Categories");
	Object poonam04 = driver.executeScript("mobile:button-text:click", poonam03);
	logger.info("categories clicked-ipad");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("categories failed-ipad");
}


sleep(1000);
//Our industry
try {
	
Map<String, Object> poonam06 = new HashMap<>();
poonam06.put("label", "Our Industry");
Object poonam07 = driver.executeScript("mobile:button-text:click", poonam06);
logger.info("Our Industry");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Our industry -test case failed-ipad");
	
	
}
//Global telecom business
try {
	

	Map<String, Object> poonam09 = new HashMap<>();
	poonam09.put("label", "Global Telecoms Business");
	Object poonam10=driver.executeScript("mobile:button-text:click", poonam09);
	logger.info("global telecom business clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("global telecom business-failed-ipad");
	
	
}

//back button global Telecom business
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("global telecom business- clicked-ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Media room  back button failed-ipad");
}

/*//competition customer
try {
	Map<String, Object> poonam11 = new HashMap<>();
	poonam11.put("label", "Competition,Customer and Partners");
	Object poonam12=driver.executeScript("mobile:button-text:click", poonam11);
	logger.info("competiotion customer- clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("competition customer-failed-ipad");
	
	
}*/

//APAC Business Summit video
try {
	Map<String, Object> poonam11 = new HashMap<>();
	poonam11.put("label", "APAC Business Summit Video");
	Object poonam12=driver.executeScript("mobile:button-text:click", poonam11);
	logger.info("competiotion customer- clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("competition customer-failed-ipad");
	
	
}
//apac video play
try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"Play\"]").click();
logger.info("apac video play sucessful-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("apac video failed-ipad");
	
}
//back button for apac video
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("media room button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Media room  back button failed-ipad");
}
//Categories
try {
	Map<String, Object> neha633 = new HashMap<>();
	neha633.put("label", "Categories");
	Object neha6334 = driver.executeScript("mobile:button-text:click", neha633);
	logger.info("categories clicked-ipad");


} catch (Exception e) {
	// TODO: handle exception
	System.out.println("categories failed-ipad");
}
//Our Offering
try {
	Map<String, Object> neha68 = new HashMap<>();
	neha68.put("label", "Our Offering");
	Object neha69 = driver.executeScript("mobile:button-text:click", neha68);
	logger.info("Our Company clicked -ipad");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("our company failed-ipad");
}
//Customer Experience Solutions (CES)
try {
	

	Map<String, Object> poonam13 = new HashMap<>();
	poonam13.put("label", "Customer Experience Solutions(CES)");
	Object poonam14=driver.executeScript("mobile:button-text:click", poonam13);
	logger.info("Customer Experience solutions- clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Customer Experience solutions-failed-ipad");
	
	
}

//Embrace a new day
try {
	
	Map<String, Object> poonam15 = new HashMap<>();
	poonam15.put("label", "Embrace A New Day");
	Object poonam16=driver.executeScript("mobile:button-text:click", poonam15);
	logger.info("embrace a new day- clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Embrace a New day-failed-ipad");
	
	}

//Our offering video play
try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"Play\"]").click();
logger.info("our offering video play-ipad");

	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("our offering video failed-ipad");
	
}

sleep(3000);
//back button for our offering video
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("media room button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("our offering  back button failed-ipad");
}

//scroll code
try {
	Map<String, Object> neha728 = new HashMap<>();
	neha728.put("location", "510,1393");
	neha728.put("operation", "down");
	Object neha738 = driver.executeScript("mobile:touch:tap", neha728);

	Map<String, Object> neha744 = new HashMap<>();
	List<String> coordinates19 = new ArrayList<>();
	coordinates19.add("514,1102");
	neha744.put("location", coordinates19);
	neha744.put("auxiliary", "notap");
	Object neha754 = driver.executeScript("mobile:touch:drag", neha744);

	Map<String, Object> neha764 = new HashMap<>();
	List<String> coordinates20 = new ArrayList<>();
	coordinates20.add("514,902");
	neha764.put("location", coordinates20);
	neha764.put("auxiliary", "notap");
	Object neha774 = driver.executeScript("mobile:touch:drag", neha764);

	Map<String, Object> neha784 = new HashMap<>();
	neha784.put("location", "514,902");
	neha784.put("operation", "up");
	Object neha794 = driver.executeScript("mobile:touch:tap", neha784);
	logger.info("scroll sucess-ipad");

} catch (Exception e) {
	// TODO: handle exception
	logger.info("scroll failed-ipad");
}

//Dive into big data with amdocs
try {
	
	Map<String, Object> poonam19 = new HashMap<>();
	poonam19.put("label", "Dive into Big Data with Amdocs");
	Object poonam20=driver.executeScript("mobile:button-text:click", poonam19);
	logger.info("Dive into Big Data with Amdocs- clicked-ipad");
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Dive into Big Data with Amdocs-failed-ipad");
	
}

try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"Play\"]").click();
logger.info("Our offering video play-ipad");

	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("our offering video failed-ipad");
	
}
sleep(3000);

//Back button for dive into your data
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("Dive into your data clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("dive into your data-  back button failed-ipad");
}
//Digital Experience
try {
	Map<String, Object> poonam22 = new HashMap<>();
	poonam22.put("label", "Digital Experience");
	Object poonam23=driver.executeScript("mobile:button-text:click", poonam22);
	logger.info("Digital Experience- clicked-ipad");
	
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Digital experience failed-ipad");
	
}
//back button for our offering
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("back button for our offering clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Our offering-  back button failed-ipad");
}
//Enterprise and b2b
try {
	Map<String, Object> poonam24 = new HashMap<>();
	poonam24.put("label", "Enterprise and B2B");
	Object poonam25=driver.executeScript("mobile:button-text:click", poonam24);
	logger.info("Enterprise and b2b- clicked-ipad");
	
	
} catch (Exception e) {
	// TODO: handle exception
	System.out.println("enterprise failed-ipad");
	
}
//back button for our offering
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"icon back\"]").click();
	logger.info("media room button clicked");

} catch (Exception e) {
	// TODO: handle exception
	System.out.println("Our offering-  back button failed-ipad");
}



////////////////-reporting part----////////////////////////////
            String reportPdfUrl = (String)(driver.getCapabilities().getCapability("reportPdfUrl"));
			// In case you want to down the report or the report
			// attachments, do it here.
			String PathAndFileName;
			// try {
			if (index == 2) { // android
				PathAndFileName = "C:\\test\\AndroidReportTestFlow33";
			} else { // iOS
				PathAndFileName = "C:\\test\\iOSReportTestFlowo33";
			}

			// This line of code is generating the report and saving it on the
			// drive
			RemoteWebDriverUtils_Fressh.downloadReport(driver, "pdf", PathAndFileName); // Here
		//	RemoteWebDriverUtils.downloadAttachment(driver, "video", "C:\test\repovideo", "flv");
			RemoteWebDriverUtils_Fressh.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

			
			
			
               } 
               }
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
