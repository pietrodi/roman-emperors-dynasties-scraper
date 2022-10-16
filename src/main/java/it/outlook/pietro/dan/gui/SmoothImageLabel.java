package it.outlook.pietro.dan.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * An improvement in image visualization quality over the standard
 * {@code JLabel}.
 * 
 * @author Pietro Danieli
 *
 */
public class SmoothImageLabel extends JLabel {
	    public SmoothImageLabel(ImageIcon image) {
	    	super(image);
	    }
	    
	    @Override
	    public void paintComponent(Graphics g) {
	    	Graphics2D g2d = (Graphics2D) g;
	    	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        super.paintComponent(g2d);
	    }
	}