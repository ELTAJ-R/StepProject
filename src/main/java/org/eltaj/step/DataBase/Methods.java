package org.eltaj.step.DataBase;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Methods {

    //checks whether string contains any value other than space, if yes returns true.
    public boolean containsRealValue(String s) {
        return s.chars().distinct().filter(a -> a != ' ').count() > 0;
    }

    //checks if all the necessary fields of form are filled or not.
    public boolean allIsFilled(HttpServletRequest request, int numOfAreasToFill) {
        Map<String, String[]> allParam = request.getParameterMap();
        long numberOfFilledAreas = allParam.entrySet().stream()
                .filter(a -> containsRealValue(a.getValue()[0])).count();
        return numberOfFilledAreas == numOfAreasToFill;
    }

    //takes current time and returns it as a String
    public String now() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.YYYY");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now);
    }

    // gets current user's name from cookies and requires http request to work
    public String findCurrUser(HttpServletRequest request) {
        return Arrays.stream(request.getCookies()).filter(Objects::nonNull).findFirst().get().getValue();
    }

    public Optional<String> getFileAsString(String path) {
        try {
            String result = new BufferedReader(new FileReader(new File(path)))
                    .lines().collect(Collectors.joining("\n"));
            return Optional.of(result);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }

    }

    // if there is no such file message specified in the method will be displayed
    public String getFileOrMessage(String path){
        return getFileAsString(path).orElse("File could not be found in specified location");
    }
}
