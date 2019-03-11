
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


//used to get the date and symbol for retrieving data
public class MarketFrame extends JFrame implements ActionListener,ItemListener{

	private JButton retrieve;
	private JLabel company,label_start_year,label_start_month,label_start_day,label_end_year,label_end_month,label_end_day,empty,startl,endl;
	private JComboBox mSymbol,mSYear,mSMonth,mSDay,mEYear,mEMonth,mEDay;
	private String symbol,start,end,starty,startm,startd,endy,endm,endd,site; 
	private JPanel p ;
	
	public MarketFrame() {    
		setTitle("Market GUI");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//set the screen size and location
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setBounds(0,0, (int) width/2, (int) height/2);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		} );
		
		Container contentPane = getContentPane();//panel to choose company and date
		
		p = new JPanel(new GridLayout(8,3));//8x3 layout
		company = new JLabel("Ticker Symbol: ");
		p.add(company);
		empty = new JLabel(" ");//empty 
		p.add(empty);
		empty = new JLabel(" ");
		p.add(empty);
		mSymbol = addSymbolJComboBox(p,this);
		p.add(mSymbol);
		empty = new JLabel(" ");
		p.add(empty);
		empty = new JLabel(" ");
		p.add(empty);
		startl = new JLabel("Start Date: ");
		p.add(startl);
		empty = new JLabel(" ");
		p.add(empty);
		empty = new JLabel(" ");
		p.add(empty);
		label_start_year = new JLabel("Year:");
		p.add(label_start_year);	
		label_start_month = new JLabel("Month:");
		p.add(label_start_month);
		label_start_day = new JLabel("Day:");
		p.add(label_start_day);
		mSYear = addYearJComboBox(p,this);
		mSMonth = addMonthJComboBox(p,this);
		mSDay = addDayJComboBox(p,this);	
		endl = new JLabel("End Date: ");
		p.add(endl);
		empty = new JLabel(" ");
		p.add(empty);
		empty = new JLabel(" ");
		p.add(empty);
		label_end_year = new JLabel("Year:");
		p.add(label_end_year);		
		label_end_month = new JLabel("Month:");
		p.add(label_end_month);		
		label_end_day = new JLabel("Day:");
		p.add(label_end_day);
		mEYear = addYearJComboBox(p,this);	
		mEMonth = addMonthJComboBox(p,this);	
		mEDay = addDayJComboBox(p,this);
		contentPane.add(p,"North");	
		p = new JPanel();
		retrieve = addJButton(p, "retrieve", this);
		contentPane.add(p);
	}
//connect to actionListener
	private JButton addJButton(JPanel p, String s, ActionListener a) {
		JButton b = new JButton(s);
		b.addActionListener(a);
		p.add(b);
		return b;
	}
//comboBox
	private JComboBox addDayJComboBox(JPanel p, ActionListener a) {
		String mList[] = new String[31];
		for(int i=0;i<31;i++)
			mList[i] = Integer.toString(i+1);
		JComboBox c = new JComboBox(mList);
		c.addActionListener(a);
		c.addItemListener(this);
		p.add(c);
		return c;
	}
//year and month need itemListener  to change the day item
	private JComboBox addMonthJComboBox(JPanel p, ActionListener a) {
		String mList[] = new String[12];
		for(int i=0;i<12;i++)
			mList[i] = Integer.toString(i+1);
		JComboBox c = new JComboBox(mList);
		c.addActionListener(a);
		c.addItemListener(this);
		p.add(c);
		return c;
	}
	private JComboBox addYearJComboBox(JPanel p, ActionListener a) {
		String mList[] = new String[31];
		for(int i=31;i>0;i--)
			mList[31-i] = Integer.toString(i+1988);
		JComboBox c = new JComboBox(mList);	
		c.addActionListener(a);
		c.addItemListener(this);
		p.add(c);
		return c;
	}
	private JComboBox addSymbolJComboBox(JPanel p, ActionListener a) {
		String mList[] = new String[2];
		mList[0] = "AAPL";
		mList[1] = "MSFT";
		JComboBox c = new JComboBox(mList);
		c.addActionListener(a);
		p.add(c);
		return c;
	}
