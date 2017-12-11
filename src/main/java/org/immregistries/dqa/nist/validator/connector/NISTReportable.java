package org.immregistries.dqa.nist.validator.connector;

import java.util.ArrayList;
import java.util.List;

import org.immregistries.dqa.hl7util.Reportable;
import org.immregistries.dqa.hl7util.SeverityLevel;
import org.immregistries.dqa.hl7util.model.CodedWithExceptions;
import org.immregistries.dqa.hl7util.model.ErrorLocation;

public class NISTReportable implements Reportable {

  private CodedWithExceptions applicationErrorCode = new CodedWithExceptions();
  private String diagnosticMessage = null;
  private CodedWithExceptions hl7ErrorCode = new CodedWithExceptions();
  private List<ErrorLocation> hl7LocationList = new ArrayList<>();
  private String reportedMessage = null;
  private SeverityLevel severity = null;

  public void setApplicationErrorCode(CodedWithExceptions applicationErrorCode) {
    this.applicationErrorCode = applicationErrorCode;
  }

  public void setDiagnosticMessage(String diagnosticMessage) {
    this.diagnosticMessage = diagnosticMessage;
  }

  public void setHl7ErrorCode(CodedWithExceptions hl7ErrorCode) {
    this.hl7ErrorCode = hl7ErrorCode;
  }

  public void setHl7LocationList(List<ErrorLocation> hl7LocationList) {
    this.hl7LocationList = hl7LocationList;
  }

  public void setReportedMessage(String reportedMessage) {
    this.reportedMessage = reportedMessage;
  }

  public void setSeverity(SeverityLevel severity) {
    this.severity = severity;
  }

  @Override
  public CodedWithExceptions getApplicationErrorCode() {
    return applicationErrorCode;
  }

  @Override
  public String getDiagnosticMessage() {
    return diagnosticMessage;
  }

  @Override
  public CodedWithExceptions getHl7ErrorCode() {
    return hl7ErrorCode;
  }

  @Override
  public List<ErrorLocation> getHl7LocationList() {
    return hl7LocationList;
  }

  @Override
  public String getReportedMessage() {
    return reportedMessage;
  }

  @Override
  public SeverityLevel getSeverity() {
    return severity;
  }

}
