package gov.nist.healthcare.hl7ws.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.helpers.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



public class HL7WSClient {

  @SuppressWarnings("unused")
  public static void main(String[] args) throws FileNotFoundException, IOException {


    String xmlLoadResource;
    String xmlResourceList;
    String dataFilePath = "/Users/indovina/NIST_HL7_WS_Client/NISTHL7WebService-Clients/files/";
    String[] msgFields;
    String[] msh9Components;
    String[] msh12Components;
    String message;
    String profileFN;
    String profile;
    String basetablesFN;
    String usertablesFN;
    String baseTables;
    String userTables;
    String[] profileOids = new String[2000];
    String[] baseTableOids = new String[2000];
    String[] userTableOids = new String[2000];
    String[] messages = new String[2000];
    String baseTablesOid;
    String userTablesOid;
    String xmlValidationContext = null;
    ArrayList<String> profileList = new ArrayList<String>();
    ArrayList<String> baseTablesList = new ArrayList<String>();
    ArrayList<String> userTablesList = new ArrayList<String>();
    ArrayList<String> validationContextList = new ArrayList<String>();
    ArrayList<String> validationContextFNList = new ArrayList<String>();


    //
    // Connect to the wb service
    //

    String URL = "http://hit-testing.nist.gov:8080/hl7v2ws/services/soap/MessageValidationV2/";
    MessageValidationV2SoapClient client = new MessageValidationV2SoapClient(URL);


    //
    //
    // Query the resources already loaded
    xmlResourceList = client.getServiceStatus();
    System.out.println("service status:\n" + xmlResourceList + "\n\n");


    //
    // Load profiles/tables and valdiate messages as specified in messages.txt
    //
    // For each message listed in messages.txt:
    // (a) open and read it (format: msg_filename,validation-context_filename)
    // (b) parse it to determine which profile to use (based on MSH.9)
    // (c) parse it to determine which table to use (based on MSH.12 HL7 version)
    // (d) if not already loaded, load that profile
    // (e) parse the response to get the oid and associate the oid with the profile name using a map
    // (f) if not already loaded, load the tables (base and user)
    // (g) parse the response to get the oids, and associate the oids with the table names using a
    // map
    // (h) call validate for each message, specifying the resource oids (profile and tables) and
    // validation-context
    //

    HashMap<String, String> map = new HashMap<String, String>();
    int totalMessages = 0;


    //
    // steps a-g above
    //
    // load all resources (being careful to only load them once) needed to validate all messages
    // build list of associations between messages and resources (profiles and tables) and
    // association between resources and OIDs
    // build list of associations between messages and validation-contexts
    //
    //
    try {
      // open messages.txt
      FileInputStream fstream = new FileInputStream(dataFilePath + "messages.txt");
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String testMessageInfo;
      String[] testMessageInfoComponents;
      String testMessageFN;
      String validationContextFN;

      // read files line by line
      while ((testMessageInfo = br.readLine()) != null) {

        testMessageInfoComponents = testMessageInfo.split(",");
        testMessageFN = testMessageInfoComponents[0];
        validationContextFN = testMessageInfoComponents[1];

        if (!"NONE".equals(validationContextFN)) {
          validationContextList
              .add(IOUtils.toString(new FileReader(new File(dataFilePath + validationContextFN))));
        } else {
          validationContextList.add(null);
        }

        validationContextFNList.add(validationContextFN);

        // open and parse the test message
        messages[totalMessages] =
            IOUtils.toString(new FileReader(new File(dataFilePath + testMessageFN)));

        // Get the profile version to use from MSH.9
        msgFields = messages[totalMessages].split("\\|");
        msh9Components = msgFields[8].split("\\^");
        profileFN = "NIST_" + msh9Components[0] + "_" + msh9Components[1] + ".xml";

        // Get the HL7 version from MSH.12
        // the version is part of the pathname to the tables (e.g. "2.3.1/HL7Tables.xml" and
        // "2.3.1/UserTables.xml")
        msh12Components = msgFields[11].split("\r");
        basetablesFN = msh12Components[0] + "/" + "HL7Tables.xml";
        usertablesFN = msh12Components[0] + "/" + "UserTables.xml";

        // if not loaded already then load the base and user tables and get the oids
        if (!baseTablesList.contains(basetablesFN)) {
          // load the base table
          System.out.print("\nLoading tables: " + basetablesFN + " ");
          baseTables =
              IOUtils.toString(new FileReader(new File(dataFilePath + "tables/" + basetablesFN)));
          xmlLoadResource = client.loadResource(baseTables, null, "TABLE");
          baseTableOids[totalMessages] = getOid(xmlLoadResource);
          map.put(basetablesFN, baseTableOids[totalMessages]);
          System.out.println("oid = " + baseTableOids[totalMessages]);

          // load the user table
          System.out.print("\nLoading tables: " + usertablesFN + " ");
          userTables =
              IOUtils.toString(new FileReader(new File(dataFilePath + "tables/" + usertablesFN)));
          xmlLoadResource = client.loadResource(userTables, null, "TABLE");
          userTableOids[totalMessages] = getOid(xmlLoadResource);
          map.put(usertablesFN, userTableOids[totalMessages]);
          System.out.println("oid = " + userTableOids[totalMessages]);

        }
        baseTablesList.add(basetablesFN);
        userTablesList.add(usertablesFN);

        // if not loaded already then load the profile and get the oid
        if (!profileList.contains(profileFN)) {
          System.out.print("\nLoading profile: " + profileFN + " ");
          profile =
              IOUtils.toString(new FileReader(new File(dataFilePath + "profiles/" + profileFN)));
          xmlLoadResource = client.loadResource(profile, null, "PROFILE");
          profileOids[totalMessages] = getOid(xmlLoadResource);
          map.put(profileFN, profileOids[totalMessages]);
          System.out.println("oid = " + profileOids[totalMessages]);
        }

        profileList.add(profileFN);
        totalMessages += 1;
      } // while

      // Close the input stream
      in.close();

    } catch (Exception e) {// Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

    System.out.println("\n\nTotal messages = " + totalMessages);

    System.out.println("\n\nMessage to base table mapping:");
    for (int i = 0; i < baseTablesList.size(); i++) {
      System.out.println("message[" + i + "] = " + baseTablesList.get(i));
    }

    System.out.println("\n\nMessage to user table mapping:");
    for (int i = 0; i < userTablesList.size(); i++) {
      System.out.println("message[" + i + "] = " + userTablesList.get(i));
    }

    System.out.println("\n\nMessage to profile mapping:");
    for (int i = 0; i < profileList.size(); i++) {
      System.out.println("message[" + i + "] = " + profileList.get(i));
    }

    System.out.println("\n\nMap = " + map);


    //
    // step h above
    //
    // Validate each message
    //
    for (int i = 0; i < totalMessages; i++) {
      baseTablesOid = (String) map.get(baseTablesList.get(i));
      userTablesOid = (String) map.get(userTablesList.get(i));
      String tableOids = baseTablesOid + ":" + userTablesOid;
      System.out.println("\n\nValidating (using profile oid = " + map.get(profileList.get(i))
          + " and table oid = " + tableOids + ") and vc=" + validationContextFNList.get(i) + ": "
          + messages[i].replaceAll("\r", "\n") + "\n\n");
      String xmlResults = client.validate(messages[i], (String) map.get(profileList.get(i)),
          tableOids, validationContextList.get(i));

      System.out.println("validation context:\n " + validationContextList.get(i));

      System.out.println("Validation returned:\n " + xmlResults.replaceAll("\r", "\n") + "\n\n\n");
    }
  }


  @SuppressWarnings("unused")
  private static URI getPath(String fn) {
    URI uri = null;
    try {
      uri = HL7WSClient.class.getResource(fn).toURI();
    } catch (Exception e) {
      System.out.println("Exception caught: " + e.toString());
    } ;
    return uri;
  }

  private static String getValue(String tag, Element element) {
    NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
    Node node = (Node) nodes.item(0);
    return node.getNodeValue();
  }

  // parse the response to get the oid
  private static String getOid(String xmlResponse) {

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = null;
    Document doc = null;
    String oid = null;

    try {
      dBuilder = dbFactory.newDocumentBuilder();
    } catch (Exception ex) {
    } ;

    try {
      doc = dBuilder.parse(new InputSource(new StringReader(xmlResponse)));
    } catch (Exception ex) {
    } ;
    doc.getDocumentElement().normalize();
    NodeList nodes = doc.getElementsByTagName("xmlLoadResource");
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        oid = getValue("oid", element);
      }
    }
    return oid;
  }

}

