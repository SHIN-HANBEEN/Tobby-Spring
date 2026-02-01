package Chapter_01.DAO;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

/**
 *
 */
public class UserDaoTest2 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory2.class);
        UserDao6 userDao6 = context.getBean("userDao6", UserDao6.class);

        User user = new User();
        user.setId("test01");
        user.setName("test01");
        user.setPassword("test01");

//        UserDao6 userDao6 = new DaoFactory().userDao6();

        userDao6.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao6.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공");
    }
}
