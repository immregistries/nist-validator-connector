package org.immregistries.dqa.nist.validator.connector;

public class HeaderReport {
  private String validationStatus = "";
  private String validationStatusInfo = "";
  private String serviceName = "";
  private String serviceProvider = "";
  private String serviceVersion = "";
  private String standardType = "";
  private String standardVersion = "";
  private String validationType = "";
  private String testIdentifier = "";
  private String dateOfTest = "";
  private String timeOfTest = "";
  private String validationObjectReferenceList = "";
  private String testObjectReferenceList = "";
  private boolean positiveAssertionIndicator = false;
  private String resultOfTest = "";
  private int affirmCount = 0;
  private int errorCount = 0;
  private int warningCount = 0;
  private int ignoreCount = 0;
  private int alertCount = 0;
  private int informCount = 0;

  public String getValidationStatusInfo() {
    return validationStatusInfo;
  }

  public void setValidationStatusInfo(String validationStatusInfo) {
    this.validationStatusInfo = validationStatusInfo;
  }

  public int getInformCount() {
    return informCount;
  }

  public void setInformCount(int informCount) {
    this.informCount = informCount;
  }

  public String getValidationStatus() {
    return validationStatus;
  }

  public void setValidationStatus(String validationStatus) {
    this.validationStatus = validationStatus;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceProvider() {
    return serviceProvider;
  }

  public void setServiceProvider(String serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public String getServiceVersion() {
    return serviceVersion;
  }

  public void setServiceVersion(String serviceVersion) {
    this.serviceVersion = serviceVersion;
  }

  public String getStandardType() {
    return standardType;
  }

  public void setStandardType(String standardType) {
    this.standardType = standardType;
  }

  public String getStandardVersion() {
    return standardVersion;
  }

  public void setStandardVersion(String standardVersion) {
    this.standardVersion = standardVersion;
  }

  public String getValidationType() {
    return validationType;
  }

  public void setValidationType(String validationType) {
    this.validationType = validationType;
  }

  public String getTestIdentifier() {
    return testIdentifier;
  }

  public void setTestIdentifier(String testIdentifier) {
    this.testIdentifier = testIdentifier;
  }

  public String getDateOfTest() {
    return dateOfTest;
  }

  public void setDateOfTest(String dateOfTest) {
    this.dateOfTest = dateOfTest;
  }

  public String getTimeOfTest() {
    return timeOfTest;
  }

  public void setTimeOfTest(String timeOfTest) {
    this.timeOfTest = timeOfTest;
  }

  public String getValidationObjectReferenceList() {
    return validationObjectReferenceList;
  }

  public void setValidationObjectReferenceList(String validationObjectReferenceList) {
    this.validationObjectReferenceList = validationObjectReferenceList;
  }

  public String getTestObjectReferenceList() {
    return testObjectReferenceList;
  }

  public void setTestObjectReferenceList(String testObjectReferenceList) {
    this.testObjectReferenceList = testObjectReferenceList;
  }

  public boolean isPositiveAssertionIndicator() {
    return positiveAssertionIndicator;
  }

  public void setPositiveAssertionIndicator(boolean positiveAssertionIndicator) {
    this.positiveAssertionIndicator = positiveAssertionIndicator;
  }

  public String getResultOfTest() {
    return resultOfTest;
  }

  public void setResultOfTest(String resultOfTest) {
    this.resultOfTest = resultOfTest;
  }

  public int getAffirmCount() {
    return affirmCount;
  }

  public void setAffirmCount(int affirmCount) {
    this.affirmCount = affirmCount;
  }

  public int getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

  public int getWarningCount() {
    return warningCount;
  }

  public void setWarningCount(int warningCount) {
    this.warningCount = warningCount;
  }

  public int getIgnoreCount() {
    return ignoreCount;
  }

  public void setIgnoreCount(int ignoreCount) {
    this.ignoreCount = ignoreCount;
  }

  public int getAlertCount() {
    return alertCount;
  }

  public void setAlertCount(int alertCount) {
    this.alertCount = alertCount;
  }

}
