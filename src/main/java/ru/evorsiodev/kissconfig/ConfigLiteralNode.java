package ru.evorsiodev.kissconfig;

import java.util.Collections;
import java.util.SequencedCollection;

public final class ConfigLiteralNode implements ConfigNode {

  private Object value;

  @Override
  public SequencedCollection<ConfigNode> getChildren() {
    return Collections.emptyList();
  }

  @Override
  public void acceptVisitor(ConfigNodeVisitor visitor) {
    visitor.acceptLiteral(this);
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
