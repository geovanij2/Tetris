import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class OuveCliques implements MouseListener {
	TelaEditor a;
	int resulx, resuly;

	OuveCliques(TelaEditor a) {
		this.a = a;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x, y;
		x = e.getX();
		y = e.getY();
		System.out.println("x: " + x + "    y:" + y);
		if (x >= 607 && x <= 651) {
			if (y >= 127 && y <= 168) {
				a.codigo = 5;
			}
			if (y >= 177 && y <= 218) {
				a.codigo = 6;
			}
			if (y >= 229 && y <= 270){
				a.codigo = 2;
			}
		}
		if(x >= 664 && x <= 707){
			if(y >= 97 && y <= 140){
				a.codigo = 1;
			}
			if(y >= 149 && y <= 192){
				a.codigo = 7;
			}
			if(y >= 203 && y <= 246){
				a.codigo = 3;
			}
			if(y >= 255 && y <= 298){
				a.codigo = 4;
			}
		}

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		int x, y;
		x = e.getX();
		y = e.getY();
		resulx = ((x - 268) / 26);
		resuly = ((y - 40) / 26);
	}

	public void mouseReleased(MouseEvent e) {
		int x, y;
		x = e.getX();
		y = e.getY();
		int resultadox = ((x - 268) / 26);
		int resultadoy = ((y - 40) / 26);
		try {
			a.atualiza(resultadox, resultadoy, resulx, resuly);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
