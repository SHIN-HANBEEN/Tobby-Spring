package Chapter_01.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 특정 클래스 대신 인터페이스를 사용해서 DB 커넥션을 가져와 사용하도록 수정한 UserDao 코드다.
 * 이렇게 수정하고 난 이후부터는 아무리 접속용 클래스를 만든다고 해도 UserDao 의 코드를 뜯어 고칠 일은 없을 것 같다.
 * -
 * 그런데, 생성자에서 클래스 이름이 나오는 것을 확인할 수 있다.
 * UserDao 의 다른 모든 곳에서는 인터페이스를 이용하게 만들어서 DB 커넥션을 제공하는 클래스에 대한 구체적인
 * 정보는 모두 제거가 가능했지만, 초기에 한 번 어떤 클래스의 오브젝트를 사용할지를 결정하는 생성자의 코드는 제거되지 않고
 * 남아 있다. 결국 UserDao 소스코드를 함께 제공해서, 필요할 때마다 UserDao 의 생성자 메소드를 직접 수정하라고
 * 하지 않고서는 고객에게 자유로운 DB 커넥션 확장 기능을 가진 UserDao 를 제공할 수가 없다.
 * -
 * UserDao 와 ConnectionMaker 라는 두 개의 관심을 인터페이스를 써가면서까지 거의 완벽하게 분리했는데도, 왜 UserDao 가
 * 인터페이스뿐 아니라 구체적인 클래스까지 알아야한다는 문제가 발생하는 것일까?
 * 그 이유는 UserDao 안에 분리되지 않은, 또 다른 관심사항이 존재하고 있기 때문이다.
 * 바로 UserDao 가 어떤 ConnectionMaker 구현 클래스의 오브젝트를 이용하게 할지를 결정하는 것이다.
 * 간단히 말하자면 UserDao 와 UserDao 가 사용할 ConnectionMaker 의 특정 구현 클래스 사이의 관계를 설정해주는
 * 것에 관한 관심이다. 이 관심사를 담은 코드를 UserDao 에서 분리하지 않으면 UserDao 는 결코 독립적으로 확장 가능한
 * 클래스가 될 수 없다.
 * -
 * 마지막 남은 관심사를 분리하기 전에 '오브젝트 간 클라이언트' 개념에 대해서 이야기해보고자 한다.
 * 두 개의 오브젝트가 있고 한 오브젝트가 다른 오브젝트의 기능을 사용한다면, 사용되는 쪽이 사용하는 쪽에게 서비스를 제공하는 셈이다.
 * 따라서 사용되는 오브젝트를 서비스, 사용하는 오브젝트를 클라이언트라고 부를 수 있다.
 * 왜 뜬금없이 클라이언트 오브젝트를 끄집어내느냐 하면, 바로 이 UserDao 의 클라이언트 오브젝트가 바로 제 3의 관심사항인
 * UserDao 와 ConnectionMaker 구현 클래스의 관계를 결정해주는 기능을 분리해서 두기에 적절한 곳이기 때문이다.
 * -
 * UserDao 의 클라이언트에서 UserDao 를 사용하기 전에, 먼저 UserDao 가 어떤 ConnectionMaker 의 구현 클래스를
 * 사용할지를 결정하도록 만들어보자.
 */
public class UserDao5 {
    private final ConnectionMaker connectionMaker;

    public UserDao5() {
        connectionMaker = new DConnectionMaker();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        UserDao5 userDao4 = new UserDao5();

        userDao4.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao4.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
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
