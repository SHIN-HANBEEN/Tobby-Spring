package DAO;

import java.sql.*;

/**
 * JDBC를 이용하는 작업의 일반적인 순서는 다음과 같습니다.
 * - DB 연결을 위한 Connection 을 가져온다.
 * - SQL을 담은 Statement(또는 PreparedStatement)를 만든다.
 * - 만들어진 Statement 를 실행한다.
 * - 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet 으로 받아서 정보를 저장할 오브젝트에 옮겨준다.
 * - 작업 중에 생성된 Connection, Statement, ResultSet 같은 리소스는 작업을 마친 후 반드시 닫아준다.
 * - JDBC API 가 만들어내는 예외를 잡아서 직접 처리하거나, 메소드에 throws 를 선언해서 예외가 발생하면 메소드 밖으로 던지게 한다.
 */
public class UserDao {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao dao = new UserDao();

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

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/DAO", "root", "skaehddn12!@");
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
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost/DAO", "root", "skaehddn12!@");
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
