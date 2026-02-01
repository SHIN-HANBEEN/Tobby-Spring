package Chapter_01.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 클래스 사이에 '관계'가 만들어진다는 것은 한 클래스가 인터페이스 없이 다른 클래스를 직접 사용한다는 뜻이다.
 * 즉, 오브젝트 사이의 관계를 설정한다는 의미인데,
 * 예를 들어 우리의 UserDao5 를 보면 connectionMaker = new DConnectionMaker() 라는 코드를 통해서
 * 두 개의 오브젝트가 '사용' 이라는 관계를 맺게 된다.
 * 따라서 UserDao 가 NConnectionMaker 를 사용하게 하려면 UserDao 의 코드를 뜯어고쳐서 NConnectionMaker 와
 * 관계를 맺도록 해야 하는데, 이런 문제를 해결하기 위해서는 UserDao 의 모든 코드는 ConnectionMaker 인터페이스
 * 외에는 어떤 클래스와도 관계를 가져서는 안 되게 해야 한다.
 * 제 3의 오브젝트라고 했던 UserDao 의 클라이언트는 그럼 무슨 역할을 하는 것일까? 바로 런타임 오브젝트 관계를
 * 갖는 구조를 만들어주는 게 바로 클라이언트의 책임이다.
 * 클라이언트는 자기가 UserDao 를 사용해야 할 입장이기 때문에 UserDao 의 세부 전략이라고도 볼 수 있는
 * ConnectionMaker 의 구현 클래스를 선택하고, 선택한 클래스의 오브젝트를 생성해서 UserDao 와 연결해줄 수 있다.
 * 기존의 UserDao 에서는 생성자에게 책임이 있었다. 다른 관심사가 함께 있으니 확장성을 떨어뜨리는 문제를 일으키는 것이다.
 * 이 관심을 분리해서 클라이언트에게 떠념겨보자.
 */


public class UserDao6 {
    private final ConnectionMaker connectionMaker;

    public UserDao6(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
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
        Connection c = connectionMaker.makeConnection();
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
