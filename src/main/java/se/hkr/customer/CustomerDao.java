package se.hkr.customer;

import se.hkr.data.ConnectionProvider;
import se.hkr.data.Dao;
import se.hkr.user.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CustomerDao extends Dao {
    public CustomerDao(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    public void getProfile(String email, Consumer<Profile> profileConsumer) {
        select(resultSet -> {
            try {
                if (resultSet.next()) {
                    profileConsumer.accept(new Profile(resultSet));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, "SELECT `Account`.`id` AS aid,`Customer`.`id` AS cid,`name`,`surname`,`ssn`,`phone`,`address` " +
                "FROM Customer,Account " +
                "WHERE ? IN (SELECT `email` FROM Account) AND " +
                "Account.id = Customer.account_id;", email);
    }

    public void getProfiles(Consumer<List<Profile>> profilesResult) {
        select(resultSet -> {
            List<Profile> result = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    result.add(new Profile(resultSet));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                profilesResult.accept(result);
            }
        }, "SELECT `Account`.`id` AS aid,`Customer`.`id` AS cid,`name`,`surname`,`ssn`,`phone`,`address` FROM Customer,Account WHERE Account.id = Customer.account_id;");
    }

    public void profileExists(String email, Consumer<Boolean> result) {
        select(resultSet -> {
            try {
                if (resultSet.next()) {
                    result.accept(true);
                } else {
                    result.accept(false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, "SELECT 1 FROM Account WHERE Account.email = ?;", email);
    }

    public void createPerson(String email, String password, String ssn, String name, String surname, String addr, String phone) {
        insert("INSERT INTO `Account` " +
                "(`email`,`password`)" +
                "VALUES (?,SHA1(?));", integer -> {
            insert("INSERT INTO `Customer` " +
                    "(`account_id`,`ssn`,`name`,`surname`,`address`,`phone`) " +
                    "VALUES (?, ?, ?, ?, ?, ?);", integer, ssn, name, surname, addr, phone);
        }, email, password);
    }

}
