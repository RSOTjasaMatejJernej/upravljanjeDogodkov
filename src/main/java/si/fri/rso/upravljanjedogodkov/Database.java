
package si.fri.rso.upravljanjedogodkov;

import java.util.ArrayList;
import java.util.List;


public class Database {
    private static List<Dogodek> dogodeks = new ArrayList<>();

    public static List<Dogodek> getDogodeks() {
        return dogodeks;
    }

    public static Dogodek getDogodek(String dogodekId) {
        for (Dogodek dogodek : dogodeks) {
            if (dogodek.getId().equals(dogodekId))
                return dogodek;
        }

        return null;
    }

    public static void addDogodek(Dogodek dogodek) {
        dogodeks.add(dogodek);
    }

    public static void deleteDogodek(String dogodekId) {
        for (Dogodek dogodek : dogodeks) {
            if (dogodek.getId().equals(dogodekId)) {
                dogodeks.remove(dogodek);
                break;
            }
        }
    }
}
