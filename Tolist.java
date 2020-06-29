import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



public class Tolist extends JFrame implements ActionListener{
      
	 private JFrame jf;

      private Container c;
      
      private JButton saveButton, deleteButton,flushButton;
	  
	  public static String[] years = {"2020","2021","2022","2023","2024","2025"};
	  
	  public static String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
	  
	  private JPanel leftpanel, rightpanel;
	  private JPanel  cardpanel;
	  
	  private	DefaultTableModel model;

	  private JTable table;
	  
	  JButton todo=new JButton("new todo");
	  
	  final private JComboBox <String> year_select = new JComboBox<String>(years);           
	    
	  private JComboBox <String> month_select = new JComboBox<String>(months);
	    
	  private JComboBox <String> days_select;
      
	  String[] col={"��","��","��","����"};   
	  
	  public Tolist()
	    {
	        
	    	super();
	        jf=new JFrame("Todo");
	        c=jf.getContentPane();
	        c.setLayout(new BorderLayout());

	        jf.setSize(800, 800);
	        jf.setLocationRelativeTo(null);
	        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
	        
	        init();
	        jf.setVisible(true);
	    }
	  
	  public void init()
	    {
	        
	        leftpanel=new JPanel(new BorderLayout());	        
	    
	        model=new DefaultTableModel(col, 0);

	        table=new JTable(model);                
	                                         //����Ĭ�����򣬷������ҳ�棬�����ÿɶ�ѡ��
	        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

	        table.setRowSorter(new TableRowSorter<>(model));

	        JScrollPane js=new JScrollPane(table);	       
	        
	        leftpanel.add(js, BorderLayout.CENTER);//����Ƭ��������ӵ�������

	        JPanel DateSelectPanel=new JPanel(new FlowLayout());

	        leftpanel.add(DateSelectPanel, BorderLayout.NORTH);

	        c.add(leftpanel,BorderLayout.WEST);

	        rightpanel=new JPanel(new BorderLayout());
	       
	        JPanel rightCenter=new JPanel(new BorderLayout());

 	        JPanel event=new JPanel(new GridLayout(1,1));
	        
 	        todo.addActionListener(this);
	        
            event.add(todo);
	        
	        saveButton=new JButton("����");

	        saveButton.addActionListener(this);

	        deleteButton=new JButton("ɾ��");

	        deleteButton.addActionListener(this);

	        flushButton  = new JButton("ˢ��");
	        
	        flushButton.addActionListener(this);
	        
	        event.add(deleteButton);
	        
	        event.add(flushButton);
	        
	        rightCenter.add(event, BorderLayout.NORTH);
	        
	        rightCenter.add(deleteButton,BorderLayout.SOUTH);

	        rightpanel.add(rightCenter, BorderLayout.CENTER);

	        c.add(rightpanel);
	                
	        year_select.addItemListener(new ItemListener() {
	             
	        	
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					// TODO Auto-generated method stub
				    Vector<String> for_days = new Vector<String>();
				    for(int i=1;i<=count_days();i++) {
				    	if(i<10) {
				    		for_days.add('0'+String.valueOf(i));
				    	}else {
				    		for_days.add(String.valueOf(i));
				    	}                                            //��ʼ��������
				    }
				    days_select = new JComboBox<String>(for_days);
				    
				    model.setValueAt((String)year_select.getSelectedItem(),table.getSelectedRow(), 0);
				    
				    table.getColumn(col[2]).setCellEditor(new DefaultCellEditor(days_select));
				    
				    days_select.addItemListener(new ItemListener() {

						@Override
						public void itemStateChanged(ItemEvent arg0) {
							// TODO Auto-generated method stub
							model.setValueAt((String)days_select.getSelectedItem(), table.getSelectedRow(), 2);
						}
				    });
				}
		});
	        
