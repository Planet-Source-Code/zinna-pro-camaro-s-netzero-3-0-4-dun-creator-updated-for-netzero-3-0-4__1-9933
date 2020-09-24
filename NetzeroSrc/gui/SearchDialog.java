// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchDialog.java

package gui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.net.URLEncoder;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Vector;
import nzcom.NZWindow;
import nzcom.OSDetectNative;
import nzcom.ZCast;
import ui.IComponent;
import ui.IContainer;
import ui.Image;
import zpc.ZpComponent;
import zpc.ZpContainer;
import zpc.ZpShell;

// Referenced classes of package gui:
//            LabelControl, ImageControl

public class SearchDialog extends Dialog
    implements MouseListener
{

    public SearchDialog(Frame frame, String s, boolean flag, ZpShell zpshell, NZWindow nzwindow)
    {
        super(frame, s, flag);
        mainPanel = new Panel();
        borderLayout1 = new BorderLayout();
        gb = new GridBagLayout();
        gl = new GridLayout(3, 3, 10, 10);
        searchButton = new ImageControl();
        textField = new TextField();
        topPanel = new Panel();
        middlePanel = new Panel() {

            public Insets insets()
            {
                return new Insets(1, 0, 1, 0);
            }

            public void paint(Graphics g)
            {
                super.paint(g);
                Rectangle rectangle = getBounds();
                g.setColor(Color.black);
                g.drawLine(0, 0, rectangle.width - 1, 0);
                g.drawLine(0, rectangle.height - 1, rectangle.width - 1, rectangle.height - 1);
            }

        };
        hrefPanel = new Panel() {

            public Insets insets()
            {
                return new Insets(6, 6, 6, 6);
            }

        };
        outerPanel = new Panel() {

            public Insets insets()
            {
                return new Insets(7, 6, 7, 6);
            }

            public void paint(Graphics g)
            {
                super.paint(g);
                Rectangle rectangle = getBounds();
                g.setColor(SystemColor.controlShadow);
                g.drawLine(4, 5, 4, rectangle.height - 6);
                g.drawLine(5, 5, rectangle.width - 6, 5);
                g.setColor(SystemColor.controlLtHighlight);
                g.drawLine(4, rectangle.height - 6, rectangle.width - 5, rectangle.height - 6);
                g.drawLine(rectangle.width - 5, 5, rectangle.width - 5, rectangle.height - 6);
                g.setColor(Color.black);
                g.drawLine(5, 6, 5, rectangle.height - 8);
                g.drawLine(6, 6, rectangle.width - 7, 6);
                g.drawLine(5, rectangle.height - 7, rectangle.width - 6, rectangle.height - 7);
                g.drawLine(rectangle.width - 6, 6, rectangle.width - 6, rectangle.height - 8);
            }

        };
        enableEvents(64L);
        try
        {
            uiShell = zpshell;
            nzWindow = nzwindow;
            jbInit();
            add(outerPanel, "Center");
            pack();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static boolean getIsVisible()
    {
        return isDialogVisible;
    }

    public void setVisible(boolean flag)
    {
        isDialogVisible = flag;
        super.setVisible(flag);
    }

    public void dispose()
    {
        super.dispose();
        isDialogVisible = false;
    }

    java.awt.Image getImage(IComponent icomponent, ZpContainer zpcontainer)
    {
        Vector vector = icomponent.getImages();
        if(vector != null)
        {
            if(vector.size() == 0)
                return null;
            Image image = (Image)vector.elementAt(0);
            byte abyte0[] = (byte[])uiShell.getResourceCache().get(image.getImageID());
            if(abyte0 != null)
                return Toolkit.getDefaultToolkit().createImage(abyte0);
            ZCast.displayDebug("ResourceCache image " + image.getImageID() + " not found; needed by " + icomponent.getComponentDesc());
        }
        return null;
    }

    public void setContainer(ZpContainer zpcontainer)
    {
        if(logo == null)
        {
            labels = zpcontainer.getIContainer().listComponent();
            Component acomponent[] = zpcontainer.getComponents();
            logo = getImage(((ZpComponent)acomponent[0]).getIComponent(), zpcontainer);
            search = getImage(((ZpComponent)acomponent[1]).getIComponent(), zpcontainer);
            directions = getImage(((ZpComponent)acomponent[2]).getIComponent(), zpcontainer);
            Long long1 = ((IComponent)labels.elementAt(1)).getActionObjID();
            searchUrl = zpcontainer.getResourceString(long1);
            zpcontainer.remove(acomponent[0]);
            zpcontainer.remove(acomponent[1]);
            zpcontainer.remove(acomponent[2]);
        }
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(new Color(255, 204, 0));
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.gridheight = 1;
        gridbagconstraints.gridwidth = 1;
        gridbagconstraints.insets = new Insets(8, 8, 8, 8);
        ImageControl imagecontrol = new ImageControl();
        imagecontrol.setImage(logo);
        topPanel.add(imagecontrol, gridbagconstraints);
        gridbagconstraints.gridx = 1;
        gridbagconstraints.fill = 2;
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.ipadx = 60;
        topPanel.add(textField, gridbagconstraints);
        gridbagconstraints.ipadx = 0;
        gridbagconstraints.weightx = 0.0D;
        gridbagconstraints.gridx = 2;
        searchButton.setImage(search);
        searchButton.setActionUrl(searchUrl);
        searchButton.setCursor(Cursor.getPredefinedCursor(12));
        searchButton.addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent mouseevent)
            {
                searchButton_mouseReleased(mouseevent);
            }

        });
        topPanel.add(searchButton, gridbagconstraints);
        imagecontrol = new ImageControl();
        imagecontrol.setImage(directions);
        GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
        gridbagconstraints1.insets = new Insets(0, 8, 0, 0);
        gridbagconstraints1.weightx = 1.0D;
        gridbagconstraints1.anchor = 17;
        middlePanel.add(imagecontrol, gridbagconstraints1);
        Font font = getFont();
        Font font1 = new Font(font.getName(), 1, font.getSize());
        Font font2 = new Font(font.getName(), 0, font.getSize() - 1);
        Panel panel = null;
        GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
        gridbagconstraints2.gridheight = 1;
        gridbagconstraints2.gridwidth = 3;
        gridbagconstraints2.anchor = 18;
        gridbagconstraints2.ipady = -6;
        gridbagconstraints2.ipadx = -6;
        GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
        gridbagconstraints3.gridx = 4;
        gridbagconstraints3.gridy = 0;
        gridbagconstraints3.gridwidth = 1;
        gridbagconstraints3.gridheight = 1;
        gridbagconstraints3.weightx = 1.0D;
        int i = 0;
        int j = 0;
        for(int k = 3; k < labels.size(); k++)
        {
            IComponent icomponent = (IComponent)labels.elementAt(k);
            String s = icomponent.getComponentDesc();
            LabelControl labelcontrol = new LabelControl();
            if(s.startsWith("category-"))
            {
                j++;
                Panel panel1 = new Panel();
                panel = new Panel();
                panel.setLayout(new GridBagLayout());
                panel.add(panel1, gridbagconstraints3);
                hrefPanel.add(panel);
                labelcontrol.setFont(font1);
                gridbagconstraints2.gridwidth = 3;
                gridbagconstraints2.gridy = 0;
                i = 0;
                s = s.substring(9);
            } else
            {
                labelcontrol.setFont(font2);
                if(gridbagconstraints2.gridy == 0)
                {
                    i = 0;
                    gridbagconstraints2.gridwidth = 1;
                }
                gridbagconstraints2.gridy = 1;
            }
            gridbagconstraints2.gridx = i++;
            if(panel != null)
                panel.add(labelcontrol, gridbagconstraints2);
            labelcontrol.setForeground(Color.blue);
            Long long2 = icomponent.getActionObjID();
            String s1 = zpcontainer.getResourceString(long2);
            labelcontrol.addMouseListener(this);
            labelcontrol.setActionUrl(s1);
            labelcontrol.setSubstituteToken(s);
            Long long3 = icomponent.getHover();
            String s2 = zpcontainer.getResourceString(long3);
            labelcontrol.setText(s2);
        }

        gl.setRows((j + 1) / 3);
        gl.setVgap(14);
    }

    void jbInit()
        throws Exception
    {
        textField.setBackground(Color.white);
        outerPanel.setLayout(new BorderLayout());
        outerPanel.setBackground(SystemColor.control);
        outerPanel.add(mainPanel, "Center");
        mainPanel.setLayout(gb);
        gl.setRows(0);
        middlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.gridheight = 1;
        gridbagconstraints.gridwidth = 1;
        gridbagconstraints.fill = 2;
        middlePanel.setBackground(new Color(255, 230, 128));
        hrefPanel.setBackground(Color.white);
        hrefPanel.setLayout(gl);
        mainPanel.add(topPanel, gridbagconstraints);
        gridbagconstraints.gridy = 1;
        mainPanel.add(middlePanel, gridbagconstraints);
        gridbagconstraints.gridy = 2;
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.weighty = 1.0D;
        gridbagconstraints.fill = 1;
        mainPanel.add(hrefPanel, gridbagconstraints);
    }

    protected void processWindowEvent(WindowEvent windowevent)
    {
        if(windowevent.getID() == 201)
            cancel();
        super.processWindowEvent(windowevent);
        if(windowevent.getID() == 200)
        {
            textField.requestFocus();
            OSDetectNative.updateProcessIcons();
        }
    }

    void cancel()
    {
        dispose();
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void searchButton_mouseReleased(MouseEvent mouseevent)
    {
        String s = searchButton.getActionUrl();
        int i = s.indexOf("[KEY]");
        if(i > 0)
            s = s.substring(0, i) + URLEncoder.encode(textField.getText()) + s.substring(i + 5);
        nzWindow.showAdUrl(s);
        dispose();
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        String s = ((LabelControl)obj).getActionUrl();
        nzWindow.showAdUrl(s);
        dispose();
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    static java.awt.Image search = null;
    static java.awt.Image logo = null;
    static java.awt.Image directions = null;
    static String searchUrl = null;
    static Vector labels;
    private static boolean isDialogVisible = false;
    ZpShell uiShell;
    NZWindow nzWindow;
    Panel mainPanel;
    BorderLayout borderLayout1;
    GridBagLayout gb;
    GridLayout gl;
    ImageControl searchButton;
    TextField textField;
    Panel topPanel;
    Panel middlePanel;
    Panel hrefPanel;
    Panel outerPanel;

}
