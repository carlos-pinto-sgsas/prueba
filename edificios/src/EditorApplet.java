import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditorApplet extends Applet implements ActionListener
{
	WindowEditor editor;

	public EditorApplet()
	{
		editor = new WindowEditor(true);

		setLayout(new BorderLayout());

		JButton boton = new JButton("Editor");
		boton.addActionListener(this);

		add("Center",boton);
	}

	public void actionPerformed(ActionEvent e)
	{
		editor.show();
	}
}
