package hello;

import hello.wsdl.GetQuoteResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * note
 * - build with maven
 * - generate class using jaxb2 plugin
 * ref
 * - https://spring.io/guides/gs/consuming-web-service/
 * - https://docs.oracle.com/javase/7/docs/api/javax/xml/bind/Marshaller.html
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	CommandLineRunner lookup(QuoteClient quoteClient) {
		return args -> {
			String ticker = "MSFT";

			if (args.length > 0) {
				ticker = args[0];
			}
			GetQuoteResponse response = quoteClient.getQuote(ticker);
			System.err.println(response.getGetQuoteResult());
		};
	}

}
