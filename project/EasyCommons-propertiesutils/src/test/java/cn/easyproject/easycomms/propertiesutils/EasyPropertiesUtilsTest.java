package cn.easyproject.easycomms.propertiesutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import cn.easyproject.easycomms.objetctproperties.EasyPropertiesUtils;

public class EasyPropertiesUtilsTest {
	
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		Properties p = new Properties();
		p.load(new FileInputStream("a.properties"));
		//
		p.setProperty("3", "dddd");
		// p.setProperty("a", "AAAA");
		p.remove("a");
		EasyPropertiesUtils.merger("a.properties", p);

		p.store(new FileOutputStream("a.properties"), "this is 注释！");

		// HashMap m=new HashMap();
		// m.put("c","dddd");
		// m.put("a","AAA");
		// Map m2=m;

		// System.out.println(m==m2);
		// System.out.println(System.getProperty("line.separator"));
		// PropertiesEdit.modify("a.properties", m);
	}
}
