/*
 * NIST HL7 WS MessageGenerationInterface.java Feb 13, 2007
 *
 * This code was produced by the National Institute of Standards and Technology (NIST). See the
 * "nist.disclaimer" file given in the distribution for information on the use and redistribution of
 * this software.
 */
package gov.nist.healthcare.hl7ws.messagegeneration;

import javax.jws.WebService;

/**
 * NIST HL7 V2 Message Generation Web Service
 * <p>
 * This service provides an interface for generation of HL7 version 2 (V2) message instances for
 * given a message profile or message template. A message template is a sample message. The message
 * can be populated with specific data values via a generation context. The interface is designed
 * for use in a web-service environment.
 * <p>
 * This service supports generation of messages for HL7 Version 2.3.1, 2.4, 2.5, and 2.5.1. The
 * version that is used for message generation is dictated by the message profile or by the message
 * template.
 * <p>
 * Messages can be generated using either a profile or a message template. Messages generated from a
 * profile use the XML profile as the guide to build the message structure. Elements are populated
 * from a repository of data values. A generation context can be used to set data values at specific
 * locations. Messages generated with a message template (i.e., a sample massage) combines the
 * template message with a generation context to form a message. The interface also allows to
 * indicate the profile to use.
 * <p>
 * The generate(), generateWithTemplate() are used in combination to generate a message. The
 * loadResource() can be used with the generate() or generateFromTemplate() method if desired
 * profile is not available on the server.
 * <p> 
 * 
 * @author Robert Snelick (NIST)
 */
@WebService
public interface MessageGenerationV2Interface {

  /**
   * Generate a message based off an HL7 conformance profile.
   * <p>
   * Message generation can create an HL7 v2 message encoded in ER7 or XML. The message is generated
   * based of an HL7 V2 conformance profile. Messages generated from a profile use the XML profile
   * as the guide to build the message structure. Elements are populated from a repository of random
   * data values; therefore the generated messages by default are not semantically coherent. A
   * generation context can be used to set data values at specific locations.
   * <p>
   * The generate() method may use an HL7 V2 conformance profile, a configuration file, and a
   * generation context. The configuration file can specify HL7 table files that are to be used
   * during the generation process. By default the generation service uses the HL7 V2 table file in
   * generation for the corresponding version of the profile. The generation context specifies data
   * values for specific locations and the encoding type.
   * <p>
   * 
   * @param OID the OID is a reference to the profile used in generation. The OID must reference a
   *        valid HL7 V2 conformance profile that is loaded on the server. The service requires that
   *        the profile is pre-loaded in the repository and validated.
   *        <p>
   * @param xmlConfig is an XML document used to specify generation configuration parameters.
   *        xmlConfig can contain the following attributes. See xmlConfig for a detailed
   *        specification.
   *        <p>
   *        additionalResources: indicates that additional resources are used during generation. In
   *        HL7 V2 an additional resource can be one or more table (i.e., value set) files. An HL7
   *        V2 table is identified by the HL7table element. HL7tables are used in the order
   *        specified by the order element. The HL7table and order elements are paired within the
   *        HL7resource element. If not specified otherwise, the HL7 default table is always used
   *        last in the generation process. To override this default, set the order for the
   *        HL7DefaultTable to 0 (i.e., the HL7DefaultTable will not be used). Below is an example
   *        of an xmlConfig file for HL7 V2.
   *        <p>
   *        The HL7 table is referenced via an OID and must be loaded on the server and validated.
   *        The table file must adhere to the hl7V2TableLibarary.xsd schema.
   *        <p>
   * 
   *        <pre>
   * &lt;b&gt;Example xmlConfig:&lt;/b&gt;
   * &lt;?xml version=&quot;1.0&quot; ?&gt;
   * &lt;xmlConfig xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;&gt;
   *   &lt;xmlMessage&gt;true&lt;/xmlMessage&gt;
   *   &lt;additionalResources&gt;
   *     &lt;HL7Resource&gt;
   *       &lt;HL7table&gt;2.16.840.1.113883.3.72.4.2.00001&lt;/HL7table&gt;
   *       &lt;Order&gt;1&lt;/Order&gt;
   *     &lt;/HL7Resource&gt;
   *     &lt;HL7Resource&gt;
   *       &lt;HL7table&gt;2.16.840.1.113883.3.72.4.2.00002&lt;/HL7table&gt;
   *       &lt;Order&gt;3&lt;/Order&gt;
   *     &lt;/HL7Resource&gt;
   *     &lt;HL7Resource&gt;
   *       &lt;!-- NIST HL7 Default Table V2.4 OID Assignment--&gt;
   *       &lt;HL7table&gt;2.16.840.1.113883.3.72.4.1.00005&lt;/HL7table&gt;
   *       &lt;Order&gt;2&lt;/Order&gt;
   *     &lt;/HL7Resource&gt;
   *   &lt;/additionalResources&gt;
   * &lt;/xmlConfig&gt;
   *        </pre>
   * 
   * @param xmlGenerationContext describes specific data content to be used in the generation
   *        process. Specific data values can be specified at specific locations. The generation
   *        context information can be used to drive scenario base testing. The generation context
   *        is also used to set the encoding type. The generation context must adhere to the
   *        contextInformation.xsd schema.
   * 
   *        <p>
   * @return the message generated
   *         <p>
   * @see xmlConfig
   * @see xmlGenerationContext
   */
  String generate(String oid, String xmlConfig, String xmlGenerationContext);

