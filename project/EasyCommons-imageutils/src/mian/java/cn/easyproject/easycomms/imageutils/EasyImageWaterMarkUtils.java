package cn.easyproject.easycomms.imageutils;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

/**
 * EasyCommons 项目下的图片处理工具类 <br/>
 * EasyImageWaterMarkUtil：图片水印工具类。为图片添加图片水印或文字水印。 <br/>
 * 适合场景：上传图片时添加水印。 <br/>
 * 
 * @author easyproject.cn
 * 
 */
public class EasyImageWaterMarkUtils {
	
	/**
	 * 把图片印刷到图片上
	 * @param waterImgFile 水印图片文件
	 * @param sourceImgFile  目标图片文件
	 */
	public final static void imageWatermark(File waterImgFile, File sourceImgFile) {
		try {
			// 目标文件
			
			Image src = ImageIO.read(sourceImgFile);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			
			// 水印文件
			Image waterImg = ImageIO.read(waterImgFile);
			int width_target = waterImg.getWidth(null);
			int height_target = waterImg.getHeight(null);
			
//			if ((width -width_target> 100) && height - height_target > 100) { // 图片足够大才设置水印
			
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) image.getGraphics();
			g.drawImage(src, 0, 0, width, height, null);

			//居中
//				g.drawImage(waterImg, (width - width_target) / 2, (height - height_target) / 2, width_target, height_target, null);
//				//距右下角
			g.drawImage(waterImg, width - width_target-10, height - height_target-10, width_target, height_target, null);
			
			// 水印文件结束
			g.dispose();
			//写出到目标文件
			ImageIO.write(image, "png", sourceImgFile);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 把图片印刷到源图片上，并生成新图片
	 * @param waterImgFile 水印图片文件
	 * @param sourceImgFile  源图片文件
	 * @param targetImgFile  生成的带水印目标文件
	 */
	public final static void imageWatermark(File waterImgFile, File sourceImgFile, File targetImgFile) {
		try {
			// 目标文件
			
			Image src = ImageIO.read(sourceImgFile);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			
			// 水印文件
			Image waterImg = ImageIO.read(waterImgFile);
			int width_target = waterImg.getWidth(null);
			int height_target = waterImg.getHeight(null);
			
//			if ((width -width_target> 100) && height - height_target > 100) { // 图片足够大才设置水印
			
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) image.getGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			
			//居中
//				g.drawImage(waterImg, (width - width_target) / 2, (height - height_target) / 2, width_target, height_target, null);
//				//距右下角
			g.drawImage(waterImg, width - width_target-10, height - height_target-10, width_target, height_target, null);
			
			// 水印文件结束
			g.dispose();
			//写出到目标文件
			ImageIO.write(image, "png", targetImgFile);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 给图片添加水印
	 * @param waterImgFile 水印图片地址或文件对象
	 * @param sourceImgFile 目标图片地址或文件对象
	 */
	public final static void imageWatermark(Object waterImgFile,Object sourceImgFile){
		File watermarkFile=null;
		File targetFile=null;
		
		if(waterImgFile instanceof File){
			watermarkFile=(File) waterImgFile;
		}else{
			watermarkFile=new File(waterImgFile.toString());
		}
		
		if(sourceImgFile instanceof File){
			targetFile=(File) sourceImgFile;
		}else{
			targetFile=new File(sourceImgFile.toString());
		}

		
		imageWatermark(watermarkFile, targetFile);
	}
	
	/**
	 * 给图片添加水印
	 * @param waterImgFile 水印图片地址或文件对象
	 * @param sourceImgFile 源图片地址或文件对象
	 * @param targetImgFile 目标图片地址或文件对象
	 */
	public final static void imageWatermark(Object waterImgFile,Object sourceImgFile,Object targetImgFile){
		File watermarkFile=null;
		File sourceFile=null;
		File targetFile=null;
		
		if(waterImgFile instanceof File){
			watermarkFile=(File) waterImgFile;
		}else{
			watermarkFile=new File(waterImgFile.toString());
		}
		
		if(sourceImgFile instanceof File){
			sourceFile=(File) sourceImgFile;
		}else{
			sourceFile=new File(sourceImgFile.toString());
		}
		
		if(targetImgFile instanceof File){
			targetFile=(File) targetImgFile;
		}else{
			targetFile=new File(targetImgFile.toString());
		}

		
		imageWatermark(watermarkFile, sourceFile,targetFile);
	}

	
	/**
	 * 在底部绘制文字水印
	 * @param sourceImgFile 图片文件对象
	 * @param content 水印内容
	 *            
	 */
	public final static  void textWatermark(File sourceImgFile, String content) {
		Image originalImg=null;
		try {
			originalImg = ImageIO.read(sourceImgFile);  //获取图像
			int width = originalImg.getWidth(null);  //获取宽度
			int height = originalImg.getHeight(null); //获取高度
			
			int fontSize=15; //水印文字大小
			//水印字符向左偏移量 = 字符长度*单个字符宽度(字体大小/1.9)
			double excursionWidth= content.length() *(fontSize/1.9);
			//水印字符向上偏移量 = 字体大小/2.8
			double excursionHeight= fontSize/2.8;
			
			// 图片足够大才设置水印
			if ((width - excursionWidth > 200) && (height - fontSize > 200)) { 
				//在内存中创建图片对象
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImg.createGraphics(); //创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中
				g.setColor(Color.BLACK);  //设置上下文颜色
				
				g.setFont(new Font("Arial", Font.PLAIN, fontSize)); //字体
				g.setColor(Color.RED);
				g.setBackground(Color.white);
				Font font=new Font("华文彩云", Font.PLAIN, fontSize);
				g.setFont(font);
				
				g.drawImage(originalImg, 0, 0, null); //绘制原始图片
				
				//在底部创建新图片
				BufferedImage bottom_image = new BufferedImage(width,(int)(fontSize+excursionHeight), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 =  bottom_image.createGraphics();
				//设置透明效果（0~1.0f之间，逐渐不透明）
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
				Color color=new Color(219, 219, 219);  //灰色
				g2.setColor(color); //设置上下文颜色
				g2.fillRect(0, 0, width, bottom_image.getHeight());  //填充颜色
				//将底部半透明图片绘制到原图
				g.drawImage(bottom_image, 0, (int)( height-bottom_image.getHeight()),width,bottom_image.getHeight(),null);
				
				
				int x=(int)(width - excursionWidth);  //x坐标，图片宽度-偏移宽度
				int y=(int)(height - excursionHeight);  //y坐标，图片高度-偏移高度
				
				
				g.drawString(content, x, y); // 添设置水印内容和位置
				g.dispose();//释放此图形的上下文以及它使用的所有系统资源。
				ImageIO.write(newImg, "png", sourceImgFile);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 在底部绘制文字水印
	 * @param sourceImgFile 源图片文件对象
	 * @param content 水印内容
	 * @param targetImgFile 目标图片文件对象
	 *            
	 */
	public final static  void textWatermark(File sourceImgFile, String content,File targetImgFile) {
		Image originalImg=null;
		try {
			originalImg = ImageIO.read(sourceImgFile);  //获取图像
			int width = originalImg.getWidth(null);  //获取宽度
			int height = originalImg.getHeight(null); //获取高度
			
			int fontSize=15; //水印文字大小
			//水印字符向左偏移量 = 字符长度*单个字符宽度(字体大小/1.9)
			double excursionWidth= content.length() *(fontSize/1.9);
			//水印字符向上偏移量 = 字体大小/2.8
			double excursionHeight= fontSize/2.8;
			
			// 图片足够大才设置水印
			if ((width - excursionWidth > 200) && (height - fontSize > 200)) { 
				//在内存中创建图片对象
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImg.createGraphics(); //创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中
				g.setColor(Color.BLACK);  //设置上下文颜色
				
				g.setFont(new Font("Arial", Font.PLAIN, fontSize)); //字体
				g.setColor(Color.RED);
				g.setBackground(Color.white);
				Font font=new Font("华文彩云", Font.PLAIN, fontSize);
				g.setFont(font);
				
				g.drawImage(originalImg, 0, 0, null); //绘制原始图片
				
				//在底部创建新图片
				BufferedImage bottom_image = new BufferedImage(width,(int)(fontSize+excursionHeight), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 =  bottom_image.createGraphics();
				//设置透明效果（0~1.0f之间，逐渐不透明）
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
				Color color=new Color(219, 219, 219);  //灰色
				g2.setColor(color); //设置上下文颜色
				g2.fillRect(0, 0, width, bottom_image.getHeight());  //填充颜色
				//将底部半透明图片绘制到原图
				g.drawImage(bottom_image, 0, (int)( height-bottom_image.getHeight()),width,bottom_image.getHeight(),null);
				
				
				int x=(int)(width - excursionWidth);  //x坐标，图片宽度-偏移宽度
				int y=(int)(height - excursionHeight);  //y坐标，图片高度-偏移高度
				
				
				g.drawString(content, x, y); // 添设置水印内容和位置
				g.dispose();//释放此图形的上下文以及它使用的所有系统资源。
				ImageIO.write(newImg, "png", targetImgFile);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 在底部绘制文字水印
	 * @param sourceImgFile 目标图片地址或文件对象
	 * @param content 水印内容
	 *            
	 */
	public final static  void textWatermark(Object sourceImgFile, String content) {
		File targetFile=null;
		
		if(sourceImgFile instanceof File){
			targetFile=(File) sourceImgFile;
		}else{
			targetFile=new File(sourceImgFile.toString());
		}

		textWatermark(targetFile, content);
	}
	
	/**
	 * 在底部绘制文字水印
	 * @param sourceImgFile 源图片地址或文件对象
	 * @param content 水印内容
	 * @param targetImgFile 目标图片地址或文件对象
	 *            
	 */
	public final static  void textWatermark(Object sourceImgFile, String content,Object targetImgFile) {
		File targetFile=null;
		File sourceFile=null;
		
		if(sourceImgFile instanceof File){
			targetFile=(File) sourceImgFile;
		}else{
			targetFile=new File(sourceImgFile.toString());
		}
		if(targetImgFile instanceof File){
			sourceFile=(File) targetImgFile;
		}else{
			sourceFile=new File(targetImgFile.toString());
		}
		
		textWatermark(sourceFile, content,targetFile);
	}
	
	

}
