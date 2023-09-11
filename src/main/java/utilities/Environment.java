package utilities;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

//@Sources({"classpath:${env}.properties"}) //su dung cai nay neu file property dat trong folder resources
@Sources({"file:environmentConfig/${env}.properties"}) //su dung cai nay neu file property dat trong folder khac folder resource
public interface Environment extends Config{
	@Key("App.Url")
	String appUrl();
	
	@Key("App.User")
	String appUsername();
	
	@Key("App.Pass")
	String appPassword();
	
	@Key("DB.Host")
	String dbHostname();
	
	@Key("DB.User")
	String dbUsername();
	
	@Key("DB.Pass")
	String dbPassword();
	
}
