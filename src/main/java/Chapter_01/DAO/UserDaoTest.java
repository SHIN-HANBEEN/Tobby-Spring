package Chapter_01.DAO;

import java.sql.SQLException;

/**
 * UserDao6 클래스의 main( ) 메소드가 UserDaoTest 로 옮겨보았다.
 * 이렇게 하고 나니 UserDao 에는 ConnectionMaker 의 구체적인 구현 클래스 이름은 DConnectionMaker 가 사라졌다!
 * UserDao 는 자신의 관심사이자 책임인 사용자 데이터 액세스 작업을 위해 SQL을 생성하고, 이를 실행하는 데만 집중할 수 있게 됐다.
 * 더 이상 DB 생성 방법이나 전략에 대해서는 조금도 고민할 필요가 없다.
 * 이렇게 인터페이스를 도입하고 클라이언트의 도움을 얻는 바업은 상속을 사용해 비슷한 시도를 했을 경우에 비해서 훨씬 유연하다.
 * ConnectionMaker 라는 인터페이스를 사용하기만 한다면 다른 DAO 클래스에도 ConnectionMaker 의 구현 클래스들을 그대로
 * 적용할 수 있기 때문이다. DAO가 아무리 많아져도 DB 접속 방법에 대한 관심은 오직 한 군데에 집중되게 할 수 있고, DB 접속 방법을
 * 변경해야 할 때도 오직 한 곳의 코드만 수정하면 된다.
 */
public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        UserDao6 userDao6 = new DaoFactory().userDao6();

        userDao6.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao6.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
    }
}
