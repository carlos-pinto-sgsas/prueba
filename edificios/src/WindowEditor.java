import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class WindowEditor extends JFrame implements WindowListener, ActionListener
{
	Editor editor;
	boolean applet;

       public WindowEditor(boolean applet)
       {
               super("Editor");
               setSize(320, 320);
	       setResizable(false);
               setLocation(0,0);

		this.applet = applet;

               addWindowListener(this);

		editor = new Editor(this);

		getContentPane().setLayout(new BorderLayout());

		Panel panel = new Panel();
		panel.setLayout(new GridLayout(2,3));

                // Creamos los botones adicionar, eliminar, vistaNorte, vista Sur
                // vista este y vista oeste.

                JButton adicionar = new JButton("Adicionar");
        	adicionar.addActionListener(editor); 

                JButton eliminar = new JButton("Eliminar");
        	eliminar.addActionListener(editor);

                JButton vistaN = new JButton("Norte");
                vistaN.addActionListener(editor);

                JButton vistaS = new JButton("Sur");
                vistaS.addActionListener(editor);

                JButton vistaE = new JButton("Este");
                vistaE.addActionListener(editor); 

                JButton vistaO = new JButton("Oeste");
                vistaO.addActionListener(editor); 

		JMenuBar barra = new JMenuBar();
		JMenu menu = new JMenu("Archivo");

		JMenuItem abrir = new JMenuItem("Abrir");
		abrir.addActionListener(this);
		menu.add(abrir);

		JMenuItem guardar = new JMenuItem("Guardar");
		guardar.addActionListener(this);
		menu.add(guardar);

		barra.add(menu);
                
                //Agrega los botones al editor.

                panel.add(adicionar);
                panel.add(vistaN);
                panel.add(vistaS);
		panel.add(eliminar);
                panel.add(vistaE);
                panel.add(vistaO);

		if(!applet)
                	setJMenuBar(barra);

                getContentPane().add("Center",editor);
                getContentPane().add("South",panel);
        }

        public void actionPerformed(ActionEvent e)
        {
		String command = e.getActionCommand();

		if(command.equals("Guardar"))
		{
			FileDialog fd = new FileDialog(this);
			fd.setMode(FileDialog.SAVE);
			fd.show();

			String file = fd.getFile();

			if(file != null)
			{
				editor.guardar(file);
			}
		}

		if(command.equals("Abrir"))
		{
			FileDialog fd = new FileDialog(this);
			fd.setMode(FileDialog.LOAD);
			fd.show();

			String file = fd.getFile();

			if(file != null)
			{
				editor.cargar(file);
			}
		}
        }

        //methods windows:
        public void windowOpened(WindowEvent e)
        {
        }
        public void windowClosing(WindowEvent e)
        {
		if(!applet)
               		System.exit(0); 
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
        public static void main(String arg [])
        {
                WindowEditor ob = new WindowEditor(false);
                ob.setVisible(true);
        }
}
