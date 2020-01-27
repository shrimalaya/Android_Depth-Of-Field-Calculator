package ca.programDemo.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LensManager implements Iterable<Lens> {
    private List<Lens> lenses = new ArrayList<>();

    public void add (Lens lens) {lenses.add(lens);}

    /*
        Singleton support
    */

    private static LensManager instance;

    private LensManager() {
        //prevent anyone else from instantiating object
    }

    public static LensManager getInstance() {
        if(instance == null) {
            instance = new LensManager();
            instance.add(new Lens("Canon", 1.8, 50));
            instance.add(new Lens("Tamron", 2.8, 90));
            instance.add(new Lens("Sigma", 2.8, 200));
            instance.add(new Lens("Nikon", 4, 200));
        }
        return instance;
    }

    /*
        Normal Object code
     */
    @Override
    public Iterator<Lens> iterator() {
        return lenses.iterator();
    }

    public int getManagerSize() {
        int count = 0;
        for(Lens lens: lenses)
            count++;

        return count;
    }
}
