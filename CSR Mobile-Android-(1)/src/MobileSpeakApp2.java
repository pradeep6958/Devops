
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import com.perfectomobile.selenium.util.EclipseConnector;
import com.sun.org.apache.xpath.internal.XPath;

import io.appium.java_client.android.AndroidDriver;

public class MobileSpeakApp2 implements Runnable {
	private String deviceID;
	private RemoteWebDriver driver;
	private String NTNET_ID = "pgouda@amdocs.com";
	private String pass = "Password1@@";
	private int index;

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Test Flow for speakapp");
		List<String> devicesIDs = new LinkedList();
	
		// Lg device
	devicesIDs.add("ADADB879");
	//iphone7
 //devicesIDs.add("3B74D48CD1555F29B76D55F495D01B384E2E7F58");
		//iPhone 6plus
    //devicesIDs.add("883A694706D73CD320FEFEC47F3ECF69BD97C9CD");
		
		
		ExecutorService executor = Executors.newFixedThreadPool(devicesIDs.size());
		for (int i = 0; i < devicesIDs.size(); i++) {
			MobileSpeakApp2 testFlow4 = new MobileSpeakApp2(devicesIDs.get(i));
			executor.execute(testFlow4);
		}
	}


	public MobileSpeakApp2(String deviceID) {
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
			//
		
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

				appPath = "PRIVATE:script\\CSR_1.0.0.apk";
				
			} else { // iOS
				appID = "com.amdocs.CSR";

				appPath = "PRIVATE:script\\CSR_1.0.0.ipa";
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

			
			//String sArticle;
			if (index == 2) { // Android
				
				System.out.println(deviceID + " Step2: Enter Email address, no password and click Login");
				switchToContext(driver, "NATIVE_APP");
                driver.findElementByXPath("//*[@resource-id=\"userNameInput\"]").sendKeys(NTNET_ID);
                System.out.println(deviceID + " Step4: Enter password and click on login");
				
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
				
              

			} else { // iOS
				
				//signin cancel flow implementation'
				Boolean canc=false;
				try {
					
					
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@label=\"Cancel\"]").click();
					
					
				System.out.println("signin cancel flow");
				} catch (Exception e) {
					// TODO: handle exception
					driver.findElementByXPath("//*[@label='Cancel']").click();
					e.getMessage();
					
				}
try {
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
}
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

/*Map<String, Object> params6w = new HashMap<>();
params6w.put("content", "sign In");
params6w.put("screen.top", "69%");
params6w.put("screen.height", "15%");
params6w.put("screen.left", "1%");
params6w.put("screen.width", "99%");
Object result6w = driver.executeScript("mobile:text:select", params6w);*/
/*
Map<String, Object> patrams7 = new HashMap<>();
patrams7.put("location", "688,1985");
Object resulty7 = driver.executeScript("mobile:touch:tap", patrams7);
*/

}
		
               if (index==2) 
               {
	           //android
	           // what on ur mind section 
            	//text input part phase   
				sleep(10000);
			
//Alert 1st time user have to enter mobile number(ok button)
				Boolean textOk=false;
				try {
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"android:id/button1\"]").click();
System.out.println("ok button succesfully clicked");
textOk=true;


				} catch (Exception e) {
					// TODO: handle exception
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@label=\"OK\"]").click();

                  textOk=false;

				}
				sleep(100);
				//register now button implementation for android
				Boolean sanjiv=false;
				try {
					
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/lstUpcomingEvents\"]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[3]/android.widget.Button[1]").click();


          System.out.println("Reg button button sucessfully clicked");
			sanjiv=true;
				} catch (Exception e) 
				{
					// TODO: handle exception
Map<String, Object> sanjiv20 = new HashMap<>();
sanjiv20.put("label", "REGISTER NOW");
sanjiv20.put("screen.top", "0%");
sanjiv20.put("screen.height", "100%");
sanjiv20.put("screen.left", "0%");
sanjiv20.put("screen.width", "100%");
Object sanjiv21 = driver.executeScript("mobile:button-text:click", sanjiv20);


                System.out.println("test case failed for reg button pls check");
                
				}
				sleep(100);
		//phone number edit android
				Boolean sanjiv2=false;
				try {
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtMobileNo\"]").sendKeys("9172559176");


			System.out.println("Edit phone number 1234567891 done");

			 sanjiv2=true;
				} catch (Exception e) 
				{
					// TODO: handle exception
					System.out.println("test case failed for Mobile number pls check");
				}			
				
				sleep(1000);		
				
				//swap point set for android 
				Boolean swap=false;
				try {
				Map<String, Object> sanjiv3 = new HashMap<>();
                sanjiv3.put("location", "820,786");
                Object sanjiv4 = driver.executeScript("mobile:touch:tap", sanjiv3);

               System.out.println("swap success");
				swap=true;
				} catch (Exception e) 
				{
					Map<String, Object> babu2 = new HashMap<>();
					babu2.put("location", "695,576");
					Object ressqu2lt28 = driver.executeScript("mobile:touch:tap", babu2);
				System.out.println("swap sucess");
					// TODO: handle exception
				}
				



				sleep(1000);		
				
				//dropdown click android
				try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/tv\"]").click();

				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}
				
				//from drop down it will select 3
				try {
					switchToContext(driver, "NATIVE_APP");
					driver.findElementByXPath("//*[@text=\"1\"]").click();

				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}
				


				
				try {
				switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@text=\"Name of member 2\"]").sendKeys("pradeep");

	
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}
				
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


	System.out.println("Reg button button2 sucessfully clicked");
	sanjiv9=true;
		} catch (Exception e) 
		
		{
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnRegister\"]").click();


			// TODO: handle exception
			System.out.println("test case failed for reg button-2 pls check");
		}			
				
	//sucess alert ok clicked android
		try {
			
			sleep(100);
Map<String, Object> sanjiv33 = new HashMap<>();
sanjiv33.put("label", "OK");
Object sanjiv34 = driver.executeScript("mobile:button-text:click", sanjiv33);
System.out.println("accept clicked succesfully");
		} catch (Exception e) {
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
System.out.println("all events tab clicked");

			
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
System.out.println("all events tab clicked");
		
	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtTab2\"]").click();
