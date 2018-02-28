package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class UpStatPanel extends JPanel {

    private static final long serialVersionUID = 598071471909685213L;
    private short online;

    UpStatPanel() {
	super();
	online = 0;
    }
    
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	switch (online) {
	    case 0 : 
	    g.setColor(Color.GRAY);
	    g.fillOval(0, 0, this.getWidth() / 6 , this.getHeight());
	    g.setColor(Color.BLACK);
	    g.setFont(new Font("Arial", Font.BOLD, 30));
	    g.drawString("Offline", this.getWidth() / 6 * 2, this.getHeight() / 2 + 15);
	    break;
	    case 1 : 
	    g.setColor(Color.GREEN);
	    g.fillOval(0, 0, this.getWidth() / 6 , this.getHeight());
	    g.setColor(Color.BLACK);
	    g.setFont(new Font("Arial", Font.BOLD, 30));
	    g.drawString("Online", this.getWidth() / 6 * 2, this.getHeight() / 2 + 15);
	    break;
	    case 2 :
	    g.setColor(Color.RED);
	    g.fillOval(0, 0, this.getWidth() / 6, this.getHeight());
	    g.setColor(Color.BLACK);
	    g.setFont(new Font("Arial", Font.BOLD, 30));
	    g.drawString("Falscher Token!", this.getWidth() / 6 * 2, this.getHeight());
	    break;
	}
    }
    
    public void setOnline(short on) {
	this.online = on;
	this.paintComponent(this.getGraphics());
    }
    public short getOnline() {
	return online;
    }
}
