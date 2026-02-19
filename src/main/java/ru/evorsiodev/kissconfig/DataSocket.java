package ru.evorsiodev.kissconfig;

/***
 * Socket for all IO operations
 */
public interface DataSocket {

  ConfigNode read();

  void save(ConfigNode configNode);

  void close();

}
