package by.latashinsky.java.topics.entities;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.models.Constants;
import by.latashinsky.java.topics.models.MyListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserTypes userType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private List<Account> accounts;

    @Override
    public String toString() {
        return String.format("%s)%s\nUser type:%s\nAccounts:\n%s",
                id, name, userType.getValue(), MyListConverter.convert(accounts));
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public void editName() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter user name:");
            String str = in.next();
            if (str.length() <= 45 && str.length() >= 3) {
                this.setName(str);
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    public void editUserType() {
        logger.info("Types:\n1)Legal\n2)Usual\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter number of type:\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_INT, str)) {
                int index = Integer.parseInt(str);
                if (index == 1) {
                    this.setUserType(UserTypes.LEGAL);
                    return;
                }
                if(index==2){
                    this.setUserType(UserTypes.USUAL);
                    return;
                }
            }
            logger.info("Invalid input!\n");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
