public class PersonInfo {

    private String name;
    private String sex;
    private int age;
    private boolean isStudent;
    private String[] hobbies;

    public void setName(String name) {
        this.name = name;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }
    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }
    //getter不能少
    public String getName() {
        return name;
    }
    public String getSex() {
        return sex;
    }
    public int getAge() {
        return age;
    }
    public boolean isStudent() {
        return isStudent;
    }
    public String[] getHobbies() {
        return hobbies;
    }
}