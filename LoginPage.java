import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JLabel lab[] = new JLabel[4];
	JTextField uname;
	JPasswordField pword;
	JButton but[] = new JButton[3];
	String n,p;
	
	//TEXT ENCRYPTION LOGIC
	String encrypt(String text) {
		for(int i=0; i<text.length(); i++) {
			char newChar = text.charAt(i);
			newChar+=i;
			text = text.replace(text.charAt(i), newChar);
		}
		return text;
	}
	
	//TEXT DECRYPTION LOGIC
	String decrypt(String text) {
		for(int i=0; i<text.length(); i++) {
			char newChar = text.charAt(i);
			newChar-=i;
			text = text.replace(text.charAt(i), newChar);
		}
		return text;
	}
	
	//STORING CREDENTIALS IN A FILE
	void appendingText() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Login\\Credentials.txt" , true)); 
			writer.append("\n"+encrypt(String.valueOf(uname.getText())));
			writer.append(" : ");
			writer.append(encrypt(String.valueOf(pword.getPassword())));
		    	} catch (Exception e) {}
				finally {
		    		try {
		    			writer.close();
		    		} catch (Exception e) {}
		    	}
	}
	
	LoginPage(){
		//CREATION OF LABELS
		String labels[] = {"LOGIN PAGE","", "Username:", "Password:"};
		String buttons[] = {"Login", "Sign Up", "Theme: Light"};
		for(int i=0; i<4; i++) {
			lab[i] = new JLabel(labels[i]);
			lab[i].setFont(new Font("MV Boli", Font.PLAIN, 15));
			lab[i].setForeground(Color.RED);
			lab[i].setVisible(true);
			this.add(lab[i]);
			if(i<3) {
				but[i] = new JButton(buttons[i]);
				but[i].setBackground(new Color(123,100,255));
				but[i].setFont(new Font("MV Boli", Font.PLAIN, 15));
				but[i].setFocusable(false);
				but[i].setVisible(true);
				but[i].addActionListener(this);
				this.add(but[i]);
			}
		}
		
		//SETTING POSITIONS
		lab[0].setBounds(100, -30, 150, 100);
		lab[0].setFont(new Font("MV Boli", Font.PLAIN, 20));
		lab[1].setBounds(50, 10, 150, 100);
		lab[1].setFont(new Font("MV Boli", Font.PLAIN, 12));
		lab[2].setBounds(50, 65, 150, 40);
		lab[3].setBounds(50, 145, 150, 40);
		but[0].setBounds(65, 240, 100, 30);
		but[1].setBounds(185, 240, 100, 30);
		but[2].setBounds(100, 290, 140, 30);
		
		//USERNAME FIELD
		uname = new JTextField();
		uname.setBounds(50, 100, 250, 35);
		uname.setFont(new Font("MV Boli", Font.PLAIN, 15));
		
		//PASSWORD FIELD
		pword = new JPasswordField();
		pword.setBounds(50, 180, 250, 35);
		pword.setFont(new Font("MV Boli", Font.PLAIN, 15));
		
		//FRAME
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(new Dimension(350,400));
		this.getContentPane().setBackground(Color.WHITE);
		this.add(uname);
		this.add(pword);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//THEME BUTTON
		if(e.getSource()==but[2]) {
			if(this.getContentPane().getBackground()==Color.WHITE) {
				this.getContentPane().setBackground(Color.BLACK);
				lab[2].setForeground(Color.WHITE);
				lab[3].setForeground(Color.WHITE);
				but[2].setText("Theme: Dark");
			}
			else{
				this.getContentPane().setBackground(Color.WHITE);
				lab[2].setForeground(Color.BLACK);
				lab[3].setForeground(Color.BLACK);
				but[2].setText("Theme: Light");
			}
		}
		
		//LOGIN BUTTON (READING CREDENTIAL FILES)
		if(e.getSource()==but[0]) {
			try{
			    	BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Login\\Credentials.txt"));
			    	String line;
			    	while ((line = reader.readLine()) != null)
			    	{
			    		if(!line.equals("")){
			    			n = decrypt(line.substring(0,line.indexOf(" ")));
			    			p = decrypt(line.substring(line.indexOf(" ")+3));
			    			if(String.valueOf(uname.getText()).equals("")||String.valueOf(pword.getPassword()).equals("")) {
			    				lab[1].setForeground(Color.red);
								lab[1].setText("Enter Valid Credentials!");
							}
			    			else if(n.equals(uname.getText())) {
								if(p.equals(String.valueOf(pword.getPassword()))) {
									new classCheck(n);
									this.dispose();
								}
								else {
									lab[1].setForeground(Color.red);
									lab[1].setText("Wrong Password!");
								}
							}
							else {
								lab[1].setForeground(Color.red);
								lab[1].setText("User Doesn't Exist!");
							}
			    		}
			    	}
			    	reader.close();
			  	}
			catch (Exception e1)
			{}
		}
		
		//SIGN UP BUTTON
		if(e.getSource()==but[1]) {
			if(String.valueOf(uname.getText()).equals("")||String.valueOf(pword.getPassword()).equals("")) {
				lab[1].setForeground(Color.red);
				lab[1].setText("Enter Valid Credentials!");
			}
			else {
				File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance");
				if(!file.exists()) 
					file.mkdir();
				file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Login");
				if(!file.exists()) 
					file.mkdir();
				appendingText();
				lab[1].setForeground(Color.GREEN);
				lab[1].setText("Registration Done!");
				uname.setText("");
				pword.setText("");
			}
		}
	}
}
