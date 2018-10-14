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


/**
 * Test {@code TranscriptTest} Class
 */
public class TranscriptTest {

    private static final Module GRADE_BMINUS_4MC_A = new ModuleBuilder()
            .withCode("BMINUSA")
            .withCredit(4)
            .withGrade("B-")
            .build();
    private static final Module GRADE_A_4MC_A = new ModuleBuilder()
            .withCode("AA")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module GRADE_A_4MC_B = new ModuleBuilder()
            .withCode("AB")
            .withCredit(4)
            .withGrade("A")
            .build();
    private static final Module INCOMPLETE_4MC_A = new ModuleBuilder()
            .withCode("INCOMPLETEA")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_4MC_B = new ModuleBuilder()
            .withCode("INCOMPLETEB")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_4MC_C = new ModuleBuilder()
            .withCode("INCOMPLETEC")
            .withCredit(4)
            .withCompleted(false)
            .build();
    private static final Module INCOMPLETE_5MC_A = new ModuleBuilder()
            .withCode("INCOMPLETE5A")
            .withCredit(5)
            .withCompleted(false)
            .build();

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
        List<Module> modules = new ArrayList<>(Arrays.asList(
            INCOMPLETE_4MC_A,
            INCOMPLETE_4MC_B,
            INCOMPLETE_4MC_C
        ));
        double capGoal = 4.0;
        List<String> expectedTargetGrades = new ArrayList<>(Arrays.asList(
            "B+",
            "B+",
            "B+"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);

        modules = new ArrayList<>(Arrays.asList(
                INCOMPLETE_4MC_A,
                INCOMPLETE_4MC_B,
                INCOMPLETE_5MC_A,
                INCOMPLETE_4MC_C,
                GRADE_BMINUS_4MC_A
        ));
        capGoal = 4.5;
        expectedTargetGrades = new ArrayList<>(Arrays.asList(
                "A",
                "A",
                "A",
                "A-"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);

        capGoal = 5.0;
        assertTargetGradesEquals(modules, capGoal, null);

        modules = new ArrayList<>(Arrays.asList(
                GRADE_BMINUS_4MC_A
        ));
        assertTargetGradesEquals(modules, capGoal, new ArrayList<>());

        modules = new ArrayList<>(Arrays.asList(
                INCOMPLETE_4MC_A,
                INCOMPLETE_4MC_B,
                INCOMPLETE_4MC_C,
                GRADE_A_4MC_A,
                GRADE_A_4MC_B
        ));
        capGoal = 4.0;
        expectedTargetGrades = new ArrayList<>(Arrays.asList(
                "B",
                "B",
                "B-"
        ));
        assertTargetGradesEquals(modules, capGoal, expectedTargetGrades);
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
