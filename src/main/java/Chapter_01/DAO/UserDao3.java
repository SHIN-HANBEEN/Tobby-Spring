package Chapter_01.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 앞서 만든 UserDao2 를 N 사와 D 사에 납품한다고 생각해보자.
 * 문제는 N 사와 D 사가 각기 다른 종류의 DB 를 사용하고 있고, DB
 * 커넥션을 가져오는 데 있어 독자적으로 만든 방법을 적용하고 싶어한다는 점이다.
 * 즉, 문제 상황을 정리하자면 UserDao 소스코드를 제공해주지 않고도
 * 고객 스스로 원하는 DB 커넥션 생성 방식을 적용해가면서 UserDao 를
 * 사용하게 할 수 있을까?
 * <p>
 * '상속을 통한 확장'을 이용할 수 있다.
 * 기존 UserDao 를 한 단계 더 분리하면 된다.
 * getConnection( ) 을 추상 메소드로 만들어 놓는다.
 * 추상 메소드라서 메소드 코드는 없지만 메소드 자체는 존재한다.
 * 그래서 기존 add( ), get( ) 에서 그대로 getConnection( )을 호출하는 코드를 유지할 수 있다.
 */
public abstract class UserDao3 {

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
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
        Connection c = getConnection();
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
