package ru.evorsiodev.kissconfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.SequencedCollection;

public final class ConfigListNode implements ConfigNode {

  private final List<ConfigNode> children;

  public ConfigListNode(List<ConfigNode> children) {
    Objects.requireNonNull(children,  "children is null");
    if (children.isEmpty()) {
      throw new IllegalArgumentException("children is empty");
    }
    this.children = new ArrayList<>(children);
  }

  @Override
  public SequencedCollection<ConfigNode> getChildren() {
    return List.copyOf(children);
  }

  public void addChild(ConfigNode node) {
    children.add(node);
  }

  public void addChildren(Collection<ConfigNode> children) {
    this.children.addAll(children);
  }

  @Override
  public void acceptVisitor(ConfigNodeVisitor visitor) {
    visitor.acceptList(this);
  }
}
