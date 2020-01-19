package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import oop_utils.OOP_Point3D;

public class KML_Logger {
	private String path = "";
	private String fileName ="";
	
	public List<String> ss = new ArrayList<>(0);
	public static void main(String[] args) {
		
		OOP_Point3D p = new OOP_Point3D(35.206908824085076,32.10545934555314,0.0);
		OOP_Point3D p1 = new OOP_Point3D(35.20689748338955,32.105467681754895,0.0);
		OOP_Point3D p2 = new OOP_Point3D(35.206884882616734,32.10547694420129,0.0);
		OOP_Point3D p3 = new OOP_Point3D(35.20687732215305,32.10548250166914,0.0);
		OOP_Point3D[] po = {p,p1,p2,p3};
		
//		KML_Logger d = new KML_Logger(po);
		
	}

	public  KML_Logger(List<OOP_Point3D> points , String filename) {
	

		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(path + "C:\\Users\\Obador\\eclipse-workspace\\"+filename+".kml"));

			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
			writer.println("<Document>");
			writer.println("<Folder>");
			writer.println("<name/>");
			for(OOP_Point3D p : points) {
				writer.println("<Placemark>");
				writer.println("<name/>");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				writer.println("<TimeStamp>" + 0 + "</TimeStamp>");

				writer.println("<Point>");
				writer.println("<coordinates>" + p.x()  + "," + p.y()+ "," + p.z() + "</coordinates>");
				writer.println("</Point>");
				writer.println("</Placemark>");
			}
			writer.println("</Folder>");
			writer.println("</Document>");
			writer.println("</kml>");
			writer.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}	
}
