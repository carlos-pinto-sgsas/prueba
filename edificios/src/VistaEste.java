import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class VistaEste extends JFrame implements WindowListener
{
       Point ini;
       Point end;

	Vector edificios;

	static int horizonte = 150;

       public VistaEste(Vector edificios)
       {
               super("Este");
               setSize(160, 160);
               setLocation(490,170);
	       setResizable(false);

		this.edificios = edificios;

               addWindowListener(this);
        }
        
        public void  paint(Graphics g)
        {
		super.paint(g);

		g.setColor(Color.black);

		Dimension d = getSize();

		g.drawLine(0,horizonte,(int) d.getWidth(),horizonte);

		Edificio[] edificio = ordenar();

		for(int i=0; i<edificio.length; i++)
		{
			Edificio ed = edificio[i];

			Point ini = ed.getIni();
			Point end = ed.getEnd();

                        int x = 160 - (end.y / 2);
                        int y = horizonte - (ed.getHeight()/2);
                        int width = (end.y - ini.y) / 2;
			int height = (ed.getHeight()) / 2;

			g.setColor(Color.red);
                        g.fillRect(x,y,width,height);
			g.setColor(Color.black);
                        g.drawRect(x,y,width,height);
                }
        }

	Edificio[] ordenar()
	{
		Edificio[] origen = new Edificio[edificios.size()];

		edificios.copyInto(origen);

                for(int i=0; i<origen.length; i++)
                {
                        for(int j=0; j<origen.length; j++)
                        {
				Edificio uno = origen[i];
				Edificio dos = origen[j];

                                if(uno.getIni().x < dos.getIni().x)
                                {
                                        Edificio tmp = origen[j];
                                        origen[j] = origen[i];
                                        origen[i] = tmp;
                                }
                        }
                }

		return origen;
	}
 
        //methods windows:
        public void windowOpened(WindowEvent e)
        {
        }
        public void windowClosing(WindowEvent e)
        {
               dispose(); 
        }
        public void windowClosed(WindowEvent e)
        {
        }
        public void windowIconified(WindowEvent e)
        {
        }
        public void windowDeiconified(WindowEvent e)
        {
        }
        public void windowActivated(WindowEvent e)
        {
        }
        public void windowDeactivated(WindowEvent e)
        {
        }
}
