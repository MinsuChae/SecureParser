import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Secure2Iptables {
	public static void main(String[] args){
		JFileChooser jFileChooser=new JFileChooser();
		BufferedReader fread;
		String buffer;
		StringBuilder sb=new StringBuilder();
		while(jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			try {
				fread=new BufferedReader(new FileReader(jFileChooser.getSelectedFile()));
				
				while((buffer=fread.readLine())!=null){
					if(buffer.length()==0)
						continue;
					else if(buffer.equals('\n'))
						continue;
					sb.append(buffer).append('\n');
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		SecureIpParser parser=new SecureIpParser(sb.toString());
		String[] blacklist=parser.getBlacklist();
		String[] whitelist=parser.getWhitelist();
		
		JPanel left=new JPanel(new BorderLayout());
		JLabel leftTitle=new JLabel("Blacklist");
		leftTitle.setOpaque(true);
		leftTitle.setBackground(Color.WHITE);
		JTextArea leftText=new JTextArea(20, 30);
		left.add(leftTitle,BorderLayout.NORTH);
		left.add(new JScrollPane(leftText),BorderLayout.CENTER);
		
		String tmp=null;
		sb.setLength(0);
		for(String s : blacklist){
			sb.append("-A INPUT -p tcp -m tcp -s ").append(s).append(" -j DROP\n");
		}
		tmp=sb.toString();
		leftText.setText(tmp);
		
		JPanel right=new JPanel(new BorderLayout());
		JLabel rightTitle=new JLabel("Whitelist");
		rightTitle.setOpaque(true);
		rightTitle.setBackground(Color.WHITE);
		JTextArea rightText=new JTextArea(20, 30);
		right.add(rightTitle,BorderLayout.NORTH);
		right.add(new JScrollPane(rightText),BorderLayout.CENTER);
		
		sb.setLength(0);
		for(String s : whitelist){
			sb.append(s).append('\n');
		}
		tmp=sb.toString();
		rightText.setText(tmp);
		JFrame jframe = new JFrame("Secure 2 Iptables");
		jframe.add(left,BorderLayout.WEST);
		jframe.add(right,BorderLayout.EAST);
		jframe.pack();
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
	}
}
