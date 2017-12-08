package gov.nist.healthcare.hl7ws.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import gov.nist.healthcare.hl7ws.messagevalidation.MessageValidationV2Interface;

public class MessageValidationV2SoapClient implements MessageValidationV2Interface {
  private final MessageValidationV2Interface client;

  public MessageValidationV2SoapClient(String endpoint) {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setServiceClass(MessageValidationV2Interface.class);
    factory.setAddress(endpoint);
    client = (MessageValidationV2Interface) factory.create();
  }

  public String validate(String message, String oid, String xmlConfig,
      String xmlValidationContext) {
    return client.validate(message, oid, xmlConfig, xmlValidationContext);
  }

  public String getServiceStatus() {
    return client.getServiceStatus();
  }

  public String loadResource(String resource, String oid, String type) {
    return client.loadResource(resource, oid, type);
  }

}
