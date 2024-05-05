
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.jfr.DataAmount;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="user")
public class User implements Serializable {
}
