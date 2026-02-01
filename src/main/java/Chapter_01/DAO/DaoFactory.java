package Chapter_01.DAO;

public class DaoFactory {
    public UserDao6 userDao6() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao6 userDao6 = new UserDao6(connectionMaker);
        return userDao6;
    }
}
