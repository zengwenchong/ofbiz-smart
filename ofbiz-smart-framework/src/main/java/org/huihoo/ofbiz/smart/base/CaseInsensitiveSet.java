package org.huihoo.ofbiz.smart.base;

import java.util.Collection;
import java.util.TreeSet;

public class CaseInsensitiveSet extends TreeSet<String> {
  private static final long serialVersionUID = 1L;

  public CaseInsensitiveSet() {
    super(String.CASE_INSENSITIVE_ORDER);
  }

  public CaseInsensitiveSet(Collection<? extends String> c) {
    this();
    addAll(c);
  }
}
