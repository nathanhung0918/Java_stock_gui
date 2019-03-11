import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ResultFrame extends JFrame implements ActionListener{
	String[] date;
	String[] open;
	String[] high;
	String[] low;
	String[] close;
	String[] volume;
	JPanel p ;
	private JComboBox choice;
	private String mChoice;
	private JTable table;
	int size;
	List<String[]> content;
	String[][] data;
	String[] columnNames = {"Date","Close"};
	String[] columnNames2 = {"Date","aa"};
	ResultPanel mGraph;
	String name;
//init the frame
	public ResultFrame(List<String[]> content,String name) {
		this.content = content;//data extracted from csv
		this.name = name;
		size = content.size();
		data = new String[size][2];
		setTitle(name);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//set screen
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		this.setBounds(0,0, (int) width/2, (int) height/2);
		transfer(content);
		Container contentPane = getContentPane();
		
		for(int i=0;i<date.length;i++) { //set data to 2 dim array
			data[i][0] = date[i]; 
			data[i][1] = close[i]; 
		}
		
		p = new JPanel();
		choice = addChoiceJComboBox(p,this);//can choose what to display
		contentPane.add(p, "North");
        Collections.reverse(Arrays.asList(date));//reverse date

        mGraph = new ResultPanel(date,close);//graph of data
        contentPane.add(mGraph,"Center");
        
        
        
        
	}
//cut data into 6 parts 
	public void transfer(List<String[]> content) {
		date = new String[size-1];
		open = new String[size-1];
		high = new String[size-1];
		low = new String[size-1];
		close = new String[size-1];
		volume = new String[size-1];
		for(int i=1;i<size;i++) {
			String[] sArray = content.get(i);
			date[i-1] = new String(sArray[0]);
			open[i-1] = new String(sArray[1]);
			high[i-1] = new String(sArray[2]);
			low[i-1] = new String(sArray[3]);
			close[i-1] = new String(sArray[4]);
			volume[i-1] = new String(sArray[5]);

		}
	}
	
//Jcombo hox and listener
	private JComboBox addChoiceJComboBox(JPanel p, ActionListener a) {
		String mList[] = new String[5];
		mList[0] = "open";
		mList[1] = "high";
		mList[2] = "low";
		mList[3] = "close";
		mList[4] = "volume";
		
		JComboBox c = new JComboBox(mList);
		c.addActionListener(a);
		p.add(c);
		return c;
	}
//actions
	@Override
	public void actionPerformed(ActionEvent e) {
		mChoice = (String) choice.getSelectedItem();
		if(mChoice=="open") {
			for(int i=0;i<date.length;i++) {//wtite to data 
				data[i][0] = date[i]; 
				data[i][1] = open[i]; 
			}
			 mGraph = new ResultPanel(date,open);
			 this.add(mGraph,"Center");
			 mGraph.revalidate();
			 mGraph.repaint();
			}
		else if(mChoice=="high") {
			for(int i=0;i<date.length;i++) {
				data[i][0] = date[i]; 
				data[i][1] = high[i]; 
			}
			 mGraph = new ResultPanel(date,high);
			 this.add(mGraph,"Center");
			 mGraph.revalidate();
			 mGraph.repaint();

			System.out.println("high");
		}
		else if(mChoice=="low") {
			for(int i=0;i<date.length;i++) {
				data[i][0] = date[i]; 
				data[i][1] = low[i]; 
			}
			 mGraph = new ResultPanel(date,low);
			 this.add(mGraph,"Center");
			 mGraph.revalidate();
			 mGraph.repaint();
		}
		else if(mChoice=="close") {
			for(int i=0;i<date.length;i++) {
				data[i][0] = date[i]; 
				data[i][1] = close[i]; 
			}
			 mGraph = new ResultPanel(date,close);
			 this.add(mGraph,"Center");
			 mGraph.revalidate();
			 mGraph.repaint();
		}
			
		else if(mChoice=="volume") {
			for(int i=0;i<date.length;i++) {
				data[i][0] = date[i]; 
				data[i][1] = volume[i]; 
			}
			 mGraph = new ResultPanel(date,volume);
			 this.add(mGraph,"Center");
			 mGraph.revalidate();
			 mGraph.repaint();
		}
			
	}
	
}


