package com.kh.spring.common.util.file;

import java.sql.Date;

import lombok.Data;

@Data
public class FileDTO {
   
   private String flIdx;
   private String typeIdx;
   private String originFileName;
   private String renameFileName;
   private String savePath;
   private Date regDate;
   private int isDel;
}