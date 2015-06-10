import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ChartManageServer extends JFrame {
	private HashMap<String, DataOutputStream> clients;
	private ServerSocket serverSocket;
	private JTextField textField;
	private JTextField textField_1;
	private JFrame help_jframe;
	//private JTextField textField;
	//private final JTable table = new JTable();
	
	private void initComponents()
	{
		/*
		JFrame frame;
		JPanel east_panel;
		JTextField printWindow;
		
		frame = new JFrame("CHART MANAGE SERVICE -SERVER");
		east_panel = new JPanel();
		//printWindow = new JTextField("0");
		
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		
		//printWindow.setHorizontalAlignment(JTextField.RIGHT);   // 우측정렬
		//printWindow.setEditable(false);
		//printWindow.setVisible(true);
		
		frame.setSize(486,300);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 231, 446, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\uCC28\uD2B8 \uD1B5\uD569");
		
		
		btnNewButton.setBounds(371, 10, 87, 44);
		frame.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("\uCC28\uD2B8 \uBAA9\uB85D");
		button.setBounds(371, 64, 87, 47);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("\uCD08\uAE30\uD654");
		button_1.setBounds(371, 121, 87, 46);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("\uB3C4\uC6C0\uB9D0");
		button_2.setBounds(371, 177, 87, 44);
		frame.getContentPane().add(button_2);
		table.setBounds(12, 10, 347, 211);
		frame.getContentPane().add(table);
		frame.setVisible(true);
		*/
		
	}
	
	
	public static void main(String[] args)
	{
		
		new ChartManageServer().start();
	}
	
	public ChartManageServer()
	{
		setTitle("CHART MANAGE SERVICE (SERVER)");
		getContentPane().setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setBounds(12, 231, 560, 21);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(12, 10, 454, 211);
		getContentPane().add(textPane);
		
		JButton btnNewButton = new JButton("\uCC28\uD2B8 \uD1B5\uD569");
		btnNewButton.setBounds(478, 10, 94, 36);
		getContentPane().add(btnNewButton);
		
		JButton button = new JButton("\uCC28\uD2B8 \uBAA9\uB85D");
		button.setBounds(478, 66, 94, 36);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("\uCD08\uAE30\uD654");
		button_1.setBounds(478, 123, 94, 36);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("\uB3C4\uC6C0\uB9D0");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame help_frame = new JFrame();
				
				
			}
		});
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		button_2.setBounds(478, 181, 94, 36);
		getContentPane().add(button_2);
		
		
		clients = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clients);
		
		initComponents();
	}
	public void start()
	{
		try{
			Socket socket = null;
			serverSocket = new ServerSocket(6000);
			System.out.println("차트 통합 관리 서버가 기동중입니다.");
			
			while(true)
			{
			socket = serverSocket.accept();
			System.out.println("호스트와 연결 완료.");
			
			ChartReceiver fr = new ChartReceiver(socket); //클라이언트와의
			fr.start();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}


class ChartReceiver extends Thread
{
	Socket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	FileOutputStream fos = null;
	BufferedOutputStream bos = null;
	public ChartReceiver(Socket socket)
	{
		this.socket = socket;
		try
		{
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} 
		catch (IOException e)
		{
			
		}
		
	}

	public void run()
	{
		String host = "";
		
		try
		{
			while(true){
			host = dis.readUTF();
			System.out.println("HOST : "+host+" 지부가 파일 송/수신 대기 상태입니다.");
			clients.put(host, dos);
		
			if (socket.isConnected() == false)
			{
				System.out.println("@@@@@@@@@@@@@@@@");
				clients.remove(host, dos);
			}
			
			String fName = dis.readUTF();
			FileOutputStream fos = new FileOutputStream("c:\\test\\" + fName);
			byte[] b = new byte[1024];
			int n = 0;
			long Filesize = 0;
			while ((n=dis.read(b)) != -1)
			{
				fos.write(b, 0, n);
				Filesize += n;
			}
			
		}
		}
		catch (IOException e)
		{
			
		}
		
		finally
		{
			Set key = clients.keySet();

			System.out.println("현재 "+ clients.size()+"개의 지부가 대기 중입니다.");
			System.out.println("========== 대기 중 지부 목록 ==========");
			for (Iterator iterator = key.iterator(); iterator.hasNext();) 
			{
				String keyName = (String) iterator.next();
				System.out.println(keyName);
			}
			System.out.println("=================================");
		}
		
		
	}
}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
