package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PropertiesReader {

  private static final String PATH_TO_RESOURCES = "src/test/java/testdata/";
  private static final String PROPERTIES = ".properties";
  private static final String READING_PROPERTIES_FILES_FAILED = "Reading properties files failed";
  private static final Map<String, String> MAP_OF_PROPERTIES =
      Collections.unmodifiableMap(propertiesValuesMap());

  private static Map<String, String> propertiesValuesMap() {
    Map<String, String> map = new HashMap<>();
    try {
      for (String path : files()) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
          map.putAll(stream
              .filter(str -> !(str.contains("#")))
              .map(entries -> entries.split("="))
              .collect(Collectors.toMap(key -> key[0], value -> value[1])));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(READING_PROPERTIES_FILES_FAILED, e);
    }
    return map;
  }

  private static List<String> files() throws IOException {
    try (Stream<Path> walk = Files.walk(Paths.get(PATH_TO_RESOURCES))) {
      return walk
          .map(Path::toString)
          .filter(x -> x.endsWith(PROPERTIES))
          .collect(Collectors.toList());
    }
  }

  public static String fetchValue(String key) {
    return MAP_OF_PROPERTIES.get(key);
  }
}
