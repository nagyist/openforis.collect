package org.openforis.collect.persistence.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.Form;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.collect.metamodel.ui.FormSectionComponent;
import org.openforis.collect.metamodel.ui.FormSet;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.UIOptionsConstants;
import org.openforis.collect.metamodel.ui.UITab;
import org.openforis.collect.metamodel.ui.UITabSet;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.model.CollectSurveyContext;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.xml.IdmlParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author S. Ricci
 *
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = {"classpath:test-context.xml"} )
public class UIOptionsBinderTest {

	private static final String OLD_UIOPTIONS_TEST_IDM_XML = "test-old-ui-model-idm.xml";
	private static final String OLD_UIOPTIONS_TEST_XML = "test-uioptions.xml";
	private static final String NEW_UIOPTIONS_TEST_XML = "test-new-model-uioptions.xml";
	
	private static final String EN_LANG_CODE = "en";

	@Autowired
	protected CollectSurveyContext collectSurveyContext;
	
	@Test
	@SuppressWarnings("deprecation")
	public void testUnmarshallOldUIModel() throws IOException {
		String optionsBody = loadOldUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		assertNotNull(uiOptions);
		List<UITabSet> tabSets = uiOptions.getTabSets();
		assertEquals(1, tabSets.size());
		UITabSet clusterRootTabSet = tabSets.get(0);
		assertEquals("cluster", clusterRootTabSet.getName());
		List<UITab> tabs = clusterRootTabSet.getTabs();
		assertEquals(4, tabs.size());
		UITab plotTab = tabs.get(1);
		assertEquals("plot", plotTab.getName());
		String label = plotTab.getLabel(EN_LANG_CODE);
		assertEquals("Plot", label);
		assertEquals(clusterRootTabSet, plotTab.getParent());
		List<UITab> plotInnerTabs = plotTab.getTabs();
		assertEquals(6, plotInnerTabs.size());
		UITab plotDetTab = plotInnerTabs.get(0);
		assertEquals("plot_det", plotDetTab.getName());
	}
	
	@Test
	public void testUnmarshallUIModel() throws IOException {
		String optionsBody = loadNewUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		assertNotNull(uiOptions);
	}
	
	@Test
	public void roundTripMarshallingTest() throws IOException {
		String optionsBody = loadNewUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		String marshalled = binder.marshal(uiOptions);
		UIOptions reloaded = binder.unmarshal(UIOptionsConstants.UI_TYPE, marshalled);
		assertEquals(uiOptions, reloaded);
	}
	
	@Test
	public void roundTripOldModelMarshallingTest() throws IOException {
		String optionsBody = loadOldUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		new File("target/test/output").mkdirs();
		FileOutputStream fos = new FileOutputStream("target/test/output/marshalled.uioptions.xml");
		String marshalled = binder.marshal(uiOptions);
		IOUtils.write(marshalled, fos);
		fos.flush();
		fos.close();
	}

	@Test
	public void testMigrator() throws IdmlParseException, IOException {
		CollectSurvey survey = loadOldUIModelTestSurvey();
		UIOptions uiOptions = survey.getUIOptions();
		List<FormSet> formSets = uiOptions.getFormSets();
		assertEquals(2, formSets.size());
		FormSet clusterFormSet = formSets.get(0);
		List<Form> clusterForms = clusterFormSet.getForms();
		assertEquals(3, clusterForms.size());
		{
			Form form = clusterForms.get(0);
			String label = form.getLabel(null);
			assertEquals("Cluster", label);
			List<FormSection> formSections = form.getFormSections();
			assertEquals(1, formSections.size());
			FormSection mainFormSection = formSections.get(0);
			List<FormSectionComponent> mainFormSectionChildren = mainFormSection.getChildren();
			assertEquals(14, mainFormSectionChildren.size());
			FormSectionComponent taskTable = mainFormSectionChildren.get(0);
			assertTrue(taskTable instanceof Table);
			String taskLabel = ((Table) taskTable).getLabel(null);
			assertEquals("Quality assurance field", taskLabel);
			
			FormSectionComponent idField = mainFormSectionChildren.get(1);
			assertTrue(idField instanceof Field);
			AttributeDefinition idAttribute = ((Field) idField).getAttribute();
			assertEquals("id", idAttribute.getName());
			
			FormSectionComponent vehicleLocationField = mainFormSectionChildren.get(7);
			assertTrue(vehicleLocationField instanceof Field);
			AttributeDefinition vehicleLocationAttribute = ((Field) vehicleLocationField).getAttribute();
			assertEquals("vehicle_location", vehicleLocationAttribute.getName());
		}
		{
			Form form = clusterForms.get(1);
			String label = form.getLabel(null);
			assertEquals("Plot", label);
			List<Form> subForms = form.getForms();
			assertEquals(6, subForms.size());
			
			{
				Form subForm = subForms.get(0);
				String subFormLabel = subForm.getLabel(null);
				assertEquals("Details (2)", subFormLabel);
				FormSection mainFormSection = subForm.getFormSections().get(0);
				List<FormSectionComponent> children = mainFormSection.getChildren();
				assertEquals(34, children.size());
			}
			
			{
				Form subForm = subForms.get(4);
				String subFormLabel = subForm.getLabel(null);
				assertEquals("Stumps (5b)", subFormLabel);
				FormSection mainFormSection = subForm.getFormSections().get(0);
				List<FormSectionComponent> children = mainFormSection.getChildren();
				assertEquals(1, children.size());
			}
		}
		
	}
	
	private String loadOldUIModelTestOptions() throws IOException {
		return loadTextResourceFile(OLD_UIOPTIONS_TEST_XML);
	}
	
	private String loadNewUIModelTestOptions() throws IOException {
		return loadTextResourceFile(NEW_UIOPTIONS_TEST_XML);
	}

	protected String loadTextResourceFile(String resourceName) throws IOException {
		URL fileUrl = ClassLoader.getSystemResource(resourceName);
		InputStream is = fileUrl.openStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, "UTF-8");
		String result = writer.toString();
		return result;
	}
	
	protected CollectSurvey loadOldUIModelTestSurvey() throws IdmlParseException, IOException  {
		URL idm = ClassLoader.getSystemResource(OLD_UIOPTIONS_TEST_IDM_XML);
		InputStream is = idm.openStream();
		CollectSurveyIdmlBinder binder = new CollectSurveyIdmlBinder(collectSurveyContext);
		CollectSurvey survey = (CollectSurvey) binder.unmarshal(is);
		survey.setName("archenland1");
		return survey;
	}
}
