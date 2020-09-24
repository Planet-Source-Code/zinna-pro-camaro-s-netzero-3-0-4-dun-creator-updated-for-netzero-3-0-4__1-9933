// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TabPanel.java

package ticker.client;

import gui.NZButton;
import gui.NZDialogBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;
import jclass.bwt.JCContainer;
import jclass.bwt.JCItemEvent;
import jclass.bwt.JCLabel;
import jclass.bwt.JCMultiColumnWindow;
import jclass.bwt.JCOutliner;
import jclass.bwt.JCOutlinerAdapter;
import jclass.bwt.JCOutlinerComponent;
import jclass.bwt.JCOutlinerEvent;
import jclass.bwt.JCOutlinerFolderNode;
import jclass.bwt.JCOutlinerNode;
import jclass.bwt.JCOutlinerNodeStyle;
import jclass.util.JCConverter;
import jclass.util.JCString;
import jclass.util.JCUtilConverter;
import jclass.util.JCVector;
import nzcom.ZCast;
import ticker.FetchRequestImpl;
import ticker.TopicItem;
import ticker.TopicItemImpl;
import ticker.TopicsInformation;

// Referenced classes of package ticker.client:
//            TickerTransactions

public class TabPanel extends Panel
{
    class AddL
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            Vector vector = getSelectedNodes();
            if(vector != null)
            {
                updateList(vector);
                subscribe(vector);
            }
        }

        AddL()
        {
        }
    }

    class RemoveL
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            Integer ainteger[] = removeFromList();
            unsubscribe(ainteger);
        }

        RemoveL()
        {
        }
    }

    class TextL
        implements TextListener
    {

        public void textValueChanged(TextEvent textevent)
        {
            String s = getTextField1().getText().trim();
            if(s.length() > 0)
                getButton3().setEnabled(true);
            else
                getButton3().setEnabled(false);
        }

        TextL()
        {
        }
    }

    class SearchL
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            if(getSearchStatus() == 0)
                lookupSymbol();
        }

        SearchL()
        {
        }
    }

    class ListItemL
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent itemevent)
        {
            if(itemevent.getStateChange() == 1)
            {
                if(!getButton2().isEnabled())
                    getButton2().setEnabled(true);
            } else
            if(getList1().getSelectedIndexes().length <= 0)
                getButton2().setEnabled(false);
        }

        ListItemL()
        {
        }
    }

    class TreeAdapter extends JCOutlinerAdapter
    {

        public void outlinerNodeSelectBegin(JCOutlinerEvent jcoutlinerevent)
        {
            if(jcoutlinerevent.getNode() instanceof JCOutlinerFolderNode)
                return;
            if(isSelected((Integer)jcoutlinerevent.getNode().getUserData()))
            {
                jcoutlinerevent.setAllowChange(false);
            } else
            {
                if(getUpperLimit() == -1)
                    return;
                int i = getSelection() != null ? getSelection().size() : 0;
                Vector vector = getSelectedNodes();
                if(vector != null)
                    i += vector.size();
                if(i >= getUpperLimit())
                    jcoutlinerevent.setAllowChange(false);
            }
        }

        public void itemStateChanged(JCItemEvent jcitemevent)
        {
            if(jcitemevent.getStateChange() == 1)
            {
                if(jcitemevent.getItem() instanceof JCOutlinerFolderNode)
                    getButton1().setEnabled(false);
                else
                    getButton1().setEnabled(true);
            } else
            if(jcitemevent.getItem() instanceof JCOutlinerNode)
            {
                Vector vector = getSelectedNodes();
                if(vector == null)
                    getButton1().setEnabled(false);
            }
        }

        public void outlinerFolderStateChangeBegin(JCOutlinerEvent jcoutlinerevent)
        {
            JCOutlinerFolderNode jcoutlinerfoldernode = (JCOutlinerFolderNode)jcoutlinerevent.getNode();
            ZCast.displayDebug("id = " + (Integer)jcoutlinerfoldernode.getUserData());
            if(jcoutlinerevent.getNewState() == 5 && jcoutlinerfoldernode.getChildren() == null)
            {
                Cursor cursor = getCursor();
                setCursor(waitCursor);
                FetchRequestImpl fetchrequestimpl = new FetchRequestImpl();
                fetchrequestimpl.setStatus(1);
                fetchrequestimpl.setItemId((Integer)jcoutlinerfoldernode.getUserData());
                Vector vector = TickerTransactions.fetchTickerInformation(fetchrequestimpl);
                if(vector != null)
                    updateOutliner(jcoutlinerfoldernode, vector, null);
                setCursor(cursor);
            }
        }

        TreeAdapter()
        {
        }
    }

    class KeyEventHandler extends KeyAdapter
    {

        public void keyPressed(KeyEvent keyevent)
        {
            if(keyevent.getKeyCode() == 10 && getSearchStatus() == 0)
            {
                lookupSymbol();
                keyevent.consume();
            }
        }

        KeyEventHandler()
        {
        }
    }


    public TabPanel(TopicsInformation topicsinformation)
    {
        Button1 = null;
        Button2 = null;
        Button3 = null;
        ivjList1 = null;
        ivjJCOutliner1 = null;
        folderStyle = null;
        leafStyle = null;
        selectedStyle = null;
        rootStyle = null;
        waitCursor = new Cursor(3);
        ivjTextField1 = null;
        ivjJCLabel1 = null;
        ivjJCLabel2 = null;
        X = 257;
        oldSel = null;
        info = topicsinformation;
        displayRoot = true;
        oldSel = new Vector();
        initialize();
    }

    private JCOutlinerFolderNode addFolderItem(TopicItem topicitem, JCOutlinerFolderNode jcoutlinerfoldernode)
    {
        JCOutlinerFolderNode jcoutlinerfoldernode1 = new JCOutlinerFolderNode(null, 1, topicitem.getItemName());
        jcoutlinerfoldernode1.setStyle(folderStyle);
        jcoutlinerfoldernode1.setUserData(topicitem.getItemId());
        jcoutlinerfoldernode.addNode(jcoutlinerfoldernode1);
        return jcoutlinerfoldernode1;
    }

    private JCOutlinerNode addLeafItem(TopicItem topicitem, JCOutlinerFolderNode jcoutlinerfoldernode)
    {
        getJCOutliner1();
        JCVector jcvector = JCContainer.getConverter().toVector(this, topicitem.getItemName(), '|', false);
        JCOutlinerNode jcoutlinernode = new JCOutlinerNode(jcvector);
        jcoutlinernode.setUserData(topicitem.getItemId());
        setLeafForeground(jcoutlinernode, isSelected(topicitem.getItemId()));
        jcoutlinerfoldernode.addNode(jcoutlinernode);
        return jcoutlinernode;
    }

    private JCOutlinerNode addSubItems(Vector vector, JCOutlinerFolderNode jcoutlinerfoldernode, String s)
    {
        Object obj = null;
        JCOutlinerNode jcoutlinernode = null;
        for(int i = 0; i < vector.size(); i++)
            if(vector.elementAt(i) instanceof TopicItem)
            {
                TopicItem topicitem = (TopicItem)vector.elementAt(i);
                if(!topicitem.hasChildren())
                {
                    JCOutlinerNode jcoutlinernode1 = addLeafItem(topicitem, jcoutlinerfoldernode);
                    if(s != null && jcoutlinernode == null && topicitem.getItemName().toLowerCase().startsWith(s))
                        jcoutlinernode = jcoutlinernode1;
                } else
                {
                    JCOutlinerFolderNode jcoutlinerfoldernode1 = addFolderItem(topicitem, jcoutlinerfoldernode);
                    if(topicitem.getSubItems() != null)
                    {
                        JCOutlinerNode jcoutlinernode2 = addSubItems(topicitem.getSubItems(), jcoutlinerfoldernode1, s);
                        if(jcoutlinernode == null)
                            jcoutlinernode = jcoutlinernode2;
                    }
                }
            }

        return jcoutlinernode;
    }

    private boolean displayRoot()
    {
        return displayRoot;
    }

    private NZButton getButton1()
    {
        if(Button1 == null)
            try
            {
                Button1 = new NZButton("plus");
                int i = getButton1().getUpImage().getWidth(this);
                ZCast.displayDebug("w == " + i);
                Button1.setBounds(X, 110, i, 32);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return Button1;
    }

    private NZButton getButton2()
    {
        if(Button2 == null)
            try
            {
                Button2 = new NZButton("minus");
                int i = getButton2().getUpImage().getWidth(this);
                ZCast.displayDebug("w == " + i);
                Button2.setBounds(X, 110, i, 32);
                X += i;
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return Button2;
    }

    private NZButton getButton3()
    {
        if(Button3 == null)
            try
            {
                Button3 = new NZButton("lookupsymbol");
                Button3.setLocation(145, 243);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return Button3;
    }

    private JCLabel getJCLabel1()
    {
        if(ivjJCLabel1 == null)
            try
            {
                ivjJCLabel1 = new JCLabel();
                ivjJCLabel1.setName("JCLabel1");
                ivjJCLabel1.setAlignment(4);
                ivjJCLabel1.setBounds(-5, 2, 223, 50);
                JCString jcstring = JCString.parse(this, "[IMG=images/text1.gif]");
                ivjJCLabel1.setLabel(jcstring);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCLabel1;
    }

    private JCLabel getJCLabel2()
    {
        if(ivjJCLabel2 == null)
            try
            {
                ivjJCLabel2 = new JCLabel();
                ivjJCLabel2.setName("JCLabel2");
                ivjJCLabel2.setAlignment(4);
                ivjJCLabel2.setBounds(312, 2, 173, 50);
                JCString jcstring = JCString.parse(this, "[IMG=images/text2.gif]");
                ivjJCLabel2.setLabel(jcstring);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCLabel2;
    }

    private JCOutliner getJCOutliner1()
    {
        if(ivjJCOutliner1 == null)
            try
            {
                ivjJCOutliner1 = new JCOutliner();
                ivjJCOutliner1.setName("JCOutliner1");
                ivjJCOutliner1.getOutliner().setBackground(Color.white);
                ivjJCOutliner1.setRootVisible(displayRoot());
                ivjJCOutliner1.setAllowMultipleSelections(true);
                ivjJCOutliner1.setAutoSelect(false);
                ivjJCOutliner1.setBounds(5, 55, 250, 175);
                String as[] = info.getColumnLabels();
                if(as != null && as.length > 1)
                    ivjJCOutliner1.setNumColumns(as.length);
                populateOutliner();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjJCOutliner1;
    }

    private java.awt.List getList1()
    {
        if(ivjList1 == null)
            try
            {
                ivjList1 = new java.awt.List();
                ivjList1.setName("List1");
                ivjList1.setBounds(335, 55, 175, 175);
                ivjList1.setBackground(Color.white);
                ivjList1.setMultipleMode(true);
                populateList();
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjList1;
    }

    TopicItem getRootItem()
    {
        return info.getHeader();
    }

    private int getSearchStatus()
    {
        return info.getSearchStatus();
    }

    private Vector getSelectedNodes()
    {
        JCOutlinerNode ajcoutlinernode[] = getJCOutliner1().getSelectedNodes();
        if(ajcoutlinernode == null)
            return null;
        Vector vector = null;
        for(int i = 0; i < ajcoutlinernode.length; i++)
            if(!(ajcoutlinernode[i] instanceof JCOutlinerFolderNode) && !isSelected((Integer)ajcoutlinernode[i].getUserData()))
            {
                if(vector == null)
                    vector = new Vector();
                vector.addElement(ajcoutlinernode[i]);
            }

        ajcoutlinernode = null;
        return vector;
    }

    Vector getSelection()
    {
        return info.getSelection();
    }

    private TextField getTextField1()
    {
        if(ivjTextField1 == null)
            try
            {
                ivjTextField1 = new TextField();
                ivjTextField1.setName("TextField1");
                ivjTextField1.setBounds(5, 240, 135, 25);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTextField1;
    }

    int getTopicId()
    {
        return info.getTopicId();
    }

    int getUpperLimit()
    {
        int i = info.getUpperLimit() != null && info.getUpperLimit().intValue() > 0 ? info.getUpperLimit().intValue() : -1;
        return i;
    }

    private void handleException(Throwable throwable)
    {
        ZCast.displayDebug("--------- UNCAUGHT EXCEPTION ---------");
        ZCast.displayDebug(throwable);
    }

    public boolean hasSelectionChanged()
    {
        if(getSelection() == null || getSelection().size() == 0)
            return oldSel.size() != 0;
        if(getSelection().size() != oldSel.size())
            return true;
        for(int i = 0; i < getSelection().size(); i++)
        {
            Integer integer = ((TopicItem)getSelection().elementAt(i)).getItemId();
            if(!oldSel.contains(integer))
                return true;
        }

        return false;
    }

    private void initConnections()
    {
        getButton1().addActionListener(new AddL());
        getButton2().addActionListener(new RemoveL());
        getJCOutliner1().addItemListener(new TreeAdapter());
        getList1().addItemListener(new ListItemL());
        if(getSearchStatus() >= 0)
        {
            getTextField1().addTextListener(new TextL());
            getButton3().addActionListener(new SearchL());
            getTextField1().addKeyListener(new KeyEventHandler());
        }
    }

    private void initialize()
    {
        setName("TabPanel");
        setLayout(null);
        setBackground(Color.lightGray);
        add(getJCLabel1(), getJCLabel1().getName());
        add(getJCLabel2(), getJCLabel2().getName());
        add(getButton2(), getButton2().getName());
        add(getButton1(), getButton1().getName());
        add(getList1(), getList1().getName());
        add(getJCOutliner1(), getJCOutliner1().getName());
        if(getSearchStatus() >= 0)
        {
            add(getTextField1(), getTextField1().getName());
            add(getButton3(), getButton3().getName());
        }
        initConnections();
    }

    private boolean isSelected(Integer integer)
    {
        if(getSelection() == null)
            return false;
        for(Enumeration enumeration = getSelection().elements(); enumeration.hasMoreElements();)
            try
            {
                if(((TopicItem)enumeration.nextElement()).getItemId().equals(integer))
                    return true;
            }
            catch(ClassCastException _ex) { }

        return false;
    }

    private JCOutlinerNode lookupByName(JCOutlinerFolderNode jcoutlinerfoldernode, String s)
    {
        JCVector jcvector = jcoutlinerfoldernode.getChildren();
        if(jcvector == null)
            return null;
        for(Enumeration enumeration = jcvector.elements(); enumeration.hasMoreElements();)
        {
            JCOutlinerNode jcoutlinernode = (JCOutlinerNode)enumeration.nextElement();
            if(jcoutlinernode instanceof JCOutlinerFolderNode)
            {
                JCOutlinerNode jcoutlinernode1 = lookupByName((JCOutlinerFolderNode)jcoutlinernode, s);
                if(jcoutlinernode1 != null)
                    return jcoutlinernode1;
            } else
            if(jcoutlinernode.getLabelString().toLowerCase().startsWith(s))
                return jcoutlinernode;
        }

        return null;
    }

    private Vector lookupOnServer(Integer integer, String s)
    {
        FetchRequestImpl fetchrequestimpl = new FetchRequestImpl();
        fetchrequestimpl.setStatus(2);
        fetchrequestimpl.setItemId(integer);
        fetchrequestimpl.setWanted(s);
        return TickerTransactions.fetchTickerInformation(fetchrequestimpl);
    }

    private void lookupSymbol()
    {
        String s = getTextField1().getText().trim().toLowerCase();
        if(s.length() == 0)
            return;
        String s1 = resNZResource.getString("company_not_found");
        Cursor cursor = getCursor();
        setCursor(waitCursor);
        JCOutlinerNode jcoutlinernode = lookupSymbolByName((JCOutlinerFolderNode)getJCOutliner1().getRootNode(), s);
        if(jcoutlinernode == null)
        {
            setCursor(cursor);
            String as[] = new String[1];
            as[0] = "ok";
            NZDialogBox.showMessageDialog(resNZResource.getString("Warning"), s1, 3, as);
        } else
        if(jcoutlinernode instanceof JCOutlinerFolderNode)
        {
            JCOutlinerFolderNode jcoutlinerfoldernode = (JCOutlinerFolderNode)jcoutlinernode;
            Vector vector = lookupOnServer((Integer)jcoutlinerfoldernode.getUserData(), s);
            if(vector == null)
            {
                setCursor(cursor);
                String as1[] = new String[1];
                as1[0] = "ok";
                String s2 = resNZResource.getString("Warning");
                NZDialogBox.showMessageDialog(s2, s1, 3, as1);
                jcoutlinernode = null;
            } else
            {
                jcoutlinernode = addSubItems(vector, jcoutlinerfoldernode, s);
                if(jcoutlinernode == null)
                    setCursor(cursor);
            }
        }
        if(jcoutlinernode != null)
        {
            for(JCOutlinerFolderNode jcoutlinerfoldernode1 = jcoutlinernode.getParent(); jcoutlinerfoldernode1 != null; jcoutlinerfoldernode1 = jcoutlinerfoldernode1.getParent())
                if(jcoutlinerfoldernode1.getState() != 5)
                {
                    jcoutlinerfoldernode1.setState(5);
                    getJCOutliner1().folderChanged(jcoutlinerfoldernode1);
                }

            getJCOutliner1().selectNode(jcoutlinernode, null);
            getJCOutliner1().makeNodeVisible(jcoutlinernode);
            setCursor(cursor);
        }
    }

    private JCOutlinerNode lookupSymbolByName(JCOutlinerFolderNode jcoutlinerfoldernode, String s)
    {
        JCVector jcvector = jcoutlinerfoldernode.getChildren();
        if(jcvector == null)
            return jcoutlinerfoldernode;
        for(Enumeration enumeration = jcvector.elements(); enumeration.hasMoreElements();)
        {
            JCOutlinerNode jcoutlinernode = (JCOutlinerNode)enumeration.nextElement();
            if(jcoutlinernode instanceof JCOutlinerFolderNode)
            {
                int i = jcoutlinernode.getLabelString().indexOf("to");
                if(i == -1)
                {
                    if(s.startsWith(jcoutlinernode.getLabelString().toLowerCase()))
                        return lookupSymbolByName((JCOutlinerFolderNode)jcoutlinernode, s);
                } else
                {
                    try
                    {
                        if((s.compareTo(jcoutlinernode.getLabelString().substring(0, i - 1).toLowerCase()) >= 0 || jcoutlinernode.getLabelString().substring(0, i - 1).toLowerCase().startsWith(s)) && (s.compareTo(jcoutlinernode.getLabelString().substring(i + 3).toLowerCase()) <= 0 || s.startsWith(jcoutlinernode.getLabelString().substring(i + 3).toLowerCase())))
                            return lookupSymbolByName((JCOutlinerFolderNode)jcoutlinernode, s);
                    }
                    catch(Exception _ex) { }
                }
            } else
            {
                String s1 = jcoutlinernode.getLabelString();
                if(s1 != null && s1.toLowerCase().startsWith(s))
                    return jcoutlinernode;
            }
        }

        return null;
    }

    private void populateList()
    {
        if(getSelection() == null)
            return;
        Object obj = null;
        for(int i = 0; i < getSelection().size(); i++)
            try
            {
                TopicItem topicitem = (TopicItem)getSelection().elementAt(i);
                String s = topicitem.getItemName();
                int j = s.indexOf(124);
                if(j != -1)
                {
                    StringBuffer stringbuffer = new StringBuffer();
                    if(j > 0)
                        stringbuffer.append(s.substring(0, j));
                    if(j < s.length() - 1)
                    {
                        if(j > 0)
                            stringbuffer.append(" - ");
                        stringbuffer.append(s.substring(j + 1));
                    }
                    getList1().addItem(stringbuffer.toString());
                } else
                {
                    getList1().addItem(topicitem.getItemName());
                }
                oldSel.addElement(topicitem.getItemId());
            }
            catch(ClassCastException _ex)
            {
                getSelection().removeElementAt(i);
                i--;
            }

    }

    private void populateOutliner()
    {
        if(getJCOutliner1() == null || getRootItem() == null)
            return;
        Image image = JCUtilConverter.toImage(this, "images/folder_open.gif");
        Image image1 = JCUtilConverter.toImage(this, "images/folder.gif");
        folderStyle = new JCOutlinerNodeStyle(getJCOutliner1().getDefaultNodeStyle());
        folderStyle.setShortcut(true);
        folderStyle.setFolderOpenIcon(image);
        folderStyle.setFolderOpenSelectedIcon(image);
        folderStyle.setFolderClosedIcon(image1);
        folderStyle.setFolderClosedSelectedIcon(image1);
        rootStyle = new JCOutlinerNodeStyle(getJCOutliner1().getDefaultNodeStyle());
        rootStyle.setFolderOpenIcon(image1);
        rootStyle.setFolderOpenSelectedIcon(image);
        rootStyle.setFolderClosedIcon(image1);
        rootStyle.setFolderClosedSelectedIcon(image1);
        leafStyle = new JCOutlinerNodeStyle(getJCOutliner1().getDefaultNodeStyle());
        Image image2 = JCUtilConverter.toImage(this, "images/doc_icon.gif");
        leafStyle.setItemIcon(image2);
        leafStyle.setItemSelectedIcon(image2);
        selectedStyle = new JCOutlinerNodeStyle(leafStyle);
        selectedStyle.setForeground(Color.red);
        JCOutlinerFolderNode jcoutlinerfoldernode = new JCOutlinerFolderNode(null, getRootItem().getItemName());
        jcoutlinerfoldernode.setStyle(rootStyle);
        jcoutlinerfoldernode.setUserData(getRootItem().getItemId());
        if(getRootItem().getSubItems() == null)
        {
            jcoutlinerfoldernode.setState(1);
        } else
        {
            byte byte0 = displayRoot() ? ((byte) (1)) : 5;
            addSubItems(getRootItem().getSubItems(), jcoutlinerfoldernode, null);
            jcoutlinerfoldernode.setState(byte0);
        }
        getJCOutliner1().setRootNode(jcoutlinerfoldernode);
    }

    private Integer[] removeFromList()
    {
        int ai[] = getList1().getSelectedIndexes();
        Integer ainteger[] = new Integer[ai.length];
        for(int i = 0; i < ai.length; i++)
        {
            ainteger[i] = ((TopicItem)getSelection().elementAt(ai[i] - i)).getItemId();
            getSelection().removeElementAt(ai[i] - i);
            getList1().remove(ai[i] - i);
        }

        getButton2().setEnabled(false);
        if(getSelectedNodes() != null)
            getButton1().setEnabled(true);
        return ainteger;
    }

    private void setLeafForeground(JCOutlinerNode jcoutlinernode, boolean flag)
    {
        if(flag)
            jcoutlinernode.setStyle(selectedStyle);
        else
            jcoutlinernode.setStyle(leafStyle);
    }

    private void subscribe(Vector vector)
    {
        if(vector == null || vector.size() <= 0)
            return;
        JCOutlinerFolderNode jcoutlinerfoldernode = ((JCOutlinerNode)vector.elementAt(0)).getParent();
        for(int i = 0; i < vector.size(); i++)
        {
            JCOutlinerNode jcoutlinernode = (JCOutlinerNode)vector.elementAt(i);
            setLeafForeground(jcoutlinernode, true);
        }

        getJCOutliner1().folderChanged(jcoutlinerfoldernode);
    }

    private void unsubscribe(Integer ainteger[])
    {
        JCOutlinerNode jcoutlinernode = getJCOutliner1().getRootNode();
        unsubscribe(jcoutlinernode.getChildren(), ainteger);
    }

    private void unsubscribe(JCVector jcvector, Integer ainteger[])
    {
        if(jcvector == null)
            return;
        boolean flag = false;
        for(Enumeration enumeration = jcvector.elements(); enumeration.hasMoreElements();)
        {
            Object obj = enumeration.nextElement();
            if(obj instanceof JCOutlinerFolderNode)
                unsubscribe(((JCOutlinerFolderNode)obj).getChildren(), ainteger);
            else
            if(obj instanceof JCOutlinerNode)
            {
                JCOutlinerNode jcoutlinernode = (JCOutlinerNode)obj;
                Integer integer = (Integer)jcoutlinernode.getUserData();
                for(int i = 0; i < ainteger.length; i++)
                    if(ainteger[i].equals(integer))
                    {
                        setLeafForeground(jcoutlinernode, false);
                        getJCOutliner1().folderChanged(jcoutlinernode);
                    }

            }
        }

    }

    private void updateList(Vector vector)
    {
        if(vector == null)
            return;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
            try
            {
                JCOutlinerNode jcoutlinernode = (JCOutlinerNode)enumeration.nextElement();
                JCVector jcvector = (JCVector)jcoutlinernode.getLabel();
                if(jcvector.size() == 0)
                    break;
                if(jcvector.size() == 1 || jcvector.size() == 2)
                {
                    getList1().addItem((String)jcvector.elementAt(0));
                } else
                {
                    StringBuffer stringbuffer = new StringBuffer();
                    if(jcvector.elementAt(0) != null)
                    {
                        stringbuffer.append((String)jcvector.elementAt(0));
                        if(jcvector.elementAt(1) != null)
                            stringbuffer.append(" - ");
                    }
                    if(jcvector.elementAt(1) != null)
                        stringbuffer.append((String)jcvector.elementAt(1));
                    getList1().addItem(stringbuffer.toString());
                }
                info.addElement(new TopicItemImpl((Integer)jcoutlinernode.getUserData(), null, false));
            }
            catch(Exception _ex) { }

        getButton1().setEnabled(false);
    }

    private void updateOutliner(JCOutlinerFolderNode jcoutlinerfoldernode, Vector vector, String s)
    {
        if(vector == null)
        {
            return;
        } else
        {
            addSubItems(vector, jcoutlinerfoldernode, s);
            getJCOutliner1().folderChanged(jcoutlinerfoldernode);
            return;
        }
    }

    private static ResourceBundle resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
    private NZButton Button1;
    private NZButton Button2;
    private NZButton Button3;
    private java.awt.List ivjList1;
    private JCOutliner ivjJCOutliner1;
    private JCOutlinerNodeStyle folderStyle;
    private JCOutlinerNodeStyle leafStyle;
    private JCOutlinerNodeStyle selectedStyle;
    private JCOutlinerNodeStyle rootStyle;
    private Cursor waitCursor;
    private TextField ivjTextField1;
    private JCLabel ivjJCLabel1;
    private JCLabel ivjJCLabel2;
    private int X;
    private boolean displayRoot;
    private TopicsInformation info;
    private Vector oldSel;
















}
