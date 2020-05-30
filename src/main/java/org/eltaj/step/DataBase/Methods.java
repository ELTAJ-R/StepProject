package org.eltaj.step.DataBase;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class Methods {

    //checks whether string contains any value other than space, if yes returns true.
    public boolean containsRealValue(String s){
        return s.chars().distinct().filter(a->a!=' ').count()>0;
    }
//checks if all the necessary fields of form are filled or not.
    public boolean allIsFilled(HttpServletRequest request,int numOfAreasToFill){
        Map<String, String[]> allParam = request.getParameterMap();
        long numberOfFilledAreas = allParam.entrySet().stream()
                .filter(a -> containsRealValue(a.getValue()[0])).count();
       return numberOfFilledAreas==numOfAreasToFill;
    }

    //takes current time and returns it as a String
    public String now() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.YYYY");
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now);
    }

    // gets current user's name from cookies and requires http request to work
    public String findCurrUser(HttpServletRequest request){
      return Arrays.stream(request.getCookies()).filter(Objects::nonNull).findFirst().get().getValue();
    }
}
