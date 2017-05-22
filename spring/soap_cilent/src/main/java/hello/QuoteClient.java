package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import hello.wsdl.GetQuote;
import hello.wsdl.GetQuoteResponse;

/**
 * a class for handling quote request
 */
public class QuoteClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(QuoteClient.class);

	public GetQuoteResponse getQuote(String ticker) {

//		create a new request
		GetQuote request = new GetQuote();
		request.setSymbol(ticker);

		log.info("Requesting quote for " + ticker);

//		send request and receive response
		GetQuoteResponse response = (GetQuoteResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://www.webservicex.com/stockquote.asmx",
						request,
						new SoapActionCallback("http://www.webserviceX.NET/GetQuote")); // namespace + soapaction

		return response;
	}

}
