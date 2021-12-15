import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
    //ArrayList<ArrayList<Nod> > aList = new ArrayList<ArrayList<Nod> >(n);
    public MyPanel()
    {
        listaNoduri=new Vector<Nod>();
        listaArce = new Vector<Arc>();
        readData();
        TransformCoordonates();
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
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("node");
            for(int temp=0;temp<list.getLength();temp++)
            {
                Node node = list.item(temp);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    String longitudine = element.getAttribute("longitude");
                    String latitudine = element.getAttribute("latitude");

                   /* if(Integer.parseInt(longitudine)>longitudinemax)
                    {
                        longitudinemax = Integer.parseInt(longitudine);
                    }
                    if(Integer.parseInt(latitudine)>latitudinemax)
                    {
                        latitudinemax=Integer.parseInt(latitudine);
                    }*/

                    Nod nod = new Nod(Integer.parseInt(id),Double.parseDouble(longitudine),Double.parseDouble(latitudine));

                    System.out.print(nod);
                    System.out.println();
                    listaNoduri.add(nod);
                }
            }

            NodeList list1 = doc.getElementsByTagName("arc");
            for(int temp=0;temp<list1.getLength();temp++)
            {
                Node node1 = list1.item(temp);
                if(node1.getNodeType()==Node.ELEMENT_NODE)
                {
                    Element element = (Element) node1;
                    String from = element.getAttribute("from");
                    String to = element.getAttribute("to");
                    String length = element.getAttribute("length");
                    Arc arc = new Arc(Integer.parseInt(from),Integer.parseInt(to),Integer.parseInt(length));
                    System.out.print(arc);
                    System.out.println();
                    for(Nod n : listaNoduri)
                    {
                        if(arc.getSourceId()==n.getId())
                        {
                            arc.setStartNode(n);
                        }
                        if(arc.getDestinationId() == n.getId())
                        {
                            arc.setEndNode(n);
                        }
                    }
                    listaArce.add(arc);
                }
            }

        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void TransformCoordonates()
    {
        for(Nod n:listaNoduri)
        {
            double x=(1920-50) *(n.getLongitudine()-longitudinemin)/(longitudinemax-longitudinemin);
            double y=(1080-50)*(latitudinemax-n.getLatitudine())/(latitudinemax-latitudinemin);
            n.setCoordX(x);
            n.setCoordY(y);
        }

    }

}
