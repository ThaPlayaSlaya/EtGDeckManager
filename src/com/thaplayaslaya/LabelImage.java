package com.thaplayaslaya;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
class LabelImage extends JLabel implements MouseListener {
	
	ImageIcon icon;
	ImageIcon full;
	ImageMagnifier im;

	public LabelImage(URL url) {
		super();
		try {
			BufferedImage temp = ImageIO.read(url);
			full = new ImageIcon(temp);
			temp = temp.getSubimage(4, 2, 278, 245);
			icon = new ImageIcon(temp.getScaledInstance(temp.getHeight()/4, temp.getWidth()/4, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}
	
	private void init() {
		addMouseListener(this);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setIcon(icon);
	}

	public void mouseClicked(MouseEvent event) {
	}

	public void mouseEntered(MouseEvent event) {
		im = new ImageMagnifier(full, DeckManager.getDeckManagerGUI());
	}

	public void mouseExited(MouseEvent event) {
		im.dispose();
	}

	public void mousePressed(MouseEvent event) {
	}

	public void mouseReleased(MouseEvent event) {
	}
}

@SuppressWarnings("serial")
class ImageMagnifier extends JFrame {
	ImageIcon full;

	public ImageMagnifier(ImageIcon imageFile, JFrame parent) {
		setUndecorated(true);
		full = imageFile;
		getContentPane().add(new JLabel(full));
		Point loc = parent.getLocation();
		loc.x = loc.x + (parent.getWidth()/2) - (full.getIconWidth()/2);
		loc.y = loc.y - (full.getIconHeight()/3);
		setLocation(loc);
		setSize(full.getIconWidth(), full.getIconHeight());
		setVisible(true);
	}

	
}