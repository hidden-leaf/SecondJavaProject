
/**
 * Write a description of class SortedArray here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.lang.Comparable;

public class SortedArrayList<E extends Comparable<? super E>> extends ArrayList<E>
{

    /**
    *  Insert in to an arraylist in order
    */
    public boolean add(E e)
    {
        if(!isEmpty())
        {
           int value = size();
           for (int i = size(); i > 0; i--)
            {
                if (e.compareTo(get(i-1))<0)
                {
                    value = (i-1);
                }
            }
            super.add(value, e);
        }
        else
        {
            super.add(0, e);
        }
        return true;
    }
    
}

