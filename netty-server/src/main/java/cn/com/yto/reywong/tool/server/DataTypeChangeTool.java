package cn.com.yto.reywong.tool.server;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: wangrui
 * Date: 14-1-14
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class DataTypeChangeTool {
    private static final Logger logger = LoggerFactory.getLogger(DataTypeChangeTool.class);

    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, Object> beanToMap(Object javaBean) {
        Map<String, Object> result = new HashMap<String, Object>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();
        //父类
        Method[] superMethods = javaBean.getClass().getSuperclass().getDeclaredMethods();
        Method[] resultMethod = Arrays.copyOf(methods, methods.length + superMethods.length);
        System.arraycopy(superMethods, 0, resultMethod, methods.length, superMethods.length);

        for (Method method : resultMethod) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(javaBean, (Object[]) null);
                    if (null != value) {
                        result.put(field, value);
                    }
                }
            } catch (Exception e) {
                logger.error("转换失败", e);
            }
        }
        return result;
    }

    /**
     * 将map转换成Javabean
     *
     * @param javabean javaBean
     * @param data     map数据
     */
    public static Object mapToBean(Object javabean, Map<String, Object> data) {
        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, new Object[]
                            {
                                    data.get(field)
                            });
                }
            } catch (Exception e) {
                logger.error("将map转换成Javabean转换失败", e);
            }
        }

        return javabean;
    }

    /**
     * 将json对象转换成Map
     *
     * @param json
     * @return Map对象
     */
    public static Map<String, Object> jsonToMap(String json) {
        ObjectMapper mapper = getDefaultObjectMapper();
        Map result = null;
        try {
            result = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            logger.error("Json转换成Map失败,json=" + json);
            logger.error("Json转换成Map失败", e);
        }
        return result;
    }

    /**
     * 将json对象转换成Map
     *
     * @param json
     * @return Map对象
     */
    public static List<Map<String, Object>> jsonToList(String json) {
        ObjectMapper mapper = getDefaultObjectMapper();
        List<Map<String, Object>> result = null;
        try {
            result = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (IOException e) {
            logger.error("Json转换成List失败,json=" + json);
            logger.error("Json转换成List失败", e);
        }
        return result;
    }

    /**
     * map转换成json对象
     *
     * @param mapData
     * @return
     */
    public static String mapToJson(Map<String, Object> mapData) {
        String result = "";
        ObjectMapper mapper = getDefaultObjectMapper();
        try {
            result = mapper.writeValueAsString(mapData);
        } catch (IOException e) {
            logger.error("Map转换成Json失败", e);
        }
        return result;
    }

    /**
     * 将javaBean转换成JSONObject
     *
     * @param bean javaBean
     * @return json对象
     */
    public static String beanToJSON(Object bean) {
        String result = "";
        ObjectMapper mapper = getDefaultObjectMapper();
        try {
            result = mapper.writeValueAsString(bean);
        } catch (IOException e) {
            logger.error("Bean转换成Json失败", e);
        }
        return result;
    }

    /**
     * JSONObject转换成javabean
     *
     * @param javabean
     * @param jsonObject
     */
    public static Object jsonToBean(Object javabean, String jsonObject) {
        Map<String, Object> datas = jsonToMap(jsonObject);
        return mapToBean(javabean, datas);
    }


    /**
     * List<String>转化成String[]
     *
     * @param datas
     * @return
     */
    public static String[] listToStrings(List<String> datas) {
        String[] result = null;
        if (datas != null && datas.size() > 0) {
            int t = datas.size();
            result = new String[t];
            for (int i = 0; i < t; i++) {
                result[i] = datas.get(i);
            }
        }
        return result;
    }

    /**
     * String[]转化成List<String>
     *
     * @param datas
     * @return
     */
    public static List<String> stringsToList(String[] datas) {
        List<String> result = new ArrayList<String>();
        if (datas != null && datas.length > 0) {
            int t = datas.length;
            for (int i = 0; i < t; i++) {
                result.add(datas[i]);
            }
        }
        return result;
    }

    /**
     * list转换成jsonArray
     *
     * @param listData
     * @return
     */
    public static String listToJson(List listData) {
        String result = "";
        ObjectMapper mapper = getDefaultObjectMapper();
        try {
            result = mapper.writeValueAsString(listData);
        } catch (IOException e) {
            logger.error("List转换成Json失败", e);
        }
        return result;
    }


    public static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //设置将对象转换成JSON字符串时候:包含的属性不能为空或"";
        //Include.Include.ALWAYS 默认
        //Include.NON_DEFAULT 属性为默认值不序列化
        //Include.NON_EMPTY 属性为 空（""）  或者为 NULL 都不序列化
        //Include.NON_NULL 属性为NULL 不序列化
//        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        //设置将MAP转换为JSON时候只转换值不等于NULL的
//        mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置有属性不能映射成PO时不报错
//        mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    /**
     * 将数据流转化成字符串
     *
     * @param is 数据流
     * @return 解析后的字符串
     */
    public static String streamToString(InputStream is) {
        String result = "";
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        String line = "";
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is, "UTF-8");
            reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            result = sb.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("解析数据流失败!!", e);
        } catch (IOException e) {
            logger.error("解析数据流失败!!", e);
        } finally {
            try {
                is.close();
                isr.close();
                reader.close();
            } catch (IOException e) {
                logger.error("数据流关闭失败！", e);
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        String json = "{\"key\":\"1\",\"data\":\"111\"}";
        Map map = DataTypeChangeTool.jsonToMap(json);
        System.out.println(map.toString());
//        String y = StringUtils.encryptMD5("reywong");
//        System.out.println(y);
        //准备数据
//        Name name1 = new Name("zhang","san");
//        Name name2 = new Name("li","si");
//        Name name3 = new Name("wang","wu");
//        Student student1 = new Student(1,name1,"一班",new Date());
//        Student student2 = new Student(2,name2,"二班",new Date());
//        Student student3 = new Student(3,name3,"三班",new Date());
//        List<Student> studentList = new ArrayList<Student>();
//        studentList.add(student1);
//        studentList.add(student2);
//        studentList.add(student3);
//        Map<String,Student> studentMap = new HashMap<String, Student>();
//        studentMap.put("1", student1);
//        studentMap.put("2", student2);
//        studentMap.put("3", student3);
//        Student json2object = null;
//        List<Student> json2list = null;
//        Map<String,Student> json2map = null;
//        ObjectMapper mapper = getDefaultObjectMapper();
//
//        /* Object --> JSON */
//        String object4json = mapper.writeValueAsString(new Object());
//        System.out.println("Object ----> JSON");
//        System.out.println(object4json);
//        System.out.println("------------------------------------------------------");
//
//        /* List<Object> --> JSON */
//        String listforjson = mapper.writeValueAsString(studentList);
//        System.out.println("List<Object> ----> JSON");
//        System.out.println(listforjson);
//        System.out.println("------------------------------------------------------");
//
//        /* Map<String,Object> ----> JSON */
//        String map4json = mapper.writeValueAsString(studentMap);
//        System.out.println("Map<String,Object> ----> JSON");
//        System.out.println(map4json);
//        System.out.println("------------------------------------------------------");
//
//        /* JSON --> Object */
//        json2object = mapper.readValue(object4json, Student.class);
//        System.out.println("JSON ----> Object");
//        System.out.println(json2object);
//        System.out.println("------------------------------------------------------");
//        /* JSON --> List<Object> */
//        json2list = mapper.readValue(listforjson, new TypeReference<List<Objects>>() {
//        });
//        System.out.println("JSON --> List<Object>");
//        System.out.println(json2list.toString());
//        System.out.println("------------------------------------------------------");
//        /* JSON --> Map<String,Object> */
//        json2map = mapper.readValue(map4json, new TypeReference<Map<String, Student>>() {
//        });
//        System.out.println("JSON --> Map<String,Object>");
//        System.out.println(json2map.toString());

//        List<CarPlateList> list = new ArrayList<CarPlateList>();
//        CarPlateList carPlateList = new CarPlateList();
//        carPlateList.setParkid("1");
//        carPlateList.setPlateid("A1111");
//        list.add(carPlateList);
//        carPlateList = new CarPlateList();
//        carPlateList.setParkid("2");
//        carPlateList.setPlateid("A2222");
//        list.add(carPlateList);
//        Map map = new HashMap();
//        map.put("parkid", "pa");
//        map.put("datas", list);
//        System.out.println(DataTypeChangeTool.mapToJson(map));

    }
}
