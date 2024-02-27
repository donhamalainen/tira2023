package oy.interact.tira.student;

import java.util.Comparator;
import oy.interact.tira.model.Coder;

public class CoderNameComparator implements Comparator<Coder> {

    @Override
    public int compare(Coder coder1, Coder coder2) {
        return coder1.getCoderName().compareTo(coder2.getCoderName());
    }
}
