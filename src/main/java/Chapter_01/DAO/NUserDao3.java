package Chapter_01.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * N 사는 UserDao3 를 상속받아 원하는대로 DB 커넥션을 구현해서
 * 사용할 수 있다.
 * -
 * DAO의 핵심 기능인 어떻게 데이터를 등록하고 가져올 것인가라는 관심을
 * 담당하는 UserDao 와, DB 연결 방법은 어떻게 할 것인가라는 관심을
 * 담고 있는 NUserDao 가 클래스 레벨로 구분이 되고 있다.
 * 클래스 계층구조를 통해 두 개의 관심이 독립적으로 분리되면서
 * 변경 작업은 한층 용이해졌다. 이제는 UserDao 의 코드는
 * 한 줄도 수정할 필요 없이 DB 연결 기능을 새롭게 정의한
 * 클래스를 만들 수 있게 되었다.
 * 이제 UserDao 는 단순히 변경이 용이하다라는 수준을 넘어서
 * 손쉽게 확장된다라고 말할 수도 있게 됐다.
 * 새로운 DB 연결 방법을 적용해야 할 때는 userDao 를 상속을 통해
 * 확장해주기만 하면 된다.
 * -
 * 이렇게 슈퍼클래스에 기본적인 로직의 흐름(커넥션 가져오기, SQL 생성, 실행 반환)을 만들고,
 * 그 기능의 일부를 추상 메소드나 오버라이딩이 가능한 protected 메소드 등으로 만든 뒤
 * 서브클래스에서 이런 메소드를 필요에 맞게 구현해서 사용하도록 하는 방법을
 * 디자인 패턴에서 '템플릿 메소드 패턴'이라고 한다.
 * -
 * UserDao 의 getConnection( ) 메소드는 Connection 타입 오브젝트를 생성한다는
 * 기능을 정의해놓은 추상 메소드다. 그리고 UserDao 의 서브클래스의 getConnection( )
 * 메소드는 어떤 Connection 클래스의 오브젝트를 어떻게 생성할 것인지를 결정하는
 * 방법이라고 볼 수 있다. 이렇게 서브클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는
 * 것을 '팩토리 메소드 패턴'이라고 부르기도 한다.
 */
public class NUserDao3 extends UserDao3 {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/DAO", "root", "skaehddn12!@");
    }
}
