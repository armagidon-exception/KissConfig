package ru.evorsiodev.kissconfig;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeWalkerTest {

  @Test
  void test_null_args_throw_npe() {
    assertThrows(NullPointerException.class, () -> TreeWalker.walkDepthFirst(null));
    assertThrows(NullPointerException.class, () -> TreeWalker.walkBreadthFirst(null));
  }

  @Test
  void test_literal_has_no_children() {
    ConfigNode node = new ConfigLiteralNode();
    var treeWalker = assertDoesNotThrow(() -> TreeWalker.walkDepthFirst(node));
    var treeWalkerStubbed = Mockito.spy(treeWalker);

    while (treeWalkerStubbed.hasNext()) {
      treeWalkerStubbed.next();
    }

    verify(treeWalkerStubbed, times(1)).next();
  }

  @Test
  void test_map_dfs() {
    ConfigNode firstLiteral = (new ConfigLiteralNode());
    ConfigNode secondLiteral = (new ConfigLiteralNode());
    ConfigNode thirdLiteral = (new ConfigLiteralNode());

    ConfigNode map = (new ConfigMapNode(
        of(
            "test", firstLiteral,
            "test2", secondLiteral
        )
    ));

    ConfigNode root = (new ConfigMapNode(
        of(
            "test2", map,
            "test", thirdLiteral
        )
    ));

    var treeWalker = TreeWalker.walkDepthFirst(root);
    List<ConfigNode> visited = new ArrayList<>();
    while (treeWalker.hasNext()) {
      visited.add(treeWalker.next());
    }

    assertIterableEquals(List.of(
        root,
        map,
        firstLiteral,
        secondLiteral,
        thirdLiteral
    ), visited);
  }


  @Test
  void test_map_bfs() {
    ConfigNode firstLiteral = (new ConfigLiteralNode());
    ConfigNode secondLiteral = (new ConfigLiteralNode());
    ConfigNode thirdLiteral = (new ConfigLiteralNode());

    ConfigNode map = (new ConfigMapNode(
        of(
            "test", firstLiteral,
            "test2", secondLiteral
        )
    ));

    ConfigNode root = (new ConfigMapNode(
        of(
            "test2", map,
            "test", thirdLiteral
        )
    ));

    var treeWalker = TreeWalker.walkBreadthFirst(root);
    List<ConfigNode> visited = new ArrayList<>();
    while (treeWalker.hasNext()) {
      visited.add(treeWalker.next());
    }

    assertIterableEquals(List.of(
        root,
        map,
        thirdLiteral,
        firstLiteral,
        secondLiteral
    ), visited);
  }

  @Test
  void test_list_dfs() {
    ConfigNode firstLiteral = (new ConfigLiteralNode());
    ConfigNode secondLiteral = (new ConfigLiteralNode());
    ConfigNode thirdLiteral = (new ConfigLiteralNode());

    ConfigNode map = (new ConfigMapNode(
        of(
            "test", firstLiteral,
            "test2", secondLiteral
        )
    ));

    ConfigNode root = new ConfigListNode(
        List.of(
            map,
            thirdLiteral
        )
    );

    var treeWalker = TreeWalker.walkDepthFirst(root);
    List<ConfigNode> visited = new ArrayList<>();
    while (treeWalker.hasNext()) {
      visited.add(treeWalker.next());
    }

    assertIterableEquals(List.of(
        root,
        map,
        firstLiteral,
        secondLiteral,
        thirdLiteral
    ), visited);
  }


  @Test
  void test_list_bfs() {
    ConfigNode firstLiteral = (new ConfigLiteralNode());
    ConfigNode secondLiteral = (new ConfigLiteralNode());
    ConfigNode thirdLiteral = (new ConfigLiteralNode());

    ConfigNode map = (new ConfigMapNode(
        of(
            "test", firstLiteral,
            "test2", secondLiteral
        )
    ));

    ConfigNode root = (new ConfigListNode(
        List.of(
            map,
            thirdLiteral
        )
    ));

    var treeWalker = TreeWalker.walkBreadthFirst(root);
    List<ConfigNode> visited = new ArrayList<>();
    while (treeWalker.hasNext()) {
      visited.add(treeWalker.next());
    }

    assertIterableEquals(List.of(
        root,
        map,
        thirdLiteral,
        firstLiteral,
        secondLiteral
    ), visited);
  }

  @Test
  void test_dfs_empty_throws_exception() {
    ConfigNode firstLiteral = new ConfigLiteralNode();
    var walker = TreeWalker.walkDepthFirst(firstLiteral);
    assertDoesNotThrow(walker::next);
    assertThrows(NoSuchElementException.class, walker::next);
  }

  @Test
  void test_bfs_empty_throws_exception() {
    ConfigNode literal = new ConfigLiteralNode();
    var walker = TreeWalker.walkBreadthFirst(literal);
    assertDoesNotThrow(walker::next);
    assertThrows(NoSuchElementException.class, walker::next);
  }

  @Test
  void test_hasNext_state() {
    ConfigNode firstLiteral = new ConfigLiteralNode();

    var walker = TreeWalker.walkDepthFirst(firstLiteral);
    assertTrue(walker.hasNext());
    assertDoesNotThrow(walker::next);
    assertFalse(walker.hasNext());
  }

  private static <K, V> LinkedHashMap<K, V> of(
      K k1, V v1, K k2, V v2
  ) {
    LinkedHashMap<K, V> map = new LinkedHashMap<>();
    map.put(k1, v1);
    map.put(k2, v2);
    return map;
  }

}