import java.awt.*;
import java.awt.geom.Line2D;

public class Arc {
    private int sourceId;
    private int destinationId;
    private int cost;
    private Nod startNode;
    private Nod endNode;

    private Point start;
    private Point end;

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
    public Point getStart() {
        return start;
    }
    public Point getEnd() {
        return end;
    }

    private boolean selected=false;
    public Nod getStartNode() {
        return startNode;
    }
    public void setStartNode(Nod startNode) {
        this.startNode = startNode;
    }
    public void setEndNode(Nod endNode) {
        this.endNode = endNode;
    }
    public Nod getEndNode() {
        return endNode;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected() {
        return selected;
    }
    public Arc(int sourceId, int destinationId, int cost) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.cost = cost;
    }
    public int getSourceId() {
        return sourceId;
    }
    public int getDestinationId() {
        return destinationId;
    }
    public int getCost() {
        return cost;
    }

    public void drawArc(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if(start!=null)
        {
            if(selected)
            {
                g.setColor(Color.red);
            }
            else
            {
                g.setColor(Color.black);
            }
            Shape l = new Line2D.Double(start.x,start.y,end.x,end.y);
            g2.draw(l);
        }
    }
}