System.out.println("all event tab clicked");
		
		
	}	
	//calendar swipe for android
sleep(200);
	try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnNext\"]").click();
System.out.println("calender next clcked to december");
	
	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnNext\"]").click();
		System.out.println("calender next clcked to december");
		
	}
	
	//event date clicked 7 date
	try {
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@text=\"7\"]").click();


	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@text=\"7\"]").click();
		
	}
	
	//event verify under calendar
	
Boolean isDisplayed31=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtEventTitle\"]").isDisplayed();
if (isDisplayed31) 
{
	System.out.println("event verified for date 7 ");
	
}
else {
	
	System.out.println("event not verified under calendar");
	
}
	
//10 date clicked for android
try {
	switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@text=\"10\"]").click();
System.out.println("10 clicked");

} catch (Exception e) {
	// TODO: handle exception
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@text=\"10\"]").click();
	System.out.println("10 clicked");
	
}
	
	//pre arrow for android

	sleep(100);
	
	try {
		switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnPrev\"]").click();


		
	} catch (Exception e) {
		e.getMessage();
		// TODO: handle exception
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnPrev\"]").click();
    }
    
    sleep(100);
    switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtTab1\"]").click();
	
	try {
		//minimize the app
		Map<String, Object> sanjiv43 = new HashMap<>();
		sanjiv43.put("target", "menu");
	Object sanjiv44 = driver.executeScript("mobile:handset:ready", sanjiv43);
	System.out.println("minimize done for android");

	} catch (Exception e) {
		// TODO: handle exception
		e.getMessage();
	}
	sleep(1000);
	try {
		//OPEN CSR ONCE AGAIN  ON ANDROID

		Map<String, Object> sanjiv45 = new HashMap<>();
		sanjiv45.put("name", "Facebook");
		Object sanjiv46 = driver.executeScript("mobile:application:open", sanjiv45);
		System.out.println("facebook open ");

	} catch (Exception e) {
		// TODO: handle exception
	}
