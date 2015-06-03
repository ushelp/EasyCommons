package cn.easyproject.easycomms.objetctproperties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * EasyCommons 项目下的  Properties 文件操作工具类 <br/>
 * EasyPropertiesUtils：直接修改Properties文件的工具类，提供原格式写功能，包括保留注释，支持内容合并（merge: 增, 删, 改）和修改（modify: 增,改）。 <br/>
 * 适合场景：对properties文件进行修改操作，又不影响原格式的情况。 <br/>
 * 
 * @author easyproject.cn
 * 
 */
public class EasyPropertiesUtils {

	/**
	 * 保留文件原格式，注释等
	 * 将properties中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath,
			Properties properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();

		try {
			FileReader fr = new FileReader(propertiesFilePath);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				FileWriter fw = new FileWriter(propertiesFilePath);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等
	 * 将properties中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath,
			Properties properties, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();

		try {
			InputStream is = new FileInputStream(propertiesFilePath);
			InputStreamReader fr = new InputStreamReader(is, charset);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is = new FileOutputStream(propertiesFilePath);
				OutputStreamWriter fw = new OutputStreamWriter(is, charset);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties中的数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile,
			Properties properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();

		try {
			FileReader fr = new FileReader(propertiesFile);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				FileWriter fw = new FileWriter(propertiesFile);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties中的数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile,
			Properties properties, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();

		try {
			InputStream is = new FileInputStream(propertiesFile);
			InputStreamReader fr = new InputStreamReader(is, charset);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is = new FileOutputStream(propertiesFile);
				OutputStreamWriter fw = new OutputStreamWriter(is, charset);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将Map中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath,
			Map<String, String> properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			FileReader fr = new FileReader(propertiesFilePath);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				FileWriter fw = new FileWriter(propertiesFilePath);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将Map中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath,
			Map<String, String> properties, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			InputStream is = new FileInputStream(propertiesFilePath);
			InputStreamReader fr = new InputStreamReader(is, charset);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is = new FileOutputStream(propertiesFilePath);
				OutputStreamWriter fw = new OutputStreamWriter(is, charset);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile,
			Map<String, String> properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			FileReader fr = new FileReader(propertiesFile);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				FileWriter fw = new FileWriter(propertiesFile);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile,
			Map<String, String> properties, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			InputStream is = new FileInputStream(propertiesFile);
			InputStreamReader fr = new InputStreamReader(is, charset);

			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {

				OutputStream is = new FileOutputStream(propertiesFile);
				OutputStreamWriter fw = new OutputStreamWriter(is, charset);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile,
			Map<String, String> properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			InputStream is=new FileInputStream(propertiesFile);
			InputStreamReader fr=new InputStreamReader(is);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is=new FileOutputStream(propertiesFile);
				OutputStreamWriter fw=new OutputStreamWriter(is);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}
	
	
	
	
	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset 文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile,
			Map<String, String> properties,String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			InputStream is=new FileInputStream(propertiesFile);
			InputStreamReader fr=new InputStreamReader(is,charset);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is=new FileOutputStream(propertiesFile);
				OutputStreamWriter fw=new OutputStreamWriter(is,charset);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath,
			Map<String, String> properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			FileReader fr = new FileReader(propertiesFilePath);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				FileWriter fw = new FileWriter(propertiesFilePath);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}
	
	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset 文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath,
			Map<String, String> properties,String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			InputStream is=new FileInputStream(propertiesFilePath);
			InputStreamReader fr=new InputStreamReader(is,charset);

			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (properties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + properties.get(key);
						properties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is=new FileOutputStream(propertiesFilePath);
				OutputStreamWriter fw=new OutputStreamWriter(is,charset);

				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile,
			Properties properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();
		try {
			FileReader fr = new FileReader(propertiesFile);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				FileWriter fw = new FileWriter(propertiesFile);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset 文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile,
			Properties properties,String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();
		try {
			InputStream is=new FileInputStream(propertiesFile);
			InputStreamReader fr=new InputStreamReader(is,charset);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is=new FileOutputStream(propertiesFile);
				OutputStreamWriter fw=new OutputStreamWriter(is,charset);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}
	
	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath,
			Properties properties) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();
		try {
			FileReader fr = new FileReader(propertiesFilePath);
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				FileWriter fw = new FileWriter(propertiesFilePath);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset 文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath,
			Properties properties,String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		BufferedWriter bw = null;
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) properties.clone();
		try {
			InputStream is=new FileInputStream(propertiesFilePath);
			InputStreamReader fr=new InputStreamReader(is,charset);

			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.get(key);
						tempProperties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (flag) {
			flag = false;
			try {
				OutputStream is=new FileOutputStream(propertiesFilePath);
				OutputStreamWriter fw=new OutputStreamWriter(is,charset);
				bw = new BufferedWriter(fw);
				bw.write(sb.toString());

				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}
	
	
}
