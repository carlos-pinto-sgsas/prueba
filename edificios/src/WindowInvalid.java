import java.awt.*;
import java.awt.event.*;

public class WindowInvalid extends Dialog  implements ActionListener, WindowListener
{
        WindowInvalid(Frame parent, String msg)
        {
          super(parent,"Window Invalid.",true);

          setLocation(300,250);
          setLayout(new BorderLayout());

          Button aceptar = new Button("Aceptar");
	  aceptar.addActionListener(this);

	  add("Center",new Label(msg));
	  add("South",aceptar);

          addWindowListener(this);

	   pack();
        }

        public void actionPerformed(ActionEvent e)
        {
             String command = e.getActionCommand();
             if(command.equals("Aceptar"))
             {
		dispose();
             }
        }

        //methods windows:
        public void windowOpened(WindowEvent e)
        {
        }
        public void windowClosed(WindowEvent e)
        {
        }
        public void windowClosing(WindowEvent e)
        {
               dispose();
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
