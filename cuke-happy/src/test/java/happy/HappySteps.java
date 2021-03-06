package happy;

import cucumber.api.java.en.*;
import sun.security.util.PendingException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class HappySteps {
    CubeSolver solver = new CubeSolver().usingNumOfFaces(6);
    String notSolvedMessage = null;
    String assertionErrorMessage = null;
    HappyFace anchor = null;
    List<HappyFace> uniqueSolutions = null;
    List<HappyFace> inputFaces = new ArrayList<>(10);

    @Given("^a face called '(.*)'$")
    public void a_face_called_face1(String face1) throws Throwable {
        if (face1==null || "".equals(face1.trim()))
            throw new AssertionError("'name of face cannot be null or blank");
        StringBuilder fileNameB = new StringBuilder();
        if (!face1.startsWith("src/"))
            fileNameB.append("src/test/resources/testFiles/");
        fileNameB.append(face1);
        if (!face1.endsWith(".txt"))
            fileNameB.append(".txt");
        System.out.println(String.format("Using face file [%s]", fileNameB.toString()));

        HappyFace face =HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(fileNameB.toString()).build();
        inputFaces.add(face);
    }

    @When("^I say solve the cube$")
    public void I_say_solve_the_cube() throws Throwable {
        try {
            notSolvedMessage = null;
            assertionErrorMessage = null;
            anchor = solver.solve(inputFaces);
        } catch (RuntimeException e) {
            notSolvedMessage =  e.getMessage();
        } catch (AssertionError e) {
            assertionErrorMessage =  e.getMessage();
        }
    }

    @Then("^give me message that (.*)$")
    public void tell_me_the_cube_needs_six_faces(String arg1) throws Throwable {
        assertEquals(arg1, assertionErrorMessage);
    }

    @Then("^tell me the cube was solved$")
    public void tell_me_the_cube_was_solved() throws Throwable {
        assertNull(notSolvedMessage);
        assertNull(assertionErrorMessage);
    }

    @Then("^print the solved cube$")
    public void print_the_solved_cube() throws Throwable {
        anchor.print();
    }

    @Then("^tell me I could not solve the cube$")
    public void tell_me_cube_not_solved() throws Throwable {
        assertNotNull(notSolvedMessage);
    }


    @When("^I say solve for unique cubes$")
    public void I_say_solve_for_unique_cubes() throws Throwable {
        try {
            notSolvedMessage = null;
            assertionErrorMessage = null;
            uniqueSolutions = solver.solveUnique(inputFaces);
        } catch (RuntimeException e) {
            notSolvedMessage =  e.getMessage();
        } catch (AssertionError e) {
            assertionErrorMessage =  e.getMessage();
        }
    }

    @Then("^tell me the (\\d+) unique cubes are possible$")
    public void tell_me_the_unique_cubes_are_possible(int numberOfUniqueSolutions) throws Throwable {
        assertNotNull(uniqueSolutions);
        assertEquals(numberOfUniqueSolutions, uniqueSolutions.size());
    }

    @Then("^print the unique cubes$")
    public void print_the_unique_cubes() throws Throwable {
        for (HappyFace u : uniqueSolutions)
            u.print();
    }
}
