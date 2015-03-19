package Huffman;

import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by Etienne on 05/03/15.
 * Provides a demo of this project.
 */
public class main {

    public static void main(String[] args){
        Huffman f = new Huffman();
        Scanner sc = new Scanner(System.in);
        String sentence = "Het houdt niet op, niet vanzelf.";
        System.out.println("Please enter a text to encrypt.");
        sentence = sc.nextLine();

        /*

        sentence =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In et est ac velit eleifend ultricies sit amet quis turpis. Aenean non nunc eros. Integer non magna porta mauris euismod ornare vitae ut ipsum. Vestibulum eget ultrices risus. Aliquam suscipit, justo sit amet vestibulum hendrerit, quam lectus dignissim nisl, sed feugiat elit sapien ac justo. Suspendisse viverra id dui in lobortis. Suspendisse sodales arcu elit. In maximus, ex ultricies ultrices rutrum, nisi urna dictum leo, ut consectetur metus ante sit amet velit. Donec eget elit ac mi tincidunt placerat.\n" +
                "\n" +
                "Phasellus in libero pellentesque, ultricies urna quis, rutrum lacus. Fusce purus velit, interdum vitae tincidunt at, finibus quis tellus. Proin eu ante a libero elementum malesuada. Quisque sit amet maximus tellus. Nunc congue dignissim metus eu sodales. Phasellus at pharetra felis. Morbi commodo porta dictum. Cras a sapien tempus, aliquam felis eget, faucibus nisi. Morbi in gravida sapien, sit amet blandit lectus. Sed dictum nibh ut dui cursus, laoreet fringilla sapien pulvinar. Nam at pharetra ex, sed rutrum tortor. Donec condimentum eros sit amet fringilla malesuada. Mauris vitae posuere felis. Nam aliquam dapibus diam. Donec leo lacus, sagittis a urna ut, commodo auctor justo.\n" +
                "\n" +
                "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In nec nulla tempor, posuere nisi vitae, volutpat nisi. Nam tristique accumsan augue id bibendum. Praesent facilisis dapibus sodales. Maecenas posuere enim nibh, vitae rutrum ligula vehicula interdum. Nunc non arcu vestibulum, feugiat nisi vel, condimentum lorem. Aliquam erat volutpat. Duis sed nibh euismod, ullamcorper ligula varius, ultrices nibh.\n" +
                "\n" +
                "Vivamus fermentum tempor euismod. Nunc nec aliquam orci. Quisque hendrerit, lacus eu euismod volutpat, ante libero consequat mi, eget auctor eros lectus vitae eros. Mauris finibus mattis tortor, eu imperdiet magna aliquam id. Morbi lacus neque, pharetra at ligula vel, pulvinar ultrices ex. Praesent non posuere mauris. Sed vel facilisis quam. Mauris nec lacus ac elit pellentesque aliquet. Cras dictum nisl iaculis urna vulputate, vel vulputate nibh lacinia. Maecenas luctus suscipit magna eget euismod. Praesent id posuere felis. Aenean quis elit accumsan, mattis ante ut, ornare ante. Fusce pharetra ultrices magna euismod aliquam.\n" +
                "\n" +
                "Donec lobortis leo ut erat iaculis, eget ullamcorper eros sodales. In consectetur vestibulum vehicula. Suspendisse lacinia porta consectetur. Integer dictum nulla ac mi commodo iaculis. Pellentesque tempor libero nec arcu dictum, in faucibus urna sagittis. Vivamus ullamcorper diam et est malesuada, eu tincidunt orci lacinia. Donec nulla libero, accumsan eget condimentum id, lacinia vel urna. Nunc sit amet vulputate justo, ac vulputate magna. Vivamus ullamcorper, nulla eget malesuada finibus, libero eros finibus diam, maximus ultricies nulla urna sit amet elit. Nunc est tortor, tristique vitae felis vitae, luctus elementum turpis.";

        */

        out.println("Encrypting text \"" + sentence + "\"");

        String zeroesandones = f.encrypt(sentence);
        out.println("\r");
        out.println("Encrypted as:");
        out.println(zeroesandones);

        String decodedsentence = f.decrypt(zeroesandones);
        out.println("\r");
        out.println("Decrypted as:");
        out.println(decodedsentence);
    }
}
