package DAO;

import java.sql.*;

/**
 * 기존 UserDao 에는 관심사가 3가지가 있었다.
 * <p>
 * 1. DB와 연결을 위한 커넥션을 어떻게 가져올까
 * 이 관심은 더 세부노하해서 어떤 DB를 쓰고, 어떤 드라이버를 사용할 것이고, 어떤 로그인 정보를 쓰는데,
 * 그 커넥션을 생성하는 방법은 또 어떤 것이다라는 것까지 구분해서 볼 수도 있다. 일단은
 * 뭉뚱그려서 DB 연결과 관련된 관심이 하나라고 보자.
 * <p>
 * 2. DB에 보낼 SQL 문장을 담을 Statement 를 만들고 실행하는 것이다.
 * 여기서의 관심은 파라미터로 넘어온 사용자 정보를 Statemet 에 바인딩시키고, Statement 에 담긴 SQL을 DB
 * 를 통해 실행시키는 방법이다. 파라미터를 바인딩하는 것과 어떤 SQL을 사용할지를
 * 다른 관심사로 분리할 수도 있기도 하지만, 우선은 이것도 하나로 묶어서 생각하자.
 * <p>
 * 3. 작업이 끝나면 사용한 리소스인 Statement 와 Connection 오브젝트를 닫아줘서
 * 소중한 공유 리소스를 시스템에 돌려주는 것이다.
 */
public class UserDao2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao2 dao = new UserDao2();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
    }

    /**
     * DB 연겨과 관련된 부분에 변경이 일어났을 경우,
     * 예를 들어 DB 종류와 접속 방법이 바뀌어서 드라이버 클래스와 URL이 바뀌었다면
     * 앞으로는 getConnection( ) 이라는 한 메소드의 코드만 수정하면 된다.
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/DAO", "root", "skaehddn12!@");
    }

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
