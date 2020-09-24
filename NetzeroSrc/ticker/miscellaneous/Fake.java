// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Fake.java

package ticker.miscellaneous;

import errors.NZErrors;
import java.util.Enumeration;
import java.util.Vector;
import nzcom.ZCast;
import ticker.*;

public class Fake
{

    static TopicItemImpl addItem(String s, TopicItemImpl topicitemimpl, boolean flag)
    {
        TopicItemImpl topicitemimpl1 = new TopicItemImpl(getNextId(), s, flag);
        topicitemimpl.addSubItem(topicitemimpl1);
        return topicitemimpl1;
    }

    public static void createFiles()
    {
        Vector vector = new Vector();
        Vector vector1 = new Vector();
        TopicItem topicitem = null;
        TopicsInformationImpl topicsinformationimpl = null;
        topicitem = createSportsTopic();
        vector.addElement(topicitem);
        topicsinformationimpl = new TopicsInformationImpl(new TopicItemImpl(topicitem.getItemId(), topicitem.getItemName(), topicitem.hasChildren()), null);
        topicsinformationimpl.setTopicId(2);
        vector1.addElement(topicsinformationimpl);
        topicitem = createNewsTopic();
        vector.addElement(topicitem);
        topicsinformationimpl = new TopicsInformationImpl(new TopicItemImpl(topicitem.getItemId(), topicitem.getItemName(), topicitem.hasChildren()), null);
        topicsinformationimpl.setTopicId(1);
        vector1.addElement(topicsinformationimpl);
        topicitem = createStocksTopic();
        vector.addElement(topicitem);
        topicsinformationimpl = new TopicsInformationImpl(new TopicItemImpl(topicitem.getItemId(), topicitem.getItemName(), topicitem.hasChildren()), null, 5);
        topicsinformationimpl.setSearchStatus(0);
        String as[] = {
            "Company Name", "Symbol", "Nasdaq"
        };
        topicsinformationimpl.setColumnLabels(as);
        topicsinformationimpl.setTopicId(0);
        vector1.addElement(topicsinformationimpl);
        NZErrors.serialize(getItemsFileName(), vector);
        NZErrors.serialize(getInformationFileName(), vector1);
    }

    private static TopicItem createNewsTopic()
    {
        TopicItemImpl topicitemimpl = new TopicItemImpl(getNextId(), "News", true);
        addItem("Financial news", topicitemimpl, false);
        addItem("Sport news", topicitemimpl, false);
        addItem("Technical news", topicitemimpl, false);
        addItem("News of the world", topicitemimpl, false);
        return topicitemimpl;
    }

    private static TopicItem createSportsTopic()
    {
        TopicItemImpl topicitemimpl = new TopicItemImpl(getNextId(), "Sports", true);
        TopicItemImpl topicitemimpl1 = null;
        Object obj = null;
        topicitemimpl1 = addItem("Tennis", topicitemimpl, true);
        addItem("ATP", topicitemimpl1, false);
        addItem("WTA", topicitemimpl1, false);
        topicitemimpl1 = addItem("Baseball", topicitemimpl, true);
        for(int i = 0; i < 5; i++)
        {
            TopicItemImpl topicitemimpl2;
            if(i == 3)
                topicitemimpl2 = addItem("League " + i, topicitemimpl1, true);
            else
                topicitemimpl2 = addItem("League " + i, topicitemimpl1, false);
            if(i == 3)
            {
                for(int j = 0; j < 4; j++)
                    addItem("Baseball - Team " + j, topicitemimpl2, false);

            }
        }

        topicitemimpl1 = addItem("Socker", topicitemimpl, true);
        for(int k = 0; k < 10; k++)
            addItem("Socker - Team " + k, topicitemimpl1, false);

        topicitemimpl1 = addItem("ski", topicitemimpl, false);
        return topicitemimpl;
    }