//actions
	public void itemStateChanged(ItemEvent event) {
		starty = (String)mSYear.getSelectedItem();
		startm = (String)mSMonth.getSelectedItem();
		if (event.getStateChange() == ItemEvent.SELECTED) {
			if(is31Day(Integer.parseInt(startm))) {//31
				mSDay.removeItemListener(this);
				mSDay.removeAllItems();
				for(int i=0;i<31;i++) {
					mSDay.addItem(String.valueOf(i+1));
				}
			}
		else {
		if(startm.equals("2")){
			if(isLeapYear(Integer.parseInt(starty))){//29
				mSDay.removeItemListener(this);
				mSDay.removeAllItems();
				for(int i=0;i<29;i++) {
					mSDay.addItem(String.valueOf(i+1));
				}
				}
			else {//28
				mSDay.removeItemListener(this);
				mSDay.removeAllItems();
				for(int i=0;i<28;i++) {
					mSDay.addItem(String.valueOf(i+1));
				}
			}
		}
		else {//30
			mSDay.removeItemListener(this);
			mSDay.removeAllItems();
			for(int i=0;i<30;i++) {
				mSDay.addItem(String.valueOf(i+1));
			}
		}
		}
        }
	
			endy = (String)mEYear.getSelectedItem();
			endm = (String)mEMonth.getSelectedItem();
			if (event.getStateChange() == ItemEvent.SELECTED) {
				if(is31Day(Integer.parseInt(endm))) {//31
					mEDay.removeItemListener(this);
					mEDay.removeAllItems();
					for(int i=0;i<31;i++) {
						mEDay.addItem(String.valueOf(i+1));
					}
				}
			else {
			if(endm.equals("2")){
				if(isLeapYear(Integer.parseInt(endy))){//29
					mEDay.removeItemListener(this);
					mEDay.removeAllItems();
					for(int i=0;i<29;i++) {
						mEDay.addItem(String.valueOf(i+1));
					}
					}
				else {//28
					mEDay.removeItemListener(this);
					mEDay.removeAllItems();
					for(int i=0;i<28;i++) {
						mEDay.addItem(String.valueOf(i+1));
					}
				}
			}
			else {//30
				mEDay.removeItemListener(this);
				mEDay.removeAllItems();
				for(int i=0;i<30;i++) {
					mEDay.addItem(String.valueOf(i+1));
				}
			}
			}
	        
			
	    }
    }
//retrieve action
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == retrieve) {
			symbol = (String) mSymbol.getSelectedItem();
			starty = (String) mSYear.getSelectedItem();
			mSYear.addItem("123");
			startm = (String) mSMonth.getSelectedItem();
			startd = (String) mSDay.getSelectedItem();
			endy = (String) mEYear.getSelectedItem();
			endm = (String) mEMonth.getSelectedItem();
			endd = (String) mEDay.getSelectedItem();
			start = startm + "/" + startd + "/" + starty;
			end = endm + "/" + endd + "/" + endy;
			site = "https://quotes.wsj.com/"
					+ symbol
					+ "/historical-prices/download?MOD_VIEW=page&num_rows=300&startDate="
					+ start
					+ "&endDate="
					+ end;
		getCSV(site);
		List<String[]> content = new ArrayList<>();
		
		if(Integer.parseInt(endy) < Integer.parseInt(starty)) {
			System.out.println("ff1" + " year wrong");//year wrong
			JOptionPane.showMessageDialog(null, "Wrong Year!!!!!");
		}
		else if(Integer.parseInt(endy) > Integer.parseInt(starty)){ 
			System.out.println("yy");//year right
			content = readData();
			if(content.size()>0) {
			popFrame(content,symbol);}
			else
				JOptionPane.showMessageDialog(null, "No data found");
		}
		else {//year = year
			if(Integer.parseInt(endm) < Integer.parseInt(startm)) {//month wrong
				JOptionPane.showMessageDialog(null, "Wrong Month!!!!!");
				}
				else if(Integer.parseInt(endm) > Integer.parseInt(startm)){
					System.out.println("yy");//month right	
						content = readData();
						if(content.size()>0) {
						popFrame(content,symbol);}
						else
							JOptionPane.showMessageDialog(null, "No data found");
				}
				else {//month = month
					if(Integer.parseInt(endd) < Integer.parseInt(startd)) {
						JOptionPane.showMessageDialog(null, "Wrong Day!!!!!");
						}
						else {
							System.out.println("yy");//day right
								content = readData();
								if(content.size()>2) {
								popFrame(content,symbol);}
								else
									JOptionPane.showMessageDialog(null, "Not enough data found");
						}
				}
		}
			
			
		
		}
		
	}
//download csv
	public void getCSV(String site) { //download
		try (BufferedInputStream inputStream = new BufferedInputStream(new URL(site).openStream()); //input stream for https
				  FileOutputStream outputStream = new FileOutputStream(symbol+starty+".csv")) {
				    byte data[] = new byte[4096];
				    int readByte;
				    while ((readByte = inputStream.read(data, 0, 4096)) != -1) {//read while not end
				    	outputStream.write(data, 0, readByte);
				    }
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			
	}
//read csv
	public List<String[]> readData() { //readFile
		    String file = symbol+starty+".csv";//file name
		    List<String[]> content = new ArrayList<>();
		    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		        String line = "";
		        while ((line = br.readLine()) != null) {
		            content.add(line.split(","));
		        }
		    } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		    return content;
		}
//create a new frame contains results
	public void popFrame(List<String[]> content,String name) {
		JFrame f = new ResultFrame(content,name);
		f.setVisible(true);
	}
//check leap year	
	public static boolean isLeapYear(int year) {    
        boolean isLeapYear = false;  
        if (year % 4 == 0 && year % 100 != 0) { 
            isLeapYear = true;  
        } 
        else if (year % 400 == 0) {  
            isLeapYear = true;  
        }  
        return isLeapYear;  
    }
//check 31 day
	public static boolean is31Day(int month) {  		  
        boolean is31Day = false;  
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)  
        	is31Day = true;  
        return is31Day;  
	}
	
	
	}
	
	
	
	





