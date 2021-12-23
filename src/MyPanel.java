import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.swing.*;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.*;

public class MyPanel extends JPanel {
    private final Vector<Nod> listaNoduri;
    private Vector<Arc> listaArce;
    private Point sourcePoint=null;
    private Point destinationPoint=null;
    private final int latitudinemax=652685;
    private final int longitudinemax=5018275;
    private final int longitudinemin=4945029;
    private final int latitudinemin = 573929;
    private List<Integer> route;
    List<List<Arc>> adjList = null;

    private Pair pair = new Pair(new Nod(-1,-1,-1),new Nod(-1,-1,-1));
    private static final String FILENAME = "hartaLuxembourg.xml";

static class Pair{
    private Nod source;
    private Nod destination;
    public Pair(Nod source, Nod destination) {
        this.source = source;
        this.destination = destination;
    }
    public Nod getSource() {
        return source;
    }
    public Nod getDestination() {
        return destination;
    }
}

double getDistance(double x1,double y1,double x2,double y2)
{
    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}

private void GetClosestNodes(Point sourcePoint,Point destinationPoint)
{
    double minSource=Integer.MAX_VALUE;
    Nod source=null;
    Nod dest=null;
    double minDestination=Integer.MAX_VALUE;
    for(Nod n:listaNoduri)
    {
        double d1 = getDistance(n.getCoordX(),n.getCoordY(),sourcePoint.x,sourcePoint.y);
        if(d1<minSource)
        {
            source=n;
            minSource = d1;
        }

        double d2 = getDistance(n.getCoordX(),n.getCoordY(),destinationPoint.x,destinationPoint.y);
        if(d2<minDestination)
        {
            dest=n;
            minDestination = d2;
        }
    }

    pair.destination = dest;
    pair.source = source;

    System.out.print(sourcePoint+" "+source.getCoordX()+" "+source.getCoordY());
    System.out.println();
    System.out.print(destinationPoint+" "+dest.getCoordX()+" "+dest.getCoordY());
    System.out.println();

}
    public void createAdjList()
    {
        for(Arc arc:listaArce)
        {
            adjList.get(arc.getSourceId()).add(arc);
        }
    }
    public void initializeAdjList()
    {
        adjList= new ArrayList<>();

        for(int i=0;i<listaNoduri.size();i++)
        {
            adjList.add(new ArrayList<>());
        }
    }

    public MyPanel()
    {
        listaNoduri=new Vector<Nod>();
        listaArce = new Vector<Arc>();

        readData();

        initializeAdjList();

        createAdjList();

        repaint();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(sourcePoint==null)
                {
                    sourcePoint=e.getPoint();
                   /*system.out.print(sourcePoint+":"+sourcePoint.x+" "+sourcePoint.y);
                    System.out.println();*/
                }
                else {
                    if (sourcePoint != null && destinationPoint==null) {
                        destinationPoint = e.getPoint();

                        GetClosestNodes(sourcePoint, destinationPoint);
                        Dijkstra djk = new Dijkstra(adjList,pair.source.getId(),pair.destination.getId(),listaNoduri.size());
                        djk.findShortestPath();
                        route = djk.getRoute();
                        colorPath(route);
                        repaint();
                        //apelez dijsktra

                        /* System.out.print(destinationPoint+":"+destinationPoint.x + " " + destinationPoint.y);
                        System.out.println();*/
                    } else {
                        if (sourcePoint != null && destinationPoint != null) {
                            sourcePoint = e.getPoint();
                            destinationPoint = null;
                        }
                    }
                }
            }
        });
    }
    public void colorPath(List<Integer> route)
    {
        for(int i=0;i<route.size()-1;i++)
        {
            for(Arc arc:listaArce)
            {
                if(arc.getDestinationId()==route.get(i) && arc.getSourceId()==route.get(i+1))
                {
                    arc.setSelected(true);
                }
            }
        }
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

                       listaNoduri.elementAt(arcFrom).setCoordX(x);
                       listaNoduri.elementAt(arcFrom).setCoordY(y);
                       listaNoduri.elementAt(arcTo).setCoordX(x1);
                       listaNoduri.elementAt(arcTo).setCoordY(y1);

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
