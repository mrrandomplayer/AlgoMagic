/**
 * Adam Van Buren and Theo "Kekisan" Trevisan
 * 20/1/17
 * An object representing magic cards, used with AVB_TT_MakeCardFiles.java
 */
import java.io.*;
public class AVB_TT_MagicCard implements Comparable<AVB_TT_MagicCard>, Serializable
{
    private String name;
    private String type;
    private String subtype;
    private String power;
    private String toughness;
    private String artist;
    private String collectorNumber;
    private String rarity;
    private String CMC;
    private String color;
    
    /**
     * Constructor for objects of class AVB_TT_MagicCard
     */
    public AVB_TT_MagicCard(String CollectorNumber, String Name, String Type, String Subtype, String Power, String Toughness, String Mana, String Rarity, String Artist)
    {
        collectorNumber = CollectorNumber;
        name = Name;
        type = Type;
        subtype = Subtype;
        power = Power;
        toughness = Toughness;
        rarity = Rarity;
        artist = Artist;
        color = "";
        int startReadColor = 0;
        String CMC = "";
        if (Mana.equals("No Mana")){
            CMC = "0";
        }
        else
        {
            if (Mana.charAt(0) <58){
                startReadColor++;
                try{
                    if (Mana.charAt(1)<58){
                        startReadColor++;
                    }
                }
                catch(Exception e){
                    
                }
            }
            for (int i = startReadColor; i<Mana.length(); i++){
                if (!color.contains((String)Mana.substring(i,i+1))){
                    color+=Mana.substring(i,i+1);
                }
            }
            if (startReadColor >0){
                int num = Integer.parseInt(Mana.substring(0,1));
                if (startReadColor>2){
                    num*=10;
                    num+= Integer.parseInt(Mana.substring(1,2));
                }
                num+=color.length();
                CMC += num;
            }
            else CMC +=color.length();
        }
    }
    public String getCollectorNumber(){
        return collectorNumber;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getSubtype(){
        return subtype;
    }
    public String getPower(){
        return power;
    }
    public String getToughness(){
        return toughness;
    }
    public String getRarity(){
        return rarity;
    }
    public String getArtist(){
        return artist;
    }
    public String getColor(){
        return color;
    }
    public String getCMC(){
        if (CMC == null) return "0";
        return CMC;
    }
    public int compareTo(AVB_TT_MagicCard o){
        return 0;
    }
}