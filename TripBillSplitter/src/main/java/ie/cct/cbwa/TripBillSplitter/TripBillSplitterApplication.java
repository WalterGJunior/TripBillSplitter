package ie.cct.cbwa.TripBillSplitter;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("ie.cct*")
public class TripBillSplitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripBillSplitterApplication.class, args);
		
	}

}
