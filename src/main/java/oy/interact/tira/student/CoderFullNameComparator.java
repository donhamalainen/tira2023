package oy.interact.tira.student;

import java.util.Comparator;
import oy.interact.tira.model.Coder;

public class CoderFullNameComparator implements Comparator<Coder> {

    @Override
    public int compare(Coder coder1, Coder coder2) {
        return coder1.getFullName().compareTo(coder2.getFullName());
    }
}
