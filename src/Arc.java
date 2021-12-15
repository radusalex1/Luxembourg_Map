import java.awt.*;
import java.awt.geom.Line2D;

public class Arc {
    private int sourceId;
    private int destinationId;
    private int cost;
    private Nod startNode;
    private Nod endNode;

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

        if(startNode!=null)
        {
            if(selected)
            {
                g.setColor(Color.red);
            }
            else
            {
                g.setColor(Color.black);
            }
            Shape l = new Line2D.Double(startNode.getMiddleX(),startNode.getMiddleY(),endNode.getMiddleX(),endNode.getMiddleY());
            g2.draw(l);
        }
    }
}
