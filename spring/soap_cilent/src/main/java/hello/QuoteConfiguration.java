
package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * config quoteClient bean
 */
@Configuration
public class QuoteConfiguration {

    /**
     * Marshaller - 指揮官
	 * - interface Marshaller - responsible for governing the process of serializing Java content trees back into XML data
     * - marshaller is pointed at the collection of generated domain objects and will use them to both serialize and deserialize between XML and POJOs.
     * @return Jaxb2Marshaller bean
     */
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("hello.wsdl");
		return marshaller;
	}

    /**
     * create and init quoteClient bean
     * @param marshaller
     * @return QuoteClient bean
     */
	@Bean
	public QuoteClient quoteClient(Jaxb2Marshaller marshaller) {
		QuoteClient client = new QuoteClient();
		client.setDefaultUri("http://www.webservicex.com/stockquote.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
