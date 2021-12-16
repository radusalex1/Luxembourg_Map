import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class MyPanel extends JPanel {
    private final Vector<Nod> listaNoduri;
    private final Vector<Arc> listaArce;
    private Point sourcePoint=null;
    private Point destinationPoint=null;
    private int latitudinemax=652685;
    private int longitudinemax=5018275;
    private  int longitudinemin=4945029;
    private int latitudinemin = 573929;

    private static final String FILENAME = "hartaLuxembourg.xml";

    public MyPanel()
    {
        listaNoduri=new Vector<Nod>();
        listaArce = new Vector<Arc>();
        readData();
        repaint();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(sourcePoint==null)
                {
                    sourcePoint=e.getPoint();
                    System.out.print(sourcePoint+":"+sourcePoint.x+" "+sourcePoint.y);
                    System.out.println();
                }
                if(sourcePoint!=null)
                {
                    destinationPoint=e.getPoint();
                    System.out.print(destinationPoint+":"+destinationPoint.x + " " + destinationPoint.y);
                    System.out.println();
                }
                if(sourcePoint!=null && destinationPoint!=null)
                {
                    sourcePoint=e.getPoint();
                    destinationPoint=null;
                }
            }
        });
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(Arc arc:listaArce)
        {
            arc.drawArc(g);
        }
    }

    public void readData()
   {
       try
       {
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser saxParser = factory.newSAXParser();
           DefaultHandler handler = new DefaultHandler()
           {

               Integer id,latitude,longitude,arcFrom,arcTo,arcLenght;
               int minLat=573929;
               int maxLat =652685;

               int minLong=4945029;
               int maxLong =5018275;

               //parser starts parsing a specific element inside the document
               public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
               {
                   // System.out.println("Start Element :" + qName);
                   if(qName.equalsIgnoreCase("node"))
                   {
                       id =Integer.parseInt(attributes.getValue("id"));
                       latitude=Integer.parseInt(attributes.getValue("latitude"));
                       longitude=Integer.parseInt(attributes.getValue("longitude"));
                       if(latitude<minLat)
                           minLat=latitude;
                       if(latitude>maxLat)
                           maxLat=latitude;
                       if(longitude<minLong)
                           minLong=longitude;
                       if(longitude>maxLong)
                           maxLong=longitude;
                       Nod nod = new Nod(id,longitude,latitude);
                       listaNoduri.add(nod);
                   }

                   if(qName.equalsIgnoreCase("arc"))
                   {
                       arcFrom =Integer.parseInt(attributes.getValue("from"));
                       arcTo=Integer.parseInt(attributes.getValue("to"));
                       arcLenght=Integer.parseInt(attributes.getValue("length"));

                       Arc arc = new Arc(arcFrom,arcTo,arcLenght);

                       double x=(1270)*(listaNoduri.get(arcFrom).getLongitudine()-longitudinemin)/(longitudinemax-longitudinemin);
                       double y=(670)*(latitudinemax-listaNoduri.get(arcFrom).getLatitudine())/(latitudinemax-latitudinemin);

                       double x1 = (1270)*(listaNoduri.get(arcTo).getLongitudine()-longitudinemin)/(longitudinemax-longitudinemin);
                       double y1 = (670)*(latitudinemax-listaNoduri.get(arcTo).getLatitudine())/(latitudinemax-latitudinemin);

                       arc.setStart(new Point((int)x,(int)y));
                       arc.setEnd(new Point((int)x1,(int)y1));
                       arc.setStartNode(listaNoduri.elementAt(arcFrom));
                       arc.setEndNode(listaNoduri.elementAt(arcTo));
                       listaArce.add(arc);
                   }
               }
               //parser ends parsing the specific element inside the document
           };
           saxParser.parse("hartaLuxembourg.xml", handler);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }
}
