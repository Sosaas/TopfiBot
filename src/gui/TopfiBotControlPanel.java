/* Copyright 2018 Jonas Wischnewski

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

import javax.security.auth.login.LoginException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import botcore.*;

import net.dv8tion.jda.core.JDA;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class TopfiBotControlPanel extends JFrame {
    
    private static final long serialVersionUID = 7244673752238006104L;
    
    private JPanel contentPane;
    private LocalDateTime displayShutdownTime;

    public TopfiBotControlPanel() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	setTitle("TopfiBotControl");
	
	UpStatPanel panelStat = new UpStatPanel();
	panelStat.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
		    try {
			if (panelStat.getOnline() != 0) {
			    Mainhub.getAPI().shutdown();
			    panelStat.setOnline((short) 0);
			} else {
			    panelStat.setOnline((short) 1);
			    try {
				Mainhub.launch();
			    } catch (LoginException logEx) {
				panelStat.setOnline((short) 2);
			    }
			}
		    } catch (Exception ex) {
			ex.printStackTrace();
		    }
		}
	});
	contentPane.setLayout(new MigLayout("", "[grow]", "[75px:n,grow][growprio 102,grow]"));
	contentPane.add(panelStat, "cell 0 0,grow");
	
	JPanel panelInfo = new JPanel();
	contentPane.add(panelInfo, "cell 0 1,grow");
	panelInfo.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow][grow][grow]"));
	
	JLabel lblOnlineSeit = new JLabel("Online seit:");
	panelInfo.add(lblOnlineSeit, "cell 0 0");
	
	JLabel lblOnlineSeitTime = new JLabel("-");
	panelInfo.add(lblOnlineSeitTime, "cell 1 0");
	
	JLabel lblVerbundeneGuilds = new JLabel("Verbundene Guilds:");
	panelInfo.add(lblVerbundeneGuilds, "cell 0 1");
	
	JLabel lblVerbundeneGuidsAnz = new JLabel("-");
	panelInfo.add(lblVerbundeneGuidsAnz, "cell 1 1");
	
	JLabel lblGeplanterShutdown = new JLabel("Geplanter Shutdown:");
	panelInfo.add(lblGeplanterShutdown, "cell 0 2");
	
	JLabel lblGeplanterShutdownTime = new JLabel("-");
	panelInfo.add(lblGeplanterShutdownTime, "cell 1 2");
	
	JLabel lblGeplanterNeustart = new JLabel("Geplanter Neustart:");
	panelInfo.add(lblGeplanterNeustart, "cell 0 3");
	
	JLabel lblGeplanterNeustartTime = new JLabel("-");
	panelInfo.add(lblGeplanterNeustartTime, "cell 1 3");
	
	if (System.console() != null) {
	    
	}
    }
    public synchronized void setDisplayShutdownTime(LocalDateTime ldt) {
	displayShutdownTime = ldt;
	this.repaint();
    }
    public synchronized void setDisplayStatus(short on) {
	UpStatPanel statPanel = (UpStatPanel) contentPane.getComponent(0);
	statPanel.setOnline(on);
    }
}


