package org.mql.java.ui;

import javax.swing.*;

import org.mql.java.xml.XMLNode;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.*;

public class UMLViewer extends JFrame {
	private static final long serialVersionUID = 1L;
	private DiagramPanel diagramPanel;
    
    public UMLViewer() {
        super("UML Class Diagram Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        diagramPanel = new DiagramPanel();
        JScrollPane scrollPane = new JScrollPane(diagramPanel);
        add(scrollPane);
        
        JPanel controlPanel = new JPanel();
        JButton zoomIn = new JButton("+");
        JButton zoomOut = new JButton("-");
        JButton resetZoom = new JButton("Reset");
        
        zoomIn.addActionListener(e -> diagramPanel.zoom(1.1));
        zoomOut.addActionListener(e -> diagramPanel.zoom(0.9));
        resetZoom.addActionListener(e -> diagramPanel.resetZoom());
        
        controlPanel.add(zoomOut);
        controlPanel.add(resetZoom);
        controlPanel.add(zoomIn);
        
        add(controlPanel, BorderLayout.NORTH);
    }
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UMLViewer().setVisible(true);
        });
    }
}