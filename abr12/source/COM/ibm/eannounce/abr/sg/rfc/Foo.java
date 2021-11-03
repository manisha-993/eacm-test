/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * use to mark exclude field in RdhExclusionStrategy
 * 
 * @author will
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
{ ElementType.FIELD })
public @interface Foo
{
    // Field tag only annotation
}
