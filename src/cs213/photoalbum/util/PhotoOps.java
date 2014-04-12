package cs213.photoalbum.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PhotoOps {
	public static ImageIcon createIcon(String img_path, int width, int height)
			throws IOException, Exception {
		BufferedImage img = ImageIO.read(new File(img_path));
		ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height,
				Image.SCALE_DEFAULT));
		return icon;
	}
	
	public static ImageIcon resizeIcon(Icon img_icon, int width, int height) throws IOException, Exception{
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		img_icon.paintIcon(null, g, 0, 0);
		g.dispose();
		return new ImageIcon(img);
	}
}
