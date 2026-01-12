package Chapter_01.DAO;

/**
 * 자바빈 규약을 따르는 오브젝트를 생성한다.
 * 현대에 자바빈(빈)은 다음 두 가지 관례를 따라 만들어진 오브젝트를 가리킨다.
 * 디폴트 생성자, 프로퍼티
 */
public class User {
    String id;
    String name;
    String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