    private static TopicItem createStocksTopic()
    {
        TopicItemImpl topicitemimpl = new TopicItemImpl(getNextId(), "Stocks", true);
        for(int i = 10; i < 36; i++)
        {
            Character character = new Character(Character.forDigit(i, 36));
            addItem(character.toString(), topicitemimpl, true);
        }

        Vector vector = topicitemimpl.getSubItems();
        TopicItemImpl topicitemimpl1 = (TopicItemImpl)vector.elementAt(0);
        TopicItemImpl topicitemimpl2 = addItem("aa to ag", topicitemimpl1, true);
        addItem("Albert Einstein|albert", topicitemimpl2, false);
        topicitemimpl2 = addItem("ah to az", topicitemimpl1, true);
        addItem("average behaviour is mediocrity|average|C", topicitemimpl2, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(1);
        addItem("bert", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(2);
        addItem("cfini", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(3);
        addItem("DataMind|DM", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(4);
        addItem("EasyPlanet|ez", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(7);
        addItem("hot", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(8);
        addItem("IBM", topicitemimpl1, false);
        addItem("Illegal|", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(12);
        addItem("Microsoft Corp.|MS", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(13);
        addItem("NetZero|nz", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(14);
        addItem("ozone", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(15);
        addItem("private", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(16);
        addItem("Quiz Studio|QS", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(24);
        addItem("yahoo||E", topicitemimpl1, false);
        topicitemimpl1 = (TopicItemImpl)vector.elementAt(25);
        addItem("|ZORO|D", topicitemimpl1, false);
        return topicitemimpl;
    }

    public static Vector fakeFetchTickerInformation(FetchRequest fetchrequest)
    {
        ZCast.displayDebug("<------------------------ entering in fakeFetchTickerInformation ------");
        Vector vector = null;
        if(fetchrequest.getStatus() == 0)
            vector = (Vector)NZErrors.deserialize(getInformationFileName(), false);
        else
        if(fetchrequest.getStatus() == 1)
        {
            Vector vector1 = (Vector)NZErrors.deserialize(getItemsFileName(), false);
            TopicItem topicitem = lookupById(vector1, fetchrequest.getItemId());
            if(topicitem != null && topicitem.getSubItems() != null && topicitem.getSubItems().size() > 0)
            {
                vector = new Vector();
                for(int i = 0; i < topicitem.getSubItems().size(); i++)
                {
                    TopicItem topicitem2 = (TopicItem)topicitem.getSubItems().elementAt(i);
                    vector.addElement(new TopicItemImpl(topicitem2.getItemId(), topicitem2.getItemName(), topicitem2.hasChildren()));
                }

            }
        } else
        {
            Vector vector2 = (Vector)NZErrors.deserialize(getItemsFileName(), false);
            TopicItem topicitem1 = lookupById(vector2, fetchrequest.getItemId());
            if(lookupByString(topicitem1, fetchrequest.getWanted(), topicitem1.getSubItems()))
                return topicitem1.getSubItems();
            else
                return null;
        }
        ZCast.displayDebug("------ fakeFetchTickerInformation ending ------------------------");
        return vector;
    }

    public static final String getInformationFileName()
    {
        return "d:\\temp\\info.obj";
    }

    public static String getItemsFileName()
    {
        return "d:\\temp\\topics.obj";
    }

    public static Vector getNewsAds()
    {
        Vector vector = new Vector();
        vector.addElement("0;TF1 la chaine qui bouge;www.yahoo.com;TT");
        vector.addElement("1;NetRadio...is dead;www.yahoo.com;TT");
        vector.addElement("1;M. divorce: le monde du tennis en deuil;www.yahoo.com;TT");
        return vector;
    }

    public static Vector getNewsSamples()
    {
        Vector vector = new Vector();
        loop %= 3;
        if(loop == 0)
        {
            NewsToken newstoken = new NewsToken();
            newstoken.setKeyName("News 1");
            newstoken.setDataObject("ELTEK REPORTS FOURTH QUARTER AND FULL YEAR 1998 RESULTS");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kp0203.htm&Mode=HOT");
            vector.addElement(newstoken);
            newstoken = new NewsToken();
            newstoken.setDataObject("INCAM AG REPORTS FIRST-QUARTER REVENUES UP 242%");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kp0038.htm&Mode=HOT");
            vector.addElement(newstoken);
            newstoken = new NewsToken();
            newstoken.setDataObject("MFRI Reports First Quarter Results; Order Backlog at Record High");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kb1105.htm&Mode=HOT");
            vector.addElement(newstoken);
            newstoken = new NewsToken();
            newstoken.setKeyName("News 2");
            newstoken.setDataObject("CPI CORP. ANNOUNCES FIRST QUARTER RESULTS");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kp0197.htm&Mode=HOT");
            vector.addElement(newstoken);
            newstoken = new NewsToken();
            newstoken.setKeyName(null);
            newstoken.setDataObject("Worldwide PetroMoly Reports a 200%  Increase in Net Sales");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kb0036.htm&Mode=HOT");
            vector.addElement(newstoken);
            newstoken = new NewsToken();
            newstoken.setKeyName(null);
            newstoken.setDataObject("Variflex Reports Third Quarter Results");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kb0084.htm&Mode=HOT");
            vector.addElement(newstoken);
            newstoken = new NewsToken();
            newstoken.setKeyName(null);
            newstoken.setDataObject("SIGNET GROUP PLC RELEASES FIRST QUARTER RESULTS");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kp0040.htm&Mode=HOT");
            vector.addElement(newstoken);
            newstoken = new NewsToken();
            newstoken.setKeyName(null);
            newstoken.setDataObject("DETROIT ECONOMY CONTINUES TO ADVANCE - COMERICA INDEX");
            newstoken.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kp0067.htm&Mode=HOT");
            vector.addElement(newstoken);
        } else
        if(loop == 1)
        {
            NewsToken newstoken1 = new NewsToken();
            newstoken1.setKeyName("News 3");
            newstoken1.setDataObject("May Sales of Non-Light Autos at 21-Year Low");
            newstoken1.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0216.htm&Mode=HOT");
            vector.addElement(newstoken1);
            newstoken1 = new NewsToken();
            newstoken1.setKeyName(null);
            newstoken1.setDataObject("BRIDGE ALERT:USDEBT: Sep bond dn 3/32; reverses earlier bounce");
            newstoken1.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kr0109.htm&Mode=HOT");
            vector.addElement(newstoken1);
            newstoken1 = new NewsToken();
            newstoken1.setKeyName(null);
            newstoken1.setDataObject("AT&T Japan Inherits IBM Data Comm Biz");
            newstoken1.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0224.htm&Mode=HOT");
            vector.addElement(newstoken1);
            newstoken1 = new NewsToken();
            newstoken1.setKeyName(null);
            newstoken1.setDataObject("AOL Buys Spinner.com, Nullsoft");
            newstoken1.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0177.htm&Mode=HOT");
            vector.addElement(newstoken1);
            newstoken1 = new NewsToken();
            newstoken1.setKeyName("News 4");
            newstoken1.setDataObject("Nissan, Renault Sharing Advertising Staff");
            newstoken1.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0211.htm&Mode=HOT");
            vector.addElement(newstoken1);
            newstoken1 = new NewsToken();
            newstoken1.setKeyName(null);
            newstoken1.setDataObject("MCI Buying SkyTel for $1.3 Billion");
            newstoken1.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0168.htm&Mode=HOT");
            vector.addElement(newstoken1);
            newstoken1 = new NewsToken();
            newstoken1.setKeyName(null);
            newstoken1.setDataObject("Applied To Acquire Startup Obsidian");
            newstoken1.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0163.htm&Mode=HOT");
            vector.addElement(newstoken1);
        } else
        if(loop == 2)
        {
            NewsToken newstoken2 = new NewsToken();
            newstoken2.setKeyName("News 5");
            newstoken2.setDataObject("Ingram Book Group, Barnes & Noble Withdraw Merger Filing");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kn0003.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("Intel To Acquire Dialogic For $780 Million");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku1483.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("Philippine Central Bank Governor Appointed");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kh0038.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("Number of people claiming unemployment benefit falls");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku1748.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName("News 6");
            newstoken2.setDataObject("Poland considering agriculture production quotas");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku1742.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("CONSUMER SPENDING PUSHES AUSTRALIAN GDP GROWTH to 4.8%");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0566.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("Rouble's mean weighted rate rises in MICEX special trading.");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602kt0024.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("Allied Irish Banks takes stake in Singapore's Keppel TatLee Bank");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku0598.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("Dollar rises, gold mixed");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku1310.htm&Mode=HOT");
            vector.addElement(newstoken2);
            newstoken2 = new NewsToken();
            newstoken2.setKeyName(null);
            newstoken2.setDataObject("Stock Prices Rise in Asia");
            newstoken2.setUrl("http://www.stockpoint.com/NewsStory.asp?Story=19990602ku1674.htm&Mode=HOT");
            vector.addElement(newstoken2);
        }
        loop++;
        return vector;
    }

    static Integer getNextId()
    {
        return new Integer(nextId++);
    }

    public static Vector getSportsAds()
    {
        Vector vector = new Vector();
        vector.addElement("100;Achetez Nike,et vous aurez une boite de pansements gratuite;www.yahoo.com;TT");
        vector.addElement("101;Coca Cola, la boisson des vainqueurs;www.yahoo.com;TT");
        return vector;
    }

    public static Vector getSportsSamples()
    {
        Vector vector = new Vector();
        SportToken sporttoken = new SportToken();
        sporttoken.setKeyName("Baseball");
        sporttoken.setDataObject("MONTREAL (4) AT NY METS (3) - FINAL");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName(null);
        sporttoken.setDataObject("LOS ANGELES (11) AT COLORADO (8) - FINAL");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName(null);
        sporttoken.setDataObject("CHICAGO CUBS (9) AT PITTSBURGH (4) - FINAL");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName(null);
        sporttoken.setDataObject("FLORIDA (2) AT ATLANTA (5) - FINAL");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName(null);
        sporttoken.setDataObject("CINCINNATI (8) AT ST LOUIS (5) - FINAL");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName(null);
        sporttoken.setDataObject("HOUSTON (7) AT ARIZONA (8) - FINAL IN 11 INNINGS");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName("Local");
        sporttoken.setDataObject("San Francisco (10) Oakland (5)");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName(null);
        sporttoken.setDataObject("San Francisco (17) San Jose (12)");
        vector.addElement(sporttoken);
        sporttoken = new SportToken();
        sporttoken.setKeyName(null);
        sporttoken.setDataObject("LA (9) San Mateo (12)");
        vector.addElement(sporttoken);
        return vector;
    }

    public static Vector getStocksAds()
    {
        Vector vector = new Vector();
        return vector;
    }

    public static Vector getStocksSamples()
    {
        Vector vector = new Vector(5);
        loop %= 3;
        if(loop == 0)
        {
            vector.addElement(new StockToken("ZERO", "NetZero|101.75|+20"));
            vector.addElement(new StockToken("AOL", "2.50|-32"));
            vector.addElement(new StockToken("MS", "1.19|-100"));
            vector.addElement(new StockToken("IBM", "89.5|+.5"));
            vector.addElement(new StockToken("GEO", "17.5|+.75"));
        } else
        if(loop == 1)
        {
            vector.addElement(new StockToken("BID1", "Bidon1|1,001.75|+20"));
            vector.addElement(new StockToken("BID2", "Bidon2|2.50|-32"));
            vector.addElement(new StockToken("BID3", "Bidon3|1.19|-100"));
            vector.addElement(new StockToken("BID4", "Bidon4|89.5|+.5"));
            vector.addElement(new StockToken("BID5", "Bidon5|17.5|+.75"));
            vector.addElement(new StockToken("BID6", "Bidon6|89.5|+.5"));
            vector.addElement(new StockToken("BID7", "Bidon7|17.5|+.75"));
        } else
        if(loop == 2)
        {
            vector.addElement(new StockToken("Test1", "1,001.75|+20"));
            vector.addElement(new StockToken("Test2", "2.50|-32"));
            vector.addElement(new StockToken("Test3", "1.19|-100"));
            vector.addElement(new StockToken("Test4", "89.5|+.5"));
        }
        loop++;
        return vector;
    }

    private static TopicItem lookupById(Vector vector, Integer integer)
    {
        if(vector == null)
            return null;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
        {
            TopicItem topicitem = (TopicItem)enumeration.nextElement();
            if(topicitem.getItemId().equals(integer))
                return topicitem;
            if(topicitem.getSubItems() != null)
            {
                TopicItem topicitem1 = lookupById(topicitem.getSubItems(), integer);
                if(topicitem1 != null)
                    return topicitem1;
            }
        }

        return null;
    }

    private static TopicItem lookupById(TopicItem topicitem, Integer integer)
    {
        if(topicitem.getItemId().equals(integer))
            return topicitem;
        if(topicitem.getSubItems() == null)
            return null;
        for(Enumeration enumeration = topicitem.getSubItems().elements(); enumeration.hasMoreElements();)
        {
            TopicItem topicitem1 = lookupById((TopicItem)enumeration.nextElement(), integer);
            if(topicitem1 != null)
                return topicitem1;
        }

        return null;
    }

    private static boolean lookupByString(TopicItem topicitem, String s, Vector vector)
    {
        boolean flag = false;
        if(((TopicItem)vector.elementAt(0)).hasChildren())
        {
            for(int i = 0; i < vector.size(); i++)
                flag = lookupByString((TopicItem)vector.elementAt(i), s, ((TopicItem)vector.elementAt(i)).getSubItems());

        } else
        {
            for(int j = 0; j < vector.size(); j++)
            {
                if(((TopicItem)vector.elementAt(j)).getItemName().startsWith(s))
                    break;
                if(j >= vector.size())
                    topicitem.removeChildren();
                else
                    flag = true;
            }

        }
        return flag;
    }

    public static void main(String args[])
    {
        createFiles();
    }

    public Fake()
    {
    }

    static int nextId = 0;
    static int loop = 0;

}
