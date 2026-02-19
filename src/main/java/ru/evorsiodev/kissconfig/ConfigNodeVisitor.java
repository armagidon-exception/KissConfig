package ru.evorsiodev.kissconfig;

public interface ConfigNodeVisitor {

  void acceptMap(ConfigMapNode node);

  void acceptList(ConfigListNode node);

  void acceptLiteral(ConfigLiteralNode node);

}
