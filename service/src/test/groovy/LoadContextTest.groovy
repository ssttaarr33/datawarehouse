import com.adrian.interview.AppStarter
import com.adrian.interview.controller.DataController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = AppStarter.class)
class LoadContextTest extends Specification{
    @Autowired(required = false)
    private DataController dataController

    def "when context is loaded then controller is created"() {
        expect: "the DataController is created"
        dataController
    }

}
