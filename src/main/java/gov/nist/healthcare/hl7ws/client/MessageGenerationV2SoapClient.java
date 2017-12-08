package gov.nist.healthcare.hl7ws.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import gov.nist.healthcare.hl7ws.messagegeneration.MessageGenerationV2Interface;


public class MessageGenerationV2SoapClient implements MessageGenerationV2Interface {

  private final MessageGenerationV2Interface client;

  public MessageGenerationV2SoapClient(String endpoint) {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setServiceClass(MessageGenerationV2Interface.class);
    factory.setAddress(endpoint);
    client = (MessageGenerationV2Interface) factory.create();
  }

  public String generate(String oid, String xmlConfig, String xmlGenerationContext) {
    return client.generate(oid, xmlConfig, xmlGenerationContext);
  }

  public String getServiceStatus() {
    return client.getServiceStatus();
  }

  public String loadResource(String resource, String oid, String type) {
    return client.loadResource(resource, oid, type);
  }

  public String generateFromTemplate(String message, String oid, String xmlConfig,
      String xmlGenerationContext) {
    return client.generateFromTemplate(message, oid, xmlConfig, xmlGenerationContext);
  }

}
