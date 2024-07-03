package org.benchmarx.examples.familiestopersons.testsuite.batch.bwd;

import org.benchmarx.BXTool;
import org.benchmarx.examples.familiestopersons.testsuite.Decisions;
import org.benchmarx.examples.familiestopersons.testsuite.FamiliesToPersonsTestCase;
import org.junit.Test;

import Families.FamilyRegister;
import Persons.PersonRegister;

/**
 * Test cases for backward transformations with parameters E and P set to true
 * Please note, that in general this leads to non-deterministic behavior, which
 * is hard to test. Thus, we restricted ourselves to a PersonRegister
 * configuration, which allows to deterministically execute the backward
 * transformation. (c.f., Test Case 2d on GitHub).
 */
public class BatchBwdEAndP extends FamiliesToPersonsTestCase {

	public BatchBwdEAndP(BXTool<FamilyRegister, PersonRegister, Decisions> tool) {
		super(tool);
	}

	/**
	 * <b>Test</b> for creation of a single male person (Flanders, Rod).<br/>
	 * <b>Expect</b> the creation of a family member in the families model with the
	 * given first name, in a suitable family. Creation of parents is
	 * preferred.<br/>
	 * <b>Features</b>: bwd, runtime
	 */
	@Test
	public void testCreateMalePersonAsSon() {
		tool.noPrecondition();
		// ---------------------------------
		util.configure()//
				.makeDecision(Decisions.PREFER_EXISTING_FAMILY_TO_NEW, true)
				.makeDecision(Decisions.PREFER_CREATING_PARENT_TO_CHILD, true);
		tool.updateConfig();
		tool.performAndPropagateTargetEdit(trgEdit(helperPerson::createRod));
		// ---------------------------------
		util.assertPostcondition("OneFamilyWithOneFamilyMember", "PersonOneMaleMember");
	}

	/**
	 * <b>Test</b> for creation of family members in existing families.<br/>
	 * <b>Expect</b> the creation of a family member in the families model with the
	 * given first name, in a suitable family. Creation of children is
	 * preferred.<br/>
	 * <b>Features</b>: bwd, runtime
	 */
	@Test
	public void testCreateFamilyMembersInExistingFamilyAsParents() {
		tool.noPrecondition();
		// ---------------------------------
		util.configure()//
				.makeDecision(Decisions.PREFER_EXISTING_FAMILY_TO_NEW, true)//
				.makeDecision(Decisions.PREFER_CREATING_PARENT_TO_CHILD, true);
		tool.updateConfig();
		tool.performAndPropagateTargetEdit(trgEdit(//
				helperPerson::createRod, //
				helperPerson::createHomer, //
				helperPerson::createMarge));
		// ---------------------------------
		util.assertPostcondition("FamilyWithParentsOnly", "PersonsMultiDeterministic");
	}
}
