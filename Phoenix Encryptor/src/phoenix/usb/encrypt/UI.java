package phoenix.usb.encrypt;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class UI extends JFrame {

	public String filePath;
	public String phoenixFilePath;
	public String ashFilePath;
	public String usbPath;
	private JPanel contentPane;
	public static URL checkedUrl;
	public static URL logoUrl;
	public static URL waitTop, waitBot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			checkedUrl = UI.class.getResource("/checked.png");
			logoUrl = UI.class.getResource("/phoenix.png");
			waitTop = UI.class.getResource("/waitTop.gif");
			waitBot = UI.class.getResource("/waitBot.gif");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ })
	public UI() {
		setResizable(false);
		java.awt.Image image;
		try {
			image = ImageIO.read(logoUrl);
			setIconImage(image);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setTitle("Phoenix");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 604, 417);
		addWindowListener(new java.awt.event.WindowAdapter()
				{
					@Override
					public void windowClosing(java.awt.event.WindowEvent windowEvent)
					{
						try
						{
							//String tempDirectory = System.getProperty("java.io.tmpdir");
							//File outputDir = new File(tempDirectory + "/phoenix");
							//FileUtils.cleanDirectory(outputDir);
							System.exit(0);
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
							System.exit(0);
						}
					}
				});
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSecureYourFiles = new JLabel("Educo Soft Encryptor");
		lblSecureYourFiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblSecureYourFiles.setForeground(Color.WHITE);
		lblSecureYourFiles.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSecureYourFiles.setBounds(205, 40, 187, 26);
		contentPane.add(lblSecureYourFiles);
		
		JLabel lblProcessingBot = new JLabel("");
		lblProcessingBot.setBounds(320, 335, 85, 50);
		lblProcessingBot.setVisible(false);
		contentPane.add(lblProcessingBot);
		lblProcessingBot.setHorizontalAlignment(SwingConstants.CENTER);
		lblProcessingBot.setIcon(new ImageIcon("C:\\Users\\SChathuranga\\Downloads\\Facebook-1s-103px.gif"));
		
		JLabel lblProcessingText = new JLabel("Please wait..");
		lblProcessingText.setForeground(Color.WHITE);
		lblProcessingText.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblProcessingText.setBounds(200, 348, 103, 25);
		lblProcessingText.setVisible(false);
		contentPane.add(lblProcessingText);
		
		JLabel lblProcessingTop = new JLabel("New label");
		lblProcessingTop.setHorizontalAlignment(SwingConstants.CENTER);
		lblProcessingTop.setIcon(new ImageIcon("C:\\Users\\SChathuranga\\Downloads\\Ripple-1s-200px(1).gif"));
		lblProcessingTop.setBounds(404, 31, 55, 50);
		lblProcessingTop.setVisible(false);
		contentPane.add(lblProcessingTop);
		
		JPanel panel = new JPanel();
		panel.setBounds(8, 75, 586, 266);
		contentPane.add(panel);
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		
		JLabel lblUsbDrive = new JLabel("USB Drive");
		lblUsbDrive.setBounds(119, 52, 73, 16);
		panel.add(lblUsbDrive);
		lblUsbDrive.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblUsbDrive.setForeground(Color.WHITE);
		
		JLabel lblSelectFiles = new JLabel("Select File");
		lblSelectFiles.setBounds(119, 113, 73, 16);
		panel.add(lblSelectFiles);
		lblSelectFiles.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblSelectFiles.setForeground(Color.WHITE);
		
		JLabel lblFileChecked = new JLabel("");
		lblFileChecked.setIcon(new ImageIcon(checkedUrl));
		lblFileChecked.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileChecked.setBounds(478, 107, 28, 28);
		lblFileChecked.setVisible(false);
		panel.add(lblFileChecked);
		
		JButton btnSelectFiles = new JButton("Select File");
		btnSelectFiles.setForeground(Color.WHITE);
		btnSelectFiles.setBackground(Color.DARK_GRAY);
		btnSelectFiles.setBounds(311, 107, 155, 28);
		panel.add(btnSelectFiles);
		
		JLabel lblExpireDate = new JLabel("Expire Date");
		lblExpireDate.setForeground(Color.WHITE);
		lblExpireDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblExpireDate.setBounds(119, 165, 73, 16);
		panel.add(lblExpireDate);
		
		JLabel lblUSBChecked = new JLabel("");
		lblUSBChecked.setHorizontalAlignment(SwingConstants.CENTER);
		lblUSBChecked.setIcon(new ImageIcon(checkedUrl));
		lblUSBChecked.setBounds(478, 46, 28, 28);
		lblUSBChecked.setVisible(false);
		panel.add(lblUSBChecked);
		
		JButton btnSelectUsbDrive = new JButton("Select USB Drive");
		btnSelectUsbDrive.setForeground(Color.WHITE);
		btnSelectUsbDrive.setBackground(Color.DARK_GRAY);
		btnSelectUsbDrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Choose your USB drive");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(fileChooser.showOpenDialog(btnSelectFiles) == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					usbPath = file.getAbsolutePath();
					lblUSBChecked.setVisible(true);
					System.out.println(usbPath.toString());
				}
				else
					JOptionPane.showMessageDialog(null, "No USB directory selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnSelectUsbDrive.setBounds(311, 46, 155, 28);
		panel.add(btnSelectUsbDrive);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setBackground(Color.DARK_GRAY);
		dateChooser.setBackground(Color.DARK_GRAY);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBorder(null);
		dateChooser.setBounds(311, 158, 155, 27);
		panel.add(dateChooser);
		
		JButton btnMakeUsb = new JButton("Make USB");
		btnMakeUsb.setForeground(Color.WHITE);
		btnMakeUsb.setBackground(Color.DARK_GRAY);
		btnMakeUsb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//make USB
				lblProcessingTop.setVisible(true);
				lblProcessingBot.setVisible(true);
				lblProcessingText.setVisible(true);
				String expireDate = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
				SampleMain process = new SampleMain();
				if(process.fileEncrypt(filePath, expireDate))
				{
					try
					{
						FileHandler handler = new FileHandler();
						//System.out.println("filePath: " + filePath.toString() + "\n usbPath: " + usbPath.toString());
						FileUtils.copyToDirectory(handler.generateSourceFile(filePath), handler.generateDestFile(usbPath));
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Copying files to USB drive failed!", "Phoenix", JOptionPane.ERROR_MESSAGE);
						lblProcessingTop.setVisible(false);
						lblProcessingBot.setVisible(false);
						lblProcessingText.setVisible(false);
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Files are Safe Now!", "Phoenix", JOptionPane.INFORMATION_MESSAGE);
					lblProcessingTop.setVisible(false);
					lblProcessingBot.setVisible(false);
					lblProcessingText.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Failed to Secure your files!", "Phoenix", JOptionPane.ERROR_MESSAGE);
					lblProcessingTop.setVisible(false);
					lblProcessingBot.setVisible(false);
					lblProcessingText.setVisible(false);
				}
			}
		});
		
		
		btnMakeUsb.setBounds(132, 220, 95, 28);
		panel.add(btnMakeUsb);
		btnMakeUsb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JButton btnClear = new JButton("Clear");
		btnClear.setForeground(Color.WHITE);
		btnClear.setBackground(Color.DARK_GRAY);
		btnClear.setBounds(359, 220, 95, 28);
		panel.add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dateChooser.getDateEditor().setDate(null);
				lblUSBChecked.setVisible(false);
				lblFileChecked.setVisible(false);
			}
		});
		btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblAbout = new JLabel("About");
		lblAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AboutUI aboutWindow = new AboutUI();
				aboutWindow.setUndecorated(true);
				aboutWindow.setVisible(true);
			}
		});
		lblAbout.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblAbout.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAbout.setForeground(Color.LIGHT_GRAY);
		lblAbout.setBounds(537, 366, 55, 16);
		contentPane.add(lblAbout);
		
		btnSelectFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Choose files to secure");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(fileChooser.showOpenDialog(btnSelectFiles) == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					filePath = file.getAbsolutePath();
					lblFileChecked.setVisible(true);
					System.out.println(filePath.toString());
				}
				else
					JOptionPane.showMessageDialog(null, "No files selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
