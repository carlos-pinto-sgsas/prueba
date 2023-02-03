import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;

import java.util.Enumeration;
import java.util.Vector;

public class Editor extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{
        JFrame parent;


        //Variables estaticas:

        static final int AGREGAR = 1;
        static final int NO_AGREGAR = 0;
        static final int NUM = 0;
        static final boolean EXISTE = true;
        static final boolean NO_EXISTE = false;
        static final boolean ESCOGIDO = true;
        static final boolean NO_ESCOGIDO = false;

        // En la variable state sera igual a AGREGAR cuando presione el boton
        //agregar. Cuando ya se a editado el rectangulo, retorna al valor NO_AGREGAR. 

        int state = NO_AGREGAR;

        //Se utiliza un vector edificio para guardar todos los
        //edificios dibujados que  se pinten ensima del editor.

        Vector edificio;

        //Se definen vn,vs,ve,vo dependiendo del Frame vista correspondiente,
        // por ejemplo: El marco vn es de tipo VistaNorte que se un Frame.

        //Esta definicio de variables se realiza con el fin de poder hacer un
        //llamado de cada Frame dentro del editor.

        VistaNorte vn;
        VistaSur vs;
        VistaEste ve;
        VistaOeste vo;

        // Los puntos ini y end son los puntos con los que se van ha dibujar
        // los edificos, a traves de la clase drawRect().

        Point ini;
	Point end;

        //El punto anterior permite capurar momentaneamente el punto seleccionadom,
        //es utilizado para el desplazamiento del rectangulo(mas informacion adelante).
        //Y el punto tmpIni es una copia del punto ini, donde permite manipular, las
        //posiciones del rectangulo durante su edicion(mas informacion adelante).
        

        Point anterior;
        Point tmpIni;

        // La variable seleccionado indica el edificio que se encuentra
        // seleccionado.Es decir Cuando el puntero del mouse a realizado el evento
        // Pressedmouse ensima del area interior del  rectangulo escogido. 

        Edificio seleccionado;
	Edificio inicial;

        // String num  coloca el numero del edificio creado dentro del
        // area del rectangulo.El contador me permite sumar el numero de edificios
        // creados. El contador sumara el numero del edificio mientras ya se
        // halla editiado. 
           
        String num;
        int cont = NUM;

        
        //Constructor Editor:
        

        public Editor(JFrame parent)
        {
                super();

                this.parent = parent;

                //Aqui se inicializa el Vector edificio.

                edificio = new Vector();

                //Se hace un llamado a todas las vistas dentro del constructor.

                vn = new VistaNorte(edificio);
        	vs = new VistaSur(edificio);
                ve = new VistaEste(edificio);
                vo = new VistaOeste(edificio);

                // Utilizo un layout absoluto. Le vamos a decir en que
                //posiciones exactamente queremos que se pongan los componentes.

                setLayout(null);

                // Agregamos los eventos del mouse al editor.

                addMouseListener(this);
                addMouseMotionListener(this);

	}
        //////////////////////////////////////////////////////////////////////////////////////////////

	public void guardar(String archivo)
	{
		try
		{
			FileOutputStream file = new FileOutputStream(archivo);

			ObjectOutputStream salida = new ObjectOutputStream(file);
			salida.writeObject(edificio);

			file.flush();
			file.close();
		}
		catch(Exception e)
		{
                        WindowInvalid ob = new WindowInvalid(parent,"No se puede escribir el plano"); 
                        ob.setVisible(true);
		}	
	}

	public void cargar(String archivo)
	{
		try
		{
			FileInputStream file = new FileInputStream(archivo);

			ObjectInputStream entrada = new ObjectInputStream(file);
			Vector tmp = (Vector) entrada.readObject();

			edificio.removeAllElements();

                        for(Enumeration ed = tmp.elements(); ed.hasMoreElements(); )
			{
                                edificio.addElement(ed.nextElement());
			}

			file.close();
			repaint();
               		vn.repaint();
 		        vs.repaint();
       		        ve.repaint();
       		        vo.repaint();
		}
		catch(Exception e)
		{
                        WindowInvalid ob = new WindowInvalid(parent,"No se puede leer el plano"); 
                        ob.setVisible(true);
		}	
	}

        //Utilizamos el metodo paint para pintar los rectangulos en el editor.


        //Utilizamos el metodo paint para pintar los rectangulos en el editor.

