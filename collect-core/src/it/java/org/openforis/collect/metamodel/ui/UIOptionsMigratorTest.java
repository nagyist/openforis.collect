package org.openforis.collect.metamodel.ui;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openforis.collect.CollectIntegrationTest;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.xml.IdmlParseException;

/**
 * 
 * @author S. Ricci
 *
 */
public class UIOptionsMigratorTest extends CollectIntegrationTest {

	private CollectSurvey survey;
	@SuppressWarnings("deprecation")
	private UIOptions uiOptions;

	@Before
	public void setUp() throws IdmlParseException {
		survey = loadSurvey();
		uiOptions = survey.getUIOptions();
	}

	@Test
	public void testMigration() {
		UIOptionsMigrator migrator = new UIOptionsMigrator();
		UIFormSets uiModel = migrator.migrateToUIModel(uiOptions);
		assertNotNull(uiModel);
		Schema schema = survey.getSchema();
		{
			EntityDefinition cluster = schema.getRootEntityDefinition("cluster");
			FormSet formSet = uiModel.getFormSetByRootEntityId(cluster.getId());
			List<Form> forms = formSet.getForms();
			assertEquals(3, forms.size());
			
			//cluster form
			{
				Form form = forms.get(0);
				FormSection formSection = form.getMainFormSection();
				List<FormSectionComponent> children = formSection.getChildren();
				assertEquals(14, children.size());
				{
					//task
					FormSectionComponent component = children.get(0);
					assertTrue(component instanceof Table);
					Table taskTable = (Table) component;
					assertNotNull(taskTable.getEntity());
					assertEquals("task", taskTable.getEntity().getName());
					
					//task/task
					assertEquals(3, taskTable.getHeadingComponents().size());
					TableHeadingComponent heading = taskTable.getHeadingComponents().get(0);
					assertTrue(heading instanceof Column);
					Column col = (Column) heading;
					assertEquals(729, col.getAttributeId());
				}
			}
			//plot form
			{
				Form plotForm = forms.get(1);
				List<Form> subforms = plotForm.getForms();
				assertEquals(6, subforms.size());
				Form detailForm = subforms.get(0);
				FormSection formSection = detailForm.getMainFormSection();
				List<FormSectionComponent> children = formSection.getChildren();
				assertEquals(34, children.size());
				{
					//plot_no
					{
						FormSectionComponent component = children.get(0);
						assertTrue(component instanceof Field);
						Field plotNoField = (Field) component;
						assertEquals(749, plotNoField.getAttributeId());
					}
					//time study (single entity -> form section)
					{
						FormSectionComponent component = children.get(2);
						assertTrue(component instanceof FormSection);
						FormSection section = (FormSection) component;
						assertEquals(3, section.getChildren().size());
						//start time
						FormSectionComponent startTimeComp = section.getChildren().get(1);
						assertTrue(startTimeComp instanceof Field);
						Field startTimeField = (Field) startTimeComp;
						assertEquals(753, startTimeField.getAttributeId());
					}
				}
			}
		}
	}

}