//minimize the app android

Map<String, Object> sanjiv47 = new HashMap<>();
sanjiv47.put("keySequence", "HOME");
Object sanjiv48 = driver.executeScript("mobile:presskey", sanjiv47);
System.out.println("minimize hua");


sleep(100);

try {
	
	Map<String, Object> sanjiv49 = new HashMap<>();
	sanjiv49.put("name", "CSR");
	Object sanjiv50 = driver.executeScript("mobile:application:open", sanjiv49);
System.out.println("csr open for second");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}

sleep(1000);
//cam open android
Map<String, Object> sanjiv51 = new HashMap<>();
sanjiv51.put("name", "Camera");
Object sanjiv52 = driver.executeScript("mobile:application:open", sanjiv51);
System.out.println("cam open");
sleep(100);
//selfie mode on
Map<String, Object> sanjiv53 = new HashMap<>();
sanjiv53.put("location", "975,97");
Object sanjiv54 = driver.executeScript("mobile:touch:tap", sanjiv53);
System.out.println("selfi on");

sleep(100);

//image captured

Map<String, Object> sanjiv55 = new HashMap<>();
sanjiv55.put("location", "533,1771");
Object sanjiv56 = driver.executeScript("mobile:touch:tap", sanjiv55);
System.out.println("image captureed");

/*//close camera
Map<String, Object> sanjiv57 = new HashMap<>();
sanjiv57.put("name", "Camera");
Object sanjiv58 = driver.executeScript("mobile:application:close", sanjiv57);
System.out.println("camera closed");*/

try {
	
	Map<String, Object> sanjiv59 = new HashMap<>();
	sanjiv59.put("name", "CSR");
	Object sanjiv60 = driver.executeScript("mobile:application:open", sanjiv59);
System.out.println("csr open for second");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}


//verify amdocs logo
sleep(100);

boolean isDispalyed33 = driver.findElementByXPath("//hierarchy/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.FrameLayout[1]/android.view.View[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.ImageView[1]").isDisplayed();
if (isDispalyed33) {
	System.out.println("logo is displayed");
} else {
	


System.out.println("logo not displayed");
}




//verify user profile
	
	
boolean isDispalyed34=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/imgUserProfile\"]").isDisplayed();

if (isDispalyed34) {
	System.out.println("profile pic verified");
} else {
System.out.println("profile not verified");
}
	
//verify reg now button
boolean isDispalyed35=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/btnRgisterNow\"]").isDisplayed();

if (isDispalyed35) {
	System.out.println("reg now button verified");
} else {
System.out.println("reg now button not verified");
}





//verify 200 points
boolean isDispalyed36=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtPoints\"]").isDisplayed();

if (isDispalyed36) {
	System.out.println("200 points verified");
} else {
System.out.println("200 points not verified");
}

//verify csr logo

boolean isDispalyed37=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/imgPic\"]").isDisplayed();
if (isDispalyed37) {
	System.out.println("csr logo verified");
} else {
System.out.println("csr logo not verified");
}



//click on event for verify innerpage
try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@text=\"Test Event 7 December\"]").click();
	System.out.println("event clicked");
} catch (Exception e) {
	// TODO: handle exception
	e.getMessage();
}

boolean isDispalyed38=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtDate\"]").isDisplayed();

if (isDispalyed38) {
	System.out.println("event page verifed inner page");
} else {
System.out.println("event not verified");
}



//event date verification
boolean isDispalyed39=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtDate\"]").isDisplayed();

if (isDispalyed39) {
	System.out.println("event date verified");
} else {
System.out.println("event date not verified");
}
	
	
	
//verify the event details	

