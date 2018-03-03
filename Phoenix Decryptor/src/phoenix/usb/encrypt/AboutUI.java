package phoenix.usb.encrypt;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class AboutUI extends JFrame {

	private JPanel contentPane;
	public static URL appLogo, companyLogo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			appLogo = UI.class.getResource("/phoenix.png");
			companyLogo = UI.class.getResource("/CompanyLogo.png");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutUI frame = new AboutUI();
					frame.setUndecorated(true);
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
	public AboutUI() {
		setResizable(false);
		/*java.awt.Image image;
		try {
			image = ImageIO.read(appLogo);
			setIconImage(image);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		setTitle("About");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 160);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new LineBorder(new Color(64, 64, 64), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCompanyLogo = new JLabel("");
		lblCompanyLogo.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/CompanyLogo.png")).getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH)));
		//lblCompanyLogo.setIcon(new ImageIcon(new javax.swing.ImageIcon(companyLogo).getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH)));
		lblCompanyLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompanyLogo.setBounds(95, 6, 243, 84);
		contentPane.add(lblCompanyLogo);
		
		JTextArea lblThisIsA = new JTextArea("This is a Software owned by Educo Soft (pvt) Ltd, & developed by Innoviax Inc. (Phoenix USB Security App)");
		lblThisIsA.setBackground(Color.WHITE);
		lblThisIsA.setWrapStyleWord(true);
		lblThisIsA.setBorder(BorderFactory.createEmptyBorder());
		lblThisIsA.setLineWrap(true);
		lblThisIsA.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblThisIsA.setBounds(26, 92, 381, 50);
		contentPane.add(lblThisIsA);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				closeWindow();
			}
		});
		lblX.setForeground(Color.RED);
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setBounds(430, 5, 15, 16);
		contentPane.add(lblX);
	}
	
	public void closeWindow()
	{
		super.dispose();
	}

}
