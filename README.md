# MentorModules for Android #

### Installation ###

To integrate MentorModules into your Android project, please  follow these steps:

1. Copy *MentorSdk.jar* into *lib* folder in your project
2. Add *MentorSdk.jar* as a library into the project, this will add this command:
  ```gradle
    implementation files('libs/MentorSdk.jar')
  ```
  into build.gradle file
  
3. Also please add the following dependency to build.gradle file
```gradle
    implementation 'com.squareup.okhttp3:okhttp:4.1.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.1.0'
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
  ```
  4. Please make sure that you add these permissions into *AndroidManifest.xml* 
  ```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_INTERNAL_STORAGE" />
  ```
  


### Usage ###

```java
import com.edriving.mentor.modulessdk.MentorModuleProvider;
import com.edriving.mentor.modulessdk.network.model.MentorModule;
import com.edriving.mentor.modulessdk.network.exception.InitializationException;
import com.edriving.mentor.modulessdk.network.exception.InvalidKeyException;
import com.edriving.mentor.modulessdk.network.exception.ModuleInitializationException;
import com.edriving.mentor.modulessdk.network.exception.ModuleListException;
```
Create an instance of *MentorModuleProvider* in a non-UI thread and please make sure that the user granted permission to access the storage.

either 
```java
MentorModuleProvider moduleProvider = new MentorModuleProvider(getApplicationContext(),sdkKey,yourUserClientId);
```
or
```java
moduleProvider = new MentorModuleProvider(getApplicationContext(),sdkKey,yourUserClientId,"es","419","America/Mexico_City");
```
Please use the second option if you need the modules in other supported languages<br/>
The value for these arguments are explained in these links:<br/>
Language: https://www.wikiwand.com/en/List_of_ISO_639-1_codes<br/>
Country Code: https://www.wikiwand.com/en/ISO_3166-1_alpha-2<br/>
Timezone: https://en.wikipedia.org/wiki/List_of_tz_database_time_zones<br/>

Get modules 
```java
List<MentorModule> moduleList = moduleProvider.getModules();
```