boolean isDispalyed40=driver.findElementByXPath("//*[@resource-id=\"com.amdocs.CSR:id/txtEventType\"]").isDisplayed();
if (isDispalyed40) {
	System.out.println("event details location verified");
} else {
	System.out.println("event details location verified");

}
	
	
	
	
	
} else 
{
	//ios code implementation
		//ok button
		sleep(4000);
		//ok laert pop up
		Boolean alertPopupMsg=false;
		try {
			sleep(1000);
			switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"OK\"]").click();


	System.out.println("ok button sucessfully clicked");
	alertPopupMsg=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"OK\"]").click();

		}

		//register now button implementation
		Boolean ReButton=false;
		try {
			
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"REGISTER NOW\"]").click();
	System.out.println("Reg button button sucessfully clicked");
	ReButton=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			Map<String, Object> RegNw = new HashMap<>();
			RegNw.put("label", "Register Now");
			Object Regnow = driver.executeScript("mobile:button-text:click", RegNw);


			System.out.println("test case failed for reg button pls check");
		}
		
		//phone number Edit
		Boolean EditBox=false;
		try {
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//UIAScrollView/UIATextField[2]").sendKeys("9172559176");

	System.out.println("Edit phone number 9178887412");

	 EditBox=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("test case failed for Mobile number pls check");
		}
		sleep(1000);
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Done\"]").click();

	//swap point set
	Boolean swap=false;
	try {
		
		Map<String, Object> babu = new HashMap<>();
		babu.put("location", "695,576");
		Object ressqult28 = driver.executeScript("mobile:touch:tap", babu);
	System.out.println("swap success");
	swap=true;
	} catch (Exception e) {
	
	Map<String, Object> sanjiv13 = new HashMap<>();
sanjiv13.put("location", "1140,819");
Object sanjiv14 = driver.executeScript("mobile:touch:tap", sanjiv13);
System.out.println("swap sucess");
}

	
	{
		
	System.out.println("swap failed");
		// TODO: handle exception
	}



	sleep(1000);
	try {
		switchToContext(driver, "NATIVE_APP");

	driver.findElementByXPath("//*[@label=\"img downarrow@2x\"]").click();
	System.out.println("drop down clicked");

	} catch (Exception e) {
		// TODO: handle exception
		
		System.out.println("dropdown failed");
	}
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"3\"]").click();

	System.out.println("3 clicked");

	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@value=\"Name of member 1\"]").sendKeys("pritam");
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Done\"]").click();
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@value=\"Name of member 2\"]").sendKeys("pradeep");
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Done\"]").click();
	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@value=\"Name of member 3\"]").sendKeys("friends");

	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"Done\"]").click();


	switchToContext(driver, "NATIVE_APP");
	driver.findElementByXPath("//*[@label=\"img checkbox@2x\"]").click();
	System.out.println("check box selected");
		
		//Register-2 button
		Boolean FullReg=false;
		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//UIAButton[@label=\"REGISTER\"]").click();

	System.out.println("Reg button button2 sucessfully clicked");
	FullReg=true;
		} catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("test case failed for reg button-2 pls check");
		}
		
	try {
		sleep(100);
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"ACCEPT\"]").click();
		System.out.println("accept clicked succesfully");
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("test case failed for accept button-1");
	}

	try {
		sleep(100);
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"OK\"]").click();
		System.out.println("ok cliked succes and reg part end");
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("test case failed for ok button");
	}
	//network id get
	Map<String, Object> params5 = new HashMap<>();
	Object result5 = driver.executeScript("mobile:network.settings:get", params5);
	System.out.println("network details visible");
	//tab interchange

		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"ALL EVENTS\"]").click();
			System.out.println("all event clicked sucessfully");
			
			} catch (Exception e) {
			// TODO: handle exception
		e.getMessage();

		}
		
		try {
			//tab interchange -allevent 
			switchToContext(driver, "NATIVE_APP");

	driver.findElementByXPath("//*[@label=\"UPCOMING EVENTS\"]").click();
	System.out.println("Upcoming event Tab interchange sucessfull");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
//tab interchange all events

		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"ALL EVENTS\"]").click();
			System.out.println("all event clicked sucessfully");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
	

		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"img nextarrow@2x\"]").click();
		//	driver.findElementByXPath("//*[@label=\"img nextarrow@2x\"]").click();

			System.out.println("next caledar clicked");
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			
		}
		
		try {
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"7\"]").click();
           System.out.println("date-7 event clicked");

			
		} 
		catch (Exception e) {
			// TODO: handle exception
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"7\"]").click();
e.getMessage();

		}
		
		//event verify under calendar

		Boolean isDisplayed11=driver.findElementByXPath("//*[@label=\"Test Event 7 December\"]").isDisplayed();


		if(isDisplayed11){
			System.out.println("Event  verified under calendar");
		}
		else {
			System.out.println("event verified under calendar");
		}
		sleep(100);
		
		try {
			switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"10\"]").click();
