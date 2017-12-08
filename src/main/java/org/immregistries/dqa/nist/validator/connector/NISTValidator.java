package org.immregistries.dqa.nist.validator.connector;

import org.immregistries.dqa.hl7util.parser.HL7Reader;

import gov.nist.healthcare.hl7ws.client.MessageValidationV2SoapClient;


public class NISTValidator {
  public static final String EVS_URL_DEFAULT =
      "http://hl7v2.ws.nist.gov/hl7v2ws//services/soap/MessageValidationV2";

  private static String soapClientUrl = EVS_URL_DEFAULT;

  private static MessageValidationV2SoapClient soapClient = null;

  private static synchronized MessageValidationV2SoapClient getSoapClient() {
    if (soapClient == null) {
      soapClient = new MessageValidationV2SoapClient(soapClientUrl);
    }
    return soapClient;
  }

  public static ValidationReport validate(String messageText) {
    ValidationResource validationResource = ascertainValidationResource(messageText);
    if (validationResource == null) {
      return null;
    } else {
      return validate(messageText, validationResource);
    }
  }


  private static String replace(String description, String search, String replace) {
    int startPos = description.indexOf(search);
    while (startPos >= 0) {
      description = description.substring(0, startPos) + replace
          + description.substring(startPos + search.length());
      startPos = description.indexOf(search);
    }
    return description;
  }

  public static ValidationReport validate(String messageText,
      ValidationResource validationResource) {
    MessageValidationV2SoapClient soapClient = getSoapClient();
    synchronized (soapClient) {
      String result = soapClient.validate(messageText, validationResource.getOid(), "", "");
      ValidationReport validationReport = new ValidationReport(result);
      return validationReport;
    }
  }

  public static ValidationResource ascertainValidationResource(String messageText) {
    ValidationResource validationResource = null;
    HL7Reader hl7Reader = new HL7Reader(messageText);
    if (hl7Reader.advanceToSegment("MSH")) {
      String messageType = hl7Reader.getValue(9);
      String profileId = hl7Reader.getValue(21);
      if (profileId.equals("Z31") && messageType.equals("RSP")) {
        validationResource = ValidationResource.IZ_RSP_Z31;
      } else if (profileId.equals("Z32") && messageType.equals("RSP")) {
        validationResource = ValidationResource.IZ_RSP_Z32;
      } else if (profileId.equals("Z42") && messageType.equals("RSP")) {
        validationResource = ValidationResource.IZ_RSP_Z42;
      } else if (profileId.equals("Z33") && messageType.equals("RSP")) {
        validationResource = ValidationResource.IZ_RSP_Z33;
      } else if (profileId.equals("Z34") && messageType.equals("QBP")) {
        validationResource = ValidationResource.IZ_QBP_Z34;
      } else if (profileId.equals("Z44") && messageType.equals("QBP")) {
        validationResource = ValidationResource.IZ_QBP_Z44;
      } else if (profileId.equals("Z22") && messageType.equals("VXU")) {
        validationResource = ValidationResource.IZ_VXU_Z22;
      } else if (profileId.equals("") && messageType.equals("VXU")) {
        validationResource = ValidationResource.IZ_VXU;
      } else if (profileId.equals("Z23") || messageType.equals("ACK")) {
        validationResource = ValidationResource.IZ_ACK_FOR_AIRA;
        // validationResource = ValidationResource.IZ_ACK_Z23;
      }
    }
    return validationResource;
  }
}
