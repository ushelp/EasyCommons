package cn.easyproject.easycomms.imageutils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CompressionTest {

	public static void c1(BufferedImage image, int w, int h, String toFile)
			throws Exception {
		// 形变：某些图片形变后识别率可以提高
		BufferedImage imageChangeSize = null;

		// 新图片比例放大操作
		int type = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		imageChangeSize = new BufferedImage(w, h, type);
		Graphics2D g = imageChangeSize.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(image, 0, 0, w, h, null);
		g.dispose();
		image = imageChangeSize;
		ImageIO.write(image, "png", new File(toFile));

	}

	public static void c2(BufferedImage img, int w, int h, String toFile)
			throws Exception {

		BufferedImage tag = new BufferedImage((int) w, (int) h,
				BufferedImage.TYPE_INT_RGB);

		Image i = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		tag.getGraphics().drawImage(i, 0, 0, null);
		FileOutputStream out = new FileOutputStream(toFile); // 输出图像
		// JPEGImageEncoder可适用于其他图片类型的转换
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		/*
		 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		 */
	}

	public static void main(String[] args) throws Exception {

		BufferedImage img = ImageIO.read(new File("D:/ejb.png"));
		c1(img, 200, 300, "D:/a_c1.png");
		c2(img, 200, 300, "D:/a_c2.png");
	}
}