System.out.println("date 10 clicked");

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}

		try {
			
			switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label=\"img prearrow@2x\"]").click();
			//driver.findElementByXPath("//*[@label=\"img nextarrow@2x\"]").click();

			
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
			
			try {
				switchToContext(driver, "NATIVE_APP");
				driver.findElementByXPath("//*[@label=\"img prearrow@2x\"]").click();
			} catch (Exception e2) {
				// TODO: handle exception
				
				e.getMessage();
			}
			
		}

//minimize the app
		 Map<String, Object> bckgee = new HashMap<>();
		 bckgee.put("timeout", "10");
		 Object bckg12e = driver.executeScript("mobile:application:back", bckgee);
		System.out.println("minimize done");
	Map<String, Object> params25I = new HashMap<>();
	params25I.put("name", "iMobile");
	Object result2I5 = driver.executeScript("mobile:application:open", params25I);
	System.out.println("social network opened");

	Map<String, Object> params18 = new HashMap<>();
	params18.put("keySequence", "HOME");
	Object result18 = driver.executeScript("mobile:presskey", params18);
	System.out.println("pressing home button for back sucess");

	Map<String, Object> FACEB = new HashMap<>();
	FACEB.put("name", "CSR");
	Object FACEB44 = driver.executeScript("mobile:application:open", FACEB);
	System.out.println("go back to csr once again sucessfully");

	//minimize the app
	 Map<String, Object> bckg = new HashMap<>();
	 bckg.put("timeout", "10");
	 Object bckg12 = driver.executeScript("mobile:application:back", bckg);
	System.out.println("minimize done");

	 //start app again for sec
	 Map<String, Object> pemp = new HashMap<>();
	 pemp.put("name", "CSR");
	 Object yuuu = driver.executeScript("mobile:application:open", pemp);
	 System.out.println("open app fofr sec");
	 
	 

	/*Map<String, Object> garbage = new HashMap<>();
	garbage.put("name", "CSR");
	Object garba11 = driver.executeScript("mobile:application:close", garbage);
	System.out.println("close csr for a second");*/
		//minimize the app
	 Map<String, Object> bckgww = new HashMap<>();
	 bckgww.put("timeout", "10");
	 Object bckg12ww = driver.executeScript("mobile:application:back", bckgww);
	System.out.println("minimize done");
	
	Map<String, Object> cameraa = new HashMap<>();
	cameraa.put("name", "camera");
	Object cam78 = driver.executeScript("mobile:application:open", cameraa);
	System.out.println("cam open");

	Map<String, Object> yrtt = new HashMap<>();
	yrtt.put("location", "675,1236");
	Object result12 = driver.executeScript("mobile:touch:tap", yrtt);
	System.out.println("selfie mode on");

	sleep(4000);
	Map<String, Object> params13o = new HashMap<>();
	params13o.put("location", "363,1215");
	Object result813 = driver.executeScript("mobile:touch:tap", params13o);
	System.out.println("image captured");

	Map<String, Object> ptarams14 = new HashMap<>();
	ptarams14.put("name", "CSR");
	Object result414 = driver.executeScript("mobile:application:open", ptarams14);
	System.out.println("csr open");
	sleep(100);
	try {
		switchToContext(driver, "NATIVE_APP");
		driver.findElementByXPath("//*[@label=\"UPCOMING EVENTS\"]").click();
		
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
}
else {
	System.out.println("logo not displayed");
}
sleep(1000);
//verify user profile
boolean isdispalyed2= driver.findElementByXPath("//UIAApplication/UIAWindow[2]/UIAImage[3]").isDisplayed();

