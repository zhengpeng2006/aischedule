package com.asiainfo.monitor.tools.util;

import java.io.Serializable;
import java.util.Date;

public class LineValue implements Serializable {
  public Date date = null;
  public double value = 0;
  public LineValue(Date date, double value) {
    this.date = date;
    this.value = value;
  }
  }
