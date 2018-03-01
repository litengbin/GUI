package ex7;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Properties;

public class PersonManager implements ActionListener{
	JFrame f = null;
	public PersonManager()
	{
		f = new JFrame("员工信息");
		f.setSize(400, 300);
		f.setLocationRelativeTo(null);
		Container contentPane = f.getContentPane();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,2,15,15));
		JButton b = new JButton("员工登记");       
		b.addActionListener(this);
		buttonPanel.add(b);
		b = new JButton("退出系统");
		b.addActionListener(this);
		buttonPanel.add(b);
		b = new JButton("查询功能");
		b.addActionListener(this);
		buttonPanel.add(b);
		b = new JButton("员工信息维护");
		b.addActionListener(this);
		buttonPanel.add(b);
		b = new JButton("统计");
		b.addActionListener(this);
		buttonPanel.add(b);
		b = new JButton("参数维护");
		b.addActionListener(this);
		buttonPanel.add(b);
		buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"员工登记系统",TitledBorder.CENTER,TitledBorder.TOP));
		contentPane.add(buttonPanel,BorderLayout.CENTER);
		JMenuBar mBar = new JMenuBar();
		JMenu selection = new JMenu("选项");
		JMenuItem regist = new JMenuItem("员工登记");
		JMenuItem sum = new JMenuItem("统计");
		JMenuItem query = new JMenuItem("查询功能");
		JMenuItem maintain = new JMenuItem("员工信息维护");
		JMenuItem mainta = new JMenuItem("参数维护");
		selection.add(regist);
		selection.add(sum);
		selection.add(query);
		selection.add(maintain);
		selection.add(mainta);
		JMenu sys = new JMenu("系统");
		JMenuItem exit = new JMenuItem("退出系统");
		sys.add(exit);
		mBar.add(selection);
		mBar.add(sys);
		f.setJMenuBar(mBar);
		regist.addActionListener(this);
		sum.addActionListener(this);
		exit.addActionListener(this);
		query.addActionListener(this);
		maintain.addActionListener(this);
		mainta.addActionListener(this);
		f.pack();
		f.setVisible(true);
		f.setResizable(false); 
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("员工登记"))
		{
			new RegistSystem(f);
		}
		else if(cmd.equals("退出系统"))
		{
			System.exit(0);
		}
		else if(cmd.equals("统计"))
		{
			new CountSystem(f);
		}
		else if(cmd.equals("查询功能"))
		{
			new QuerySystem(f);
		}
		else if(cmd.equals("员工信息维护"))
		{
			new UpdateSystem(f);
		}
		else if(cmd.equals("参数维护"))
		{
			new OtherSystem(f);
		}
	}
	public static void main(String[] args)
	{
		new PersonManager();
	}
}
class OtherSystem implements ActionListener,ItemListener, TableModelListener
{
	JDialog dialog;
	JPanel p,p1,p2,p3,p4;
	JRadioButton r1;
	JRadioButton r2;
	JButton b1,b2,b3,b4;
	JTable table;
	JLabel l,l1,l2;
	ButtonGroup bg;
	JScrollPane scrollPane;
	DefaultTableModel model;
	int i = 0;
	int n = 0;
	Integer Id;
	String Name;
	Properties pro;
	Container dialogPane;
	String oldValue;
	OtherSystem(JFrame f)
	{
		pro = new Properties();
		pro.setProperty("user","Test");
		pro.setProperty("passwords","1234");
	    pro.setProperty("charSet","gbk");
		dialog = new JDialog(f,"参数维护",true);
		dialogPane = dialog.getContentPane();
		dialogPane.setLayout(new BorderLayout());
		p = new JPanel();
		p.setLayout(new GridLayout(1,2,5,5));
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2,1,10,10));
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"选择表",TitledBorder.CENTER,TitledBorder.TOP));
		r1 = new JRadioButton("部门表");
		r2 = new JRadioButton("学历表");
		bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		r1.addItemListener(this);
		r2.addItemListener(this);
		p1.add(r1);
		p1.add(r2);
		p2 = new JPanel();
		p2.setLayout(new GridLayout(3,1,5,3));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"选择方式",TitledBorder.CENTER,TitledBorder.TOP));
		b1 = new JButton("增加");
		p2.add(b1);
		b2 = new JButton("删除");
		p2.add(b2);
		b3 = new JButton("修改");
		p2.add(b3);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		p.add(p1);
		p.add(p2);
		p3 = new JPanel();
		
		dialogPane.add(p,BorderLayout.NORTH);
		dialogPane.add(p3,BorderLayout.CENTER);
		dialog.getRootPane().setDefaultButton(b1);
		dialog.setSize(500,630);
		dialog.setLocationRelativeTo(null);
		r1.setSelected(true);
	}
	public void itemStateChanged(ItemEvent e)
	{
		
		if(e.getSource() == r1)
		{
			i = 1;
			p3.removeAll();
			p3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"显示",TitledBorder.CENTER,TitledBorder.TOP));
			scrollPane = new JScrollPane();
			p3.add(scrollPane, BorderLayout.CENTER);
			table = new JTable();
			scrollPane.setColumnHeaderView(table);
			model = new DefaultTableModel(new Object[][] {}, new String[]{"部门编号",
			"具体部门"}){
				public boolean isCellEditable(int rowindex,int colindex)
				{
					if (colindex==0) 
						return false;  
					return true;                     
				}
			};
			table.setModel(model);
			table.setRowHeight(30);
			scrollPane.setViewportView(table);
			model.addTableModelListener(this);
			JButton b4 = new JButton("关闭");
			b4.addActionListener(this);
			p3.add(b4);
			p3.updateUI();
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				ResultSet rs;
				rs = stmt.executeQuery("select * from Department");
				n = 0;
				while(rs.next())
				{
					n++;
				}
				rs = stmt.executeQuery("select * from Department");
				String[][] info = new String[n][2];
				int j = 0;
				while(rs.next())
				{	
					info[j][0] = String.valueOf(rs.getInt("DepID")); 
					info[j][1] = rs.getString("DepName");
					j++;
				}
				j = 0;
				while(j < n)
				{
					model.addRow(info[j]);
					j++;
				}
				stmt.close();
				con.close();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.print("Error " + ex.getMessage());
			}
			dialogPane.add(p3,BorderLayout.CENTER);
		}
		if(e.getSource() == r2)
		{
			i = 2;
			p3.removeAll();
			p3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"显示",TitledBorder.CENTER,TitledBorder.TOP));
			scrollPane = new JScrollPane();
			p3.add(scrollPane, BorderLayout.CENTER);
			table = new JTable();
			scrollPane.setColumnHeaderView(table);
			model = new DefaultTableModel(new Object[][] {}, new String[]{"学历编号",
			"具体学历"}){
				public boolean isCellEditable(int rowindex,int colindex)
				{
					if (colindex==0) 
						return false;  
					return true;                     
				}
			};	
			table.setModel(model);
			table.setRowHeight(30);
			scrollPane.setViewportView(table);
			model.addTableModelListener(this);
			JButton b4 = new JButton("关闭");
			b4.addActionListener(this);
			p3.add(b4);
			p3.updateUI();
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				ResultSet rs;
				rs = stmt.executeQuery("select * from Education");
				n = 0;
				while(rs.next())
				{
					n++;
				}
				rs = stmt.executeQuery("select * from Education");
				String[][] info = new String[n][2];
				int j = 0;
				while(rs.next())
				{	
					info[j][0] = String.valueOf(rs.getInt("EduID")); 
					info[j][1] = rs.getString("EduName");
					j++;
				}
				j = 0;
				while(j < n)
				{
					model.addRow(info[j]);
					j++;
				}
				stmt.close();
				con.close();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.print("Error " + ex.getMessage());
			}
			dialogPane.add(p3,BorderLayout.CENTER);
		}	
		dialog.setVisible(true);
	}
	public void tableChanged(TableModelEvent e) 
	{
		if(e.getColumn() < 0)
		{
			return;
		}
		if(e.getColumn() == 0) 
		{
			JOptionPane.showMessageDialog(dialog,"主键不能修改!","警告",JOptionPane.WARNING_MESSAGE);
			return;
			
		}
		Id = Integer.valueOf(table.getValueAt(e.getLastRow(),0).toString());
		Name = table.getValueAt(e.getLastRow(),1).toString();
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		String sql = "";
		pro = new Properties();
		pro.setProperty("user","Test");
		pro.setProperty("passwords","1234");
	    pro.setProperty("charSet","gbk");
		if(cmd.equals("增加"))
		{
			Object info[][] = new Object[1][2];
			info[0][0] = String.valueOf(++n);
			info[0][1] = "请输入数据";
			model.addRow(info[0]);
			Id = Integer.valueOf(table.getValueAt(n-1,0).toString());
			Name = table.getValueAt(n-1, 1).toString();
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				switch(i)
				{
				case 1:sql = "insert into Department values("+Id+",'"+Name+"')";break;
				case 2:sql = "insert into Education values("+Id+",'"+Name+"')";break;
				}
		//		System.out.println(sql);
				stmt.executeUpdate(sql);		
				stmt.close();
				con.close();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.print("Error " + ex.getMessage());
			}
		}
		else if(cmd.equals("删除"))
		{
			if(table.getSelectedRowCount() <= 0) 
			{
				JOptionPane.showMessageDialog(null, "请选择要删除的数据行");
				return;
			}
			int result = JOptionPane.showConfirmDialog(null, "是否确定要删除");
			if (result == JOptionPane.OK_OPTION) 
			{
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
					Statement stmt = con.createStatement();
					int delIndex = table.getSelectedRow();
					Id = Integer.valueOf(table.getValueAt(table.getSelectedRow(),0).toString());
					switch(i)
					{
					case 1:sql = "delete from Department where DepID = " + Id.toString().trim();break;
					case 2:sql = "delete from Education where EduID = " + Id.toString().trim();break;
					}
			//		System.out.println(sql);
					stmt.executeUpdate(sql);
					n--;
					stmt.close();
					con.close();
					model.removeRow(delIndex) ;
					model.fireTableDataChanged();
					JOptionPane.showMessageDialog(dialog,"恭喜你,删除成功!","消息",JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
					System.out.print("Error " + ex.getMessage());
				}
			}
		}
		else if(cmd.equals("修改"))
		{
			if(table.getSelectedRow() < 0)
			{
				JOptionPane.showMessageDialog(dialog,"请修改数据","消息",JOptionPane.WARNING_MESSAGE);
				return;
			}
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				switch(i)
				{
				case 1:sql = "update Department set DepName = '" + Name + "'where DepID = " + Id.toString().trim();break;
				case 2:sql = "update Education set EduName = '" + Name + "'where EduID = " + Id.toString().trim();break;
				}
			//	System.out.println(sql);
				stmt.executeUpdate(sql);
				stmt.close();
				con.close();
				JOptionPane.showMessageDialog(dialog,"恭喜你,修改成功!","消息",JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.print("Error " + ex.getMessage());
			}
		}
		else if(cmd.equals("关闭"))
		{
			dialog.dispose();
		}
	}
}
class CountSystem implements ActionListener
{
	JDialog dialog;
	JPanel p1,p2;
	JTextField tF1 = new JTextField();
	JTextField tF2 = new JTextField();
	JTextField tF3 = new JTextField();
	JTextField tF4 = new JTextField();
	JTextField tF5 = new JTextField();
	JTextField tF6 = new JTextField();
	JTextField tF7 = new JTextField();
	JTextField tF8 = new JTextField();
	JTextField tF9 = new JTextField();
	JButton b1;
	JLabel l,l1,l2;
	CountSystem(JFrame f)
	{
		tF1.setEditable(false);
		tF2.setEditable(false);
		tF3.setEditable(false);
		tF4.setEditable(false);
		tF5.setEditable(false);
		tF6.setEditable(false);
		tF7.setEditable(false);
		tF8.setEditable(false);
		tF9.setEditable(false);		
		Properties pro = new Properties();
		pro.setProperty("user","Test");
		pro.setProperty("passwords","1234");
	    pro.setProperty("charSet","gbk");
		dialog = new JDialog(f,"员工统计",true);
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(new BorderLayout());
		p1 = new JPanel();
		p1.setLayout(new GridLayout(3,2));
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"员工状态",TitledBorder.CENTER,TitledBorder.TOP));
		l = new JLabel("在职:",SwingConstants.CENTER);
		p1.add(l);
		p1.add(tF1);
		l1 = new JLabel("新入职:",SwingConstants.CENTER);
		p1.add(l1);
		p1.add(tF2);
		l2 = new JLabel("离职:",SwingConstants.CENTER);
		p1.add(l2);
		p1.add(tF3);
		p2 = new JPanel();
		p2.setLayout(new GridLayout(6,2));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"学历",TitledBorder.CENTER,TitledBorder.TOP));
		p2.add(new JLabel("初中:",SwingConstants.CENTER));
		p2.add(tF4);
		p2.add(new JLabel("高中:",SwingConstants.CENTER));
		p2.add(tF5);
		p2.add(new JLabel("大专:",SwingConstants.CENTER));
		p2.add(tF6);
		p2.add(new JLabel("本科:",SwingConstants.CENTER));
		p2.add(tF7);
		p2.add(new JLabel("硕士:",SwingConstants.CENTER));
		p2.add(tF8);
		p2.add(new JLabel("博士:",SwingConstants.CENTER));
		p2.add(tF9);
		b1 = new JButton("关闭");
		b1.addActionListener(this);
		dialogPane.add(p1,BorderLayout.NORTH);
		dialogPane.add(p2,BorderLayout.CENTER);
		dialogPane.add(b1,BorderLayout.SOUTH);
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Person1 where Statement = 1");
			int i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF1.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Statement = 2");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF2.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Statement = 3");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF3.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Education = 1");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF4.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Education = 2");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF5.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Education = 3");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF6.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Education = 4");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF7.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Education = 5");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF8.setText(String.valueOf(i));
			rs = stmt.executeQuery("select * from Person1 where Education = 6");
			i = 0;
			while(rs.next())
			{
				i = i + 1;
			}
			tF9.setText(String.valueOf(i));
			stmt.close();
			con.close();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
			System.out.print("Error " + ex.getMessage());
		}
		dialog.getRootPane().setDefaultButton(b1);
		dialog.setSize(400,300);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("关闭"))
		{
			dialog.dispose();
		}
	}
}
class RegistSystem implements ActionListener
{
	JDialog dialog;
	JTextField tF1 = new JTextField();
	JTextField tF2 = new JTextField();
	JTextField tF3 = new JTextField();
	JTextField tF4 = new JTextField();
	JTextField tF5 = new JTextField();
	JTextField tF6 = new JTextField();
	JTextField tF7 = new JTextField();
	JPanel p1;
	RegistSystem(JFrame f)
	{
		dialog = new JDialog(f,"员工登记",true);
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(new BorderLayout());
		p1 = new JPanel();
		p1.setLayout(new GridLayout(8,2,5,5));
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"员工信息登记",TitledBorder.CENTER,TitledBorder.TOP));
		p1.add(new JLabel("员工编号:",SwingConstants.CENTER));
		p1.add(tF1);
		p1.add(new JLabel("员工姓名:",SwingConstants.CENTER));
		p1.add(tF2);
		p1.add(new JLabel("部门编号:",SwingConstants.CENTER));
		p1.add(tF3);
		p1.add(new JLabel("职务:",SwingConstants.CENTER));
		p1.add(tF4);
		p1.add(new JLabel("工资:",SwingConstants.CENTER));
		p1.add(tF5);
		p1.add(new JLabel("学历编号:",SwingConstants.CENTER));
		p1.add(tF6);
		p1.add(new JLabel("员工状态:",SwingConstants.CENTER));
		p1.add(tF7);
		JButton b1 = new JButton("确定");
		p1.add(b1);
		JButton b2 = new JButton("取消");
		p1.add(b2);
		b1.addActionListener(this);
		b2.addActionListener(this);
		dialogPane.add(p1,BorderLayout.CENTER);
		dialog.getRootPane().setDefaultButton(b1);
		dialog.setSize(350,310);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		Properties pro = new Properties();
		pro.setProperty("user","Test");
		pro.setProperty("passwords","1234");
	    pro.setProperty("charSet","gbk");
		if(cmd.equals("确定"))
		{
			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				int ID = Integer.parseInt(tF1.getText());
				String name = tF2.getText();
				int DepID = Integer.parseInt(tF3.getText());
				String Occupation = tF4.getText();
				int salary = Integer.parseInt(tF5.getText());
				int EduID = Integer.parseInt(tF6.getText());
				int StaID = Integer.parseInt(tF7.getText());
				String SQLOrder = "INSERT INTO Person1 VALUES("+ID+",'"+name+"',"+DepID+",'"+Occupation+"',"+salary+","+EduID+","+StaID+")";
				stmt.executeUpdate(SQLOrder);
				stmt.close();
				con.close();
				JOptionPane.showMessageDialog(dialog,"增加成功!","消息",JOptionPane.INFORMATION_MESSAGE);
				tF1.setText("");
				tF2.setText("");
				tF3.setText("");
				tF4.setText("");
				tF5.setText("");
				tF6.setText("");
				tF7.setText("");
				tF1.requestFocus();
			}
			catch(Exception ex)
			{
				
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.println("Error " + ex.getMessage());
			}
		}
		else if(cmd.equals("取消"))
		{
			dialog.dispose();
		}
	}
}
class QuerySystem implements ActionListener,ItemListener
{
	JDialog dialog;
	JPanel p1,p2;
	JRadioButton r1;
	JRadioButton r2;
	JRadioButton r3;
	JTextField tF1 = new JTextField();
	ButtonGroup bg;
	JButton b1,b2,b3;
	JTable table;
	JLabel l,l1,l2;
	JScrollPane scrollPane;
	DefaultTableModel model;
	int i = 1;
	Container dialogPane;
	QuerySystem(JFrame f)
	{
		dialog = new JDialog(f,"员工查询",true);
		dialogPane = dialog.getContentPane();
		dialogPane.setLayout(new BorderLayout());
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2,3,10,10));
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"查询方式",TitledBorder.CENTER,TitledBorder.TOP));
		r1 = new JRadioButton("按编号查询");
		r2 = new JRadioButton("按姓名查询");
		r3 = new JRadioButton("按部门查询");
		p1.add(r1);
		p1.add(r2);
		p1.add(r3);
		bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		bg.add(r3);
		r1.addItemListener(this);
		r2.addItemListener(this);
		r3.addItemListener(this);
		l = new JLabel("员工编号:",SwingConstants.CENTER);
		p1.add(l);
		p1.add(tF1);
		b1 = new JButton("查询");
		p1.add(b1); 
		b1.addActionListener(this);
		r1.setSelected(true);
		p2 = new JPanel();
		scrollPane = new JScrollPane();
		p2.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		model = new DefaultTableModel(new Object[][] {}, new String[] { "员工编号",
				"员工姓名", "所属部门", "职位", "工资", "学历", "员工状态"}){
			public boolean isCellEditable(int rowindex,int colindex)
			{
				return false;                     
			}
		};
		table.setModel(model);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"查询结果",TitledBorder.CENTER,TitledBorder.TOP));
		l1 = new JLabel("页数:");
		l2 = new JLabel("0/0");
		p2.add(l1);
		p2.add(l2);
		b2 = new JButton("下一页");
		p2.add(b2);
		b2.setEnabled(false);
		b3 = new JButton("取消");
		p2.add(b3);
		b2.addActionListener(this);
		b3.addActionListener(this);
		dialogPane.add(p1,BorderLayout.NORTH);
		dialogPane.add(p2,BorderLayout.CENTER);
		dialog.getRootPane().setDefaultButton(b1);
		dialog.setSize(500,610);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getSource() == r1)
		{
			i = 1;
			l.setText("员工编号:");
		}
		if(e.getSource() == r2)
		{
			i = 2;
			l.setText("员工姓名:");
		}
		if(e.getSource() == r3)
		{
			i = 3;
			l.setText("员工部门:");
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		int id = 1;
		String s;
		String sql = "";
		Properties pro = new Properties();
		pro.setProperty("user","Test");
		pro.setProperty("passwords","1234");
	    pro.setProperty("charSet","gbk");
	    int n = 0;
		if(cmd.equals("查询"))
		{
			p2.removeAll();
			p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"查询结果",TitledBorder.CENTER,TitledBorder.TOP));
			scrollPane = new JScrollPane();
			p2.add(scrollPane, BorderLayout.CENTER);
			table = new JTable();
			scrollPane.setColumnHeaderView(table);
			model = new DefaultTableModel(new Object[][] {}, new String[] { "员工编号",
					"员工姓名", "所属部门", "职位", "工资", "学历", "员工状态"}){
				public boolean isCellEditable(int rowindex,int colindex)
				{
					return false;                     
				}
			};
			table.setModel(model);
			table.setRowHeight(30);
			scrollPane.setViewportView(table);
			l1 = new JLabel("页数:");
			l2 = new JLabel("1/1");
			p2.add(l1);
			p2.add(l2);
			b2 = new JButton("下一页");
			p2.add(b2);
			b3 = new JButton("取消");
			p2.add(b3);
			b2.addActionListener(this);
			b3.addActionListener(this);
			dialogPane.add(p2,BorderLayout.CENTER);
			p2.updateUI();
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				ResultSet rs;
				rs = stmt.executeQuery("select * from Person1");
				while(rs.next())
				{
					n++;
				}
				switch(i)
				{
				case 1:
					id = Integer.parseInt(tF1.getText().toString().trim());
					sql = "select * from Person1 where ID = " + id;
					break;
				case 2:
					s = tF1.getText().toString().trim();
					sql = "select * from Person1 where Name = '" + s + "'";
					break;
				case 3:
					id = Integer.parseInt(tF1.getText().toString().trim());
					sql = "select * from Person1 where Department = " + id;
					break;
				}
				rs = stmt.executeQuery(sql);		
				String[][] info = new String[n][7];
				int j = 0;
				if(rs.next() == false)
				{
					l2.setText("0/0");
					b2.setEnabled(false);
					JOptionPane.showMessageDialog(dialog,"无查询结果","消息",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					do
					{	
						info[j][0] = String.valueOf(rs.getInt("ID")); 
						info[j][1] = rs.getString("Name");
						info[j][2] = String.valueOf(rs.getInt("Department"));
						info[j][3] = rs.getString("Occupation");
						info[j][4] = String.valueOf(rs.getInt("Salary"));  
						info[j][5] = String.valueOf(rs.getInt("Education"));  
						info[j][6] = String.valueOf(rs.getInt("Statement"));
						j++;
					}while(rs.next());
					j = 0;
					while(j < n)
					{
						model.addRow(info[j]);
						j++;
					}
				}
				stmt.close();
				con.close();
				tF1.setText("");
				tF1.requestFocus();
			}
			catch(Exception ex)
			{
				l2.setText("0/0");
				b2.setEnabled(false);
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.println("Error " + ex.getMessage());
				tF1.setText("");
				tF1.requestFocus();
			}
		}
		else if(cmd.equals("取消"))
		{
			dialog.dispose();
		}
		else if(cmd.equals("下一页"))
		{
			JOptionPane.showMessageDialog(dialog,"已经是最后一页","消息",JOptionPane.WARNING_MESSAGE);
		}
	}
}
class UpdateSystem implements ActionListener
{
	JDialog dialog;
	JPanel p1,p2;
	JTextField tF1 = new JTextField();
	JTextField tF2 = new JTextField();
	JTextField tF3 = new JTextField();
	JTextField tF4 = new JTextField();
	JTextField tF5 = new JTextField();
	JTextField tF6 = new JTextField();
	JTextField tF7 = new JTextField();
	JTextField tF8 = new JTextField();
	JButton b1,b2,b3;
	JLabel l,l1,l2;
	UpdateSystem(JFrame f)
	{
		tF2.setEditable(false);
		dialog = new JDialog(f,"员工信息维护",true);
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(new BorderLayout());
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1,3,10,10));
		p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"编号查找",TitledBorder.CENTER,TitledBorder.TOP));
		l = new JLabel("员工编号:",SwingConstants.CENTER);
		p1.add(l);
		p1.add(tF1);
		b1 = new JButton("查询");
		p1.add(b1); 
		b1.addActionListener(this);
		p2 = new JPanel();
		p2.setLayout(new GridLayout(8,2,5,5));
		p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray,1),"查询结果",TitledBorder.CENTER,TitledBorder.TOP));
		p2.add(new JLabel("员工编号:",SwingConstants.CENTER));
		p2.add(tF2);
		p2.add(new JLabel("员工姓名:",SwingConstants.CENTER));
		p2.add(tF3);
		p2.add(new JLabel("部门编号:",SwingConstants.CENTER));
		p2.add(tF4);
		p2.add(new JLabel("职务:",SwingConstants.CENTER));
		p2.add(tF5);
		p2.add(new JLabel("工资:",SwingConstants.CENTER));
		p2.add(tF6);
		p2.add(new JLabel("学历编号:",SwingConstants.CENTER));
		p2.add(tF7);
		p2.add(new JLabel("员工状态:",SwingConstants.CENTER));
		p2.add(tF8);
		b2 = new JButton("修改");
		p2.add(b2);
		b3 = new JButton("取消");
		p2.add(b3);
		b2.addActionListener(this);
		b3.addActionListener(this);
		dialogPane.add(p1,BorderLayout.NORTH);
		dialogPane.add(p2,BorderLayout.CENTER);
		dialog.getRootPane().setDefaultButton(b1);
		dialog.setSize(400,335);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		int id = 1;
		String sql = "";
		Properties pro = new Properties();
		pro.setProperty("user","Test");
		pro.setProperty("passwords","1234");
	    pro.setProperty("charSet","gbk");
		if(cmd.equals("查询"))
		{
			try
			{
				id = Integer.parseInt(tF1.getText().toString().trim());
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				ResultSet rs;
				sql = "select * from Person1 where ID = " + id;
				rs = stmt.executeQuery(sql);		
				if(rs.next())
				{	
					tF2.setText(String.valueOf(rs.getInt("ID")));
					tF3.setText(rs.getString("Name"));
					tF4.setText(String.valueOf(rs.getInt("Department")));
					tF5.setText(rs.getString("Occupation"));
					tF6.setText(String.valueOf(rs.getInt("Salary")));
					tF7.setText(String.valueOf(rs.getInt("Education")));
					tF8.setText(String.valueOf(rs.getInt("Statement")));
				}
				else
				{
					JOptionPane.showMessageDialog(dialog,"无查询结果","消息",JOptionPane.WARNING_MESSAGE);
				}
				stmt.close();
				con.close();
				tF3.requestFocus();
			}
			catch(Exception ex)
			{
				tF2.setText("");
				tF3.setText("");
				tF4.setText("");
				tF5.setText("");
				tF6.setText("");
				tF7.setText("");
				tF8.setText("");
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.println("Error " + ex.getMessage());
				tF1.setText("");
				tF1.requestFocus();
			}
		}
		else if(cmd.equals("取消"))
		{
			dialog.dispose();
		}
		else if(cmd.equals("修改"))
		{
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con = DriverManager.getConnection("jdbc:odbc:PIMS",pro);
				Statement stmt = con.createStatement();
				int t1 = Integer.parseInt(tF2.getText().toString().trim());
				String t2 = tF3.getText().toString().trim();
				int t3 = Integer.parseInt(tF4.getText().toString().trim());
				String t4 = tF5.getText().toString().trim();
				int t5 = Integer.parseInt(tF6.getText().toString().trim());
				int t6 = Integer.parseInt(tF7.getText().toString().trim());
				int t7 = Integer.parseInt(tF8.getText().toString().trim());
				String s1,s2;
				s1 = "update Person1 set Name = '"+t2+"',Department = "+t3+",Occupation = '"+t4+"',";
				s2 = "Salary = "+t5+",Education = "+t6+",Statement = "+t7+" where ID = "+t1;
				sql = s1 + s2;
				stmt.executeUpdate(sql);		
				stmt.close();
				con.close();
				JOptionPane.showMessageDialog(dialog,"恭喜你,修改成功!","消息",JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(dialog,"Error","消息",JOptionPane.WARNING_MESSAGE);
				System.out.print("Error " + ex.getMessage());
			}
		}
	}
}