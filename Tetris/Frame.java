import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;


public class Frame extends JFrame {
	private Screen screen;
	private BauStein[][] stein;
	private long t, t1;
	private int score;
	private int s;
	private Random r;
	private boolean[][] a;
	private boolean[][] b;
	private boolean[][] c;
	private boolean[][] d;
	private int dr;
	private JButton button;
	private boolean p;
	
	private boolean key_up = false;
	private boolean key_down = false;
	private boolean key_left = false;
	private boolean key_right = false;
	private boolean key_space = false;
	
	public Frame(BauStein[][] stein){
		super("Tetris");
		r=new Random();
		dr=0;
		this.stein=stein;
		addKeyListener(new KeyHandler());
		t=System.currentTimeMillis();
		score=0;
		addMouseListener(new UnserListener());
		
		screen = new Screen();
		screen.setBounds(0,100,510,1032);
		add(screen);
	}

	public boolean getUp(){
		if(key_up){
			key_up=false;
			return true;
		}
		return false;
	}
	public boolean getDown(){
		if(key_down){
			key_down=false;
			return true;
		}
		return false;
	}
	public boolean getLeft(){
		if(key_left){
			key_left=false;
			return true;
		}
		return false;
	}
	public boolean getRight(){
		if(key_right){
			key_right=false;
			return true;
		}
		return false;
	}
	public boolean getSpace(){
		if(key_space){
			key_space=false;
			return true;
		}
		return false;
	}
	
	
	public void repaintScreen(){
		screen.repaint();
	}
	
	public boolean pruefen(){
		for(int i=17;i>=0;i--){
			for(int j=9;j>=0;j--){
				if(stein[j][i].getEx())
					if(i==17 || stein[j][i+1].getStop()==true){
						return false;
					}
			}
		}
		return true;
	}
	
	
	public boolean pruefenLeft(){
		for(int i=17;i>=0;i--){
			for(int j=9;j>=0;j--){
				if(stein[j][i].getEx())
					if(j==0 || stein[j-1][i].getStop()==true){
						return false;
					}
			}
		}
		return true;
	}
	
	
	
	public void fallen(){
		if(pruefen()){
			for(int i=17;i>=0;i--){
				for(int j=9;j>=0;j--){
					if(stein[j][i].getEx()==true){
						stein[j][i].setEx(false);
						stein[j][i+1].setEx(true);
					}
				}
			}
		}else{
			for(int i=17;i>=0;i--){
				for(int j=9;j>=0;j--){
					if(stein[j][i].getEx()){
						stein[j][i].setEx(false);
						stein[j][i].setStop(true);
					}
				}
			}
		}
	}

	public void left(){
		if(pruefenLeft()){
			for(int i=17;i>=0;i--){
				for(int j=0;j<=9;j++){
					if(stein[j][i].getEx()==true){
						stein[j][i].setEx(false);
						stein[j-1][i].setEx(true);
					}
				}
			}
		}
		key_left=false;
	}

	public boolean pruefenRight(){
		for(int i=17;i>=0;i--){
			for(int j=9;j>=0;j--){
				if(stein[j][i].getEx())
					if(j==9 || stein[j+1][i].getStop()==true){
						return false;
					}
			}
		}
		return true;
	}
	
	public void right(){
		if(pruefenRight()){
			for(int i=17;i>=0;i--){
				for(int j=9;j>=0;j--){
					if(stein[j][i].getEx()==true){
						stein[j][i].setEx(false);
						stein[j+1][i].setEx(true);
					}
				}
			}
		}
		key_right=false;
	}
	
	public boolean durchsuchen(){
		for(int i=0;i<18;i++){
			for(int j=0;j<10;j++){
				if(stein[j][i].getEx()){
					return false;
				}
			}
		}
		return true;
	}
	
	public int reihePruefen(){
		int x=0;
		for(int i=17;i>=0;i--){
			for(int j=0;j<10;j++){
				if(stein[j][i].getStop()){
					x++;
				}
			}
			if(x==10)return i;
			x=0;
		}
		return -5;
	}
	
	public void reihe(){
		int i = reihePruefen();
		if(i>0){
			for(;i>=0;i--){
				for(int j=0;j<10;j++){
					if(i!=0){
						stein[j][i].setEx(stein[j][i-1].getEx());
						stein[j][i].setStop(stein[j][i-1].getStop());
					}else{
						stein[j][i].setEx(false);
						stein[j][i].setStop(false);
					}
				}
			}
			score++;
		}
	}
	
