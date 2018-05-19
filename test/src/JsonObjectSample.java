
import org.json.JSONException;
import org.json.JSONObject;


public class JsonObjectSample {

    public static void main(String[] args) {
        createJsonByJavaBean();
        System.out.println("***************");
        createJson();
    }
    private static void createJsonByJavaBean() {
        PersonInfo info = new PersonInfo();
        info.setName("John");
        info.setSex("male");
        info.setAge(22);
        info.setStudent(true);
        info.setHobbies(new String[] {"hiking", "swimming"});

        JSONObject obj = new JSONObject(info);
        System.out.println(obj);
    }

    private static void createJson() {
        JSONObject obj = new JSONObject();
        obj.put("name", "John");
        obj.put("sex", "male");
        obj.put("age", 22);
        obj.put("is_student", true);
        obj.put("hobbies", new String[] {"hiking", "swimming"});
        //调用toString()方法可直接将其内容打印出来
        System.out.println(obj.toString());
    }

}