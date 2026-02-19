package ru.evorsiodev.kissconfig;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SequencedCollection;

public final class ConfigMapNode implements ConfigNode {

  private final LinkedHashMap<String, ConfigNode> children;

  public ConfigMapNode(LinkedHashMap<String, ConfigNode> children) {
    Objects.requireNonNull(children,  "children is null");
    if (children.isEmpty()) {
      throw new IllegalArgumentException("children is empty");
    }
    this.children = new LinkedHashMap<>(children);
  }

  public void addChild(String name, ConfigNode node) {
    children.put(name, node);
  }

  public void addChildren(Collection<? extends Map.Entry<String, ConfigNode>> children) {
    for (Map.Entry<String, ConfigNode> entry : children) {
      addChild(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public SequencedCollection<ConfigNode> getChildren() {
    return children.sequencedValues();
  }

  @Override
  public void acceptVisitor(ConfigNodeVisitor visitor) {
    visitor.acceptMap(this);
  }
}