if (isdispalyed2) {
	System.out.println("profile pic dispalyed");
} else {
System.out.println("profile pic not displayed..test case failed");
}
 
sleep(1000);
  //verify reg now button
boolean isDisplayed3=driver.findElementByXPath("//UIATableCell[@name=\"Test Event 7 December\"]//*[@label=\"REGISTER NOW\"]").isDisplayed();
if (isDisplayed3) {
	System.out.println("reg now button verified");
} else {
System.out.println("reg now button not verified ..test case failed");
}
//verify 200 points
boolean isDisplayed4=driver.findElementByXPath("//*[@label=\"200 Points\"]").isDisplayed();
switchToContext(driver, "NATIVE_APP");

if (isDisplayed4) {
	System.out.println("200 pints verified");
} else {
System.out.println("200 points not verified//test case failed");
}
sleep(100);
//verify csr logo
boolean isDisplayed5=driver.findElementByXPath("//*[@name=\"iPhone/img_blossom@2x.png\"]").isDisplayed();


if (isDisplayed5) {
	System.out.println("csr logo verified");
} else {
System.out.println("csr logo not verified//test case failed");
}

sleep(100);
//click on event for verify innerpage
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"Test Event 7 December\"]").click();

System.out.println("event cliked for verify");
sleep(1000);
boolean isDisplayed6=driver.findElementByXPath("//*[@label=\"07 December 2017 | 04:30 AM to 05:30 AM\"]").isDisplayed();

if (isDisplayed6) {
	System.out.println("innerpage verified");
} else 
{
System.out.println("inner page not verified//test case failed");
}
//event date verification
boolean isDisplayed9=driver.findElementByXPath("//*[@label=\"07 December 2017 | 04:30 AM to 05:30 AM\"]").isDisplayed();

if (isDisplayed9) {
	System.out.println("Date verified");
} else 
{
System.out.println("Date not verified//test case failed");
}
//verify the event details

Boolean isDisplayed10=driver.findElementByXPath("//*[@label=\"Meeting | magarpatta | One Time\"]").isDisplayed();

if(isDisplayed10){
	System.out.println("Event details verified");
}
else {
	System.out.println("event details test case failed");
}










//Register process complete
//process start for unregister
/*Boolean ur=false;
try {
Map<String, Object> arna = new HashMap<>();
arna.put("content", "Annapurna School");
Object result3 = driver.executeScript("mobile:text:click", arna);
System.out.println("unreg process start --event cliking succesfully");
ur=true;
} catch (Exception e) 
{
	Map<String, Object> Alambss = new HashMap<>();
	Alambss.put("content", "Alamb");
	Object amb = driver.executeScript("mobile:text:click", Alambss);
	// TODO: handle exception
	System.out.println("test case failed for reg button-2 pls check");
}
sleep(100);
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"Unregister\"]").click();
System.out.println("unregister button clicked succefully");
sleep(100);
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"OK\"]").click();
System.out.println("alert pop up-ok for unregister");
sleep(100);
switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"OK\"]").click();
System.out.println("ok clicked for unreg sucess alert popup");
sleep(4000);
Map<String, Object> mannat = new HashMap<>();
mannat.put("location", "409,974");
Object kekas = driver.executeScript("mobile:touch:tap", mannat);



System.out.println("Test event 7 clicked");

switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"Unregister\"]").click();

switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"OK\"]").click();

switchToContext(driver, "NATIVE_APP");
driver.findElementByXPath("//*[@label=\"OK\"]").click();
}
*/
		

