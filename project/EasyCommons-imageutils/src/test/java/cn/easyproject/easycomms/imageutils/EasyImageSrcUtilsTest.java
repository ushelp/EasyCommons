package cn.easyproject.easycomms.imageutils;

import java.util.List;
/**
 * 图片提取工具类，
 * @author easyproject.cn
 *
 */

/**
 * EasyCommons 项目下的图片处理工具类 <br/>
 * 图片提取工具类（EasyImageSrcUtil）: 将字符串内容中的图片路径提取出来。 <br/>
 * 适合场景：在发表新闻内容时，自动提取内容中所有的图片，或提取第一张图片作为新闻封面。 <br/>
 * 
 * @author easyproject.cn
 * 
 */
public class EasyImageSrcUtilsTest {

	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String content="<p><a href=\"/userfiles/images/2(1).jpg\"><img height=\"466\" src=\"http://image.com/zximages/images/201212/b7d5382858e4b77262a0fb819659f926.jpg\" width=\"700\" /><img alt=\"\" src=\"/userfiles/images/2(1).jpg\" /></a></p><p>&nbsp;</p><img alt=\"\" src=\"/userfiles/images/2(1).jpg\" /><p>&nbsp;&nbsp;&nbsp;&nbsp; 玄关与餐厅之间用一款铁艺的玻璃屏风做出隔断。餐厅的天花板用线板拉高了层次，壁面则以仿布板壁纸和画框境搭配，华丽的吊灯反射出放大空间的<img alt=\"\" src=\"/userfiles/images/2(1).jpg\" />效果<img height=\"466\" src=\"http://image.meilele.com/zximages/images/201212/b7d5382858e4b77262a0fb819659f926.jpg\" width=\"700\" />。</p>";
		List<String> list=EasyImageSrcUtils.findAllSrcNoNet(content);
		for(String s: list){
			System.out.println(s+"@");
		}
	}
}
