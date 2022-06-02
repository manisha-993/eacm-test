/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

/**
 * A Utility class to set default value for RDH classes
 * 
 * @author Li Chao Ji (jilichao@cn.ibm.com)
 * @since 08/11/2016
 */
public class RdhClassDefaultValuesUtility
{

    private static final String json = readJsonFile();;

    /**
     * According to the given Json String, set default values for RDH classes
     * 
     * @param rdhBase RDH classes, please assign this to the parameter
     * @param compType type
     * @throws Exception
     */
    public static void setDefaultValues(RdhBase thiz, String compType) throws Exception
    {
        Gson gson = new GsonBuilder().create();

        LinkedTreeMap<String, LinkedTreeMap<String, String>> entities = findDefaultValues(thiz.getClass()
                .getSimpleName(), compType);
        HashMap<String, Field> objFields = getAllDeclaredFeilds(thiz.getClass());

        for (String fieldName : objFields.keySet())
        {
            Field objField = objFields.get(fieldName);
            objField.setAccessible(true);
            
            if (entities.containsKey(fieldName))
            {
                LinkedTreeMap<String, String> entityMap = entities.get(fieldName);

                List<Object> objMember = new ArrayList<Object>();
                try
                {
                    if (objField.getGenericType() instanceof ParameterizedType)
                    {
                        ParameterizedType fieldType = (ParameterizedType) objField.getGenericType();
                        Class<?> actualType = (Class<?>) fieldType.getActualTypeArguments()[0];
                        LinkedTreeMap<String, String> tmpMap = new LinkedTreeMap<String, String>();
                        for (String entityKey : entityMap.keySet())
                        {
                            tmpMap.put(entityKey.toUpperCase(), entityMap.get(entityKey));
                        }

                        Object entity = gson.fromJson(gson.toJson(tmpMap), actualType);

                        objMember.add(entity);
                        objField.set(thiz, objMember);
                    } else
                    {
                        continue;
                    }

                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                if (objField.getGenericType() instanceof ParameterizedType)
                {
                    List<Object> objMember = new ArrayList<Object>();
                    java.lang.reflect.ParameterizedType fieldType = (ParameterizedType) objField.getGenericType();
                    Class<?> actualType = (Class<?>) fieldType.getActualTypeArguments()[0];

                    objMember.add(actualType.newInstance());
                    objField.set(thiz, objMember);
                }
            }
        }
//        for (String key : entities.keySet())
//        {
//            Field objField = objFields.get(key);
//
//            LinkedTreeMap<String, String> entityMap = entities.get(key);
//
//            if (objField != null)
//            {
//                objField.setAccessible(true);
//
//                try
//                {
//                    java.lang.reflect.ParameterizedType fieldType = (ParameterizedType) objField.getGenericType();
//                    Class<?> actualType = (Class<?>) fieldType.getActualTypeArguments()[0];
//
//                    LinkedTreeMap<String, String> tmpMap = new LinkedTreeMap<String, String>();
//                    for (String entityKey : entityMap.keySet())
//                    {
//                        tmpMap.put(entityKey.toUpperCase(), entityMap.get(entityKey));
//                    }
//
//                    Object entity = gson.fromJson(gson.toJson(tmpMap), actualType);
//
//                    objMember.add(entity);
//                    objField.set(thiz, objMember);
//
//                } catch (IllegalArgumentException e)
//                {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    /**
     * According to the parameters, get part Json String for inner Class
     * @param className
     * @param compType
     * @param innerClassName
     * @return
     * @throws Exception
     */
    public static String getSpecificJson(String className, String compType, String innerClassName)
            throws Exception
    {
        String result = "{}";
        Gson gson = new GsonBuilder().create();

        LinkedTreeMap<String, LinkedTreeMap<String, String>> objects = findDefaultValues(className, compType);

        if (objects.containsKey(innerClassName))
        {
            LinkedTreeMap<String, String> innerMap = objects.get(innerClassName);
            LinkedTreeMap<String, String> tmpMap = new LinkedTreeMap<String, String>();
            for (String key : innerMap.keySet())
            {
                tmpMap.put(key.toUpperCase(), innerMap.get(key));
            }
            result = gson.toJson(tmpMap);
        }

        return result;
    }

    /**
     * According to the parameters, return specific object
     * @param className
     * @param compType
     * @param innerClassName
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T getSpecificObject(String className, String compType, String innerClassName, Class<T> clazz)
            throws Exception
    {
        T result = null;
        Gson gson = new GsonBuilder().create();

        String specJson = getSpecificJson(className, compType, innerClassName);
        if (specJson != null)
        {
            result = gson.fromJson(specJson, clazz);
        }
        return result;
    }

    /**
     * Read Json String from a given Json file, the location of Json file should
     * be configured in rdhconfig.properties
     * 
     * @return Json String
     */
    private static String readJsonFile()
    {
        StringBuffer result = new StringBuffer();
        try
        {
            File jsonFile = null;
            String jsonFilePath = ConfigUtils.getProperty("rdh.class.default.values.json");

            if (jsonFilePath == null)
            {
                jsonFile = new File("./RdhJavaClassDefaultValues.json");
            } else
            {
                jsonFile = new File(ConfigUtils.getProperty("rdh.class.default.values.json"));
            }
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                result.append(line);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return result.toString();
    }

    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    private static LinkedTreeMap<String, LinkedTreeMap<String, String>> findDefaultValues(String className,
            String compType) throws Exception
    {
        LinkedTreeMap<String, LinkedTreeMap<String, String>> values = null;

        Gson gson = new GsonBuilder().create();
        HashMap<String, List<LinkedTreeMap>> output = gson.fromJson(json, HashMap.class);

        List<LinkedTreeMap> classList = output.get("RdhJavaClassDefaultValues");

        if (classList == null || classList.size() < 1)
        {
            throw new Exception("Element 'RdhJavaClassDefaultValues' does not exist in Json file");
        }

        for (LinkedTreeMap cls : classList)
        {
            if (cls.get("rdhClassName") != null && cls.get("rdhClassName").equals(className))
            {
                List<LinkedTreeMap> types = (List<LinkedTreeMap>) cls.get("types");

                if (types == null || types.size() < 1)
                {
                    throw new Exception("Element 'types' does not exist in Json file");
                }

                for (LinkedTreeMap entities : types)
                {
                    if (entities.get("key") != null && entities.get("key").equals(compType))
                    {
                        values = (LinkedTreeMap) entities.get("values");
                    }
                }
            }
        }
        if (values == null || values.size() < 1)
        {
            throw new Exception("Cannot find default value for class " + className + " compType: " + compType);
        }
        return values;
    }

    /**
     * Get all declared fields of clazz, including the fields of its supper
     * Classes
     * 
     * @param clazz
     * @return
     */
    private static HashMap<String, Field> getAllDeclaredFeilds(Class<?> clazz)
    {
        HashMap<String, Field> result = new HashMap<String, Field>();
        //        for (; clazz != Object.class; clazz = clazz.getSuperclass())
        //        {
        try
        {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields)
            {
                result.put(field.getName(), field);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        //        }
        return result;
    }
}