/*
	
		
			
			
			 
		

			/*
			 * sleep(10000); switchToContext(driver, "NATIVE_APP");
			 * driver.quit();
			 */

			

			// ----------------------Update until here--------------
			
			/* * System.out.println(deviceID + " Step7: Click on User Account");
			 * if (index == 2) { // Android switchToContext(driver,
			 * "NATIVE_APP"); driver.findElementByXPath(
			 * "//android.widget.RelativeLayout[3]/android.widget.ImageView[1]")
			 * .click(); } else { // iOS switchToContext(driver, "NATIVE_APP");
			 * driver.findElementByXPath("//UIATabBar/UIAButton[3]").click(); }
			 * 
			 * sleep(4000); System.out.println(deviceID +
			 * " Verify: User account screen opened"); Map<String, Object>
			 * params6 = new HashMap<>(); params6.put("content", "Logout");
			 * params6.put("source", "native"); String result6 = (String)
			 * driver.executeScript("mobile:checkpoint:text", params6); if
			 * (result6.equals("true")) { System.out.println(deviceID +
			 * " Result: TRUE"); } else { System.out.println(deviceID +
			 * " Result: FALSE"); }
			 * 
			 * // Click on Logout System.out.println(deviceID +
			 * " Step8: Click on Logout"); result6 = (String)
			 * driver.executeScript("mobile:text:select", params6);
			 * 
			 sleep(4000);
			 System.out.println(deviceID +" Verify: User logged out");
			 Map<String, Object> params70 = new HashMap<>(); 
			 params70.put("content", "Sign In");
			  params70.put("source", "native");
			  String result70 = (String)driver.executeScript("mobile:checkpoint:text", params70); 
			  if(result70.equals("true")) 
			  { System.out.println(deviceID +" Result: TRUE"); 
			  } 
			  else
			  { System.out.println(deviceID +" Result: FALSE"); }
			 */

			// ------------Finish-- Close the application----------
		/*	sleep(5000);
			System.out.println(deviceID + " Finish: Close the App");
			Map<String, Object> params22i2 = new HashMap<>();
			params22i2.put("name", "speakapp");
			Object result22i2 = driver.executeScript("mobile:application:close", params22i2);

			System.out.println(deviceID + " Finish: Close the App");
			Map<String, Object> params220 = new HashMap<>();
			params220.put("name", "iLearn");
			Object result220 = driver.executeScript("mobile:application:close", params220);*/

			// driver.quit();
			// In case you want to down the report or the report
			// attachments, do it here.
			String PathAndFileName;
			// try {
			if (index == 2) { // android
				PathAndFileName = "C:\\test\\AndroidReportTestFlowo";
			} else { // iOS
				PathAndFileName = "C:\\test\\iOSReportTestFlowo";
			}

			// This line of code is generating the report and saving it on the
			// drive
			RemoteWebDriverUtils.downloadReport(driver, "pdf", PathAndFileName); // Here
			//RemoteWebDriverUtils.downloadAttachment(driver, "video", "C:\test\repovideo", "flv");
			//RemoteWebDriverUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

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
// my learning ending
/*
 * sleep(10000); Map<String, Object> params211 = new HashMap<>();
 * params211.put("content", "Basic Selenium WebDriver ");
 * params211.put("source", "primary"); Object result211 =
 * driver.executeScript("mobile:text:select", params211); sleep(10000);
 * System.out.println(deviceID +
 * " Verify: In the spotlight articles list page visible");
 * 
 * Map<String, Object> params212 = new HashMap<>(); params212.put("content",
 * "Basic Selenium WebDriver"); params212.put("source", "native"); String
 * result260 = (String) driver.executeScript("mobile:checkpoint:text",
 * params212); if (result260.equals("true")) { System.out.println(deviceID +
 * " Result: TRUE"); } else { System.out.println(deviceID + " Result: FALSE"); }
 */
/*
 * // Getting first article title String firstArticleTitle; if (index == 2) { //
 * Android switchToContext(driver, "NATIVE_APP"); firstArticleTitle =
 * driver.findElementByClassName("//android.widget.RelativeLayout").click();
 * 
 * } else { // iOS switchToContext(driver, "NATIVE_APP"); firstArticleTitle =
 * driver.findElementByXPath("//UIATableCell[1]/UIAStaticText[2]")
 * .getAttribute("value"); } System.out.println(deviceID +
 * " Title of first article is: " + firstArticleTitle);
 */

// -------start video
