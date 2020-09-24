// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextField1.java

package beans;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.*;

public class TextField1 extends TextField
    implements KeyListener
{

    public TextField1()
    {
        pwdEcho = '*';
        maxChars = 99;
        maxDataChars = 8;
        minChars = 0;
        dataChanged = false;
        validData = false;
        replaceMode = false;
        dataRequired = false;
        dataType = 'A';
        formattingChars = "";
        helpText = "";
        initialize();
    }

    public TextField1(int i)
    {
        super(i);
        pwdEcho = '*';
        maxChars = 99;
        maxDataChars = 8;
        minChars = 0;
        dataChanged = false;
        validData = false;
        replaceMode = false;
        dataRequired = false;
        dataType = 'A';
        formattingChars = "";
        helpText = "";
    }

    public TextField1(String s)
    {
        super(s);
        pwdEcho = '*';
        maxChars = 99;
        maxDataChars = 8;
        minChars = 0;
        dataChanged = false;
        validData = false;
        replaceMode = false;
        dataRequired = false;
        dataType = 'A';
        formattingChars = "";
        helpText = "";
    }

    public TextField1(String s, int i)
    {
        super(s, i);
        pwdEcho = '*';
        maxChars = 99;
        maxDataChars = 8;
        minChars = 0;
        dataChanged = false;
        validData = false;
        replaceMode = false;
        dataRequired = false;
        dataType = 'A';
        formattingChars = "";
        helpText = "";
    }

    public boolean checkFormatting(char c, int i)
    {
        if(!replaceMode)
            return getText().length() < maxChars;
        if(formattingChars.charAt(i) != ' ')
            i++;
        curChars[i] = c;
        updateText(i + 1);
        return false;
    }

    public static final int classifyChar(char c, String s)
    {
        if(alpha.indexOf(c) > -1)
            return 1;
        if(numeric.indexOf(c) > -1)
            return 2;
        if(s != null && s.indexOf(c) > -1)
            return 8;
        return printable.indexOf(c) <= -1 ? 0 : 4;
    }

    private void connEtoM1(KeyEvent keyevent)
    {
        try
        {
            processKey(keyevent);
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public String getData()
    {
        if(replaceMode)
        {
            char ac[] = new char[maxChars];
            int i = 0;
            for(int j = 0; i < maxChars && j < curChars.length;)
                if(formattingChars.charAt(j) == ' ')
                    ac[i++] = curChars[j++];
                else
                    j++;

            return (new String(ac, 0, i)).trim();
        } else
        {
            return getText().trim();
        }
    }

    public char getDataType()
    {
        return dataType;
    }

    public int getMaxChars()
    {
        return maxDataChars;
    }

    private void handleException(Throwable throwable)
    {
    }

    private void initConnections()
    {
        addKeyListener(this);
    }

    private void initialize()
    {
        setName("TextField1");
        setSize(125, 30);
        initConnections();
        normalEcho = getEchoChar();
    }

    public boolean isDataChanged()
    {
        return dataChanged;
    }

    public void keyPressed(KeyEvent keyevent)
    {
        if(keyevent.getSource() == this)
            connEtoM1(keyevent);
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public static void main(String args[])
    {
        try
        {
            Frame frame;
            try
            {
                Class class1 = Class.forName("com.ibm.uvm.abt.edit.TestFrame");
                frame = (Frame)class1.newInstance();
            }
            catch(Throwable _ex)
            {
                frame = new Frame();
            }
            frame.setLayout(null);
            TextField1 textfield1 = new TextField1();
            textfield1.setBounds(20, 50, 150, 20);
            frame.add(textfield1);
            frame.setSize(300, 300);
            frame.setVisible(true);
            textfield1.setMaxChars(13);
            textfield1.setDataType(new Character('P'));
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of textedits.TextField1");
            throwable.printStackTrace(System.out);
        }
    }

    private boolean processControlKey(int i, int j)
    {
        if(i == 10)
            return false;
        switch(i)
        {
        case 8: // '\b'
            while(j > 0) 
            {
                dataChanged = true;
                j--;
                if(formattingChars.charAt(j) == ' ')
                {
                    curChars[j] = ' ';
                    updateText(j);
                    return true;
                }
            }
            return true;

        case 127: // '\177'
            return true;
        }
        return false;
    }

    public void processKey(KeyEvent keyevent)
    {
        if(keyevent.getKeyCode() == 112)
        {
            showMessage(helpText);
            keyevent.consume();
            return;
        }
        char c = keyevent.getKeyChar();
        int i = classifyChar(c, validChars);
        int j = getCaretPosition();
        if(i == 0)
            if(replaceMode)
            {
                if(processControlKey(keyevent.getKeyCode(), j))
                    keyevent.consume();
                return;
            } else
            {
                return;
            }
        if(dataType == 'H')
        {
            keyevent.consume();
            return;
        }
        if(j < maxChars)
        {
            if(verifyChar(i))
            {
                if(checkFormatting(c, j))
                {
                    dataChanged = true;
                    return;
                }
            } else
            {
                showMessage("'" + c + resNZResource.getString("'_not_a_valid_character"));
            }
        } else
        {
            showMessage(resNZResource.getString("Data_field_is_full"));
        }
        keyevent.consume();
    }

    public void refreshData(String s)
    {
        dataChanged = false;
        if(replaceMode)
        {
            int i = 0;
            for(int j = 0; i < s.length() && j < curChars.length;)
                if(formattingChars.charAt(j) == ' ')
                    curChars[j++] = s.charAt(i++);
                else
                    j++;

            updateText(0);
        } else
        {
            setText(s);
        }
    }

    public void setComponent(Component component)
    {
        msgComponent = component;
    }

    public void setDataRequired(boolean flag)
    {
        dataRequired = flag;
    }

    public void setDataType(Character character)
    {
        int i = 0;
        dataType = character.charValue();
        helpText = "";
        setEchoChar(normalEcho);
        switch(dataType)
        {
        case 65: // 'A'
            validChars = special1;
            formattingChars = "";
            minChars = 0;
            replaceMode = false;
            break;

        case 68: // 'D'
            validChars = null;
            formattingChars = "  /  /    ";
            replaceMode = true;
            maxChars = 10;
            minChars = 8;
            setHelpText(resNZResource.getString("Date_format_is__mm/dd/yyyy"));
            break;

        case 80: // 'P'
            validChars = null;
            formattingChars = "(   )   -    ";
            replaceMode = true;
            maxChars = 13;
            minChars = 10;
            setHelpText(resNZResource.getString("Phone_number_format_is__(x"));
            i = 1;
            break;

        case 88: // 'X'
            validChars = special2;
            replaceMode = false;
            maxChars = 12;
            minChars = 3;
            setEchoChar(pwdEcho);
            break;

        case 90: // 'Z'
            validChars = null;
            replaceMode = true;
            formattingChars = "     -    ";
            maxChars = 10;
            minChars = 5;
            setHelpText(resNZResource.getString("Zipcode_format_is__nnnnn__"));
            break;

        default:
            validChars = null;
            formattingChars = "";
            replaceMode = false;
            minChars = 0;
            break;
        }
        setText(formattingChars);
        setCaretPosition(i);
        curChars = formattingChars.toCharArray();
    }

    public void setHelpText(String s)
    {
        helpText = s;
        showMessage(s);
    }

    public void setMaxChars(int i)
    {
        maxDataChars = i;
        maxChars = maxDataChars;
    }

    public void setMinChars(int i)
    {
        minChars = i;
    }

    private void showMessage(String s)
    {
        if(msgComponent != null)
        {
            ((Label)msgComponent).setText(s);
            if(s.length() > 2)
                getToolkit().beep();
        }
    }

    private void updateText(int i)
    {
        setText(new String(curChars));
        setCaretPosition(i);
        dataChanged = true;
    }

    private boolean verifyBlanks(String s)
    {
        int i = s.indexOf(32);
        if(i > -1)
        {
            showMessage(resNZResource.getString("Blank(s)_not_allowed_in_th"));
            requestFocus();
            select(i, i + 1);
            return false;
        } else
        {
            return true;
        }
    }

    private boolean verifyChar(int i)
    {
        switch(dataType)
        {
        case 65: // 'A'
            return i == 1 || i == 2 || i == 8;

        case 68: // 'D'
            return i == 2;

        case 80: // 'P'
            return i == 2;

        case 78: // 'N'
            return i == 2;

        case 90: // 'Z'
            return i == 2;

        case 88: // 'X'
            return i == 1 || i == 2 || i == 8;
        }
        return i == 1 || i == 2 || i == 8;
    }

    public boolean verifyData()
    {
        String s = getData();
        if(s.length() == 0)
            if(dataRequired)
            {
                showMessage(resNZResource.getString("Answer_required"));
                return false;
            } else
            {
                return true;
            }
        if(s.length() < minChars || s.length() > maxChars)
        {
            showMessage(resNZResource.getString("incorrect_amount_of_data_e"));
            return false;
        }
        switch(dataType)
        {
        case 65: // 'A'
            return true;

        case 68: // 'D'
            return verifyDate(s);

        case 80: // 'P'
            return verifyPhone(s);

        case 90: // 'Z'
            return verifyZipCode(s);

        case 88: // 'X'
            return true;
        }
        return true;
    }

    private boolean verifyDate(String s)
    {
        int ai[] = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
            30, 31
        };
        if(s.length() < 8)
            return false;
        String s1 = s.substring(0, 2);
        String s2 = s.substring(2, 4);
        String s3 = s.substring(4);
        int i = Integer.parseInt(s3);
        boolean flag = false;
        Calendar calendar = Calendar.getInstance();
        int j = calendar.get(1);
        flag = i % 4 == 0;
        if(flag)
            ai[1] = 29;
        if(j - i < 5 || j - i > 100)
        {
            showMessage(resNZResource.getString("invalid_year_") + s3);
            requestFocus();
            select(6, 10);
            return false;
        }
        if(s1.compareTo("01") < 0 || s1.compareTo("12") > 0)
        {
            showMessage(resNZResource.getString("invalid_month_") + s1);
            requestFocus();
            select(0, 2);
            return false;
        }
        int k = ai[Integer.parseInt(s1) - 1];
        int l = Integer.parseInt(s2);
        if(s2.compareTo("01") < 0 || l > k)
        {
            showMessage(resNZResource.getString("invalid_day_") + s2);
            requestFocus();
            select(3, 5);
            return false;
        } else
        {
            return verifyBlanks(getText());
        }
    }

    private boolean verifyPhone(String s)
    {
        if(s.charAt(0) == '0')
        {
            showMessage(resNZResource.getString("First_digit_of_areacode_ca"));
            requestFocus();
            select(1, 2);
            return false;
        } else
        {
            return verifyBlanks(getText());
        }
    }

    private boolean verifyZipCode(String s)
    {
        if(s.length() == 5)
            return verifyBlanks(s);
        if(s.length() == 9)
        {
            return verifyBlanks(getText());
        } else
        {
            showMessage(resNZResource.getString("Incomplete_zipcode"));
            return false;
        }
    }

    private static ResourceBundle resNZResource;
    protected char normalEcho;
    protected char pwdEcho;
    protected int maxChars;
    protected int maxDataChars;
    protected int minChars;
    protected String validChars;
    protected Component msgComponent;
    protected String oldData;
    protected String curData;
    protected char curChars[];
    protected boolean dataChanged;
    protected boolean validData;
    protected boolean replaceMode;
    protected boolean dataRequired;
    protected char dataType;
    protected static final String alpha;
    protected static final String numeric;
    protected static final String special1;
    protected static final String special2;
    protected static final String printable;
    protected String formattingChars;
    protected String helpText;

    static 
    {
        resNZResource = ResourceBundle.getBundle("nzcom.NZResource");
        alpha = resNZResource.getString("alphabet");
        numeric = resNZResource.getString("numers");
        special1 = resNZResource.getString("special1");
        special2 = resNZResource.getString("special2");
        printable = resNZResource.getString("printable");
    }
}
