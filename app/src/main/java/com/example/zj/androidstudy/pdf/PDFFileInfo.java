package com.example.zj.androidstudy.pdf;

/**
 * Created on 2019-12-24.
 */
public class PDFFileInfo {
  private String fileName;
  private String filePath;
  private long fileSize;
  private String time;

  public boolean isSelect() {
    return select;
  }

  public void setSelect(boolean select) {
    this.select = select;
  }

  private boolean select;

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getFileName() {
    return fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public long getFileSize() {
    return fileSize;
  }
}
