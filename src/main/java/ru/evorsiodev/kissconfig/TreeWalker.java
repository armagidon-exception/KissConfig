package ru.evorsiodev.kissconfig;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * TreeWalker is an iterator over ConfigNodes.
 * It must support BFS and DFS searches.
 * When it encounters a map or a list it must handle its children according to search algorithm
 * When it encounters a literal it must not try to handle its children.
 * It must be null safe.
 */
public final class TreeWalker implements Iterator<ConfigNode> {

  private final Deque<ConfigNode> deque;
  private final WalkHandler walkHandler;

  private TreeWalker(ConfigNode root, WalkHandler walkHandler) {
    Objects.requireNonNull(root, "root must not be null");
    Objects.requireNonNull(walkHandler, "walkHandler must not be null");
    this.walkHandler = walkHandler;
    this.deque = new ArrayDeque<>();
    this.deque.add(root);
  }

  @Override
  public boolean hasNext() {
    return !deque.isEmpty();
  }

  @Override
  public ConfigNode next() {
    if (deque.isEmpty())
      throw new NoSuchElementException();
    return walkHandler.advance(this.deque);
  }

  public static TreeWalker walkDepthFirst(ConfigNode root) {
    return new TreeWalker(
        root, WalkHandler.DFS
    );
  }

  public static TreeWalker walkBreadthFirst(ConfigNode root) {
    return new TreeWalker(
        root, WalkHandler.BFS
    );
  }

  private interface WalkHandler {
    WalkHandler DFS = stack -> {
      var node = stack.pop();
      for (ConfigNode child : node.getChildren().reversed()) {
        stack.push(child);
      }
      return node;
    };

    WalkHandler BFS = queue -> {
      var node = queue.removeFirst();
      for (ConfigNode child : node.getChildren()) {
        queue.addLast(child);
      }
      return node;
    };

    ConfigNode advance(Deque<ConfigNode> deque);
  }

}
