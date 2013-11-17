/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Simen
 */
public class Undecorated {
    
    boolean fullScreen = false;
    boolean drag = false;
    boolean dragRight = false;
    int current;
    int na;
    int counter;
    int size;
    int number;
    int number2;
    boolean canMove = false;
    int size2;
    int xPos;
    int yPos;
    boolean dragDownRight, dragUpLeft, dragUpRight, dragDownLeft;
    int dragWhere;
    int x;
    int y;
    boolean canResize = false;
    final int MIN_SIZE = 200;
    
    private void dragDownRight(java.awt.event.MouseEvent evt, JFrame frame) {
        frame.setSize(evt.getPoint().x, evt.getPoint().y);
    }
    
    private void dragUpRight(java.awt.event.MouseEvent evt, JFrame frame) {
        
        
        if (frame.getSize().height != MIN_SIZE) {
            
            frame.setLocation(frame.getLocation().x, evt.getYOnScreen());
        } else {
            frame.setLocation(frame.getLocation().x, y - frame.getSize().height);
            
        }
        //setLocation(getLocation().x, evt.getYOnScreen());
        frame.setSize(evt.getPoint().x, frame.getSize().height - evt.getPoint().y);
    }
    
    public void formMouseDragged(java.awt.event.MouseEvent evt, JFrame frame) {
        
        if (canMove) {
            frame.setLocation(evt.getLocationOnScreen().x - xPos, evt.getLocationOnScreen().y - yPos);
        }
        
        
        if (dragWhere == 0) {
            if (frame.getSize().width != MIN_SIZE) {
                
                frame.setLocation(evt.getXOnScreen(), frame.getLocation().y);
            } else {
                frame.setLocation(x - frame.getSize().width, frame.getLocation().y);
                
            }
            
            if (frame.getSize().height != MIN_SIZE) {
                
                frame.setLocation(frame.getLocation().x, evt.getYOnScreen());
            } else {
                frame.setLocation(frame.getLocation().x, y - frame.getSize().height);
                
            }



            //System.out.println("here");
            //setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            frame.setSize(frame.getSize().width - evt.getPoint().x, frame.getSize().height - evt.getPoint().y);
        }
        if (dragWhere == 1) {
            
            
            if (frame.getSize().height != MIN_SIZE) {
                
                frame.setLocation(frame.getLocation().x, evt.getYOnScreen());
            } else {
                frame.setLocation(frame.getLocation().x, y - frame.getSize().height);
                
            }
            // setLocation(getLocation().x, evt.getYOnScreen());
            frame.setSize(frame.getSize().width, frame.getSize().height - evt.getPoint().y);
        }
        if (dragWhere == 2) {
            dragUpRight(evt, frame);
        }
        if (dragWhere == 3) {
            frame.setSize(evt.getPoint().x, frame.getSize().height);
        }
        if (dragWhere == 4) {
            dragDownRight(evt, frame);
        }
        if (dragWhere == 5) {
            frame.setSize(frame.getSize().width, evt.getPoint().y);
        }
        if (dragWhere == 6) {
            
            if (frame.getSize().width != MIN_SIZE) {
                
                frame.setLocation(evt.getXOnScreen(), frame.getLocation().y);
            } else {
                frame.setLocation(x - frame.getSize().width, frame.getLocation().y);
                
            }

            // setLocation(evt.getXOnScreen(), getLocation().y);
            frame.setSize(frame.getSize().width - evt.getPoint().x, evt.getPoint().y);
            
        }
        if (dragWhere == 7) {
            
            if (frame.getSize().width != MIN_SIZE) {
                
                frame.setLocation(evt.getXOnScreen(), frame.getLocation().y);
            } else {
                frame.setLocation(x - frame.getSize().width, frame.getLocation().y);
                
            }
            
            frame.setSize(frame.getSize().width - evt.getPoint().x, frame.getSize().height);
        }
        
    }
    
