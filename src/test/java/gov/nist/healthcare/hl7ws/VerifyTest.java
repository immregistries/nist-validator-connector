package gov.nist.healthcare.hl7ws;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.immregistries.dqa.hl7util.Reportable;
import org.immregistries.dqa.hl7util.builder.AckBuilder;
import org.immregistries.dqa.hl7util.builder.AckData;
import org.immregistries.dqa.nist.validator.connector.NISTValidator;
import org.immregistries.dqa.nist.validator.connector.ValidationReport;
import org.immregistries.dqa.nist.validator.connector.ValidationResource;
import org.junit.Test;

import gov.nist.healthcare.hl7ws.client.MessageValidationV2SoapClient;

public class VerifyTest {

  private static final String EXAMPLE_MESSAGE = "MSH|^~\\&|Test EHR Application|X68||NIST Test Iz Reg|201207010822||VXU^V04^VXU_V04|NIST-IZ-001.00|P|2.5.1|||AL|ER\r"
      + "PID|1||D26376273^^^NIST MPI^MR||Snow^^Ainsley^^^^L|Lam^Morgan|20070706|F||2076-8^Native Hawaiian or Other Pacific Islander^CDCREC|32 Prescott Street Ave^^Warwick^MA^02452^USA^L||^PRN^PH^^^657^5558563|||||||||2186-5^non Hispanic or Latino^CDCREC\r"
      + "PD1|||||||||||02^Reminder/Recall - any method^HL70215|||||A|20120701|20120701\r"
      + "NK1|1|Lam^Morgan^^^^^L|MTH^Mother^HL70063|32 Prescott Street Ave^^Warwick^MA^02452^USA^L|^PRN^PH^^^657^5558563\r"
      + "ORC|RE||IZ-783274^NDA|||||||I-23432^Burden^Donna^A^^^^^NIST-AA-1||57422^RADON^NICHOLAS^^^^^^NIST-AA-1^L\r"
      + "RXA|0|1|||140^Influenza, seasonal, injectable, preservative free^CVX|0.5|mL^MilliLiter [SI Volume Units]^UCUM||00^New immunization record^NIP001|7832-1^Lemon^Mike^A^^^^^NIST-AA-1|^^^X68||||Z0860BB|20121104|CSL^CSL Behring^MVX|||CP|A\r"
      + "RXR|C28161^Intramuscular^NCIT|LD^Left Arm^HL70163\r"
      + "OBX|1|CE|64994-7^Vaccine funding program eligibility category^LN|1|V05^VFC eligible - Federally Qualified Health Center Patient (under-insured)^HL70064||||||F|||20120701|||VXC40^Eligibility captured at the immunization level^CDCPHINVS\r"
      + "OBX|2|CE|30956-7^vaccine type^LN|2|88^Influenza, unspecified formulation^CVX||||||F\r"
      + "OBX|3|TS|29768-9^Date vaccine information statement published^LN|2|20120702||||||F\r"
      + "OBX|4|TS|29769-7^Date vaccine information statement presented^LN|2|20120814||||||F\r";


  private static final String OID = "2.16.840.1.113883.3.72.2.3.99001";
  /*
   * 
   * <Resource> <resourceID>2.16.840.1.113883.3.72.2.2.99001</resourceID>
   * <name>Immunization Messaging</name> <version>IZ MU 2014 1.1</version>
   * <organization>NIST</organization> <HL7version>2.5.1</HL7version>
   * <resourceType>PROFILE</resourceType> </Resource>
   */
  
  @Test
  public void testAssertion()
  {
    NISTValidator validator = new NISTValidator();
    AckData ackData = new AckData();
    List<Reportable> reportableList = validator.validateAndReport(EXAMPLE_MESSAGE);
    assertEquals(8, reportableList.size());
    ackData.setReportables(reportableList);
    System.out.println(AckBuilder.INSTANCE.buildAckFrom(ackData));
  }

  @Test
  public void test() {
    MessageValidationV2SoapClient soapClient = new MessageValidationV2SoapClient(
        "http://hl7v2.ws.nist.gov/hl7v2ws//services/soap/MessageValidationV2");
    String result = soapClient.validate(EXAMPLE_MESSAGE, OID, "", "");
    System.out.println(result);
    ValidationReport validationReport = new ValidationReport(result);
    assertEquals("Complete", validationReport.getHeaderReport().getValidationStatus());
    assertEquals(47, validationReport.getAssertionList().size());
    assertEquals(6, validationReport.getAssertionList().get(46).getLine());
    assertEquals(124, validationReport.getAssertionList().get(46).getColumn());
    assertEquals("VALUE_NOT_IN_TABLE", validationReport.getAssertionList().get(46).getType());
    assertEquals("PID[1].10[1].3", validationReport.getAssertionList().get(46).getPath());
    assertEquals("PID", validationReport.getAssertionList().get(46).getSegment());
    assertEquals("Race", validationReport.getAssertionList().get(46).getField());
    assertEquals("Name of Coding System", validationReport.getAssertionList().get(46).getComponent());
    
    
    NISTValidator validator = new NISTValidator();

    validationReport = validator.validate(EXAMPLE_MESSAGE, ValidationResource.IZ_VXU);
    assertEquals("Complete", validationReport.getHeaderReport().getValidationStatus());
    assertEquals(16, validationReport.getAssertionList().size());
    assertEquals(2, validationReport.getAssertionList().get(4).getLine());
    assertEquals(124, validationReport.getAssertionList().get(4).getColumn());
    assertEquals("PID[1].10[1].3", validationReport.getAssertionList().get(4).getPath());
    assertEquals("PID", validationReport.getAssertionList().get(4).getSegment());
    assertEquals("Race", validationReport.getAssertionList().get(4).getField());
    assertEquals("Name of Coding System", validationReport.getAssertionList().get(4).getComponent());

    

  }

}
