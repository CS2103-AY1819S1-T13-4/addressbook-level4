package seedu.address.model;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.TypicalModules.MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP;
import static seedu.address.testutil.TypicalModules.getModulesWithNonGradeAffectingModules;
import static seedu.address.testutil.TypicalModules.getModulesWithoutNonGradeAffectingModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

//@@author jeremiah-ang
/**
 * Test {@code TranscriptTest} Class
 */
public class TranscriptTest {

    private static final Module COMPLETED_BMINUS_FOURMC_A = new ModuleBuilder()
            .withCode("BMINUSA")
            .withCredit(4)
            .withGrade("B-")
            .build();

    private static final Module COMPLETED_A_FOURMC_A = new ModuleBuilder()
            .withCode("AA")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module COMPLETED_A_FOURMC_B = new ModuleBuilder()
            .withCode("AB")
            .withCredit(4)
            .withGrade("A")
            .build();

    private static final Module INCOMPLETE_FOURMC_A = new ModuleBuilder()
            .withCode("INCOMPLETEA")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_FOURMC_B = new ModuleBuilder()
            .withCode("INCOMPLETEB")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_FOURMC_C = new ModuleBuilder()
            .withCode("INCOMPLETEC")
            .withCredit(4)
            .withCompleted(false)
            .build();

    private static final Module INCOMPLETE_FIVEMC_A = new ModuleBuilder()
            .withCode("INCOMPLETE5A")
            .withCredit(5)
            .withCompleted(false)
            .build();

    /**
     * Set A - all incomplete
     */
    public final List<Module> MODULES_SET_A_MODULES = new ArrayList<>(Arrays.asList(
            INCOMPLETE_FOURMC_A,
            INCOMPLETE_FOURMC_B,
            INCOMPLETE_FOURMC_C
    ));
    public final double MODULES_SET_A_CAP_GOAL = 4.0;
    public final List<String> MODULES_SET_A_EXPECTED_TARGET_GRADES = new ArrayList<>(Arrays.asList(
            "B+",
            "B+",
            "B+"
    ));

    /**
     * Set B - some completed, some incomplete
     */
    public final List<Module> MODULES_SET_B_MODULES = new ArrayList<>(Arrays.asList(
        INCOMPLETE_FOURMC_A,
        INCOMPLETE_FOURMC_B,
        INCOMPLETE_FIVEMC_A,
        INCOMPLETE_FOURMC_C,
        COMPLETED_BMINUS_FOURMC_A
    ));
    public final double MODULES_SET_B_CAP_GOAL = 4.5;
    public final List<String> MODULES_SET_B_EXPECTED_TARGET_GRADES = new ArrayList<>(Arrays.asList(
            "A",
            "A",
            "A",
            "A-"
    ));

    /**
     * Set C - same modules as Set B but a unachievable goal
     */
    public final double MODULES_SET_C_CAP_GOAL = 5.0;
    public final List<String> MODULES_SET_C_EXPECTED_TARGET_GRADES = null;

    /**
     * Set D - All completed modules
     */
    public final List<Module> MODULES_SET_D_MODULES = new ArrayList<>(Arrays.asList(
        COMPLETED_BMINUS_FOURMC_A
    ));
    public final double MODULES_SET_D_CAP_GOAL = 5.0;
    public final List<String> MODULES_SET_D_EXPECTED_TARGET_GRADES = new ArrayList<>(Arrays.asList());

    /**
     * Set E - Very low Goal
     */
    public final List<Module> MODULES_SET_E_MODULES = new ArrayList<>(Arrays.asList(
        INCOMPLETE_FOURMC_A,
        INCOMPLETE_FOURMC_B,
        INCOMPLETE_FOURMC_C,
        COMPLETED_A_FOURMC_A,
        COMPLETED_A_FOURMC_B
    ));
    public final double MODULES_SET_E_CAP_GOAL = 2.0;
    public final List<String> MODULES_SET_E_EXPECTED_TARGET_GRADES = new ArrayList<>(Arrays.asList(
            "D",
            "D",
            "D"
    ));

    @Test
    public void typicalModulesCapScore() {
        List<Module> modules = getModulesWithoutNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);
    }

    @Test
    public void calculateCapScoreWithSuModule() {
        List<Module> modules = getModulesWithNonGradeAffectingModules();
        assertCapScoreEquals(modules, MODULES_WITHOUT_NON_AFFECTING_MODULES_CAP);

    }

    @Test
    public void calculateTargetGrades() {

        assertTargetGradesEquals(MODULES_SET_A_MODULES, MODULES_SET_A_CAP_GOAL, MODULES_SET_A_EXPECTED_TARGET_GRADES);
        assertTargetGradesEquals(MODULES_SET_B_MODULES, MODULES_SET_B_CAP_GOAL, MODULES_SET_B_EXPECTED_TARGET_GRADES);
        assertTargetGradesEquals(MODULES_SET_B_MODULES, MODULES_SET_C_CAP_GOAL, MODULES_SET_C_EXPECTED_TARGET_GRADES);
        assertTargetGradesEquals(MODULES_SET_D_MODULES, MODULES_SET_D_CAP_GOAL, MODULES_SET_D_EXPECTED_TARGET_GRADES);
        assertTargetGradesEquals(MODULES_SET_E_MODULES, MODULES_SET_E_CAP_GOAL, MODULES_SET_E_EXPECTED_TARGET_GRADES);

    }

    /**
     * Assert that the modules will have the CAP score of expectedCapScore
     * @param modules
     * @param expectedCapScore
     */
    private void assertCapScoreEquals(List<Module> modules, Double expectedCapScore) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        double cap = transcript.getCap();
        assertEquals(Double.valueOf(cap), expectedCapScore);
    }

    /**
     * Assert that the given modules and cap goal will result in expected target grades
     * @param modules
     * @param capGoal
     * @param expectedTargetGrades
     */
    private void assertTargetGradesEquals(List<Module> modules, Double capGoal, List<String> expectedTargetGrades) {
        Transcript transcript = new Transcript();
        transcript.setModules(modules);
        transcript.setCapGoal(capGoal);
        ObservableList<Module> targetModules = transcript.getTargetModuleGrade();

        if (expectedTargetGrades == null) {
            assertEquals(targetModules, null);
            return;
        }

        List<String> targetGrades = new ArrayList<>();
        targetModules.forEach(module -> targetGrades.add(module.getGrade().value));
        String targetGradesString = String.join(" ", targetGrades);
        String expectedTargetGradesString = String.join(" ", expectedTargetGrades);
        assertEquals(targetGradesString, expectedTargetGradesString);
    }

}
