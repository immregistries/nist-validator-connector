package org.immregistries.dqa.nist.validator.connector;

public class Assertion {
  private String virtualId = "";
  private String type = "";
  private String result = "";
  private String severity = "";
  private int line = 0;
  private int column = 0;
  private String path = "";
  private String segment = "";
  private String field = "";
  private String component = "";
  private String description = "";
  
  public String getVirtualId() {
    return virtualId;
  }
  
  public void setVirtualId(String virtualId) {
    this.virtualId = virtualId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getSegment() {
    return segment;
  }

  public void setSegment(String segment) {
    this.segment = segment;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
