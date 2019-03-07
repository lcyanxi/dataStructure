package lcyanxi.maputils;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.codehaus.jettison.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/11/15
 */
public class UDFStr4Map extends UDF {

    public static void main(String[] args) {

        String strJson = "{\"PROCESS\":\"com.douguo.recipe:magic_v1\",\"sdsfs\":\"13421\",\"dealtype\":[23,311,226]}";
        UDFStr4Map p = new UDFStr4Map();
        System.out.println(p.evaluate(strJson));

    }

    /**
     * @param strJson 输入一个json串
     * @return 返回一个map
     */
    public Map<String, String> evaluate(final String strJson) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(strJson)) {
            map = getMap4Json(strJson);
        }

        return map;
    }

    public static Map<String, String> getMap4Json(String strJson) {
        Map<String, String> map = new HashMap(16);
        try {
            JSONObject jsonObj = new JSONObject(strJson);
            Iterator it = jsonObj.keys();

            String key;
            String value;
            while (it.hasNext()) {
                key = (String)it.next();
                value = jsonObj.getString(key);
                map.put(key, value);
            }
        } catch (Exception e) {
            // nothing
        }

        return map;
    }
}
