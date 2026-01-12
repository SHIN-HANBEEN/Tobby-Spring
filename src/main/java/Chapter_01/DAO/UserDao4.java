package Chapter_01.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 기존 상속 방식을 활용한 템프릿 메서드패턴과 팩토리 메서드 패턴은 상속을 활용한 패턴들이기 때문에
 * 그 자체로 단점들을 가지고 있다.
 * UserDao4 부터는 합성을 통해서 관심사를 분리해낼 것이다.
 * -
 * UserDao4 에서 합성을 통해 관심사를 성공적으로 분리해냈다.
 * 하지만 이 경우에도 다음과 같은 두 가지 문제점이 발생했다. 첫째는 SimpleConnectionMaker 의 메소드가 문제다.
 * simpleConnectionMaker.makeNewConnection( ) 을 호출하고 있는데, 고객사가 메서드 명을
 * 다른 것으로 사용하거나 나중에 다른 메서드로 바뀌면 수정해야 할 부분이 한 두개가 아니게 된다.
 * 두 번째는 DB 커넥션을 제공하는 클래스가 어떤 것인지를 UserDao 가 구체적으로 알고 있어야 한다는 점이다.
 * -
 * 이런 문제의 근본적인 원인은 UserDao 가 바뀔 수 있는 정보, 즉 다른 관심사에 대해서 너무나도
 * 많은 것을 알고 있기 때문에 그렇다. 어떤 클래스가 쓰일지, 그 클래스에서 커넥션을 가져오는 메서드 이름이
 * 무엇인지 까지 명확하게 알고 있는 것이다.
 * -
 * 클래스를 분리하면서도 이런 문제를 해결하는 가장 좋은 방법은 인터페이스의 도입이다.
 * 두 개의 클래스가 서로 긴밀하게 연결되어 있지 않도록 중간에 추상적인 느슨한 연결고리를 만들어주는 것이다.
 * 추상화란 어떤 것들의 공통적인 성격을 뽑아내는 것으로 자바가 추상화를 제공하는 가장 유용한 도구는 바로 인터페이스이다.
 */
public class UserDao4 {
    private final SimpleConnectionMaker simpleConnectionMaker;

    public UserDao4() {
        simpleConnectionMaker = new SimpleConnectionMaker();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        UserDao4 userDao4 = new UserDao4();

        userDao4.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao4.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = simpleConnectionMaker.makeNewConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = simpleConnectionMaker.makeNewConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
