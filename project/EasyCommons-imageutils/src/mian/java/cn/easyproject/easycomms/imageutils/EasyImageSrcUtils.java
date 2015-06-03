package cn.easyproject.easycomms.imageutils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * EasyCommons 项目下的图片处理工具类 <br/>
 * EasyImageSrcUtil：图片提取工具类。将字符串内容中的图片路径提取出来。 <br/>
 * 适合场景：在发表新闻内容时，自动提取内容中所有的图片，或提取第一张图片作为新闻封面。 <br/>
 * 
 * @author easyproject.cn
 * 
 */
@SuppressWarnings("rawtypes")
public class EasyImageSrcUtils {

	
	
	/**
	 * 提取第一幅图片的src属性值
	 * @param content
	 * @return 压缩是否成功
	 */
	public static String findFirstSrc(String content){
		String src="";
		String regImg="<[img|IMG][^>]*>";
		String regSrc="src=\"?[^\\s]*(\\s|>|\"?)";
		Pattern imgPatter=Pattern.compile(regImg);
		Pattern srcPatter=Pattern.compile(regSrc);
		
		Matcher imgMatcher=imgPatter.matcher(content);
		
		boolean imgExists=imgMatcher.find();
		if(imgExists){
			String img=imgMatcher.group();
			Matcher srcMatcher=srcPatter.matcher(img);
			
			if(srcMatcher.find()){
				src=srcMatcher.group().replaceAll("src=", "").replaceAll("\"", "").replaceAll(" ","");
			}
		}
		return src;
	}
	
	/**
	 * 提取所有图片对象的src属性值，包含http://开头的网络图片
	 * @param content
	 * @return 压缩是否成功
	 */
	public static List findAllSrc(String content){
		List<String> list=new ArrayList<String>();
		String regImg="<[img|IMG][^>]*>";
		String regSrc="src=\"?[^\\s]*(\\s|>|\"?)";
		Pattern imgPatter=Pattern.compile(regImg);
		Pattern srcPatter=Pattern.compile(regSrc);
		
		Matcher imgMatcher=imgPatter.matcher(content);
		while(imgMatcher.find()){
			String img=imgMatcher.group();
			Matcher srcMatcher=srcPatter.matcher(img);
			if(srcMatcher.find()){
				list.add(srcMatcher.group().replaceAll("src=", "").replaceAll("\"", "").replaceAll(" ",""));
			}
		}
		return list;
	}

	/**
	 * 提取所有图片对象的src属性值，但不包含http://开头的网络图片
	 * @param content
	 * @return 压缩是否成功
	 */
	public static List findAllSrcNoNet(String content){
		List<String> list=new ArrayList<String>();
		String regImg="<[img|IMG][^>]*>";
		String regSrc="src=\"?[^\\s]*(\\s|>|\"?)";
		Pattern imgPatter=Pattern.compile(regImg);
		Pattern srcPatter=Pattern.compile(regSrc);
		
		StringBuffer src=new StringBuffer();
		Matcher imgMatcher=imgPatter.matcher(content);
		while(imgMatcher.find()){
			String img=imgMatcher.group();
			Matcher srcMatcher=srcPatter.matcher(img);
			if(srcMatcher.find()){
			
				src.delete(0, src.length());
				src.append(srcMatcher.group().replaceAll("src=", "").replaceAll("\"", "").replaceAll(" ",""));
				if(!src.substring(0, 7).equals("http://")){
					list.add(src.toString());
				}
			}
		}
		return list;
	}
}
