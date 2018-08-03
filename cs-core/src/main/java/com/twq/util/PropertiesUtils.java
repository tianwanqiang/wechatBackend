package com.twq.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private static Properties PROPERTIES_RETMSG_MAPPER;
    private static Properties PROPERTIES_DYNPROPS_MAPPER;
    private static Properties PROPERTIES_API_FIELDS_MAPPER;
    private static Properties PROPERTIES_API_MAPPER;


    public static Properties getRetMapperInstans() {
        PROPERTIES_RETMSG_MAPPER = initProperties(PROPERTIES_RETMSG_MAPPER, "ret_mapper.properties");
        return PROPERTIES_RETMSG_MAPPER;
    }

    public static Properties getDynpropsMapperInstans() {
        PROPERTIES_DYNPROPS_MAPPER = initProperties(PROPERTIES_DYNPROPS_MAPPER, "dynprops.properties");
        return PROPERTIES_DYNPROPS_MAPPER;
    }

    public static Properties getApiFieldsMapperInstans() {
        PROPERTIES_API_FIELDS_MAPPER = initProperties(PROPERTIES_API_FIELDS_MAPPER, "api_check.properties");
        return PROPERTIES_API_FIELDS_MAPPER;
    }

    public static Properties getApiMapperInstans() {
        PROPERTIES_API_MAPPER = initProperties(PROPERTIES_API_MAPPER, "api_mapper.properties");
        return PROPERTIES_API_MAPPER;
    }


    public static Properties initProperties(Properties p, String filePath) {
        if (p != null)
            return p;
        InputStream inputStream = null;
        p = new Properties();
        try {
            inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(filePath);
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return p;
    }

}
