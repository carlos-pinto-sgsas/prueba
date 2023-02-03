import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.*;

import java.io.*;

/*
Clase para contruir Edificios 
*/

public class Edificio implements Serializable
{
       Point ini;
       Point end;

       int width;
       int height;

       String num;

       // Variables estaticas:

       static final boolean ESCOGIDO = true;
       static final boolean NO_ESCOGIDO = true;

       boolean select = NO_ESCOGIDO;

       public Edificio(Point ini, Point end, String num)
       {
              this.num = num;
              this.ini = ini;
              this.end = end;
       }

       public Edificio(Point ini, int width, int height, String num)
       {
              this.num = num;
              this.ini = ini;
	      this.width = width;
	      this.height = height;

	      end = new Point(ini.x + width, ini.y + height);
       }


       public void alterColour(Graphics g)
       {
             // Ventana que permita observar los colores.
       }

       public void altertexture(Graphics g)
       {
            //Ventana que permita obtener la textura.
       }
       public Point getIni()
       {
		return ini;
       }
       public Point getEnd()
       {
		return end;
       }

       public void setIni(Point p)
       {
		ini = p;
       }
       public void setEnd(Point p)
       {
		end = p;
       }


       public boolean isOver(Edificio e)
       {
		boolean over = false;

		Point ini1 = e.getIni();
		Point end1 = e.getEnd();

		if(ini.x < end1.x && ini.y < end1.y && end.x > end1.x && end.y > end1.y)
			over = true;		

		if(ini.x < ini1.x && ini1.x < end.x && ini.y < end1.y && end1.y < end.y)
			over = true;		

		if(ini.x < end1.x && ini.y < ini1.y && end1.x < end.x && ini1.y < end.y)
			over = true;		

		if(ini1.x > ini.x && ini1.y > ini.y && ini1.x < end.x && ini1.y < end.y)
			over = true;		

		return over;
       }
       public boolean isOverStreet(Edificio e)
       {
		boolean over = false;

		Point iniD = (Point) ini.clone();
		Point endD = (Point) end.clone();

		iniD.x -= 10;
		iniD.y -= 10;
		endD.x += 10;
		endD.y += 10;

		Point ini1 = e.getIni();
		Point end1 = e.getEnd();

		if(iniD.x < end1.x && iniD.y < end1.y && endD.x > end1.x && endD.y > end1.y)
			over = true;		

		if(iniD.x < ini1.x && ini1.x < endD.x && iniD.y < end1.y && end1.y < endD.y)
			over = true;		

		if(iniD.x < end1.x && iniD.y < ini1.y && end1.x < endD.x && ini1.y < endD.y)
			over = true;		

		if(ini1.x > iniD.x && ini1.y > iniD.y && ini1.x < endD.x && ini1.y < endD.y)
			over = true;		

		return over;
       }
       public String getNum()
       {
		return num;
       }
       public int getHeight()
       {
                return(height);
       }
}

