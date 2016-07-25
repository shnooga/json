package com.oogie.jackson;

import java.io.File;
        import java.math.BigDecimal;
        import java.util.ArrayList;
        import java.util.List;
        import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaToJSon {

    public static void main(String[] args) {
        JavaToJSon obj = new JavaToJSon();
        obj.run();
    }

    private void run() {
        ObjectMapper mapper = new ObjectMapper();
        Staff staff = createDummyObject();

        try {
            // Convert object to JSON string and save into a file directly
            mapper.writeValue(new File("./src/main/resources/staff.json"), staff);
//            System.out.println("dir: " + new File("").getAbsoluteFile().toString());

            // Convert object to JSON string
            String jsonInString = mapper.writeValueAsString(staff);
            System.out.println(jsonInString);

            // Convert object to JSON string and pretty print
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);
            System.out.println(jsonInString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Staff createDummyObject() {
        Staff staff = new Staff();

        staff.setName("mkyong");
        staff.setAge(33);
        staff.setPosition("Developer");
        staff.setSalary(new BigDecimal("7500"));

        List<String> skills = new ArrayList<String>();
        skills.add("java");
        skills.add("python");

        staff.setSkills(skills);

        return staff;

    }

}
