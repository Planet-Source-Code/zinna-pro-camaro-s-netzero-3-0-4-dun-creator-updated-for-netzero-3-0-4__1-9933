// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomizationDialog.java

package ticker.client;

import gui.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import java.util.Vector;
import jclass.bwt.JCContainer;
import jclass.bwt.JCTabManager;
import nzcom.*;
import ticker.*;

// Referenced classes of package ticker.client:
//            ImageGenerator, TickerTransactions, TabPanel

public class CustomizationDialog extends Dialog
{
    class CancelListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            quit();
        }

        CancelListener()
        {
        }
    }

    class SaveListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            saveSelections();
        }

        SaveListener()
        {
        }
    }

    class FrameAdapter extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            quit();
        }

        public void windowOpened(WindowEvent windowevent)
        {
            OSDetectNative.updateProcessIcons();
        }

        FrameAdapter()
        {
        }
    }


    public CustomizationDialog(Frame frame, Vector vector)
    {
        super(frame);
        headers = null;
        parent = null;
        ivjJCTabManager1 = null;
        ivjContentsPane = null;
        ivjPanel1 = null;
        ivjPanel2 = null;
        ivjJCButton1 = null;
        ivjJCButton2 = null;
        headers = vector;
        parent = frame;
        initialize();
    }

    public CustomizationDialog(Frame frame, boolean flag, Vector vector)
    {
        super(frame, flag);
        headers = null;
        parent = null;
        ivjJCTabManager1 = null;
        ivjContentsPane = null;
        ivjPanel1 = null;
        ivjPanel2 = null;
        ivjJCButton1 = null;
        ivjJCButton2 = null;
        headers = vector;
        parent = frame;
        initialize();
    }

    private void addPages()
    {
        if(headers == null)
            return;
        for(int i = 0; i < headers.size();)
            try
            {
                TopicsInformation topicsinformation = (TopicsInformation)headers.elementAt(i);
                TabPanel tabpanel = new TabPanel(topicsinformation);
                getJCTabManager1().addPage(topicsinformation.getHeader().getItemName(), tabpanel);
                i++;
            }
            catch(Exception _ex)
            {
                headers.removeElementAt(i);
            }

    }

    public static void dump(TopicsInformation topicsinformation)
    {
        ZCast.displayDebug("--- topic added ---");
        ZCast.displayDebug("\tname: " + topicsinformation.getHeader().getItemName());
        ZCast.displayDebug("\tsearch status: " + topicsinformation.getSearchStatus());
        ZCast.displayDebug("\ttopic id: " + topicsinformation.getTopicId());
        ZCast.displayDebug("\tupper limit: " + topicsinformation.getUpperLimit());
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                getContentsPane().add(getPanel1(), getPanel1().getName());
                getContentsPane().add(getPanel2(), getPanel2().getName());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private NZButton getJCButton1()
    {
        if(ivjJCButton1 == null)
            try
            {
                ivjJCButton1 = new NZButton("save&exit");
                ivjJCButton1.setLocation(337, 5);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCButton1;
    }

    private NZButton getJCButton2()
    {
        if(ivjJCButton2 == null)
            try
            {
                ivjJCButton2 = new NZButton("cancel");
                ivjJCButton2.setLocation(459, 5);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCButton2;
    }

    private JCTabManager getJCTabManager1()
    {
        if(ivjJCTabManager1 == null)
            try
            {
                ivjJCTabManager1 = new JCTabManager();
                ivjJCTabManager1.setName("JCTabManager1");
                ivjJCTabManager1.setTabStretch(false);
                ivjJCTabManager1.setInsets(new Insets(5, 5, 0, 5));
                ivjJCTabManager1.setCurrentTabFont(new Font("dialog", 0, 12));
                ivjJCTabManager1.setTabShape(0);
                ivjJCTabManager1.setTabResize(false);
                addPages();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCTabManager1;
    }

    private Panel getPanel1()
    {
        if(ivjPanel1 == null)
            try
            {
                ivjPanel1 = new Panel();
                ivjPanel1.setName("Panel1");
                ivjPanel1.setLayout(new BorderLayout());
                ivjPanel1.setBounds(0, 0, 530, 305);
                ivjPanel1.setBackground(NZColor.BACKGROUND);
                ivjPanel1.add(getJCTabManager1(), "Center");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel1;
    }

    private Panel getPanel2()
    {
        if(ivjPanel2 == null)
            try
            {
                ivjPanel2 = new Panel();
                ivjPanel2.setName("Panel2");
                ivjPanel2.setLayout(null);
                ivjPanel2.setBackground(NZColor.BACKGROUND);
                ivjPanel2.setBounds(0, 305, 530, 50);
                NZButton anzbutton[] = new NZButton[2];
                anzbutton[0] = getJCButton1();
                anzbutton[1] = getJCButton2();
                NZButtonPanel nzbuttonpanel = new NZButtonPanel(anzbutton, 1);
                nzbuttonpanel.setLocation(5, 5);
                nzbuttonpanel.setWidth(524);
                nzbuttonpanel.repaint();
                getPanel2().add(nzbuttonpanel);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjPanel2;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    private void initConnections()
    {
        addWindowListener(new FrameAdapter());
        getJCButton1().addActionListener(new SaveListener());
        getJCButton2().addActionListener(new CancelListener());
    }

    private void initialize()
    {
        setName("Ticker Customization");
        setLayout(new BorderLayout());
        setBackground(NZColor.BACKGROUND);
        setSize(535, 370);
        setResizable(false);
        setTitle(resNZResource.getString("ZeroPort_Ticker_Customizat"));
        add(getContentsPane(), "Center");
        initConnections();
        int i = Initializer.m_zwindow.getCurrentTopic();
        if(i != -1)
        {
            for(int j = 0; j < headers.size(); j++)
            {
                if(((TopicsInformation)headers.elementAt(j)).getTopicId() != i)
                    continue;
                getJCTabManager1().setCurrentTab(j);
                break;
            }

        }
    }

    public static void main(String args[])
    {
        String s = System.getProperty("nz.fake");
        if(s != null)
            TickerTransactions.nzFake = s.equals("true");
        Vector vector = TickerTransactions.fetchTickerInformation(new FetchRequestImpl());
        if(vector == null)
        {
            return;
        } else
        {
            CustomizationDialog customizationdialog = new CustomizationDialog(new Frame(), vector);
            customizationdialog.setModal(true);
            customizationdialog.setVisible(true);
            System.exit(0);
            return;
        }
    }

    private void quit()
    {
        Component acomponent[] = getJCTabManager1().getPages();
        boolean flag = false;
        for(int i = 0; i < acomponent.length; i++)
        {
            if(!((TabPanel)acomponent[i]).hasSelectionChanged())
                continue;
            flag = true;
            break;
        }

        if(!flag)
        {
            dispose();
            return;
        }
        String as[] = new String[2];
        as[0] = "yes";
        as[1] = "no";
        int j = NZDialogBox.showMessageDialog(resNZResource.getString("ZeroPort_Ticker_Customizat1"), resNZResource.getString("Are_you_sure_you_want_to_e"), 1, as);
        if(j == 0)
            dispose();
    }

    private void saveSelections()
    {
        Component acomponent[] = getJCTabManager1().getPages();
        if(acomponent.length == 0)
            return;
        ImageGenerator imagegenerator = Initializer.m_zwindow.getImageGenerator();
        boolean flag = false;
        for(int i = 0; i < acomponent.length; i++)
        {
            TabPanel tabpanel = (TabPanel)acomponent[i];
            if(tabpanel.hasSelectionChanged())
                flag = true;
            else
                ((TopicsInformation)headers.elementAt(i)).setSelection2Null();
        }

        if(flag && TickerTransactions.saveTickerInformation(headers) && imagegenerator != null)
        {
            for(int j = 0; j < headers.size(); j++)
            {
                TopicsInformation topicsinformation = (TopicsInformation)headers.elementAt(j);
                if(topicsinformation.getSelection() != null)
                    if(imagegenerator.getTopic() == topicsinformation.getTopicId())
                    {
                        ZCast.displayDebug("current selection has changed");
                        imagegenerator.refreshDataAfterUserSelection();
                    } else
                    {
                        imagegenerator.selChanged(topicsinformation.getTopicId(), true);
                    }
            }

        }
        dispose();
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private Vector headers;
    private Frame parent;
    private JCTabManager ivjJCTabManager1;
    private Panel ivjContentsPane;
    private Panel ivjPanel1;
    private Panel ivjPanel2;
    private NZButton ivjJCButton1;
    private NZButton ivjJCButton2;



}