        public void paint(Graphics g)
	{	
		super.paint(g);

            // La primera vez que utilizamos los puntos se encuentran en
            //null por eso utilizamos esta condicion para evitar excepciones y ademas
            //la condicion no se cumple, si el boton agregar no es presionado,
            //es decir no permite dibujar nada si state != AGREGAR.

                if(ini != null && end != null && state == AGREGAR)
		{

                        //Se crea un punto tmpIni que es una copia de ini. Esto
                        //con el fin de impedir que se modifiquen los puntos originales del
                        // rectangulo y  tambien permitir dibujar el rectangulo que se encuentra
                        //en edicion en diferentes posiciones.(rectangulo arriba derecha o
                        //rectangulo arriba izquierda o rectangulo abajo izquierda y rectangulo abajo derecha).
                        

                        tmpIni = (Point) ini.clone();

                        //Declaramos el ancho y el alto del rectangulo.

                        int width = end.x - ini.x;
			int height = end.y - ini.y;

                        //Siempre que el rectangulo se encuentre en edicion, es decir
                        //apenas se este construyendo se pintara con un color amarillo su marco.

                        g.setColor(Color.yellow);

                        // La primera decision es cuando tanto el ancho como el alto del
                        //rectangulo son negativos. entoces se intercambia el punto ini por el punto
                        //final del rectangulo.

                        if(width < 0  && height < 0)
			{
				tmpIni = end;
			}
			else
                                // La segunda decision es cuando el ancho del rectangulo es negativo
                                //y el alto es positivo.Entonces se realiza el intercambio de puntos: 
                                 

                                if(width < 0  && height > 0)
				{
					tmpIni.x = end.x;
					tmpIni.y = ini.y;
				}
				else
                                // La tercera desicion es cuando el ancho del rectangulo es positivo
                                //y el alto es negativo. Entonces se realiza el intercambio de puntos: 

                                        if(width > 0  && height < 0)
					{
						tmpIni.x = ini.x;
						tmpIni.y = end.y;
					}


                       // La altura y el ancho del edificio se trabajara con valores positivos.

                        width = Math.abs(width);
			height = Math.abs(height);

                        // Se dibuja el rectangulo con los puntos clonados, esto con el fin
                        //  de poder pintar los puntos que se modificaron.

                        g.drawRect(tmpIni.x,tmpIni.y,width,height);
                }
                //Utilizamos el metodo elements de vector para devolver una referencia a un
                //enumeration que contiene los elementos del vector edificio, despues recorremos
                //el vector edificio para pintar los rectangulos en el editor.

                for(Enumeration en = edificio.elements(); en.hasMoreElements();)
		{
                        //La variable edi toma el valor de cada edificio.

                        Edificio edi = (Edificio) en.nextElement();

                        //La siguiente decision me indica: si  el edificio  se encuentra seleccionado, entonces
                        //dibujara el marco del rectangulo de  color negro, con su correspondiente enumeracion
                        //y altura, en caso contrario, si no esta seleccionado se pintara su borde con azul
                        //tambien con su correspondiente enumeracion y altura. La variable select es
                        //de tipo boolean  que se encuentra definida en la clase edificio y permite determinar
                        //si el edificio se encuentra o no seleccionado.


                        if(!edi.select)
			{
                                g.setColor(Color.black);
                                g.drawRect(edi.ini.x,edi.ini.y, edi.end.x - edi.ini.x,edi.end.y - edi.ini.y);
                                g.drawString(edi.getNum(),edi.ini.x + 5 , edi.ini.y + 10);
                                g.drawString("" + edi.getHeight(), edi.end.x - 15, edi.end.y - 5);
                        }
			else
                        {
                                g.setColor(Color.blue);
                                g.drawRect(edi.ini.x,edi.ini.y,edi.end.x - edi.ini.x,edi.end.y - edi.ini.y);
                                g.drawString(edi.getNum(), edi.ini.x + 5 , edi.ini.y + 10);
                                g.drawString("" + edi.getHeight(), edi.end.x - 15, edi.end.y - 5);
                        }
		}
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        //metodos del mouse:

        public void mouseClicked(MouseEvent e)
	{
        }
        public void mouseDragged(MouseEvent e)
	{

                //seleccionado es un punto  del rectangulo seleccionado.
                //anterior es un punto donde se guarda momentaneamente el punto
                //del edificio que anteriormente estubo seleccionado.
                

                if(seleccionado != null && anterior != null && state == NO_AGREGAR)
                {

                     //El punto p es un punto arbitrario que puede ser o no seleccionado.
                     //El punto p permite hallar la distancia entre el punto del rectangulo
                     //que se quiere desplazar y el punto al que se desea llevar.

                     Point p=new Point(end.x-anterior.x, end.y-anterior.y);

                     //El punto seleccionado es modificado de su posicion hacia el punto
                     //donde se quiere llevar el rectangulo.

                     seleccionado.ini.x = seleccionado.ini.x + p.x;
                     seleccionado.ini.y = seleccionado.ini.y + p.y;
                     seleccionado.end.x = seleccionado.end.x + p.x;
                     seleccionado.end.y = seleccionado.end.y + p.y;
                }
                //anterior toma el punto final end del cuadro desplazado y
                //end recibe el valor de un punto arbitrario del frame editor.

                anterior = end;
		end = e.getPoint();

                // (repintar) hace un llamado a la funcion paint.
                repaint();
	}

        public void mouseEntered(MouseEvent e)
	{
	}
        public void mouseExited(MouseEvent e)
	{
	}
        public void mouseMoved(MouseEvent e)
	{
	}
        public void mousePressed(MouseEvent e)
	{
		anterior = null;

                //Variable que indica si algun edificio se  encuentra sobre el editor.

                boolean alguno = NO_EXISTE;

                //ini recibe el valor de un punto arbitrario del frame editor.

                ini = e.getPoint();


                if(state == NO_AGREGAR)
		{

                        for(Enumeration ed = edificio.elements(); ed.hasMoreElements(); )
			{
                                Edificio edi = (Edificio) ed.nextElement();


                                //Decision que me indica si el punto presionado por el mouse se
                                //encuentra dentro de uno de los rectangulos editados anteriormente.

                                if(ini.x > edi.ini.x && ini.x < edi.end.x && ini.y > edi.ini.y && ini.y < edi.end.y )
				{
                                        //Como el punto presionado se encuentra dentro del area de
                                        //algun rectangulo anteriormente editado, entonces se coloca
                                        //la seleccion del edificio como escogido en caso contrario
                                        //el edificio no ha sido escogido.
                                         
                                        edi.select = ESCOGIDO;

                                        seleccionado = edi;

                                        alguno = EXISTE;
				}
				else
                                        edi.select = NO_ESCOGIDO;
			}

                }

		if(!alguno)
		{
			if(seleccionado != null)
				seleccionado.select = false;

			seleccionado = null;
		}

		if(seleccionado != null)
                        inicial = new Edificio((Point)seleccionado.getIni().clone(),(Point)seleccionado.getEnd().clone(),seleccionado.getNum());

                repaint();
	}
        public void mouseReleased(MouseEvent e)
	{
                boolean adicionar = true;
                anterior = null;
                end = e.getPoint();
                

               if(state == NO_AGREGAR)
               {

                        //asigna a una nueva variable el edificio seleccionado.

                        Edificio ed = seleccionado;

			if(ed != null)
                        {            
                               
                               for(Enumeration  en = edificio.elements(); en.hasMoreElements();)
                               {
                                        Edificio  edAux = (Edificio) en.nextElement();


                                        //La siguiente decision indica si el edificio que se esta editando
                                        //se encuentra o no ensima de otro.Se realiza un llamado al metodo
                                        //"isOver"  que esta en la clase edificio.

                                        if((ed.isOver(edAux) || edAux.isOver(ed))&&(!edAux.select))
                                        {
                                                //Se hace un llamado al constructor de WindowInvalid que es
                                                //un Dialog y es utilizado para mostrar un mensaje  de informacion
                                                //al usuario.
                                                  

                                                WindowInvalid ob = new WindowInvalid(parent,"Edificios se superponen");
                                                ob.setVisible(true);
						seleccionado.setIni(inicial.getIni());
						seleccionado.setEnd(inicial.getEnd());
                                                repaint();
                                        }
					else
                                        //La siguiente decision indica si el edificio que se esta editando
                                        //se encuentra o no sobre la calle.Se realiza un llamado al metodo
                                        //"isOverStreet" que esta en la clase edificio.

                                        if((ed.isOverStreet(edAux) || edAux.isOverStreet(ed))&&(!edAux.select))
                                        {
                                                WindowInvalid ob = new WindowInvalid(parent,"La distancia entre los edificios debe ser mayor");
                                                ob.setVisible(true);
						seleccionado.setIni(inicial.getIni());
						seleccionado.setEnd(inicial.getEnd());
                                                repaint();
                                        }
                                }
			}
               }

               if(state == AGREGAR)
               {

                        //Si el alto y ancho de un edificio es editado menor que 10 pixel
                        //entonces el programa impedira que se pueda crear dicho edificio
                        //debido a su pequea area.

                        if(Math.abs(ini.x - end.x) < 10 ||  Math.abs(ini.y - end.y) < 10)
               		 {
                              //Se hace un llamado al constructor de WindowInvalid que es
                              //un Dialog y es utilizado para mostrar un mensaje  de informacion
                              //al usuario.
                                                  

                              WindowInvalid ob = new WindowInvalid(parent,"Edificio es muy pequeo");
                              ob.setVisible(true);

                              state = NO_AGREGAR;
               		 }

                        int width = Math.abs(end.x - ini.x);
			int height = Math.abs(end.y - ini.y);

			Edificio ed;

       		       ed = new Edificio(tmpIni,width,height,"" + cont);

        		       for(Enumeration  en = edificio.elements(); en.hasMoreElements();)
                               {
                                Edificio  edAux = (Edificio) en.nextElement();

                                 if(ed.isOver(edAux) || edAux.isOver(ed))
                       		 {
                                      WindowInvalid ob = new WindowInvalid(parent,"Los Edificios se superponen");
                      		      ob.setVisible(true);

                                      state = NO_AGREGAR;
                                      adicionar = false;
               		         }
				else
                                if(ed.isOverStreet(edAux) || edAux.isOverStreet(ed))
                       		 {
                         		   WindowInvalid ob = new WindowInvalid(parent,"La distancia entre los edificios debe ser mayor");
                                           ob.setVisible(true);

                                           state = NO_AGREGAR;
                                           adicionar = false;
               		         }
          		     }

			if(adicionar)
			{
			
				ed.select = true;
                             edificio.addElement(ed);

				if(seleccionado != null)
					seleccionado.select = false;

				seleccionado = ed;

                             cont++;
			}
                             state = NO_AGREGAR;
			
               }
               //Se hace un llamado a repaint de todas las vistas y del Editor con
               //el fin de poder repintar las vistas constantemente.


               repaint();
               vn.repaint();
               vs.repaint();
               ve.repaint();
               vo.repaint();
               end = null;
               inicial = null;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        //metodo de botones:

        
        public void actionPerformed(ActionEvent e)
	{
                String command = e.getActionCommand();

                 //hacemos un llamado a la cadena o etiqueta que genero el
                 //  evento.

                if(command.equals("Adicionar"))
		{
                    //Cada vez que se presione el boton adicionar la variable
                    //state sera igual a agregar. Esto con el fin de activar
                    //la posibilidad de poder dibujar los rectangulos sobre el editor.
                    
                    state = AGREGAR;
                }

		if(command.equals("Eliminar"))
		{

                        Vector eliminar = new Vector();

                    //Cada vez que se presione le boton eliminar, el primer for hara
                    //un recorrido sobre el Vector edificio para determinar cual fue el
                    //cuadro seleccionado,he inmediatamente despues se lo agregara a un vector
                    //temporal llamado eliminar.Con este se elimina el edificio que se
                    //encuentra en el vector edificio.
                        

                        for(Enumeration en = edificio.elements(); en.hasMoreElements(); )
			{
                                Edificio r = (Edificio) en.nextElement();
				if(r.select)
                                {
                                   eliminar.addElement(r);
                                }
			}
                        for(Enumeration en = eliminar.elements(); en.hasMoreElements(); )
			{
                                Edificio r = (Edificio) en.nextElement();
                                edificio.remove(r);
			}
                        repaint();
			vn.repaint();
			vs.repaint();
                        ve.repaint();
                        vo.repaint();
		}
                if(command.equals("Norte"))
		{
                       vn.setVisible(true);
                }
                if(command.equals("Sur"))
		{
                       vs.setVisible(true);
                }
                if(command.equals("Este"))
		{
                       ve.setVisible(true);

                }
                if(command.equals("Oeste"))
		{
                      vo.setVisible(true);
                }
        }
}
