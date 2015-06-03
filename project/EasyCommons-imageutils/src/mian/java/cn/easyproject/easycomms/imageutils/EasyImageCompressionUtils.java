package cn.easyproject.easycomms.imageutils;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * EasyCommons 项目下的图片处理工具类 <br/>
 * EasyImageCompression：图片大小压缩改变工具类，支持网络图片，等比，最大宽高等多种模式。 <br/>
 * 适合场景：上传图片时压缩到不同大小。 <br/>
 * 
 * @author easyproject.cn
 * 
 */
public class EasyImageCompressionUtils {
	
	/**
	 * 将带缓冲的输入流信息输入写入到带缓冲的输出流
	 * @param bis 带缓冲的输入流
	 * @param bos 带缓冲的输出流
	 * @throws IOException IO异常
	 */
	private static boolean bisToBos(BufferedInputStream bis,BufferedOutputStream bos){
		boolean flag=false;
		try {
			byte[] buf = new byte[8192];
			int len = bis.read(buf);
			while (-1 != len) {
				bos.write(buf, 0, len);
				len = bis.read(buf);
			}
			flag=true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 判断非空，然后关闭流对象
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != bos) {
				try {
					bos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	/**
	 * 按最大高度压缩内部核心方法
	 * @param img 图片
	 * @param sourceFile 来自文件（网络图片为null）
	 * @param toFile 保存文件
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	private static boolean compressByMaxHeightCore(InputStream is,File sourceFile,File toFile,int maxHeight){
		boolean flag=false;
		FileOutputStream out = null;
		BufferedImage img = null; // 输入图片
		try {
			img = ImageIO.read(is);
			// 判断图片格式是否正确
			if (img.getWidth(null) != -1) {
				double nowHeight = (double) img.getHeight(null);
				// 判断原始图片高度是否大于最大允许的高度
				if (nowHeight > maxHeight) { // 如果大于压缩比例
					int newHeight = maxHeight; // 图片宽度最大
					double rate = nowHeight / (double) maxHeight;
					// 高度等比缩放
					int newWidth = (int) (((double) img.getWidth(null)) / rate);

					BufferedImage tag = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					Image i = img.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH);
					tag.getGraphics().drawImage(i, 0, 0, null);
					out = new FileOutputStream(toFile); // 输出图像
					// JPEGImageEncoder可适用于其他图片类型的转换
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(tag);
					/*
					 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好
					 * 但速度慢
					 */
					flag=true;
				}else{ //不大于压缩比例，无需压缩
				
					if(sourceFile!=null){ 	//非网络图片
						//是否需要写入新文件，（如果无需写入sourceFile==toFile，压缩覆盖原图）
						if(sourceFile==toFile||sourceFile.getCanonicalFile().equals(toFile.getCanonicalFile())){ //如果目标文件和源文件不同
							flag=true;
						}else{ //将压缩保存到新位置
							flag= saveFile(sourceFile, toFile);
						}
					}else{ //网络图片
						flag=saveFile(is, toFile);
					}
				}
			}else{
				//非图片无法压缩
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return flag;
	}
	/**
	 * 按最大高度压缩内部核心方法
	 * @param img 图片
	 * @param sourceFile 来自文件（网络图片为null）
	 * @param toFile 保存文件
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	private static boolean compressByMaxWidthAndMaxHeightCore(InputStream is,File sourceFile,File toFile, int maxWidth,int maxHeight){
		boolean flag=false;
		FileOutputStream out = null;
		BufferedImage img = null; // 输入图片
		try {
			img = ImageIO.read(is);
			// 判断图片格式是否正确
			if (img.getWidth(null) != -1) {
				int newWidth=0;
				int newHeight=0;

				if ((double) img.getWidth(null) > maxWidth
						|| (double) img.getHeight(null) > maxHeight) {

					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) maxWidth + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) maxHeight + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);


					BufferedImage tag = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					Image i = img.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH);
					tag.getGraphics().drawImage(i, 0, 0, null);
					out = new FileOutputStream(toFile); // 输出图像
					// JPEGImageEncoder可适用于其他图片类型的转换
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(tag);
					/*
					 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好
					 * 但速度慢
					 */
					flag=true;
				}else{ //不大于压缩比例，无需压缩
				
					if(sourceFile!=null){ 	//非网络图片
						//是否需要写入新文件，（如果无需写入sourceFile==toFile，压缩覆盖原图）
						if(sourceFile==toFile||sourceFile.getCanonicalFile().equals(toFile.getCanonicalFile())){ //如果目标文件和源文件不同
							flag=true;
						}else{ //将压缩保存到新位置
							flag= saveFile(sourceFile, toFile);
						}
					}else{ //网络图片
						flag=saveFile(is, toFile);
					}
				}
			}else{
				//非图片无法压缩
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return flag;
	}
	/**
	 * 按最大宽度压缩内部核心方法
	 * @param img 图片
	 * @param sourceFile 来自文件（网络图片为null）
	 * @param toFile 保存文件
	 * @param maxWidth 最大宽度
	 * @return 压缩成功或失败
	 */
	private static boolean compressByMaxWidthCore(InputStream is,File sourceFile,File toFile,int maxWidth){
		boolean flag=false;
		FileOutputStream out = null;
		BufferedImage img = null; // 输入图片
		try {
			img = ImageIO.read(is);
			// 判断图片格式是否正确
			if (img.getWidth(null) != -1) {
				double nowWidth = (double) img.getWidth(null);
				// 判断原始图片宽度是否大于最大允许的宽度
				if (nowWidth > maxWidth) { // 如果大于压缩比例
					int newWidth = maxWidth; // 图片宽度最大
					double rate = nowWidth / (double) maxWidth;
					// 高度等比缩放
					int newHeight = (int) (((double) img.getHeight(null)) / rate);

					BufferedImage tag = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					Image i = img.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH);
					tag.getGraphics().drawImage(i, 0, 0, null);
					out = new FileOutputStream(toFile); // 输出图像
					// JPEGImageEncoder可适用于其他图片类型的转换
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(tag);
					/*
					 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好
					 * 但速度慢
					 */
					flag=true;
				}else{ //不大于压缩比例，无需压缩
				
					if(sourceFile!=null){ 	//非网络图片
						//是否需要写入新文件，（如果无需写入sourceFile==toFile，压缩覆盖原图）
						if(sourceFile==toFile||sourceFile.getCanonicalFile().equals(toFile.getCanonicalFile())){ //如果目标文件和源文件不同
							flag=true;
						}else{ //将压缩保存到新位置
							flag= saveFile(sourceFile, toFile);
						}
					}else{ //网络图片
						flag=saveFile(is, toFile);
					}
				}
			}else{
				//非图片无法压缩
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return flag;
	}
	/**
	 * 按指定宽度和高度压缩图片内部核心方法
	 * @param img 图片
	 * @param sourceFile 来自文件（网络图片为null）
	 * @param toFile 保存文件
	 * @param width 指定宽度，-1代表不变
	 * @param height 指定高度，-1代表不变
	 * @return 压缩成功或失败
	 */
	private static boolean compressByWidthAndHeightCore(InputStream is,File sourceFile,File toFile, int width, int height){
		boolean flag=false;
		FileOutputStream out = null;
		BufferedImage img = null; // 输入图片
		try {
			img = ImageIO.read(is);
			// 判断图片格式是否正确
			if (img.getWidth(null) != -1) {
				
				double nowWidth = (double) img.getWidth(null);
				double nowHeight = (double) img.getHeight(null);
				
				// 判断原始图片宽度是否大于最大允许的宽度
				if (!(nowWidth == width && nowHeight == height)) { // 如果大于压缩比例
					int newWidth = width; // 宽度
					int newHeight = height; // 高度
					
					//如果为-1，则长度不变
					if(newWidth==-1){
						newWidth=(int) nowWidth;
					}
					if(newHeight==-1){
						newWidth=(int) nowHeight;
					}
					
					BufferedImage tag = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					Image i = img.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH);
					tag.getGraphics().drawImage(i, 0, 0, null);
					out = new FileOutputStream(toFile); // 输出图像
					// JPEGImageEncoder可适用于其他图片类型的转换
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(tag);
					/*
					 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好
					 * 但速度慢
					 */
					flag=true;
				}else{ //不大于压缩比例，无需压缩
				
					if(sourceFile!=null){ 	//非网络图片
						//是否需要写入新文件，（如果无需写入sourceFile==toFile，压缩覆盖原图）
						if(sourceFile==toFile||sourceFile.getCanonicalFile().equals(toFile.getCanonicalFile())){ //如果目标文件和源文件不同
							flag=true;
						}else{ //将压缩保存到新位置
							flag= saveFile(sourceFile, toFile);
						}
					}else{ //网络图片
						flag=saveFile(is, toFile);
					}
				}
			}else{
				//非图片无法压缩
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return flag;
	}
	/**
	 * 根据最大高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toFile 压缩后的文件
	 * @param maxHeight 最大高度
	 * @return 压缩是否成功
	 */
	public static boolean compressPicByMaxHeight(File sourceFile, File toFile, int maxHeight){
		boolean flag=false;
		InputStream is=null;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxHeightCore(is, sourceFile, toFile, maxHeight);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	/**
	 * 根据最大高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxHeight(File sourceFile, int maxHeight){
		boolean flag=false;
		File toFile=sourceFile;
		InputStream is;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxHeightCore(is, toFile, sourceFile, maxHeight);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据最大高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toPath 保存的图片文件路径
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxHeight(File sourceFile, String toPath, int maxHeight){
		boolean flag=false;
		InputStream is=null;
		File toFile=new File(toPath);
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxHeightCore(is, sourceFile, toFile, maxHeight);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大高度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toFile 压缩后的文件
	 * @param maxHeight 最大高度
	 * @return 压缩是否成功
	 */
	public static boolean compressPicByMaxHeight(String fromPath, File toFile, int maxHeight){
		boolean flag=false;
		InputStream is=null;
		File sourceFile=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByMaxHeightCore(is, sourceFile, toFile, maxHeight);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大高度压缩图片
	 * @param fromPath 图片路径（包括http://开头的网络图片）
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxHeight(String fromPath, int maxHeight){
		boolean flag=false;
		File sourceFile=null;
		File toFile=sourceFile;
		InputStream is=null;
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag=compressByMaxHeightCore(is, sourceFile, toFile, maxHeight);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大高度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toPath 保存的图片文件路径
	 * @param maxHeight 最大高度
	 * @return 压缩是否成功
	 */
	public static boolean compressPicByMaxHeight(String fromPath, String toPath, int maxHeight){
		boolean flag=false;
		File sourceFile=null;
		File toFile=new File(toPath);
		InputStream is=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByMaxHeightCore(is, sourceFile, toFile, maxHeight);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return flag;
	}
	/**
	 * 根据最大宽度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toFile 压缩后的文件
	 * @param maxWidth 最大宽度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidth(File sourceFile, File toFile, int maxWidth){
		boolean flag=false;
		InputStream is=null;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxWidthCore(is, sourceFile, toFile, maxWidth);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大宽度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param maxWidth 最大宽度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidth(File sourceFile, int maxWidth){
		boolean flag=false;
		File toFile=sourceFile;
		InputStream is;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxWidthCore(is, toFile, sourceFile, maxWidth);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
	
	
	/**
	 * 根据最大宽度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toPath 保存的图片文件路径
	 * @param maxWidth 最大宽度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidth(File sourceFile, String toPath, int maxWidth){
		boolean flag=false;
		InputStream is=null;
		File toFile=new File(toPath);
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxWidthCore(is, sourceFile, toFile, maxWidth);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 根据最大宽度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toFile 压缩后的文件
	 * @param maxWidth 最大宽度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidth(String fromPath, File toFile, int maxWidth){
		boolean flag=false;
		InputStream is=null;
		File sourceFile=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByMaxWidthCore(is, sourceFile, toFile, maxWidth);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大宽度压缩图片
	 * @param fromPath 图片路径（包括http://开头的网络图片）
	 * @param maxWidth 最大宽度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidth(String fromPath, int maxWidth){
		boolean flag=false;
		File sourceFile=null;
		File toFile=sourceFile;
		InputStream is=null;
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag=compressByMaxWidthCore(is, sourceFile, toFile, maxWidth);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大宽度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toPath 保存的图片文件路径
	 * @param maxWidth 最大宽度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidth(String fromPath, String toPath, int maxWidth){
		boolean flag=false;
		File sourceFile=null;
		File toFile=new File(toPath);
		InputStream is=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByMaxWidthCore(is, sourceFile, toFile, maxWidth);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return flag;
	}
	/**
	 * 根据最大宽度和高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toFile 压缩后的文件
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidthAndMaxHeight(File sourceFile, File toFile, int maxWidth, int maxHeight){
		boolean flag=false;
		InputStream is=null;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxWidthAndMaxHeightCore(is, sourceFile, toFile, maxWidth, maxHeight);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大宽度和高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidthAndMaxHeight(File sourceFile, int maxWidth, int maxHeight){
		boolean flag=false;
		File toFile=sourceFile;
		InputStream is;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxWidthAndMaxHeightCore(is, toFile, sourceFile, maxWidth, maxHeight);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 根据最大宽度和高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toPath 保存的图片文件路径
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidthAndMaxHeight(File sourceFile, String toPath, int maxWidth, int maxHeight){
		boolean flag=false;
		InputStream is=null;
		File toFile=new File(toPath);
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByMaxWidthAndMaxHeightCore(is, sourceFile, toFile, maxWidth, maxHeight);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	
	
	/**
	 * 根据最大宽度和高度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toFile 压缩后的文件
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidthAndMaxHeight(String fromPath, File toFile, int maxWidth, int maxHeight){
		boolean flag=false;
		InputStream is=null;
		File sourceFile=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByMaxWidthAndMaxHeightCore(is, sourceFile, toFile, maxWidth, maxHeight);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 根据最大宽度和高度压缩图片
	 * @param fromPath 图片路径（包括http://开头的网络图片）
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidthAndMaxHeight(String fromPath, int maxWidth, int maxHeight){
		boolean flag=false;
		File sourceFile=null;
		File toFile=sourceFile;
		InputStream is=null;
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag=compressByMaxWidthAndMaxHeightCore(is, sourceFile, toFile, maxWidth, maxHeight);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据最大宽度和高度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toPath 保存的图片文件路径
	 * @param maxWidth 最大宽度
	 * @param maxHeight 最大高度
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByMaxWidthAndMaxHeight(String fromPath, String toPath, int maxWidth, int maxHeight){
		boolean flag=false;
		File sourceFile=null;
		File toFile=new File(toPath);
		InputStream is=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByMaxWidthAndMaxHeightCore(is, sourceFile, toFile, maxWidth, maxHeight);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return flag;
	}
	/**
	 * 根据指定宽度和高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toFile 压缩后的文件
	 * @param width 指定宽度，-1代表不变
	 * @param height 指定高度，-1代表不变
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByWidthAndHeight(File sourceFile, File toFile, int width, int height){
		boolean flag=false;
		InputStream is=null;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByWidthAndHeightCore(is, sourceFile, toFile, width, height);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据指定宽度和高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param width 指定宽度，-1代表不变
	 * @param height 指定高度，-1代表不变
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByWidthAndHeight(File sourceFile, int width, int height){
		boolean flag=false;
		File toFile=sourceFile;
		InputStream is;
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByWidthAndHeightCore(is, toFile, sourceFile, width, height);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 根据指定宽度和高度压缩图片
	 * @param sourceFile 要压缩的文件
	 * @param toPath 保存的图片文件路径
	 * @param width 指定宽度，-1代表不变
	 * @param height 指定高度，-1代表不变
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByWidthAndHeight(File sourceFile, String toPath, int width, int height){
		boolean flag=false;
		InputStream is=null;
		File toFile=new File(toPath);
		try {
			is = new FileInputStream(sourceFile);
			flag= compressByWidthAndHeightCore(is, sourceFile, toFile, width, height);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	/**
	 * 根据指定宽度和高度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toFile 压缩后的文件
	 * @param width 指定宽度，-1代表不变
	 * @param height 指定高度，-1代表不变
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByWidthAndHeight(String fromPath, File toFile, int width, int height){
		boolean flag=false;
		InputStream is=null;
		File sourceFile=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByWidthAndHeightCore(is, sourceFile, toFile, width, height);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	/**
	 * 根据指定宽度和高度压缩图片
	 * @param fromPath 图片路径（包括http://开头的网络图片）
	 * @param width 指定宽度，-1代表不变
	 * @param height 指定高度，-1代表不变
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByWidthAndHeight(String fromPath, int width, int height){
		boolean flag=false;
		File sourceFile=null;
		File toFile=sourceFile;
		InputStream is=null;
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag=compressByWidthAndHeightCore(is, sourceFile, toFile, width, height);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 根据指定宽度和高度压缩图片
	 * @param fromPath  图片路径（包括http://开头的网络图片）
	 * @param toPath 保存的图片文件路径
	 * @param width 指定宽度，-1代表不变
	 * @param height 指定高度，-1代表不变
	 * @return 压缩成功或失败
	 */
	public static boolean compressPicByWidthAndHeight(String fromPath, String toPath, int width, int height){
		boolean flag=false;
		File sourceFile=null;
		File toFile=new File(toPath);
		InputStream is=null;
		
		try {
			if (fromPath.toLowerCase().startsWith("http://")) {
				URL u = new URL(fromPath); // 获得网络资源
				is = u.openStream(); // 获得URL的输入数据流
			}else{
				 sourceFile=new File(fromPath);
				 is = new FileInputStream(sourceFile);
			}
			flag= compressByWidthAndHeightCore(is, sourceFile, toFile, width, height);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return flag;
	}
	
	
	/**
	 * 复制文件（将源文件复制到目标文件）
	 * @param file 源文件
	 * @param toFile 目标文件
	 * @return 复制成功与否
	 */
	private static  boolean saveFile(File sourceFile,File toFile){
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		boolean fileFlag=false;
		try {
			bis = new BufferedInputStream(new FileInputStream(sourceFile));// 读取上传的文件
			bos = new BufferedOutputStream(new FileOutputStream(toFile)); // 输出到新文件
			fileFlag=bisToBos(bis, bos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return fileFlag;
	}
	
	
	/**
	 * 将网路输入流信息，写入文件
	 * @param is 输入流
	 * @param toFile 文件对象
	 * @return 复制成功与否
	 */
	private static  boolean saveFile(InputStream is,File toFile){
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		boolean fileFlag=false;
		try {
				bis = new BufferedInputStream(is); // 读取上传的文件
				bos = new BufferedOutputStream(new FileOutputStream(toFile)); // 输出到新文件
			fileFlag = bisToBos(bis, bos);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return fileFlag;
	}
	
	
}
