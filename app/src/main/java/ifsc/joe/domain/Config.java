package ifsc.joe.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config 
{
    private static final Properties props = new Properties();

    static 
    {
        try 
        {
            InputStream in = Config.class.getClassLoader().getResourceAsStream("joe.properties");
            
            if (in == null) 
            {
                throw new RuntimeException("Arquivo joe.properties não encontrado no classpath!");
            }

            props.load(in);
            in.close();
        }
        catch (IOException e) 
        {
            throw new RuntimeException("Erro ao carregar joe.properties: " + e.getMessage());
        }
    }

    public static String getString(String key) 
    {
        return props.getProperty(key);
    }

    public static int getInt(String key) 
    {
        return Integer.parseInt(props.getProperty(key));
    }

    public static boolean getBoolean(String key) 
    {
        return Boolean.parseBoolean(props.getProperty(key));
    }

    public static double getDouble(String key) 
    {
        return Double.parseDouble(props.getProperty(key));
    }
}
