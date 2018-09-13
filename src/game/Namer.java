/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author voice
 */
public class Namer {
    private Random r = new Random();
    private HashSet<Integer> favored = new HashSet<>();
    private final String[] syllables = {
        "ab",
        "eb",
        "ib",
        "ob",
        "ub",
        "yb",
        "ac",
        "ec",
        "ic",
        "oc",
        "uc",
        "yc",
        "ad",
        "ed",
        "id",
        "od",
        "ud",
        "yd",
        "ae",
        "ee",
        "ie",
        "oe",
        "ue",
        "ye",
        "ad",
        "ed",
        "id",
        "od",
        "ud",
        "yd",
        "af",
        "ef",
        "if",
        "of",
        "uf",
        "yf",
        "ag",
        "eg",
        "ig",
        "og",
        "ug",
        "yg",
        "ah",
        "eh",
        "ih",
        "oh",
        "uh",
        "yh",
        "ah",
        "eh",
        "ih",
        "oh",
        "uh",
        "yh",
        "aj",
        "ej",
        "ij",
        "oj",
        "uj",
        "yj",
        "ak",
        "ek",
        "ik",
        "ok",
        "uk",
        "yk",
        "al",
        "el",
        "il",
        "ol",
        "ul",
        "yl",
        "am",
        "em",
        "im",
        "om",
        "um",
        "ym",
        "an",
        "en",
        "in",
        "on",
        "un",
        "yn",
        "ap",
        "ep",
        "ip",
        "op",
        "up",
        "yp",
        "aq",
        "eq",
        "iq",
        "oq",
        "uq",
        "yq",
        "ar",
        "er",
        "ir",
        "or",
        "ur",
        "yr",
        "as",
        "es",
        "is",
        "os",
        "us",
        "ys",
        "at",
        "et",
        "it",
        "ot",
        "ut",
        "yt",
        "av",
        "ev",
        "iv",
        "ov",
        "uv",
        "yv",
        "aw",
        "ew",
        "iw",
        "ow",
        "uw",
        "yw",
        "ax",
        "ex",
        "ix",
        "ox",
        "ux",
        "yx",
        "az",
        "ez",
        "iz",
        "oz",
        "uz",
        "yz",
    };
    public Namer(){
        for (int i = 0; i < 10; i++){
            favored.add(r.nextInt(syllables.length));
        }
    }
    public String getName(String start){
        Iterator fit = favored.iterator();
        
        for (int i = 0; i < favored.size(); i++){
            if (r.nextInt(4) == 3) break;
            else{
                if (fit.hasNext()) fit.next();
                else fit = favored.iterator();
            }
        }
        if (!fit.hasNext()) fit = favored.iterator();
        
        return start + syllables[r.nextInt(syllables.length)] + syllables[(int) fit.next()];
    }
}
