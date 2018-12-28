package mx.com.anzen.app.abanking.fake.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;



/**
 * <p>TODO [Add comments of the class]</p>
 *
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
@ComponentScan
@EnableAutoConfiguration

@EntityScan({
	"mx.com.anzen.app.abanking.fake.user.controller"
})
@SpringBootApplication
public class Application {

    /**
     * <p> Method to initialize the spring boot project architecture. </p>
     */
    public static void main(String[] args ) {
    	SpringApplication.run(Application.class, args);
    }
}
