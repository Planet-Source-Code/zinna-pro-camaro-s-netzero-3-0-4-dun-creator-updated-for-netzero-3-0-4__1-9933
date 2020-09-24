// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NZButton.java

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import nzcom.ZCast;

public class NZButton extends Component
    implements MouseListener, FocusListener
{

    public NZButton()
    {
        upImage = null;
        downImage = null;
        hoverImage = null;
        disabledImage = null;
        currentImage = null;
        enabled = true;
        actionListener = null;
        actionCommand = "";
        mouseOver = false;
        hasFocus = false;
        enableEvents(8L);
        addMouseListener(this);
        addFocusListener(this);
    }

    public NZButton(String s)
    {
        this();
        if(s == null)
        {
            return;
        } else
        {
            setName(s);
            setActionCommand(s);
            setUpImage(s);
            setDownImage(s);
            setHoverImage(s);
            setDisabledImage(s);
            setSize(getPreferredSize());
            return;
        }
    }

    public synchronized void addActionListener(ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, actionlistener);
    }

    public void cleanup()
    {
        if(upImage != null)
            upImage.flush();
        if(downImage != null)
            downImage.flush();
        if(hoverImage != null)
            hoverImage.flush();
        if(disabledImage != null)
            disabledImage.flush();
        if(currentImage != null)
            currentImage.flush();
        upImage = downImage = hoverImage = disabledImage = currentImage = null;
    }

    public void focusGained(FocusEvent focusevent)
    {
        hasFocus = true;
        if(hoverImage != null)
        {
            currentImage = hoverImage;
            repaint();
        }
    }

    public void focusLost(FocusEvent focusevent)
    {
        hasFocus = false;
        if(!mouseOver)
        {
            currentImage = upImage;
            repaint();
        }
    }

    public String getActionCommand()
    {
        return actionCommand;
    }

    public Dimension getPreferredSize()
    {
        if(currentImage != null)
        {
            Dimension dimension = new Dimension(currentImage.getWidth(this), currentImage.getHeight(this));
            return dimension;
        } else
        {
            return new Dimension(0, 0);
        }
    }

    public Image getUpImage()
    {
        return upImage;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean isFocusTraversable()
    {
        return enabled;
    }

    private Image loadImage(String s)
    {
        Image image = null;
        try
        {
            MediaTracker mediatracker = new MediaTracker(this);
            image = Toolkit.getDefaultToolkit().getImage(s);
            mediatracker.addImage(image, 0);
            mediatracker.waitForID(0);
        }
        catch(Exception exception)
        {
            ZCast.displayDebug(exception);
        }
        return image;
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
        mouseOver = true;
        if(enabled && hoverImage != null)
        {
            currentImage = hoverImage;
            repaint();
        }
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        mouseOver = false;
        if(enabled && !hasFocus)
        {
            currentImage = upImage;
            repaint();
        }
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        if(enabled && downImage != null)
        {
            currentImage = downImage;
            repaint();
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        if(enabled)
        {
            if(currentImage == downImage)
                processAction();
            currentImage = upImage;
            repaint();
        }
    }

    public void paint(Graphics g)
    {
        if(currentImage != null)
        {
            if(!enabled && disabledImage != null)
                g.drawImage(disabledImage, 0, 0, this);
            else
                g.drawImage(currentImage, 0, 0, this);
        } else
        {
            ZCast.displayDebug("Current Image = null!");
        }
    }

    public void processAction()
    {
        if(actionListener != null)
            try
            {
                actionListener.actionPerformed(new ActionEvent(this, 1001, getActionCommand()));
            }
            catch(Exception exception)
            {
                ZCast.displayDebug(exception);
            }
    }

    protected void processKeyEvent(KeyEvent keyevent)
    {
        boolean flag = false;
        if(keyevent.getKeyCode() == 32 || keyevent.getKeyCode() == 10)
        {
            if(keyevent.getID() == 401)
                mousePressed(null);
            else
            if(keyevent.getID() == 402)
                mouseReleased(null);
            keyevent.consume();
            return;
        } else
        {
            super.processKeyEvent(keyevent);
            return;
        }
    }

    public synchronized void removeActionListener(ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.remove(actionListener, actionlistener);
    }

    public void setActionCommand(String s)
    {
        actionCommand = s;
    }

    public void setDisabledImage(String s)
    {
        String s1 = "images/buttons/" + s + "_dim.gif";
        File file = new File(s1);
        if(file.exists())
            disabledImage = loadImage(s1);
    }

    public void setDownImage(String s)
    {
        String s1 = "images/buttons/" + s + "_in.gif";
        File file = new File(s1);
        if(file.exists())
            downImage = loadImage(s1);
    }

    public void setEnabled(boolean flag)
    {
        enabled = flag;
        repaint();
    }

    public void setHoverImage(String s)
    {
        String s1 = "images/buttons/" + s + "_hvr.gif";
        File file = new File(s1);
        if(file.exists())
            hoverImage = loadImage(s1);
    }

    public void setUpImage(String s)
    {
        String s1 = "images/buttons/" + s + "_stc.gif";
        File file = new File(s1);
        if(file.exists())
        {
            upImage = loadImage(s1);
            currentImage = upImage;
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public static final String IMG_PATH = "images/buttons/";
    private Image upImage;
    private Image downImage;
    private Image hoverImage;
    private Image disabledImage;
    private Image currentImage;
    private boolean enabled;
    private ActionListener actionListener;
    private String actionCommand;
    private boolean mouseOver;
    private boolean hasFocus;
    private static int threadCount = 0;

}
