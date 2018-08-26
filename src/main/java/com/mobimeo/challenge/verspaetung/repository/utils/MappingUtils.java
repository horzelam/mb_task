package com.mobimeo.challenge.verspaetung.repository.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Simplified CSV mapping utilities.
 */
public class MappingUtils {

    /**
     * Read all the lines from CSV file. Assuming header is ALWAYS the first line and can be skipped.
     *
     * @param resource    input resource file
     * @param mapFunction function to map CSV line to Pojo
     * @param <T>         Pojo type
     *
     * @return list of Pojo instances
     *
     * @throws IOException if failed to read from input
     */
    public static <T> Set<T> readAllData(final String resource, final Function<String[], T> mapFunction) throws IOException {
        return loadLines(new ClassPathResource(resource)).stream()
                .skip(1)
                .map(line -> line.split(","))
                .map(mapFunction)
                .collect(Collectors.toSet());
    }

    public static <T> Set<T> readAllData(final Resource resource, final Function<String[], T> mapFunction) throws IOException {
        return loadLines(resource).stream().skip(1).map(line -> line.split(",")).map(mapFunction).collect(Collectors.toSet());
    }

    private static List<String> loadLines(final Resource csvData) throws IOException {
        try(Scanner scanner = new Scanner(csvData.getInputStream(), StandardCharsets.UTF_8.name())) {
            String text = scanner.useDelimiter("\\A").next();
            return Arrays.asList(text.split("\n"));
        }
    }

}
