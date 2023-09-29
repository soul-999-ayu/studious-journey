import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class classCheck extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	boolean CheckClass(String user) {
		if(!(new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Info.txt").exists())) {
			CreateFile(user);
			return false;
		}
		return true;
	}
	
	void CreateFile(String user) {
		File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\");
		if(!file.exists()) 
			file.mkdir();
		file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user);
		file.mkdir();
	}
	
	void SaveFile(String user) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Info.txt" , true));
			for(int i=0; i<students; i++)
				writer.append((i+1)+" : "+nameArray[i]+"\n");
		    } catch (Exception e) {}
		finally {
			try {
				writer.close();
			} catch (Exception e) {}
		}
		new MainRegister(username);
	}
	
	String username;
	JLabel lab[] = new JLabel[4];
	JButton button[] = new JButton[3];
	JTextField name;
	boolean num;
	String[] nameArray;
	int index;
	int students;
	
	classCheck(String usr){
		
		username = usr;
		
		//USERNAME FIELD
		name = new JTextField();
		name.setFont(new Font("MV Boli", Font.PLAIN, 15));
		
		String labels[] = {"How many students are there?","Roll No." , "1.", "Name:"};
		String buttonLabels[] = {"Back", "Next", "Finish"};
		for(int i=0; i<4; i++) {
			lab[i] = new JLabel(labels[i]);
			lab[i].setFont(new Font("MV Boli", Font.PLAIN, 15));
			lab[i].setForeground(Color.black);
			lab[i].setVisible(true);
			this.add(lab[i]);
			if(i<3) {
				button[i] = new JButton(buttonLabels[i]);
				button[i].setBackground(new Color(123,100,255));
				button[i].setFont(new Font("MV Boli", Font.PLAIN, 15));
				button[i].setFocusable(false);
				button[i].setVisible(true);
				button[i].addActionListener(this);
				this.add(button[i]);
			}
			
		}
		
		lab[0].setBounds(30, 30, 280, 25);
		
		name.setBounds(30, 65, 50, 25);
		
		button[0].setEnabled(false);
		button[2].setEnabled(false);
		button[0].setBounds(20, 200, 70, 25);
		button[1].setBounds(200, 200, 70, 25);
		button[2].setBounds(100, 200, 90, 25);
		
		lab[1].setBounds(40, 65, 80, 25);
		lab[1].setVisible(false);
		lab[2].setVisible(false);
		lab[3].setVisible(false);
		lab[3].setBounds(140, 65, 80, 25);
		
		//FRAME
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(new Dimension(300,420));
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("Attendance Register");
		this.add(name);
		this.setVisible(true);
		
		if(CheckClass(username)) {
			this.dispose();
			new MainRegister(username);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource()==button[1]) {
				if(lab[0].getText().equals("How many students are there?")) {
					try {
						Integer.parseInt(name.getText());
						num=true;
					}
					catch(Exception e1) {
						num=false;
					}
					
					if(num && Integer.parseInt(name.getText())!=0) {
						students = Integer.parseInt(name.getText());
						nameArray = new String[students];
						index = 0;
						name.setText("");
						name.setBounds(140, 100, 80, 20);
						lab[0].setText("Enter Details:");
						lab[0].setBounds(90, 30, 280, 25);
						lab[1].setVisible(true);
						lab[2].setVisible(true);
						lab[3].setVisible(true);
						lab[2].setText(index+1+".");
						lab[2].setBounds(40, 100, 80, 25);
						lab[2].setForeground(Color.black);
						button[0].setEnabled(true);
					}
					else {
						if(num)
							lab[2].setText("Please enter a valid number!");
						else
							lab[2].setText("Please enter a number!");
						lab[2].setVisible(true);
						lab[2].setForeground(Color.red);
						lab[2].setBounds(30, 10, 200, 20);
					}
				}
				else {
					if(!name.getText().equals("") && index<(students-1)) {
						nameArray[index]=name.getText();
						index++;
						name.setText(nameArray[index]);
						lab[2].setText(index+1+".");
					}
					if(index==(students-1)) {
						button[2].setEnabled(true);
						button[1].setEnabled(false);
					}
				}
			}
			
			if(e.getSource()==button[0]) {
				if(index==0) {
					lab[0].setText("How many students are there?");
					lab[0].setBounds(30, 30, 280, 25);
					
					name.setBounds(30, 65, 50, 25);
				
					button[0].setEnabled(false);
					button[0].setBounds(20, 200, 70, 25);
					button[1].setBounds(200, 200, 70, 25);
					button[2].setBounds(100, 200, 90, 25);
					
					lab[1].setVisible(false);
					lab[2].setVisible(false);
					lab[3].setVisible(false);
					
				}
				else if(index==(students-1)) {
					nameArray[index]=name.getText();
					index--;
					name.setText(nameArray[index]);
					lab[2].setText(index+1+".");
				}
				else {
					index--;
					name.setText(nameArray[index]);
					lab[2].setText(index+1+".");
				}
				button[1].setEnabled(true);
			}
			
			if(e.getSource()==button[2]) {
				if(!name.getText().equals("")) {
					nameArray[index]=name.getText();
					SaveFile(username);
					this.dispose();
				}
			}
	}
}
