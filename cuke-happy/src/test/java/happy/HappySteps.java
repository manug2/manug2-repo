package happy;

import cucumber.api.java.en.*;
import static org.junit.Assert.*;

public class HappySteps {
    CubeSolver solver = new CubeSolver(5);
    String errorMessage = null;

    @Given("^a face called '(.*)'$")
    public void a_face_called_arg1(String arg1) throws Throwable {
        if (arg1==null || "".equals(arg1.trim()))
            throw new AssertionError("'name of face cannot be null or blank");
        StringBuilder fileNameB = new StringBuilder();
        if (!arg1.startsWith("src/"))
            fileNameB.append("src/test/resources/testFiles/");
        fileNameB.append(arg1);
        if (!arg1.endsWith(".txt"))
            fileNameB.append(".txt");
        System.out.println(String.format("Using face file [%s]", fileNameB.toString()));

        solver.loadFace(fileNameB.toString());
    }

    @When("^I say solve the cube$")
    public void I_say_solve_the_cube() throws Throwable {
        try {
            solver.solve();
        } catch (AssertionError e) {
            errorMessage =  e.getMessage();
        }
    }

    @Then("^give me message that (.*)$")
    public void tell_me_the_cube_needs_six_faces(String arg1) throws Throwable {
        assertEquals(arg1, errorMessage);
    }

    @Then("^tell me the cube was solved$")
    public void tell_me_the_cube_was_solved() throws Throwable {
        assertNull(errorMessage);
    }

    @Then("^print the solved cube$")
    public void print_the_solved_cube() throws Throwable {
        fail("Not yet implemented");
    }

}