    public void formMouseMoved(java.awt.event.MouseEvent evt, JFrame frame) {
        if (evt.getPoint().y < 50 && !fullScreen) {
            canMove = true;
            
        } else {
            canMove = false;
        }
        
        if (fullScreen) {
            canResize = false;
        } else {
            canResize = true;
        }
        if (canResize) {
            if (evt.getPoint().x > frame.getSize().width - 4 || evt.getPoint().y > frame.getSize().height - 4) {
                
                if (evt.getPoint().y > frame.getSize().height - 4 && evt.getPoint().x > frame.getSize().width - 4) {
                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.NW_RESIZE_CURSOR));
                    //NEderst høyre
                    //dragDownRight = true;
                    dragWhere = 4;
                    canMove = false;
                } else if (evt.getPoint().y < 4) {
                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.NE_RESIZE_CURSOR));
                    //ØVERST HØYRE
                    dragWhere = 2;
                    canMove = false;
                    System.out.println("na");
                    //dragUpRight = true;
                    //drag = false;
                    //dragRight = false;
                    //dragDownRight = false;
                } else if (evt.getPoint().y > frame.getSize().height - 4) {
                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.S_RESIZE_CURSOR));
                    //NEDERST
                    dragWhere = 5;
                    canMove = false;
                } else {
                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.W_RESIZE_CURSOR));
                    
                    canMove = false;
                    dragWhere = 3;
                }
                
            } else if (evt.getPoint().x < 4 || evt.getPoint().y < 4) {
                
                if (evt.getPoint().x < 4 && evt.getPoint().y < 4) {
                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.SE_RESIZE_CURSOR));
                    //ØVERST venstre
                    dragWhere = 0;
                    canMove = false;
                } else if (evt.getPoint().y > frame.getSize().height - 10 && evt.getPoint().x < 4) {
                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.NE_RESIZE_CURSOR));
                    //NEDERST VENSTRE
                    dragWhere = 6;
                    canMove = false;
                } else if (evt.getPoint().y < 4) {
                    //OPPERST

                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.S_RESIZE_CURSOR));
                    dragWhere = 1;
                    canMove = false;
                } else if (evt.getPoint().x < 4) {
                    //System.out.println("hei");
                    frame.setCursor(new java.awt.Cursor(java.awt.Cursor.W_RESIZE_CURSOR));
                    dragWhere = 7;
                    canMove = false;
                }
                
                
                drag = true;
            } else {
                frame.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                // drag = false;
                // dragRight = false;
                // dragDownRight = false;
                // dragUpRight = false;
                number = evt.getXOnScreen();
                number2 = evt.getYOnScreen() + frame.getSize().height;
                xPos = evt.getPoint().x;
                yPos = evt.getPoint().y;
                dragWhere = 10;
            }
        }
        
        
    }

    public void formMouseClicked(java.awt.event.MouseEvent evt) {        
        current = evt.getPoint().x;
        na = evt.getXOnScreen();
        
        
    }    
    
    public void formMouseReleased(java.awt.event.MouseEvent evt, JFrame frame) {        
        size = frame.getSize().width;
        size2 = frame.getSize().height;
        
    }    
    
    public void formMousePressed(java.awt.event.MouseEvent evt, JFrame frame) {        
        x = evt.getXOnScreen() + frame.getSize().width;
        y = evt.getYOnScreen() + frame.getSize().height;
    }    
    
    public void jButton3ActionPerformed(java.awt.event.ActionEvent evt, JFrame frame) {        
        frame.setState(Frame.ICONIFIED);
    }

    public void jButton2ActionPerformed(java.awt.event.ActionEvent evt, JFrame frame, JButton button) {        
        if (!fullScreen) {
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/databaseconnector/min2.png"))); // NOI18N
            fullScreen = true;
        } else {
            frame.setExtendedState(Frame.NORMAL);
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/databaseconnector/resize.png"))); // NOI18N
            fullScreen = false;
            
        }
        
        
    }    
}