  /**
   * Generate a message from a template.
   * <p>
   * The generateFromTemplate() method may use a template, an HL7 V2 conformance profile, a
   * configuration file, and a generation context. The template message is the initial message to
   * generate from. The configuration file can specify HL7 table files that are to be used during
   * the generation process. By default the generation service uses the HL7 V2 table file in
   * generation for the corresponding version of the profile. The generation context specifies data
   * values for specific locations and the encoding type.
   * <p>
   * 
   * @param template the sample message to generate the message from.
   * @param OID the OID is a reference to the profile used in generation. The OID must reference a
   *        valid HL7 V2 conformance profile that is loaded on the server. The service requires that
   *        the profile is pre-loaded in the repository and validated.
   *        <p>
   * 
   * @param xmlConfig is an XML document used to specify generation configuration parameters.
   *        xmlConfig can contain the following attributes. See xmlConfig for a detailed
   *        specification.
   *        <p>
   *        additionalResources: indicates that additional resources are used during generation. In
   *        HL7 V2 an additional resource can be one or more table (i.e., value set) files. An HL7
   *        V2 table is identified by the HL7table element. HL7tables are used in the order
   *        specified by the order element. The HL7table and order elements are paired within the
   *        HL7resource element. If not specified otherwise, the HL7 default table is always used
   *        last in the generation process. To override this default, set the order for the
   *        HL7DefaultTable to 0 (i.e., the HL7DefaultTable will not be used). Below is an example
   *        of an xmlConfig file for HL7 V2.
   *        <p>
   *        The HL7 table is referenced via an OID and must be loaded on the server and validated.
   *        The table file must adhere to the hl7V2TableLibarary.xsd schema.
   *        <p>
   * 
   *        <pre>
   * &lt;b&gt;Example xmlConfig:&lt;/b&gt;
   * &lt;?xml version=&quot;1.0&quot; ?&gt;
   * &lt;xmlConfig xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;&gt;
   *   &lt;xmlMessage&gt;true&lt;/xmlMessage&gt;
   *   &lt;additionalResources&gt;
   *     &lt;HL7Resource&gt;
   *       &lt;HL7table&gt;2.16.840.1.113883.3.72.4.2.00001&lt;/HL7table&gt;
   *       &lt;Order&gt;1&lt;/Order&gt;
   *     &lt;/HL7Resource&gt;
   *     &lt;HL7Resource&gt;
   *       &lt;HL7table&gt;2.16.840.1.113883.3.72.4.2.00002&lt;/HL7table&gt;
   *       &lt;Order&gt;3&lt;/Order&gt;
   *     &lt;/HL7Resource&gt;
   *     &lt;HL7Resource&gt;
   *       &lt;!-- NIST HL7 Default Table V2.4 OID Assignment--&gt;
   *       &lt;HL7table&gt;2.16.840.1.113883.3.72.4.1.00005&lt;/HL7table&gt;
   *       &lt;Order&gt;2&lt;/Order&gt;
   *     &lt;/HL7Resource&gt;
   *   &lt;/additionalResources&gt;
   * &lt;/xmlConfig&gt;
   *        </pre>
   * 
   * 
   * @param xmlGenerationContext describes specific data content to be used in the generation
   *        process. Specific data values can be specified at specific locations. The generation
   *        context information can be used to drive scenario base testing. The generation context
   *        is also used to set the encoding type. The generation context must adhere to the
   *        contextInformation.xsd schema.
   *        <p>
   * @return the message generated
   *         <p>
   * @see oid
   * @see xmlGenerationContext
   */
  String generateFromTemplate(String message, String oid, String xmlConfig,
      String xmlGenerationContext);

