
import javax.swing.JFrame;

public class Tetris {
	public static void main(String[] args){
		BauStein[][] stein= new BauStein[10][18];
		for(int i=0;i<18;i++){
			for(int j=0;j<10;j++){
				stein[j][i]= new BauStein();
			}
		}
		
		
		
		
		
		
		
		
		
		
		Frame f = new Frame(stein);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(510,982);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		
		
		
		while(true){
			f.repaintScreen();
			
		}
	}
}
