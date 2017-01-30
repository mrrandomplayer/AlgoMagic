import java.util.ArrayList;
import java.io.*;
/**
 * Write a description of class AVB_TT_SymbolTableCreator here.
 * 
 * Adam Van Buren and Theo Trevisan
 * Creates the symbol tables
 * 
 */
public class AVB_TT_SymbolTableCreator
{
    public static void main(){
        AVB_TT_MagicCard[] cards = new AVB_TT_MagicCard[194]; //194 magic cards in the set
        String home = System.getProperty("user.home"); //this and the next line makes card accessing easier
        String sep = System.getProperty("file.separator");
        for (int i = 0; i < 194; i++){//this whole for loop takes in the data for each card from the generated ser files and adds them to the list of cards
            try {
                FileInputStream fileIn = new FileInputStream(home + sep + "Documents" + sep + "CardFiles" + sep + "Card#" + (i + 1) + ".ser"); //this and the next line set up the input stream
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
                cards[i] = (AVB_TT_MagicCard)objIn.readObject(); //actually adds them to the list
                objIn.close();//necessary for ObjectInputStream
                fileIn.close();//see above, for FileInputStream
            }
            catch (Exception e){ //see above again
                System.out.println("There was an error in reading the files.");
                System.out.println(e);
            }
        }
        //done importing the data at this point, next we actually write in the sets of shared fields
        ArrayList<RedBlackBST<String, SET<AVB_TT_MagicCard>>> parameters = new ArrayList<RedBlackBST<String, SET<AVB_TT_MagicCard>>>();//one array list for each searchable card property
        for (int i = 0; i < 10; i++){//the 10 are Collector's Number, name, type, subtype, power, toughness, color, converted mana cost, artist, and rarity
            parameters.add(new RedBlackBST<String, SET<AVB_TT_MagicCard>>());
            for (int j = 0; j < 194; j++){//for each searchable property, go through each card and add the attribute of that card to the database
                String[] words = null;//needed for splitting stuff later
                switch (i){//it's the least bad way to implement it
                    case 0: //for each field, it's basically the same thing: get the respective data field and either create a new set for the card or add it to the set of cards that have that same attribute for that field
                            if (parameters.get(0).contains(cards[j].getCollectorNumber())){//collector's number, technically this is kinda redundant since each card definitionally has a different collector's number, but it's good protocol to do it similarly for each data field
                                 parameters.get(0).get(cards[j].getCollectorNumber()).add(cards[j]);
                            }
                            else{
                                 parameters.get(0).put(cards[j].getCollectorNumber(), new SET<AVB_TT_MagicCard>());
                                 parameters.get(0).get(cards[j].getCollectorNumber()).add(cards[j]);
                            }
                    break;
                    case 1: words = cards[j].getName().split(" ");//you can search for any word in a card's name, so we split the name up into individual word strings, we do the same for type, subtype, and artist
                            for (int k = 0; k < words.length; k++){//you have to do this for each word in the card's name, see above
                                if (parameters.get(1).contains(words[k])){//if the word is already indexed in the red-black bst, add the CARD (this is important for all of them) to the set for that word
                                     parameters.get(1).get(words[k]).add(cards[j]);
                                }
                                else{//otherwise create a new set for that word and add the card
                                     parameters.get(1).put(words[k], new SET<AVB_TT_MagicCard>());
                                     parameters.get(1).get(words[k]).add(cards[j]);
                                }
                            }
                    break;
                    case 2: words = cards[j].getType().split(" ");//same deal as name, as cards can have multiple types and we want to be able to search for either one
                            for (int k = 0; k < words.length; k++){
                                if (parameters.get(2).contains(words[k])){
                                     parameters.get(2).get(words[k]).add(cards[j]);
                                }
                                else{
                                     parameters.get(2).put(words[k], new SET<AVB_TT_MagicCard>());
                                     parameters.get(2).get(words[k]).add(cards[j]);
                                }
                            }
                    break;
                    case 3: words = cards[j].getSubtype().split(" ");//again, cards can have multiple subtypes
                            for (int k = 0; k < words.length; k++){
                                if (parameters.get(3).contains(words[k])){
                                     parameters.get(3).get(words[k]).add(cards[j]);
                                }
                                else{
                                     parameters.get(3).put(words[k], new SET<AVB_TT_MagicCard>());
                                     parameters.get(3).get(words[k]).add(cards[j]);
                                }
                            }
                    break;
                    case 4: if (parameters.get(4).contains(cards[j].getPower())){//there's only one string for power, so we don't have to split like above
                                 parameters.get(4).get(cards[j].getPower()).add(cards[j]);
                            }
                            else{
                                 parameters.get(4).put(cards[j].getPower(), new SET<AVB_TT_MagicCard>());
                                 parameters.get(4).get(cards[j].getPower()).add(cards[j]);
                            }
                    break;
                    case 5: if (parameters.get(5).contains(cards[j].getToughness())){//toughness of the card, if it's a creature
                                 parameters.get(5).get(cards[j].getToughness()).add(cards[j]);
                            }
                            else{
                                 parameters.get(5).put(cards[j].getToughness(), new SET<AVB_TT_MagicCard>());
                                 parameters.get(5).get(cards[j].getToughness()).add(cards[j]);
                            }
                    break;
                    case 6: words = arrayOfOnes(cards[j].getColor());//cards can have multiple colors, but color is represented as one string with one letter for each color (ie just white is "W", while white-black would be "WB"), so I made a method to split the string up into one-character strings (I'm lazy)
                            for (int k = 0; k < words.length; k++){//we need to go through each color
                                if (parameters.get(6).contains(words[k])){
                                     parameters.get(6).get(words[k]).add(cards[j]);
                                }
                                else{
                                     parameters.get(6).put(words[k], new SET<AVB_TT_MagicCard>());
                                     parameters.get(6).get(words[k]).add(cards[j]);
                                }
                            }
                    break;
                    case 7: if (parameters.get(7).contains(cards[j].getCMC())){//the converted mana cost (how much it costs to cast)
                                 parameters.get(7).get(cards[j].getCMC()).add(cards[j]);
                            }
                            else{
                                 parameters.get(7).put(cards[j].getCMC(), new SET<AVB_TT_MagicCard>());
                                 parameters.get(7).get(cards[j].getCMC()).add(cards[j]);
                            }
                    break;
                    case 8: words = cards[j].getArtist().split(" ");//artists have first and last names (usually) so someone should be able to search any word in the artist's name
                            for (int k = 0; k < words.length; k++){
                                if (parameters.get(8).contains(words[k])){
                                     parameters.get(8).get(words[k]).add(cards[j]);
                                }
                                else{
                                     parameters.get(8).put(words[k], new SET<AVB_TT_MagicCard>());
                                     parameters.get(8).get(words[k]).add(cards[j]);
                                }
                            }
                    break;
                    case 9: if (parameters.get(9).contains(cards[j].getRarity())){//the rarity of the card (common, uncommon, rare, mythic rare)
                                 parameters.get(9).get(cards[j].getRarity()).add(cards[j]);
                            }
                            else{
                                 parameters.get(9).put(cards[j].getRarity(), new SET<AVB_TT_MagicCard>());
                                 parameters.get(9).get(cards[j].getRarity()).add(cards[j]);
                            }
                    break;
                    default: System.out.println("Something's not right.");
                    break;
                }
            }
            //now we're done putting the files in red-black BSTs, now we write them out into ser files
            try{//this is very similar to the
                FileOutputStream fileOut = new FileOutputStream(home + sep + "Documents" + sep + "CardFiles" + sep + "Field#" + (i + 1) + ".ser");
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                objOut.writeObject(parameters.get(i));
                objOut.close();
                fileOut.close();
            }
            catch (Exception e){
                System.out.println("There was an error in storing the data.");
                System.out.println(e);
            }
        }
    }
    public static String[] arrayOfOnes(String string){//this just splits a string into multiple one-character strings, this is just needed for the color(s) of the card
        int l = string.length();
        String[] strings = new String[l];
        for (int i = 0; i < l; i++){
            strings[i] = string.substring(i, i + 1);
        }
        return strings;
    }
}
