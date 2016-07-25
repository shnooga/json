package com.oogie.jackson;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSonToJava {

    public static void main(String[] args) {
        JSonToJava obj = new JSonToJava();
        obj.run();
    }

    private void run() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert JSON string from file to Object
            Staff staff = mapper.readValue(new File("./src/main/resources/staff.json"), Staff.class);
//            mapper.writeValue(new File("./src/main/resources/staff.json"), staff);
            System.out.println(staff);

            // Convert JSON string to Object
            String jsonInString = "{\"name\":\"mkyong\",\"salary\":7500,\"skills\":[\"java\",\"python\"]}";
            Staff staff1 = mapper.readValue(jsonInString, Staff.class);
            System.out.println(staff1);

            //Pretty print
            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff1);
            System.out.println(prettyStaff1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
