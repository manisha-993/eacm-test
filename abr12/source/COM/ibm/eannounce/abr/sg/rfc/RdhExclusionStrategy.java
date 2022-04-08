/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * this is strategy for converting data entity to json format
 * 
 * @author will
 * 
 */
public class RdhExclusionStrategy implements ExclusionStrategy
{

    @Override
    public boolean shouldSkipClass(Class<?> arg0)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f)
    {
        return f.getAnnotation(Foo.class) != null;
    }

}
