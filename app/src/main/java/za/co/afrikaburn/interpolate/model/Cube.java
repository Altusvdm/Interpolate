package za.co.afrikaburn.interpolate.model;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Altus on 2015/02/01.
 */
public class Cube extends SugarRecord<Cube> {
    public String identifier;
    public float battery;
    public int positionX;
    public int positionY;
    public CubeMode cubeMode;

    public Cube() {}

    public static void saveCube(Cube cube) {
        List<Cube> readerData = Cube.find(Cube.class, "identifier = ?", cube.identifier);
        if (readerData.size() == 0) {
            cube.save();
        } else {
            cube.id = readerData.get(0).id;
            cube.save();
        }
    }

    public static List<Cube> getAllCubes() {
        ArrayList<Cube> list = new ArrayList<>();
        Iterator<Cube> iterator = Cube.findAll(Cube.class);
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }
}