  /**
   * The getServiceStatus() method returns a list of the profiles and tables currently supported by
   * the generation service. Note that if the service is not operational, the call will not return.
   * Therefore the getServiceStatus() invocation can be used to query the service for a list of
   * supported profiles and tables or to ping the operational status of the service. The supported
   * profiles are return in the xmlResourceList document. Profiles and tables that are loaded with
   * the loadResource() method are included in this list. String xmlResourceList =
   * getServiceStatus();
   * 
   * @return xmlResourceList- an XML string is returned (xmlSpecificationList) that provides
   *         descriptive information about the available generation resources. For HL7 version 2
   *         resources include profiles and tables. The xmlResourceList will provide this
   *         information for all the available generation resources provided by the service. The
   *         following attributes describe a particular resource and is provided if the information
   *         is available.
   *         <p>
   *         resourceID: This identifier references the generation resource. For HL7 V2 the
   *         resourceID is a OID. The resourceID can be used in the generate() or generateSet()
   *         method to indicate the resource. A profile is referenced by the OID parameter. A table
   *         is referenced within the xmlConfig parameter.
   *         <p>
   *         name: A descriptive name of the profile/table.
   *         <p>
   *         version: The version of the profile/table.
   *         <p>
   *         organization: The organization that created, provided, or owns the profile/table.
   *         <p>
   *         date: The date the profile/table was created.
   *         <p>
   *         description: A detailed description of the profile/table.
   *         <p>
   *         HL7version: The HL7 version of the profile/table (e.g, 2.5.1).
   * @see xmlResourceList
   */
  String getServiceStatus();

  /**
   * The loadResource() method is used to load an HL7 V2 conformance profile or table (resources)
   * that can be used in a subsequence generate invocations. The length of time the resource is
   * available for use in generation is the duration of the web service session. Note that resources
   * that are loaded with this call are reported in the getServiceStatus() method. String
   * xmlLoadResource = loadResource (String resource, String OID, String type);
   * 
   * @return xmlLoadResource - an XML document indicating the results from the loadResource()
   *         invocation. The document contains the following attributes.
   *         <p>
   *         status: true if the resource was loaded and validated successfully; false otherwise.
   *         <p>
   *         OID: identifier that references the resource. If the OID is not specified, the
   *         generation service provides an OID. On the NIST server the OID root for a profile is
   *         2.16.840.1.113883.3.72.2.2.XXXXX. For a table resource the OID is
   *         2.16.840.1.113883.3.72.4.2.XXXXX. The �XXXXX� value is assigned by the server. If the
   *         OID has the same value as an OID that is already registered on the service, it is an
   *         error. The original resource referenced by the OID is not altered. The user must select
   *         another OID to load their resource or need to check to see if the resource they want is
   *         already available.
   *         <p>
   *         errorDescription: This field provides a detailed description indicating the reason the
   *         resource could not be loaded. For a profile, this will include the results from a
   *         profile validation.
   * @param resource - is the resource to load. A resource is either a profile or a table. The
   *        profile is a valid HL7 conformance profile (i.e., it adheres to the HL7 conformance
   *        profile schema and additional requirements place on it by the NIST service). A table is
   *        validated by the hl7TableLibrary schema.
   * @param OID an identifier that references the resource.
   * @param type - is the type of the resource. The type is either PROFILE or TABLE. ======= The
   *        loadResource() method is used to load an HL7 V2 conformance profile or table (resources)
   *        that can be used in a subsequence generate invocations. The length of time the resource
   *        is available for use in generation is the duration of the web service session. Note that
   *        resources that are loaded with this call are reported in the getServiceStatus() method.
   *        String xmlLoadResource = loadResource (String resource, String OID, String type);
   * @return xmlLoadResource - an XML document indicating the results from the loadResource()
   *         invocation. The document contains the following attributes.
   *         <p>
   *         status: true if the resource was loaded and validated successfully; false otherwise.
   *         <p>
   *         OID: identifier that references the resource. If the OID is not specified, the
   *         generation service provides an OID. On the NIST server the OID root for a profile is
   *         2.16.840.1.113883.3.72.2.2.XXXXX. For a table resource the OID is
   *         2.16.840.1.113883.3.72.4.2.XXXXX. The �XXXXX� value is assigned by the server. If the
   *         OID has the same value as an OID that is already registered on the service, it is an
   *         error. The original resource referenced by the OID is not altered. The user must select
   *         another OID to load their resource or need to check to see if the resource they want is
   *         already available.
   *         <p>
   *         errorDescription: This field provides a detailed description indicating the reason the
   *         resource could not be loaded. For a profile, this will include the results from a
   *         profile validation.
   * @param resource - is the resource to load. A resource is either a profile or a table. The
   *        profile is a valid HL7 conformance profile (i.e., it adheres to the HL7 conformance
   *        profile schema and additional requirements place on it by the NIST service). A table is
   *        validated by the hl7TableLibrary schema.
   * @param OID an identifier that references the resource.
   * @param type - is the type of the resource. The type is either PROFILE or TABLE
   */
  String loadResource(String resource, String oid, String type);
}
