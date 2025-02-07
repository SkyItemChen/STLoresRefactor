package silentdream.stlores.uitl;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {
    public static String parseColor(String s) {
        return s.replace("&", "§").replace("§§", "&");
    }
    public static String parseColorTwo(String s) {
        return s.replace("§", "&");
    }
    public static List<String> parseColorList(List<String> ls){
        List<String> newList = new ArrayList<>();
        for (String s : ls) {
            newList.add(s.replace("&", "§").replace("§§", "&"));
        }
        return newList;
    }
}