	public void addd(){
		if(durchsuchen()){
			s=r.nextInt(7);
			dr=0;
			switch(s){
			case 0:
				if(!(stein[4][0].getStop()|stein[4][1].getStop()|stein[5][1].getStop()|stein[6][1].getStop())){
					stein[4][0].setEx(true);
					stein[4][1].setEx(true);
					stein[5][1].setEx(true);
					stein[6][1].setEx(true);
					a = new boolean[4][4];
					a[0][0]=true;
					a[0][1]=true;
					a[1][1]=true;
					a[2][1]=true;
					d = new boolean[4][4];
					d[1][0]=true;
					d[1][1]=true;
					d[1][2]=true;
					d[0][2]=true;
					c = new boolean[4][4];
					c[2][2]=true;
					c[0][1]=true;
					c[1][1]=true;
					c[2][1]=true;
					b = new boolean[4][4];
					b[1][0]=true;
					b[1][1]=true;
					b[1][2]=true;
					b[2][0]=true;
				}else System.exit(0);
				break;
			case 1:
				if(!(stein[4][1].getStop()|stein[5][1].getStop()|stein[6][1].getStop()|stein[6][0].getStop())){
					stein[4][1].setEx(true);
					stein[5][1].setEx(true);
					stein[6][1].setEx(true);
					stein[6][0].setEx(true);
					a = new boolean[4][4];
					a[0][1]=true;
					a[1][1]=true;
					a[2][1]=true;
					a[2][0]=true;
					d = new boolean[4][4];
					d[0][0]=true;
					d[1][0]=true;
					d[1][1]=true;
					d[1][2]=true;
					c = new boolean[4][4];
					c[0][2]=true;
					c[0][1]=true;
					c[1][1]=true;
					c[2][1]=true;
					b = new boolean[4][4];
					b[1][0]=true;
					b[1][1]=true;
					b[1][2]=true;
					b[2][2]=true;
				}else System.exit(0);
				break;
			case 2:
				if(!(stein[5][0].getStop()|stein[4][1].getStop()|stein[5][1].getStop()|stein[6][1].getStop())){
					stein[5][0].setEx(true);
					stein[4][1].setEx(true);
					stein[5][1].setEx(true);
					stein[6][1].setEx(true);
					a = new boolean[4][4];
					a[1][0]=true;
					a[0][1]=true;
					a[1][1]=true;
					a[2][1]=true;
					d = new boolean[4][4];
					d[1][0]=true;
					d[0][1]=true;
					d[1][1]=true;
					d[1][2]=true;
					c = new boolean[4][4];
					c[1][2]=true;
					c[0][1]=true;
					c[1][1]=true;
					c[2][1]=true;
					b = new boolean[4][4];
					b[1][0]=true;
					b[2][1]=true;
					b[1][1]=true;
					b[1][2]=true;
				}else System.exit(0);
				break;
			case 3:
				if(!(stein[4][2].getStop()|stein[5][2].getStop()|stein[5][1].getStop()|stein[6][1].getStop())){
					stein[4][2].setEx(true);
					stein[5][2].setEx(true);
					stein[5][1].setEx(true);
					stein[6][1].setEx(true);
					a = new boolean[4][4];
					a[0][2]=true;
					a[1][2]=true;
					a[1][1]=true;
					a[2][1]=true;
					b = new boolean[4][4];
					b[1][0]=true;
					b[2][2]=true;
					b[1][1]=true;
					b[2][1]=true;
				}else System.exit(0);
				break;
			case 4:
				if(!(stein[4][1].getStop()|stein[5][1].getStop()|stein[5][2].getStop()|stein[6][2].getStop())){
					stein[4][1].setEx(true);
					stein[5][1].setEx(true);
					stein[5][2].setEx(true);
					stein[6][2].setEx(true);
					a = new boolean[4][4];
					a[0][1]=true;
					a[1][1]=true;
					a[1][2]=true;
					a[2][2]=true;
					b = new boolean[4][4];
					b[0][1]=true;
					b[1][1]=true;
					b[0][2]=true;
					b[1][0]=true;
				}else System.exit(0);
				break;
			case 5:
				if(!(stein[3][1].getStop()|stein[4][1].getStop()|stein[5][1].getStop()|stein[6][1].getStop())){
					stein[3][1].setEx(true);
					stein[4][1].setEx(true);
					stein[5][1].setEx(true);
					stein[6][1].setEx(true);
					a = new boolean[4][4];
					a[0][1]=true;
					a[1][1]=true;
					a[2][1]=true;
					a[3][1]=true;
					b = new boolean[4][4];
					b[1][0]=true;
					b[1][1]=true;
					b[1][2]=true;
					b[1][3]=true;
				}else System.exit(0);
				break;
			default:
				if(!(stein[4][0].getStop()|stein[4][1].getStop()|stein[5][1].getStop()|stein[5][0].getStop())){
					stein[4][0].setEx(true);
					stein[4][1].setEx(true);
					stein[5][1].setEx(true);
					stein[5][0].setEx(true);
				}else System.exit(0);
				break;
			}
		}
	}
	public void drehen(){
		if(s<3){
			switch(dr%4){
			case 0:
				wechseln(b,a);
				break;
			case 1:
				wechseln(c,b);
				break;
			case 2:
				wechseln(d,c);
				break;
			case 3:
				wechseln(a,d);
				break;
			default:
				break;
			}
			dr++;
		}else{
			if(s<6){
				switch(dr%4){
				case 0:
					wechseln(b,a);
					break;
				case 1:
					wechseln(a,b);
					break;
				default:
					break;
				}
			}
			dr++;
		}
	}
	public void wechseln(boolean[][] a,boolean[][] b){
		int pos_x=-10;
		int pos_y=-10;
		switch(s){
		case 0:
			for(int i=0;i<18;i++){
				for(int j=0;j<10;j++){
					if(stein[j][i].getEx()){
						switch(dr%4){
						case 0:
							pos_x=j;
							pos_y=i;
							j=10;
							i=18;
							break;
						case 1:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						case 2:
							pos_x=j;
							pos_y=i-1;
							j=10;
							i=18;
							break;
						case 3:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						default:
							break;
						}
					}
				}
			}

			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i] == true && stein[j+pos_x][i+pos_y].getStop() == true){
						return;
					}
				}
			}
			
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(b[j][i]){
						stein[j+pos_x][i+pos_y].setEx(false);
					}
				}
			}
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i]){
						stein[j+pos_x][i+pos_y].setEx(true);
					}
				}
			}
			break;
		case 1:
			for(int i=0;i<18;i++){
				for(int j=0;j<10;j++){
					if(stein[j][i].getEx()){
						switch(dr%4){
						case 0:
							pos_x=j-2;
							pos_y=i;
							j=10;
							i=18;
							break;
						case 1:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						case 2:
							pos_x=j;
							pos_y=i-1;
							j=10;
							i=18;
							break;
						case 3:
							pos_x=j;
							pos_y=i;
							j=10;
							i=18;
							break;
						default:
							break;
						}
					}
				}
			}

			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i] && stein[j+pos_x][i+pos_y].getStop()){
						return;
					}
				}
			}
			
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(b[j][i]){
						stein[j+pos_x][i+pos_y].setEx(false);
					}
				}
			}
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i]){
						stein[j+pos_x][i+pos_y].setEx(true);
					}
				}
			}
			break;
		case 2:
			for(int i=0;i<18;i++){
				for(int j=0;j<10;j++){
					if(stein[j][i].getEx()){
						switch(dr%4){
						case 0:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						case 1:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						case 2:
							pos_x=j;
							pos_y=i-1;
							j=10;
							i=18;
							break;
						case 3:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						default:
							break;
						}
					}
				}
			}

			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i] == true && stein[j+pos_x][i+pos_y].getStop() == true){
						return;
					}
				}
			}
			
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(b[j][i]){
						stein[j+pos_x][i+pos_y].setEx(false);
					}
				}
			}
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i]){
						stein[j+pos_x][i+pos_y].setEx(true);
					}
				}
			}
			break;
		case 3:
			for(int i=0;i<18;i++){
				for(int j=0;j<10;j++){
					if(stein[j][i].getEx()){
						switch(dr%2){
						case 0:
							pos_x=j-1;
							pos_y=i-1;
							j=10;
							i=18;
							break;
						case 1:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						default:
							break;
						}
					}
				}
			}

			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i] == true && stein[j+pos_x][i+pos_y].getStop() == true){
						return;
					}
				}
			}
			
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(b[j][i]){
						stein[j+pos_x][i+pos_y].setEx(false);
					}
				}
			}
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i]){
						stein[j+pos_x][i+pos_y].setEx(true);
					}
				}
			}
			break;
		case 4:
			for(int i=0;i<18;i++){
				for(int j=0;j<10;j++){
					if(stein[j][i].getEx()){
						switch(dr%2){
						case 0:
							pos_x=j;
							pos_y=i-1;
							j=10;
							i=18;
							break;
						case 1:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						default:
							break;
						}
					}
				}
			}

			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i] == true && stein[j+pos_x][i+pos_y].getStop() == true){
						return;
					}
				}
			}
			
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(b[j][i]){
						stein[j+pos_x][i+pos_y].setEx(false);
					}
				}
			}
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i]){
						stein[j+pos_x][i+pos_y].setEx(true);
					}
				}
			}
			break;
		case 5:
			for(int i=0;i<18;i++){
				for(int j=0;j<10;j++){
					if(stein[j][i].getEx()){
						switch(dr%2){
						case 0:
							pos_x=j;
							pos_y=i-1;
							j=10;
							i=18;
							break;
						case 1:
							pos_x=j-1;
							pos_y=i;
							j=10;
							i=18;
							break;
						default:
							break;
						}
					}
				}
			}

			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i] == true && stein[j+pos_x][i+pos_y].getStop() == true){
						return;
					}
				}
			}
			
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(b[j][i]){
						stein[j+pos_x][i+pos_y].setEx(false);
					}
				}
			}
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[j][i]){
						stein[j+pos_x][i+pos_y].setEx(true);
					}
				}
			}
			break;
		default:
			break;
		}
	}
	
	
	
	
	
	public class Screen extends JComponent{
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			if(p){
				key_down=false;
				key_left=false;
				key_right=false;
				key_up=false;
				key_space=false;
				t=System.currentTimeMillis();
				g.drawString("pausiert", 255, 45);
			}
			addd();
			t1=System.currentTimeMillis();
			if(((t1-t) > 1000.0)||key_down){
				fallen();
				t=System.currentTimeMillis();
				key_down=false;
			}
			if(key_up){
				try{
					drehen();
				}
				catch(ArrayIndexOutOfBoundsException e){
					
				}
				key_up=false;
			}
			if(key_left)left();
			if(key_right)right();
			if(key_space){
				if(pruefen()){
					while(pruefen()){
						fallen();
					}
				}
				key_space=false;
			}
			reihe();
			g.setColor(Color.black);
			g.drawString(""+score, 10, 25);
			for(int i=1;i<19;i++){
				for(int j=0;j<10;j++){
					if(stein[j][i-1].getEx()==false){
						g.setColor(Color.GRAY);
						g.fillRect((j*50), (i*50), 50, 50);
						g.setColor(Color.WHITE);
						g.drawRect(j*50, i*50, 50, 50);
					}else{
						g.setColor(Color.RED);
						g.fillRect((j*50), (i*50), 50, 50);
						g.setColor(Color.WHITE);
						g.drawRect(j*50, i*50, 50, 50);
					}
					if(stein[j][i-1].getStop()==true){
						g.setColor(Color.ORANGE);
						g.fillRect((j*50), (i*50), 50, 50);
						g.setColor(Color.WHITE);
						g.drawRect(j*50, i*50, 50, 50);
					}
				}
			}
			
		}
	}
	
	private class UnserListener implements MouseListener{
		
		UnserListener(){}
		
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1)p=!p;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	
	private class KeyHandler implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP)key_up = true;
			if(e.getKeyCode() == KeyEvent.VK_DOWN)key_down = true;
			if(e.getKeyCode() == KeyEvent.VK_LEFT)key_left = true;
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)key_right = true;
			if(e.getKeyCode() == KeyEvent.VK_SPACE)key_space = true;
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP)key_up = false;
			if(e.getKeyCode() == KeyEvent.VK_DOWN)key_down = false;
			if(e.getKeyCode() == KeyEvent.VK_LEFT)key_left = false;
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)key_right = false;
			if(e.getKeyCode() == KeyEvent.VK_SPACE)key_space = false;
			
		}

		
		//unnötig
		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
}
