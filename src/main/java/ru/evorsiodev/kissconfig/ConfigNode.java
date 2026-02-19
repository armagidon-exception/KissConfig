package ru.evorsiodev.kissconfig;

import java.util.SequencedCollection;

public sealed interface ConfigNode permits ConfigMapNode, ConfigListNode, ConfigLiteralNode {

  SequencedCollection<ConfigNode> getChildren();

  void acceptVisitor(ConfigNodeVisitor visitor);

}
