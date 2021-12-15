import java.awt.*;

public class Nod {

    private int id;
    private double longitudine;
    private double latitudine;
    private double coordX;
    private double coordY;
    private static final int size=5;

    public Nod(int id, double longitudine, double latitudine) {
        this.id = id;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }

    public int getId() {
        return id;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }
    public double getMiddleX(){
        return (this.getCoordX()*2+size)/2;
    }
    public double getMiddleY(){
        return (this.getCoordY()*2+size)/2;
    }

   /* public void drawNode(Graphics g)
    {
        g.setColor(Color.gray);
        g.drawOval(coordX,coordY,size,size);
    }*/

}
