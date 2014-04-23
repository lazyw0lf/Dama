import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class Grafica extends JFrame { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame fm = this;
	JMenuBar menuBar = new JMenuBar();
	JMenu optionsMenu = new JMenu("Opzioni");
	JMenuItem nuovaAction = new JMenuItem("Nuova Partita");
	JMenuItem ricominciaAction = new JMenuItem("Ricomincia Partita");
	private Image im1;
	private Image im2;
	private Image im3;
	private Image im4;
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private GridLayout griglia = new GridLayout(8,8);
	private GridLayout frame = new GridLayout(2,0);
	private JLabel label1 = new JLabel("E' IL TURNO DEI BIANCHI");	
	private JButton[][] grigliabottoni= new JButton[8][8];
	private Giocatore g1;
	private Giocatore g2;
	private Scacchiera s = new Scacchiera(g1,g2);
	


	public Grafica(Giocatore g1,Giocatore g2){ 

		super("Dama 1.0");	
		setResizable(false);
		this.g1=g1;
		this.g2=g2;
		this.s=new Scacchiera(g1,g2);
		int i,j;
		setSize(700,700);
		menuBar.add(optionsMenu);
		nuovaAction.addActionListener(new ListenMenu1(fm,s));
		ricominciaAction.addActionListener(new ListenMenu2(fm,g1,g2,s));
		optionsMenu.add(nuovaAction);
		optionsMenu.add(ricominciaAction);	
		setJMenuBar(menuBar);	
		loadIcons();
		Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
		Dimension frameSize = getSize();
		setLocation((screenSize.width - frameSize.width) / 2,(screenSize.height - frameSize.height) / 2);
		setLayout(frame);
		p1.add(label1);		
		p2.setLayout(griglia);
		setLayout(new BorderLayout());
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);

		for(i=0; i<8; i++){
			for(j=0; j<8; j++){
				JButton b = new JButton("");  		

				if ((i+j)%2==0){
					b.setBackground(new Color(61,43,31));
					if (i>=0 && i<=2){
						b.setIcon(new ImageIcon(im2));
						b.setFocusPainted(false);
						
					}	

					else if (i>=5 && i<=7){
						b.setIcon(new ImageIcon(im1));
						b.setFocusPainted(false);

					}        		   

					b.addActionListener(new ScacchieraGrafica(i,j,s,this));
					p2.add(b);

					grigliabottoni[i][j]=b;


				}
				else{
					b.setBackground(new Color(210,180,140));
					b.addActionListener(new ScacchieraGrafica(i,j,s,this));
					p2.add(b);
					grigliabottoni[i][j]=b; 


				}

			}
		}


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	} 
	
	public JButton getButton(int x, int y){
		return grigliabottoni[x][y];
	}


	private void loadIcons(){
		try {
			im1 = ImageIO.read(new File("images/pedina1.gif")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			im2 = ImageIO.read(new File("images/pedina2.gif")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			im3 = ImageIO.read(new File("images/pedina3.gif")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			im4 = ImageIO.read(new File("images/pedina4.gif")).getScaledInstance(75, 75, Image.SCALE_SMOOTH); //che scosciata questa a destra

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Image getIm1(){
		return im1;
	}
	public Image getIm2(){
		return im2;
	}
	public Image getIm3(){
		return im3;
	}
	public Image getIm4(){
		return im4;
	}

	public void setLabel(){
		if (s.getGiocatore1().getTurn()==false){
			label1.setText("E' IL TURNO DEI NERI");

		}
		else{

			label1.setText("E' IL TURNO DEI BIANCHI");

		}
	}

	public void setVincitore(){
		if (s.getGiocatore1().getPawn()== 0){
			label1.setText("VINCONO I NERI!");

		}
		if (s.getGiocatore2().getPawn()== 0){
			label1.setText("VINCONO I BIANCHI!");

		}
		if (s.getList().size()==0 && s.getGiocatore1().getPawn()>s.getGiocatore2().getPawn()){
			label1.setText("VINCONO I BIANCHI!");
			
		}
		if (s.getList().size()==0 && s.getGiocatore2().getPawn()>s.getGiocatore1().getPawn()){
			label1.setText("VINCONO I NERI!");
			
		}
	}
}

class ListenMenu1 implements ActionListener {

	private JFrame fr;
	private Scacchiera s;
	
	public ListenMenu1(JFrame fr, Scacchiera s){
		this.fr = fr;
		this.s = s;

	}
	public void actionPerformed(ActionEvent e) {

		fr.setVisible(false);
		fr.dispose();
		s.getList().clear();
		
		Start f = new Start();
	}
}

class ListenMenu2 implements ActionListener {

	private JFrame fr;
	private Giocatore g1;
	private Giocatore g2;
	private Scacchiera s;

	public ListenMenu2(JFrame fr, Giocatore g1, Giocatore g2, Scacchiera s){
		this.fr = fr;
		this.g1 = g1;
		this.g2 = g2;
		this.s = s;

	}
	public void actionPerformed(ActionEvent e) {
		fr.setVisible(false);
		fr.dispose();
		s.getList().clear();
		s.getGiocatore1().resetPawn();
		s.getGiocatore2().resetPawn();
		if (s.getGiocatore1().getTurn()==false){
			s.getGiocatore1().setTurn(true);
			s.getGiocatore2().setTurn(false);
		}
		Grafica f = new Grafica(g1,g2);
		

	}
}



