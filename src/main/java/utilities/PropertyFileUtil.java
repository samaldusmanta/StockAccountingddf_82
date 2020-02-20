package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {

	public static String getValueForKey( String key   ) throws IOException{
		
		Properties p=new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\PropertiesFile\\Environment.properties");
		p.load(fis);
		return p.getProperty(key);
	}
}
