package org.immregistries.nist.validator.connector;

import java.util.ArrayList;
import java.util.List;

import org.immregistries.mqe.hl7util.Reportable;
import org.immregistries.mqe.hl7util.SeverityLevel;
import org.immregistries.mqe.hl7util.model.CodedWithExceptions;
import org.immregistries.mqe.hl7util.model.Hl7Location;
import org.immregistries.mqe.hl7util.parser.HL7Reader;


import gov.nist.healthcare.hl7ws.client.MessageValidationV2SoapClient;


public class NISTValidator {
  public static boolean enabled = true;
  public static final String EVS_URL_DEFAULT =
      "https://hl7v2.ws.nist.gov/hl7v2ws//services/soap/MessageValidationV2";

  private String soapClientUrl = null;

  private MessageValidationV2SoapClient soapClient = null;

  public NISTValidator() {
    this.soapClientUrl = EVS_URL_DEFAULT;
  }

  public NISTValidator(String soapClientUrl) {
    this.soapClientUrl = soapClientUrl;
  }

  private synchronized MessageValidationV2SoapClient getSoapClient() {
    if (soapClient == null) {
      soapClient = new MessageValidationV2SoapClient(soapClientUrl);
    }
    return soapClient;
  }

  public ValidationReport validate(String messageText) {
    if (enabled) {
      ValidationResource validationResource = ascertainValidationResource(messageText);
      if (validationResource == null) {
        return null;
      } else {
        return validate(messageText, validationResource);
      }
    }
    return null;
  }

  public List<Reportable> validateAndReport(String messageText) {
    if (enabled) {
        ValidationResource validationResource = ascertainValidationResource(messageText);
        if (validationResource == null) {
            List<Reportable> reportableList = new ArrayList<>();
            NISTReportable reportable = new NISTReportable();
            reportableList.add(reportable);
            reportable.setReportedMessage("Unable to validate with NIST, unrecognized message");
            reportable.setSeverity(SeverityLevel.WARN);
            reportable.getHl7ErrorCode().setIdentifier("0");
            return reportableList;
        } else {
            return validateAndReport(messageText, validationResource);
        }
    }

    return new ArrayList<>();
  }

  public List<Reportable> validateAndReport(String messageText,
      ValidationResource validationResource) {

    ValidationReport validationReport = validate(messageText, validationResource);
    List<Reportable> reportableList = new ArrayList<>();
    for (Assertion assertion : validationReport.getAssertionList()) {
      String severity = assertion.getResult();
      SeverityLevel severityLevel = SeverityLevel.ACCEPT;
      if (severity.equalsIgnoreCase("error")) {
        severityLevel = SeverityLevel.WARN;
      }
      if (severityLevel != SeverityLevel.ACCEPT) {
        NISTReportable reportable = new NISTReportable();
        reportableList.add(reportable);
        reportable.setReportedMessage(assertion.getDescription());
        reportable.setSeverity(severityLevel);
        reportable.getHl7ErrorCode().setIdentifier("0");
        CodedWithExceptions cwe = new CodedWithExceptions();
        cwe.setAlternateIdentifier(assertion.getType());
        cwe.setAlternateText(assertion.getType());
        cwe.setNameOfAlternateCodingSystem("L");
        reportable.setApplicationErrorCode(cwe);

        String path = assertion.getPath();
        reportable.setDiagnosticMessage(path);
        readErrorLocation(reportable, path);
      }
    }
    return reportableList;
  }

  public void readErrorLocation(NISTReportable reportable, String path) {
    if (path != null && path.length() >= 3) {
      String segmentid = path.substring(0, 3);
      if (path.length() > 3) {
        path = path.substring(3);
      } else {
        path = "";
      }

      Hl7Location errorLocation = readErrorLocation(path, segmentid);
      if (errorLocation != null) {
        reportable.getHl7LocationList().add(errorLocation);
      }
    }
  }

  public Hl7Location readErrorLocation(String path, String segmentid) {
	  Hl7Location errorLocation = new Hl7Location();
    errorLocation.setSegmentId(segmentid);
    int firstHyphenPos = path.indexOf("-");
    String segmentSequence = path;
    if (firstHyphenPos >= 0) {
      segmentSequence = path.substring(0, firstHyphenPos);
      path = path.substring(firstHyphenPos + 1);
    } else {
      path = "";
    }
    int sequence = parseBracketInt(segmentSequence);
    if (sequence > 0) {
      errorLocation.setSegmentSequence(sequence);
    }
    if (path.length() > 0) {
      {
        String fieldString = path;
        int dotPos = path.indexOf(".");
        if (dotPos >= 0) {
          fieldString = path.substring(0, dotPos);
          path = path.substring(dotPos + 1);
        } else {
          path = "";
        }
        int fieldPosition = 0;
        int bracketPos = fieldString.indexOf("[");
        try {
          if (bracketPos >= 0) {
            fieldPosition = Integer.parseInt(fieldString.substring(0, bracketPos).trim());
            fieldString = fieldString.substring(bracketPos);
            errorLocation.setFieldRepetition(parseBracketInt(fieldString));
          } else {
            fieldPosition = Integer.parseInt(fieldString.trim());
          }
        } catch (NumberFormatException nfe) {
          // ignore
        }
        if (fieldPosition != 0) {
          errorLocation.setFieldPosition(fieldPosition);
        }
      }
      if (path.length() > 0) {
        String componentString = path;
        int dotPos = path.indexOf(".");
        if (dotPos >= 0) {
          componentString = path.substring(0, dotPos);
          path = path.substring(dotPos + 1);
        } else {
          path = "";
        }
        try {
          errorLocation.setComponentNumber(Integer.parseInt(componentString.trim()));
        } catch (NumberFormatException nfe) {
          // ignore
        }
      }
      if (path.length() > 0) {
        try {
          errorLocation.setSubComponentNumber(Integer.parseInt(path.trim()));
        } catch (NumberFormatException nfe) {
          // ignore
        }
      }
    }
    return errorLocation;
  }

  public int parseBracketInt(String s) {
    s = s.trim();
    if (s.startsWith("[") && s.endsWith("]")) {
      try {
        return Integer.parseInt(s.substring(1, s.length() - 1).trim());
      } catch (NumberFormatException nfe) {
        // ignore
      }
    }
    return 0;
  }

  public ValidationReport validate(String messageText, ValidationResource validationResource) {
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
      } else if (messageType.equals("VXU")) {
        validationResource = ValidationResource.IZ_VXU_Z22;
      } else if (profileId.equals("Z23") || messageType.equals("ACK")) {
        validationResource = ValidationResource.IZ_ACK_FOR_AIRA;
        // validationResource = ValidationResource.IZ_ACK_Z23;
      }
    }
    return validationResource;
  }
}
