/**
 * Adam Van Buren and Theo "Butcher of Sequencing" Trevisan
 * 19/1/17
 * Reads data in from a file copied from magiccards.info and turns it into
 * a bunch of files for later use
 */
import java.util.ArrayList;
import java.io.*;
public class AVB_TT_MakeCardFiles
{
    public static void main(String arg){
        File file = new File(arg);
        In in = new In(file);
        String[] cardTypes = {"Creature", "Enchantment", "Artifact", "Instant", "Land", "Legendary", "Planeswalker", "Sorcery"};
        String[] rarities = {"Common", "Uncommon", "Rare", "Mythic"};
        
        String collectorNumber = "";
        String  name = "";
        String type = "";
        String subtype = "";
        String power = "";
        String toughness = "";
        String mana = "";
        String rarity = "";
        String artist = "";
        //boolean lookAtNum = true;
        int number = 0;
        int rarities1 = 0;
        while (!in.isEmpty()){
            /******************************************************/
            String numberIn;
            //if (lookAtNum){
                numberIn= in.readString();
                collectorNumber += numberIn + "*";
            //}
            /*************************************************/
            boolean notCardType = true;
            while (true){
                String nameIn = in.readString();
                for (String cardtype : cardTypes){
                    //if a card type has been reached
                    if (nameIn.equals(cardtype)){
                        type+= nameIn;
                        notCardType = false;
                        number+=1;
                        System.out.println("Card type reached, " + nameIn + "   " + number);
                        name+="*";
                        break;
                    }
                }
                if (!notCardType) break;
                name += nameIn+ " ";
            }
            /***************************************************/
            boolean hasReadMana = false;
            boolean hasReadRarity = false;
            while (true){
                String typeIn = in.readString();
                //if the card has a subtype
                if (typeIn.contains("—")){
                    type += "*";
                    break;
                }
                //if the card does not have a subtype but has a mana cost
                if (typeIn.length() == 1 || typeIn.charAt(1) <91){
                    type+= "*";
                    hasReadMana = true;
                    power += "-1*";
                    toughness += "-1*";

                    subtype += "No Subtype*";
                    mana += typeIn + "*";
                    break;
                }
                //if the card has no subtype and no mana cost, like most lands
                if (typeIn.equals("Common") || typeIn.equals("Uncommon") || typeIn.equals("Rare")){
                    subtype += "No Subtype*";
                    type+="*";
                    hasReadRarity = true;
                     power += "-1*";
                    toughness += "-1*";
                    mana+="No Mana*";
                    hasReadMana= true;
                    rarity += typeIn + "*";
                    break;
                }
                if (typeIn.equals("Mythic")){
                    hasReadRarity = true;
                     power += "-1*";
                    toughness += "-1*";
                    subtype += "No Subtype,";
                    mana+= "No Mana*";
                    type+="*";
                    hasReadMana = true;
                    rarity += typeIn + " " + in.readString() +"*";
                    break;
                }
                type += typeIn +" ";
                
            }
            /*****************************************************/
            if (!hasReadMana){
                while (true){
                    String subtypeIn = in.readString();
                    //if creature with p/t
                    if (subtypeIn.contains("/")){
                        subtype+= "*";
                        int index = subtypeIn.indexOf("/");
                        power += subtypeIn.substring(0, index) + "*";
                        toughness +=subtypeIn.substring(index+1) + "*";
                        mana += in.readString()+"*";
                        break;
                    }
                    else{
                        //if planeswalker
                        if (subtypeIn.charAt(0) == '('){
                            String disregardThis = in.readString();
                            power+="-1*";
                            toughness += "-1*";
                            mana+= in.readString() +"*";
                            break;
                        }
                        //if has a subtype but is not planeswalker or creature, such as an aura
                        if (subtypeIn.length() == 1 || (subtypeIn.charAt(1) <91 && subtypeIn.charAt(1) >64)){

                            subtype+= "*";
                            power+="-1*";
                            toughness+= "-1*";
                            mana += subtypeIn + "*";
                            break;
                        }
                    }
                    subtype+= subtypeIn + " ";
                }
            }
            /************************************************************/
            if (!hasReadRarity){
                String rarityIn = in.readString();
                rarity+= rarityIn;
                /*if (rarityIn.equals("Common") || rarityIn.equals("Uncommon") || rarityIn.equals("Rare")){
                    hasReadRarity = true;
                    hasReadMana= true;
                    rarity += rarityIn + "*";
                }*/
                if (rarityIn.charAt(0) == 'M'){
                    hasReadRarity = true;
                    hasReadMana = true;
                    rarity +=" " + in.readString();
                }
                rarity+="*";
            }
            /*************************************************************/
            while (true){
                String artistIn = in.readString();
                /*try{
                    if (artistIn.charAt(0) < 58){
                        lookAtNum = false;
                        collectorNumber+=artistIn+"*";
                        
                        break;
                    }
                }
                catch(Exception e){
                }*/
                if (artistIn.equals("Aether")){
                    artist+="*";
                    String disregardThis = in.readString();
                    break;
                }
                artist+=artistIn+" ";
            }
            /***************************************************************/
        }
        String home = System.getProperty("user.home");
        String sep = System.getProperty("file.separator");
        
        File outputDirectory = new File(home+sep+"Documents"+sep+"CardFiles");
        if (!outputDirectory.exists()){
            try{
                outputDirectory.mkdir();
            }
            catch(Exception e){
                System.out.println("Directory not created.");
            }
        }
        String[] collectorNumberList = collectorNumber.split("\\*");
        String[] nameList = name.split("\\*");
        String[] typeList = type.split("\\*");
        String[] subtypeList = subtype.split("\\*");
        String[] powerList = power.split("\\*");
        String[] toughnessList = toughness.split("\\*");
        String[] manaList = mana.split("\\*");
        String[] rarityList = rarity.split("\\*");
        String[] artistList = artist.split("\\*");
        /*System.out.println(collectorNumberList.length);
        System.out.println(nameList.length);
        System.out.println(typeList.length);
        System.out.println(subtypeList.length);
        System.out.println(powerList.length);
        System.out.println(toughnessList.length);
        System.out.println(manaList.length);
        System.out.println(rarityList.length);
        System.out.println(artistList.length);*/
        try{
            for (int i = 0; i<collectorNumberList.length; i++){
                FileOutputStream fileOut = new FileOutputStream(home+sep+"Documents"+sep+"CardFiles"+sep+"Card#"+(i+1) +".ser");
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                objOut.writeObject(new AVB_TT_MagicCard(collectorNumberList[i], nameList[i], typeList[i], subtypeList[i], powerList[i], toughnessList[i],
                    manaList[i], rarityList[i], artistList[i]));
                objOut.close();
                fileOut.close();
            }
        }
        catch (Exception e){
            System.out.println("There was an error.");
            System.out.println(e);
        }
    }
}