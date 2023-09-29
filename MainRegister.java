import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.border.Border;

public class MainRegister extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	String user;
	JPanel mainPanel;
	JPanel dateShow;
	JScrollPane scroll;
	JButton[] button = new JButton[5];
	JButton[] marking;
	JPanel[] info;
	JLabel[] labels;
	String[] attendance;
	JButton[] txtChooser;
	String[] contents;
	JLabel dateLabel;
	int strength = 0;
	String[] names;
	int initial=10;
	
	
	long count=0;
	
	LocalDate date = LocalDate.now();
	boolean checking=false;
	String filename;
	
	
	void appendingText(String date) {
		BufferedWriter writer = null;
		try {
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data");
			if(!file.exists()) 
				file.mkdir();
				file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data\\"+date+".txt");
				file.delete();
				writer = new BufferedWriter(new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data\\"+date+".txt" , true));
				for(int i=0; i< strength; i++)	
					writer.append(attendance[i]+"\n");
			    } catch (Exception e) {}
		finally {
			try {
				writer.close();
			} catch (Exception e) {}
		}
		checking = false;
		mainPanel.remove(button[4]);
		button[0].setEnabled(true);
			}
		
		void create() {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Info.txt"));
				String line;
				while ((line = reader.readLine()) != null)
					strength++;
				names = new String[strength];
				marking = new JButton[strength];
				info = new JPanel[strength];
				labels = new JLabel[strength];
				attendance = new String[strength];
				txtChooser = new JButton[strength];
				contents = new String[strength];
				strength = 0;
				reader.close();
				reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Info.txt"));
				while ((line = reader.readLine()) != null) {
					names[strength]=line.substring(line.indexOf(" ")+3);
					strength++;
				}
				reader.close();
			} catch (IOException e1) {}
		}
		
		void getFiles() {
				File directoryPath = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data");
				contents = directoryPath.list();
				Stream<Path> files = null;
				try {
					files = Files.list(Paths.get("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				count = files.count();
				for(int i=0; i<count; i++) 
					contents[i] = contents[i].replaceAll(".txt", "");
		}
		
		void markMethod(String fname) {
			for(int i=0; i<3; i++)
				mainPanel.remove(button[i]);
			BufferedReader reader = null;
			create();
			String Label[] = new String[strength];
			
			File file = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data\\"+fname+".txt");
			int index=0;
			if(file.exists()) {
				try {
					reader = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data\\"+fname+".txt"));
					String line;
					while ((line = reader.readLine()) != null) {
						if(index<strength) {
							if(line.equalsIgnoreCase("p")) {
								Label[index]="Present";
								attendance[index] = "P";
							}
							else {
								Label[index]="Absent";
								attendance[index] = "A";
							}
							index++;
							}
					}
					reader.close();
				} catch (IOException e1) {}
					
				}
			else {
				Label[0] = "Absent";
				}
			
			Border eBorder1 = BorderFactory.createEtchedBorder();
			
			dateLabel = new JLabel("Date: "+fname);
			dateLabel.setFont(new Font("MV Boli", Font.PLAIN, 13));
			dateLabel.setBounds(65,7,145,15);
			
			dateShow = new JPanel(null);
			dateShow.setBounds(10, initial, 250, 30);
			dateShow.add(dateLabel);
			initial+=40;
			mainPanel.add(dateShow);
			dateShow.setBorder(eBorder1);
			
			for(int i=0; i<strength; i++) {
				if(index==0) {
					marking[i] = new JButton(Label[0]);
					attendance[i] = "A";
					marking[i].setBackground(Color.RED);
				}
				else {
					marking[i] = new JButton(Label[i]);
					if(Label[i].equals("Present")) 
						marking[i].setBackground(Color.GREEN);
					else
						marking[i].setBackground(Color.RED);
				}
				marking[i].setFont(new Font("MV Boli", Font.PLAIN, 13));
				marking[i].setFocusable(false);
				marking[i].setVisible(true);
				marking[i].setBounds(155,2,80,25);
				marking[i].addActionListener(this);
				
				labels[i] = new JLabel((i+1)+". "+names[i]);
				labels[i].setFont(new Font("MV Boli", Font.PLAIN, 13));
				labels[i].setBounds(5,7,145,15);
					
				info[i] = new JPanel(null);
				info[i].setBounds(10, initial, 250, 30);
				initial+=40;
				info[i].setBorder(eBorder1);
				info[i].add(labels[i]);
				info[i].add(marking[i]);
				mainPanel.add(info[i]);
				
			}
			
			if(checking) {
				button[3].setBounds(150, initial, 70, 30);
				mainPanel.add(button[3]);
				button[4].setBounds(50, initial, 70, 30);
				mainPanel.add(button[4]);
				for(int i=0; i<strength; i++) 
					marking[i].setEnabled(false);
			}
			else {
				button[3].setBounds(100, initial, 70, 30);
				mainPanel.add(button[3]);
			}
			
			initial+=40;
			
			if(initial>360)
				mainPanel.setPreferredSize(new Dimension(260,initial));
			super.paint(getGraphics());
			initial=10;
			
		}
	
	
	MainRegister(String user) {
		this.user = user;

		mainPanel = new JPanel(null);
		mainPanel.setBounds(0, 0, 300, 420);
		mainPanel.setPreferredSize(new Dimension(280,360));
		
		scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVisible(true);
		
		String buttonLabels[] = {"Check/Edit","Mark" , "ATTENDANCE REGISTER", "Done", "Edit"};
		for(int i=0; i<=4; i++) {
				button[i] = new JButton(buttonLabels[i]);
				button[i].setBackground(new Color(123,100,255));
				button[i].setFont(new Font("MV Boli", Font.PLAIN, 15));
				button[i].setFocusable(false);
				button[i].setVisible(true);
				button[i].addActionListener(this);
				if(i<3)
					mainPanel.add(button[i]);
		}
		
		button[0].setBounds(15, 200, 120, 25);
		button[1].setBounds(145, 200, 120, 25);
		button[2].setBackground(Color.WHITE);
		button[2].setForeground(Color.RED);
		button[2].setBounds(20, 50, 240, 25);
		
		//FRAME
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(300,420));
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("Attendance Marker");
		this.getContentPane().add(scroll);
		this.setVisible(true);
		
		File directoryPath = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Attendance\\Class\\"+user+"\\Data");
		if(directoryPath.exists())
			getFiles();
		if(count==0) 
			button[0].setEnabled(false);
		
		create();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==button[2]) {
			JOptionPane.showMessageDialog(null, "This software is created by Ayush\n\nV0.5 (Beta)", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource()==button[1]) {
			if(checking) 
				markMethod(filename);
			else 
				markMethod(date+"");
		}
		
		for(int i=0; i<strength; i++) {
			if(e.getSource()==marking[i]) {
				if(marking[i].getText().equals("Absent")) {
					marking[i].setText("Present");
					attendance[i] = "P";
					marking[i].setBackground(Color.green);
				}
				else {
					marking[i].setText("Absent");
					attendance[i] = "A";
					marking[i].setBackground(Color.RED);
				}
			}
		}
		
		if(e.getSource()==button[0]) {
			for(int i=0; i<3; i++)
					mainPanel.remove(button[i]);
			getFiles();
			for(int i=0; i<count; i++) {
				txtChooser[i] = new JButton(contents[i]);
				txtChooser[i].setBackground(new Color(123,100,255));
				txtChooser[i].setFont(new Font("MV Boli", Font.PLAIN, 13));
				txtChooser[i].setFocusable(false);
				txtChooser[i].setVisible(true);
				txtChooser[i].setBounds(20,initial,230,30);
				txtChooser[i].addActionListener(this);
				initial+=40;
				mainPanel.add(txtChooser[i]);
			}
			if(initial>360) {
				mainPanel.setPreferredSize(new Dimension(260,initial));
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			}
			super.paint(getGraphics());
			
		}
		
		for(int i=0; i<count; i++) {
			if(e.getSource()==txtChooser[i]) {
				initial=10;
				for(int j=0; j<count; j++) {
					mainPanel.remove(txtChooser[j]);
				}
				checking = true;
				filename = contents[i];
				markMethod(contents[i]);
			}
		}
		
		if(e.getSource()==button[3]) {
			if(checking)
				appendingText(filename);
			else
				appendingText(date+"");
			for(int i=0; i<strength; i++) {
				mainPanel.remove(info[i]);
			}
			mainPanel.remove(dateShow);
			for(int i=0; i<3; i++)
				mainPanel.add(button[i]);
			mainPanel.setPreferredSize(new Dimension(280,360));
			initial=10;
			strength=0;
			mainPanel.remove(button[3]);
			for(int i=0; i<strength; i++) 
				attendance[i] = "A";
			super.paintComponents(getGraphics());
		}
		
		if(e.getSource()==button[4]) {
			for(int i=0; i<strength; i++) 
				marking[i].setEnabled(true);
		}
		
	}
	
	}