	        month_select.addItemListener(new ItemListener() {
	        	
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					// TODO Auto-generated method stub
				    Vector<String> for_days = new Vector<String>();
				    for(int i=1;i<=count_days();i++) {
				    	if(i<10) {
				    		for_days.add('0'+String.valueOf(i));
				    	}else {
				    		for_days.add(String.valueOf(i));
				    	}                                            //��ʼ��������
				    }
				    days_select = new JComboBox<String>(for_days);
				    		
				    model.setValueAt((String)month_select.getSelectedItem(),table.getSelectedRow(), 1);
				    
	                table.getColumn(col[2]).setCellEditor(new DefaultCellEditor(days_select));
	                
	                days_select.addItemListener(new ItemListener() {

						@Override
						public void itemStateChanged(ItemEvent arg0) {
							// TODO Auto-generated method stub
							model.setValueAt((String)days_select.getSelectedItem(), table.getSelectedRow(), 2);
						}
				    });
	                
				}
	        });
	    }
	  
	  int count_days() {
			int temp_year = Integer.parseInt((String) year_select.getSelectedItem());
			int temp_month = Integer.parseInt((String)month_select.getSelectedItem());
			
			if(((temp_year%4 == 0&&temp_year%100!=0)||temp_year%400 == 0)&&temp_month ==2) {
				return 29;                
			}
			if(temp_month==1||temp_month==3||temp_month==5||temp_month==7||temp_month==8||temp_month==10||temp_month==12) {
				return 31;
			}                                           //����¼��������ڸ�������ʱ�жϸ�������
			else {
				return temp_month==2? 28:30;
			}
		}
	  	 
	  
	  public static ArrayList<Todo> PIMStrogeList = new ArrayList<Todo>();
		public static void main(String[] args) 
	    {	        
	        new Tolist();
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			 if(e.getSource()==deleteButton)
		        {
		             int [] delete_target = table.getSelectedRows();
		             
		             int result = JOptionPane.showConfirmDialog(jf, "ȷ��ɾ����","Tips",JOptionPane.YES_NO_CANCEL_OPTION);
		             
		             if(result == JOptionPane.YES_OPTION) {
		            	 for(int i=0;i<delete_target.length;i++) {
		            		    model.removeRow(table.getSelectedRow());
		            	 }                                          
		            	       //ɾ�����ܣ���ѡ�е���ɾ��
		               JOptionPane.showMessageDialog(jf, "ɾ���ɹ�","Tips",JOptionPane.INFORMATION_MESSAGE);	 
		        }
		    }
			
			 if(e.getSource()==todo) {
		    		Todo PIM = new Todo();
		    		
		    		JFrame noteCreateFrame = new JFrame("Creat note");
		    		
		    		noteCreateFrame.setSize(400, 400);
		    		
		    		noteCreateFrame.setLocationRelativeTo(null);
		    		
		    		noteCreateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    		
		    		JPanel panel = new JPanel(); 
		    	
		    		JButton input_text = new JButton("��������");
		    		
		    		input_text.addActionListener(new ActionListener() {
		    			      public void actionPerformed(ActionEvent e) {
		    			    	  String text = JOptionPane.showInputDialog(noteCreateFrame,"��������",null);
		    			    	   PIM.text = text;
		    			      }
		    		});
		    				    				    				    				    				    		
		    		Box vBox = Box.createVerticalBox();		    		
		    		
		    		JButton input_date = new JButton("�������ڲ��Կո�ָ�");
		    		
		    		input_date.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
							String date = JOptionPane.showInputDialog(noteCreateFrame,"����",null);
							PIM.date = date;
						}		    					    			    
		    		});
		    				    				    				    				    		
		    		vBox.add(input_text);
		    		
		    		vBox.add(saveButton);
		    		
		    		vBox.add(input_date);
		    				    		    				    				    		
		    		panel.add(vBox);
		    		
		    		noteCreateFrame.setContentPane(panel);
		    		
		    		noteCreateFrame.setVisible(true);
		    				    				    		
		    		Tolist.PIMStrogeList.add(PIM);
			 }
			 
			if(e.getSource() == saveButton) {
				try {

	                String str= Tolist.PIMStrogeList.get(0).date;   //��Ҫ�������������Ϊ�ļ���
	                                            
	                File file=new  File("D://PIMGUIfile//"+str+".ser");   //��������strΪ�ļ����Ķ����ļ�

	                if(!file.getParentFile().exists())
	                {
	                    file.getParentFile().mkdirs();      //�������ڷ����ϼ�����
	                }

	                file.createNewFile();
	                
	                if(file.length()==0) {
	                	 ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
	                     os.writeObject(Tolist.PIMStrogeList);
	                	 os.close();                                       //ֱ�ӽ��洢��������Ϊ�������ܿ�stream������ĩβʱ�������鷳
	                }else {
	                	ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
	                	ArrayList<Todo>templist = (ArrayList<Todo>)is.readObject();
	                	is.close();
	                	templist.add(Tolist.PIMStrogeList.get(Tolist.PIMStrogeList.size()-1));
	                	ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(file));
	                	oss.writeObject(templist);                               
	                	oss.close();
	                }
	                                         
	                Tolist.PIMStrogeList.clear();  //�����ʱ��������
	                
	                JOptionPane.showMessageDialog(this, "����ɹ���","��ʾ��",JOptionPane.WARNING_MESSAGE);
	                
	            } catch (Exception e3) {

	                e3.printStackTrace();
	            }
		   }
			 
			   if(e.getSource()==flushButton)
		        {                             //ÿһ��ˢ���б�������洢����ˢ�£���һ��չʾǰ���ж�ȡ
		            try {
		            	
	                		                	
		                    File file=new File("D://PIMGUIfile");

		                    File[] PIMlist=file.listFiles();
		                                                                  //��ȡ�洢������������д�ӡ
		                    for(int i=0;i<PIMlist.length;i++)
		                    {

		                        if(PIMlist[i].isFile()){

		                           ObjectInputStream os = new ObjectInputStream(new FileInputStream(PIMlist[i]));
		                           
		                           ArrayList<Todo>templist = (ArrayList<Todo>)os.readObject();
		                           
		                           os.close();
		                           
		                           for(Todo PIM :templist) {
		                        	     String [] info = {PIM.date.substring(0, 4),PIM.date.substring(5, 7),PIM.date.substring(8, 10),PIM.text};
		                                 model.addRow(info);
		                           }		                           
		                        }
		                    }
		                   
		                   DefaultCellEditor editor_year = new DefaultCellEditor(year_select);
		                   
		                   DefaultCellEditor editor_month = new DefaultCellEditor(month_select);
		                   		                    
		                   table.getColumn(col[0]).setCellEditor(editor_year);
		                   
		                   table.getColumn(col[1]).setCellEditor(editor_month);
		                   
		                   Vector<String> for_days = new Vector<String>();
		                     for(int i=0;i<=count_days();i++) {
		                    	 if(i<10) {
		     			            for_days.add('0'+String.valueOf(i));		
		                     }else {
		                    	 for_days.add(String.valueOf(i));                     
		                    	 }
		                }
		                     
		                     days_select = new  JComboBox<String>(for_days);
		                     
		                     table.getColumn(col[2]).setCellEditor(new DefaultCellEditor(days_select));
		                     		                                
		                }catch (Exception e2) {
		        	e2.printStackTrace();
		          }
		}					
    }
}
class  Todo implements Serializable {
    
    String date;
    String text;
    public void  getDate(String date) {
    	this.date = date;
    }
    public void getText(String Text) {
    	this.text = Text;
    }                                     //�ĸ�����
}

