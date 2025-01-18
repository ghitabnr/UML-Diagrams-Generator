package org.mql.java.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.mql.java.xml.XMLNode;

class DiagramPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private double scale = 0.73;
    private Map<String, Rectangle2D> classBoxes = new HashMap<>();
    private Map<String, Point2D> classPositions = new HashMap<>();
    private XMLNode projectData;
    
    public DiagramPanel() {
        setPreferredSize(new Dimension(3000, 2000));
        loadProjectData();
        calculateClassPositions();
        
        MouseAdapter ma = new MouseAdapter() {
            private Point start;
            
            public void mousePressed(MouseEvent e) {
                start = e.getPoint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (start != null) {
                    Point p = e.getPoint();
                    int dx = p.x - start.x;
                    int dy = p.y - start.y;
                    start = p;
                    repaint();
                }
            }
        };
        
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }
    
    private void loadProjectData() {
        try {
            projectData = new XMLNode("resources/project.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void calculateClassPositions() {
        int x = 50;
        int y = 50;
        int maxHeight = 0;

        for (XMLNode packageNode : projectData.getChildren()) {
            String packageName = packageNode.getAttribute("name");

            for (XMLNode classNode : packageNode.getChildren()) {
                String className = classNode.getAttribute("name");
                Rectangle2D bounds = calculateClassBounds(classNode);

                if (x + bounds.getWidth() > 2800) {
                    x = 50;
                    y += maxHeight + 100;
                    maxHeight = 0;
                }

                classBoxes.put(className, bounds);
                classPositions.put(className, new Point2D.Double(x, y));

                maxHeight = Math.max(maxHeight, (int)bounds.getHeight());
                x += bounds.getWidth() + 100;
            }

            y += maxHeight + 150;
            x = 50;
            maxHeight = 0;
        }
    }
    
    private Rectangle2D calculateClassBounds(XMLNode classNode) {
        FontMetrics fm = getFontMetrics(getFont());
        int width = fm.stringWidth(classNode.getAttribute("name")) + 20;
        int height = 30;
        
        XMLNode fieldsNode = classNode.getChild("fields");
        if (fieldsNode != null) {
            for (XMLNode field : fieldsNode.getChildren()) {
                width = Math.max(width, 
                    fm.stringWidth(field.getAttribute("modifier") + " " + 
                    field.getAttribute("name") + ": " + 
                    field.getAttribute("type")) + 20);
                height += 20;
            }
        }
        
        XMLNode methodsNode = classNode.getChild("methods");
        if (methodsNode != null) {
            for (XMLNode method : methodsNode.getChildren()) {
                width = Math.max(width, 
                    fm.stringWidth(method.getAttribute("modifier") + " " + 
                    method.getAttribute("name") + "(): " + 
                    method.getAttribute("type")) + 20);
                height += 20;
            }
        }
        
        return new Rectangle2D.Double(0, 0, width, height + 40);
    }
    
    public void zoom(double factor) {
        scale *= factor;
        repaint();
    }
    
    public void resetZoom() {
        scale = 0.73;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.scale(scale, scale);
        
        for (XMLNode packageNode : projectData.getChildren()) {
            drawPackage(g2d, packageNode);
        }
        
        drawRelationships(g2d);
    }
    
    private void drawPackage(Graphics2D g2d, XMLNode packageNode) {
        String packageName = packageNode.getAttribute("name");
        
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = 0, maxY = 0;
        
        for (XMLNode classNode : packageNode.getChildren()) {
            String className = classNode.getAttribute("name");
            Point2D pos = classPositions.get(className);
            Rectangle2D bounds = classBoxes.get(className);
            
            if (pos != null && bounds != null) {
                minX = Math.min(minX, pos.getX());
                minY = Math.min(minY, pos.getY());
                maxX = Math.max(maxX, pos.getX() + bounds.getWidth());
                maxY = Math.max(maxY, pos.getY() + bounds.getHeight());
            }
        }
        
        g2d.setColor(new Color(240, 240, 255));
        g2d.fill(new Rectangle2D.Double(minX - 20, minY - 40, 
                                      maxX - minX + 40, maxY - minY + 60));
        g2d.setColor(Color.BLACK);
        g2d.draw(new Rectangle2D.Double(minX - 20, minY - 40, 
                                      maxX - minX + 40, maxY - minY + 60));
        g2d.drawString(packageName, (float)minX, (float)minY - 25);
        
        for (XMLNode classNode : packageNode.getChildren()) {
            drawClass(g2d, classNode);
        }
    }
    
    private void drawClass(Graphics2D g2d, XMLNode classNode) {
        String className = classNode.getAttribute("name");
        Point2D pos = classPositions.get(className);
        Rectangle2D bounds = classBoxes.get(className);
        
        if (pos == null || bounds == null) return;
        
        g2d.setColor(Color.WHITE);
        g2d.fill(new Rectangle2D.Double(pos.getX(), pos.getY(), 
                                      bounds.getWidth(), bounds.getHeight()));
        g2d.setColor(Color.BLACK);
        g2d.draw(new Rectangle2D.Double(pos.getX(), pos.getY(), 
                                      bounds.getWidth(), bounds.getHeight()));
        
        g2d.drawString(className, (float)pos.getX() + 10, 
                      (float)pos.getY() + 20);
        
        g2d.drawLine((int)pos.getX(), (int)pos.getY() + 30, 
                    (int)(pos.getX() + bounds.getWidth()), 
                    (int)pos.getY() + 30);
        
        int y = (int)pos.getY() + 50;
        XMLNode fieldsNode = classNode.getChild("fields");
        if (fieldsNode != null) {
            for (XMLNode field : fieldsNode.getChildren()) {
                String fieldStr = field.getAttribute("modifier") + " " + 
                                field.getAttribute("name") + ": " + 
                                field.getAttribute("type");
                g2d.drawString(fieldStr, (float)pos.getX() + 10, y);
                y += 20;
            }
        }
        
        g2d.drawLine((int)pos.getX(), y - 10, 
                    (int)(pos.getX() + bounds.getWidth()), y - 10);
        
        XMLNode methodsNode = classNode.getChild("methods");
        if (methodsNode != null) {
            for (XMLNode method : methodsNode.getChildren()) {
                String methodStr = method.getAttribute("modifier") + " " + 
                                 method.getAttribute("name") + "(): " + 
                                 method.getAttribute("type");
                g2d.drawString(methodStr, (float)pos.getX() + 10, y);
                y += 20;
            }
        }
    }
    
    private void drawRelationships(Graphics2D g2d) {
        for (XMLNode packageNode : projectData.getChildren()) {
            for (XMLNode classNode : packageNode.getChildren()) {
                XMLNode relationsNode = classNode.getChild("relations");
                if (relationsNode != null) {
                    for (XMLNode relation : relationsNode.getChildren()) {
                        drawRelation(g2d, classNode.getAttribute("name"),
                                   relation.getAttribute("classe"),
                                   relation.getValue());
                    }
                }
            }
        }
    }
    
    private void drawRelation(Graphics2D g2d, String from, String to, String type) {
        Point2D fromPos = classPositions.get(from);
        Point2D toPos = classPositions.get(to);
        Rectangle2D fromBounds = classBoxes.get(from);
        Rectangle2D toBounds = classBoxes.get(to);

        if (fromPos == null || toPos == null || 
            fromBounds == null || toBounds == null) return;

        Point2D fromCenter = new Point2D.Double(
            fromPos.getX() + fromBounds.getWidth() / 2,
            fromPos.getY() + fromBounds.getHeight() / 2);
        Point2D toCenter = new Point2D.Double(
            toPos.getX() + toBounds.getWidth() / 2,
            toPos.getY() + toBounds.getHeight() / 2);

        double angle = Math.atan2(toCenter.getY() - fromCenter.getY(),
                                 toCenter.getX() - fromCenter.getX());

        Point2D p1 = getIntersectionPoint(fromCenter, fromBounds, angle);
        Point2D p2 = getIntersectionPoint(toCenter, toBounds, angle + Math.PI);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1)); 
        g2d.draw(new Line2D.Double(p1, p2));

        if ("extension".equals(type)) {
            drawInheritanceArrow(g2d, p1, p2);
        } else if ("implementation".equals(type)) {
        	drawImplementationArrow(g2d, p1, p2); 
        } else if ("Aggregation".equals(type)) {
            drawDiamond(g2d, p1, p2, false); 
        } else if ("Composition".equals(type)) {
            drawDiamond(g2d, p1, p2, true);
        }
    }
    private Point2D getIntersectionPoint(Point2D center, Rectangle2D bounds, double angle) {
        double cx = center.getX();
        double cy = center.getY();
        double halfWidth = bounds.getWidth() / 2;
        double halfHeight = bounds.getHeight() / 2;

        double dx = Math.cos(angle);
        double dy = Math.sin(angle);

        double tx = (dx > 0) ? halfWidth : -halfWidth;
        double ty = (dy > 0) ? halfHeight : -halfHeight;

        double x = cx + tx;
        double y = cy + ty;

        return new Point2D.Double(x, y);
    }
     
    private void drawDiamond(Graphics2D g2, Point2D from, Point2D to, boolean filled) {
        int diamondSize = 10;
        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();
        double length = Math.sqrt(dx * dx + dy * dy);
        double unitDx = dx / length;
        double unitDy = dy / length;

        int centerX = (int) (from.getX() + unitDx * diamondSize);
        int centerY = (int) (from.getY() + unitDy * diamondSize);

        int topX = centerX;
        int topY = centerY - diamondSize;
        int leftX = centerX - diamondSize;
        int leftY = centerY;
        int bottomX = centerX;
        int bottomY = centerY + diamondSize;
        int rightX = centerX + diamondSize;
        int rightY = centerY;

        Polygon diamond = new Polygon(
            new int[]{topX, leftX, bottomX, rightX},
            new int[]{topY, leftY, bottomY, rightY},
            4
        );

        double angle = Math.atan2(dy, dx);
        AffineTransform transform = new AffineTransform();
        transform.setToRotation(angle, centerX, centerY);

        Shape rotatedDiamond = transform.createTransformedShape(diamond);

        if (filled) {
            g2.setColor(Color.BLACK); 
            g2.fill(rotatedDiamond);
        } else {
            g2.setColor(Color.WHITE); 
            g2.fill(rotatedDiamond);
        }

        g2.setColor(Color.BLACK);
        g2.draw(rotatedDiamond);
    }
    
    
    private void drawInheritanceArrow(Graphics2D g2d, Point2D from, 
                                    Point2D to) {
        double angle = Math.atan2(to.getY() - from.getY(), 
                                to.getX() - from.getX());
        int arrowSize = 10;
        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(to.getX(), to.getY());
        path.lineTo(to.getX() - arrowSize * Math.cos(angle - Math.PI/6),
                   to.getY() - arrowSize * Math.sin(angle - Math.PI/6));
        path.lineTo(to.getX() - arrowSize * Math.cos(angle + Math.PI/6),
                   to.getY() - arrowSize * Math.sin(angle + Math.PI/6));
        path.closePath();
        
        g2d.fill(path);
    }
    
    private void drawImplementationArrow(Graphics2D g2d, Point2D from, Point2D to) {
        Stroke originalStroke = g2d.getStroke();
        
        float[] dash = { 3.0f, 3.0f };  
        BasicStroke dottedStroke = new BasicStroke(
            1.0f,                   
            BasicStroke.CAP_BUTT,   
            BasicStroke.JOIN_BEVEL, 
            0.0f,                   
            dash,                   
            0.0f                    
        );
        
        g2d.setStroke(dottedStroke);
        
        g2d.drawLine((int)from.getX(), (int)from.getY(), (int)to.getX(), (int)to.getY());
        
        g2d.setStroke(originalStroke);
        
        double angle = Math.atan2(to.getY() - from.getY(), to.getX() - from.getX());
        int arrowSize = 10;
        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(to.getX(), to.getY());
        path.lineTo(to.getX() - arrowSize * Math.cos(angle - Math.PI/6),
                    to.getY() - arrowSize * Math.sin(angle - Math.PI/6));
        path.lineTo(to.getX() - arrowSize * Math.cos(angle + Math.PI/6),
                    to.getY() - arrowSize * Math.sin(angle + Math.PI/6));
        path.closePath();
        
        g2d.fill(path);
    }
}