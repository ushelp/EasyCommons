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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * EasyCommons 项目下的 Properties 文件操作工具类 <br/>
 * EasyProperties：替代java.util.Properties的properties文件类，
 * 扩展了java.util.Properties，提供了内容合并（merge: 增, 删, 改）和修改（modify:
 * 增,改）功能，修改属性时不影响原格式。 <br/>
 * 适合场景：任何需要对properties文件进行读写操作的场景，不影响文件原格式。 <br/>
 * 
 * @author easyproject.cn
 * 
 */
@SuppressWarnings("resource")
public class EasyProperties extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * EasyProperties内部使用的merge方法（增，删，改）
	 * 
	 * @return 合并结果
	 * @throws IOException
	 */
	private boolean merge(BufferedReader br, BufferedWriter bw)
			throws IOException {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) this.clone();
		try {
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.getProperty(key);
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
				bw.write(sb.toString());
				if (!tempProperties.isEmpty()) {

					for (Entry<Object, Object> entry : tempProperties
							.entrySet()) {
						bw.write(entry.getKey() + "=" + entry.getValue());
						bw.newLine();
					}
				}

				flag = true;
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						throw e;
					}
				}
			}

		}
		return flag;
	}
	
	
	/**
	 * EasyProperties内部使用的modify方法（增，删）
	 * 
	 * @return 合并结果
	 * @throws IOException
	 */
	private boolean modify(BufferedReader br, BufferedWriter bw)
			throws IOException {
		
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) this.clone();
		try {
			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					if (tempProperties.containsKey(key)) { // 如果存在要修改的属性，则修改
						s = key + "=" + tempProperties.getProperty(key);
						tempProperties.remove(key);
					}
				}
				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
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
				throw e;
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						throw e;
					}
				}
			}

		}
		return flag;
	}

	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFile
	 *            合并到的properties文件
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean mergeToFile(File propertiesFile) {
		return this.mergeToFile(propertiesFile, null);
	}

	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFilePath
	 *            合并到的properties文件路径
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean mergeToFile(String propertiesFilePath) {
		return this.mergeToFile(new File(propertiesFilePath));
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFile 修改到的properties文件
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean modifyToFile(File propertiesFile) {
		return this.modifyToFile(propertiesFile, null);
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFilePath 修改到的properties文件路径
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean modifyToFile(String propertiesFilePath) {
		return modifyToFile(new File(propertiesFilePath));
	}


	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFile
	 *            合并到的properties文件
	 * @param charset
	 *            文件字符集
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean mergeToFile(File propertiesFile, String charset){

		try {
			BufferedReader br=null;
			BufferedWriter bw=null;
			if(charset!=null){
				br = new BufferedReader(new InputStreamReader(new FileInputStream(propertiesFile), charset));
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(propertiesFile), charset));
			}else{
				FileReader fr = new FileReader(propertiesFile);
				br = new BufferedReader(fr);

				FileWriter fw = new FileWriter(propertiesFile);
				bw = new BufferedWriter(fw);
			}
			return merge(br, bw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFilePath
	 *            合并到的properties文件路径
	 * @param charset
	 *            文件字符集
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean mergeToFile(String propertiesFilePath, String charset)
			throws IOException {
		return this.mergeToFile(new File(propertiesFilePath), charset);
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFile 修改到的properties文件
	 * @param charset
	 *            文件字符集
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean modifyToFile(File propertiesFile, String charset){
		try {
			BufferedReader br=null;
			BufferedWriter bw=null;
			if(charset!=null){
				br = new BufferedReader(new InputStreamReader(new FileInputStream(propertiesFile), charset));
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(propertiesFile), charset));
			}else{
				FileReader fr = new FileReader(propertiesFile);
				br = new BufferedReader(fr);

				FileWriter fw = new FileWriter(propertiesFile);
				bw = new BufferedWriter(fw);
			}
			return modify(br, bw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFilePath 修改到的properties文件路径
	 * @return 合并结果
	 * @param charset
	 *            文件字符集
	 * @throws IOException
	 *             操作异常
	 */
	public boolean modifyToFile(String propertiesFilePath, String charset){
		return modifyToFile(new File(propertiesFilePath), charset);
	}

}
