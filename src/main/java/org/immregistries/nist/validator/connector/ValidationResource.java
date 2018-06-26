package org.immregistries.nist.validator.connector;

public enum ValidationResource {
  IZ_VXU("VXU", "HL7 v2.5.1 IG 1.4 8/2012", "2.16.840.1.113883.3.72.2.2.99001"),
  IZ_VXU_Z22("Z22", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99001"),
  IZ_ACK_Z23("Z23", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99002"),
  IZ_ACK_FOR_AIRA("Z23 AIRA", "HL7 v2.5.1 IG 1.5 10/2014 AIRA", "2.16.840.1.113883.3.72.2.3.99009"),
  IZ_QBP_Z34("Z34", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99006"),
  IZ_QBP_Z44("Z44", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99008"),
  IZ_RSP_Z31("Z31", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99003"),
  IZ_RSP_Z32("Z32", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99004"),
  IZ_RSP_Z33("Z33", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99005"),
  IZ_RSP_Z42("Z42", "HL7 v2.5.1 IG 1.5 10/2014", "2.16.840.1.113883.3.72.2.3.99007"),;
  private String oid = "";
  private String profile = "";
  private String version = "";

  public String getOid() {
    return oid;
  }

  public String getProfile() {
    return profile;
  }

  public String getVersion() {
    return version;
  }

  private ValidationResource(String profile, String version, String oid) {
    this.profile = profile;
    this.version = version;
    this.oid = oid;
  }
}
